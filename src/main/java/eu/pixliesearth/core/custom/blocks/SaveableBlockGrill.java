package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.CustomSaveableBlock;
import eu.pixliesearth.core.custom.interfaces.IRecipeable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;

public class SaveableBlockGrill extends CustomSaveableBlock implements IRecipeable {

    public SaveableBlockGrill() {

    }
    
    @Override
    public String getUUID() {
        return "Pixlies:Grill";
    }

    @Override
    public Material getMaterial() {
        return Material.LOOM;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Grill";
    }

    @Override
    public void onTick(Location location, Inventory inventory, Timer timer) {
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        if (timer==null) {
        	if (inventory.isEmpty()) { // Not cooking anything
        		h.registerTimer(location, new Timer(250L));
        	} else {
        		Set<CustomRecipe> rs = h.getRecipesFromUUID(getUUID()); // Get all recipes for the grill
        		Map<String, Integer> map = new HashMap<>();
        		ItemStack is;
        		String s;
        		int i2 = 0;
        		for (int i = 0; i < 8; i++) {
        			is = inventory.getItem(i);
        			if (is==null || is.getType().equals(Material.AIR)) continue;
        			s = CustomItemUtil.getUUIDFromItemStack(is);
        			if (s==null || s.equalsIgnoreCase("")) continue;
        			if (map.containsKey(s)) {
        				i2 = map.get(s);
        				map.put(s, i2+is.getAmount());
        			} else {
        				map.put(s, is.getAmount());
        			}
        		}
        		for (CustomRecipe r : rs) {
        			if (craft(r, map, inventory, location)) {
        				h.registerTimer(location, new Timer((r.getCraftTime()==null) ? 250L : r.getCraftTime()));
        				inventory.setItem(8, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
        				return;
        			}
        		}
        		h.registerTimer(location, new Timer(250L));
        	}
        } else {
        	if (timer.hasExpired()) {
        		h.unregisterTimer(location);
        	} else {
        		// Do nothing
        		if (Math.random()<=0.35D) 
        			makeAnimationAt(location, Effect.SMOKE, 1);
        	}
        }
        /*if (timer != null) {
            if (timer.hasExpired()) {
                h.unregisterTimer(location);
                Bukkit.getScheduler().runTask(h.getInstance(), () -> {
                    h.getHologramAtLocation(location).clearLines();
                    h.getHologramAtLocation(location).insertItemLine(0, Grillable.getByFrom(inventory.getItem(0)).getTo());
                    h.getHologramAtLocation(location).insertTextLine(1, "§aCooked!");
                });
                // Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), () -> location.getWorld().dropItemNaturally(location, Grillable.getByFrom(inventory.getItem(0)).getTo()), 0L);
            } else {
                // makeParticeAt(location.clone(), Particle.CAMPFIRE_COSY_SMOKE, 1);
                Bukkit.getScheduler().runTask(h.getInstance(), () -> {
                    h.getHologramAtLocation(location).removeLine(1);
                    h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(timer.getRemaining(), false));
                });
            }
        }*/
    }
    
    public boolean craft(CustomRecipe recipe, Map<String, Integer> items, Inventory inv, Location location) {
    	Map<String, Integer> leftovers = new ConcurrentHashMap<>();
    	Map<String, Integer> recipeMap = recipe.getAsUUIDToAmountMap();
    	for (String key : recipeMap.keySet()) {
    		if (!items.containsKey(key)) return false;
    		if (recipeMap.get(key)>items.get(key)) return false;
    		if ((items.get(key)-recipeMap.get(key))>0)leftovers.put(key, items.get(key)-recipeMap.get(key));
    	}
    	for (String key : items.keySet()) {
    		if (!recipeMap.containsKey(key)) leftovers.put(key, items.get(key));
    	}
    	inv.clear();
    	for (String key : leftovers.keySet()) {
    		inv.addItem(new ItemBuilder(CustomItemUtil.getItemStackFromUUID(key)).setAmount(leftovers.get(key)).build());
    	}
    	Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < recipe.getResultAmount(); i++) 
					location.getWorld().dropItemNaturally(location, CustomItemUtil.getItemStackFromUUID(recipe.getResultUUID()));
				inv.clear(8);
			}
		}, ((recipe.getCraftTime()/1000L)*20L)+3L); // Convert the craft time to ticks and add 3
    	return true;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 9, getInventoryTitle());
    }

    @Override
    public void open(Player player, Location location) {
    	Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
    		@Override
			public void run() {
				player.closeInventory();
			}
		}, 1L);
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	Inventory inv = h.getInventoryFromLocation(location);
        if (player.isSneaking()) {
        	// Take items
        	if (inv.getItem(8)==null || inv.getItem(8).getType().equals(Material.AIR)) {
        		// Not doing anything
        		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
        			@Override
        			public void run() {
        				for (int i = 0; i < 8; i++) {
        					if (inv.getItem(i)!=null) {
	        					location.getWorld().dropItemNaturally(location, inv.getItem(i));
	        					inv.clear(i);
	        				}
        				}
        			}
        		}, 1L);
        	} else {
        		// Cooking
        		player.sendMessage("The contents are being cooked! You cannot take them out!");
			}
        } else {
        	// Add items
        	if (inv.firstEmpty()==-1 || inv.firstEmpty()>=9) {
        		// Full
        		player.sendMessage("Its too full to add anything else!");
			} else {
        		// Has room
        		inv.addItem(player.getInventory().getItemInMainHand().asOne());
        		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
        	}
        }
        /*CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        ItemStack item = h.getInventoryFromLocation(location).getItem(0);
        if (item == null || item.getType().equals(Material.AIR)) {
        	
            for (Grillable g : Grillable.values())
                if (player.getInventory().getItemInMainHand().getType().equals(g.getFrom().getType())) {
                    if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                        player.getInventory().setItemInMainHand(player.getInventory().getItemInMainHand().asQuantity(player.getInventory().getItemInMainHand().getAmount() - 1));
                    } else {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                    h.registerHologramAtLocation(location, createHologram(g.getFrom(), location));
                    h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(g.getTime(), false));
                    h.getInventoryFromLocation(location).setItem(0, g.getFrom());
                    h.registerTimer(location, new Timer(g.getTime()));
                    break;
                }
        } else {
            player.getInventory().addItem(Grillable.getByFrom(item).getTo());
            h.getHologramAtLocation(location).clearLines();
        }*/
    }
    
    public void makeParticeAt(Location loc, Particle p, int amount) {
    	Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
    		@Override
    		public void run() {
    			// Random random = new Random();
    			// loc.getWorld().spawnParticle(p, loc.getX(), loc.getY(), loc.getZ(), amount, 0D, 0.75D, 0D, 0.05D);
				loc.getWorld().spawnParticle(p, loc, amount, 0.0D, 0.005D, 0.0D);
				// loc.getWorld().spawnParticle(p, loc, amount);
    		}
    	}, 0L);
    }

	public void makeAnimationAt(Location loc, Effect p, int amount) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			@Override
			public void run() {
				loc.getWorld().playEffect(loc, p, amount);
			}
		}, 0L);
	}

	@Override
	public Inventory getCraftingExample(CustomRecipe customRecipe) {
		Inventory inv = Bukkit.createInventory(null, 6*9, craftingExampleTitle);
		for (int i = 0; i < 6*9; i++) 
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		int[] ints = {10,11,12,13,19,20,21,22,28,29,30,31};
		for (Entry<String, Integer> entry : customRecipe.getAsUUIDToAmountMap().entrySet()) {
			for (int i2 : ints) {
				if (inv.getItem(i2)==null || inv.getItem(i2).getType().equals(Material.AIR) || CustomItemUtil.getUUIDFromItemStack(inv.getItem(i2)).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) {
					int i = entry.getValue();
					if (i>64) {
						while (i!=0) {
							if (i>64) {
								inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(64).build());
								i -= 64;
							} else {
								inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(i).build());
								i = 0;
							}
						}
					} else {
						inv.setItem(i2, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(entry.getKey())).setAmount(i).build());
					}
					break;
				}
			}
		}
		inv.setItem(24, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID())).setAmount(customRecipe.getResultAmount()).build());
		inv.setItem(48, backItem); // Back
		inv.setItem(49, closeItem); // Close
		inv.setItem(50, nextItem); // Next
		inv.setItem(recipeItemSlot, CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID()));
		return inv;
	}

}
