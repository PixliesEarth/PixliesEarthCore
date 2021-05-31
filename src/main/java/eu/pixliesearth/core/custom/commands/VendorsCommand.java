package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.vendors.VendorItem;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VendorsCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "vendors";
    }
    
    @Override
    public String getCommandDescription() {
    	return "idk what to put here";
    }
    
    @Override
    public String getCommandUsage() {
    	return "/vendors <buyprice> <sellprice>";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.vendor";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return true;
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
    	if (parameters.length != 2) {
            Lang.WRONG_USAGE.send(commandSender, "%USAGE%;/vendors [BUYPRICE] [SELLPRICE]");
            return false;
        }
        Double buy = parameters[0].equals("NONE") ? null : Double.parseDouble(parameters[0]);
        Double sell = parameters[1].equals("NONE") ? null : Double.parseDouble(parameters[1]);
        Main.getInstance().getVendorItems().put(CustomItemUtil.getUUIDFromItemStack(((Player)commandSender).getInventory().getItemInMainHand()), new VendorItem(InventoryUtils.serialize(((Player)commandSender).getInventory().getItemInMainHand()), buy, sell));
        commandSender.sendMessage("§7Item added.");
        return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new TabableStrings("BUYPRICE", "NONE", "spawn"), new TabableStrings("SELLPRICE", "NONE")};
    }

}
