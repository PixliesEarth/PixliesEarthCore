package eu.pixliesearth.core.listener;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.core.utils.AfkMap;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.core.utils.SkullBuilder;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
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
        profile.backup();
        Main.getInstance().getPlayerLists().locationMap.put(player.getUniqueId(), new AfkMap(new SimpleLocation(player.getLocation()), 0));
        event.setJoinMessage(PlaceholderAPI.setPlaceholders(player, "§8[§a§l+§8] %vault_prefix%" + player.getName()));

        if (!player.hasPlayedBefore()) {
            for (Player op : Bukkit.getOnlinePlayers())
                op.sendMessage(Lang.PLAYER_JOINED_FIRST_TIME.get(op).replace("%PLAYER%", player.getDisplayName()).replace("%COUNT%", Main.getPlayerCollection().countDocuments()+""));
            Gui gui = new Gui(Main.getInstance(), 3, "§bChoose your language");
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Germany\\\"},SkullOwner:{Id:\\\"be211c23-d8aa-4119-bd0d-7f50fd115d9f\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").setDisplayname("§eGerman").build(), e -> {
                profile.setLang("DE");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 0, 0);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"United States of America\\\"},SkullOwner:{Id:\\\"3c30484a-76d3-4cfe-88e5-e7599bc9ac4d\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").setDisplayname("§eEnglish").build(), e -> {
                profile.setLang("ENG");
                profile.save();
                player.closeInventory();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 1, 0);
            gui.addPane(pane);
            gui.show(player);
        }

        //VANISH
        if(!(Main.getInstance().getPlayerLists().vanishList.isEmpty())) {
            //VANISHES FOR PLAYERS WHO NEWLY JOINED
            for (UUID pUUID : Main.getInstance().getPlayerLists().vanishList) {
                Player p = Bukkit.getOfflinePlayer(pUUID).getPlayer();
                if (!(player.hasPermission("earth.seevanished"))) {
                    player.hidePlayer(Main.getInstance(), p);
                }
            }
        }

        //TODO WITH NATIONS
        NationChunk nchunk = NationChunk.get(player.getLocation().getChunk());
        if (nchunk == null) {
            player.sendTitle("§c" + Lang.WILDERNESS.get(player), Lang.WILDERNESS_SUBTITLE.get(player), 20, 20 * 3, 20);
        } else if (nchunk.getNationId().equals("safezone")) {
            player.sendTitle("§6SafeZone", Lang.SAFEZONE_SUBTITLE.get(player), 20, 20 * 3, 20);
        } else {
            Nation n = Nation.getById(nchunk.getNationId());
            player.sendTitle(n.getName(), n.getDescription(), 20, 20 * 3, 20);
        }

        //Discord Joins
        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage("<:arrowright:627916581237686291> **" + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) + "** joined the server!");

        long needed = System.currentTimeMillis() - started;
        player.sendMessage(Lang.PROFILE_LOADED.get(player).replace("%TIME%", needed + "ms"));
    }

}
