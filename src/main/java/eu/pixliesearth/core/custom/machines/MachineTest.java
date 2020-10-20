package eu.pixliesearth.core.custom.machines;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomCrafterMachine;

public class MachineTest extends CustomCrafterMachine {
	
	public MachineTest() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "8245a1c3e8d7c3d59d05e3634b04af4cbf8d11b70e2a40e2e6364386db49e737";
	}

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Test Machine";
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>();
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.NONE;
    }

    @Override
    public String getUUID() {
        return "Machine:Test"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
