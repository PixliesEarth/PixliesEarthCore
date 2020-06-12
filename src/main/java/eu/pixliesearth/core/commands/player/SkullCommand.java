package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
       if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
       Player p = (Player) sender;
       if(!(p.hasPermission("earth.skull"))){
           p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
           return false;
       }

       if(args.length == 0){
           Material type;
           ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
           SkullMeta meta = (SkullMeta) skull.getItemMeta();
           meta.setOwningPlayer(p);
           skull.setItemMeta(meta);
           p.getInventory().addItem(skull);
           p.sendMessage(Lang.SKULL_GIVEN_OWN.get(sender));
       }

       if(args.length == 1){
           Material type;
           ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
           SkullMeta meta = (SkullMeta) skull.getItemMeta();
           meta.setOwningPlayer(Bukkit.getOfflinePlayer(args[0]));
           skull.setItemMeta(meta);
           p.getInventory().addItem(skull);
           p.sendMessage(Lang.SKULL_GIVEN.get(sender).replace("%player%", args[0]));
       }
        return false;
    }
}
