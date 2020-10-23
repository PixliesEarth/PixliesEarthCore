package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineFarmingWorkbench extends CustomCrafterMachine {
	
	public MachineFarmingWorkbench() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "fdfde9015d7b16fcdadf8090e7443bdb9a7f6748860b5ae2718b44ab0628f4d7";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Farming Workbench";
	}
    
    @Override
    public String getUUID() {
        return "Machine:FarmingWorkBench"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
