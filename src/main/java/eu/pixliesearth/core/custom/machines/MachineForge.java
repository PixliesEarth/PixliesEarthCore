package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineForge extends CustomCrafterMachine { //TODO: make use fuel
	
	public MachineForge() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Forge";
    }

    @Override
    public String getUUID() {
        return "Machine:Forge"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
