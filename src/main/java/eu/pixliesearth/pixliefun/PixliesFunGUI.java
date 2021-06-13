package eu.pixliesearth.pixliefun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import eu.pixliesearth.core.custom.commands.CIControl;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import lombok.Data;

/**
 * @author MickMMars
 * this is the class for the Mick version of the PixlieFun GUI
 */
@Data
public class PixliesFunGUI implements Constants {

    public static final Map<String, List<CustomRecipe>> recipes = new HashMap<>();

    private Player player;
    private ChestGui gui;

    public PixliesFunGUI(Player player) {
        this.player = player;
    }

    public void open() {
        if (gui == null || gui.getInventory().getItem(0) == null || gui.getInventory().getItem(0).getType() == Material.AIR) {
            renderMainMenu();
            return;
        }
        gui.show(player);
    }

    private void renderMainMenu() {
        gui = new ChestGui(6, "§b§lPixliesFun");
        gui.getPanes().clear();

        StaticPane background = new StaticPane(0, 0, 9, 6, Pane.Priority.LOW);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setCustomModelData(3).setNoName().build(), e -> e.setCancelled(true));
        background.addItem(new GuiItem(
                new ItemBuilder(Material.COMPASS).setDisplayName("§e§lSearch").addLoreLine("§7Search for items").build(),
                e -> {
                    e.setCancelled(true);
                    AnvilGui searchGui = new AnvilGui("§e§lSearch items");

                    StaticPane resultPane = new StaticPane(0, 0, 1, 1, Pane.Priority.HIGHEST);
                    resultPane.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§eSearch").build(), rIE -> {
                        rIE.setCancelled(true);
                        if (!searchGui.getRenameText().isEmpty()) renderSearchItems(searchGui.getRenameText());
                    }), 0, 0);
                    searchGui.getSecondItemComponent().addPane(resultPane);

                    StaticPane firstItemPane = new StaticPane(0, 0, 1, 1, Pane.Priority.HIGHEST);
                    firstItemPane.addItem(new GuiItem(new ItemBuilder(Material.STICK).setNoName().build(), fIE -> fIE.setCancelled(true)), 0, 0);
                    searchGui.getFirstItemComponent().addPane(firstItemPane);

