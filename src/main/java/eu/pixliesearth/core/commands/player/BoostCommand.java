package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import eu.pixliesearth.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//TODO
public class BoostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (args.length) {
            case 0:
                Gui boostGui = new Gui(Main.getInstance(), 3, "&dBoosters");
                break;
        }
        return false;
    }

}
