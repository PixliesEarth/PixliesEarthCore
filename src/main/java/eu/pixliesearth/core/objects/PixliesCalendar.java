package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import lombok.Data;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

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
        events.put(360, () -> {
            Bukkit.broadcastMessage("§cHappy new year!");
        });
    }

}
