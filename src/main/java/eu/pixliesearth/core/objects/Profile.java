package eu.pixliesearth.core.objects;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import java.util.*;

@Data
@AllArgsConstructor
public class Profile {

    private static Main instance = Main.getInstance();

    private String uniqueId;
    private String discord;
    private boolean inNation;
    private double balance;
    private List<String> rececipts;
    private double energy;
    private String nationId;
    private List<String> invites;
    private boolean scoreboard;
    private String nationRank;
    private List<String> knownIps;
    private List<String> knownUsernames;
    private String nickname;
    private String messageSound;
    private boolean pingSound;
    private String chatColor;
    private int boosts;
    private String lastAt;
    private double pixliecoins;
    private Map<String, Object> extras;

    public static Profile get(UUID uuid) {
        Document profile = new Document("uniqueId", uuid.toString());
        Document found = Main.getPlayerCollection().find(profile).first();
        Profile data;
        if (found == null) {
            profile.append("discord", "NONE");
            profile.append("inNation", false);
            profile.append("balance", 4000.0);
            profile.append("receipts", new ArrayList<>());
            profile.append("energy", 10.0);
            profile.append("nationId", "NONE");
            profile.append("invites", new ArrayList<>());
            profile.append("scoreboard", true);
            profile.append("nationRank", "NONE");
            profile.append("knownIps", new ArrayList<>());
            profile.append("knownUsernames", new ArrayList<>());
            profile.append("nickname", "NONE");
            profile.append("messageSound", Sound.BLOCK_NOTE_BLOCK_PLING.name());
            profile.append("pingSound", true);
            profile.append("chatColor", "f");
            profile.append("boosts", 0);
            profile.append("lastAt", "NONE");
            profile.append("pixliecoins", 0D);
            profile.append("extras", new HashMap<>());
            Main.getPlayerCollection().insertOne(profile);
            data = new Profile(uuid.toString(), "NONE",false, 4000, new ArrayList<>(), 10.0, "NONE", new ArrayList<>(), true, "NONE", new ArrayList<>(), new ArrayList<>(), "NONE", Sound.BLOCK_NOTE_BLOCK_PLING.name(), true, "f",0, "NONE", 0D, new HashMap<>());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Profile for " + uuid.toString() + " created in Database.");
        } else {
            data = new Gson().fromJson(found.toJson(), Profile.class);
        }
        return data;
    }

    public void backup() {
        Document profile = new Document("uniqueId", uniqueId);
        Document found = Main.getPlayerCollection().find(profile).first();
        if (found == null) return;
        profile.append("discord", discord);
        profile.append("inNation", inNation);
        profile.append("balance", balance);
        profile.append("receipts", rececipts);
        profile.append("energy", energy);
        profile.append("nationId", nationId);
        profile.append("invites", invites);
        profile.append("scoreboard", scoreboard);
        profile.append("nationRank", nationRank);
        profile.append("knownIps", knownIps);
        profile.append("knownUsernames", knownUsernames);
        profile.append("nickname", nickname);
        profile.append("messageSound", messageSound);
        profile.append("pingSound", pingSound);
        profile.append("chatColor", chatColor);
        profile.append("boosts", boosts);
        profile.append("lastAt", lastAt);
        profile.append("pixliecoins", pixliecoins);
        profile.append("extras", extras);
        Main.getPlayerCollection().deleteOne(found);
        Main.getPlayerCollection().insertOne(profile);
        return;
    }

    public void addToNation(String id) {
        if (inNation) return;
        this.nationId = id;
        this.inNation = true;
        Nation nation = Nation.getById(id);
        nation.getMembers().add(uniqueId);
        nation.save();
        backup();
    }

    public void removeFromNation() {
        if (!isInNation()) return;
        this.inNation = false;
        this.nationId = "NONE";
        backup();
    }

    public static Profile getByDiscord(String discordId) {
        Document found = Main.getPlayerCollection().find(new Document("discord", discordId)).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), Profile.class);
        return null;
    }

}
