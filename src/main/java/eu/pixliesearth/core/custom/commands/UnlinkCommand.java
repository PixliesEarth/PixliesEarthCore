package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlinkCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "unlink";
    }

    @Override
    public String getCommandDescription() {
        return "Unlink your discord and minecraft accounts";
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return true;
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.discordIsSynced()) {
            Lang.DC_NOT_SYNCED.send(player);
            return false;
        }
        profile.setDiscord("NONE");
        profile.save();
        profile.backup();
        player.sendMessage(Lang.DISCORD + "§7your Discord and Minecraft accounts have successfully been unlinked.");
        return false;
    }

}
