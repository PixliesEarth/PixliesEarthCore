package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class inviteNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"invite"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("remove", 2);
        returner.put("add", 2);
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 3);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
        if (targetUUID == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile target = instance.getProfile(targetUUID);
        if (target.isInNation()) {
            Lang.INVITE_PLAYER_ALREADY_IN_NATION.send(sender);
            return false;
        }
        switch (args.length) {
            case 1:
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                if (target.getInvites().contains(profile.getNationId())) {
                    player.sendMessage(Lang.PLAYER_ALREADY_INVITED.get(sender));
                    return false;
                }
                target.getInvites().add(profile.getNationId());
                target.save();
                for (Player np : profile.getCurrentNation().getOnlineMemberSet())
                    np.sendMessage(Lang.SUCCESSFULLY_INVITED.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", Bukkit.getOfflinePlayer(targetUUID).getName()));
                if (target.isOnline())
                    Bukkit.getPlayer(targetUUID).sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", profile.getCurrentNation().getName()));
                break;
            case 2:
                if (args[1].equalsIgnoreCase("add")) {
                    Player player1 = (Player) sender;
                    Profile profile1 = instance.getProfile(player1.getUniqueId());
                    if (!profile1.isInNation()) {
                        Lang.NOT_IN_A_NATION.send(player1);
                        return false;
                    }
                    if (target.getInvites().contains(profile1.getNationId())) {
                        sender.sendMessage(Lang.PLAYER_ALREADY_INVITED.get(sender));
                        return false;
                    }
                    target.getInvites().add(profile1.getNationId());
                    target.save();
                    for (Player np : profile1.getCurrentNation().getOnlineMemberSet())
                        np.sendMessage(Lang.SUCCESSFULLY_INVITED.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", Bukkit.getOfflinePlayer(targetUUID).getName()));
                    if (target.isOnline())
                        Bukkit.getPlayer(targetUUID).sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", profile1.getCurrentNation().getName()));
                } else if (args[1].equalsIgnoreCase("remove")) {
                    Player player1 = (Player) sender;
                    Profile profile1 = instance.getProfile(player1.getUniqueId());
                    if (!profile1.isInNation()) {
                        Lang.NOT_IN_A_NATION.send(player1);
                        return false;
                    }
                    if (!target.getInvites().contains(profile1.getNationId())) {
                        sender.sendMessage(Lang.PLAYER_NEVER_INVITED.get(sender));
                        return false;
                    }
                    target.getInvites().remove(profile1.getNationId());
                    target.save();
                    for (Player np : profile1.getCurrentNation().getOnlineMemberSet())
                        np.sendMessage(Lang.REMOVED_INVITE.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", Bukkit.getOfflinePlayer(targetUUID).getName()));
                }
                break;
            case 3:
                if (sender instanceof Player && !instance.getUtilLists().staffMode.contains(((Player) sender))) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                Nation nation = Nation.getByName(args[2]);
                if (nation == null) {
                    sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
                    return false;
                }
                if (args[1].equalsIgnoreCase("add")) {
                    if (target.getInvites().contains(nation.getNationId())) {
                        sender.sendMessage(Lang.PLAYER_ALREADY_INVITED.get(sender));
                        return false;
                    }
                    target.getInvites().add(nation.getNationId());
                    target.save();
                    for (Player np : nation.getOnlineMemberSet())
                        np.sendMessage(Lang.SUCCESSFULLY_INVITED.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", Bukkit.getOfflinePlayer(targetUUID).getName()));
                    if (target.isOnline())
                        Bukkit.getPlayer(targetUUID).sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", nation.getName()));
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (!target.getInvites().contains(nation.getNationId())) {
                        sender.sendMessage(Lang.PLAYER_NEVER_INVITED.get(sender));
                        return false;
                    }
                    target.getInvites().remove(nation.getNationId());
                    target.save();
                    for (Player np : nation.getOnlineMemberSet())
                        np.sendMessage(Lang.REMOVED_INVITE.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", Bukkit.getOfflinePlayer(targetUUID).getName()));
                }
                break;
        }
        return false;
    }

}
