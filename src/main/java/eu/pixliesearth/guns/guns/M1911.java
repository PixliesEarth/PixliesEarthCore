package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class M1911 extends CustomGun {

	public static String getDefaultDisplayName() {
		return "§c§lM1911";
	}

	public static Integer getCustomModelData() {
		return 8;
	}

	public static String getUUID() {
		return "Gun:M1911";
	}

	public static int getMaxAmmo() {
		return 40;
	}

	public static int getRange() {
		return 40;
	}

	public static double getAccuracy() {
		return 0.1;
	}

	public static String getOrigin() {
		return "USA";
	}

	public static AmmoType getAmmoType() {
		return AmmoType.NINEMM;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static long getDelayPerShot() {
		return 200L;
	}
	

}
