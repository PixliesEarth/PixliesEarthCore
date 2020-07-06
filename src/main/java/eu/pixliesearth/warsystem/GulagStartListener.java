package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.CustomItems;
import eu.pixliesearth.events.GulagStartEvent;
import eu.pixliesearth.localization.Lang;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class GulagStartListener implements Listener {


    @EventHandler
    public void onFightStart(GulagStartEvent e){
        Main.getInstance().gulagActive = true;
        Player p = e.getPlayer();
        Player enemy = e.getEnemy();

        if(!p.isOnline()){

            fightOver(p, enemy);
            return;
        }
        if(!enemy.isOnline()){
            fightOver(enemy, p);
            return;
        }
        World world = Bukkit.getWorld("gulag");
        List<Entity> entList = world.getEntities();
        //removes all of the entities in da gulag
        for(Entity current : entList) {
            if (current instanceof Item) {
                current.remove();
            }
        }
        final int[] countdown = {5};
        //countdown in chat
        new BukkitRunnable(){
            @Override
            public void run(){
                if (countdown[0] != 0) {
                    p.sendMessage(Lang.GULAG_COUNTDOWN.get(p).replace("%COUNTER%", String.valueOf(countdown[0])));
                    enemy.sendMessage(Lang.GULAG_COUNTDOWN.get(enemy).replace("%COUNTER%", String.valueOf(countdown[0])));
                    countdown[0]--;
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    enemy.playSound(enemy.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                } else {
                    this.cancel();
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                    enemy.playSound(enemy.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
        //countdown to teleport
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                //TODO: DONT HARDCODE LOCATIONS
                Location spawn1 = new Location(world, 7, 75, -3);
                Location spawn2 = new Location(world, 1, 75, 7);
                p.teleport(spawn1);
                enemy.teleport(spawn2);
                setKit(p, enemy);

        //Capping
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override//countdown to cap
            public void run() {
                if(Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId()) && Main.getInstance().getUtilLists().fightingGulag.containsValue(enemy.getUniqueId())){
                    p.sendActionBar(Lang.GULAG_CAP.get(p));
                    enemy.sendActionBar(Lang.GULAG_CAP.get(enemy));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override//distance calc
                        public void run() {
                            //TODO: Dont hardcode locations
                            World world;
                            if (Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId()) && Main.getInstance().getUtilLists().fightingGulag.containsValue(enemy.getUniqueId())){
                                Location middle = new Location(Bukkit.getWorld("gulag"), 4, 73, 2);
                            if (p.getLocation().distance(middle) > enemy.getLocation().distance(middle)) {
                                fightOver(p, enemy);
                                }else if(p.getLocation().distance(middle) < enemy.getLocation().distance(middle)){
                                fightOver(enemy, p);
                              }else{
                                int random = (int) (1 * Math.random());
                                if(random == 0){
                                    fightOver(p, enemy);
                                }else if(random == 1){
                                    fightOver(enemy, p);
                                }
                            }
                            }
                        }

                    }, 20*10);
                }
            }
        }, 20*60);
            }
        }, 20*5);
        //KILL END IS HANDLED IN DeathListener


    }

   public static void fightOver(Player loser, Player winner) {
      if(loser != null) {
          if (Main.getInstance().getUtilLists().fightingGulag.get(loser.getUniqueId()) != null) {
              Main.getInstance().getUtilLists().fightingGulag.remove(loser.getUniqueId(), winner.getUniqueId());
          } else {
              Main.getInstance().getUtilLists().fightingGulag.remove(winner.getUniqueId(), loser.getUniqueId());
          }
          loser.getInventory().clear();
          loser.teleport(Bukkit.getWorld("world").getSpawnLocation());
          loser.banPlayer("Died in gulag. You will be unbanned after the war.");
          winner.sendMessage(Lang.WON_GULAG.get(winner));
          winner.playSound(winner.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
      }
        Main.getInstance().getUtilLists().wasGulag.add(winner.getUniqueId());


        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                winner.teleport(Bukkit.getWorld("world").getSpawnLocation());
                winner.setHealth(20);
                winner.setFoodLevel(20);
                winner.getInventory().clear();
            }
        }, 20*5);
        Main.getInstance().gulagActive = false;

    }
    private void setKit(Player p, Player player){
        int random = (int) (2 * Math.random());
        switch (random){
            case 0:
                ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                ItemStack pant = new ItemStack(Material.IRON_LEGGINGS);
                ItemStack boot = new ItemStack(Material.IRON_BOOTS);
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack steak = new ItemStack(Material.COOKED_BEEF, 16);
                p.getInventory().setHelmet(helmet);
                p.getInventory().setChestplate(chestplate);
                p.getInventory().setLeggings(pant);
                p.getInventory().setBoots(boot);
                p.getInventory().addItem(steak);
                p.getInventory().addItem(sword);
                player.getInventory().setHelmet(helmet);
                player.getInventory().setChestplate(chestplate);
                player.getInventory().setLeggings(pant);
                player.getInventory().setBoots(boot);
                player.getInventory().addItem(sword);
                player.getInventory().addItem(steak);
                break;
            case 1:
                ItemStack helmet1 = new ItemStack(Material.LEATHER_HELMET);
                ItemStack chestplate1 = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack pant1 = new ItemStack(Material.LEATHER_LEGGINGS);
                ItemStack boot1 = new ItemStack(Material.LEATHER_BOOTS);
                ItemStack rock = new ItemStack(Material.STONE, 15);
                p.getInventory().setHelmet(helmet1);
                p.getInventory().setChestplate(chestplate1);
                p.getInventory().setLeggings(pant1);
                p.getInventory().setBoots(boot1);
                p.getInventory().addItem(rock);
                p.getInventory().addItem(CustomItems.SLINGSHOT.clazz.getRecipe());
                player.getInventory().addItem(CustomItems.SLINGSHOT.clazz.getRecipe());
                player.getInventory().setHelmet(helmet1);
                player.getInventory().setChestplate(chestplate1);
                player.getInventory().setLeggings(pant1);
                player.getInventory().setBoots(boot1);
                player.getInventory().addItem(rock);
                break;
            case 2:
                ItemStack shield = new ItemStack(Material.SHIELD);
                ItemStack stick = new ItemStack(Material.STICK);
                p.getInventory().setItemInOffHand(shield);
                p.getInventory().addItem(stick);
                player.getInventory().setItemInOffHand(shield);
                player.getInventory().addItem(stick);

        }



    }
    public static void startGulag(){
        //TODO: Check if same fac
        Player player = Bukkit.getPlayer(Main.getInstance().getUtilLists().awaitingGulag1.get(0));
        Player player2 = Bukkit.getPlayer(Main.getInstance().getUtilLists().awaitingGulag2.get(0));
        Main.getInstance().getUtilLists().fightingGulag.put(player.getUniqueId(), player2.getUniqueId());
        Bukkit.getPluginManager().callEvent(new GulagStartEvent(player, player2));
        Main.getInstance().getUtilLists().awaitingGulag1.remove(player.getUniqueId());
        Main.getInstance().getUtilLists().awaitingGulag2.remove(player2.getUniqueId());
    }
}
