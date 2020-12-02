package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;
import java.util.UUID;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            Lang.WRONG_USAGE.send(sender, "%USAGE%;/stats <player>");
            return false;
        }
        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);
        if (uuid == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile profile = Main.getInstance().getProfile(uuid);
        final String energy = new DecimalFormat("#.##").format(profile.getEnergy()) + "§8/§e10" + "§6§l⚡";
        sender.sendMessage(PlaceholderAPI.setPlaceholders(profile.getAsOfflinePlayer(), "%vault_prefix%" + profile.getDisplayName()));
        sender.sendMessage("  §8» §2§l$§a" + profile.getBalance());
        sender.sendMessage("  §8» §c" + profile.getElo() + "§4§l✦");
        sender.sendMessage("  §8» §e" + energy);
        return false;
    }

}
