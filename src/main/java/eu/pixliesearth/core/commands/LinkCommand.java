package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class LinkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§9DISCORD §8| §7This command can only be executed by a player.");
            return false;
        }
        Main instance = Main.getInstance();
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.getDiscord().equals("NONE")) {
            player.sendMessage("§9DISCORD §8| §7Your in-game and discord accounts are already synced.");
            return false;
        }
        if (instance.getPlayerLists().discordcodes.containsValue(player.getUniqueId())) {
            player.sendMessage("§9DISCORD §8| §7You already have a code: §b" + getCode(player.getUniqueId()));
            return false;
        }
        String code = Methods.generateId(5);
        instance.getPlayerLists().discordcodes.put(code, player.getUniqueId());
        player.sendMessage("§9DISCORD §8| §7Your verification code is §b" + code + "§7. Type §e/link §b" + code + " §7in our discord bot channel to complete the verification process.");
        return false;
    }

    public String getCode(UUID uuid) {
        for (Map.Entry<String, UUID> entry : Main.getInstance().getPlayerLists().discordcodes.entrySet())
            if (entry.getValue() == uuid)
                return entry.getKey();
        return null;
    }

}
