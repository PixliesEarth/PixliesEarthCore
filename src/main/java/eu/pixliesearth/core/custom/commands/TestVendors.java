package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TestVendors extends CustomCommand {

    @Override
    public String getName() {
        return "testvendors";
    }

    @Override
    public String getDescription() {
        return "Tests vendors";
    }

    @Override
    public boolean execute(CommandSender commandsender, String alias, String[] args) {
        Map<ItemStack, Double> sell = new HashMap<>();
        sell.put(CustomItemUtil.getItemStackFromUUID("Pixlies:Zulfiqar"), 69420D);
        sell.put(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Block"), 400D);
        Map<ItemStack, Double> buy = new HashMap<>();
        buy.put(CustomItemUtil.getItemStackFromUUID("Pixlies:Zulfiqar"), 42069D);
        buy.put(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Block"), 4D);
        Vendor vendor = new Vendor("NONE", "§bTest Vendor", buy, sell);
        vendor.open((Player) commandsender);
        return true;
    }

}
