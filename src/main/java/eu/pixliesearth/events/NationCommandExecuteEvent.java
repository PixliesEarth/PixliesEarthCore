package eu.pixliesearth.events;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NationCommandExecuteEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    private @Getter final CommandSender sender;
    private @Getter final SubCommand command;
    private boolean isCancelled = false;


    public NationCommandExecuteEvent(CommandSender sender, SubCommand command) {
        this.sender = sender;
        this.command = command;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = true;
    }

}
