package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

@Data
@AllArgsConstructor
public class NationElection {

    // ALL COLOUR OPTIONS
    public static final String[] colorOptions = new String[]{
            "§1",
            "§2",
            "§3",
            "§4",
            "§5",
            "§6",
            "§7",
            "§8",
            "§9",
            "§a",
            "§b",
            "§c",
            "§d",
            "§e"
    };

    private String id;
    private String topic;
    private String startedBy;
    private Map<String, String> options;
    private Map<String, String> votes;
    private Long start;
    private Long duration;
    private boolean started;

    public static NationElection create(String topic, Player starter) {
        return new NationElection(Methods.generateId(3), topic, starter.getUniqueId().toString(), new HashMap<>(), new HashMap<>(), null, Timer.DAY * 2, false);
    }

    public boolean ended() {
        return System.currentTimeMillis() > (start + duration);
    }

    public UUID getStartedBy() {
        return UUID.fromString(startedBy);
    }

    public List<String> getOptionsFormatted() {
        List<String> lines = new ArrayList<>();

        Map<String, Integer> votesByOption = getVotesByOption();

        // STRING 1 = COLOR
        // STRING 2 = NAME
        for (Map.Entry<String, String> entry : options.entrySet()) {
            if (started && ended() && entry.getValue().equalsIgnoreCase(getWinner())) {
                lines.add("§a✔ " + entry.getKey() + entry.getValue() + "§8[" + Methods.getProgressBar(votesByOption.get(entry.getValue()), votesByOption.size(), 7, "|", entry.getKey(), "§7") + "§8]");
            } else {
                lines.add(entry.getKey() + entry.getValue() + "§8[" + Methods.getProgressBar(votesByOption.get(entry.getValue()), votesByOption.size(), 7, "|", entry.getKey(), "§7") + "§8]");
            }
        }
        return lines;
    }

    public void addOption(String option) {
        for (String chatColor : colorOptions) {
            if (options.containsKey(chatColor)) continue;
            options.put(chatColor, option);
            break;
        }
    }

    public Map<String, Integer> getVotesByOption() {
        Map<String, Integer> votesByOption = new HashMap<>();
        for (String option : votes.values()) {
            votesByOption.putIfAbsent(option, 0);
            votesByOption.put(option, votesByOption.get(option) + 1);
        }
        return votesByOption;
    }

    public String getWinner() {
        return Methods.sortByValue(getVotesByOption()).entrySet().iterator().next().getKey();
    }

    public void vote(Player player, String option) {
        if (votes.containsKey(player.getUniqueId().toString())) {
            player.sendMessage(Lang.NATION + "§7You have already voted in this eleciton.");
            return;
        }
        votes.put(player.getUniqueId().toString(), option);
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        Nation nation = profile.getCurrentNation();
        nation.addElection(this);
        player.sendMessage(Lang.NATION + "§7Thank you for voting in this election.");
    }

}
