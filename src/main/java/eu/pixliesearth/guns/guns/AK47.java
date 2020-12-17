package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class AK47 extends CustomGun {

	@Override
	public String getDefaultDisplayName() {
		return "§c§lAK-47";
	}

	@Override
	public Integer getCustomModelData() {
		return 5;
	}

	@Override
	public String getUUID() {
		return "Gun:AK-47";
	}

	@Override
	public int getMaxAmmo() {
		return 40;
	}

	@Override
	public int getRange() {
		return 60;
	}

	@Override
	public double getAccuracy() {
		return 0.08;
	}

	@Override
	public String getOrigin() {
		return "RUSSIA/USSR";
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}

}
