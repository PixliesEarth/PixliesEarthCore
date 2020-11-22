package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

public class MachinesCommand extends CustomCommand {

	public MachinesCommand() {
		
	}
	
	@Override
	public String getCommandName() {
		return "machines";
	}

	@Override
	public Set<String> getCommandAliases() {
		Set<String> returner = new HashSet<>();
		returner.add("items");
		returner.add("recipes");
		return returner;
	}
	
	@Override
	public String getCommandDescription() {
		return "Shows all machines n stuff";
	}
	
	@Override
	public boolean isPlayerOnlyCommand() {
		return true;
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Machines");
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.setItem(11, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§b?").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN1", NBTTagType.STRING).build());
		inv.setItem(13, new ItemBuilder(Material.ITEM_FRAME).setDisplayName("§bItems").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN2", NBTTagType.STRING).build());
		inv.setItem(15, new ItemBuilder(Material.CRAFTING_TABLE).setDisplayName("§bRecipes").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN3", NBTTagType.STRING).build());
		((Player)commandSender).openInventory(inv);
		return true;
	}

}
