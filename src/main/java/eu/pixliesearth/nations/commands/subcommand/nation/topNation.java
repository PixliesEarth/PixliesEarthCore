package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.NTop;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class topNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"top"};
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
        Object[] array = instance.getNationsTop().getTopMap().toArray();
        sender.sendMessage(Methods.getCenteredMessage("§8--== §bN-TOP §7(§b" + page + "§8/§b" + (array.length <= 10 ? 1 : ((array.length + 5) / 10))  + "§7) §8==--"));
        List<Object> currentPage = Arrays.asList(Arrays.copyOfRange(array, (page * 10) - 10, Math.min(array.length, (page * 10))));
        for(int i = 1; i < 11; i++) {
            if (i >= currentPage.size()) {
                continue;
            }

            Object object = currentPage.get(i);
            if (!(object instanceof NTop.NTopProfile)) {
                continue;
            }
            NTop.NTopProfile prof = (NTop.NTopProfile) object;
            ChatColor rankColor = i < 4 ? ChatColor.GREEN : ChatColor.AQUA;
            sender.sendMessage(rankColor + "" + i + " §8. §b" + prof.getName() + " §7- §a" + prof.getPoints() + "P");
        }
        sender.sendMessage("§c(§4§l!§c) This list get's updated every 5 minutes §c(§4§l!§c)");
        return false;
    }

}
