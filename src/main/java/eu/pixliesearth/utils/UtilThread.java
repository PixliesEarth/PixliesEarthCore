package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordMessage;
import eu.pixliesearth.localization.Lang;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class UtilThread extends Thread {

    private final Map<Integer, UtilTask> runnables = new HashMap<>();

    @Override
    public void run() {
        while(true) {
            //Tick
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Data
    @AllArgsConstructor
    class UtilTask {

        private long period;
        private Runnable runnable;

    }

    public void registerTask(Runnable runnable, long period) {
        runnables.put(runnables.size(), new UtilTask(period, runnable));
    }

    private void tick() {

        // GUN DELAYS
        Main.getInstance().getUtilLists().waitingGuns.entrySet().removeIf(item -> item.getValue().getRemaining() <= 0);

        for (Map.Entry<UUID, String> entry : Main.getInstance().getUtilLists().chatQueue.entrySet()) {
            DiscordMessage dm = new DiscordMessage.Builder()
                    .withUsername(Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .withContent(ChatColor.stripColor(entry.getValue().replace("@", "").replace("discord.gg", "").replace("discord.com", "")))
                    .withAvatarURL("https://minotar.net/avatar/" + Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .build();
            Main.getInstance().getUtilLists().webhook.sendMessage(dm);
            System.out.println(Bukkit.getOfflinePlayer(entry.getKey()).getName());
            Main.getInstance().getUtilLists().chatQueue.remove(entry.getKey());
        }

        for (Map.Entry<String, String> entry : Main.getInstance().getUtilLists().dynmapQueue.entrySet()) {
            DiscordMessage dm = new DiscordMessage.Builder()
                    .withUsername(entry.getKey() + " @ pixlies.net/map")
                    .withContent(ChatColor.stripColor(entry.getValue().replace("@", "").replace("discord.gg", "").replace("discord.com", "")))
                    .withAvatarURL("https://freeiconshop.com/wp-content/uploads/edd/location-map-flat.png")
                    .build();
            Main.getInstance().getUtilLists().webhook.sendMessage(dm);
            Main.getInstance().getUtilLists().dynmapQueue.remove(entry.getKey());
        }

        Iterator<EmbedBuilder> iterator = Main.getInstance().getUtilLists().embedsToSend.listIterator();
        while (iterator.hasNext()) {
            Main.getInstance().getMiniMick().getChatChannel().sendMessage(iterator.next());
            iterator.remove();
        }

        for (Map.Entry<Boost.BoostType, Boost> entry : Main.getInstance().getUtilLists().boosts.entrySet()) {
            if (entry.getValue().getTimer().getRemaining() < 0) {
                Main.getInstance().getUtilLists().boosts.remove(entry.getKey());
                Lang.BOOST_EXPIRED.broadcast("%BOOST%;" + entry.getValue().getName());
            }
        }
    }

}
