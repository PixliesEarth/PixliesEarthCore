package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class bankNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"bank", "money"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> map = new HashMap<>();
        map.put("deposit", 1);
        map.put("withdraw", 1);
        map.put("balance", 1);
        for (String s : NationManager.names.keySet()) {
            map.put(s, 3);
            map.put(s, 2);
        }
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("balance")) {
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
                    Lang.NATION_BALANCE.send(player, "%NATION%;" + profile.getCurrentNation().getName(), "%AMOUNT%;" + profile.getCurrentNation().getMoney());
                }
                break;
        }
        return false;
    }

}
