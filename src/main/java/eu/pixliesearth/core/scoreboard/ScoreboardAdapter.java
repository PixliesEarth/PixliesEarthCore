package eu.pixliesearth.core.scoreboard;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.io.github.thatkawaiisam.assemble.AssembleAdapter;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardAdapter implements AssembleAdapter {

    private static int frame = 0;
    private static String[] frames = new String[]{
            "§f§lEARTH",
            "§f§lEARTH",
            "§2§lE§f§lARTH",
            "§a§lE§2§lA§f§lRTH",
            "§a§lEA§2§lR§f§lTH",
            "§a§lEAR§2§lT§f§lH",
            "§2§lE§a§lART§2§lH",
            "§f§lE§2§lA§a§lRTH",
            "§f§lEA§2§lR§a§lTH",
            "§f§lEAR§2§lT§a§lH",
            "§f§lEART§2§lH"
    };

    @Override
    public String getTitle(Player player) {
        if (frame == frames.length - 1) frame = 0;
        frame++;
        return frames[frame];
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> returnable = new ArrayList<>();
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        ChatColor c = ChatColor.getByChar(profile.getFavoriteColour().replace("§", ""));
        switch (scoreboardType.valueOf(profile.getBoardType())) {
            case STANDARD:
                returnable.add(c + "§lPlayer");
                returnable.add(PlaceholderAPI.setPlaceholders(player, "  §8» %vault_prefix%" + player.getDisplayName()));
                returnable.add("  §8» §fBalance");
                returnable.add("    §8╚ §2§l$§a" + profile.getBalance());
                returnable.add("  §8» §fPixlieCoins");
                returnable.add("    §8╚ §b" + profile.getPixliecoins() + "§3§l⛃");
                returnable.add("  §8» §fEnergy");
                returnable.add("    §8╚ §e" + profile.getEnergy() + "§6§l⚡");
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "§lNation");
                    returnable.add("  §8» §fName");
                    returnable.add("    §8╚ §b" + nation.getName());
                    returnable.add("  §8» §fOnline members");
                    returnable.add("    §8╚ §a" + nation.getOnlineMembers());
                }
                break;
            case COMPACT:
                returnable.add(PlaceholderAPI.setPlaceholders(player, "  §8» %vault_prefix%" + player.getDisplayName()));
                returnable.add("§2§l$§a" + profile.getBalance());
                returnable.add("§b" + profile.getPixliecoins() + "§3§l⛃");
                returnable.add("§e" + profile.getEnergy() + "§6§l⚡");
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "♜ §8| §b" + nation.getName());
                    returnable.add(c + "☺ §8| §a" + nation.getOnlineMembers());
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