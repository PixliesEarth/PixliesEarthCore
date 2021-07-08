package eu.pixliesearth.discord;

import eu.pixliesearth.Main;
import eu.pixliesearth.discord.commands.DiscordNation;
import eu.pixliesearth.utils.Methods;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MiniMick {

    private static @Getter
    DiscordApi api;
    private @Getter
    TextChannel chatChannel;
    private static final @Getter
    Map<String, DiscordCommand> commands = new HashMap<>();
    private static final @Getter
    Map<String, MiniMickServerConfig> configs = new HashMap<>();

    public static String prefix = Main.getInstance().getConfig().getString("discord-prefix", "~");

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
            if (split[0].startsWith(prefix) && commands.containsKey(split[0].replace(prefix, ""))) {
                DiscordCommand command = commands.get(split[0].replace(prefix, ""));
                if (command.onlyPixlies() && !event.getServer().get().getIdAsString().equals("589958750866112512")) {
                    event.getMessage().reply(new EmbedBuilder().setTitle("This command is only executable on the official PixliesNet server.").setUrl("https://discord.gg/cKJkFTG").setDescription("Click above to join").setColor(Color.RED));
                    return;
                }
                command.run(event);
            } else {
                if (event.getChannel().equals(chatChannel) && event.getMessageAuthor().isRegularUser()) {
                    if (event.getMessage().getReadableContent().length() > 0 && !event.getMessageContent().startsWith("/")) {
                        String roleColour = "#{#00ffff}";
                        if (event.getMessageAuthor().getRoleColor().isPresent()) {
                            Color col = event.getMessageAuthor().getRoleColor().get();
                            roleColour = String.format("#{%02x%02x%02x}", col.getRed(), col.getGreen(), col.getBlue());
                        }
                        for (String s1 : Main.getInstance().getConfig().getStringList("modules.chatsystem.blacklist"))
                            if (StringUtils.containsIgnoreCase(event.getReadableMessageContent(), s1))
                                return;
                        String message = event.getReadableMessageContent();
                        Bukkit.broadcastMessage("§9D §8| §b" + ChatColor.translateAlternateColorCodes('&', Methods.translateToHex(roleColour)) + event.getMessageAuthor().getDisplayName() + " §8» §7" + message);
                    }
                }
            }
        });

        api.addMessageComponentCreateListener(event -> {
            Message message = event.getMessageComponentInteraction().getMessage().get();
            if (event.getMessageComponentInteraction().getCustomId().startsWith("page-")) {
                int page = Integer.parseInt(event.getMessageComponentInteraction().getCustomId().split("-")[1]);
                message.delete();
                DiscordNation.getListEmbed(page, message.getAuthor()).send(message.getChannel());
            }
        });
    }

    static Color hexToColor(String value) {
        String digits;
        if (value.startsWith("#")) {
            digits = value.substring(1, Math.min(value.length(), 7));
        } else {
            digits = value;
        }
        String hstr = "0x" + digits;
        Color c;
        try {
            c = Color.decode(hstr);
        } catch (NumberFormatException nfe) {
            c = null;
        }
        return c;
    }

}
