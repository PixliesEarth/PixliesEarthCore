package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class Uzi extends CustomGun {
    
    @Override
	public String getDefaultDisplayName() {
		return "§c§lUzi";
	}

	@Override
	public Integer getCustomModelData() {
		return 4;
	}

	@Override
	public String getUUID() {
		return "Gun:Uzi";
	}

	@Override
	public int getMaxAmmo() {
		return 32;
	}

	@Override
	public int getRange() {
		return 40;
	}

	@Override
	public double getAccuracy() {
		return 0.04;
	}

	@Override
	public String getOrigin() {
		return "Israel";
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.NINEMM;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}
	
	@Override
	public long getDelayPerShot() {
		return 200l;
	}
	

}
