package eu.pixliesearth.core.scoreboard;

import com.vdurmont.emoji.EmojiParser;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleAdapter;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.warsystem.War;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ScoreboardAdapter implements AssembleAdapter {

    private static final Main instance = Main.getInstance();
    private static String[] frames(Player player) {
        String c = Main.getInstance().getProfile(player.getUniqueId()).getFavoriteColour();
        String nc = Methods.getNeighbourColor(c)+"";
        return new String[]{
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                "§f§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",
                c + "§lEARTH",

                c + "§lEART" + nc + "§lH",
                c + "§lEART" + nc + "§lH",
                c + "§lEAR" + nc + "§lT§f§lH",
                c + "§lEAR" + nc + "§lT§f§lH",
                c + "§lEA" + nc + "§lR§f§lTH",
                c + "§lEA" + nc + "§lR§f§lTH",
                c + "§lE" + nc + "§lA§f§lRTH",
                c + "§lE" + nc + "§lA§f§lRTH",
                nc + "§lE§f§lARTH",
                nc + "§lE§f§lARTH",
                "§f§lEARTH",
                "§f§lEARTH",

                nc+"§lE§f§lARTH",
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
        instance.getUtilLists().scoreboardFrames.putIfAbsent(player.getUniqueId(), 0);
        int frame = instance.getUtilLists().scoreboardFrames.get(player.getUniqueId());
        if (frame == frames(player).length - 1) instance.getUtilLists().scoreboardFrames.put(player.getUniqueId(), 0);
        instance.getUtilLists().scoreboardFrames.put(player.getUniqueId(), instance.getUtilLists().scoreboardFrames.get(player.getUniqueId()) + 1);
        return frames(player)[frame];
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> returnable = new ArrayList<>();
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        ChatColor c = ChatColor.getByChar(profile.getFavoriteColour().replace("§", ""));
        final World world = player.getWorld();
        if (profile.isInWar()) {
            War war = instance.getCurrentWar();
            returnable.add("§c§lWAR §7players left");
            returnable.add("§a§lYour side");
            returnable.add("§a§l| " + war.getLeft().get(war.getPlayers().get(player.getUniqueId()).getSide()));
            returnable.add("§c§lOpponent");
            returnable.add("§c§l| " + war.getLeft().get(war.getPlayers().get(player.getUniqueId()).getSide().getOpposite()));
            return returnable;
        }
        if (Main.getInstance().getUtilLists().scoreboardMaps.contains(player.getUniqueId())) {
            returnable.add(c + "Claim-map");
            final int height = 3;
            final int width = 3;

            final int playerCX = player.getLocation().getChunk().getX();
            final int playerCZ = player.getLocation().getChunk().getZ();
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
                            builder.append("§").append(rel.colChar).append("█");
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
        final String energy = new DecimalFormat("#.##").format(profile.getEnergy()) + "§8/§e10⚡";
        switch (scoreboardType.valueOf(profile.getBoardType())) {
            case STANDARD:
                if (instance.getUtilLists().boosts.size() > 0)
                    for (Boost boost : instance.getUtilLists().boosts.values())
                        returnable.add("§d§l" + boost.getName() + "§7" + boost.getTimer().getRemainingAsString());

                String timeIcon = instance.getCalendar().day() ? "☀" : "§3§l☾";

                returnable.add(instance.getCalendar().getSeason().getIcon() + " §7" + instance.getCalendar().formatDate() + " " + Methods.formatClock(world.getTime() + 6000L) + "§r " + timeIcon);

                returnable.add(" ");

                // returnable.add(c + "§l" + Lang.PLAYER.get(player));
                // returnable.add(c + "§l| " + c + player.getDisplayName());
                returnable.add(c + "§l| " + profile.getBalanceFormatted());
                returnable.add(c + "§l| §e" + energy);
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId())) {
                    returnable.add(c + "§lStaff §aenabled");
                    returnable.add(c + "§lTPS §a" + Methods.round(Bukkit.getTPS()[0], 2));
                }
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "§lNation");
                    returnable.add(c + "§l| " + c + nation.getName());
                    returnable.add(c + "§l| §7PP: " + c + nation.getXpPoints());
                    returnable.add(c + "§l| §7Era: " + c + nation.getEra());
                }
                returnable.add(" ");
                returnable.add(c + "§l| §7" + profile.getPlayTimeFormatted());
                if (profile.getTimers().size() > 0)
                    for (Map.Entry<String, Map<String, String>> entry : profile.getTimers().entrySet())
                        returnable.add(c + "§l" + entry.getKey() + " §7" + Methods.getTimeAsString(new Timer(entry.getValue()).getRemaining(), true));
                break;
            case COMPACT:
                if (instance.getUtilLists().boosts.size() > 0)
                    for (Boost boost : instance.getUtilLists().boosts.values())
                        returnable.add("§d" + boost.getName() + ": §7" + boost.getTimer().getRemainingAsString());
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()))
                    returnable.add("&3Staff: §aenabled");
                returnable.add("§b" + profile.getBalance() + "§3⛃");
                returnable.add("§e" + energy);
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "♚ §8| §b" + nation.getName());
                    returnable.add(c + "P §8| §b" + nation.getXpPoints());
                    returnable.add(c + "☗ §8| §b" + nation.getEra());
                }
                if (profile.getTimers().size() > 0) {
                    for (Map.Entry<String, Map<String, String>> entry : profile.getTimers().entrySet()) {
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