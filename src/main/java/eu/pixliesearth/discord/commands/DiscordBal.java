package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class DiscordBal extends DiscordCommand {

    public DiscordBal() {
        super("bal", "balance");
    }

    @Override
    public void run(MessageCreateEvent event) {
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
    }

}
