package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.pixliemoji.PixlieMoji;
import eu.pixliesearth.utils.Methods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmojiCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "emoji";
    }

    @Override
    public String getCommandDescription() {
        return "Emoji Command";
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return true;
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        sender.sendMessage(Methods.getCenteredMessage("§8-= §bPixlieMoji Keyboard §8=-"));
        List<TextComponent> lines = new ArrayList<>();
        int i = 0;
        TextComponent currentComponent = new TextComponent();
        for (Map.Entry<String, Character> entry : PixlieMoji.pixlieMojis.entrySet()) {
            if (i + 1 > 15) {
                i = 0;
                lines.add(currentComponent.duplicate());
                currentComponent = new TextComponent();
            }
            TextComponent currentEmoji = new TextComponent(entry.getValue() + " ");
            currentEmoji.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click me to paste " + entry.getValue() + " into your chat.")));
            currentEmoji.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, entry.getValue() + ""));
            currentComponent.addExtra(currentEmoji);
            i++;
        }
        if (lines.isEmpty()) {
            sender.spigot().sendMessage(currentComponent);
        } else {
            for (TextComponent line : lines) {
                sender.spigot().sendMessage(line);
            }
        }
        return true;
    }

}
