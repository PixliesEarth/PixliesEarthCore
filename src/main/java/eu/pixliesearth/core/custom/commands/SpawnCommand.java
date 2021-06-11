package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.spawn.SetSpawnCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandDescription() {
        return "Teleport to spawn";
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return true;
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomSubCommand.TabableSubCommand(new SetSpawnCommand())};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.teleport(instance.getFastConf().getSpawnLocation(), "Spawn", 0.0, 6);
        return true;
    }

}
