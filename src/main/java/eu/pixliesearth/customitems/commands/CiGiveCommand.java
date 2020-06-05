package eu.pixliesearth.customitems.commands;

import eu.pixliesearth.customitems.ItemSlingshot;
import eu.pixliesearth.localization.Lang;
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
                if(!valid){
                    p.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(p));
                    return false;
                    }
                }
            }


        return false;
    }
}
