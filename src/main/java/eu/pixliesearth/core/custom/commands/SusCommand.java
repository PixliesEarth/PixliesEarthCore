package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.pixliemoji.PixlieMoji;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SusCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "sus";
    }

    @Override
    public String getCommandDescription() {
        return "Declare someone as sus";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player target = Bukkit.getPlayer(parameters[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Bukkit.broadcastMessage("§6§o" + sender.getName() + " §7§othinks that §6§o" + target.getName() + " §7§ois §c§l§oSUS§r" + PixlieMoji.pixlieMojis.get(":amogus:"));
        return false;
    }
}
