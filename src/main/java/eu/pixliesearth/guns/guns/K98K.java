package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class K98K extends CustomGun {

    @Override
    public String getDefaultDisplayName() {
    	return "§c§lKarabiner98k";
    }

    @Override
	public Integer getCustomModelData() {
		return 6;
	}

	@Override
	public String getUUID() {
		return "Gun:Karabiner98k";
	}

	@Override
	public int getMaxAmmo() {
		return 5;
	}

	@Override
	public int getRange() {
		return 100;
	}

	@Override
	public double getAccuracy() {
		return 0.04;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.RIFLE_AMMO;
	}

	@Override
	public String getOrigin() {
		return "Germany";
	}
	
	@Override
	public long getDelayPerShot() {
		return 550l;
	}
	
}
