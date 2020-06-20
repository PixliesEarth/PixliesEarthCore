package eu.pixliesearth.nations.entities.nation.ranks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Rank {

    private String name;
    private String prefix;
    private List<String> permissions;

    public static Rank getFromMap(Map<String, Object> map) {
        return new Rank((String) map.get("name"), (String) map.get("prefix"), (List<String>) map.get("permissions"));
    }

}
