package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

public class K98K extends CustomGun {

	public K98K() {
		super(PixlieFun.gunsCategory, buildItem(), PixlieFun.GUN_WORKBENCH, getGunRecipe());
	}

	public static SlimefunItemStack buildItem() {
		return PixlieFunItems.AK47;
	}

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
