package eu.pixliesearth.core.economy;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import eu.pixliesearth.Main;
import eu.pixliesearth.utils.Timer;
import org.bson.Document;

import java.util.*;

public class BalTopThread extends Thread {

    public static final Map<UUID, Double> balTopMap = new LinkedHashMap<>();
    public static Date date;

    public boolean running = false;

    public void run() {
        while (running) {
            try {
                tick();
                sleep((1000 * 60) * 5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        System.out.println("Updating baltop");
        balTopMap.clear();
        FindIterable<Document> cursor = Main.getPlayerCollection().find().sort(Sorts.descending("balance")).limit(10);
        for (Document document : cursor) {
            if (!document.containsKey("balance") || !document.containsKey("uniqueId")) continue;
            balTopMap.put(UUID.fromString(document.getString("uniqueId")), document.getDouble("balance"));
        }
        date = new Date();
    }

    public void startThread() {
        this.running = true;
        this.start();
    }

    public void stopThread() {
        this.running = false;
    }

}
