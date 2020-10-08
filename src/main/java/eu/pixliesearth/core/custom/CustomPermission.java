package eu.pixliesearth.core.custom;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class CustomPermission {
	
	public CustomPermission() {
		
	}

	public String getName() {
		return null;
	}
	
	public String getDescription() {
		return null;
	}
	
	public PermissionDefault getPermissionDefault() {
		return PermissionDefault.FALSE;
	}
	
	public Permission getAsRegisterablePermission() {
		return new Permission(getName()) {{
			if (getDescription()!=null) 
				setDescription(getDescription());
			setDefault(getPermissionDefault());
		}};
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
