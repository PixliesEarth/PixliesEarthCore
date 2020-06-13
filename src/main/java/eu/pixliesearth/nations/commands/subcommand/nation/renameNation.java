package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.RegisterSub;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@RegisterSub(
        command = "nations"
)
public class renameNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"rename", "name"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 2);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Nation nation;
            Map<String, String> placeholders;
            boolean success;
            switch (args.length) {
                case 1:
                    Profile profile = instance.getProfile(player.getUniqueId());
                    if (!profile.isInNation()) {
                        player.sendMessage(Lang.NOT_IN_A_NATION.get(player));
                        return false;
                    }
                    //TODO permissionsystem
                    nation = profile.getCurrentNation();
                    final String oldName = nation.getName();
                    success = nation.rename(args[0]);
                    if (!success) {
                        Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                        return false;
                    }
                    placeholders = new HashMap<>();
                    placeholders.put("%PLAYER%", player.getDisplayName());
                    placeholders.put("%OLD%", oldName);
                    placeholders.put("%NEW%", nation.getName());
                    Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
                    break;
                case 2:
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                        player.sendMessage(Lang.NO_PERMISSIONS.get(player));
                        return false;
                    }
                    nation = Nation.getByName(args[1]);
                    if (nation == null) {
                        player.sendMessage(Lang.NATION_DOESNT_EXIST.get(player));
                        return false;
                    }
                    final String oldName1 = nation.getName();
                    success = nation.rename(args[0]);
                    if (!success) {
                        Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                        return false;
                    }
                    placeholders = new HashMap<>();
                    placeholders.put("%PLAYER%", player.getDisplayName());
                    placeholders.put("%OLD%", oldName1);
                    placeholders.put("%NEW%", nation.getName());
                    Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
                    break;
            }
        } else {
            Nation nation = Nation.getByName(args[1]);
            if (nation == null) {
                sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
                return false;
            }
            final String oldName1 = nation.getName();
            boolean success = nation.rename(args[0]);
            if (!success) {
                Lang.NATION_WITH_NAME_ALREADY_EXISTS.send(sender);
                return false;
            }
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("%PLAYER%", sender.getName());
            placeholders.put("%OLD%", oldName1);
            placeholders.put("%NEW%", nation.getName());
            Lang.PLAYER_NAMED_NATION_NAME.broadcast(placeholders);
        }
        return false;
    }

}
