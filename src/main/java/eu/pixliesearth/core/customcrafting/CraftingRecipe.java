package eu.pixliesearth.core.customcrafting;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CraftingRecipe implements Listener {

    HashMap<String, Gear> recipes = new HashMap<String, Gear>();
    public CraftingRecipe(){}
    public CraftingRecipe(Main main) { // < --------------------------------------------------- CHANGE THIS TO YOUR MAIN CLASS
        GearManagement manager = new GearManagement();
        new CraftingListeners(main);

        for (Gear gear : manager.getGear()) {
            if (gear.getRecipes() == null) continue;
            for (String s : gear.getRecipes()) {
                recipes.put(s, gear);
            }
        }
    }

    public String getResult(Inventory inv, String grid) {
        //LinkedHashMap<String, Gear> minSearch = recipes.get(length); //sorts by length to make search time faster. Linked because larger recipes take precedence
        //	if (minSearch == null) return;
        String rec = null;

        for (String recipe : recipes.keySet()) {
            if (grid.contains(recipe)) {
                rec = recipe;
                inv.setItem(24, recipes.get(recipe).getItem());
                for (int i : Const.BOTTOM)	inv.setItem(i, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").asItem());

                break;
            }
        }
        if (rec == null) {
            for (int i : Const.BOTTOM)	inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").asItem());
            inv.setItem(24, new ItemBuilder(Material.BARRIER).setName("&cRecipe Required").setLore(new ArrayList<String>(Arrays.asList("&7Add the items for a valid", "&7recipe in the crafting grid", "&7to the left!"))).asItem());
        }

        inv.setItem(49, new ItemBuilder(Material.BARRIER).setName("&cClose").asItem());

        return rec;
    }
}
class GearManagement {

    private Set<Gear> gear = new HashSet<Gear>();

    public GearManagement() {
       // this.gear.add(new Stone());
    }

    public Set<Gear> getGear() {
        return Collections.unmodifiableSet(gear);
    }

    public Gear getGear(String name) {
        return gear.stream().filter(f-> f.getName().equals(name)).findFirst().orElse(null);
    }
}
class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material mat) {
        itemStack = new ItemStack(mat, 1);

        ItemMeta iM = itemStack.getItemMeta();
        iM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(iM);
    }

    public ItemBuilder setType(Material mat) {
        itemStack.setType(mat);
        return this;
    }

    public ItemBuilder setName(String display) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display));

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> s = new ArrayList<String>();
        lore.forEach(l -> s.add(ChatColor.translateAlternateColorCodes('&', l)));

        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(s);

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack asItem() {
        return itemStack;
    }
}
class Const {

    private Const() { }

    public static final String CRAFTING_NAME = "Craft Item";
    public static final int[] SLOTS = new int[]{ 10,11,12,19,20,21,28,29,30 };
    public static final int[] V_SLOTS = new int[]{ 80, 81, 82, 83 };
    public static final int[] BOTTOM = new int[]{ 53, 52, 51, 50, 49, 48, 47, 46, 45 };
    public static final List<Material> BLACKLIST = Arrays.asList( Material.IRON_BOOTS, Material.IRON_AXE );  //Where you can blacklist items
}
class CraftingListeners implements Listener {

    private Main main; // <---------------------------------------------------- CHANGE THIS TO YOUR MAIN CLASS
    private HashMap<Player, String> map;

    public CraftingListeners(Main main) { // <--------------------------------- CHANGE THIS TO YOUR MAIN CLASS
        this.main = main;
        this.map = new HashMap<Player, String>();

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onCraftingTableClick(PlayerInteractEvent e) {

        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.CRAFTING_TABLE) return;

        if (e.getPlayer().isSneaking())return;

        e.setCancelled(true);
        Inventory inv = Bukkit.createInventory(e.getPlayer(), 54, "Craft Item");

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").asItem());
        }

        for (int i : Const.SLOTS) {
            inv.setItem(i, null); }

        for (int i : Const.BOTTOM) {
            inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").asItem());
        }

        inv.setItem(49, new ItemBuilder(Material.BARRIER).setName("&cClose").asItem());
        inv.setItem(24, new ItemBuilder(Material.BARRIER).setName("&cRecipe Required").setLore(new ArrayList<String>(Arrays.asList("&7Add the items for a valid", "&7recipe in the crafting grid", "&7to the left!"))).asItem());
        e.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();

