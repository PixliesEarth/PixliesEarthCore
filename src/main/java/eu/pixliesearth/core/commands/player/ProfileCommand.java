package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileCommand implements CommandExecutor {

    Profile profile;
    Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
                return false;
            }
            player = (Player) sender;
            profile = Main.getInstance().getProfile(player.getUniqueId());

            Gui menu = new Gui(Main.getInstance(), 3, "§e§l" + Lang.YOUR_PROFILE.get(player));
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            // LANGUAGE
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4d48e75ff55cb57533c7b904be887a374925f93832f7ae16b7923987e970")).setDisplayName("§b§o" + Lang.LANGUAGE.get(player)).build(), event -> {
                event.setCancelled(true);
                getLangGui().show(player);
            }), 1, 1);

            // SCOREBOARD
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/6ff6a4af8fd64a87448e82879a12ff55194c874d14e472c26d8de86d1208274e")).setDisplayName("§b§oScoreboard").build(), event -> {
                event.setCancelled(true);
                getScoreboardGui().show(player);
            }), 3, 1);
            menu.addPane(pane);
            menu.show(player);
        } else {
            if (args[0].equalsIgnoreCase("adddynmapmarkers")) {
                int toAdd = Integer.parseInt(args[1]);
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[2]);
                if (targetUUID == null || !Bukkit.getOfflinePlayer(targetUUID).hasPlayedBefore()) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                    return false;
                }
                if (!sender.hasPermission("earth.admin")) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                Profile targetProfile = Main.getInstance().getProfile(targetUUID);
                targetProfile.getExtras().putIfAbsent("dynmapMarkers", 0);
                targetProfile.getExtras().put("dynmapMarkers", (int) targetProfile.getExtras().get("dynmapMarkers") + toAdd);
                targetProfile.save();
                sender.sendMessage("§aDone.");
            }
        }
        return false;
    }

    private Gui getLangGui() {
        Gui langgui = new Gui(Main.getInstance(), 3, "§b"+ Lang.CHOOSE_LANG.get(player));
        StaticPane langpane = new StaticPane(0, 0, 9, 3);
        langpane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f")).setDisplayName("§eDeutsch").build(), e -> {
            e.setCancelled(true);
            profile.setLang("DE");
            profile.save();
            langgui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 0, 0);
        langpane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4")).setDisplayName("§eEnglish").build(), e -> {
            e.setCancelled(true);
            profile.setLang("ENG");
            profile.save();
            langgui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 1, 0);
        langpane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setDisplayName("§efrançais").build(), e -> {
            e.setCancelled(true);
            profile.setLang("FR");
            profile.save();
            langgui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 2, 0);
        langpane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/32bd4521983309e0ad76c1ee29874287957ec3d96f8d889324da8c887e485ea8")).setDisplayName("§eEspañol").build(), e -> {
            e.setCancelled(true);
            profile.setLang("ES");
            profile.save();
            langgui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 3, 0);
        langpane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/c23cf210edea396f2f5dfbced69848434f93404eefeabf54b23c073b090adf")).setDisplayName("§eNederlands").build(), e -> {
            e.setCancelled(true);
            profile.setLang("NL");
            profile.save();
            langgui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 4, 0);
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
                    scoregui.update();
                    player.sendMessage(Lang.CHANGED_SCOREBOARDTYPE.get(player).replace("%TYPE%", ScoreboardAdapter.scoreboardType.COMPACT.name()));
                    break;
                case COMPACT:
                    profile.setBoardType(ScoreboardAdapter.scoreboardType.STANDARD.name());
                    profile.save();
                    scoregui.update();
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
                menu.update();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§bAQUA"));
            }), 0, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.RED_WOOL).setDisplayName("§cRed").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§c");
                profile.save();
                menu.update();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§cRED"));
            }), 1, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.YELLOW_WOOL).setDisplayName("§eYellow").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§e");
                profile.save();
                menu.update();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§eYELLOW"));
            }), 2, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.GREEN_WOOL).setDisplayName("§aGreen").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§a");
                profile.save();
                menu.update();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§aGREEN"));
            }), 3, 0);
            pane2.addItem(new GuiItem(new ItemBuilder(Material.CYAN_WOOL).setDisplayName("§3Blue").build(), e -> {
                e.setCancelled(true);
                profile.setFavoriteColour("§3");
                profile.save();
                menu.update();
                player.sendMessage(Lang.CHANGED_FAV_COL.get(player).replace("%COL%", "§3BLUE"));
            }), 4, 0);
            menu.addPane(pane2);
            menu.show(player);
        }), 3, 1);
        ChatColor sbo = profile.isScoreboard() ? ChatColor.GREEN : ChatColor.RED;
        pane.addItem(new GuiItem(new ItemBuilder(Methods.getSBWoolByCC("§" + sbo.getChar())).setDisplayName("§7Scoreboard: " + sbo + profile.isScoreboard()).build(), event -> {
            event.setCancelled(true);
            profile.setScoreboard(!profile.isScoreboard());
            profile.save();
            scoregui.update();
            scoregui.show(player);
        }), 5, 1);
        scoregui.addPane(pane);
        return scoregui;
    }

}
