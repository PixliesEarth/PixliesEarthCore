package eu.pixliesearth.nations.managers.dynmap;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.settlements.Settlement;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.nations.managers.dynmap.area.AreaStyle;
import eu.pixliesearth.nations.managers.dynmap.commons.Direction;
import eu.pixliesearth.nations.managers.dynmap.players.PlayerSetUpdate;
import eu.pixliesearth.nations.managers.dynmap.pojo.NationBlock;
import eu.pixliesearth.nations.managers.dynmap.pojo.NationBlocks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static eu.pixliesearth.nations.managers.dynmap.area.AreaCommon.*;
import static eu.pixliesearth.nations.managers.dynmap.commons.Constant.*;
import static eu.pixliesearth.nations.managers.dynmap.players.PlayerSetCommon.updatePlayerSets;

public class DynmapEngine {
    private static Logger log;

    private Map<String, AreaMarker> resareas = new HashMap<>();
    private Map<String, Marker> resmark = new HashMap<>();
    private boolean reload = false;
    private Plugin dynmap;
    private Main factions;
    private DynmapCommonAPI dynmapAPI;
    private boolean displayWarps;

    // Status of the plugin.
    private boolean stop;
    private MarkerSet set;
    private MarkerAPI markerAPI;
    private Main factionAPI;
    private boolean playersets;
    private int blocksize;
    private long updperiod;
    private String infoWindow;
    private AreaStyle defstyle;
    private Map<String, AreaStyle> cusstyle;
    private Set<String> visible;
    private Set<String> hidden;

    public static void info(String msg) {
        log.log(Level.INFO, msg);
    }

    public static void severe(String msg) {
        log.log(Level.SEVERE, msg);
    }

    public Plugin getDynmap() {
        return dynmap;
    }

    public void setDynmap(Plugin dynmap) {
        this.dynmap = dynmap;
    }

    public Plugin getFactions() {
        return factions;
    }

    public boolean isStop() {
        return this.stop;
    }

    public long getUpdperiod() {
        return updperiod;
    }

    public MarkerAPI getMarkerAPI() {
        return markerAPI;
    }

    public void onEnable(DynmapCommonAPI dynmapAPIGiven) {
        log = Main.getInstance().getLogger();
        info("initializing");

        // Get Dynmap plugin
        dynmap = Bukkit.getPluginManager().getPlugin("dynmap");
        if (dynmap == null) {
            severe("Cannot find dynmap!");
            return;
        }

        // Get Dynmap API
        dynmapAPI = dynmapAPIGiven; /* Get API */


        // Get Factions
        factions = Main.getInstance();
        if (factions == null) {
            severe("Cannot find Factions!");
            return;
        }

        // If both enabled, activate
        if (dynmap.isEnabled() && factions.isEnabled()) {
            activate();
        }

        try {
            final MetricsLite metricsLite = new MetricsLite(factions);
            metricsLite.start();
        } catch (final IOException iox) {
            severe(iox.getMessage());
        }
    }

    public void onDisable() {
        if (set != null) {
            set.deleteMarkerSet();
            set = null;
        }
        resareas.clear();
        stop = true;
    }

    public int scheduleSyncDelayedTask(final Runnable run, final long period) {
        return factionAPI.getServer().getScheduler().scheduleSyncDelayedTask(factions, run, period);
    }

    public int scheduleSyncDelayedTask(final Runnable run) {
        return factions.getServer().getScheduler().scheduleSyncDelayedTask(factions, run);
    }

    public void requestUpdatePlayerSet(final String factionUUID) {
        if (playersets) {
            scheduleSyncDelayedTask(new PlayerSetUpdate(this, factionUUID));
        }
    }

    public void requestUpdateFactions() {
        final NationsUpdate factionsUpdate = new NationsUpdate(this);
        factionsUpdate.setRunOnce(true);
        scheduleSyncDelayedTask(factionsUpdate, 20);
    }

    private boolean isVisible(final String id, final String worldName) {
        if (visible != null && visible.size() > 0) {
            if ((!visible.contains(id)) && (!visible.contains("world:" + worldName))) {
                return false;
            }
        }
        if (hidden != null && hidden.size() > 0) {
            return !(hidden.contains(id) || hidden.contains("world:" + worldName));
        }
        return true;
    }