        if (!e.getView().getTitle().equals(Const.CRAFTING_NAME)) return;

        for (int i : Const.SLOTS) {
            if (inv.getItem(i) == null) continue;

            e.getPlayer().getInventory().addItem(inv.getItem(i));
        }
    }

    @EventHandler
    public void onVCraft(InventoryClickEvent e) {
        if (e.getInventory() != e.getWhoClicked().getInventory()) return;

        Player p = (Player) e.getWhoClicked();
    }

    @EventHandler
    public void onVDrag(InventoryDragEvent e) {
        if (e.getInventory() != e.getWhoClicked().getInventory()) return;

        Player p = (Player) e.getWhoClicked();
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getInventory() == null) return;
        if (!e.getView().getTitle().equals(Const.CRAFTING_NAME)) return;

        Inventory inv = e.getInventory();

        Player p = (Player) e.getWhoClicked();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (map.containsKey(p)) {
                    map.remove(p);
                }
                map.put(p, main.getCraftingAPI().getResult(e.getInventory(), getGrid(inv, Const.SLOTS)));
            }
        }.runTaskLater(main, 1L);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(Const.CRAFTING_NAME)) return;
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();

        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;

        if (inv.getItem(24) != null && e.getCurrentItem() != null) {
            if (!Objects.equals(inv.getItem(24), e.getCurrentItem()) && e.getCurrentItem().isSimilar(inv.getItem(24)) && e.isShiftClick()) {
                e.setCancelled(true);
                return;
            }
        }

        if (e.getView().getTitle().equals(Const.CRAFTING_NAME) && Objects.equals(e.getClickedInventory(), e.getView().getTopInventory())) {
            boolean c = false;
            List<String> recipe = new ArrayList<String>();

            if ((e.getSlot() == 24 && inv.getItem(e.getSlot()).getType() != Material.BARRIER)) {
                e.setCancelled(true);
                if (e.getCursor().isSimilar(e.getCurrentItem())) {
                    int amount = e.getCursor().getAmount();
                    int curAmmount = e.getCurrentItem().getAmount();

                    e.setCursor(e.getCurrentItem());
                    e.getCursor().setAmount(amount + curAmmount);
                }
                else {
                    if (e.getCursor() != null) {
                        p.getInventory().addItem(e.getCursor());
                    }
                    e.setCursor(e.getCurrentItem());
                }
                if (map.get(p) != null) {
                    c = true;
                    recipe = Arrays.asList(map.get(p).split(""));
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(recipe.size()) + "size");
                }
            }
            int in = 0;
            for (int i : Const.SLOTS) {
                if (c) {
                    if (!recipe.get(in).equals("-") && !recipe.get(in).equals("0")) {
                        if (inv.getItem(i) == null) continue;
                        if (inv.getItem(i).getAmount() - 1 <= 0) {
                            inv.clear(i);
                        }
                        else {
                            Bukkit.getConsoleSender().sendMessage(" minus 1");
                            inv.getItem(i).setAmount(inv.getItem(i).getAmount() - 1);
                        }
                    }
                    in++;
                }
                if (e.getSlot() == i) {
                    e.setCancelled(false);
                    break;
                }
            }
        }
        else {
            e.setCancelled(false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (map.containsKey(p)) {
                    map.remove(p);
                }
                map.put(p, main.getCraftingAPI().getResult(e.getInventory(), getGrid(inv, Const.SLOTS)));
            }
        }.runTaskLater(main, 1L);
    }

    @EventHandler
    public void onCloseMap(InventoryCloseEvent e) {
        if (map.keySet().contains(e.getPlayer())) {
            map.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (map.keySet().contains(e.getPlayer())) {
            map.remove(e.getPlayer());
        }
    }
    private String getGrid(Inventory inv, int[] slots) {
        StringBuilder recipe = new StringBuilder();

        for (int i : slots) {
            if (inv.getItem(i) == null) {
                recipe.append("-");
                continue;
            }

            if (Const.BLACKLIST.contains(inv.getItem(i).getType())) {
                recipe.append("0");
                continue;
            }
            //TODO: Check if item is custom or not? Does hypixel even check for this?
            recipe.append(inv.getItem(i).getType().toString());
        }
        return (recipe != null) ? recipe.toString() : null;
    }
}