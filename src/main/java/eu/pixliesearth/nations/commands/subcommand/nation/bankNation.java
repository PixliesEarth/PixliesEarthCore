package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.apache.commons.lang.StringUtils;
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
                    Nation nation = profile.getCurrentNation();
                    Lang.NATION_BALANCE.send(player, "%NATION%;" + nation.getName(), "%AMOUNT%;" + nation.getMoney());
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("balance")) {
                    Nation nation = Nation.getByName(args[1]);
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(sender);
                        return false;
                    }
                    Lang.NATION_BALANCE.send(sender, "%NATION%;" + nation.getName(), "%AMOUNT%;" + nation.getMoney());
                } else if (args[0].equalsIgnoreCase("deposit")) {
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
                    Nation nation = profile.getCurrentNation();
                    if (!StringUtils.isNumeric(args[1]) || args[1].startsWith("-")) {
                        Lang.INVALID_INPUT.send(player);
                        return false;
                    }
                    int amount = Integer.parseInt(args[1]);
                    boolean withdrawPlayer = profile.withdrawMoney(amount, "Nation-bank deposit");
                    if (!withdrawPlayer) {
                        Lang.NOT_ENOUGH_MONEY.send(player);
                        return false;
                    }
                    nation.deposit(amount);
                    profile.save();
                    nation.save();
                    Lang.DEPOSIT_MONEY_INTO_NATION.send(player);
                }
                break;
        }
        return false;
    }

}
