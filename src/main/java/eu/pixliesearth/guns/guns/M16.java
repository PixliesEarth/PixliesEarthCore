package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import org.bukkit.Material;

import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class M16 extends CustomGun {

    public static Material getMaterial() {
    	return Material.WOODEN_AXE;
    }

    public static String getDefaultDisplayName() {
    	return "§c§lM-16";
    }

	public static Integer getCustomModelData() {
		return 6;
	}

	public static String getUUID() {
		return "Gun:M-16";
	}

	public static int getMaxAmmo() {
		return 30;
	}

	public static int getRange() {
		return 60;
	}

	public static double getAccuracy() {
		return 0.1;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	public static String getOrigin() {
		return "USA";
	}

	public static long getDelayPerShot() {
		return 150L;
	}
	

}
