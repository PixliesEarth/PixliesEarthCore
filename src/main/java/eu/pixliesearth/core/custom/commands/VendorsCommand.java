package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.vendors.VendorItem;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VendorsCommand extends CustomCommand {

    @Override
    public String getName() {
        return "vendors";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.vendor";
    }

    @Override
    public boolean execute(CommandSender commandsender, String alias, String[] args) {
        if (args.length != 2) {
            Lang.WRONG_USAGE.send(commandsender, "%USAGE%;/vendors [BUYPRICE] [SELLPRICE]");
            return false;
        }
        Double buy = args[0].equals("NONE") ? null : Double.parseDouble(args[0]);
        Double sell = args[1].equals("NONE") ? null : Double.parseDouble(args[1]);
        Main.getInstance().getVendorItems().put(CustomItemUtil.getUUIDFromItemStack(((Player)commandsender).getInventory().getItemInMainHand()), new VendorItem(InventoryUtils.serialize(((Player)commandsender).getInventory().getItemInMainHand()), buy, sell));
        commandsender.sendMessage("§7Item added.");
        return true;
    }

}
