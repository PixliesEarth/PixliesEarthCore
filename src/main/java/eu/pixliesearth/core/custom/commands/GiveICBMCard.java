package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.items.ItemICBMCard;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveICBMCard extends CustomCommand {
	
	public GiveICBMCard() {
		
	}
	
	@Override
	public String getName() {
		return "icbmgive";
	}
	
	@Override
	public String getDescription() {
		return "Gives a custom item based on the input";
	}
	
	@Override
	public String getPermission() {
		return "eu.pixlies.customitems.give";
	}
	
	@Override
	public boolean execute(CommandSender commandsender, String alias, String[] args) {
		if (args.length<5) {
			commandsender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		Player p = Bukkit.getPlayer(args[0]);
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromClass(ItemICBMCard.class); //TODO: Maybe change this to uuid?
		if (p==null) {
			commandsender.sendMessage("Please enter a valid player");
			return false;
		}
		
		ItemStack itemStack = CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(c.getClass());
		NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
		tags.addTag("e", args[1], NBTTagType.STRING);
		tags.addTag("d", args[2], NBTTagType.STRING);
		tags.addTag("r", args[3], NBTTagType.STRING);
		tags.addTag("l", args[4], NBTTagType.STRING);
		commandsender.sendMessage("§rGave the player §a"+p.getDisplayName()+"§r a §a"+c.getDefaultDisplayName()+"§r(§a"+c.getUUID()+"§r) with the stats Explosive: §a"+args[1]+"§r, PlayerDamage: §a"+args[2]+"§r, Range: §a"+args[3]+"§r, Launchtime: §a"+args[4]);
		p.getInventory().addItem(NBTUtil.addTagsToItem(itemStack, tags));
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
		List<String> array = new ArrayList<String>();
		
		if (args.length<2) {
			StringUtil.copyPartialMatches(args[0], GiveCustomItems.getOnlinePlayersAsStringList(), array);
			Collections.sort(array);
			return array;
		} else if (args.length<3) {
			array.add("Explosive");
		} else if (args.length<4) {
			array.add("Player Damage");
		} else if (args.length<5) {
			array.add("Range");
		} else if (args.length<5) {
			array.add("LaunchTime");
		}
		return array;
	}
}
