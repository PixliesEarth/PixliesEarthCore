package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class allyNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"ally"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        return new HashMap<>() {
            @Serial
            private static final long serialVersionUID = -1002643333466882536L;

            {
                for (String s : NationManager.names.keySet()) put(s, 1);
            }
        };
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
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
        if (nation.getAllies().size() == nation.getMaxAllies()) {
            sender.sendMessage(Lang.NATION + "§7Your nation has already reached the maximum amount of allies.");
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
                TextComponent accept = Component.text(Lang.ACCEPT.get(member))
                        .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7§oClick to accept")))
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/n ally " + nation.getName()));
                member.sendMessage(accept);
            }
        }
        return false;
    }

}