    /**
     * Handle specific Nation on specific world
     */
    private void handleNationOnWorld(String factionName, Nation fact, String world, LinkedList<NationBlock> blocks, Map<String, AreaMarker> newmap) {
        int poly_index = 0; /* Index of polygon for given Nation */

        /* Build popup */
        final String desc = formatInfoWindow(infoWindow, fact);

        /* Handle areas */
        if (isVisible(factionName, world)) {
            if (blocks.isEmpty())
                return;
            LinkedList<NationBlock> nodevals = new LinkedList<>();
            TileFlags curblks = new TileFlags();
            /* Loop through blocks: set flags on blockmaps */
            for (final NationBlock b : blocks) {
                curblks.setFlag(b.getX(), b.getZ(), true); /* Set flag for block */
                nodevals.addLast(b);
            }
            /* Loop through until we don't find more areas */
            while (nodevals != null) {
                LinkedList<NationBlock> ournodes = null;
                LinkedList<NationBlock> newlist = null;
                TileFlags ourblks = null;
                int minx = Integer.MAX_VALUE;
                int minz = Integer.MAX_VALUE;
                for (NationBlock node : nodevals) {
                    final int nodex = node.getX();
                    final int nodez = node.getZ();
                    /* If we need to start shape, and this block is not part of one yet */
                    if ((ourblks == null) && curblks.getFlag(nodex, nodez)) {
                        ourblks = new TileFlags();  /* Create map for shape */
                        ournodes = new LinkedList<>();
                        floodFillTarget(curblks, ourblks, nodex, nodez);   /* Copy shape */
                        ournodes.add(node); /* Add it to our node list */
                        minx = nodex;
                        minz = nodez;
                    }
                    /* If shape found, and we're in it, add to our node list */
                    else if ((ourblks != null) && ourblks.getFlag(nodex, nodez)) {
                        ournodes.add(node);
                        if (nodex < minx) {
                            minx = nodex;
                            minz = nodez;
                        } else if ((nodex == minx) && (nodez < minz)) {
                            minz = nodez;
                        }
                    } else {  /* Else, keep it in the list for the next polygon */
                        if (newlist == null) newlist = new LinkedList<>();
                        newlist.add(node);
                    }
                }
                nodevals = newlist; /* Replace list (null if no more to process) */
                if (ourblks != null) {
                    /* Trace outline of blocks - start from minx, minz going to x+ */
                    int cur_x = minx;
                    int cur_z = minz;
                    Direction dir = Direction.XPLUS;
                    ArrayList<int[]> linelist = new ArrayList<>();
                    linelist.add(new int[]{minx, minz}); // Add start point
                    while ((cur_x != minx) || (cur_z != minz) || (dir != Direction.ZMINUS)) {
                        switch (dir) {
                            case XPLUS: /* Segment in X+ Direction */
                                if (!ourblks.getFlag(cur_x + 1, cur_z)) { /* Right turn? */
                                    linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                    dir = Direction.ZPLUS;  /* Change Direction */
                                } else if (!ourblks.getFlag(cur_x + 1, cur_z - 1)) {  /* Straight? */
                                    cur_x++;
                                } else {  /* Left turn */
                                    linelist.add(new int[]{cur_x + 1, cur_z}); /* Finish line */
                                    dir = Direction.ZMINUS;
                                    cur_x++;
                                    cur_z--;
                                }
                                break;
                            case ZPLUS: /* Segment in Z+ Direction */
                                if (!ourblks.getFlag(cur_x, cur_z + 1)) { /* Right turn? */
                                    linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                    dir = Direction.XMINUS;  /* Change Direction */
                                } else if (!ourblks.getFlag(cur_x + 1, cur_z + 1)) {  /* Straight? */
                                    cur_z++;
                                } else {  /* Left turn */
                                    linelist.add(new int[]{cur_x + 1, cur_z + 1}); /* Finish line */
                                    dir = Direction.XPLUS;
                                    cur_x++;
                                    cur_z++;
                                }
                                break;
                            case XMINUS: /* Segment in X- Direction */
                                if (!ourblks.getFlag(cur_x - 1, cur_z)) { /* Right turn? */
                                    linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                    dir = Direction.ZMINUS;  /* Change Direction */
                                } else if (!ourblks.getFlag(cur_x - 1, cur_z + 1)) {  /* Straight? */
                                    cur_x--;
                                } else {  /* Left turn */
                                    linelist.add(new int[]{cur_x, cur_z + 1}); /* Finish line */
                                    dir = Direction.ZPLUS;
                                    cur_x--;
                                    cur_z++;
                                }
                                break;
                            case ZMINUS: /* Segment in Z- Direction */
                                if (!ourblks.getFlag(cur_x, cur_z - 1)) { /* Right turn? */
                                    linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                    dir = Direction.XPLUS;  /* Change Direction */
                                } else if (!ourblks.getFlag(cur_x - 1, cur_z - 1)) {  /* Straight? */
                                    cur_z--;
                                } else {  /* Left turn */
                                    linelist.add(new int[]{cur_x, cur_z}); /* Finish line */
                                    dir = Direction.XMINUS;
                                    cur_x--;
                                    cur_z--;
                                }
                                break;
                        }
                    }

                    /* Build information for specific area */
                    final String polyId = factionName + "__" + world + "__" + poly_index;

                    final int sz = linelist.size();
                    final double[] x = new double[sz];
                    final double[] z = new double[sz];
                    for (int i = 0; i < sz; i++) {
                        final int[] line = linelist.get(i);
                        x[i] = (double) line[0] * (double) blocksize;
                        z[i] = (double) line[1] * (double) blocksize;
                    }

                    /* Find existing one */
                    AreaMarker areaMarker = resareas.remove(polyId); /* Existing area? */
                    if (areaMarker == null) {
                        areaMarker = set.createAreaMarker(polyId, factionName, false, world, x, z, false);
                        if (areaMarker == null) {
                            info("error adding area marker " + polyId);
                            return;
                        }
                    } else {
                        areaMarker.setCornerLocations(x, z); /* Replace corner locations */
                        areaMarker.setLabel(factionName);   /* Update label */
                    }
                    areaMarker.setDescription(desc); /* Set popup */

                    /* Set line and fill properties */
                    cusstyle.put(fact.getNationId(), new AreaStyle(markerAPI, Main.getInstance().getConfig(), "custstyle." + fact.getNationId(), defstyle, fact.getDynmapFill(), fact.getDynmapBorder()));
                    addStyle(cusstyle, defstyle, fact.getNationId(), areaMarker);

                    /* Add to map */
                    newmap.put(polyId, areaMarker);
                    poly_index++;
                }
            }
        }
    }

