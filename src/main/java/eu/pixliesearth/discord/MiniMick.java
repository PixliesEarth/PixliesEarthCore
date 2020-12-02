package eu.pixliesearth.discord;

import com.google.gson.GsonBuilder;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Methods;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;

import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MiniMick {

    private static @Getter DiscordApi api;
    private @Getter TextChannel chatChannel;
    private static final @Getter Map<String, DiscordCommand> commands = new HashMap<>();

    public void start() {

        String token = Main.getInstance().getConfig().getString("discordtoken", "TOKEN_HERE");
        if (token.equals("TOKEN_HERE")) {
            Bukkit.getConsoleSender().sendMessage("§cDiscord token is not configured. Bot will not start.");
            return;
        }

        api = new DiscordApiBuilder().setToken(token).login().join();
        api.updateActivity(ActivityType.PLAYING, "on pixlies.net");

        chatChannel = api.getTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get();

        DiscordCommand.loadAll();

        api.addMessageCreateListener(event -> {
            String[] split = event.getMessageContent().split(" ");
            if (split[0].startsWith("/") && commands.containsKey(split[0].replace("/", ""))) {
                commands.get(split[0].replace("/", "")).run(event);
            } else {
                if (event.getChannel().equals(chatChannel) && event.getMessageAuthor().isRegularUser()) {
                    if (event.getMessage().getReadableContent().length() > 0 && !event.getMessageContent().startsWith("/")) {
                        String roleColour = "#{#00ffff}";
                        if (event.getMessageAuthor().getRoleColor().isPresent()) {
                            Color col = event.getMessageAuthor().getRoleColor().get();
                            roleColour = String.format("#{%02x%02x%02x}", col.getRed(), col.getGreen(), col.getBlue());
                        }
                        String message = event.getReadableMessageContent();
                        Bukkit.broadcastMessage("§9D §8| §b" + ChatColor.translateAlternateColorCodes('&', Methods.translateToHex(roleColour)) + event.getMessageAuthor().getDisplayName() + " §8» §7" + message);
                    }
                }
            }
        });

    }

    static final Color hexToColor( String value )
    {
        String digits;
        if ( value.startsWith( "#" ) )
        {
            digits = value.substring( 1, Math.min( value.length( ), 7 ) );
        }
        else
        {
            digits = value;
        }
        String hstr = "0x" + digits;
        Color c;
        try
        {
            c = Color.decode( hstr );
        }
        catch ( NumberFormatException nfe )
        {
            c = null;
        }
        return c;
    }

}
