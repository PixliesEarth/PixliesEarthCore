package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class M1911 extends CustomGun {
    
    @Override
	public String getDefaultDisplayName() {
		return "§c§lM1911";
	}

	@Override
	public Integer getCustomModelData() {
		return 8;
	}

	@Override
	public String getUUID() {
		return "Gun:M1911";
	}

	@Override
	public int getMaxAmmo() {
		return 40;
	}

	@Override
	public int getRange() {
		return 40;
	}

	@Override
	public double getAccuracy() {
		return 0.1;
	}

	@Override
	public String getOrigin() {
		return "USA";
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