    /* Update Factions information */
    public void updateClaimedChunk() {
        Map<String, AreaMarker> newmap = new HashMap<>(); /* Build new map */
        Map<String, Marker> newmark = new HashMap<>(); /* Build new map */

        /* Parse into Nation centric mapping, split by world */
        Map<String, NationBlocks> blocks_by_faction = new HashMap<>();

        Collection<Nation> facts = NationManager.nations.values();
        /* Loop through factions */
        for (final Nation nation : facts) {

            List<String> chunks = nation.getChunks();
            String fid = "World_" + nation.getNationId();
            NationBlocks factblocks = blocks_by_faction.get(fid); /* Look up Nation */
            if (factblocks == null) {    /* Create Nation block if first time */
                factblocks = new NationBlocks();
                blocks_by_faction.put(fid, factblocks);
            }

            for (final String s : chunks) {
                NationChunk cc = NationChunk.fromString(s);
                final String world = cc.getWorld();

                /* Get block set for given world */
                LinkedList<NationBlock> blocks = factblocks.getBlocks().computeIfAbsent(world, k -> new LinkedList<>());

                blocks.add(new NationBlock(cc.getX(), cc.getZ())); /* Add to list */
            }

            final String factname = ChatColor.stripColor(nation.getName());

            /* Loop through each world that Nation has blocks on */
            for (Map.Entry<String, LinkedList<NationBlock>> worldblocks : factblocks.getBlocks().entrySet()) {
                handleNationOnWorld(factname, nation, worldblocks.getKey(), worldblocks.getValue(), newmap);
            }
            factblocks.clear();

            // Display warp on not
            if (displayWarps) {

                /* Now, add marker for warp location */
                for (Map.Entry<String, String> settleMents : nation.getSettlements().entrySet()) {
                    final String name = nation.getNationId() + "_" + settleMents.getKey();
                    final Settlement settlement = new Gson().fromJson(settleMents.getValue(), Settlement.class);
                    final MarkerIcon ico = settlement.isCapital() ? markerAPI.getMarkerIcon("blueflag") : markerAPI.getMarkerIcon("house");
                    if (ico != null) {
                        final SimpleLocation pos = SimpleLocation.fromString(settlement.getLocation());
                        final double lx = pos.getX();
                        final double ly = pos.getY();
                        final double lz = pos.getZ();
                        final String labelName = "[" + (settlement.isCapital() ? "Capital" : "Settlement") + "] " + factname + " - " + settlement.getName();

                        Marker marker = resmark.remove(name);
                        if (marker == null) {
                            marker = set.createMarker(name, labelName, pos.getWorld(), lx, ly, lz, ico, false);
                        } else {
                            marker.setLocation(pos.getWorld(), lx, ly, lz);
                            marker.setLabel(labelName);
                            marker.setMarkerIcon(ico);
                        }

                        if (marker != null) {
                            // Set popup
                            marker.setDescription(formatInfoWindow(infoWindow, nation));
                            newmark.put(name, marker);
                        }
                    }
                }
            }
        }
        blocks_by_faction.clear();

        /* Now, review old map - anything left is gone */
        for (final AreaMarker oldm : resareas.values()) {
            oldm.deleteMarker();
        }

        for (final Marker oldm : resmark.values()) {
            oldm.deleteMarker();
        }
        /* And replace with new map */
        resareas = newmap;
        resmark = newmark;
    }

