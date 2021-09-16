package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;

import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class Slingshot extends CustomGun {

	public Slingshot() {
		super(PixlieFun.gunsCategory, buildItem(), PixlieFun.GUN_WORKBENCH, getGunRecipe());
	}

	public static SlimefunItemStack buildItem() {
		return PixlieFunItems.AK47;
	}

    public static Material getMaterial() {
    	return Material.GOLDEN_HOE;
    }

	public static String getDefaultDisplayName() {
		return "§c§lSlingshot";
	}

	public static Integer getCustomModelData() {
		return 55;
	}

	public static String getUUID() {
		return "Gun:Slingshot";
	}

	public static int getMaxAmmo() {
		return 1;
	}

	public static int getRange() {
		return 20;
	}

	public static double getAccuracy() {
		return 0.04;
	}

	public static String getOrigin() {
		return "Unknown";
	}

	public static AmmoType getAmmoType() {
		return AmmoType.COBBLESTONE;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static long getDelayPerShot() {
		return 1000L;
	}
	

}
