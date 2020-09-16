package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void onCommmandPreProcess(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        String command =  cmd.contains(" ") ? cmd.split(" ")[0] : cmd;
        if(Main.getInstance().getUtilLists().awaitingGulag1.contains(p.getUniqueId())
        || Main.getInstance().getUtilLists().awaitingGulag2.contains(p.getUniqueId())
        || Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId())
        || Main.getInstance().getUtilLists().fightingGulag.containsValue(p.getUniqueId())){
            if(!command.equalsIgnoreCase("/msg")
                && !command.equalsIgnoreCase("/skipgulag")
                && !command.equalsIgnoreCase("/gulagskip")
                && !p.hasPermission("gulag.commandblock.bypass")){
                e.setCancelled(true);
                p.sendMessage(Lang.GULAG_COMMAND.get(p));
            }
        }
    }
}
