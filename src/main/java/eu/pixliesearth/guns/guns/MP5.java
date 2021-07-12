package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class MP5 extends CustomGun {

	public static String getDefaultDisplayName() {
		return "§c§lMP5";
	}

	public static Integer getCustomModelData() {
		return 7;
	}

	public static String getUUID() {
		return "Gun:MP5";
	}

	public static int getMaxAmmo() {
		return 15;
	}

	public static int getRange() {
		return 40;
	}

	public static double getAccuracy() {
		return 0.1;
	}

	public static String getOrigin() {
		return "Germany";
	}

	public static AmmoType getAmmoType() {
		return AmmoType.NATO762x51;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static long getDelayPerShot() {
		return 350l;
	}
	

}
