package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class M1911 extends CustomGun {

	public M1911() {
		super(PixlieFun.gunsCategory, buildItem(), PixlieFun.GUN_WORKBENCH, getGunRecipe());
	}

	public static SlimefunItemStack buildItem() {
		return PixlieFunItems.M1911;
	}

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
		return AmmoType.PISTOL_AMMO;
	}

	public static int getDelayToReload() {
		return 2;
	}

	public static long getDelayPerShot() {
		return 200L;
	}

	public static ItemStack[] getGunRecipe() {
		return new ItemStack[]{
				PixlieFunItems.PISTOL_BARREL, PixlieFunItems.PISTOL_RECEIVER, PixlieFunItems.RIFLE_STOCK,
				null, null, null,
				null, null, null
		};
	}

}
