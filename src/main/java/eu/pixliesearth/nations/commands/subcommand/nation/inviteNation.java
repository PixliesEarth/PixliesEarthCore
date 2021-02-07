package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class inviteNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"invite"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("remove", 2);
        returner.put("add", 2);
        for (String s : NationManager.names.keySet())
            returner.put(s, 3);
        for (Player player : Bukkit.getOnlinePlayers())
            returner.put(player.getName(), 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
        if (targetUUID == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile target = instance.getProfile(targetUUID);
        if (target == null) return false;
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
                    np.sendMessage(Lang.SUCCESSFULLY_INVITED.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", target.getAsOfflinePlayer().getName()));
                if (target.isOnline()) {
                    target.getAsPlayer().sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", profile.getCurrentNation().getName()));
                    TextComponent accept = new TextComponent("§a" + Lang.ACCEPT.get(target.getAsOfflinePlayer().getPlayer()));
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7§oClick me to accept!")));
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n join " + profile.getCurrentNation().getName()));
                    target.getAsOfflinePlayer().getPlayer().spigot().sendMessage(accept);
                }
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
                    if (target.isOnline()) {
                        Bukkit.getPlayer(targetUUID).sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", profile1.getCurrentNation().getName()));
                        TextComponent accept = new TextComponent("§a" + Lang.ACCEPT.get(target.getAsOfflinePlayer().getPlayer()));
                        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7§oClick me to accept!")));
                        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n join " + profile1.getCurrentNation().getName()));
                        target.getAsOfflinePlayer().getPlayer().spigot().sendMessage(accept);
                    }
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
                Nation nation = Nation.getByName(args[2]);
                if (nation == null) {
                    sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
                    return false;
                }
                if (sender instanceof Player && !instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId()) && !Permission.hasForeignPermission(instance.getProfile(((Player) sender).getUniqueId()), Permission.INVITE, nation)) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
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
                    if (target.isOnline()) {
                        Bukkit.getPlayer(targetUUID).sendMessage(Lang.YOU_HAVE_BEEN_INVITED.get(Bukkit.getPlayer(targetUUID)).replace("%NATION%", nation.getName()));
                        TextComponent accept = new TextComponent("§a" + Lang.ACCEPT.get(target.getAsOfflinePlayer().getPlayer()));
                        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7§oClick me to accept!")));
                        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n join " + nation.getName()));
                        target.getAsOfflinePlayer().getPlayer().spigot().sendMessage(accept);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (!target.getInvites().contains(nation.getNationId())) {
                        sender.sendMessage(Lang.PLAYER_NEVER_INVITED.get(sender));
                        return false;
                    }
                    target.getInvites().remove(nation.getNationId());
                    target.save();
                    for (Player np : nation.getOnlineMemberSet())
                        np.sendMessage(Lang.REMOVED_INVITE.get(np).replace("%INVITER%", sender.getName()).replace("%TARGET%", target.getAsOfflinePlayer().getName()));
                }
                break;
        }
        return false;
    }

}
