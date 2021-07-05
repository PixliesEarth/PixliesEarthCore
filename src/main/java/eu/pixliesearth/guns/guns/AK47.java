package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class AK47 extends CustomGun {

	public static String getDefaultDisplayName() {
		return "§c§lAK-47";
	}

	public static Integer getCustomModelData() {
		return 5;
	}

	public static String getUUID() {
		return "Gun:AK-47";
	}

	public static int getMaxAmmo() {
		return 40;
	}

	public static int getRange() {
		return 60;
	}

	public static double getAccuracy() {
		return 0.08;
	}

	public static String getOrigin() {
		return "RUSSIA/USSR";
	}

	public static AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static long getDelayPerShot() {
		return 200L;
	}
}
