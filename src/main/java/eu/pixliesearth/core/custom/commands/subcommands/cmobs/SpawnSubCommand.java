package eu.pixliesearth.core.custom.commands.subcommands.cmobs;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomMob;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnSubCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandUsage() {
        return "Spawn custom mob.";
    }


    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabableEnums<>(CustomMob.class)};
    }

    @SneakyThrows
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        CustomMob customMob = CustomMob.valueOf(parameters[0]);
        customMob.getClazz().getConstructor(Location.class).newInstance(((Player)commandSender).getLocation());
        commandSender.sendMessage("§bMob spawned");
        return true;
    }

}
