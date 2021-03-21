package eu.pixliesearth.core.objects.punishments;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Punishment {

    private PunishmentType type;
    private long creation;
    private String punisher;
    private String punished;
    private long duration;
    private String reason;

    public Punishment(Map<String, String> map) {
        this.type = PunishmentType.valueOf(map.get("type"));
        this.creation = Long.parseLong(map.get("creation"));
        this.punisher = map.get("punisher");
        this.punished = map.get("punished");
        this.duration = Long.parseLong(map.get("duration"));
        this.reason = map.get("reason");
    }

    public Map<String, String> serialize() {
        return new HashMap<>(){{
            put("type", type.name());
            put("creation", Long.toString(creation));
            put("punisher", punisher);
            put("punished", punished);
            put("duration", Long.toString(duration));
            put("reason", reason);
        }};
    }

}
