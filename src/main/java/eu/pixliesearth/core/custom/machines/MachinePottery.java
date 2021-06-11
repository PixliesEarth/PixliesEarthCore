package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachinePottery extends CustomCrafterMachine {
	
	public MachinePottery() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "d6eeab45beec2f0861026b9a354b63f23c2b88d79e41b7504c87bcbeb779e";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Pottery";
    }

    @Override
    public String getUUID() {
        return "Machine:Pottery"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
