package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
        for (Map.Entry<UUID, String> entry : Main.getInstance().getUtilLists().chatQueue.entrySet()) {
            DiscordMessage dm = new DiscordMessage.Builder()
                    .withUsername(Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .withContent(ChatColor.stripColor(entry.getValue().replace("@", "").replace("discord.gg", "").replace("discord.com", "")))
                    .withAvatarURL("https://minotar.net/avatar/" + Bukkit.getOfflinePlayer(entry.getKey()).getName())
                    .build();
            Main.getInstance().getUtilLists().webhook.sendMessage(dm);
            Main.getInstance().getUtilLists().chatQueue.remove(entry.getKey());
        }
    }

}
