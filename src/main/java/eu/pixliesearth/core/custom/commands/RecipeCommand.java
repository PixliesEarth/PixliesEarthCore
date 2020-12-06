package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.interfaces.IRecipeable;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.listeners.CustomMachineCommandListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class RecipeCommand extends CustomCommand {

	public RecipeCommand() {
		
	}
	
	@Override
	public String getCommandName() {
		return "viewrecipe";
	}
	
	@Override
	public String getCommandDescription() {
		return "Uses the input to show the items recipes";
	}
	
	@Override
	public boolean isPlayerOnlyCommand() {
		return true;
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (parameters.length < 1) {
			commandSender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		String s = parameters[0];
		List<CustomRecipe> list = CustomMachineCommandListener.getRecipesOfUUIDInOrderedList(s);
		if (list.isEmpty()) {
			commandSender.sendMessage("No recipes found for this UUID!");
		} else {
			CustomRecipe r = list.get(0);
			CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
			if (c!=null && c instanceof IRecipeable) {
				((Player)commandSender).closeInventory();
				Inventory inv = ((IRecipeable)c).getCraftingExample(r);
				if (inv==null) {
					commandSender.sendMessage("Unable to load the gui for this UUID! It returns null.");
					return false;
				}
				inv.setItem(IRecipeable.recipeItemSlot, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("LIST", Integer.toString(0), NBTTagType.STRING).build());
				inv.setItem(IRecipeable.cratinInItemSlot, CustomItemUtil.getItemStackFromUUID(r.craftedInUUID()));
				((Player)commandSender).openInventory(inv);
			} else {
				commandSender.sendMessage("Unable to load the gui for this UUID!");
			}
		}
		return true;
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableRecipes()};
	}
	
	private static class TabableRecipes implements ITabable {
		
		public static List<String> getCustomRecipesAsStringList() {
			List<String> array = new ArrayList<>();
			for (CustomRecipe c : Methods.convertSetIntoList(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes())) {
				if (!array.contains(c.getResultUUID())) {
					array.add(c.getResultUUID());
				}
			}
			return array;
		}

		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			return getCustomRecipesAsStringList();
		}
		
		@Override
		public String getTabableName() {
			return "UUID";
		}
		
	}
}
