package eu.pixliesearth.discord;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.discord.commands.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;

@Data
public abstract class DiscordCommand {

    protected static final Main instance = Main.getInstance();
    protected static final DiscordApi api = MiniMick.getApi();

    private String[] alias;

    public DiscordCommand(String... alias) {
        super();
        this.alias = alias;
    }

    public boolean onlyPixlies() {
        return false;
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

    private static java.util.List<DiscordCommand> commands() {
        return new ArrayList<>(){{
           add(new DiscordBal());
           add(new DiscordBlockStats());
           add(new DiscordGiveMyData());
           add(new DiscordHistory());
           add(new DiscordLink());
           add(new DiscordNation());
           add(new DiscordPay());
           add(new DiscordRecipe());
           add(new DiscordServer());
           add(new DiscordSetIgChat());
           add(new DiscordStats());
           add(new DiscordUnlink());
           add(new DiscordWar());
           add(new DiscordSetNationUpdates());
        }};
    }

    @SneakyThrows
    public static void loadAll() {
        System.out.println("Loading Discord Commands");
/*        for (Class<? extends DiscordCommand> clazz : CustomFeatureLoader.reflectBasedOnExtentionOf("eu.pixliesearth.discord.commands", DiscordCommand.class)) {
            System.out.println("Found Discord command " + clazz.getName());
            DiscordCommand cmd = clazz.getConstructor().newInstance();
            for (String s : cmd.alias)
                MiniMick.getCommands().put(s, cmd);
        }*/
        for (DiscordCommand command : commands()) {
            System.out.println("Found Discord command " + command.alias[0]);
            for (String s : command.alias)
                MiniMick.getCommands().put(s, command);
        }
    }

    public void reply(MessageCreateEvent event, String message) {
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + ">, " + message);
    }

}
