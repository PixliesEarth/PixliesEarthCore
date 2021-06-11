package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.warsystem.War;
import eu.pixliesearth.warsystem.WarParticipant;
import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordWar extends DiscordCommand {

    public DiscordWar() {
        super("war", "currentwar", "warinfo");
    }

    @Override
    public void run(MessageCreateEvent event) {
        if (instance.getCurrentWar() == null) {
            reply(event, "there is no active war at the moment.");
            return;
        }
        War war = instance.getCurrentWar();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("War information");
        embed.setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar());
        if (war.getTimers().containsKey("gracePeriod")) {
            embed.setDescription("Grace-period: " + war.getTimers().get("gracePeriod").getRemainingAsString());
            embed.addInlineField("Attacker", war.getAggressorInstance().getName());
            embed.addInlineField("    ", "    ");
            embed.addInlineField("Defender", war.getDefenderInstance().getName());
            event.getChannel().sendMessage(embed);
            return;
        }

        embed.addInlineField("Attacker", war.getAggressorInstance().getName());
        embed.addInlineField("    ", "    ");
        embed.addInlineField("Defender", war.getDefenderInstance().getName());

        embed.addInlineField("Fighters left", war.getLeft().get(WarParticipant.WarSide.AGGRESSOR)+"");
        embed.addInlineField("    ", "    ");
        embed.addInlineField("Fighters left", war.getLeft().get(WarParticipant.WarSide.DEFENDER)+"");
        event.getChannel().sendMessage(embed);
    }

}
