package eu.pixliesearth.core.custom.commands.subcommands.market;

import eu.pixliesearth.core.auctionhouse.AuctionHouseInventory;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SearchSubCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "search";
    }

    @Override
    public String getCommandUsage() {
        return "search items in market.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabableString("§cQuery")};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        new AuctionHouseInventory(player).openSearch(parameters[0]);
        return true;
    }

}
