package eu.pixliesearth.utils;

import eu.pixliesearth.core.custom.skills.Skill;
import eu.pixliesearth.core.custom.skills.SkillHandler;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SkillThread extends Thread {

    private boolean running = false;

    @Override
    public void run() {
        while (running) {
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MINUTES.sleep(15);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        Map<String, List<UUID>> newMap = new HashMap<>();
        for (Skill skill : SkillHandler.skills) {
            Map<UUID, Integer> map = new HashMap<>();
            SkillHandler.skillMap.entrySet().parallelStream().forEach((entry) -> map.put(entry.getKey(), entry.getValue().getOrDefault(skill.getSkillUUID(), 0)));
            List<UUID> list = new LinkedList<>();
            entriesSortedByValues(map).forEach((entry) -> list.add(0, entry.getKey()));
            newMap.put(skill.getSkillUUID(), list);
        }
        SkillHandler.leaderboardMap = newMap;
    }

    public void startThread() {
        running = true;
        this.start();
    }

    public void stopThread() {
        running = false;
    }

    private <K,V extends Comparable<? super V>>SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
