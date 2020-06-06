package eu.pixliesearth.core.customitems.commands;

import eu.pixliesearth.core.customitems.CustomItems;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CiGiveCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("earth.ci.give")) {
                p.sendMessage(Lang.NO_PERMISSIONS.get(p));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(Lang.WRONG_USAGE.get(p).replace("%USAGE%", "/csgive <item> [player]"));
                return false;
            }
            switch (args.length) {
                case 0:
                    p.sendMessage(Lang.WRONG_USAGE.get(p).replace("%USAGE%", "/csgive <item> [player]"));
                    return false;
                case 1:
                    if (!CustomItems.contains(args[0].toUpperCase())) {
                        p.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(p));
                        return false;
                    }
                    p.getInventory().addItem(CustomItems.valueOf(args[0].toUpperCase()).clazz.getRecipe());
                    p.sendMessage(Lang.CUSTOM_GIVE_SELF.get(p).replace("%item%", CustomItems.valueOf(args[0].toUpperCase()).clazz.getName()));
                    break;
                case 2:
                    if (Bukkit.getPlayer(args[1]) == null) {
                        p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(p));
                        return false;
                    }
                    if (!CustomItems.contains(args[0].toUpperCase())) {
                        p.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(p));
                        return false;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    player.getInventory().addItem(CustomItems.valueOf(args[0].toUpperCase()).clazz.getRecipe());
                    p.sendMessage(Lang.CUSTOM_GIVE_OTHER.get(p).replace("%item%", CustomItems.valueOf(args[0].toUpperCase()).clazz.getName()).replace("%player%", player.getName()));
                    player.sendMessage(Lang.CUSTOM_GIVEN_BY_OTHER.get(player).replace("%item%", CustomItems.valueOf(args[0].toUpperCase()).clazz.getName()).replace("%player%", p.getName()));
                    break;
            }
        } else {
            if (args.length != 2) {
                sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/csgive <item> [player]"));
                return false;
            }
            if (Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            if (!CustomItems.contains(args[0].toUpperCase())) {
                sender.sendMessage(Lang.CUSTOM_DOESNT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            player.getInventory().addItem(CustomItems.valueOf(args[0].toUpperCase()).clazz.getRecipe());
            sender.sendMessage(Lang.CUSTOM_GIVE_OTHER.get(sender).replace("%item%", CustomItems.valueOf(args[0].toUpperCase()).clazz.getName()).replace("%player%", player.getName()));
        }
        return false;
    }

    private List<String> getItemNames() {
        List<String> returner = new ArrayList<>();
        for (CustomItems g : CustomItems.values())
            returner.add(g.name());
        return returner;
    }

    private List<String> onlinePlayerNames() {
        List<String> returner = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
            returner.add(p.getName());
        return returner;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], getItemNames(), completions);
        } else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], onlinePlayerNames(), completions);
        }

        Collections.sort(completions);

        return completions;
    }

}
