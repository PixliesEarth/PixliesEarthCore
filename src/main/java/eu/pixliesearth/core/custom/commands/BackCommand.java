package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "back";
    }

    @Override
    public String getCommandDescription() {
        return "Go back to the last location";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(commandSender);
            return false;
        }
        Player player = (Player) commandSender;
        Location loc = Main.getInstance().getUtilLists().lastLocation.get(player.getUniqueId());
        if (loc == null) {
            player.sendMessage(Lang.EARTH + "§7You don't have a recorded last location.");
            return false;
        }
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.teleport(loc, "your last location");
        return true;
    }

}
