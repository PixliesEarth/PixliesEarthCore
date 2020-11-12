package eu.pixliesearth.core.custom.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;

public class TestTranslationCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "translations";
    }

    @Override
    public String getCommandDescription() {
        return "Sends translations to sender";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.translations";
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
    	if (parameters.length == 0) {
            for (Lang lang : Lang.values()) {
                try {
                	commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getLanguages().get("ENG")));
                } catch (Exception e) {
                    System.out.println(lang.name() + " has a problem.");
                }
            }
        } else {
            for (Lang lang : Lang.values())
                lang.sendWithlangName(commandSender, parameters[0]);
        }
        return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new TabablePlayer()};
    }

}
