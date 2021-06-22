package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo.AmmoType;

public class MP5 extends CustomGun {
    
    @Override
	public String getDefaultDisplayName() {
		return "§c§lMP5";
	}

	@Override
	public Integer getCustomModelData() {
		return 7;
	}

	@Override
	public String getUUID() {
		return "Gun:MP5";
	}

	@Override
	public int getMaxAmmo() {
		return 15;
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
		return "Germany";
	}

	@Override
	public AmmoType getAmmoType() {
		return AmmoType.NATO762x51;
	}

	@Override
	public int getDelayToReload() {
		return 2;
	}
	
	@Override
	public long getDelayPerShot() {
		return 350l;
	}
	

}
