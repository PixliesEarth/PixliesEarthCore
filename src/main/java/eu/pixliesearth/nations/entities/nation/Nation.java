package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Data
@AllArgsConstructor
public class Nation {

    private String nationId;
    private String name;
    private String description;
    private String era;
    private String ideology;
    private String religion;
    private int xpPoints;
    private double money;
    private String leader;
    private Map<String, Map<String, Object>> ranks;
    private List<String> members;
    private List<String> chunks;
    private List<String> allyRequests;
    private List<String> allies;
    private List<String> pacts;
    private List<Object> settlements;
    private Map<String, Object> extras;


    // ADVANCED METHODS
    public void backup() {
        Document nation = new Document("nationId", nationId);
        Document found = Main.getNationCollection().find(nation).first();
        nation.append("name", name);
        nation.append("description", description);
        nation.append("era", era);
        nation.append("ideology", ideology);
        nation.append("religion", religion);
        nation.append("xpPoints", xpPoints);
        nation.append("money", money);
        nation.append("leader", leader);
        nation.append("ranks", ranks);
        nation.append("members", members);
        nation.append("chunks", chunks);
        nation.append("allyRequests", allyRequests);
        nation.append("allies", allies);
        nation.append("pacts", pacts);
        nation.append("settlements", settlements);
        nation.append("extras", extras);
        if (found != null) {
            Main.getNationCollection().deleteOne(found);
        }
        Main.getNationCollection().insertOne(nation);
    }

    public Nation save() {
        NationManager.nations.put(nationId, this);
        return this;
    }

    public void remove() {
        for (String member : members)
            Main.getInstance().getProfile(UUID.fromString(member)).removeFromNation();
        for (String s : getChunks())
            NationChunk.fromString(s).unclaim();
        Document found = Main.getNationCollection().find(new Document("nationId", nationId)).first();
        if (found != null)
            Main.getNationCollection().deleteOne(found);
        NationManager.nations.remove(nationId);
        NationManager.names.remove(name);
    }

    public boolean rename(String newName) {
        if (getByName(newName) != null)
            return false;
        NationManager.names.remove(name);
        NationManager.names.put(newName, nationId);
        setName(newName);
        save();
        return true;
    }

    public int getOnlineMembers() {
        return (int) members.stream().filter(string -> Bukkit.getPlayer(UUID.fromString(string)) != null).count();
    }

    public Set<Player> getOnlineMemberSet() {
        Set<Player> oplayers = new HashSet<>();
        for (String s : getMembers())
            if (Bukkit.getPlayer(UUID.fromString(s)) != null)
                oplayers.add(Bukkit.getPlayer(UUID.fromString(s)));
        return oplayers;
    }

    public int broadcastMembers(String message) {
        int i = 0;
        for (Player player : getOnlineMemberSet())
            player.sendMessage(message); i++;
        return i;
    }

    public boolean isAlliedWith(String nationId) {
        return allies.contains(nationId);
    }

    public static NationRelation getRelation(String n1, String n2) {
        if (n1.equals(n2))
            return NationRelation.SAME;
        if (getById(n1).isAlliedWith(n2))
            return NationRelation.ALLY;
        return NationRelation.NEUTRAL;
    }

    public static Nation getById(String id) {
        return NationManager.nations.get(id);
    }

    public static Nation getByName(String name) {
        return getById(NationManager.names.get(name));
    }

    public enum NationRelation {

        ALLY('d'),
        NEUTRAL('f'),
        SAME('b'),
        PACT('3');

        public char colChar;

        NationRelation(char colChar) {
            this.colChar = colChar;
        }

    }

}
