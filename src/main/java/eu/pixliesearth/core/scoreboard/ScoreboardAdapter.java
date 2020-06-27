package eu.pixliesearth.core.scoreboard;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.lib.io.github.thatkawaiisam.assemble.AssembleAdapter;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreboardAdapter implements AssembleAdapter {

    private static int frame = 0;
    private static String[] frames(Player player) {
        String c = Main.getInstance().getProfile(player.getUniqueId()).getFavoriteColour();
        String nc = Methods.getNeighbourColor(c)+"";
        String[] f =new String[]{
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
        return f;
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
        final String energy = new DecimalFormat("#.##").format(profile.getEnergy()) + "§8/§e5" + "§6§l⚡";
        switch (scoreboardType.valueOf(profile.getBoardType())) {
            case STANDARD:
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId())) {
                    returnable.add(c + "§lStaff");
                    returnable.add("  §8» §aenabled");
                }
                returnable.add(c + "§l" + Lang.PLAYER.get(player));
                returnable.add(PlaceholderAPI.setPlaceholders(player, "  §8» %vault_prefix%" + player.getDisplayName()));
                returnable.add("  §8» §2§l$§a" + profile.getBalance());
                returnable.add("  §8» §b" + profile.getPixliecoins() + "§3⛃");
                returnable.add("  §8» §e" + energy);
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "§lNation");
                    returnable.add("  §8» §b" + nation.getName());
                    returnable.add("  §8» §7Online: §a" + nation.getOnlineMembers());
                    returnable.add("  §8» §7Era: §b" + nation.getEra());
                }
                if (profile.getTimers().size() > 0) {
                    for (Map.Entry<String, Timer> entry : profile.getTimers().entrySet()) {
                        returnable.add(c + "§l" + entry.getKey());
                        returnable.add("  §8» §7" + Methods.getTimeAsString(entry.getValue().getRemaining(), true));
                    }
                }
                break;
            case COMPACT:
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()))
                    returnable.add("&3Staff: §aenabled");
                returnable.add("§2§l$§a" + profile.getBalance());
                returnable.add("§b" + profile.getPixliecoins() + "§3§l⛃");
                returnable.add("§e" + energy);
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "♚ §8| §b" + nation.getName());
                    returnable.add(c + "☺ §8| §a" + nation.getOnlineMembers());
                    returnable.add(c + "☗ §8| §b" + nation.getEra());
                }
                if (profile.getTimers().size() > 0) {
                    for (Map.Entry<String, Timer> entry : profile.getTimers().entrySet()) {
                        returnable.add("§7" + entry.getKey());
                        returnable.add("§3" + Methods.getTimeAsString(entry.getValue().getRemaining(), true));
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