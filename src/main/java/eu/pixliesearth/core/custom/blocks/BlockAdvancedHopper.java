package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.IHopperable;

public class BlockAdvancedHopper extends CustomBlock implements IHopperable {
	
	public BlockAdvancedHopper() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.HOPPER;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Advanced Hopper";
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
        return CreativeTabs.REDSTONE;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Advanced_Hopper"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	return false;
    }
    
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		return false;
	}

	@Override
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		return false;
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		return false;
	}

	@Override
	public ItemStack takeFirstTakeableItemFromIHopperableInventory(Location location) {
		Inventory inv = getMCInventory(location);
		for (ItemStack itemStack : inv.getContents()) {
			if (itemStack!=null) {
				ItemStack itemStack2 = itemStack.clone().asOne();
				itemStack.setAmount(itemStack.getAmount()-1);
				return itemStack2;
			}
		}
		return null;
	}

	@Override
	public boolean addItemToIHopperableInventory(Location location, ItemStack itemStack) {
		Inventory inv = getMCInventory(location);
		if (inv.firstEmpty()==-1) {
			if (inv.first(itemStack)==-1) {
				return false;
			} else {
				if (inv.getItem(inv.first(itemStack)).getAmount()==64) {
					return false;
				} else {
					inv.addItem(itemStack);
					return true;
				}
			}
		} else {
			inv.addItem(itemStack);
			return true;
		}
	}
	
	@Override
	public void onTick(Location location) {
		take(location);
		give(location);
	}

	public void take(Location location) {
		Inventory inventory = getMCInventory(location);
		Block b = location.getBlock().getRelative(BlockFace.UP);
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomBlock cb = h.getCustomBlockFromLocation(b.getLocation());
		if (cb instanceof IHopperable) {
			ItemStack itemStack = ((IHopperable) cb).takeFirstTakeableItemFromIHopperableInventory(b.getLocation());
			if (itemStack==null) return;
			if (hasRoomFor(inventory, itemStack)) {
				inventory.addItem(itemStack);
			}
		}
	}

	private boolean hasRoomFor(Inventory inventory, ItemStack itemStack) {
		if (inventory.isEmpty() || inventory.firstEmpty()!=-1) return true;
		for (ItemStack is : inventory.getContents()) {
			if (is.asOne().equals(itemStack.asOne()) && is.getAmount()<64) return true;
		}
		return false;
	}

	public void give(Location location) {
		Inventory inventory = getMCInventory(location);
		Block b = location.getBlock().getRelative(getFactingOutput(location));
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomBlock cb = h.getCustomBlockFromLocation(b.getLocation());
		if (cb instanceof IHopperable) {
			ItemStack itemStack = null;
			for (ItemStack is : inventory.getContents()) {
				if (is!=null) {
					itemStack = is.asOne();
					break;
				}
			}
			if (itemStack==null) return;
			if (((IHopperable) cb).addItemToIHopperableInventory(location, itemStack.asOne())) {
				itemStack.setAmount(itemStack.getAmount()-1);
			}
		}
	}

	private Inventory getMCInventory(Location loc) {
		return ((InventoryHolder) loc.getBlock().getState()).getInventory();
	}

	private BlockFace getFactingOutput(Location loc) {
		return ((org.bukkit.block.data.type.Hopper) ((Hopper) loc.getBlock().getState()).getData()).getFacing();
	}
	
}
