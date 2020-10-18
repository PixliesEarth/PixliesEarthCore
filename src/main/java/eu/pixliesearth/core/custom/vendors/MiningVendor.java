package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;


public class MiningVendor extends Vendor {

    public MiningVendor() {
        super("§6Miner Julio", "§6Miner Julio", CustomItemUtil.getItemStackFromUUID("Minecraft:stone"),
                CustomItemUtil.getItemStackFromUUID("Minecraft:cobblestone"),
                CustomItemUtil.getItemStackFromUUID("Pixlies:Explosive_Pickaxe"));
    }

}
