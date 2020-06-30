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
import eu.pixliesearth.utils.AfkMap;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullBuilder;
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
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Germany\\\"},SkullOwner:{Id:\\\"be211c23-d8aa-4119-bd0d-7f50fd115d9f\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==").setDisplayname("§eDeutsch").build(), e -> {
                e.setCancelled(true);
                profile.setLang("DE");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 0, 0);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"United States of America\\\"},SkullOwner:{Id:\\\"3c30484a-76d3-4cfe-88e5-e7599bc9ac4d\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19").setDisplayname("§eEnglish").build(), e -> {
                e.setCancelled(true);
                profile.setLang("ENG");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 1, 0);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"France\\\"},SkullOwner:{Id:\\\"395a599f-9588-4fe2-ada9-07cd81262996\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==").setDisplayname("§efrançais").build(), e -> {
                e.setCancelled(true);
                profile.setLang("FR");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 2, 0);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Spain\\\"},SkullOwner:{Id:\\\"884a57c8-27ad-4b50-b42b-bee01239f4a8\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=").setDisplayname("§eEspañol").build(), e -> {
                e.setCancelled(true);
                profile.setLang("ES");
                profile.save();
                gui.update();
                player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
            }), 3, 0);
            pane.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Netherlands\\\"},SkullOwner:{Id:\\\"5ddfbff0-7173-48ec-82e6-73343e7fce0f\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzY2YyMTBlZGVhMzk2ZjJmNWRmYmNlZDY5ODQ4NDM0ZjkzNDA0ZWVmZWFiZjU0YjIzYzA3M2IwOTBhZGYifX19\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzY2YyMTBlZGVhMzk2ZjJmNWRmYmNlZDY5ODQ4NDM0ZjkzNDA0ZWVmZWFiZjU0YjIzYzA3M2IwOTBhZGYifX19").setDisplayname("§eNederlands").build(), e -> {
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
