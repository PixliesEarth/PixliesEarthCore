package eu.pixliesearth.core.scoreboard;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.io.github.thatkawaiisam.assemble.AssembleAdapter;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return "§b§lPixlies§fEarth§b§l!";
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> returnable = new ArrayList<>();
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        ChatColor c = ChatColor.getByChar(profile.getFavoriteColour().replace("§", ""));
        switch (scoreboardType.valueOf(profile.getBoardType())) {
            case STANDARD:
                returnable.add(c + "§lPlayer");
                returnable.add("  §8» §2§l$§a" + profile.getBalance());
                returnable.add("  §8» §b" + profile.getPixliecoins() + "§3§l⛃");
                returnable.add("  §8» §e" + profile.getEnergy() + "§6§l⚡");
                if (profile.isInNation()) {
                    Nation nation = Nation.getById(profile.getNationId());
                    returnable.add(c + "§8Nation");
                    returnable.add("  §8» §b" + nation.getName());
                    returnable.add("  §8» §7Online: §a" + nation.getOnlineMembers());
                }
                break;
            case COMPACT:
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