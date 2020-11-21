package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomBlock;

public class BlockAntiMissileHead extends CustomBlock {
	
	public BlockAntiMissileHead() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.OBSIDIAN;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6AntiMissile_Head";
    }

    @Override
    public String getUUID() {
        return "Pixlies:AntiMissile_Head"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
