package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import org.bukkit.Material;

import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class M16 extends CustomGun {

    @Override
    public Material getMaterial() {
    	return Material.WOODEN_AXE;
    }

    @Override
    public String getDefaultDisplayName() {
    	return "§c§lM-16";
    }

    @Override
	public Integer getCustomModelData() {
		return 6;
	}

	@Override
	public String getUUID() {
		return "Gun:M-16";
	}

	@Override
	public int getMaxAmmo() {
		return 30;
	}

	@Override
	public int getRange() {
		return 60;
	}

	@Override
	public double getAccuracy() {
		return 0.1;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	@Override
	public String getOrigin() {
		return "USA";
	}
	
	@Override
	public long getDelayPerShot() {
		return 150L;
	}
	

}
