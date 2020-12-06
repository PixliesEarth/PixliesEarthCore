package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.localization.Lang;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ReplyCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "reply";
    }

    @Override
    public String getCommandDescription() {
        return "Reply to last commands";
    }

    @Override
    public Set<String> getCommandAliases() {
        Set<String> returner = new HashSet<>();
        returner.add("r");
        return returner;
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return true;
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] args, boolean ranByPlayer) {
        Player player = (Player) sender;
        if (!instance.getUtilLists().lastMessageSender.containsKey(player.getUniqueId())) {
            sender.sendMessage(Lang.EARTH + "§7You don't have anyone to reply to :(");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage("§5PM §8| §7Wrong syntax! §e/r <message>");
            return false;
        }
        if (!sender.hasPermission("earth.chat.bypassblacklist")) {
            for (String arg : args)
                for (String s1 : instance.getConfig().getStringList("modules.chatsystem.blacklist"))
                    if (arg.equalsIgnoreCase(s1)) {
                        sender.sendMessage("§aEARTH §8| §7You are not allowed to say §b" + s1 + " §7here.");
                        return false;
                    }
        }

        Player receiver = Bukkit.getPlayer(instance.getUtilLists().lastMessageSender.get(player.getUniqueId()));
        if (receiver == null) {
            sender.sendMessage("§5PM §8| §7This player is not online!");
            return false;
        }
        if (instance.getProfile(receiver.getUniqueId()).getBlocked().contains((player.getUniqueId().toString()))) {
            Lang.PLAYER_BLOCKED_YOU.send(sender);
            return false;
        }
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i != args.length; i++)
            messageBuilder.append(args[i]).append(" ");

        String receiverFormat;
        receiverFormat = PlaceholderAPI.setPlaceholders(player, instance.getConfig().getString("modules.privatemessage.format-receiver-player")).replace("%message%", messageBuilder.toString());
        String senderFormat = PlaceholderAPI.setPlaceholders(receiver, instance.getConfig().getString("modules.privatemessage.format-sender")).replace("%message%", messageBuilder.toString());
        receiver.sendMessage(receiverFormat);
        sender.sendMessage(senderFormat);
        String senderName = player.getName();
        for (UUID uuid : instance.getUtilLists().staffMode)
            if (Bukkit.getPlayer(uuid) != null)
                Bukkit.getPlayer(uuid).sendMessage("§5PM §8| §6" + senderName + " §8> §b" + receiver.getName() + " §8| §7" + messageBuilder.toString());
        if (sender instanceof Player)
            instance.getUtilLists().lastMessageSender.put(receiver.getUniqueId(), ((Player)sender).getUniqueId());
        System.out.println("§5PM §8| §6" + senderName + " §8> §b" + receiver.getName() + " §8| §7" + messageBuilder.toString());
        return true;
    }

}
