package eu.pixliesearth.guns.commands;

import eu.pixliesearth.guns.Guns;
import eu.pixliesearth.localization.Lang;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GunGiveCommand implements CommandExecutor, TabExecutor {

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!(p.hasPermission("earth.gun.give"))) {
                p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/gungive <gun/ammo> [player]"));
                return false;
            }
            if (args.length == 1) {
                if (!Guns.contains(args[0].toUpperCase())) {
                    sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                    return false;
                }
                ItemStack is = Guns.valueOf(args[0].toUpperCase()).getClazz().getConstructor(int.class, UUID.class).newInstance(Guns.valueOf(args[0].toUpperCase()).getMaxAmmo(), UUID.randomUUID()).getItem();
                p.getInventory().addItem(is);
                p.sendMessage(Lang.GUN_GIVEN.get(sender).replace("%gun%", args[0].toUpperCase()));

            } else if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (!Guns.contains(args[0].toUpperCase())) {
                    sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                    return false;
                }
                ItemStack is = Guns.valueOf(args[0].toUpperCase()).getClazz().getConstructor(int.class, UUID.class).newInstance(Guns.valueOf(args[0].toUpperCase()).getMaxAmmo(), UUID.randomUUID()).getItem();
                player.getInventory().addItem(is);
                player.sendMessage(Lang.GUN_RECIEVED.get(player).replace("%gun%", args[0].toUpperCase()));
                p.sendMessage(Lang.GUN_GIVEN_OTHER.get(p).replace("%gun%", args[0].toUpperCase()).replace("%player%", player.getName()));
            }

        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length < 2) {
                sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/gungive <gun/ammo> <player>"));
                return false;
            }
            if (Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            if (!Guns.contains(args[0].toUpperCase())) {
                sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            ItemStack is = Guns.valueOf(args[0].toUpperCase()).getClazz().getConstructor(int.class, UUID.class).newInstance(Guns.valueOf(args[0].toUpperCase()).getMaxAmmo(), UUID.randomUUID()).getItem();
            assert player != null;
            player.getInventory().addItem(is);
            player.sendMessage(Lang.GUN_RECIEVED.get(player).replace("%gun%", args[0].toUpperCase()));
            sender.sendMessage(Lang.GUN_GIVEN_OTHER.get(sender).replace("%gun%", args[0].toUpperCase()).replace("%player%", player.getName()));
        }
        return false;
    }

    private List<String> getGunNames() {
        List<String> returner = new ArrayList<>();
        for (Guns g : Guns.values())
            returner.add(g.name());
        return returner;
    }

    private List<String> onlineplayerNames() {
        List<String> returner = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
            returner.add(p.getName());
        return returner;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], getGunNames(), completions);
        } else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], onlineplayerNames(), completions);
        }

        Collections.sort(completions);

        return completions;
    }

}