package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.interfaces.Module;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ModulesCommand implements CommandExecutor {

    private static Main instance = Main.getInstance();
    private FileConfiguration config = instance.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean allowed = false;
        if (!(sender instanceof Player)) allowed = true;
        if (sender.hasPermission("earth.admin")) allowed = true;
        if (!allowed) {
            sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("§aEARTH §8| §7Wrong syntax. §e/modules §b<name> §eenable/disable");
            return false;
        }
        Module module = Module.getByName(args[0]);
        if (module == null) {
            sender.sendMessage("§aEARTH §8| §7This module §cdoes not §7exist.");
            return false;
        }
        if (args[1].equalsIgnoreCase("disable")) {
            if (!module.isEnabled()) {
                sender.sendMessage("§aEARTH §8| §b" + module.name() + " §7is already disabled.");
                return false;
            }
            config.set("modules.chatsystem.enabled", false);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§aEARTH §8| §aSuccessfully §7enabled §b" + module.name() + "§7!");
        } else if (args[1].equalsIgnoreCase("enable")) {
            if (module.isEnabled()) {
                sender.sendMessage("§aEARTH §8| §b" + module.name() + " §7is already enabled.");
                return false;
            }
            config.set("modules.chatsystem.enabled", true);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§aEARTH §8| §aSuccessfully §7enabled §b" + module.name() + "§7!");
        } else {
            sender.sendMessage("§aEARTH §8| §7Wrong syntax. §e/modules §b<name> §eenable/disable");
        }

        return false;
    }

}
