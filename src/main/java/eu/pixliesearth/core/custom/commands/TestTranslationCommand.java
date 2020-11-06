package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TestTranslationCommand extends CustomCommand {

    @Override
    public String getName() {
        return "translations";
    }

    @Override
    public String getDescription() {
        return "Sends translations to sender";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.translations";
    }

    @Override
    public boolean execute(CommandSender commandsender, String alias, String[] args) {
        if (args.length == 0) {
            for (Lang lang : Lang.values()) {
                try {
                    commandsender.sendMessage(ChatColor.translateAlternateColorCodes('&', lang.getLanguages().get("ENG")));
                } catch (Exception e) {
                    System.out.println(lang.name() + " has a problem.");
                }
            }
        } else {
            for (Lang lang : Lang.values())
                lang.sendWithlangName(commandsender, args[0]);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
        List<String> array = new ArrayList<String>();
        if (args.length == 1)
            array.addAll(Lang.PLAYER.getLanguages().keySet());
        return array;
    }

}
