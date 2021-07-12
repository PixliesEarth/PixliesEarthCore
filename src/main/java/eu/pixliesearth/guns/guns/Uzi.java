package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class Uzi extends CustomGun {

	public static String getDefaultDisplayName() {
		return "§c§lUzi";
	}

	public static Integer getCustomModelData() {
		return 4;
	}

	public static String getUUID() {
		return "Gun:Uzi";
	}

	public static int getMaxAmmo() {
		return 32;
	}

	public static int getRange() {
		return 40;
	}

	public static double getAccuracy() {
		return 0.04;
	}

	public static String getOrigin() {
		return "Israel";
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
