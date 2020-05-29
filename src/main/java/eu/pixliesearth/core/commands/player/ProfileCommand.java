package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.core.utils.ItemBuilder;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.core.utils.SkullBuilder;
import eu.pixliesearth.localization.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ProfileCommand implements CommandExecutor {

    Profile profile;
    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        player = (Player) sender;
        profile = Main.getInstance().getProfile(player.getUniqueId());

        Gui menu = new Gui(Main.getInstance(), 3, "§e§l"+Lang.YOUR_PROFILE.get(player));
        StaticPane pane = new StaticPane(0, 0, 9, 3);
        // LANGUAGE
        pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Globe\\\"},SkullOwner:{Id:\\\"bd287f02-7b3b-ffd9-c56c-99cb0fafab3b\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19").setDisplayname("§b§o"+Lang.LANGUAGE.get(player)).build(), event ->{
            event.setCancelled(true);
            getLangGui().show(player);
        }), 1, 1);

        // SCOREBOARD
        pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Rainbow Cube\\\"},SkullOwner:{Id:\\\"6154870f-0e62-4c8e-8ee8-f073955be633\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFmZTI3YTEzYzVmYzE3NTE1Y2FlNjk1ODUyNzE2MzI2YjJiNWRmNDdkOGQ2Yjk1YTc4OWFlMzhjYWM3YjEifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFmZTI3YTEzYzVmYzE3NTE1Y2FlNjk1ODUyNzE2MzI2YjJiNWRmNDdkOGQ2Yjk1YTc4OWFlMzhjYWM3YjEifX19").setDisplayname("§b§oScoreboard").build(), event -> {
            event.setCancelled(true);
            getScoreboardGui().show(player);
        }), 3, 1);
        menu.addPane(pane);
        menu.show(player);
        return false;
    }

    private Gui getLangGui() {
        Gui langgui = new Gui(Main.getInstance(), 3, "§b"+Lang.CHOOSE_LANG.get(player));
        StaticPane langpane = new StaticPane(0, 0, 9, 3);
        langpane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Germany\\\"},SkullOwner:{Id:\\\"be211c23-d8aa-4119-bd0d-7f50fd115d9f\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").setDisplayname("§eGerman").build(), e -> {
            e.setCancelled(true);
            profile.setLang("DE");
            profile.save();
            player.closeInventory();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 0, 0);
        langpane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"United States of America\\\"},SkullOwner:{Id:\\\"3c30484a-76d3-4cfe-88e5-e7599bc9ac4d\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").setDisplayname("§eEnglish").build(), e -> {
            e.setCancelled(true);
            profile.setLang("ENG");
            profile.save();
            player.closeInventory();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 1, 0);
        langgui.addPane(langpane);
        return langgui;
    }

    private Gui getScoreboardGui() {
        Gui scoregui = new Gui(Main.getInstance(), 3, "§bScoreboard");
        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.addItem(new GuiItem(new ItemBuilder(Material.MAP).setDisplayName("§7Style: §b" + profile.getBoardType()).build(), event -> {
            event.setCancelled(true);
            switch (ScoreboardAdapter.scoreboardType.valueOf(profile.getBoardType())) {
                case STANDARD:
                    profile.setBoardType(ScoreboardAdapter.scoreboardType.COMPACT.name());
                    profile.save();
                    player.closeInventory();
                    player.sendMessage(Lang.CHANGED_SCOREBOARDTYPE.get(player).replace("%TYPE%", ScoreboardAdapter.scoreboardType.COMPACT.name()));
                    break;
                case COMPACT:
                    profile.setBoardType(ScoreboardAdapter.scoreboardType.STANDARD.name());
                    profile.save();
                    player.closeInventory();
                    player.sendMessage(Lang.CHANGED_SCOREBOARDTYPE.get(player).replace("%TYPE%", ScoreboardAdapter.scoreboardType.STANDARD.name()));
                    break;
            }
        }), 1, 1);
        pane.addItem(new GuiItem(new ItemBuilder(Methods.getSBWoolByCC(profile.getFavoriteColour())).setDisplayName("§7Favorite color").build(), event -> {
            event.setCancelled(true);
            Gui menu = new Gui(Main.getInstance(), 3, "§b"+ Lang.CHOOSE_COLOUR.get(player));
            StaticPane pane2 = new StaticPane(0, 0, 9, 3);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.LIGHT_BLUE_WOOL).setDisplayName("§bAqua").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§b");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§bAQUA"));
            }), 0, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.RED_WOOL).setDisplayName("§cRed").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§c");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§cRED"));
            }), 1, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.YELLOW_WOOL).setDisplayName("§eYellow").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§e");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§eYELLOW"));
            }), 2, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.GREEN_WOOL).setDisplayName("§aGreen").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§a");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§aGREEN"));
            }), 3, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.CYAN_WOOL).setDisplayName("§3Blue").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§3");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§3BLUE"));
            }), 4, 0);
            menu.addPane(pane2);
            menu.show(player);
        }), 3, 1);
        scoregui.addPane(pane);
        return scoregui;
    }

}
