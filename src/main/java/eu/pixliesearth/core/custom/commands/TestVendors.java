package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

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
        Vendor vendor = new Vendor(0, "§bTest Vendor", Arrays.asList(CustomItemUtil.getItemStackFromUUID("Pixlies:Zulfiqar")));
        vendor.open((Player) commandsender);
        return true;
    }

}
