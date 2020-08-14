package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        if (!instance.getUtilLists().tpaRequests.containsKey(player.getUniqueId())) {
            player.sendMessage(Lang.NO_OPEN_TPA_REQUEST.get(player));
            return false;
        }
        Player requester = Bukkit.getPlayer(instance.getUtilLists().tpaRequests.get(player.getUniqueId()));
        if (requester == null) {
            player.sendMessage(Lang.TPA_REQUESTER_WENT_OFF.get(player));
            instance.getUtilLists().tpaRequests.remove(player.getUniqueId());
            return false;
        }
        player.sendMessage(Lang.TPA_REQUEST_ACCEPTED.get(player).replace("%REQUESTER%", requester.getName()));
        Profile rqProf = instance.getProfile(requester.getUniqueId());
        if (Energy.calculateNeeded(player.getLocation(), requester.getLocation()) > rqProf.getEnergy()) {
            requester.sendMessage(Lang.NOT_ENOUGH_ENERGY.get(requester));
            return false;
        }
        instance.getUtilLists().tpaRequests.remove(player.getUniqueId());
        rqProf.teleport(player.getLocation(), player.getDisplayName());
        return false;
    }

}
