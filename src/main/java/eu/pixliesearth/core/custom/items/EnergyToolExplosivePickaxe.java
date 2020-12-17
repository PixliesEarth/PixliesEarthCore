package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyItem;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.listener.ProtectionManager;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EnergyToolExplosivePickaxe extends CustomEnergyItem {
	
	public EnergyToolExplosivePickaxe() {
		
	}

	@Override
    public Rarity getRarity() {
	    return Rarity.UNCOMMON;
    }

	@Override
    public Material getMaterial() {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Explosive Pickaxe";
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
        return 3;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Explosive_Pickaxe"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
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
			b.getWorld().createExplosion(b.getLocation(), 0F);
			b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.2F, 1F);
			
			boolean v = false;
			List<Block> blocks = findBlocks(b, p);
			for (Block bloc : blocks) {
				if (!(getContainedPower(e.getPlayer().getInventory().getItemInMainHand())>=energyCostPerBlock)) continue;
				e.getPlayer().getInventory().setItemInMainHand(removeEnergy(e.getPlayer().getInventory().getItemInMainHand(), energyCostPerBlock));
				breakBlock(p.getInventory().getItemInMainHand(), bloc);
				v=true;
			}
			if (v) {
				BlockExplodeEvent blockExplodeEvent = new BlockExplodeEvent(b, blocks, 0);
				blockExplodeEvent.callEvent(); // You can just use this!
				// Bukkit.getServer().getPluginManager().callEvent(blockExplodeEvent);
				return false;
			} else {
				p.sendMessage("You need to charge your explosive pickaxe for this!");
				return true;
			}
			// breakBlocks(p, p.getInventory().getItemInMainHand(), b, blocks);
		} else {
			if (!(getContainedPower(is)>=energyCostPerBlock)) {
				p.sendMessage("You need to charge your explosive pickaxe for this!");
				return true;
			} else {
				e.getPlayer().getInventory().setItemInMainHand(removeEnergy(e.getPlayer().getInventory().getItemInMainHand(), energyCostPerBlock));
				breakBlock(p.getInventory().getItemInMainHand(), b);
				return false;
			}
		}
    }
    
    @SuppressWarnings("unused")
	@Deprecated
	private void breakBlocks(Player p, ItemStack item, Block b, List<Block> blocks) {
		BlockExplodeEvent blockExplodeEvent = new BlockExplodeEvent(b, blocks, 0);
		Bukkit.getServer().getPluginManager().callEvent(blockExplodeEvent);

		if (!blockExplodeEvent.isCancelled())
			for (Block block : blockExplodeEvent.blockList()) breakBlock(item, block);
	}

	private List<Block> findBlocks(Block b, Player player) {
		List<Block> blocks = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (x == 0 && y == 0 && z == 0)
						continue;
					Block relative = b.getRelative(x, y, z);
					if (ProtectionManager.canBreak(relative, player)) blocks.add(relative);
				}
			}
		}
		return blocks;
	}

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
