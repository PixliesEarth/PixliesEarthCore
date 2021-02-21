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
        if(event.getMessage().contains("@a") || event.getMessage().contains("@e")){
            player.sendMessage("§cCommands with @a and @e may only be executed from console!");
        }
        if (!instance.getUtilLists().inGulag.contains(player.getUniqueId())) return;
        String[] split = event.getMessage().split(" ");
        if (split[0].equalsIgnoreCase("/back")
                || split[0].equalsIgnoreCase("/ec")
                || split[0].equalsIgnoreCase("/enderchest")
                || split[0].equalsIgnoreCase("/echest")
                || split[0].equalsIgnoreCase("/home")) {
            player.sendMessage(Lang.WAR + "§7You cant use nation commands in war.");
        }
    }

    @EventHandler
    public void onSubCommand(NationCommandExecuteEvent event) {
        if (!(event.getSender() instanceof Player)) return;
        Player player = (Player) event.getSender();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInWar()) return;
        if (event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.settlementsCommand ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.chatNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.bankNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.infoNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.mapNation ||
        event.getCommand() instanceof eu.pixliesearth.nations.commands.subcommand.nation.topNation) return;
        event.setCancelled(true);
        player.sendMessage(Lang.WAR + "§7You cant use nation commands in war.");
    }

}
