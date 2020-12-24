package eu.pixliesearth.pixliefun;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.*;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MickMMars
 * this is the class for the Mick version of the PixlieFun GUI
 */
@Data
public class PixliesFunGUI implements Constants {

    public static final Map<String, List<CustomRecipe>> recipes = new HashMap<>();

    private Player player;
    private Gui gui;

    public PixliesFunGUI(Player player) {
        this.player = player;
    }

    public void open() {
        gui = new Gui(Main.getInstance(), 6,"§b§lPixliesFun");
        renderMainMenu();
    }

    private void renderMainMenu() {
        gui = new Gui(Main.getInstance(), 6, "§b§lPixliesFun");
        gui.getPanes().clear();

        StaticPane background = new StaticPane(0, 0, 9, 6);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background);

        StaticPane background2 = new StaticPane(1, 1, 7, 4);
        background2.fillWith(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background2);

        PaginatedPane categories = new PaginatedPane(1, 1, 7, 4);
        List<GuiItem> categoryItems = new ArrayList<>();
        for (CustomItem.Category category : CustomItem.Category.values()) {
            categoryItems.add(new GuiItem(new ItemBuilder(CustomItemUtil.getItemStackFromUUID(category.getIcon())).setDisplayName(category.getName()).addLoreLine("§f§lLEFT §7click to open").build(), event -> {
                event.setCancelled(true);
                renderCategoryMenu(category);
            }));
        }
        categories.populateWithGuiItems(categoryItems);
        gui.addPane(categories);
        gui.show(player);
    }

    public List<String> searchItems(String keyWord) {
        List<String> returner = new ArrayList<>();
        CustomFeatureHandler handler = CustomFeatureLoader.getLoader().getHandler();
        for (CustomItem.Category category : CustomItem.Category.values())
            for (String s : handler.getCategoriesForItems().get(category))
                if (s.matches("(?i)(" + keyWord + ").*"))
                    returner.add(s);
        return returner;
    }

    public void renderSearchItems(String keyWord) {
        gui = new Gui(Main.getInstance(), 6, "§b§lPixliesFun §8| §b" + keyWord);

        StaticPane background = new StaticPane(0, 0, 9, 6);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background);

        StaticPane background2 = new StaticPane(1, 1, 7, 4);
        background2.fillWith(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background2);

        PaginatedPane entriesPane = new PaginatedPane(1, 1, 7, 4);
        List<GuiItem> entries = new ArrayList<>();
        List<String> itemsToRender = searchItems(keyWord);
        for (String s : itemsToRender) {
            if (s.contains("test")) continue;
            ItemStack i = CustomItemUtil.getItemStackFromUUID(s);
            if (i == null) continue;
            entries.add(new GuiItem(new ItemBuilder(i).addLoreLine(" ").addLoreLine("§f§lLEFT §7click to show recipe").build(), event -> {
                event.setCancelled(true);
                renderRecipe(i, 0);
            }));
        }
        entriesPane.populateWithGuiItems(entries);
        gui.addPane(entriesPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setDisplayName("§c§lClose").build(), e -> {
            e.setCancelled(true);
            renderMainMenu();
        }), 4, 0);
        if (entriesPane.getPages() > 1) {
            hotBar.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() + 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 8, 0);
            hotBar.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() - 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 0, 0);
        }
        gui.addPane(hotBar);
        gui.show(player);
    }

    private void renderCategoryMenu(CustomItem.Category category) {
        gui = new Gui(Main.getInstance(), 6, "§b§lPixliesFun §8| " + category.getName());

        StaticPane background = new StaticPane(0, 0, 9, 6);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background);

        StaticPane background2 = new StaticPane(1, 1, 7, 4);
        background2.fillWith(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background2);

        PaginatedPane entriesPane = new PaginatedPane(1, 1, 7, 4);
        List<GuiItem> entries = new ArrayList<>();
        for (String s : CustomFeatureLoader.getLoader().getHandler().getCategoriesForItems().get(category)) {
            if (s.contains("test")) continue;
            ItemStack i = CustomItemUtil.getItemStackFromUUID(s);
            if (i == null) continue;
            entries.add(new GuiItem(new ItemBuilder(i).addLoreLine(" ").addLoreLine("§f§lLEFT §7click to show recipe").build(), event -> {
                event.setCancelled(true);
                renderRecipe(i, 0);
            }));
        }
        entriesPane.populateWithGuiItems(entries);
        gui.addPane(entriesPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setDisplayName("§c§lClose").build(), e -> {
            e.setCancelled(true);
            renderMainMenu();
        }), 4, 0);
        if (entriesPane.getPages() > 1) {
            hotBar.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() + 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 8, 0);
            hotBar.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() - 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 0, 0);
        }
        gui.addPane(hotBar);
        gui.show(player);
    }

    private void renderRecipe(ItemStack i, int page) {
        try {
            if (!recipes.containsKey(getId(i))) return;
            if (recipes.get(getId(i)).size() < page - 1) return;
            if (page < 0) return;

            gui = new Gui(Main.getInstance(), 6, "§b§lRecipe");

            CustomRecipe recipe = recipes.get(getId(i)).get(page);

            gui.getPanes().clear();

            StaticPane background = new StaticPane(0, 0, 9, 6);
            background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
            gui.addPane(background);

            PaginatedPane recipePane = new PaginatedPane(1, 1, 3, 3);
            List<GuiItem> ingredients = new ArrayList<>();
            // Loop through all ingredients and set them to their slots
            for (String s : recipe.getContentsList().values()) {
                ItemStack ingredient = s == null ? new ItemStack(Material.AIR) : getItem(s);
                // If Item is not null, apply unclickable UUID to it
                ingredients.add(new GuiItem(ingredient, e -> {
                    e.setCancelled(true);
                    try {
                        renderRecipe(ingredient, 0);
                    } catch (Exception ignore) {
                    }
                }));
            }
            // Add all ingredients to the pane
            recipePane.populateWithGuiItems(ingredients);
            gui.addPane(recipePane);

            StaticPane result = new StaticPane(7, 2, 1, 1);
            result.addItem(new GuiItem(getItem(recipe.getResultUUID()), e -> {
                e.setCancelled(true);
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()) && e.isLeftClick())
                    player.getInventory().addItem(getItem(recipe.getResultUUID()));
            }), 0, 0);
            gui.addPane(result);

            StaticPane hotBar = new StaticPane(0, 5, 9, 1);
            hotBar.addItem(new GuiItem(getItem(recipe.craftedInUUID()), e -> e.setCancelled(true)), 2, 0);
            hotBar.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    renderRecipe(i, page + 1);
                } catch (Exception ignore) {
                }
            }), 5, 0);
            hotBar.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    renderRecipe(i, page - 1);
                } catch (Exception ignore) {
                }
            }), 3, 0);
            hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setDisplayName("§c§lClose").build(), event -> {
                event.setCancelled(true);
                renderMainMenu();
            }), 4, 0);
            long craftTime = recipe.getCraftTime() == null ? 0 : recipe.getCraftTime();
            hotBar.addItem(new GuiItem(new ItemBuilder(Material.CLOCK).setDisplayName("§b§lCraft-time").addLoreLine("§3" + (craftTime / 1000) + "s").build(), event -> {
                event.setCancelled(true);
            }), 6, 0);
            gui.addPane(hotBar);

            gui.show(player);
        } catch (Exception ignored) { }
    }

    private String getId(ItemStack i) {
        return CustomItemUtil.getUUIDFromItemStack(i);
    }

    private ItemStack getItem(String s) {
        return CustomItemUtil.getItemStackFromUUID(s);
    }

}
