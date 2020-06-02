package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
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
    public String[] autocompletion() { return new String[]{}; }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
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
                instance.getPlayerLists().nationDisbander.put(player.getUniqueId(), user.getNationId());
                player.sendMessage(Lang.NATION_DELEATION_CONIIRMATION.get(player));
                break;
        }
        return false;
    }

}
