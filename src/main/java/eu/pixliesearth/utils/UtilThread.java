package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordMessage;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class UtilThread extends Thread {

    @Override
    public void run() {
        while(true) {
            //Tick
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Thread Sleep
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        // GUN DELAYS
        for (Map.Entry<UUID, Timer> entry : Main.getInstance().getUtilLists().waitingGuns.entrySet())
            if (entry.getValue().getRemaining() <= 0)
                Main.getInstance().getUtilLists().waitingGuns.remove(entry.getKey());

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
