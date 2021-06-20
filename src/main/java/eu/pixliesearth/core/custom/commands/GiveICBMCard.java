package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.custom.items.ItemICBMCard;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveICBMCard extends CustomCommand {
	
	public GiveICBMCard() {
		
	}
	
	@Override
	public String getCommandName() {
		return "icbmgive";
	}
	
	@Override
	public String getCommandDescription() {
		return "Gives a custom item based on the input";
	}
	
	@Override
	public String getPermission() {
		return "eu.pixlies.customitems.give";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (parameters.length<5) {
			commandSender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		Player p = Bukkit.getPlayer(parameters[0]);
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromClass(ItemICBMCard.class); //TODO: Maybe change this to uuid?
		if (p==null) {
			commandSender.sendMessage("Please enter a valid player");
			return false;
		}
		ItemStack itemStack = CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(c.getClass());
		NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
		tags.addTag("e", parameters[1], NBTTagType.STRING);
		tags.addTag("d", parameters[2], NBTTagType.STRING);
		tags.addTag("r", parameters[3], NBTTagType.STRING);
		tags.addTag("l", parameters[4], NBTTagType.STRING);
		commandSender.sendMessage("§rGave the player §a"+p.getDisplayName()+"§r a §a"+c.getDefaultDisplayName()+"§r(§a"+c.getUUID()+"§r) with the stats Explosive: §a"+parameters[1]+"§r, PlayerDamage: §a"+parameters[2]+"§r, Range: §a"+parameters[3]+"§r, Launchtime: §a"+parameters[4]);
		p.getInventory().addItem(NBTUtil.addTagsToItem(itemStack, tags));
		return true;
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabablePlayer(), new TabableString("Explosive"), new TabableString("Player Damage"), new TabableString("Range"), new TabableString("Launch Time")};
	}
}
