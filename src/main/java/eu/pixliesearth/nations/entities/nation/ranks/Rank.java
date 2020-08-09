package eu.pixliesearth.nations.entities.nation.ranks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Rank {

    private String name;
    private String prefix;
    private double priority;
    private List<String> permissions;

    public static Rank get(Map<String, Object> map) {
        return new Rank((String) map.get("name"), (String) map.get("prefix"), (double) map.get("priority"),(List<String>) map.get("permissions"));
    }

    public Map<String, Object> toMap() {
        Map<String, Object> returner = new HashMap<>();
        returner.put("name", name);
        returner.put("prefix", prefix);
        returner.put("priority", priority);
        returner.put("permissions", permissions);
        return returner;
    }

    public static Rank MEMBER() {
        List<String> perms = new ArrayList<>();
        perms.add(Permission.BUILD.name());
        perms.add(Permission.INTERACT.name());
        perms.add(Permission.CLAIM.name());
        return new Rank("member", "§b**", 222, perms);
    }

    public static Rank ADMIN() {
        List<String> perms = new ArrayList<>(MEMBER().permissions);
        perms.add(Permission.INVITE.name());
        perms.add(Permission.MODERATE.name());
        perms.add(Permission.MANAGE_SETTLEMENTS.name());
        perms.add(Permission.UNCLAIM.name());
        perms.add(Permission.EDIT_RANKS.name());
        perms.add(Permission.BANK.name());
        perms.add(Permission.PURCHASE_UPGRADES.name());
        return new Rank("admin", "§c***", 333, perms);
    }

}
