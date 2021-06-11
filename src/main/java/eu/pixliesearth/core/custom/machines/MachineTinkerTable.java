package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineTinkerTable extends CustomCrafterMachine {
	
	public MachineTinkerTable() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "83d47199d034fae71e5c7ef1e12bf9f1adbb88c22ad4b0e9453abf8cee5c350b";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Tinker Table";
    }

    @Override
    public String getUUID() {
        return "Machine:TinkerTable"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
