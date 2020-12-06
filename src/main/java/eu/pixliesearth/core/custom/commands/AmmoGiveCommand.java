package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmmoGiveCommand extends CustomCommand {
	
	@Override
	public String getCommandName() {
		return "ammogive";
	}

	@Override
	public String getCommandDescription() {
		return "Gives ammo item based on the input";
	}

    @Override
    public String getPermission() {
        return "net.pixlies.ammogive";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return false;
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] args, boolean ranByPlayer) {
    	if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/ammogive <gun/ammo> [player]"));
                return false;
            }
            if (args.length == 1) {
                if (!PixliesAmmo.AmmoType.contains(args[0])) {
                    sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                    return false;
                }
                ItemStack is = PixliesAmmo.AmmoType.valueOf(args[0]).getAmmo().getItem();
                p.getInventory().addItem(is);
                p.sendMessage(Lang.GUN_GIVEN.get(sender).replace("%gun%", args[0].toUpperCase()));

            } else if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) == null) {
                    p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (!PixliesAmmo.AmmoType.contains(args[0])) {
                    sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                    return false;
                }
                ItemStack is = PixliesAmmo.AmmoType.valueOf(args[0]).getAmmo().getItem();
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
            if (!PixliesAmmo.AmmoType.contains(args[0].toUpperCase())) {
                sender.sendMessage(Lang.GUN_DOESNT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            ItemStack is = PixliesAmmo.AmmoType.valueOf(args[0]).getAmmo().getItem();
            player.getInventory().addItem(is);
            player.sendMessage(Lang.GUN_RECIEVED.get(player).replace("%gun%", args[0].toUpperCase()));
            sender.sendMessage(Lang.GUN_GIVEN_OTHER.get(sender).replace("%gun%", args[0].toUpperCase()).replace("%player%", player.getName()));
        }
        return false;
    }
    
    @Override
    public ITabable[] getParams() { 
    	return new ITabable[] {new TabableAmmoTypes(), new TabablePlayer()};
    }

    /**@Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], getAmmoNames(), completions);
        } else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], onlineplayerNames(), completions);
        }

        Collections.sort(completions);

        return completions;
    }*/
    
    private static class TabableAmmoTypes implements ITabable {
    	
    	private List<String> getAmmoNames() {
            List<String> returner = new ArrayList<>();
            for (PixliesAmmo.AmmoType g : PixliesAmmo.AmmoType.values())
                returner.add(g.name());
            return returner;
        }
    	
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			final List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(params[params.length-1], getAmmoNames(), completions);
			Collections.sort(completions);
			return completions;
		}
    	
		@Override
		public String getTabableName() {
			return "ammotype";
		}
		
    }

}