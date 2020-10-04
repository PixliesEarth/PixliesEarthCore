package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
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
            player.sendMessage(Lang.DC_ALREADY_SYNCED.get(sender));
            return false;
        }
        if (instance.getUtilLists().discordcodes.containsValue(player.getUniqueId())) {
            player.sendMessage(Lang.DC_ALREADY_HAVE_CODE.get(sender).replace("%CODE%", getCode(player.getUniqueId())));
            return false;
        }
        String code = Methods.generateId(5);
        instance.getUtilLists().discordcodes.put(code, player.getUniqueId());
        player.sendMessage(Lang.DC_VERIFICATION_CODE.get(sender).replace("%CODE%", code));
        return false;
    }

    public String getCode(UUID uuid) {
        for (Map.Entry<String, UUID> entry : Main.getInstance().getUtilLists().discordcodes.entrySet())
            if (entry.getValue() == uuid)
                return entry.getKey();
        return null;
    }

}
