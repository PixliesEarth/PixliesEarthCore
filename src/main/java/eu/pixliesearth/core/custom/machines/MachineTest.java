package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineTest extends CustomCrafterMachine {
	
	public MachineTest() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "8245a1c3e8d7c3d59d05e3634b04af4cbf8d11b70e2a40e2e6364386db49e737";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Test Machine";
    }

    @Override
    public String getUUID() {
        return "Machine:Test"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
