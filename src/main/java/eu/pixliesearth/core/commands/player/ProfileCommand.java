package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
                return false;
            }
            Player player = (Player) sender;
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());

            Gui menu = new Gui(Main.getInstance(), 3, "§e§l" + Lang.YOUR_PROFILE.get(player));
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
            // LANGUAGE
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4d48e75ff55cb57533c7b904be887a374925f93832f7ae16b7923987e970")).setDisplayName("§b§o" + Lang.LANGUAGE.get(player)).build(), event -> {
                event.setCancelled(true);
                profile.openLangGui();
            }), 1, 1);

            // SCOREBOARD
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/6ff6a4af8fd64a87448e82879a12ff55194c874d14e472c26d8de86d1208274e")).setDisplayName("§b§oScoreboard").build(), event -> {
                event.setCancelled(true);
                getScoreboardGui(profile, player).show(player);
            }), 3, 1);

            // NOTIFICATION SOUND
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4ceeb77d4d25724a9caf2c7cdf2d88399b1417c6b9ff5213659b653be4376e3")).setDisplayName("§b§oNotification sound").build(), event -> {
                event.setCancelled(true);
                profile.openPingSoundGui();
            }), 5, 1);
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
            } else if (args[0].equalsIgnoreCase("print")) {
                if (sender instanceof Player && !Main.getInstance().getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) {
                    return false;
                }
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                if (targetUUID == null || !Bukkit.getOfflinePlayer(targetUUID).hasPlayedBefore()) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                    return false;
                }
                Profile targetProfile = Main.getInstance().getProfile(targetUUID);
                sender.sendMessage(new Gson().toJson(targetProfile));
            } else if (args[0].equalsIgnoreCase("drop")) {
                if (sender instanceof Player && !Main.getInstance().getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) {
                    return false;
                }
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                if (!Main.getInstance().getUtilLists().profiles.containsKey(targetUUID)) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                    return false;
                }
                Main.getInstance().getUtilLists().profiles.remove(targetUUID);
                sender.sendMessage("Profile dropped from internal database.");
            }
        }
        return false;
    }

    private Gui getScoreboardGui(Profile profile, Player player) {
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
