package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomFuelableCrafterMachine;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.interfaces.IRecipeable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Map.Entry;

public class MachineForge extends CustomFuelableCrafterMachine implements IRecipeable {
	
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

	@Override
	public String getFuelUUID() {
		return "Pixlies:Carbon_Dust";
	}

	
	public Inventory getCraftingExample(CustomRecipe customRecipe) {
		Inventory inv = Bukkit.createInventory(null, 6*9, craftingExampleTitle);
		inv.setContents(getInventory2(customRecipe, 1).getContents());
		for (int i : craftSlots) 
			inv.clear(i);
		for (Entry<String, Integer> entry : customRecipe.getAsUUIDToAmountMap().entrySet()) {
			if (entry.getValue()>64) {
				int i = entry.getValue();
				while (i!=0) {
					if (i>64) {
						int i2 = getNextFreeSlotInCrafting(inv);
						if (i2==-1) {
							// Do nothing (maybe add an error panel)
						} else {
							inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(64).build());
						}
						i -= 64;
					} else {
						int i2 = getNextFreeSlotInCrafting(inv);
						if (i2==-1) {
							// Do nothing (maybe add an error panel)
						} else {
							inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(i).build());
						}
						i -= i;
					}
				}
			} else {
				int i = getNextFreeSlotInCrafting(inv);
				if (i==-1) {
					// Do nothing (maybe add an error panel)
				} else {
					inv.setItem(i, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(entry.getValue()).build());
				}
			}
		}
		addToResult(null, inv, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID())).setAmount(customRecipe.getResultAmount()).build());
		inv.setItem(48, backItem); // Back
		inv.setItem(49, closeItem); // Close
		inv.setItem(50, nextItem); // Next
		inv.setItem(recipeItemSlot, CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID()));
		inv.setItem(52, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.setItem(53, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		return inv;
	}
}
