package eu.pixliesearth.nations.entities.nation.ranks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Rank {

    private String name;
    private String prefix;
    private List<String> permissions;

    public static Rank MEMBER() {
        List<String> perms = new ArrayList<>();
        perms.add(Permission.BUILD.name());
        perms.add(Permission.INTERACT.name());
        perms.add(Permission.CLAIM.name());
        return new Rank("member", "§b**", perms);
    }

    public static Rank ADMIN() {
        List<String> perms = new ArrayList<>(MEMBER().permissions);
        perms.add(Permission.INVITE.name());
        perms.add(Permission.MODERATE.name());
        perms.add(Permission.MANAGE_SETTLEMENTS.name());
        perms.add(Permission.UNCLAIM.name());
        return new Rank("admin", "§c***", perms);
    }

}
