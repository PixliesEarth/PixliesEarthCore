package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SexCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (!profile.isMarried()) {
            player.sendMessage(Lang.YOU_ARE_NOT_MARRIED.get(player));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(profile.getMarriagePartner()));
        if (!target.isOnline()) {
            player.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(player));
            return false;
        }
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%PLAYER1%", player.getName());
        placeholders.put("%PLAYER2%", target.getName());
        Lang.PLAYERS_HAD_SEX.broadcast(placeholders);
        return false;
    }

}
