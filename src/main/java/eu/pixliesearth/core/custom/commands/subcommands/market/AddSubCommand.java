package eu.pixliesearth.core.custom.commands.subcommands.market;

import eu.pixliesearth.core.auctionhouse.AuctionHouseInventory;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddSubCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public String getCommandUsage() {
        return "Add item to market.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabableString("§cAmount")};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            sender.sendMessage(Lang.EARTH + "§7Nobody is going to buy air from you.");
            return false;
        }
        try {
            double price = Double.parseDouble(parameters[0]);
            final ItemStack item = player.getInventory().getItemInMainHand();
            player.getInventory().setItemInMainHand(null);
            AuctionHouseInventory.addItemToDatabase(item, price, player);
            player.sendMessage(Lang.EARTH + "§7You §asuccessfully §7added an item to the market.");
        } catch (Exception e) {
            sender.sendMessage(Lang.EARTH + "§7Invalid price.");
        }
        return true;
    }

}
