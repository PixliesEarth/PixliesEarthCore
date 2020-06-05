package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class VanishCommand implements CommandExecutor {
    private static Main instace = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {



        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("earth.vanish"))) {
            p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }
        if(args.length == 0) {

            if (instace.getPlayerLists().vanishList.contains(p.getUniqueId())) {
                for(Player players : Bukkit.getOnlinePlayers()) {
                    players.showPlayer(Main.getInstance(), p);

                }
                p.sendMessage(Lang.VANISH_OFF.get(sender));
                Main.getInstance().getPlayerLists().vanishList.remove(p.getUniqueId());
            }else{
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(!(p.hasPermission("earth.seevanished"))) {
                        players.hidePlayer(Main.getInstance(), p);
                    }

                }
                p.sendMessage(Lang.VANISH_ON.get(sender));
                Main.getInstance().getPlayerLists().vanishList.add(p.getUniqueId());
            }
        }else if(args.length == 1){
            if(Bukkit.getPlayer(args[0]) == null){
                p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(p));
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (instace.getPlayerLists().vanishList.contains(player.getUniqueId())) {
                for(Player players : Bukkit.getOnlinePlayers()){
                    players.showPlayer(Main.getInstance(), player);
                }
                player.sendMessage(Lang.VANISH_OFF_BY_OTHER.get(sender).replace("%other%", p.getName()));
                Main.getInstance().getPlayerLists().vanishList.remove(player.getUniqueId());
            }else{
                for(Player players : Bukkit.getOnlinePlayers()){
                    players.hidePlayer(Main.getInstance(), player);
                }
                player.sendMessage(Lang.VANISH_ON_BY_OTHER.get(sender).replace("%other%", p.getName()));
                Main.getInstance().getPlayerLists().vanishList.add(player.getUniqueId());
            }

        }
        return false;
    }
}
