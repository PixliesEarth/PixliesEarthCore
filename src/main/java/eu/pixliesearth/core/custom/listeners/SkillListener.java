package eu.pixliesearth.core.custom.listeners;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.*;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
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
		
		event.getPlayer().sendMessage("§8[§a✔§8] §7You have gained a level in §a" + event.getGainedSkillUUID().split(":")[1] + "§7! (§8" + ((int)event.getOldLevel()) + "§7 -> §a"+((int)event.getNewLevel()) + "§7)");
		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

		Profile profile = Main.getInstance().getProfile(event.getPlayer().getUniqueId());
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();

		switch (event.getGainedSkillUUID()) {
			case "Pixlies:Traveling":
				for (int i = 0; i < event.getNewLevel() - event.getOldLevel(); i++) {
					Energy.add(profile, BigDecimal.valueOf((0.1D * (Math.floor(Math.floor((event.getOldLevel() + 1 + i) / 10.0) / 10.0)))).setScale(1, RoundingMode.DOWN).doubleValue());
				}
				break;
			case "Pixlies:Lumbering":
				if (event.getOldLevel() < 10 && event.getNewLevel() >= 10) {
					event.getPlayer().sendMessage("§a[§r✔§a]§r You have unlocked the recipe for " + h.getCustomItemFromClass(ToolLumberAxe.class).getUUID());
				}
				if (event.getOldLevel() < 35 && event.getNewLevel() >= 35) {
					event.getPlayer().sendMessage("§a[§r✔§a]§r You have unlocked the recipe for " + h.getCustomItemFromClass(EnergyToolLumberAxe.class).getUUID());
				}
				break;
			case "Pixlies:Mining":
				if (event.getOldLevel() < 35 && event.getNewLevel() >= 35) {
					event.getPlayer().sendMessage("§a[§r✔§a]§r You have unlocked the recipe for " + h.getCustomItemFromClass(EnergyToolExplosivePickaxe.class).getUUID());
				}
				break;
		}
		if (!event.getGainedSkillUUID().equals("Pixlies:Mining") && !event.getGainedSkillUUID().equals("Pixlies:Building") && profile.isInNation()) {
			Nation nation = profile.getCurrentNation();
			nation.setXpPoints(nation.getXpPoints() + 0.1);
			nation.save();
		}
	}
	
	@EventHandler
	public void SkillXPGainedEvent(SkillXPGainedEvent event) {
		if (event.getAmount()<=0 || event.getGainedSkillUUID()==null) {
			event.setCancelled(true);
			return;
		}
		if (event.isCancelled()) return;

		if (!event.getGainedSkillUUID().equals("Pixlies:Traveling")) event.getPlayer().sendActionBar("§7Gained §a" + event.getAmount() + "§7 " + event.getGainedSkillUUID().split(":")[1] + "§7 XP");

		if (event.getPlayer().getGameMode() == GameMode.CREATIVE ||event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;

		if (event.getPlayer().isFlying()) return;

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
		if (!buildingBlocks.contains(event.getBlock().getType())) return;
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
			if (!miningBlocks.contains(event.getBlock().getType())) return;
			if (!event.getPlayer().getInventory().getItemInMainHand().getType().name().contains("PICKAXE") && !event.getPlayer().getInventory().getItemInOffHand().getType().name().contains("PICKAXE")) return;
			new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Mining", random.nextInt(2)+1).callEvent();
		}
	}
	
	@EventHandler
	public void PlayerMoveEvent(PlayerMoveEvent event) {
		if (((event.getFrom().getBlockX()!=event.getTo().getBlockX()) || (event.getFrom().getBlockZ()!=event.getTo().getBlockZ())) && !event.getPlayer().getLocation().getBlock().getType().equals(Material.WATER) && !event.getPlayer().getLocation().getBlock().getType().equals(Material.LAVA)) {
			DISTANCE_MAP.put(event.getPlayer().getUniqueId(), (DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)+1));
		}
		if (DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)>3) {
			DISTANCE_MAP.put(event.getPlayer().getUniqueId(), DISTANCE_MAP.getOrDefault(event.getPlayer().getUniqueId(), 0)-3);
			new SkillXPGainedEvent(event.getPlayer(), "Pixlies:Traveling", 1).callEvent();
		}
	}
	
	private static final Map<UUID, Integer> DISTANCE_MAP = new HashMap<>();
	
	// @EventHandler
	@Deprecated
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
		if (event.isCancelled() || event.getEntity()==null || event.getEntity().getKiller()==null) return;
		new SkillXPGainedEvent(event.getEntity().getKiller(), "Pixlies:Hunting", (int) Math.ceil((event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()/10D))).callEvent();
	}
	
	private boolean isWood(Material material) { return ( material.equals(Material.OAK_LOG) || material.equals(Material.DARK_OAK_LOG) || material.equals(Material.BIRCH_LOG) || material.equals(Material.SPRUCE_LOG) || material.equals(Material.JUNGLE_LOG) || material.equals(Material.ACACIA_LOG) || material.equals(Material.WARPED_STEM) || material.equals(Material.CRIMSON_STEM) ); }

	private static final List<Material> miningBlocks = new ArrayList<>();

	private static final List<Material> buildingBlocks = new ArrayList<>();

	static {
		// MINING BLOCKS
		miningBlocks.add(Material.STONE);
		miningBlocks.add(Material.COAL_ORE);
		miningBlocks.add(Material.DIAMOND_ORE);
		miningBlocks.add(Material.EMERALD_ORE);
		miningBlocks.add(Material.GOLD_ORE);
		miningBlocks.add(Material.IRON_ORE);
		miningBlocks.add(Material.LAPIS_ORE);
		miningBlocks.add(Material.REDSTONE_ORE);
		miningBlocks.add(Material.NETHER_GOLD_ORE);
		miningBlocks.add(Material.NETHER_QUARTZ_ORE);
		miningBlocks.add(Material.ANDESITE);
		miningBlocks.add(Material.DIORITE);
		miningBlocks.add(Material.GRANITE);
		miningBlocks.add(Material.COBBLESTONE);
		miningBlocks.add(Material.MOSSY_COBBLESTONE);
		miningBlocks.add(Material.OBSIDIAN);

		// BUILDING BLOCKS
		buildingBlocks.add(Material.NETHER_BRICKS);
		buildingBlocks.add(Material.RED_NETHER_BRICKS);
		buildingBlocks.add(Material.CHISELED_NETHER_BRICKS);
		buildingBlocks.add(Material.QUARTZ_BLOCK);
		buildingBlocks.add(Material.CHISELED_QUARTZ_BLOCK);
		buildingBlocks.add(Material.QUARTZ_PILLAR);
		buildingBlocks.add(Material.QUARTZ_BRICKS);
		buildingBlocks.add(Material.HAY_BLOCK);
		for (Material material : Material.values()) {
			if (material.name().contains("CARPET"))
				buildingBlocks.add(material);
			if (material.name().contains("TERRACOTTA"))
				buildingBlocks.add(material);
			if (material.name().contains("CONCRETE"))
				buildingBlocks.add(material);
			if (material.name().contains("WOOD"))
				buildingBlocks.add(material);
			if (material.name().contains("HYPHAE"))
				buildingBlocks.add(material);
			if (material.name().contains("PLANKS"))
				buildingBlocks.add(material);
			if (material.name().contains("LOG"))
				buildingBlocks.add(material);
			if (material.name().contains("STAINED"))
				buildingBlocks.add(material);
			if (material.name().contains("STAIRS"))
				buildingBlocks.add(material);
			if (material.name().contains("SLAB"))
				buildingBlocks.add(material);
			if (material.name().contains("WOOL"))
				buildingBlocks.add(material);
		}
		buildingBlocks.add(Material.PACKED_ICE);
		buildingBlocks.add(Material.GRANITE);
		buildingBlocks.add(Material.POLISHED_GRANITE);
		buildingBlocks.add(Material.DIORITE);
		buildingBlocks.add(Material.POLISHED_DIORITE);
		buildingBlocks.add(Material.SEA_LANTERN);
		buildingBlocks.add(Material.COARSE_DIRT);
		buildingBlocks.add(Material.ANDESITE);
		buildingBlocks.add(Material.POLISHED_ANDESITE);
		buildingBlocks.add(Material.PRISMARINE);
		buildingBlocks.add(Material.PRISMARINE_BRICKS);
		buildingBlocks.add(Material.DARK_PRISMARINE);
		buildingBlocks.add(Material.MAGMA_BLOCK);
		buildingBlocks.add(Material.END_STONE_BRICKS);
		buildingBlocks.add(Material.BONE_BLOCK);
		buildingBlocks.add(Material.NETHER_WART_BLOCK);
		buildingBlocks.add(Material.PURPUR_BLOCK);
		buildingBlocks.add(Material.PURPUR_PILLAR);
		buildingBlocks.add(Material.BLUE_ICE);
		buildingBlocks.add(Material.CONDUIT);
		buildingBlocks.add(Material.DRIED_KELP_BLOCK);
		buildingBlocks.add(Material.EMERALD_BLOCK);
		buildingBlocks.add(Material.STONECUTTER);
		buildingBlocks.add(Material.COAL_BLOCK);
		buildingBlocks.add(Material.POLISHED_BLACKSTONE_BRICKS);
		buildingBlocks.add(Material.CHAIN);
		buildingBlocks.add(Material.NETHERITE_BLOCK);
		buildingBlocks.add(Material.POLISHED_BLACKSTONE);
		buildingBlocks.add(Material.CHISELED_POLISHED_BLACKSTONE);
		buildingBlocks.add(Material.GOLD_BLOCK);
		buildingBlocks.add(Material.BOOKSHELF);
		buildingBlocks.add(Material.DIAMOND_BLOCK);
		buildingBlocks.add(Material.BRICKS);
		buildingBlocks.add(Material.SNOW_BLOCK);
		buildingBlocks.add(Material.CLAY);
		buildingBlocks.add(Material.JACK_O_LANTERN);
		buildingBlocks.add(Material.GLOWSTONE);
		buildingBlocks.add(Material.LAPIS_BLOCK);
		buildingBlocks.add(Material.SANDSTONE);
		buildingBlocks.add(Material.RED_SANDSTONE);
		buildingBlocks.add(Material.CUT_RED_SANDSTONE);
		buildingBlocks.add(Material.CUT_SANDSTONE);
		buildingBlocks.add(Material.CHISELED_SANDSTONE);
		buildingBlocks.add(Material.CHISELED_RED_SANDSTONE);
		buildingBlocks.add(Material.MELON);
		buildingBlocks.add(Material.STONE_BRICKS);
		buildingBlocks.add(Material.MOSSY_STONE_BRICKS);
		buildingBlocks.add(Material.CHISELED_STONE_BRICKS);
		buildingBlocks.add(Material.IRON_BLOCK);
		buildingBlocks.add(Material.MOSSY_COBBLESTONE);
		buildingBlocks.add(Material.CARTOGRAPHY_TABLE);
		buildingBlocks.add(Material.SCAFFOLDING);
	}

}
