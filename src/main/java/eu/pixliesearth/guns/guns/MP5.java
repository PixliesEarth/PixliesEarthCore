package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

public class MP5 extends CustomGun {

	public MP5() {
		super(PixlieFun.gunsCategory, buildItem(), PixlieFun.GUN_WORKBENCH, getGunRecipe());
	}

	public static SlimefunItemStack buildItem() {
		return PixlieFunItems.AK47;
	}

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
