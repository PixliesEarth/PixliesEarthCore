package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.*;

@Data
@AllArgsConstructor
public class War {

    private static final Main instance = Main.getInstance();

    private String id;
    private String mainAggressor;
    private String mainDefender;
    private Map<UUID, WarParticipant> players;
    private Map<WarParticipant.WarSide, Integer> left;
    private boolean declareAble;
    private boolean running;
    private boolean declared;
    private Map<String, Timer> timers;

    public War(String mainAggressor, String mainDefender) {
        this.id = Methods.generateId(7);
        this.mainAggressor = mainAggressor;
        this.mainDefender = mainDefender;
        this.players = new HashMap<>();
        left = new HashMap<>();
        this.declareAble = false;
        this.running = false;
        this.declared = false;
        this.timers = new HashMap<>();
    }

    public String getTimeUntilDeclarable() {
        return Methods.getTimeAsString(timers.get("warGoalJustification").getRemaining(), false);
    }

    public static double getCost(Nation aggressor, Nation defender) {
        return 1 + ((aggressor.getCurrentEra().getNumber() - defender.getCurrentEra().getNumber()) * 10) + (aggressor.getXpPoints() / 20);
    }

    @SneakyThrows
    public boolean justifyWarGoal() {
        Nation aggressor = Nation.getById(mainAggressor);
        Nation defender = Nation.getById(mainDefender);
        double cost = getCost(aggressor, defender);
        if (!aggressor.hasPoliticalPower(cost))
            return false;
        if (aggressor.getExtras().containsKey("WAR:" + mainDefender))
            return false;

        this.timers.put("warGoalJustification", new Timer(259_200_000));

        instance.getMiniMick().getChatChannel().sendMessage(new EmbedBuilder().setTitle("**" + aggressor.getName() + "** just started justifying a war-goal against **" + getDefenderInstance().getName() + "**.").setDescription("This will take " + Methods.getTimeAsString(timers.get("warGoalJustification").getRemaining(), false) + "."));
        aggressor.getExtras().put("WAR:" + mainDefender, id);
        aggressor.setXpPoints(aggressor.getXpPoints() - cost);
        aggressor.save();
        instance.getUtilLists().wars.put(id, this);
        return true;
    }

    @SneakyThrows
    public boolean declare() {
        if (!declareAble) return false;
        boolean isOn = false;
        for (Player player : getDefenderInstance().getOnlineMemberSet())
            if (Permission.hasNationPermission(instance.getProfile(player.getUniqueId()), Permission.DECLARE_WAR)) {
                isOn = true;
                break;
            }
        if (!isOn) return false;
        this.timers.put("gracePeriod", new Timer(600_000));
        StringBuilder mentionsBuilder = new StringBuilder();
        for (String s : Nation.getById(this.mainDefender).getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (profile.discordIsSynced())
                mentionsBuilder.append(MiniMick.getApi().getUserById(profile.getDiscord()).get().getMentionTag()).append(", ");
        }
        if (mentionsBuilder.length() > 0) instance.getMiniMick().getChatChannel().sendMessage("Hey! " + mentionsBuilder.toString() + "**" + Nation.getById(mainAggressor).getName() + "** just declared a war against your nation. The grace period will take " + Methods.getTimeAsString(timers.get("gracePeriod").getRemaining(), false) + ".");
        instance.getMiniMick().getChatChannel().sendMessage(new EmbedBuilder().setTitle("**" + getAggressorInstance().getName() + "** just declared war on **" + getDefenderInstance().getName() + "**.").setDescription("The grace period will take " + Methods.getTimeAsString(timers.get("gracePeriod").getRemaining(), false) + "."));
        declared = true;
        instance.setCurrentWar(this);
        return true;
    }

    public void remove() {
        Main.getWarCollection().findOneAndDelete(new Document("id", id));
    }

    public boolean skipGrace() {
        if (!this.timers.containsKey("gracePeriod")) return false;
        start();
        return true;
    }

    @SneakyThrows
    public void start() {
        Main.getWarCollection().findOneAndDelete(new Document("id", id));
        Nation aggressor = Nation.getById(mainAggressor);
        aggressor.getExtras().remove("WAR:" + mainDefender);
        aggressor.save();
        this.timers.remove("gracePeriod");
        this.running = true;
        left.put(WarParticipant.WarSide.DEFENDER, 0);
        left.put(WarParticipant.WarSide.AGGRESSOR, 0);
        for (Player player : Nation.getById(this.mainDefender).getOnlineMemberSet())
            addPlayer(player, new WarParticipant(this.mainDefender, WarParticipant.WarSide.DEFENDER, id));
        for (Player player : Nation.getById(this.mainAggressor).getOnlineMemberSet())
            addPlayer(player, new WarParticipant(this.mainAggressor, WarParticipant.WarSide.AGGRESSOR, id));
        broadcastDiscord(Nation.getById(mainDefender), "the war between you and **" + Nation.getById(mainAggressor) + "** just started.");
    }

    @SneakyThrows
    public void cancel() {
        Nation aggressor = getAggressorInstance();
        aggressor.getExtras().remove("WAR:" + mainDefender);
        aggressor.save();
        instance.getUtilLists().wars.remove(this.getId());
        Main.getWarCollection().findOneAndDelete(new Document("id", id));
        broadcastDiscord(getDefenderInstance(), "**" + getAggressorInstance().getName() + "** just cancelled their war justification against you.");
    }

    public WarParticipant.WarSide getCurrentWinner() {
        if (left.get(WarParticipant.WarSide.AGGRESSOR) > left.get(WarParticipant.WarSide.DEFENDER)) {
            return WarParticipant.WarSide.AGGRESSOR;
        } else {
            return WarParticipant.WarSide.DEFENDER;
        }
    }

