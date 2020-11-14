package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordMessage;
import eu.pixliesearth.localization.Lang;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
        Iterator<Map.Entry<UUID, Timer>> it = Main.getInstance().getUtilLists().waitingGuns.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Timer> item = it.next();
            if (item.getValue().getRemaining() <= 0)
                it.remove();
        }

        for (Map.Entry<UUID, String> entry : Main.getInstance().getUtilLists().chatQueue.entrySet()) {
            DiscordMessage dm = new DiscordMessage.Builder()
                    .withUsername(Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .withContent(ChatColor.stripColor(entry.getValue().replace("@", "").replace("discord.gg", "").replace("discord.com", "")))
                    .withAvatarURL("https://minotar.net/avatar/" + Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .build();
            Main.getInstance().getUtilLists().webhook.sendMessage(dm);
            Main.getInstance().getUtilLists().chatQueue.remove(entry.getKey());
        }

        for (Map.Entry<Boost.BoostType, Boost> entry : Main.getInstance().getUtilLists().boosts.entrySet()) {
            if (entry.getValue().getTimer().getRemaining() < 0) {
                Main.getInstance().getUtilLists().boosts.remove(entry.getKey());
                Lang.BOOST_EXPIRED.broadcast("%BOOST%;" + entry.getValue().getName());
            }
        }
    }

}
