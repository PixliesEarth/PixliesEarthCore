package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineGunWorkbench extends CustomCrafterMachine {
	
	public MachineGunWorkbench() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "579ee734b739c0d19546d5b4b049c01a055506cc00b0cfcbf177883d2c814c04";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Gun Workbench";
    }

    @Override
    public String getUUID() {
        return "Machine:GunWorkbench"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
