package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.utils.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class NationElection {

    private String topic;
    private String startedBy;
    private Map<String, String> options;
    private Map<String, String> votes;
    private long start;
    private long duration;
    private boolean started;

    public boolean ended() {
        return System.currentTimeMillis() > (start + duration);
    }

    public UUID getStartedBy() {
        return UUID.fromString(startedBy);
    }

    public List<String> getOptionsFormatted() {
        List<String> lines = new ArrayList<>();
        Map<String, Integer> votesByOption = new HashMap<>();
        for (String option : votes.values()) {
            votesByOption.putIfAbsent(option, 0);
            votesByOption.put(option, votesByOption.get(option) + 1);
        }
        // STRING 1 = COLOR
        // STRING 2 = NAME
        for (Map.Entry<String, String> entry : options.entrySet())
            lines.add(entry.getKey() + entry.getValue() + "§8[" + Methods.getProgressBar(votesByOption.get(entry.getValue()), votesByOption.size(), 7, "|", entry.getKey(), "§7") + "§8]");
        return lines;
    }

}
