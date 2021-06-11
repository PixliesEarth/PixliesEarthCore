package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyItem;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.CustomItemUtil;

public class EnergyToolLumberAxe extends CustomEnergyItem {
	
	public EnergyToolLumberAxe() {
		
	}

	@Override
    public Rarity getRarity() {
	    return Rarity.UNCOMMON;
    }

	@Override
    public Material getMaterial() {
        return Material.IRON_AXE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Electric Lumber Axe";
    }
    
	public double getCapacity() {
		return 1_000_000D;
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
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Lumber_Axe_Electric"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }
    
    @Override
    public boolean onBlockBrokeWithItem(BlockBreakEvent e) {
    	
    	double energyCostPerBlock = 125D;
    	
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
		if (is==null) return false;
		if (!CustomItemUtil.isItemStackACustomItem(is)) return false;

		if (b.getType().getHardness() < 0.05) {
			p.sendMessage(Lang.VERY_SMART.get(p));
			return false;
		}
		
		if (!p.isSneaking()) {
			boolean v = false;
			Set<Block> blocks = getTree(b);
			for (Block bloc : blocks) {
				if (!(getContainedPower(e.getPlayer().getInventory().getItemInMainHand())>=energyCostPerBlock)) continue;
				e.getPlayer().getInventory().setItemInMainHand(removeEnergy(e.getPlayer().getInventory().getItemInMainHand(), energyCostPerBlock));
				breakBlock(p.getInventory().getItemInMainHand(), bloc);
				v=true;
			}
			if (!v) {
				p.sendMessage("You need to charge your lumber axe for this!");
				return true;
			} else {
				return false;
			}
			// breakBlocks(p, p.getInventory().getItemInMainHand(), b, blocks);
		} else {
			if (!(getContainedPower(is)>=energyCostPerBlock)) {
				p.sendMessage("You need to charge your lumber axe for this!");
				return true;
			} else {
				e.getPlayer().getInventory().setItemInMainHand(removeEnergy(e.getPlayer().getInventory().getItemInMainHand(), energyCostPerBlock));
				breakBlock(p.getInventory().getItemInMainHand(), b);
				return false;
			}
		}
    }
    
    private Set<Block> getTree(Block start) {
    	Set<Block> set = new LinkedHashSet<>();
    	set.add(start);
    	if (!isWood(start.getType())) return set;
    	Set<Block> set2 = getSurroundingSimilarWood(start);
    	Set<Block> set3 = new LinkedHashSet<>();
    	boolean loop = true;
    	while (loop) {
    		for (Block block : set2) {
    			set.remove(block);
    			set.add(block);
    			set3.addAll(getSurroundingSimilarWood(block));
    		}
    		set2.clear();
    		set2.addAll(set3);
    		set3.clear();
    		if (set.containsAll(set2)) loop = false;
    	}
    	return set;
    }
    
    private Set<Block> getSurroundingSimilarWood(Block block) {
    	Set<Block> set = new HashSet<>();
    	if (!isWood(block.getType())) return set;
    	Block block2 = block.getRelative(BlockFace.DOWN);
    	if (block.getType().equals(block2.getType())) set.add(block2);
    	block2 = block.getRelative(BlockFace.UP);
    	if (block.getType().equals(block2.getType())) set.add(block2);
    	block2 = block.getRelative(BlockFace.EAST);
    	if (block.getType().equals(block2.getType())) set.add(block2);
    	block2 = block.getRelative(BlockFace.SOUTH);
    	if (block.getType().equals(block2.getType())) set.add(block2);
    	block2 = block.getRelative(BlockFace.WEST);
    	if (block.getType().equals(block2.getType())) set.add(block2);
    	block2 = block.getRelative(BlockFace.NORTH);
    	if (block.getType().equals(block2.getType())) set.add(block2);
		return set;
    }
    
    private boolean isWood(Material material) { return ( material.equals(Material.OAK_LOG) || material.equals(Material.DARK_OAK_LOG) || material.equals(Material.BIRCH_LOG) || material.equals(Material.SPRUCE_LOG) || material.equals(Material.JUNGLE_LOG) || material.equals(Material.ACACIA_LOG) || material.equals(Material.WARPED_STEM) || material.equals(Material.CRIMSON_STEM) ); }

	private boolean breakBlock(ItemStack item, Block block) {

		Material material = block.getType();

		block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, material);

		CustomBlock c = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(block.getLocation());
		if (c == null) {
			block.breakNaturally(item);
		} else {
			block.getWorld().dropItem(block.getLocation(), CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(c.getUUID()));
			CustomFeatureLoader.getLoader().getHandler().removeCustomBlockFromLocation(block.getLocation());
		}
		return true;
	}

}
