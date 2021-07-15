package eu.pixliesearth.core.custom.commands.subcommands.market;

import eu.pixliesearth.core.auctionhouse.AuctionHouseInventory;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsSubCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "items";
    }

    @Override
    public String getCommandDescription() {
        return "add items to market";
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        new AuctionHouseInventory(player).openManagementMenu();
        return true;
    }

}
