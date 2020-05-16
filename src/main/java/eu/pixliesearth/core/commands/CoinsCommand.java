package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (args.length) {

            case 0:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§aEARTH §8| §7Only executable by a player.");
                    return false;
                }
                Player player = (Player) sender;
                Profile user = Main.getInstance().getProfile(player.getUniqueId());
                sender.sendMessage("§6PIXLIECOINS §8| §7You have §6" + user.getPixliecoins() + "✪ §7on your account.");
                break;
            case 2:
                boolean allowed = false;
                if (!(sender instanceof Player)) allowed = true;
                if (sender.hasPermission("earth.coins.admin")) allowed = true;
                if (!allowed) {
                    sender.sendMessage("§6PIXLIECOINS §8| §cInsufficient permissions.");
                    return false;
                }
                if (args[0].equalsIgnoreCase("check")) {
                    //TODO

                }

                break;

        }
        return false;
    }

}
