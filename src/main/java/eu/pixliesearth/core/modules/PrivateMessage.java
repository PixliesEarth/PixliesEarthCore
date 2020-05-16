package eu.pixliesearth.core.modules;

import eu.pixliesearth.core.interfaces.Module;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateMessage implements CommandExecutor, Module {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!enabled()) {
            sender.sendMessage("§5PM §8| §7The chatsystem is §cdisabled §7at the moment.");
            return false;
        }
        if (args[0] == null) {
            sender.sendMessage("§5PM §8| §7You need to state the player you want to send a message to.");
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage("§5PM §8| §7Wrong syntax! §e/msg <player> <message>");
            return false;
        }
        if (!sender.hasPermission("earth.pm.bypassblacklist")) {
            for (String arg : args)
                for (String s1 : config.getStringList("modules.privatemessage.blacklist"))
                    if (arg.equalsIgnoreCase(s1)) {
                        sender.sendMessage("§aEARTH §8| §7You are not allowed to say §b" + s1 + " §7here.");
                        return false;
                    }
        }

        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null) {
            sender.sendMessage("§5PM §8| §7This player is not online!");
            return false;
        }
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i != args.length; i++)
            messageBuilder.append(args[i]).append(" ");

        String receiverformat = "";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            receiverformat = PlaceholderAPI.setPlaceholders(player, config.getString("modules.privatemessage.format-receiver-player")).replace("%message%", messageBuilder.toString());
        } else {
            receiverformat = config.getString("modules.privatemessage.format-receiver-console").replace("%message%", messageBuilder.toString());
        }
        String senderformat = PlaceholderAPI.setPlaceholders(receiver, config.getString("modules.privatemessage.format-sender")).replace("%message%", messageBuilder.toString());
        receiver.sendMessage(receiverformat);
        sender.sendMessage(senderformat);
        String senderName = "";
        if (sender instanceof Player) {
            senderName = sender.getName();
        } else {
            senderName = "§cCONSOLE";
        }
        System.out.println("§5PM §8| §6" + senderName + " §8> §b" + receiver.getName() + " §8| §7" + messageBuilder.toString());
        return false;
    }

    @Override
    public String name() {
        return "privatemessage";
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("modules.privatemessage.enabled");
    }

}
