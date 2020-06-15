package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class descriptionNation implements SubCommand {
    @Override
    public String[] aliases() {
        return new String[]{"description", "setdescription", "desc", "setdesc"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("§bDESCRIPTION", 1);
        return returner;
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
        //TODO PERMISSIONS
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++)
            stringBuilder.append(args[i]).append(" ");
        Nation nation = profile.getCurrentNation();
        nation.setDescription(stringBuilder.toString());
        nation.save();
        for (Player np : nation.getOnlineMemberSet())
            np.sendMessage(Lang.PLAYER_CHANGED_DESCRIPTION.get(np).replace("%PLAYER%", np.getName()).replace("%DESC%", nation.getDescription()));

        return false;
    }
}