    public void activate() {
        markerAPI = dynmapAPI.getMarkerAPI();
        if (markerAPI == null) {
            severe("Error loading dynmap marker API!");
            return;
        }
        /* Connect to factions API */
        factionAPI = Main.getInstance();

        blocksize = MAX_BLOCK_SIZE; /* Fixed at 16 */

        /* Load configuration */
        if (reload) {
            if (set != null) {
                set.deleteMarkerSet();
                set = null;
            }
        } else {
            reload = true;
        }

        final FileConfiguration cfg = Main.getInstance().getDynmapCfg().getConfiguration();
        cfg.options().copyDefaults(true);   /* Load defaults, if needed */

        /* Now, add marker set for mobs (make it transient) */
        set = markerAPI.getMarkerSet("factions.markerset");
        if (set == null) {
            set = markerAPI.createMarkerSet("factions.markerset", cfg.getString("layer.name", "Factions"), null, false);
        } else {
            set.setMarkerSetLabel(cfg.getString("layer.name", "Factions"));
        }

        if (set == null) {
            severe("Error creating marker set");
            return;
        }
        /* Make sure these are empty (on reload) */
        resareas.clear();
        resmark.clear();

        final int minZoom = cfg.getInt("layer.minzoom", 0);
        if (minZoom > 0) {
            set.setMinZoom(minZoom);
        }

        set.setLayerPriority(cfg.getInt("layer.layerprio", 10));
        set.setHideByDefault(cfg.getBoolean("layer.hidebydefault", false));
        // use3d = cfg.getBoolean("use3dregions", false);
        infoWindow = cfg.getString("infoWindow", DEF_INFO_WINDOW);
        // displayFactionName = cfg.getBoolean("show-Nation-name", true);
        displayWarps = cfg.getBoolean("display-warp", true);

        /* Get style information */
        defstyle = new AreaStyle(markerAPI, cfg, "regionstyle", "#34ebc3", "#33968b");
        cusstyle = new HashMap<>();
        for (Nation nation : NationManager.nations.values())
            cusstyle.put(nation.getNationId(), new AreaStyle(markerAPI, cfg, "custstyle." + nation.getNationId(), defstyle, nation.getDynmapFill(), nation.getDynmapBorder()));
        List<String> vis = cfg.getStringList("visibleregions");
        visible = new HashSet<>(vis);
        List<String> hid = cfg.getStringList("hiddenregions");
        hidden = new HashSet<>(hid);
        /* Chec if player sets enabled */
        playersets = cfg.getBoolean("visibility-by-Nation", false);
        if (playersets) {
            try {
                if (!dynmapAPI.testIfPlayerInfoProtected()) {
                    playersets = false;
                    info("Dynmap does not have player-info-protected enabled - visibility-by-Nation will have no effect");
                }
            } catch (NoSuchMethodError x) {
                playersets = false;
                info("Dynmap does not support function needed for 'visibility-by-Nation' - need to upgrade to 0.60 or later");
            }
        }

        updatePlayerSets(markerAPI, playersets);

        /* Set up update job - based on periond */
        int per = cfg.getInt("update.period", 300);
        if (per < 15)
            per = 15;
        updperiod = (per * TICKRATE_RATIO);
        stop = false;

        scheduleSyncDelayedTask(new NationsUpdate(this), 40);   /* First time is 2 seconds */
        Main.getInstance().getServer().getPluginManager().registerEvents(new OurServerListener(this), Main.getInstance());

        // info("version " + this.getDescription().getVersion() + " is activated");
    }
}
