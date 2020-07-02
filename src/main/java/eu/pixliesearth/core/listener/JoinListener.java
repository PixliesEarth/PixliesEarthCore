package eu.pixliesearth.core.listener;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        final long started = System.currentTimeMillis();

        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (!profile.getKnownIps().contains(Methods.getIp(player))) {
            profile.getKnownIps().add(Methods.getIp(player));
        }
        if (!profile.getKnownUsernames().contains(player.getName())) {
            profile.getKnownUsernames().add(player.getName());
        }
        Main.getInstance().getUtilLists().locationMap.put(player.getUniqueId(), new AfkMap(player.getLocation(), 0));
        event.setJoinMessage(PlaceholderAPI.setPlaceholders(player, "§8[§a§l+§8] %vault_prefix%" + player.getName()));

        if (!player.hasPlayedBefore()) {
            for (Player op : Bukkit.getOnlinePlayers())
                op.sendMessage(Lang.PLAYER_JOINED_FIRST_TIME.get(op).replace("%PLAYER%", player.getDisplayName()).replace("%COUNT%", Main.getPlayerCollection().countDocuments() + ""));
            Gui gui = new Gui(Main.getInstance(), 3, "§bChoose your language");
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f")).setDisplayName("§eDeutsch").build(), e -> {
                e.setCancelled(true);
                profile.setLang("DE");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 0, 0);
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4")).setDisplayName("§eEnglish").build(), e -> {
                e.setCancelled(true);
                profile.setLang("ENG");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 1, 0);
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setDisplayName("§efrançais").build(), e -> {
                e.setCancelled(true);
                profile.setLang("FR");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 2, 0);
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/32bd4521983309e0ad76c1ee29874287957ec3d96f8d889324da8c887e485ea8")).setDisplayName("§eEspañol").build(), e -> {
                e.setCancelled(true);
                profile.setLang("ES");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 3, 0);
            pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/c23cf210edea396f2f5dfbced69848434f93404eefeabf54b23c073b090adf")).setDisplayName("§eNederlands").build(), e -> {
                e.setCancelled(true);
                profile.setLang("NL");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 4, 0);
            gui.addPane(pane);
            gui.show(player);
        }

        //VANISH
        if (!(Main.getInstance().getUtilLists().vanishList.isEmpty())) {
            //VANISHES FOR PLAYERS WHO NEWLY JOINED
            for (UUID pUUID : Main.getInstance().getUtilLists().vanishList) {
                Player p = Bukkit.getOfflinePlayer(pUUID).getPlayer();
                if (!(player.hasPermission("earth.seevanished"))) {
                    player.hidePlayer(Main.getInstance(), p);
                }
            }
        }

        NationChunk tn = NationChunk.get(player.getLocation().getChunk());
        if (tn == null) {  // WILDERNESS
            player.sendTitle("§c" + Lang.WILDERNESS.get(player), Lang.WILDERNESS_SUBTITLE.get(player), 20, 20 * 2, 20);
        } else {
            if (tn.getNationId().equals(profile.getCurrentNation().getNationId())) { // YOUR NATION
                player.sendTitle("§b" + tn.getCurrentNation().getName(), "§7" + tn.getCurrentNation().getDescription(), 20, 20 * 2, 20);
            } else if (tn.getNationId().equals("safezone")) { // SAFEZONE
                player.sendTitle("§aSafeZone", "§7" + Lang.SAFEZONE_SUBTITLE.get(player), 20, 20 * 2, 20);
            } else if (tn.getNationId().equals("warzone")) { // WARZONE
                player.sendTitle("§cWarZone", "§7" + Lang.WARZONE_SUBTITLE.get(player), 20, 20 * 2, 20);
            } else if (tn.getCurrentNation().isAlliedWith(profile.getNationId())) { // ALLIES
                player.sendTitle("§d" + tn.getCurrentNation().getName(), "§7" + tn.getCurrentNation().getDescription(), 20, 20 * 2, 20);
            } else { // ANY OTHER NATION
                player.sendTitle(tn.getCurrentNation().getName(), "§7" + tn.getCurrentNation().getDescription(), 20, 20 * 2, 20);
            }
        }

/*
        //Discord Joins
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage(ChatColor.stripColor("<:arrowright:627916581237686291> **" + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) + "** joined the server!"));
*/

        if (profile.getEnergy() > 5)
            profile.setEnergy(5);

        profile.getTimers().clear();
        if (profile.getNickname().length() > 0)
            player.setDisplayName(profile.getNickname());
        profile.save();
        long needed = System.currentTimeMillis() - started;
        player.sendMessage(Lang.PROFILE_LOADED.get(player).replace("%TIME%", needed + "ms"));
    }

}
