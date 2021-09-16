package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class AK47 extends CustomGun {

	public AK47() {
		super(PixlieFun.gunsCategory, buildItem(), PixlieFun.GUN_WORKBENCH, getGunRecipe());
	}

	public static SlimefunItemStack buildItem() {
		return PixlieFunItems.AK47;
	}

	public static String getDefaultDisplayName() {
		return "§c§lAK-47";
	}

	public static Integer getCustomModelData() {
		return 5;
	}

	public static String getUUID() {
		return "AK-47";
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

	public static ItemStack[] getGunRecipe() {
		return new ItemStack[]{
				PixlieFunItems.RIFLE_BARREL,
				PixlieFunItems.RIFLE_RECEIVER,
				PixlieFunItems.RIFLE_STOCK,
				null,
				null,
				null,
				null,
				null,
				null
		};
	}

}
