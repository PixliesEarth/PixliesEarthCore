package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class disbandNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"disband", "delete"};
    }

    @Override
    public String[] autocompletion() { return new String[]{}; }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
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
                //TODO permission check
                instance.getUtilLists().nationDisbander.put(player.getUniqueId(), user.getNationId());
                player.sendMessage(Lang.NATION_DELEATION_CONIIRMATION.get(player));
                break;
            case 1:
                Nation nation = Nation.getById(args[0]);
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
                    sender.sendMessage(Lang.NATION_DELEATION_CONIIRMATION.get(sender));
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
