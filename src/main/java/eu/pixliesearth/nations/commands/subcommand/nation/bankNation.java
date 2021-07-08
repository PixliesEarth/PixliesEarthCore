package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class bankNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"bank", "money"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("deposit", 1);
        map.put("withdraw", 1);
        map.put("balance", 1);
        map.put("§eAMOUNT", 2);
        for (String s : NationManager.names.keySet()) {
            map.put(s, 3);
            map.put(s, 2);
        }
        return map;
    }

    @Override
    public String getSyntax() {
        return """
                §7Show bank balance: §b/n bank §cbalance §8[§eNATION§8]
                §7Withdraw money from the bank: §b/n bank §cwithdraw §eAMOUNT §8[§6NATION§8]
                §7Deposit money into the bank: §b/n bank §cdeposit §eAMOUNT §8[§6NATION§8]""";
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("balance")) {
                    if (!checkIfPlayer(sender)) return false;
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
                    if (!checkIfPlayer(sender)) return false;
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
                    if (!Permission.hasNationPermission(profile, Permission.BANK_DEPOSIT) && !profile.isStaff()) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    nation.deposit(amount);
                    profile.save();
                    nation.save();
                    Lang.DEPOSIT_MONEY_INTO_NATION.send(player, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                } else if (args[0].equalsIgnoreCase("withdraw")) {
                    if (!(sender instanceof Player player)) {
                        Lang.ONLY_PLAYERS_EXEC.send(sender);
                        return false;
                    }
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
                    if (!Permission.hasNationPermission(profile, Permission.BANK_WITHDRAW) && !profile.isStaff()) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    boolean nationWithdraw = nation.withdraw(amount);
                    if (!nationWithdraw) {
                        Lang.NOT_ENOUGH_MONEY_IN_NATION.send(player);
                        return false;
                    }
                    profile.depositMoney(amount, "Nation-bank withdraw");
                    Lang.WITHDREW_MONEY_FROM_NATION.send(player, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("deposit")) {
                    Nation nation = Nation.getByName(args[1]);
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(sender);
                        return false;
                    }
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (!Permission.hasForeignPermission(profile, Permission.BANK_DEPOSIT, nation) && !profile.isStaff() && !profile.getNationId().equals(nation.getNationId())) {
                            Lang.NO_PERMISSIONS.send(sender);
                            return false;
                        }
                        if (!StringUtils.isNumeric(args[2]) || args[2].startsWith("-")) {
                            Lang.INVALID_INPUT.send(player);
                            return false;
                        }
                        int amount = Integer.parseInt(args[2]);
                        boolean withdrawPlayer = profile.withdrawMoney(amount, "Nation-bank deposit: " + nation.getName());
                        if (!withdrawPlayer) {
                            Lang.NOT_ENOUGH_MONEY.send(player);
                            return false;
                        }
                        nation.deposit(amount);
                        profile.save();
                        nation.save();
                        Lang.DEPOSIT_MONEY_INTO_NATION.send(player, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                    } else {
                        if (!StringUtils.isNumeric(args[2]) || args[2].startsWith("-")) {
                            Lang.INVALID_INPUT.send(sender);
                            return false;
                        }
                        int amount = Integer.parseInt(args[2]);
                        nation.deposit(amount);
                        nation.save();
                        Lang.DEPOSIT_MONEY_INTO_NATION.send(sender, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                    }
                } else if (args[0].equalsIgnoreCase("withdraw")) {
                    Nation nation = Nation.getByName(args[1]);
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(sender);
                        return false;
                    }
                    if (sender instanceof Player player) {
                        Profile profile = instance.getProfile(player.getUniqueId());
                        if (!Permission.hasForeignPermission(profile, Permission.BANK_WITHDRAW, nation) && !profile.isStaff() && !profile.getNationId().equals(nation.getNationId())) {
                            Lang.NO_PERMISSIONS.send(sender);
                            return false;
                        }
                        if (!StringUtils.isNumeric(args[2]) || args[2].startsWith("-")) {
                            Lang.INVALID_INPUT.send(player);
                            return false;
                        }
                        int amount = Integer.parseInt(args[2]);
                        boolean nationWithdraw = nation.withdraw(amount);
                        if (!nationWithdraw) {
                            Lang.NOT_ENOUGH_MONEY_IN_NATION.send(player);
                            return false;
                        }
                        profile.depositMoney(amount, "Nation-bank withdraw: " + nation.getName());
                        Lang.WITHDREW_MONEY_FROM_NATION.send(player, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                    } else {
                        if (!StringUtils.isNumeric(args[2]) || args[2].startsWith("-")) {
                            Lang.INVALID_INPUT.send(sender);
                            return false;
                        }
                        int amount = Integer.parseInt(args[2]);
                        boolean nationWithdraw = nation.withdraw(amount);
                        if (!nationWithdraw) {
                            Lang.NOT_ENOUGH_MONEY_IN_NATION.send(sender);
                            return false;
                        }
                        Lang.WITHDREW_MONEY_FROM_NATION.send(sender, "%AMOUNT%;" + amount, "%NATION%;" + nation.getName());
                    }
                }
                break;
            default:
                sendSyntax(sender, "bank");
        }
        return false;
    }

}
