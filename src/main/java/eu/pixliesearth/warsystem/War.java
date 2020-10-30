package eu.pixliesearth.warsystem;

import eu.pixliesearth.nations.entities.nation.Nation;
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

    public boolean justifyWarGoal() {
        Nation aggressor = Nation.getById(aggressorId);
        if (!aggressor.hasPoliticalPower(25D))
            return false;

        this.timers.put("warGoalJustification", new Timer(259_200_000));
        return true;
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
