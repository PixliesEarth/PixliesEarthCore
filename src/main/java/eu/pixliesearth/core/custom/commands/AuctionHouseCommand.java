package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.auctionhouse.AuctionHouseInventory;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.market.AddSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.market.SearchSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class AuctionHouseCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "market";
    }

    @Override
    public Set<String> getCommandAliases() {
        return Set.of("auctionhouse", "ah");
    }

    @Override
    public String getCommandDescription() {
        return "Auction House";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomSubCommand.TabableSubCommand(new AddSubCommand(), new SearchSubCommand())};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] args, boolean ranByPlayer) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        new AuctionHouseInventory(player).open();
        return true;
    }

}