    @SneakyThrows
    public void stop(WarParticipant.WarSide winner) {
        this.running = false;
        Nation winnerNation;
        Nation loserNation;
        switch (winner) {
            case AGGRESSOR:
                winnerNation = Nation.getById(mainAggressor);
                loserNation = Nation.getById(mainDefender);
                break;
            case DEFENDER:
                winnerNation = Nation.getById(mainDefender);
                loserNation = Nation.getById(mainAggressor);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + winner);
        }
        for (UUID uuid : instance.getUtilLists().bannedInWar) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban " + Bukkit.getOfflinePlayer(uuid).getName());
            Profile profile = instance.getProfile(uuid);
            if (profile.discordIsSynced())
                MiniMick.getApi().getUserById(profile.getDiscord()).get().openPrivateChannel().get().sendMessage("§7Since the war is over, you have been unbanned. Thank you for playing on PixliesNet!");
        }
        instance.getUtilLists().playersInWar.clear();
        broadcastDiscord(new EmbedBuilder().setTitle("War ended!").setDescription(winnerNation.getName() + "just won a war against " + loserNation.getName()));
        instance.setCurrentWar(null);
    }

    public void handleLeave(Profile left) {
        if (!running) return;
        this.left.put(players.get(left.getUUID()).getSide(), this.left.get(players.get(left.getUUID()).getSide()) - 1);
        players.remove(left.getUUID());
        instance.getGulag().handleLeave(left.getAsPlayer());
    }

    public void handleKill(Profile killed) {
        if (!running) return;
        if (instance.getUtilLists().inGulag.contains(killed.getUUID())) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + killed.getAsPlayer().getName() + " &7You are &cbanned &7until the war is over.");
            instance.getUtilLists().inGulag.remove(killed.getUUID());
            instance.getUtilLists().bannedInWar.add(killed.getUUID());
            players.remove(killed.getUUID());
            left.put(players.get(killed.getUUID()).getSide(), left.get(players.get(killed.getUUID()).getSide()) - 1);
            return;
        }
        instance.getGulag().addPlayer(killed.getAsPlayer(), players.get(killed.getUUID()).getSide());
    }

    public void tick() {
        if (!declareAble) {
            if (this.timers.containsKey("warGoalJustification"))
                if (this.timers.get("warGoalJustification").hasExpired())
                    makeDeclarable();
        } else {
            if (this.timers.containsKey("gracePeriod"))
                if (this.timers.get("gracePeriod").hasExpired())
                    start();
            if (running) {
                if (this.left.get(WarParticipant.WarSide.DEFENDER) <= 0) {
                    stop(WarParticipant.WarSide.AGGRESSOR);
                } else if (this.left.get(WarParticipant.WarSide.AGGRESSOR) <= 0) {
                    stop(WarParticipant.WarSide.DEFENDER);
                }
            }
        }
    }

    @SneakyThrows
    public boolean makeDeclarable() {
        if (this.isDeclareAble()) return false;
        this.declareAble = true;
        this.timers.remove("warGoalJustification");
        for (String s : getAggressorInstance().getMembers()) {
            Profile profile = instance.getProfile(UUID.fromString(s));
            if (!profile.discordIsSynced()) continue;
            if (!Permission.hasNationPermission(profile, Permission.DECLARE_WAR)) continue;
            MiniMick.getApi().getUserById(profile.getDiscord()).get().openPrivateChannel().get().sendMessage("**Congrats!** Your war-goal against **" + getDefenderInstance().getName() + "** has just been justified! You may now declare the war.");
        }
        return true;
    }

    public boolean isRunning() {
        return running;
    }

    public void addPlayer(Profile profile, WarParticipant participant) {
        players.put(profile.getUUID(), participant);
        instance.getUtilLists().playersInWar.put(profile.getUUID(), this);
        left.put(participant.getSide(), left.get(participant.getSide()) + 1);
    }

    public void addPlayer(Player player, WarParticipant participant) {
        players.put(player.getUniqueId(), participant);
        instance.getUtilLists().playersInWar.put(player.getUniqueId(), this);
        left.put(participant.getSide(), left.get(participant.getSide()) + 1);
    }

    public static War getById(String id) {
        if (instance.getUtilLists().wars.containsKey(id))
            return instance.getUtilLists().wars.get(id);
        return null;
    }
    
    public static List<War> getWars(Nation nation) {
        List<War> returner = new ArrayList<>();
        for (War war : instance.getUtilLists().wars.values())
            if (war.getMainAggressor().equals(nation.getNationId()))
                if (!war.isDeclared())
                    returner.add(war);
        return returner;
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

    @SneakyThrows
    public void broadcastDiscord(String message) {
        instance.getMiniMick().getChatChannel().sendMessage(message);
    }

    @SneakyThrows
    public void broadcastDiscord(EmbedBuilder message) {
        instance.getMiniMick().getChatChannel().sendMessage(message);
    }

    public void broadcastInGame(String message) {
        for (UUID uuid : players.keySet())
            Bukkit.getPlayer(uuid).sendMessage(message);
    }

    public void backup() {
        Document war = new Document("id", id);
        Document found = Main.getWarCollection().find(war).first();
        war.append("json", instance.getGson().toJson(this));
        if (found != null)
            Main.getWarCollection().deleteOne(found);
        Main.getWarCollection().insertOne(war);
    }

    public Nation getDefenderInstance() {
        return Nation.getById(mainDefender);
    }

    public Nation getAggressorInstance() {
        return Nation.getById(mainAggressor);
    }

}
