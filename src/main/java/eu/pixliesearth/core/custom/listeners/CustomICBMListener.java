package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom missiles</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomICBMListener extends CustomListener {
	
	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand()==null) return;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return;
		if (!isHoldingAMissileKey(event.getPlayer())) return;
		if (!isAMissile(event.getClickedBlock())) return;
		event.getPlayer().sendMessage("MISSILE FOUND");
	}
	
	public boolean isAMissile(Block b) {
		Location bl = b.getLocation().clone();
		World w = b.getLocation().getWorld();
		Block b2 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-1, bl.getBlockZ());
		Block b3 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ());
		Block b4 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()-1);
		Block b5 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()+1);
		Block b6 = w.getBlockAt(bl.getBlockX()-1, bl.getBlockY()-2, bl.getBlockZ());
		Block b7 = w.getBlockAt(bl.getBlockX()+1, bl.getBlockY()-2, bl.getBlockZ());
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		int val = 0;
		if (h.getCustomBlockFromLocation(b.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b.getLocation()).getUUID().equals("Pixlies:Missile_Warhead_Block")) //Warhead UUID
				val += 1;
		if (h.getCustomBlockFromLocation(b2.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b2.getLocation()).getUUID().equals("Pixlies:Missile_Block"))
				val += 1;
		if (h.getCustomBlockFromLocation(b3.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b3.getLocation()).getUUID().equals("Pixlies:Missile_Block"))
				val += 1;
		if (h.getCustomBlockFromLocation(b4.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b4.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b5.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b5.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b6.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b6.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b7.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b7.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		return val>=7;
	}
	
	public boolean isHoldingAMissileKey(Player player) {
		return CustomItemUtil.getUUIDFromItemStack(player.getInventory().getItemInMainHand()).equals("Pixlies:ICBM_Key");
	}
}
