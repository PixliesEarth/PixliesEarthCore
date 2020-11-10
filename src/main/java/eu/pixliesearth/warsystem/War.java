package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class War {

    private static final Main instance = Main.getInstance();

    private String id;
    private String mainAggressor;
    private String mainDefender;
    private List<String> aggressorIds;
    private List<String> defenderIds;
    private boolean declareAble;
    private boolean running;
    private Map<String, Timer> timers;

    public War(String mainAggressor, String mainDefender, List<String> aggressorIds, List<String> defenderIds) {
        this.id = Methods.generateId(7);
        this.mainAggressor = mainAggressor;
        this.mainDefender = mainDefender;
        this.aggressorIds = aggressorIds;
        this.defenderIds = defenderIds;
        this.declareAble = false;
        this.running = false;
        this.timers = new HashMap<>();
    }

    @SneakyThrows
    public boolean justifyWarGoal() {
        Nation aggressor = Nation.getById(mainAggressor);
        Nation defender = Nation.getById(mainDefender);
        double cost = 1 + ((aggressor.getCurrentEra().getNumber() - defender.getCurrentEra().getNumber()) * 10) + (aggressor.getXpPoints() / 20);
        if (!aggressor.hasPoliticalPower(cost))
            return false;
        if (aggressor.getExtras().containsKey("WAR:" + defender.getNationId()))
            return false;

        this.timers.put("warGoalJustification", new Timer(259_200_000));
        StringBuilder mentionsBuilder = new StringBuilder();
        for (String s : defender.getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (profile.discordIsSynced())
                mentionsBuilder.append(MiniMick.getApi().getUserById(profile.getDiscord()).get().getMentionTag()).append(", ");
        }
        if (mentionsBuilder.length() > 0) instance.getMiniMick().getChatChannel().sendMessage("Hey! " + mentionsBuilder.toString() + "**" + aggressor.getName() + "** just started justifying a war-goal against your nation. This will take " + Methods.getTimeAsString(timers.get("warGoalJustification").getRemaining(), false) + ".");
        return true;
    }

    public boolean declare() {
        if (!declareAble) return false;
        this.running = true;
        return true;
    }

    public void handleKill(Profile killed, Profile killer, boolean KillerIsAttacker) {
        //TODO handleKill
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

    public boolean isRunning() {
        return running;
    }

    public static War getById(String id) {
        if (instance.getUtilLists().wars.containsKey(id))
            return instance.getUtilLists().wars.get(id);
        return null;
    }

}
