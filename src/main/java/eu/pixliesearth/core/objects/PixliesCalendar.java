package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.commands.util.BroadcastCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class PixliesCalendar extends Thread {

    private int day;
    private int month;
    private int year;
    private long prevTime;
    private long currTime;
    private boolean exit = false;
    private static final int daysInYear = 360;
    private static final int daysInMonth = 30;
    private static final int monthsInYear = 12;
    private static final Map<Integer, Runnable> events = new HashMap<>();

    public PixliesCalendar(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDayInYear() {
        return month * daysInMonth + day;
    }

    public boolean day() {
        return Bukkit.getWorlds().get(0).getTime() < 12300 || Bukkit.getWorlds().get(0).getTime() > 23850;
    }

    public void dayPassed() {
        this.day++;
        if (this.day > daysInMonth) {
            this.month++;
            this.day = 1;
        }
        if (this.month > monthsInYear) {
            this.year++;
            this.day = 1;
            this.month = 1;
        }
        if (events.containsKey(getDayInYear())) events.get(getDayInYear()).run();
        save();
    }

    public PixliesSeasons getSeason() {
        switch (month) {
            case 1:
            case 2:
            case 3:
                return PixliesSeasons.SPRING;
            case 4:
            case 5:
            case 6:
                return PixliesSeasons.SUMMER;
            case 7:
            case 8:
            case 9:
                return PixliesSeasons.AUTUMN;
            case 10:
            case 11:
            case 12:
                return PixliesSeasons.WINTER;
        }
        return PixliesSeasons.SPRING;
    }

    public String formatDate() {
        return day + "/" + month + "/" + year;
    }

    public void startRunner() {
        prevTime = 0L;
        currTime = (Bukkit.getWorlds().get(0).getTime() + 6000L) % 24000L;
        this.start();
    }

    public void run() {
        while (!exit) {
            this.prevTime = this.currTime;
            this.currTime = (Bukkit.getWorlds().get(0).getTime() + 6000L) % 24000L;
            if (this.currTime < this.prevTime) dayPassed();
        }
    }

    public void stopRunner() {
        this.exit = true;
    }

    public void save() {
        Main instance = Main.getInstance();
        instance.getCalendarCfg().getConfiguration().set("date", day + "/" + month + "/" + year);
        instance.getCalendarCfg().save();
    }

    static {
        // NEW YEAR
        events.put(360, () -> {
            BroadcastCommand.broadcastDiscord("Happy Pixlies New Year!", Bukkit.getConsoleSender());
            Bukkit.broadcastMessage("§6Happy Pixlies New Year!");
            for (Player player : Bukkit.getOnlinePlayers())
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        });
    }

    public enum PixliesSeasons {

        SPRING("\uD83C\uDF33"),
        SUMMER("\uD83C\uDFD6"),
        AUTUMN("\uD83C\uDF42"),
        WINTER("☃");

        private @Getter final String icon;

        PixliesSeasons(String icon) {
            this.icon = icon;
        }

    }

}