                    searchGui.show(player);
                }
        ), 4, 0);
        gui.addPane(background);

        StaticPane background2 = new StaticPane(0, 1, 9, 4, Pane.Priority.NORMAL);
        background2.fillWith(new ItemStack(Material.AIR));
        gui.addPane(background2);

        PaginatedPane categories = new PaginatedPane(0, 1, 9, 4, Pane.Priority.HIGH);
        List<GuiItem> categoryItems = new ArrayList<>();
        for (CustomItem.Category category : CustomItem.Category.values()) {
            categoryItems.add(new GuiItem(new ItemBuilder(CustomItemUtil.getItemStackFromUUID(category.getIcon())).setDisplayName(category.getName()).addLoreLine("§f§lLEFT §7click to open").build(), event -> {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
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
                if (StringUtils.containsIgnoreCase(ChatColor.stripColor(CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(s).getDefaultDisplayName()), keyWord))
                    returner.add(s);
        return returner;
    }

    public void renderSearchItems(String keyWord) {
        gui = new ChestGui(6, "§b§lPixliesFun §8| §b" + keyWord);
        gui.getPanes().clear();

        StaticPane background = new StaticPane(0, 0, 9, 6, Pane.Priority.LOW);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setCustomModelData(3).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background);

        StaticPane background2 = new StaticPane(0, 1, 9, 4, Pane.Priority.NORMAL);
        background2.fillWith(new ItemStack(Material.AIR));
        gui.addPane(background2);

        PaginatedPane entriesPane = new PaginatedPane(0, 1, 9, 4, Pane.Priority.HIGH);
        List<GuiItem> entries = new ArrayList<>();
        final List<String> itemsToRender = searchItems(keyWord);
        for (String s : itemsToRender) {
            if (s.contains("test")) continue;
            if (s.equalsIgnoreCase(" ")) continue;
            if (CIControl.DISABLED_ITEMS.contains(s)) continue;
            if (s.contains("_UNB")) continue;
            ItemStack i = CustomItemUtil.getItemStackFromUUID(s);
            if (i == null) continue;
            entries.add(new GuiItem(new ItemBuilder(i).addLoreLine(" ").addLoreLine("§f§lLEFT §7click to show recipe").build(), event -> {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                renderRecipe(i, 0);
            }));
        }
        entriesPane.populateWithGuiItems(entries);
        gui.addPane(entriesPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1, Pane.Priority.HIGHEST);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setCustomModelData(2).setDisplayName("§c§lClose").build(), e -> {
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
        if (category == null) {
            renderMainMenu();
            return;
        }
        gui = new ChestGui(6, "§b§lPixliesFun §8| " + category.getName());

        StaticPane background = new StaticPane(0, 0, 9, 6, Pane.Priority.LOW);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setCustomModelData(3).setNoName().build(), e -> e.setCancelled(true));
        gui.addPane(background);

        StaticPane background2 = new StaticPane(0, 1, 9, 4, Pane.Priority.NORMAL);
        background2.fillWith(new ItemStack(Material.AIR));
        gui.addPane(background2);

        PaginatedPane entriesPane = new PaginatedPane(0, 1, 9, 4, Pane.Priority.HIGH);
        List<GuiItem> entries = new ArrayList<>();
        for (String s : CustomFeatureLoader.getLoader().getHandler().getCategoriesForItems().get(category)) {
            if (s.contains("test")) continue;
            if (s.equalsIgnoreCase(" ")) continue;
            if (CIControl.DISABLED_ITEMS.contains(s)) continue;
            if (s.contains("_UNB")) continue;
            ItemStack i = CustomItemUtil.getItemStackFromUUID(s);
            if (i == null) continue;
            entries.add(new GuiItem(new ItemBuilder(i).addLoreLine(" ").addLoreLine("§f§lLEFT §7click to show recipe").build(), event -> {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                renderRecipe(i, 0);
            }));
        }
        entriesPane.populateWithGuiItems(entries);
        gui.addPane(entriesPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1, Pane.Priority.HIGHEST);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setCustomModelData(2).setDisplayName("§c§lClose").build(), e -> {
            e.setCancelled(true);
            renderMainMenu();
        }), 4, 0);
        if (entriesPane.getPages() > 1) {
            hotBar.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() + 1);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 8, 0);
            hotBar.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    entriesPane.setPage(entriesPane.getPage() - 1);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
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
            if (recipes.get(getId(i)).get(page) == null) return;

            gui = new ChestGui(6, "§b§lRecipe");

            CustomRecipe recipe = recipes.get(getId(i)).get(page);

            gui.getPanes().clear();

            StaticPane background = new StaticPane(0, 0, 9, 6, Pane.Priority.LOW);
            background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setCustomModelData(3).setNoName().build(), e -> e.setCancelled(true));
            background.addItem(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setCustomModelData(4).setDisplayName("§aResult").build(), event -> {
                event.setCancelled(true);
            }), 5, 2);
            gui.addPane(background);

            PaginatedPane recipePane = new PaginatedPane(1, 1, 3, 3, Pane.Priority.NORMAL);
            List<GuiItem> ingredients = new ArrayList<>();
            // Loop through all ingredients and set them to their slots
            for (String s : recipe.getContentsList().values()) {
                ItemStack ingredient = s == null ? new ItemStack(Material.AIR) : getItem(s);
                // If Item is not null, apply unclickable UUID to it
                ingredients.add(new GuiItem(ingredient, e -> {
                    e.setCancelled(true);
                    try {
                        renderRecipe(ingredient, 0);
                        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                    } catch (Exception ignore) {
                    }
                }));
            }
            // Add all ingredients to the pane
            recipePane.populateWithGuiItems(ingredients);
            gui.addPane(recipePane);

            StaticPane result = new StaticPane(7, 2, 1, 1, Pane.Priority.HIGH);
            result.addItem(new GuiItem(new ItemBuilder(getItem(recipe.getResultUUID())).setAmount(recipe.getResultAmount()).build(), e -> {
                e.setCancelled(true);
                if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()) && e.isLeftClick())
                    player.getInventory().addItem(getItem(recipe.getResultUUID()));
            }), 0, 0);
            gui.addPane(result);

            StaticPane hotBar = new StaticPane(0, 5, 9, 1, Pane.Priority.HIGHEST);
            hotBar.addItem(new GuiItem(getItem(recipe.craftedInUUID()), e -> {
                e.setCancelled(true);
                renderRecipe(getItem(recipe.craftedInUUID()), 0);
            }), 2, 0);
            hotBar.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    renderRecipe(i, page + 1);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                } catch (Exception ignore) {
                }
            }), 5, 0);
            hotBar.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    renderRecipe(i, page - 1);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
                } catch (Exception ignore) {
                }
            }), 3, 0);
            hotBar.addItem(new GuiItem(new ItemBuilder(Material.BARRIER).setCustomModelData(2).setDisplayName("§c§lClose").build(), event -> {
                event.setCancelled(true);
                renderCategoryMenu(CustomFeatureLoader.getLoader().getHandler().getItemsForCategories().get(recipe.getResultUUID()));
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
            }), 4, 0);
            long craftTime = recipe.getCraftTime() == null ? 0 : recipe.getCraftTime();
            hotBar.addItem(new GuiItem(new ItemBuilder(Material.CLOCK).setDisplayName("§b§lCraft-time").addLoreLine("§3" + (craftTime / 1000) + "s").build(), event -> {
                event.setCancelled(true);
            }), 6, 0);
            if (recipe.getEnergyCost() != null) hotBar.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/f4f21cf5c234fc96db90a0a311d6fbe12f8789b7fa8155716757fd516b1811")).setDisplayName("§eEnergy needed").addLoreLine("§6" + Methods.convertEnergyDouble(recipe.getEnergyCost())).build(), event -> {
                event.setCancelled(true);
            }), 7, 0);
            if (recipe.getProbability() != null) hotBar.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/3d143dbdcc9f4e377e34f36e316bd19df634c59b3bf93a69ab21d95b2fd57b")).setDisplayName("§bProbability").addLoreLine("§3" + (recipe.getProbability() * 10) + "%").build(), event -> {
                event.setCancelled(true);
            }), 1, 0);
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
