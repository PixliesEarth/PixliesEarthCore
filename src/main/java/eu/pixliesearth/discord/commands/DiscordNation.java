package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.javacord.api.entity.emoji.CustomEmojiBuilder;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class DiscordNation extends DiscordCommand {

    public DiscordNation() {
        super("nation", "nationinfo");
    }

    @Override
    public void run(MessageCreateEvent event) {
        String[] split = event.getMessageContent().split(" ");
        if (split.length >= 2) {
            if (split[1].equalsIgnoreCase("list")) {
                int page = 1;
                if (split.length > 2 && NumberUtils.isNumber(split[2]))
                    page = Integer.parseInt(split[2]);
                List<String> Nations = new ArrayList<>(NationManager.names.keySet());
                int height = 9;
                int pages = Nations.size() / height + 1;
                if (page > pages) {
                    page = pages;
                } else if (page < 1) {
                    page = 1;
                }
                int start = (page - 1) * height;
                int end = start + height;
                if (end > Nations.size())
                    end = Nations.size();
                StringBuilder builder = new StringBuilder();
                builder.append("```md\n");
                for (String nation : Nations.subList(start, end)) {
                    builder.append("* ").append(nation).append("\n");
                }
                builder.append("```");
                event.getChannel().sendMessage(new EmbedBuilder().setTitle("**Nation list**").setDescription(builder.toString()).setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar()).setAuthor("Page: " + page + "/" + pages)).thenAccept(message -> {

                });
                return;
            }
            Nation nation = Nation.getByName(split[1]);
            if (nation == null) {
                event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, this nation does not exist, please be aware that this action is case-sensitive.");
                return;
            }
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(hexToColor(nation.getDynmapFill()))
                    .setTitle("**" + nation.getName() + "**")
                    .setDescription("*" + nation.getDescription() + "*")
                    .addInlineField("Wiki article", nation.getExtras().containsKey("wikiUrl") ? nation.getExtras().get("wikiUrl").toString() : "N/A")
                    .addInlineField("Members", nation.getMembers().size() + "")
                    .addInlineField("Leader", nation.getLeaderName())
                    .addInlineField("Ideology", WordUtils.capitalize(nation.getIdeology().toLowerCase().replace("_", " ")))
                    .addInlineField("Religion", WordUtils.capitalize(nation.getReligion().toLowerCase().replace("_", " ")))
                    .addInlineField("Era", nation.getCurrentEra().getName())
                    .addInlineField("Balance", "$" + nation.getMoney())
                    .addInlineField("Territory", nation.getChunks().size() + "")
                    .setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar())
               );
        }
    }

    public static MessageBuilder getListEmbed(int page, MessageAuthor author) {
        List<String> Nations = new ArrayList<>(NationManager.names.keySet());
        int height = 9;
        int pages = Nations.size() / height + 1;
        if (page > pages) {
            page = pages;
        } else if (page < 1) {
            page = 1;
        }
        int start = (page - 1) * height;
        int end = start + height;
        if (end > Nations.size())
            end = Nations.size();
        StringBuilder builder = new StringBuilder();
        builder.append("```md\n");
        for (String nation : Nations.subList(start, end)) {
            builder.append("* ").append(nation).append("\n");
        }
        builder.append("```");
        MessageBuilder mBuilder = new MessageBuilder();
        if (page > 1) mBuilder.addComponents(ActionRow.of(Button.primary("page-" + (page - 1), "Previous Page")));
        if (page < pages) mBuilder.addComponents(ActionRow.of(Button.primary("page-" + (page + 1), "Next Page")));
        return mBuilder.setEmbed(new EmbedBuilder().setTitle("**Nation list**").setDescription(builder.toString()).setFooter("Requested by " + author.getDisplayName() + " (" + author.getDiscriminatedName() + ")", author.getAvatar()).setAuthor("Page: " + page + "/" + pages));
    }

}
