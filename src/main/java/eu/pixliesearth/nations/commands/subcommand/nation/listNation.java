package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Methods;
import org.bukkit.command.CommandSender;

import java.util.*;

public class listNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"list", "nations"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return new HashMap<>();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int page = 1;
        if (args.length > 0 && args[0].chars().allMatch(Character::isDigit))
            page = Integer.parseInt(args[0]);
        List<Nation> Nations = new LinkedList<>(NationManager.nations.values());
        Nations.sort((f1, f2) -> {
            int f1Online = f1.getMembers().size();
            int f2Online = f2.getMembers().size();
            return Integer.compare(f2Online, f1Online);
        });
        int height = 9;
        int pages = Nations.size() / height + 1;
        if (page > pages) {
            page = pages;
        } else if (page < 1) {
            page = 1;
        }
        List<String> lines = new LinkedList<>();
        int start = (page - 1) * height;
        int end = start + height;
        if (end > Nations.size())
            end = Nations.size();
        for (Nation nation : Nations.subList(start, end)) {
            if (nation.getNationId().equals("safezone") || nation.getNationId().equals("warzone"))
                continue;
            lines.add("§7* §b" + nation.getName() + " §8- §2§l$§a" + nation.getMoney() + " §8- §a☺ " + nation.getMembers().size());
        }

        sender.sendMessage(Methods.getCenteredMessage("&8-= &bNations &7(&b" + page + "&8/&b" + pages + "&7) &8=-"));
        lines.forEach(sender::sendMessage);
        return false;
    }

}
