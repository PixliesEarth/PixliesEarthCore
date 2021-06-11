package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CringeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String name = null;

        if(commandSender instanceof Player && !(Main.getInstance().getUtilLists().cringers.contains(((Player) commandSender).getUniqueId()))){
            name = commandSender.getName();
            Main.getInstance().getUtilLists().cringers.add(((Player) commandSender).getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Main.getInstance().getUtilLists().cringers.remove(((Player) commandSender).getUniqueId());
                }
            }, 20*60);
        }else if(!(commandSender instanceof Player)){
            name = "Console";
        }
        if (name == null) {
            commandSender.sendMessage(Lang.EARTH + "§7You are on a cringe cooldown.");
            return false;
        }
        Bukkit.broadcastMessage("§6" + name + " §7just cringed!");
        return false;
    }
}
