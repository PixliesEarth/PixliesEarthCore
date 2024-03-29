package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.NationCommandExecuteEvent;
import eu.pixliesearth.localization.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WarCommandListener extends CustomListener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        String[] split = event.getMessage().split(" ");
        if (profile.isInWar() && (
                split[0].equalsIgnoreCase("/back")
                || split[0].equalsIgnoreCase("/ec")
                || split[0].equalsIgnoreCase("/enderchest")
                || split[0].equalsIgnoreCase("/echest")
                || split[0].equalsIgnoreCase("/home"))) {
            player.sendMessage(Lang.WAR + "§7You can't use nation commands in war.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSubCommand(NationCommandExecuteEvent event) {
        if (!(event.getSender() instanceof Player player)) return;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInWar()) return;
        if (event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.settlementsCommand ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.chatNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.bankNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.infoNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.mapNation) return;
        event.setCancelled(true);
        player.sendMessage(Lang.WAR + "§7You can't use nation commands in war.");
    }

}
