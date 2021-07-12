package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class K98K extends CustomGun {

    public static String getDefaultDisplayName() {
    	return "§c§lKarabiner98k";
    }

	public static Integer getCustomModelData() {
		return 6;
	}

	public static String getUUID() {
		return "Gun:Karabiner98k";
	}

	public static int getMaxAmmo() {
		return 5;
	}

	public static int getRange() {
		return 100;
	}

	public static double getAccuracy() {
		return 0.04;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	public static String getOrigin() {
		return "Germany";
	}

	public static long getDelayPerShot() {
		return 550L;
	}
	
}
