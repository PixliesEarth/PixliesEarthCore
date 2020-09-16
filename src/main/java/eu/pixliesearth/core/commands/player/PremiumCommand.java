package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PremiumCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("earth.premium")) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Profile profile = instance.getProfile(player.getUniqueId());
        if (player.hasPermission("earth.premium.snow") && !profile.getExtras().containsKey("dynmapMarkers")) {
            profile.getExtras().put("dynmapMarkers", 1);
        } else if (player.hasPermission("earth.premium.ultimate") && !profile.getExtras().containsKey("dynmapMarkers")) {
            profile.getExtras().put("dynmapMarkers", 3);
        }
        int dynmapMarker = (int) Math.round((Double) profile.getExtras().getOrDefault("dynmapMarkers", 0));
        boolean alreadyGifted = (boolean) profile.getExtras().getOrDefault("giftedRoyal", false);
        Gui gui = new Gui(instance, 3, "§6Premium-GUI");
        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        ItemStack markerItem = dynmapMarker > 0 ? new ItemBuilder(Material.SOUL_CAMPFIRE).setDisplayName("§aSet a dynmapmarker").addLoreLine("§a" + dynmapMarker + " §7left").build() : new ItemBuilder(Material.BARRIER).setDisplayName("§cAlready used").build();
        ItemStack giftItem = !alreadyGifted ? new ItemBuilder(Material.CAKE).setDisplayName("§6Gift someone 6 days of royal").addLoreLine("§7Be generous!").build() : new ItemBuilder(Material.BARRIER).setDisplayName("§cAlready used").build();
        pane.addItem(new GuiItem(markerItem, event -> {
            event.setCancelled(true);
            player.closeInventory();
            if (dynmapMarker > 0) {
                instance.getUtilLists().dynmapSetters.add(player.getUniqueId());
                player.sendMessage("§7Type the name of the dynmap-marker you want to add. Type §ccancel §7to cancel the operation.");
            }
        }), 3, 1);
        pane.addItem(new GuiItem(giftItem, event -> {
            event.setCancelled(true);
            player.closeInventory();
            if (!alreadyGifted) {
                instance.getUtilLists().royalGifters.add(player.getUniqueId());
                player.sendMessage("§7Type the name of the player you want to gift 6days of royal to. Type §ccancel §7to cancel the operation.");
            }
        }), 5, 1);
        gui.addPane(pane);
        gui.show(player);
        return false;
    }

}
