package eu.pixliesearth.discord;

import com.google.gson.GsonBuilder;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.Methods;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MiniMick {

    private static @Getter
    DiscordApi api;

    public void start() {

        String token = Main.getInstance().getConfig().getString("discordtoken");
        if (token.equals("TOKEN_HERE")) {
            Bukkit.getConsoleSender().sendMessage("§cDiscord token is not configured. Bot will not start.");
            return;
        }

        api = new DiscordApiBuilder().setToken(token).login().join();
        api.updateActivity(ActivityType.PLAYING, "on pixlies.net");

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().startsWith("/link")) {
                String[] split = event.getMessageContent().split(" ");
                event.deleteMessage();
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
                if (Main.getInstance().getUtilLists().discordcodes.containsKey(split[1])) {
                    Profile profile = Main.getInstance().getProfile(Main.getInstance().getUtilLists().discordcodes.get(split[1]));
                    profile.setDiscord(event.getMessageAuthor().getIdAsString());
                    profile.backup();
                    event.getServer().get().addRoleToUser(event.getMessageAuthor().asUser().get(), event.getServer().get().getRoleById("709463355529887854").get());
                    Main.getInstance().getUtilLists().discordcodes.remove(split[1]);
                    Role rank = api.getServerById("589958750866112512").get().getRoleById(DiscordIngameRank.groupRoleMap().get(profile.getRank().getName())).get();
                    rank.addUser(event.getMessageAuthor().asUser().get());
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, your account successfully got verified.");
                } else {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, that code is invalid.");
                }
            } else if (event.getMessageContent().equalsIgnoreCase("/unlink")) {
                Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
                if (profile == null) {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, your discord and ingame accounts are not linked!");
                    return;
                }
                profile.setDiscord("NONE");
                profile.save();
                profile.backup();
                event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, you just unlinked your discord & ingame accounts.");
            } else if (event.getMessageContent().equalsIgnoreCase("/givemydata")) {
                Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
                if (profile == null) {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored about you in our database.");
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
            } else if (event.getMessageContent().equalsIgnoreCase("/bal")) {
                Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
                if (profile == null) {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored from you in our database.");
                    return;
                }
                double balance = profile.getBalance();
                UUID uuid = UUID.fromString(profile.getUniqueId());
                String name = Objects.requireNonNull(Bukkit.getOfflinePlayer(uuid)).getName();
                ArrayList<String> messages = new ArrayList<>();
                messages.add(name + " has $" + balance + " in their pockets.");
                messages.add(name + "'s current balance: $" + balance);
                messages.add(name + " currently has $" + balance + ", what a snob!");
                messages.add("Only $" + balance + "??? Get a load of " + name);
                int random = (int) (4 * Math.random());
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setDescription("**" + messages.get(random) + "**")
                        .setFooter("MiniMick powered by PixliesEarth", event.getServer().get().getIcon().get().getUrl().toString())
                        .setTimestampToNow());
            } else if (event.getMessageContent().equalsIgnoreCase("/history")) {
                Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
                if (profile == null) {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored from you in our database.");
                    return;
                }
                List<String> lastTransActions = profile.getReceipts().subList(profile.getReceipts().size()-11, profile.getReceipts().size());
                StringBuilder builder = new StringBuilder();
                builder.append("```diff" + "\n");
                final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
                for (String s : lastTransActions) {
                    Receipt receipt = Receipt.fromString(s);
                    if (receipt.isLost())
                        builder.append("-$").append(receipt.getAmount()).append(" | ").append(ChatColor.stripColor(receipt.getReason())).append(" @ ").append(sdf.format(new Timestamp(receipt.getTime()))).append("\n");
                    else
                         builder.append("+$").append(receipt.getAmount()).append(" | ").append(ChatColor.stripColor(receipt.getReason())).append(" @ ").append(sdf.format(new Timestamp(receipt.getTime()))).append("\n");

                }
                builder.append("```");
                event.getChannel().sendMessage(new EmbedBuilder().setTitle("**Your transactions | Balance: $" + profile.getBalance() + "**")
                        .setDescription(builder.toString())
                        .setFooter("MiniMick powered by PixliesEarth", event.getServer().get().getIcon().get().getUrl().toString())
                        .setTimestampToNow());;
            } else {
                if (event.getChannel().equals(event.getServer().get().getTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get()) && event.getMessageAuthor().isRegularUser()) {
                    if (event.getMessage().getReadableContent().length() > 0 && !event.getMessageContent().startsWith("/")) {
                        String roleColour = "#{#00ffff}";
                        if (event.getMessageAuthor().getRoleColor().isPresent()) {
                            Color col = event.getMessageAuthor().getRoleColor().get();
                            roleColour = String.format("#{%02x%02x%02x}", col.getRed(), col.getBlue(), col.getGreen());
                        }
                        Bukkit.broadcastMessage("§9D §8| §b" + ChatColor.translateAlternateColorCodes('&', Methods.translateToHex(roleColour)) + event.getMessageAuthor().getDisplayName() + " §8» §7" + event.getReadableMessageContent());
                    }
                }
            }
        });

    }

}
