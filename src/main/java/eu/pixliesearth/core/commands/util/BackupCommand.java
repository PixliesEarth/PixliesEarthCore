package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackupCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("earth.admin")) {
            sender.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }
        switch (args.length) {
            case 0:
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), this::backupProfiles);
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    sender.sendMessage("§7Backing up all nations in the database...");
                    for (Nation nation : NationManager.nations.values())
                        nation.backup();
                    sender.sendMessage("§aDone.");
                });
                break;
            case 1:
                if (args[0].equalsIgnoreCase("nations")) {
                    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                        sender.sendMessage("§7Backing up all nations in the database...");
                        for (Nation nation : NationManager.nations.values())
                            nation.backup();
                        sender.sendMessage("§aDone.");
                    });
                } else if (args[0].equalsIgnoreCase("profiles")) {
                    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), this::backupProfiles);
                }
                break;
        }
        return false;
    }

    private void backupProfiles() {
        Bukkit.getConsoleSender().sendMessage("§7Backing up all profiles in the database.");
        for (Player player : instance.getServer().getOnlinePlayers()) {
            Profile profile = instance.getProfile(player.getUniqueId());
            profile.syncDiscordAndIngameRoles();
            profile.backup();
        }
        Bukkit.getConsoleSender().sendMessage("§aDone.");
    }

}
