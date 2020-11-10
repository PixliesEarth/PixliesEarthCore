package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import org.bukkit.ChatColor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class DiscordHistory extends DiscordCommand {

    public DiscordHistory() {
        super("history", "transactions");
    }

    @Override
    public void run(MessageCreateEvent event) {
        Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
        if (profile == null) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored from you in our database.");
            return;
        }
        List<String> lastTransActions = profile.getReceipts().subList(profile.getReceipts().size() - 11, profile.getReceipts().size());
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
                .setTimestampToNow());
    }

}
