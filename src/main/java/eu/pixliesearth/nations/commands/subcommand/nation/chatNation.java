package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class chatNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"chat"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> map = new HashMap<>();
        map.put("nation", 1);
        map.put("ally", 1);
        map.put("public", 1);
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
        Map<UUID, ChatType> map = instance.getUtilLists().chatTypes;
        switch (args.length) {
            case 0:
                if (map.containsKey(player.getUniqueId())) {
                    switch (map.get(player.getUniqueId())) {
                        case NATION:
                            instance.getUtilLists().chatTypes.put(player.getUniqueId(), ChatType.ALLY);
                            Lang.CHANGED_CHATTYPE.send(player, "%TYPE%;&dAlly");
                            break;
                        case ALLY:
                            instance.getUtilLists().chatTypes.remove(player.getUniqueId());
                            Lang.CHANGED_CHATTYPE.send(player, "%TYPE%;&aPublic");
                            break;
                    }
                } else {
                    instance.getUtilLists().chatTypes.put(player.getUniqueId(), ChatType.NATION);
                    Lang.CHANGED_CHATTYPE.send(player, "%TYPE%;&bNation");
                }
                break;
            case 1:
                if (args[0].equalsIgnoreCase("public")) {
                    instance.getUtilLists().chatTypes.remove(player.getUniqueId());
                    Lang.CHANGED_CHATTYPE.send(player, "%TYPE%;" + ChatType.valueOf(args[0].toUpperCase()).name);
                    return false;
                }
                instance.getUtilLists().chatTypes.put(player.getUniqueId(), ChatType.valueOf(args[0].toUpperCase()));
                Lang.CHANGED_CHATTYPE.send(player, "%TYPE%;" + ChatType.valueOf(args[0].toUpperCase()).name);
                break;
        }
        return false;
    }

    public enum ChatType {

        NATION("§bNation"),
        ALLY("§dAlly"),
        PUBLIC("§aPublic");

        String name;
        ChatType(String name) {
            this.name = name;
        }


    }

}
