package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Methods;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordBlockStats extends DiscordCommand {

    public DiscordBlockStats() {
        super("block", "getblock");
    }

    @Override
    public void run(MessageCreateEvent event) {
        String[] s = event.getMessageContent().split(" ");
        // SPLIT[0] = COMMAND
        // SPLIT[1] = X
        // SPLIT[2] = Y
        // SPLIT[3] = Z
        // SPLIT[4] = WORLD
        if (s.length != 5) {
            event.getChannel().sendMessage("Usage: /getblock X Y Z World");
            return;
        }
        if (!a(s[1]) || !a(s[2]) || !a(s[3])) {
            event.getChannel().sendMessage("Invalid location.");
            return;
        }
        World world = Bukkit.getWorld("world");
        if (s[4].equalsIgnoreCase("nether"))
            world = Bukkit.getWorld("world_nether");
        else if (s[4].equalsIgnoreCase("end"))
            world = Bukkit.getWorld("world_the_end");
        else if (Bukkit.getWorld(s[4]) != null)
            world = Bukkit.getWorld(s[4]);
        Location loc = new Location(world, Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        event.getChannel().sendMessage(
                new EmbedBuilder()
                        .setTitle("Block Information")
                        .setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar())
                        .addInlineField("UUID", CustomItemUtil.getUUIDFromLocation(loc))
                        .addInlineField("Energy", h.getPowerAtLocation(loc) != null ? Methods.convertEnergyDouble(h.getPowerAtLocation(loc)) : "No energy!")
                        .addInlineField("Temperature", h.getTempratureAtLocation(loc) != null ? Methods.round(h.getTempratureAtLocation(loc), 2) + "°C" : "0°C")
                        .setImage(getImage(loc.getBlock().getType()))
        );
    }

    private boolean a(String i) {
        return NumberUtils.isNumber(i);
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
