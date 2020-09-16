package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GulagSkipCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("gulag.command.skip")){
            p.sendMessage(Lang.NO_PERMISSIONS.get(p));
            return false;
        }
        if(!Main.getInstance().getUtilLists().awaitingGulag1.contains(p.getUniqueId()) && !Main.getInstance().getUtilLists().awaitingGulag2.contains(p.getUniqueId())){
            p.sendMessage(Lang.GULAG_SKIP_NOT_AWAITING.get(p));
            return false;
        }
        if(Main.getInstance().getUtilLists().awaitingGulag1.contains(p.getUniqueId())){
            Main.getInstance().getUtilLists().awaitingGulag1.remove(p.getUniqueId());
            p.sendMessage(Lang.GULAG_SKIPPED.get(p));
            GulagStartListener.fightOver(null, p);
        }else if(Main.getInstance().getUtilLists().awaitingGulag2.contains(p.getUniqueId())){
            Main.getInstance().getUtilLists().awaitingGulag2.remove(p.getUniqueId());
            p.sendMessage(Lang.GULAG_SKIPPED.get(p));
            GulagStartListener.fightOver(null, p);
        }
        return false;
    }
}
