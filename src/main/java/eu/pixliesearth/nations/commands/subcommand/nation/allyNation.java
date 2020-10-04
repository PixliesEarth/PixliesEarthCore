package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class allyNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"ally"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> map = new HashMap<>();
        for (String s : NationManager.names.keySet())
            map.put(s, 1);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        Nation target = Nation.getByName(args[0]);
        if (target == null) {
            Lang.NATION_DOESNT_EXIST.send(player);
            return false;
        }
        if (nation.getNationId().equals(target.getNationId())) {
            Lang.TWO_NATIONS_ARE_THE_SAME.send(player);
            return false;
        }
        if (Nation.getRelation(nation.getNationId(), target.getNationId()) == Nation.NationRelation.ALLY) {
            Lang.ALREADY_ALLIED.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_RELATIONS)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        if (nation.getAllyRequests().contains(target.getNationId())) {
            target.getAllies().add(nation.getNationId());
            target.getAllyRequests().remove(nation.getNationId());
            target.save();
            nation.getAllies().add(target.getNationId());
            nation.save();
            for (Player member : target.getOnlineMemberSet())
                Lang.YOU_ARE_NOW_ALLIED.send(member, "%NATION%;" + nation.getName());
            for (Player member : nation.getOnlineMemberSet())
                Lang.YOU_ARE_NOW_ALLIED.send(member, "%NATION%;" + target.getName());
            return false;
        }
        target.getAllyRequests().add(nation.getNationId());
        target.save();
        Lang.SENT_ALLY_REQUEST.send(player);
        for (Player member : target.getOnlineMemberSet()) {
            if (Permission.hasNationPermission(instance.getProfile(member.getUniqueId()), Permission.MANAGE_RELATIONS)) {
                Lang.RECEIVED_ALLY_REQUEST.send(member, "%NATION%;" + nation.getName());
                TextComponent accept = new TextComponent(Lang.ACCEPT.get(member));
                accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oClick to accept").create()));
                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n ally " + nation.getName()));
                member.spigot().sendMessage(accept);
            }
        }
        return false;
    }

}
