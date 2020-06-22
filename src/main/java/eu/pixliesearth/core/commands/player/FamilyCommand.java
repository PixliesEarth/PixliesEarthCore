package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class FamilyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (profile.isMarried()) {
            Profile partner = Main.getInstance().getProfile(UUID.fromString(profile.getMarriagePartner()));
            sender.sendMessage("§8██████████████████████████████████");
            sender.sendMessage("§7" + Methods.getCenteredMessage(Lang.YOU_ARE_MARRIED_WITH.get(player)));
            if (partner.isInNation()) {
                sender.sendMessage(Methods.getCenteredMessage("§c♥" + Bukkit.getOfflinePlayer(UUID.fromString(partner.getUniqueId())).getName() + " §8(§b" + Nation.getById(partner.getNationId()).getName() + "§8)"));
            } else {
                sender.sendMessage(Methods.getCenteredMessage("§c♥" + Bukkit.getOfflinePlayer(UUID.fromString(partner.getUniqueId())).getName()));
            }
            sender.sendMessage(Methods.getCenteredMessage("&7Other relations:"));
            StringJoiner relJoiner = new StringJoiner("§8, ");
            for (Map.Entry<String, String> entry : profile.getRelations().entrySet())
                if (!entry.getValue().startsWith("REQ="))
                    relJoiner.add("§6" + Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getName() + "§7: §b" + entry.getValue());
            player.sendMessage(relJoiner.toString());
            sender.sendMessage("§8██████████████████████████████████");
        } else {
            sender.sendMessage("§8██████████████████████████████████");
            sender.sendMessage(Methods.getCenteredMessage("&7Other relations:"));
            StringJoiner relJoiner = new StringJoiner("§8, ");
            for (Map.Entry<String, String> entry : profile.getRelations().entrySet())
                if (!entry.getValue().startsWith("REQ="))
                    relJoiner.add("§6" + Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getName() + "§7: §b" + entry.getValue());
            player.sendMessage(relJoiner.toString());
            sender.sendMessage("§8██████████████████████████████████");
        }
        return false;
    }

}
