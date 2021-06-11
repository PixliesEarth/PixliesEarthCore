package eu.pixliesearth.core.custom.commands.subcommands.vendors;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.vendors.VendorItem;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddItemCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "additem";
    }

    @Override
    public String getCommandUsage() {
        return "Add item price";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Double buy = parameters[0].equals("NONE") ? null : Double.parseDouble(parameters[0]);
        Double sell = parameters[1].equals("NONE") ? null : Double.parseDouble(parameters[1]);
        Main.getInstance().getVendorItems().put(CustomItemUtil.getUUIDFromItemStack(((Player)commandSender).getInventory().getItemInMainHand()), new VendorItem(InventoryUtils.serialize(((Player)commandSender).getInventory().getItemInMainHand()), buy, sell));
        commandSender.sendMessage("§7Item added.");
        return false;
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[] {new CustomCommand.TabableStrings("BUYPRICE", "NONE", "spawn"), new CustomCommand.TabableStrings("SELLPRICE", "NONE")};
    }

}
