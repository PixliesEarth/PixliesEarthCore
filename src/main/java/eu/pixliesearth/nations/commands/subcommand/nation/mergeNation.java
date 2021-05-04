package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class mergeNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"merge"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        if (args.length == 2) {
            map.put("accept", 1);
            map.put("deny", 1);
        } else if (args.length == 3) {
            for (String s : NationManager.names.keySet())
                map.put(s, 2);
        } else if (args.length == 1) {
            for (String s : NationManager.names.keySet())
                map.put(s, 1);
        }
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!profile.isLeader()) {
            Lang.YOU_HAVE_TO_BE_LEADER.send(player);
            return false;
        }
        if (args.length == 1) {
            Nation targetNation = Nation.getByName(args[0]);
            if (targetNation == null) {
                Lang.NATION_DOESNT_EXIST.send(player);
                return false;
            }
            if (targetNation.getNationId().equalsIgnoreCase(profile.getNationId())) {
                Lang.BOTH_PLAYERS_IN_THE_SAME_NATION.send(sender);
                return false;
            }
            targetNation.getExtras().put("mergeRequest:" + profile.getNationId(), "request");
            targetNation.save();
            targetNation.broadcastMembers(Lang.NATION + "§b" + profile.getCurrentNation().getName() + " §7just sent you a merge-request. §e/n merge " + profile.getCurrentNation().getName());
            profile.getCurrentNation().broadcastMembers(Lang.NATION + "§7Your nation just sent a merge-request to §b" + targetNation.getName());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("accept")) {
            Nation targetNation = Nation.getByName(args[1]);
            if (targetNation == null) {
                Lang.NATION_DOESNT_EXIST.send(player);
                return false;
            }
            if (profile.getCurrentNation().getExtras().containsKey("mergeRequest:" + targetNation.getNationId())) {
                profile.getCurrentNation().broadcastMembers(Lang.NATION + "§6" + player.getName() + " §7just merged your nation with §b" + targetNation.getName());
                targetNation.broadcastMembers(Lang.NATION + "§6" + player.getName() + " §7just merged your nation with §b" + targetNation.getName());
                profile.getCurrentNation().merge(targetNation);
                return false;
            }
            sender.sendMessage(Lang.NATION + "§7You don't have a merge request from this nation.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("deny")) {
            Nation targetNation = Nation.getByName(args[1]);
            if (targetNation == null) {
                Lang.NATION_DOESNT_EXIST.send(player);
                return false;
            }
            Nation nation = profile.getCurrentNation();
            if (nation.getExtras().containsKey("mergeRequest:" + targetNation.getNationId())) {
                nation.getExtras().remove("mergeRequest:" + targetNation.getNationId());
                nation.save();
                targetNation.broadcastMembers(Lang.NATION + "§b" + nation.getName() + "§7Just denied your merge request.");
                return false;
            }
            sender.sendMessage(Lang.NATION + "§7You don't have a merge request from this nation.");
        }
        return true;
    }

}
