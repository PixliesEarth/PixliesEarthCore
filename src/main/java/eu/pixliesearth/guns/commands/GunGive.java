package eu.pixliesearth.guns.commands;

import eu.pixliesearth.guns.gunObjects.GunObject;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GunGive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
        Player p = (Player) sender;
            if (!(p.hasPermission("earth.gun.give"))){
                p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                return false;
            }
            if(args.length == 0){
                p.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/gungive <gun/ammo> [player]" ));
                return false;
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("ak47") || args[0].equalsIgnoreCase("ak")){
                    ItemStack ak = new GunObject().AK();
                    p.getInventory().addItem(ak);
                    p.sendMessage(Lang.GUN_GIVEN.get(sender).replace("%gun%", "AK47"));
                }
            }else if(args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("ak47") || args[0].equalsIgnoreCase("ak")) {


                    ItemStack ak = new GunObject().AK();
                    assert player != null;
                    player.getInventory().addItem(ak);
                    player.sendMessage(Lang.GUN_RECIEVED.get(player).replace("%gun%", "AK47"));
                    p.sendMessage(Lang.GUN_GIVEN_OTHER.get(p).replace("%gun%", "AK47").replace("%player%", player.getName()));
                }
            }

        }else if(sender instanceof ConsoleCommandSender){
            if(args.length < 2){
                sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/gungive <gun/ammo> <player>"));
                return false;
            }
            if(Bukkit.getPlayer(args[1]) == null){
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            ItemStack ak = new GunObject().AK();
            assert player != null;
            player.getInventory().addItem(ak);
            player.sendMessage(Lang.GUN_RECIEVED.get(player).replace("%gun%", "AK47"));
            sender.sendMessage(Lang.GUN_GIVEN_OTHER.get(sender).replace("%gun%", "AK47").replace("%player%", player.getName()));
        }
        return false;
    }
}
