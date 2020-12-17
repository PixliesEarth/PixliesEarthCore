package eu.pixliesearth.guns.guns;

import org.bukkit.Material;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class Slingshot extends CustomGun {
    
    @Override
    public Material getMaterial() {
    	return Material.GOLDEN_HOE;
    }
    
    @Override
	public String getDefaultDisplayName() {
		return "§c§lSlingshot";
	}

	@Override
	public Integer getCustomModelData() {
		return 55;
	}

	@Override
	public String getUUID() {
		return "Gun:Slingshot";
	}

	@Override
	public int getMaxAmmo() {
		return 1;
	}

	@Override
	public int getRange() {
		return 20;
	}

	@Override
	public double getAccuracy() {
		return 0.04;
	}

	@Override
	public String getOrigin() {
		return "Unknown";
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.COBBLESTONE;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}

}
