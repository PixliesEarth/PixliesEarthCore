package eu.pixliesearth.core.custom.commands.subcommands.economy;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.economy.BalTopThread;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Map;
import java.util.UUID;

public class EconomyTopCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "top";
    }

    @Override
    public String getCommandUsage() {
        return "Baltop";
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        sender.sendMessage("§7-----------------------------------------");
        sender.sendMessage("§a§lBALANCE TOP §f§l- TOP §a§l10");
        PrettyTime p = new PrettyTime();
        sender.sendMessage("§7§oLast updated " + p.format(BalTopThread.date));
        sender.sendMessage(" ");
        int i = 1;
        for (Map.Entry<UUID, Double> entry : BalTopThread.balTopMap.entrySet()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getKey());
            ChatColor numberColor;
            switch (i) {
                case 1:
                    numberColor = ChatColor.GOLD;
                    break;
                case 2:
                    numberColor = ChatColor.GRAY;
                    break;
                case 3:
                    numberColor = ChatColor.RED;
                    break;
                default:
                    numberColor = ChatColor.WHITE;
                    break;
            }
            sender.sendMessage(numberColor + "§l" + i + "§f. §6" + offlinePlayer.getName() + " §7- §2§l$§a" + Methods.round(entry.getValue(), 2));
            i++;
        }
        sender.sendMessage("§7-----------------------------------------");
        return false;
    }

}
