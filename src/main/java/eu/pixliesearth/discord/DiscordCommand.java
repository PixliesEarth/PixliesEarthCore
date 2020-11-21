package eu.pixliesearth.discord;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import lombok.Data;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

@Data
public abstract class DiscordCommand {

    protected static final Main instance = Main.getInstance();
    protected static final DiscordApi api = MiniMick.getApi();

    private String[] alias;

    public DiscordCommand(String... alias) {
        super();
        this.alias = alias;
    }

    public abstract void run(MessageCreateEvent event);

    protected static Color hexToColor(String value)
    {
        String digits;
        if ( value.startsWith( "#" ) )
            digits = value.substring( 1, Math.min( value.length( ), 7 ) );
        else
            digits = value;
        String hstr = "0x" + digits;
        Color c;
        try {
            c = Color.decode( hstr );
        } catch ( NumberFormatException nfe ) {
            c = null;
        }
        return c;
    }

    @SneakyThrows
    public static void loadAll() {
        for (Class<? extends DiscordCommand> clazz : CustomFeatureLoader.reflectBasedOnExtentionOf("eu.pixliesearth.discord.commands", DiscordCommand.class)) {
            System.out.println("Found Discord command " + clazz.getName());
            DiscordCommand cmd = clazz.getConstructor().newInstance();
            for (String s : cmd.alias)
                MiniMick.getCommands().put(s, cmd);
        }
    }

    public void reply(MessageCreateEvent event, String message) {
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + ">, " + message);
    }

}
