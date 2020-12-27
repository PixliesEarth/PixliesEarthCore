package eu.pixliesearth.core.custom;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.items.EnergyToolExplosivePickaxe;
import eu.pixliesearth.core.custom.items.EnergyToolLumberAxe;
import eu.pixliesearth.core.custom.items.ToolLumberAxe;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.core.custom.skills.events.SkillLevelGainedEvent;
import eu.pixliesearth.core.custom.skills.events.SkillXPGainedEvent;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;

public class SkillListener extends CustomListener {
	
	private final Random random = new Random();
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		SkillHandler.getSkillHandler().createSkillsFor(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void SkillLevelGainedEvent(SkillLevelGainedEvent event) {
		if (event.getNewLevel()<=event.getOldLevel()) {
			event.setCancelled(true);
			return;
		}
		if (event.isCancelled()) return;
		
		event.getPlayer().sendMessage("§a[§r✔️§a]§r You have gained a level in "+event.getGainedSkillUUID()+"!");
		event.getPlayer().sendMessage("§aYou are now level §r"+event.getNewLevel()+"§a!");
		
		Profile profile = Main.getInstance().getProfile(event.getPlayer().getUniqueId());
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		
		if (event.getGainedSkillUUID().equals("Pixlies:Traveling")) {
			for (int i = 0; i < event.getNewLevel()-event.getOldLevel(); i++) {
				Energy.add(profile, BigDecimal.valueOf((0.1D*(Math.floor(Math.floor((event.getOldLevel()+1+i)/10.0)/10.0)))).setScale(1, BigDecimal.ROUND_DOWN).doubleValue());
			}
		} else if (event.getGainedSkillUUID().equals("Pixlies:Lumbering")) {
			if (event.getOldLevel()<10 && event.getNewLevel()>=10) {
				event.getPlayer().sendMessage("§a[§r✔️§a]§r You have unlocked the recipe for "+h.getCustomItemFromClass(ToolLumberAxe.class).getUUID());
			}
			if (event.getOldLevel()<35 && event.getNewLevel()>=35) {
				event.getPlayer().sendMessage("§a[§r✔️§a]§r You have unlocked the recipe for "+h.getCustomItemFromClass(EnergyToolLumberAxe.class).getUUID());
			}
		} else if (event.getGainedSkillUUID().equals("Pixlies:Mining")) {
			if (event.getOldLevel()<35 && event.getNewLevel()>=35) {
				event.getPlayer().sendMessage("§a[§r✔️§a]§r You have unlocked the recipe for "+h.getCustomItemFromClass(EnergyToolExplosivePickaxe.class).getUUID());
			}
		}
	}
	
	@EventHandler
	public void SkillXPGainedEvent(SkillXPGainedEvent event) {
		if (event.getAmount()<=0) {
			event.setCancelled(true);
			return;
		}
		if (event.isCancelled()) return;
		
		event.getPlayer().sendActionBar("§aGained §r"+event.getAmount()+"§a "+event.getGainedSkillUUID()+"§a XP");
		
		int level = SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID());
		SkillHandler.getSkillHandler().addXPTo(event.getPlayer().getUniqueId(), event.getGainedSkillUUID(), event.getAmount());
		if (level<SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID())) {
			new SkillLevelGainedEvent(event.getPlayer(), event.getGainedSkillUUID(), SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), event.getGainedSkillUUID()), level).callEvent();
		}
	}
	
	@EventHandler
	public void EntityTameEvent(EntityTameEvent event) {
		if (event.isCancelled()) return;
		if (event.getOwner() instanceof Player) {
			new SkillXPGainedEvent((Player)event.getOwner(), "Pixlies:Taming", random.nextInt(15)+1).callEvent();
		}
	}
	
	@EventHandler
	public void EntityBreedEvent(EntityBreedEvent event) {
		if (event.isCancelled()) return;
		if (event.getBreeder() instanceof Player) {
			new SkillXPGainedEvent((Player)event.getBreeder(), "Pixlies:Farming", 1).callEvent();
			new SkillXPGainedEvent((Player)event.getBreeder(), "Pixlies:Taming", random.nextInt(2)+1).callEvent();
		}
	}
	
	@EventHandler
	public void BlockPlaceEvent(BlockPlaceEvent event) {
		if (event.isCancelled() || event.getBlock()==null || event.getBlock().getType().equals(Material.AIR)) return;
		new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Building", 1).callEvent();
	}
	
	@EventHandler
	public void BlockBreakEvent(BlockBreakEvent event) {
		if (event.isCancelled() || event.getBlock()==null || event.getBlock().getType().equals(Material.AIR)) return;
		if (SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), "Pixlies:Mining")>=15) {
			// (int) (Math.floor((level/15D))*10);
			if ((random.nextInt(100)+1)<=SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), "Pixlies:Mining")/10) {
				if (event.getPlayer().getInventory().getItemInMainHand().hasDamage()) {
					event.getPlayer().getInventory().getItemInMainHand().setDamage(event.getPlayer().getInventory().getItemInMainHand().getDamage()-1);
				}
			}
		}
		if (isWood(event.getBlock().getType())) {
			new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Lumbering", random.nextInt(3)+1).callEvent();
		} else if (event.getBlock().getBlockData()!=null && event.getBlock().getBlockData() instanceof Ageable) {
			if (((Ageable)event.getBlock().getBlockData()).getMaximumAge()==((Ageable)event.getBlock().getBlockData()).getAge()) {
				new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Farming", 1).callEvent();
				if (SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), "Pixlies:Farming")>=35) {
					event.setDropItems(false);
					Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {

						@Override
						public void run() {
							for (int i = 0; i < (Math.floor(SkillHandler.getSkillHandler().getLevelOf(event.getPlayer().getUniqueId(), "Pixlies:Farming")/75D)+1); i++) {
								for (ItemStack itemStack : event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand())) {
									event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
								}
							}
						}
						
					}, 1l);
				}
			}
		} else {
			new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Mining", random.nextInt(2)+1).callEvent();
		}
	}
	
	private static final Map<UUID, Integer> DISTANCE_MAP = new HashMap<>();
	
	@EventHandler
	public void PlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
		if (event.isCancelled()) return;
		if (event.getStatistic().equals(Statistic.WALK_ONE_CM)) {
			DISTANCE_MAP.put(event.getPlayer().getUniqueId(), DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)+(event.getPreviousValue()-event.getNewValue()));
		}
		if (DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)>=350) {
			SkillXPGainedEvent event2 = new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Traveling", 1);
			event2.callEvent();
			if (!event2.isCancelled()) {
				DISTANCE_MAP.put(event.getPlayer().getUniqueId(), DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)-350);
			}
		}
	}
	
	@EventHandler
	public void PlayerFishEvent(PlayerFishEvent event) {
		if (event.isCancelled()) return;
		if (event.getCaught()!=null) {
			new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Fishing", random.nextInt(5)+1).callEvent();
		}
	}
	
	@EventHandler
	public void EnchantItemEvent(EnchantItemEvent event) {
		if (event.isCancelled()) return;
		new SkillXPGainedEvent(event.getEnchanter(), "Pixlies:Enchanting", (int) (Math.floor(random.nextInt(event.getExpLevelCost())/2)+1)).callEvent();
	}
	
	@EventHandler
	public void EntityDeathEvent(EntityDeathEvent event) {
		if (event.isCancelled()) return;
		new SkillXPGainedEvent(event.getEntity().getKiller(), "Pixlies:Hunting", (int) Math.ceil((event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()/10D))).callEvent();
	}
	
	private boolean isWood(Material material) { return ( material.equals(Material.OAK_LOG) || material.equals(Material.DARK_OAK_LOG) || material.equals(Material.BIRCH_LOG) || material.equals(Material.SPRUCE_LOG) || material.equals(Material.JUNGLE_LOG) || material.equals(Material.ACACIA_LOG) || material.equals(Material.WARPED_STEM) || material.equals(Material.CRIMSON_STEM) ); }
	
}
