package eu.pixliesearth.discord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.economy.VaultAPI;
import eu.pixliesearth.core.objects.Profile;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public class MiniMick {

    private static @Getter DiscordApi api;

    public void start() {

        String token = Main.getInstance().getConfig().getString("discordtoken");
        if (token.equals("TOKEN_HERE")) {
            Bukkit.getConsoleSender().sendMessage("§cDiscord token is not configured. Bot will not start.");
            return;
        }

        api = new DiscordApiBuilder().setToken(token).login().join();
        api.updateActivity(ActivityType.LISTENING, "to your heart <3");

        api.addMessageCreateListener(event -> {
           if (event.getMessageContent().startsWith("/link")) {
               String[] split = event.getMessageContent().split(" ");
               if (split.length == 1) {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, you have to give me a code so I can verify your account.");
                   return;
               }
               Document user = new Document("discord", event.getMessageAuthor().getIdAsString());
               Document found = Main.getPlayerCollection().find(user).first();
               if (found != null) {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, this discord account is already linked to an in-game account.");
                   return;
               }
               if (Main.getInstance().getPlayerLists().discordcodes.containsKey(split[1])) {
                   Profile profile = Main.getInstance().getProfile(Main.getInstance().getPlayerLists().discordcodes.get(split[1]));
                   profile.setDiscord(event.getMessageAuthor().getIdAsString());
                   profile.backup();
                   event.getServer().get().addRoleToUser(event.getMessageAuthor().asUser().get(), event.getServer().get().getRoleById("709463355529887854").get());
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, your account successfully got verified.");
                   Main.getInstance().getPlayerLists().discordcodes.remove(split[1]);
               } else {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, that code is invalid.");
               }
           } else if (event.getMessageContent().equalsIgnoreCase("/givemydata")) {
               Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
               if (profile == null) {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored from you in our database.");
                   return;
               }
               event.getMessageAuthor().asUser().get().openPrivateChannel().join().sendMessage("**This is the data we currently have in our database:**\n```json\n" + new GsonBuilder().setPrettyPrinting().create().toJson(profile) + "\n```");
           } else if (event.getMessageContent().equalsIgnoreCase("/setigchat")) {
               if (!event.getServer().get().hasPermission(event.getMessageAuthor().asUser().get(), PermissionType.ADMINISTRATOR)) {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, Insufficient permissions.");
                   return;
               }
               Main.getInstance().getConfig().set("chatchannel", event.getChannel().getIdAsString());
               Main.getInstance().saveConfig();
               Main.getInstance().reloadConfig();
               event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, successfully set the chat-channel.");
           } else if(event.getMessageContent().equalsIgnoreCase("/bal")) {
               Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
               if (profile == null) {
                   event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored from you in our database.");
                   return;
               }
               double balance = profile.getBalance();
               UUID uuid = UUID.fromString(profile.getUniqueId());
               event.getChannel().sendMessage(new EmbedBuilder()
                       .setColor(Color.GREEN)
                        .setDescription("**" + Objects.requireNonNull(Bukkit.getOfflinePlayer(uuid).getPlayer()).getName() + "** has $" + balance + " to his name!" )
                       .setFooter("MiniMick powered by PixliesEarth", event.getServer().get().getIcon().get().getUrl().toString())
                       .setTimestampToNow());
           } else {
               if (event.getChannel().equals(event.getServer().get().getTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get()) && event.getMessageAuthor().isRegularUser()) {
                   if (event.getMessage().getReadableContent().length() > 0 && !event.getMessageContent().startsWith("/"))
                       Bukkit.broadcastMessage("§9D §8| §b" + event.getMessageAuthor().getDisplayName() + " §8» §7" + event.getReadableMessageContent());
               }
           }
        });

    }

}
