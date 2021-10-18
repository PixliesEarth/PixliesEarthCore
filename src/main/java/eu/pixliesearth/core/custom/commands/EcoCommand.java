package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.economy.*;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class EcoCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "eco";
    }

    @Override
    public Set<String> getCommandAliases() {
        return new HashSet<>(){{
            add("economy");
            add("money");
            add("dollars");
        }};
    }

    @Override
    public String getCommandDescription() {
        return "Eco command";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomSubCommand.TabableSubCommand(new EconomySetCommand(), new EconomyTakeCommand(), new EconomyBalanceCommand(), new EconomyTopCommand(), new EconomyAddCommand())};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (ranByPlayer) {
            Player player = (Player) sender;
            Profile profile = instance.getProfile(player.getUniqueId());
            sender.sendMessage(Lang.BALANCE_YOU.get(player).replace("%BALANCE%", profile.getBalanceFormatted()));
        } else {
            sender.sendMessage("§eEconomy Commands");
            sender.sendMessage("§7* §6/eco set <player> <amount>");
            sender.sendMessage("§7* §6/eco give <player> <amount>");
            sender.sendMessage("§7* §6/eco take <player> <amount>");
            sender.sendMessage("§7* §6/eco balance <player>");
            sender.sendMessage("§7* §6/eco top");
        }
        return true;
    }

}
