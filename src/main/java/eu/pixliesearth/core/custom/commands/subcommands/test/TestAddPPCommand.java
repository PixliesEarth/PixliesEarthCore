package eu.pixliesearth.core.custom.commands.subcommands.test;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestAddPPCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "givepp";
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[] {new CustomCommand.TabableString("AMOUNT"), new CustomCommand.TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (commandSender.isOp()) return false;
        Player player = Bukkit.getPlayer(parameters[1]);
        if (player == null) return false;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) return false;
        double amount = Double.parseDouble(parameters[0]);
        Nation nation = profile.getCurrentNation();
        nation.setXpPoints(nation.getXpPoints() + amount);
        nation.save();
        commandSender.sendMessage("Gave " + player.getName() + " " + amount + "PoliticalPower.");
        return true;
    }

}
