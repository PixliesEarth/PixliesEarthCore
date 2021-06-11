package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ManaCommand extends CustomCommand {


    @Override
    public String getCommandName() {
        return "mana";
    }
    
    @Override
    public String getCommandDescription() {
    	return "Gives the inputted player the mana provided";
    }
    
    @Override
    public String getPermission() {
        return "net.pixlies.mana";
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
    	if (parameters.length != 2) {
            Lang.WRONG_USAGE.send(commandSender, "%USAGE%;/mana [PLAYER] [MANA]");
            return false;
        }
        UUID target = Bukkit.getPlayerUniqueId(parameters[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(commandSender);
            return false;
        }
        Profile targetProfile = Main.getInstance().getProfile(target);
        targetProfile.setEnergy(Double.parseDouble(parameters[1]));
        targetProfile.save();
        commandSender.sendMessage("§7Set mana.");
        return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new TabablePlayer(), new TabableString("Amount")};
    }
    
}
