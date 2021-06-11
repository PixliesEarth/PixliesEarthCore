package eu.pixliesearth.core.custom.commands.subcommands.economy;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class EconomyAddCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "give";
    }

    @Override
    public String getCommandUsage() {
        return "Add balance.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabablePlayer(), new CustomCommand.TabableString("§cAmount")};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!instance.isStaff(sender)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(parameters[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        if (!NumberUtils.isNumber(parameters[1])) {
            Lang.INVALID_INPUT.send(sender);
            return false;
        }
        Profile profile = instance.getProfile(target.getUniqueId());
        profile.depositMoney(Double.parseDouble(parameters[1]), sender.getName() + " deposited into your account");
        sender.sendMessage(Lang.GAVE_MONEY_TO_PLAYER.get(sender).replace("%AMOUNT%", parameters[1]).replace("%PLAYER%", target.getName()));
        return true;
    }

}
