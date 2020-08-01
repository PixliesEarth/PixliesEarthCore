package eu.pixliesearth.core.scoreboard;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleAdapter;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreboardAdapter implements AssembleAdapter {

    private static final Main instance = Main.getInstance();
    private static int frame = 0;
    private static String[] frames(Player player) {
        String c = Main.getInstance().getProfile(player.getUniqueId()).getFavoriteColour();
        String nc = Methods.getNeighbourColor(c)+"";
        return new String[]{
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                nc+"§lE§f§lARTH",
                nc+"§lE§f§lARTH",
                c+"§lE"+nc+"§lA§f§lRTH",
                c+"§lE"+nc+"§lA§f§lRTH",
                c+"§lEA"+nc+"§lR§f§lTH",
                c+"§lEA"+nc+"§lR§f§lTH",
                c+"§lEAR"+nc+"§lT§f§lH",
                c+"§lEAR"+nc+"§lT§f§lH",
                nc+"§lE"+c+"§lART"+nc+"§lH",
                nc+"§lE"+c+"§lART"+nc+"§lH",
                "§f§lE"+nc+"§lA"+c+"§lRTH",
                "§f§lE"+nc+"§lA"+c+"§lRTH",
                "§f§lEA"+nc+"§lR"+c+"§lTH",
                "§f§lEA"+nc+"§lR"+c+"§lTH",
                "§f§lEAR"+nc+"§lT"+c+"§lH",
                "§f§lEAR"+nc+"§lT"+c+"§lH",
                "§f§lEART"+nc+"§lH",
                "§f§lEART"+nc+"§lH"
        };
    }

    @Override
    public String getTitle(Player player) {
        if (frame == frames(player).length - 1) frame = 0;
        frame++;
        return frames(player)[frame];
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> returnable = new ArrayList<>();
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        ChatColor c = ChatColor.getByChar(profile.getFavoriteColour().replace("§", ""));
        if (Main.getInstance().getUtilLists().scoreboardMaps.contains(player.getUniqueId())) {
            returnable.add("§bClaim-map");
            final int height = 3;
            final int width = 3;

            final int playerCX = player.getLocation().getChunk().getX();
            final int playerCZ = player.getLocation().getChunk().getZ();
            final World world = player.getWorld();
            for (int row = height; row >= -height; row--) {
                StringBuilder builder = new StringBuilder();
                for (int x = width; x >= -width; x--) {
                    final int chunkX = playerCX - x,
                            chunkZ = playerCZ - row;
                    NationChunk nc = NationChunk.get(world.getName(), chunkX, chunkZ);
                    if (chunkX == playerCX && chunkZ == playerCZ) {
                        builder.append("§e█");
                        continue;
                    }
                    if (profile.isInNation()) {
                        if (nc == null) {
                            builder.append("§2█");
                        } else  {
                            Nation.NationRelation rel = Nation.getRelation(profile.getNationId(), nc.getNationId());
                            builder.append("§" + rel.colChar + "█");
                        }
                    } else {
                        if (nc == null) {
                            builder.append("§2█");
                        } else  {
                            builder.append("█");
                        }
                    }
                }
                returnable.add(builder.toString());
            }
            return returnable;
        }
        final String energy = new DecimalFormat("#.##").format(profile.getEnergy()) + "§8/§e5" + "§6§l⚡";
        switch (scoreboardType.valueOf(profile.getBoardType())) {
            case STANDARD:
                if (instance.getUtilLists().boosts.size() > 0)
                    for (Boost boost : instance.getUtilLists().boosts.values())
                        returnable.add("§d§l" + boost.getName() + "§7" + boost.getTimer().getRemainingAsString());
                returnable.add(c + "§l" + Lang.PLAYER.get(player));
                returnable.add("  §8» §6" + player.getDisplayName());
                returnable.add("  §8» §2§l$§a" + profile.getBalance());
                // returnable.add("  §8» §b" + profile.getPixliecoins() + "§3⛃");
                returnable.add("  §8» §c" + profile.getElo() + "§4§l✦");
                returnable.add("  §8» §e" + energy);
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()))
                    returnable.add(c + "§lStaff§aenabled");
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "§lNation");
                    returnable.add("  §8» §b" + nation.getName());
                    returnable.add("  §8» §7Online: §a" + nation.getOnlineMembers());
                    returnable.add("  §8» §7Era: §b" + nation.getEra());
                }
                if (profile.getTimers().size() > 0)
                    for (Map.Entry<String, Map<String, Object>> entry : profile.getTimers().entrySet())
                        returnable.add(c + "§l" + entry.getKey() + "§7" + Methods.getTimeAsString(new Timer(entry.getValue()).getRemaining(), true));
                break;
            case COMPACT:
                if (instance.getUtilLists().boosts.size() > 0)
                    for (Boost boost : instance.getUtilLists().boosts.values())
                        returnable.add("§d" + boost.getName() + ": §7" + boost.getTimer().getRemainingAsString());
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()))
                    returnable.add("&3Staff: §aenabled");
                returnable.add("§2§l$§a" + profile.getBalance());
                // returnable.add("§b" + profile.getPixliecoins() + "§3§l⛃");
                returnable.add("§4§l✦§c" + profile.getElo());
                returnable.add("§e" + energy);
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "♚ §8| §b" + nation.getName());
                    returnable.add(c + "☺ §8| §a" + nation.getOnlineMembers());
                    returnable.add(c + "☗ §8| §b" + nation.getEra());
                }
                if (profile.getTimers().size() > 0) {
                    for (Map.Entry<String, Map<String, Object>> entry : profile.getTimers().entrySet()) {
                        returnable.add("§7" + entry.getKey());
                        returnable.add("§3" + Methods.getTimeAsString(new Timer(entry.getValue()).getRemaining(), true));
                    }
                }
                break;
        }
        return returnable;
    }

    public enum scoreboardType {

        STANDARD,
        COMPACT

    }

}