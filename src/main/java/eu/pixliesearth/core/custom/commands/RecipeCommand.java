package eu.pixliesearth.core.custom.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.StringUtil;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.interfaces.Recipeable;
import eu.pixliesearth.core.custom.listeners.CustomMachineCommandListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;

public class RecipeCommand extends CustomCommand {

	public RecipeCommand() {
		
	}
	
	@Override
	public String getName() {
		return "getrecipe";
	}
	
	@Override
	public String getDescription() {
		return "Uses the input to show the items recipes";
	}
	
	@Override
	public boolean execute(CommandSender commandsender, String alias, String[] args) {
		if (!(commandsender instanceof Player)) return false;
		if (args.length < 1) {
			commandsender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		String s = args[0];
		List<CustomRecipe> list = CustomMachineCommandListener.getRecipesOfUUIDInOrderedList(s);
		if (list.isEmpty()) {
			commandsender.sendMessage("No recipes found for this UUID!");
		} else {
			CustomRecipe r = list.get(0);
			CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
			if (c!=null && c instanceof Recipeable) {
				((Player)commandsender).closeInventory();
				Inventory inv = ((Recipeable)c).getCraftingExample(r);
				inv.setItem(Recipeable.recipeItemSlot, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("LIST", Integer.toString(0), NBTTagType.STRING).build());
				((Player)commandsender).openInventory(inv);
			} else {
				commandsender.sendMessage("Unable to load the gui for this UUID!");
			}
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
		List<String> array = new ArrayList<String>();
		if (args.length<2) {
			StringUtil.copyPartialMatches(args[0], getCustomRecipesAsStringList(), array);
		}
		Collections.sort(array);
		return array;
	}
	
	public static List<String> getCustomRecipesAsStringList() {
		List<String> array = new ArrayList<>();
		for (CustomRecipe c : Methods.convertSetIntoList(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes())) {
			if (!array.contains(c.getResultUUID())) {
				array.add(c.getResultUUID());
			}
		}
		return array;
	}
}
