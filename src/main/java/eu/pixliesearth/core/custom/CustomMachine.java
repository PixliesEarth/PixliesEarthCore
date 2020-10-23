package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public class CustomMachine extends CustomBlock {
	/**
	 * Initialises the class
	 */
	public CustomMachine() {
		
	}
	/**
	 * The default material
	 */
	@Override
	public Material getMaterial() {
		return MinecraftMaterial.PLAYER_HEAD.getMaterial();
	}
	/**
	 * @return The player head UUID from the API call http://textures.minecraft.net/texture/{@link #getPlayerHeadUUID()}
	 */
	public String getPlayerHeadUUID() {
		return null;
	}
	/**
	 * Opens the custom inventory
	 * 
	 * @param player The {@link Player} who wants to open the {@link Inventory}
	 * @param location The {@link Location} of the {@link CustomMachine} that houses the {@link Inventory}
	 */
	public void open(Player player, Location location) {
		player.openInventory(CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location));
	}
	/**
	 * @return The {@link CustomMachine}'s {@link Inventory}
	 */
	public Inventory getInventory() { return null; }
	/**
	 * @return The {@link Inventory}'s title
	 */
	public String getInventoryTitle() {
		return getDefaultDisplayName();
	}
	/**
	 * Called every Minecraft Tick
	 */
	public void onTick(Location location, Inventory inventory, Timer timer) { }
	// TODO: notes
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) { }
	// TODO: notes
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		return null;
	}
	/**
	 * Called when a player clicks on the {@link Inventory}
	 * 
	 * @param event The {@link InventoryClickEvent} that occurred
	 * @return If the event should be cancelled
	 */
	public boolean InventoryClickEvent(InventoryClickEvent event) { return false; }
	/**
	 * Called when the {@link Inventory} is closed
	 * 
	 * @param event The {@link InventoryCloseEvent} that occurred
	 */
	public void InventoryCloseEvent(InventoryCloseEvent event) { }
	/**
	 * Builds the item as a player head
	 */
	@Override
	public ItemStack buildItem() {
		return new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/"+getPlayerHeadUUID())) {{
			setGlow(isGlowing());
			setUnbreakable(isUnbreakable());
			if (getDefaultDisplayName()==null) 
					setNoName();
			else 
				setDisplayName(getDefaultDisplayName());
			if (getCustomModelData()!=null) 
				setCustomModelData(getCustomModelData());
			for (Entry<Enchantment, Integer> entry : getDefaultEnchants().entrySet()) 
				addEnchantment(entry.getKey(), entry.getValue());
			if (getDefaultLore()!=null)
				addLoreAll(getDefaultLore());
			for (ItemFlag flag : getItemFlags()) 
				addItemFlag(flag);
			addLoreLine("§fRarity: "+getRarity().getName());
			for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
				addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
			addNBTTag("UUID", getUUID(), NBTTagType.STRING);
			addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
		}}.build();
	}
	/**
	 * Called when the custom block is interacted with
	 * 
	 * @param event The {@link PlayerInteractEvent} that interacted with the block
	 * @return If the event should be cancelled
	 */
	@Override
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		open(event.getPlayer(), event.getClickedBlock().getLocation());
		return false;
	}
	/**
	 * Sets the provided {@link Location} to a more optimal {@link Location} and returns it
	 */
	public static Location holoLocation(Location location) {
        Location toSpawn = location.clone();
        toSpawn.setY(toSpawn.getY() + 1D);
        toSpawn.setX(toSpawn.getX() + 0.5);
        toSpawn.setZ(toSpawn.getZ() + 0.5);
        return toSpawn;
    }
	/**
	 * Creates a {@link Hologram} at the {@link Location} provided with the text provided
	 * 
	 * @param title The {@link Hologram}'s title
	 * @param location The {@link Hologram}'s {@link Location}
	 * @return The created {@link Hologram}
	 */
	public static Hologram createHologram(String title, Location location) {
		Hologram hologram = HologramsAPI.createHologram(CustomFeatureLoader.getLoader().getInstance(), holoLocation(location));
		hologram.insertTextLine(0, title);
		return hologram;
	}
}
