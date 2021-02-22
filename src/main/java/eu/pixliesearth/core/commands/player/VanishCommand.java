package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.DynmapAPI;
import org.dynmap.DynmapCommonAPI;


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

            if (instace.getUtilLists().vanishList.contains(p.getUniqueId())) {
                for(Player players : Bukkit.getOnlinePlayers()) {
                    players.showPlayer(Main.getInstance(), p);

                }
                p.sendMessage(Lang.VANISH_OFF.get(sender));
                Main.getInstance().getUtilLists().vanishList.remove(p.getUniqueId());
                DynmapAPI dynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
                dynmapAPI.assertPlayerVisibility(p, true, Main.getInstance());
            }else{
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(!(players.hasPermission("earth.seevanished"))) {
                        players.hidePlayer(Main.getInstance(), p);
                    }

                }
                p.sendMessage(Lang.VANISH_ON.get(sender));
                Main.getInstance().getUtilLists().vanishList.add(p.getUniqueId());
                DynmapAPI dynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
                dynmapAPI.assertPlayerInvisibility(p, true, Main.getInstance());
            }
        }else if(args.length == 1){
            if(Bukkit.getPlayer(args[0]) == null){
                p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(p));
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (instace.getUtilLists().vanishList.contains(player.getUniqueId())) {
                for(Player players : Bukkit.getOnlinePlayers()){
                    players.showPlayer(Main.getInstance(), player);
                }
                player.sendMessage(Lang.VANISH_OFF_BY_OTHER.get(sender).replace("%other%", p.getName()));
                Main.getInstance().getUtilLists().vanishList.remove(player.getUniqueId());
                DynmapAPI dynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
                dynmapAPI.assertPlayerVisibility(player, true, Main.getInstance());
            }else{
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(!players.hasPermission("earth.seevanished")) {
                        players.hidePlayer(Main.getInstance(), player);
                    }
                }
                player.sendMessage(Lang.VANISH_ON_BY_OTHER.get(sender).replace("%other%", p.getName()));
                Main.getInstance().getUtilLists().vanishList.add(player.getUniqueId());
                DynmapAPI dynmapAPI = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
                dynmapAPI.assertPlayerInvisibility(player, true, Main.getInstance());
            }

        }
        return false;
    }
}
