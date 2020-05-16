package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class disbandNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"disband", "delete"};
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cThis command is only executable as a player.");
                    return false;
                }
                Player player = (Player) sender;
                Profile user = instance.getProfile(player.getUniqueId());
                if (!user.isInNation()) {
                    player.sendMessage("§bNATION §8| §cYou are not in a nation.");
                    return false;
                }
                //TODO permission check
                instance.getPlayerLists().nationDisbander.put(player.getUniqueId(), user.getNationId());
                player.sendMessage("§bNATION §8| §7Are you sure that you want to disband your nation? Type §aconfirm §7to disband, if your decision changed, type §ccancel§7.");
                break;
        }
        return false;
    }

}
