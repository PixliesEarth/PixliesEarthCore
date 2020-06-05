package eu.pixliesearth.customitems.commands;

import eu.pixliesearth.customitems.ItemSlingshot;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CiGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!(p.hasPermission("earth.ci.give"))){
                p.sendMessage(Lang.NO_PERMISSIONS.get(p));
                return false;
            }
            if(args.length == 0){
                p.sendMessage(Lang.WRONG_USAGE.get(p).replace("%USAGE%", "/csgive <item> [player]"));
                return false;
            }
            if(args.length == 1){
                Boolean valid = false;
                if(args[0].equalsIgnoreCase("slingshot")){
                ItemStack slingshot = new ItemSlingshot().getRecipe();
                p.getInventory().addItem(slingshot);
                p.sendMessage(Lang.CUSTOM_GIVE_SELF.get(p).replace("%item%", "slingshot"));
                valid = true;
                    }
                //TODO: if statements for other items
                if(!valid){
                    p.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(p));
                    return false;
                    }
                }else if(args.length == 2){
                if(Bukkit.getPlayer(args[1]) == null){
                    p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(p));
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);

                if(args[0].equalsIgnoreCase("slingshot")){
                    Boolean valid = false;
                    if(args[0].equalsIgnoreCase("slingshot")){
                        ItemStack slingshot = new ItemSlingshot().getRecipe();
                        player.getInventory().addItem(slingshot);
                        p.sendMessage(Lang.CUSTOM_GIVE_OTHER.get(p).replace("%item%", "slingshot").replace("%player%", player.getName()));
                        player.sendMessage(Lang.CUSTOM_GIVEN_BY_OTHER.get(player).replace("%item%", "slingshot").replace("%player%", p.getName()));
                        valid = true;
                    }
                    //TODO: if statements for other items
                    if(!valid){
                        p.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(p));
                        return false;
                    }
                }
            }
            }


        return false;
    }
}
