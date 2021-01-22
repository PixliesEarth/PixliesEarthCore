package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class disbandNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"disband", "delete"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
                    return false;
                }
                Player player = (Player) sender;
                Profile user = instance.getProfile(player.getUniqueId());
                if (!user.isInNation()) {
                    player.sendMessage(Lang.NOT_IN_A_NATION.get(player));
                    return false;
                }
                if (!user.getNationRank().equals("leader")) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                instance.getUtilLists().nationDisbander.put(player.getUniqueId(), user.getNationId());
                player.sendMessage(Lang.NATION_DELETATION_CONFIRMATION.get(player));
                break;
            case 1:
                Nation nation = Nation.getByName(args[0]);
                if (nation == null) {
                    sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
                    return false;
                }
                boolean allowed = false;
                if (sender instanceof Player) {
                    if (instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) allowed = true;
                    if (instance.getProfile(((Player) sender).getUniqueId()).getNationId().equals(nation.getNationId())) allowed = true;
                } else {
                    allowed = true;
                }
                if (!allowed) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (sender instanceof Player) {
                    instance.getUtilLists().nationDisbander.put(((Player) sender).getUniqueId(), nation.getNationId());
                    sender.sendMessage(Lang.NATION_DELETATION_CONFIRMATION.get(sender));
                } else {
                    nation.remove();
                    sender.sendMessage("§bNATION §8| §7You disbanded §b" + nation.getName());
                    Bukkit.broadcastMessage("§bNATION §8| §7The nation of §b" + nation.getName() + " §7was disbanded by §6" + sender.getName() + "§7.");
                }
                break;
        }
        return false;
    }

}
