package eu.pixliesearth.warsystem;

import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Timer;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class War {

    private String mainAggressor;
    private String mainDefender;
    private List<String> aggressorIds;
    private List<String> defenderIds;
    private boolean declareAble;
    private Map<String, Timer> timers;

    public War(String mainAggressor, String mainDefender, List<String> aggressorIds, List<String> defenderIds) {
        this.mainAggressor = mainAggressor;
        this.mainDefender = mainDefender;
        this.aggressorIds = aggressorIds;
        this.defenderIds = defenderIds;
        this.declareAble = false;
        this.timers = new HashMap<>();
    }

    public boolean justifyWarGoal() {
        Nation aggressor = Nation.getById(mainAggressor);
        Nation defender = Nation.getById(mainDefender);
        double cost = 1 + ((aggressor.getCurrentEra().getNumber() - defender.getCurrentEra().getNumber()) * 10) + (aggressor.getXpPoints() / 20);
        if (!aggressor.hasPoliticalPower(cost))
            return false;
        if (aggressor.getExtras().containsKey("WAR:" + defender.getNationId()))
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
