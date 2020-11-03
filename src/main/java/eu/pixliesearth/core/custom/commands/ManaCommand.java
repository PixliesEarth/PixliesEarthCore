package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ManaCommand extends CustomCommand {


    @Override
    public String getName() {
        return "mana";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.mana";
    }

    @Override
    public boolean execute(CommandSender commandsender, String alias, String[] args) {
        if (args.length != 2) {
            Lang.WRONG_USAGE.send(commandsender, "%USAGE%;/mana [PLAYER] [MANA]");
            return false;
        }
        UUID target = Bukkit.getPlayerUniqueId(args[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(commandsender);
            return false;
        }
        Profile targetProfile = Main.getInstance().getProfile(target);
        targetProfile.setEnergy(Double.parseDouble(args[1]));
        targetProfile.save();
        commandsender.sendMessage("§7Set mana.");
        return true;
    }

}
