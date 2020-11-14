package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class War {

    private static final Main instance = Main.getInstance();

    private String id;
    private String mainAggressor;
    private String mainDefender;
    private Map<UUID, WarParticipant> players;
    private boolean declareAble;
    private boolean running;
    private Map<String, Timer> timers;

    public War(String mainAggressor, String mainDefender) {
        this.id = Methods.generateId(7);
        this.mainAggressor = mainAggressor;
        this.mainDefender = mainDefender;
        this.players = new HashMap<>();
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
        if (aggressor.getExtras().containsKey("WAR:" + mainDefender))
            return false;

        this.timers.put("warGoalJustification", new Timer(259_200_000));
        StringBuilder mentionsBuilder = new StringBuilder();
        for (String s : defender.getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (profile.discordIsSynced())
                mentionsBuilder.append(MiniMick.getApi().getUserById(profile.getDiscord()).get().getMentionTag()).append(", ");
        }
        if (mentionsBuilder.length() > 0) instance.getMiniMick().getChatChannel().sendMessage("Hey! " + mentionsBuilder.toString() + "**" + aggressor.getName() + "** just started justifying a war-goal against your nation. This will take " + Methods.getTimeAsString(timers.get("warGoalJustification").getRemaining(), false) + ".");
        aggressor.getExtras().put("WAR:" + mainDefender, id);
        return true;
    }

    @SneakyThrows
    public boolean declare() {
        if (!declareAble) return false;
        this.timers.put("gracePeriod", new Timer(600_000));
        StringBuilder mentionsBuilder = new StringBuilder();
        for (String s : Nation.getById(this.mainDefender).getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (profile.discordIsSynced())
                mentionsBuilder.append(MiniMick.getApi().getUserById(profile.getDiscord()).get().getMentionTag()).append(", ");
        }
        if (mentionsBuilder.length() > 0) instance.getMiniMick().getChatChannel().sendMessage("Hey! " + mentionsBuilder.toString() + "**" + Nation.getById(mainAggressor).getName() + "** just declared a war against your nation. The grace period will take " + Methods.getTimeAsString(timers.get("gracePeriod").getRemaining(), false) + ".");
        return true;
    }

    @SneakyThrows
    public void start() {
        this.timers.remove("gracePeriod");
        this.running = true;
        broadcastDiscord(Nation.getById(mainDefender), "the war between you and **" + Nation.getById(mainAggressor) + "** just started.");
    }

    public void handleKill(Profile killed, Profile killer) {
        if (!running) return;
        if (instance.getUtilLists().inGulag.contains(killed.getUUID())) {
            killed.getAsPlayer().banPlayer("§7You are §cbanned §7until the war is over.");
            instance.getUtilLists().inGulag.remove(killed.getUUID());
            instance.getUtilLists().bannedInWar.add(killed.getUUID());
            return;
        }
        instance.getGulag().addPlayer(killed.getAsPlayer(), players.get(killed.getUUID()).getSide());
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
            if (this.timers.containsKey("gracePeriod"))
                if (this.timers.get("gracePeriod").hasExpired())
                    start();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void addPlayer(Profile profile, WarParticipant participant) {
        players.put(profile.getUUID(), participant);
    }

    public static War getById(String id) {
        if (instance.getUtilLists().wars.containsKey(id))
            return instance.getUtilLists().wars.get(id);
        return null;
    }

    @SneakyThrows
    public void broadcastDiscord(Nation n1, String message) {
        StringBuilder mentionsBuilder = new StringBuilder();
        for (String s : n1.getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (profile.discordIsSynced())
                mentionsBuilder.append(MiniMick.getApi().getUserById(profile.getDiscord()).get().getMentionTag()).append(", ");
        }
        if (mentionsBuilder.length() > 0) instance.getMiniMick().getChatChannel().sendMessage("Hey! " + mentionsBuilder.toString() + message + ".");
    }

}
