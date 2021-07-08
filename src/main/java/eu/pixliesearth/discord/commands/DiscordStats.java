package eu.pixliesearth.discord.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.UUID;

public class DiscordStats extends DiscordCommand {

    public DiscordStats() {
        super("stats", "player");
    }

    @Override
    public void run(MessageCreateEvent event) {
        try {
            String[] split = event.getMessageContent().split(" ");
            if (split.length == 2) {
                if (Bukkit.getOfflinePlayerIfCached(split[1]) == null) {
                    event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, this player does not exist.");
                    return;
                }
                UUID uuid = Bukkit.getPlayerUniqueId(split[1]);
                Profile profile = Main.getInstance().getProfile(uuid);
                event.getChannel().sendMessage(
                        new EmbedBuilder()
                                .setTitle("**" + profile.getAsOfflinePlayer().getName() + "**")
                                .addInlineField("Nation", profile.isInNation() ? profile.getCurrentNation().getName() : "Not in a nation")
                                .addInlineField("Balance", "$" + Methods.formatNumber((long) profile.getBalance()))
                                .addInlineField("Mana", profile.getEnergy() + "")
                                .addInlineField("Nickname", profile.getNickname())
                                .addInlineField("Boosts", profile.getBoosts() + "")
                                .addInlineField("Discord synced", profile.discordIsSynced() + "")
                                .addInlineField("Nation rank", profile.getNationRank())
                                .setImage("https://minotar.net/avatar/" + profile.getAsOfflinePlayer().getUniqueId())
                                .setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("There was a shooting star.");
        }
    }

}
