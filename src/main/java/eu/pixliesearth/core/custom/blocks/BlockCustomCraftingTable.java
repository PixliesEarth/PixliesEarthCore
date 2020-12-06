package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.interfaces.IRecipeable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class BlockCustomCraftingTable extends CustomBlock implements IRecipeable {
	
	public BlockCustomCraftingTable() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.DISPENSER;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {/** * */private static final long serialVersionUID = -7718795550188641493L;{
        	add("§aUsed for crafting custom materials together!");
        }};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Crafting Table";
    }

    @Override
    public boolean isGlowing() {
        return true;
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
        return CreativeTabs.BUILDING;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Crafting_Table"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
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
	public Inventory getCraftingExample(CustomRecipe customRecipe) {
		Inventory inv = Bukkit.createInventory(null, 6*9, craftingExampleTitle);
		int[] ints = {10,11,12,19,20,21,28,29,30};
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		int i2 = 0;
		for (int i : ints) {// Loop threw Items Slots
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(customRecipe.getContentsList().get(i2)));
			i2++;
		}
		inv.setItem(24, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID())).setAmount(customRecipe.getResultAmount()).build());
		inv.setItem(48, backItem); // Back
		inv.setItem(49, closeItem); // Close
		inv.setItem(50, nextItem); // Next
		inv.setItem(recipeItemSlot, CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID()));
		return inv;
	}
	
}
