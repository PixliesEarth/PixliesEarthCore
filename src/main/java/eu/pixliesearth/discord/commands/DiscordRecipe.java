package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.listeners.CustomMachineCommandListener;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Material;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;
import java.util.Set;

public class DiscordRecipe extends DiscordCommand {

    public DiscordRecipe() {
        super("recipe", "recipes");
    }

    @Override
    public void run(MessageCreateEvent event) {
        String[] split = event.getMessageContent().split(" ");
        if (split.length != 2) {
            event.getChannel().sendMessage("Wrong Usage! /recipe ITEM");
            return;
        }
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        if (CustomItemUtil.getItemStackFromUUID(split[1]) == null) {
            event.getChannel().sendMessage("This item does not exist.");
            return;
        }
        List<CustomRecipe> list = CustomMachineCommandListener.getRecipesOfUUIDInOrderedList(split[1]);
        CustomRecipe recipe = null;
        if (list.size() > 0)
            recipe = list.get(0);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(split[1]);
        builder.setThumbnail(getImage(CustomItemUtil.getItemStackFromUUID(split[1]).getType()));
        if (recipe != null) {
            int i = 0;
            for (String s : recipe.getAsUUIDToAmountMap().keySet()) {
                if (s.equalsIgnoreCase("Minecraft:Air")) continue;
                builder.addInlineField("Slot " + i, recipe.getAsUUIDToAmountMap().get(s) + "x " + s);
                i++;
            }
            builder.setDescription("Crafted in: " + recipe.craftedInUUID());
        } else
            builder.addField("No recipe", "for this item");
        builder.setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")");
        event.getChannel().sendMessage(builder);
    }

    private String getImage(Material material) {
        switch (material) {
            case AIR:
                return "https://minecraftitemids.com/item/64/barrier.png";
            case WATER:
                return "https://minecraftitemids.com/item/64/water_bucket.png";
            case LAVA:
                return "https://minecraftitemids.com/item/64/lava_bucket.png";
        }
        return "https://minecraftitemids.com/item/64/" + material + ".png";
    }

}
