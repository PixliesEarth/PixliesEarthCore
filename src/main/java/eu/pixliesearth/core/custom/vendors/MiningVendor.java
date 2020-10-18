package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;

import java.util.Arrays;


public class MiningVendor extends Vendor {

    public MiningVendor() {
        super(0, "§8§lMiner Juan", Arrays.asList(CustomItemUtil.getItemStackFromUUID("Minecraft:stone"),
                CustomItemUtil.getItemStackFromUUID("Minecraft:cobblestone"),
                CustomItemUtil.getItemStackFromUUID("Pixlies:Explosive_Pickaxe")));
    }

}
