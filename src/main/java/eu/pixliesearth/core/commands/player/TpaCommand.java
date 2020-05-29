package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (profile.getTimers().containsKey("Teleport")) {
            player.sendMessage(Lang.ALREADY_HAVE_A_REQ.get(player));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(Lang.WRONG_USAGE.get(player).replace("%USAGE%", "/tpa <player>/deny"));
            return false;
        }
        if (args[0].equalsIgnoreCase("deny")) {
            if (instance.getPlayerLists().tpaRequests.containsKey(player.getUniqueId())) {
                player.sendMessage(Lang.TPA_REQUEST_DENIED.get(player).replace("%REQUESTER%", Bukkit.getPlayer(instance.getPlayerLists().tpaRequests.get(player.getUniqueId())).getName()));
                instance.getPlayerLists().tpaRequests.remove(player.getUniqueId());
            } else {
                player.sendMessage(Lang.NO_OPEN_TPA_REQUEST.get(player));
            }
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(player));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        instance.getPlayerLists().tpaRequests.put(target.getUniqueId(), player.getUniqueId());

        return false;
    }

}
