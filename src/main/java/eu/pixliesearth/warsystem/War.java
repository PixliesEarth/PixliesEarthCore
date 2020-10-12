package eu.pixliesearth.warsystem;

import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class War {

    private String aggressorId;
    private String defenderId;
    private boolean declareAble;
    private Map<String, Timer> timers;

    public void justifyWarGoal() {
        this.timers.put("warGoalJustification", new Timer(259_200_000));
    }

    public void tick() {
        if (!declareAble) {
            if (this.timers.containsKey("warGoalJustification")) {
                if (this.timers.get("warGoalJustification").hasExpired()) {
                    this.declareAble = true;
                    this.timers.remove("warGoalJustification");
                }
            }
        }
    }

}
