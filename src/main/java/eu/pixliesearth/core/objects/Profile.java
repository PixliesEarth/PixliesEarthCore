package eu.pixliesearth.core.objects;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

@Data
@AllArgsConstructor
public class Profile {

    private static Main instance = Main.getInstance();

    private String uniqueId;
    private String discord;
    private boolean inNation;
    private double balance;
    private List<String> receipts;
    private int playTime;
    private int elo;
    private String marriagePartner;
    private List<String> marriageRequests;
    private Map<String, String> relations;
    private double energy;
    private String nationId;
    private List<String> invites;
    private List<String> homes;
    private boolean scoreboard;
    private String nationRank;
    private List<String> knownUsernames;
    private String nickname;
    private String messageSound;
    private boolean pingSound;
    private String chatColor;
    private int boosts;
    private String lastAt;
    private double pixliecoins;
    private Map<String, Map<String, Object>> timers;
    private String favoriteColour;
    private String boardType;
    private String lang;
    private List<String> blocked;
    private Map<String, String> punishments;
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
            profile.append("playTime", 0);
            profile.append("elo", 0);
            profile.append("marriagePartner", "NONE");
            profile.append("marriageRequests", new ArrayList<>());
            profile.append("relations", new HashMap<>());
            profile.append("energy", 10.0);
            profile.append("nationId", "NONE");
            profile.append("invites", new ArrayList<>());
            profile.append("homes", new ArrayList<>());
            profile.append("scoreboard", true);
            profile.append("nationRank", "NONE");
            profile.append("knownUsernames", new ArrayList<>());
            profile.append("nickname", "NONE");
            profile.append("messageSound", Sound.BLOCK_NOTE_BLOCK_PLING.name());
            profile.append("pingSound", true);
            profile.append("chatColor", "f");
            profile.append("boosts", 0);
            profile.append("lastAt", "NONE");
            profile.append("pixliecoins", 0D);
            profile.append("timers", new HashMap<>());
            profile.append("favoriteColour", "§3");
            profile.append("boardType", ScoreboardAdapter.scoreboardType.STANDARD.name());
            profile.append("lang", "ENG");
            profile.append("blocked", new ArrayList<>());
            profile.append("banned", false);
            profile.append("extras", new HashMap<>());
            Main.getPlayerCollection().insertOne(profile);
            data = new Profile(uuid.toString(), "NONE",false, 4000, new ArrayList<>(), 0, 0,"NONE", new ArrayList<>(), new HashMap<>(), 10.0, "NONE", new ArrayList<>(), new ArrayList<>(), true, "NONE", new ArrayList<>(), "NONE", Sound.BLOCK_NOTE_BLOCK_PLING.name(), true, "f",0, "NONE", 0D, new HashMap<>(), "§3", ScoreboardAdapter.scoreboardType.STANDARD.name(), "ENG", new ArrayList<>(), new HashMap<>(), new HashMap<>());
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
        profile.append("receipts", receipts);
        profile.append("playTime", playTime);
        profile.append("elo", elo);
        profile.append("marriagePartner", marriagePartner);
        profile.append("marriageRequests", marriageRequests);
        profile.append("relations", relations);
        profile.append("energy", energy);
        profile.append("nationId", nationId);
        profile.append("invites", invites);
        profile.append("homes", homes);
        profile.append("scoreboard", scoreboard);
        profile.append("nationRank", nationRank);
        profile.append("knownUsernames", knownUsernames);
        profile.append("nickname", nickname);
        profile.append("messageSound", messageSound);
        profile.append("pingSound", pingSound);
        profile.append("chatColor", chatColor);
        profile.append("boosts", boosts);
        profile.append("lastAt", lastAt);
        profile.append("pixliecoins", pixliecoins);
        profile.append("timers", timers);
        profile.append("favoriteColour", favoriteColour);
        profile.append("boardType", boardType);
        profile.append("lang", lang);
        profile.append("blocked", blocked);
        profile.append("punishments", punishments);
        profile.append("extras", extras);
        Main.getPlayerCollection().deleteOne(found);
        Main.getPlayerCollection().insertOne(profile);
    }

    public void save() {
        instance.getUtilLists().profiles.put(UUID.fromString(uniqueId), this);
    }

    public String getDisplayName() {
        if (nickname.equalsIgnoreCase("NONE") || nickname.equalsIgnoreCase(""))
            return getAsOfflinePlayer().getName();
        return nickname;
    }

    public void addToNation(String id, Rank rank) {
        if (inNation) return;
        this.nationId = id;
        this.inNation = true;
        this.nationRank = rank.getName();
        Nation nation = Nation.getById(id);
        nation.getMembers().add(uniqueId);
        nation.save();
        save();
    }

    public boolean leaveNation() {
        if (!inNation)
            return false;
        Nation nation = getCurrentNation();
        nation.getMembers().remove(uniqueId);
        nation.save();
        this.nationId = "NONE";
        this.inNation = false;
        save();
        for (Player p : nation.getOnlineMemberSet())
            p.sendMessage(Lang.PLAYER_LEFT_NATION.get(p).replace("%PLAYER%", getAsOfflinePlayer().getName()));
        return true;
    }

    public OfflinePlayer getAsOfflinePlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uniqueId));
    }

    public void removeFromNation() {
        if (!isInNation()) return;
        this.inNation = false;
        this.nationId = "NONE";
        save();
    }

    public boolean withdrawMoney(double amount, String reason) {
        if (balance - amount < 0)
            return false;
        this.balance = this.balance - amount;
        String receipt = Receipt.create(amount, true, reason);
        if (this.receipts == null)
            this.receipts = new ArrayList<>();
        this.receipts.add(receipt);
        save();
        return true;
    }

    public void depositMoney(double amount, String reason) {
        this.balance = this.balance + amount;
        String receipt = Receipt.create(amount, false, reason);
        if (this.receipts == null)
            this.receipts = new ArrayList<>();
        receipts.add(receipt);
        save();
    }

    public Nation getCurrentNation() {
        if (!inNation)
            return null;
        return Nation.getById(nationId);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(UUID.fromString(uniqueId)) != null;
    }

    public boolean isMarried() {
        return !marriagePartner.equals("NONE");
    }

    public void addForeignPermission(Nation host, Permission permission) {
        extras.put("PERMISSION:" + host.getNationId() + ":" + permission.name(), true);
        save();
    }

    public void removeForeignPermission(Nation host, Permission permission) {
        extras.remove("PERMISSION:" + host.getNationId() + ":" + permission.name());
        save();
    }

    public void addChunkAccess(NationChunk chunk) {
        extras.put("ACCESS:" + chunk.serialize(), true);
        save();
    }

    public void removeChunkAccess(NationChunk chunk) {
        extras.remove("ACCESS:" + chunk.serialize());
        save();
    }

    public boolean areRelated(UUID uuid) {
        if (marriagePartner.equals(uuid.toString()))
            return true;
        if (relations.get(uuid.toString()) == null)
            return false;
        return relations.get(uuid.toString()) == null || !relations.get(uuid.toString()).startsWith("REQ=");
    }

    public boolean isStaff() {
        return instance.getUtilLists().staffMode.contains(UUID.fromString(uniqueId));
    }

    public static Profile getByDiscord(String discordId) {
        Document found = Main.getPlayerCollection().find(new Document("discord", discordId)).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), Profile.class);
        return null;
    }

    public static Profile getByNickname(String nickname) {
        Document found = Main.getPlayerCollection().find(new Document("nickname", nickname)).first();
        if (found != null)
            return new Gson().fromJson(found.toJson(), Profile.class);
        return null;
    }

    public static Map<UUID, Profile> onlineProfiles() {
        Map<UUID, Profile> returner = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers())
            returner.put(player.getUniqueId(), instance.getProfile(player.getUniqueId()));
        return returner;
    }

    public Rank getCurrentNationRank() {
        return Rank.get(getCurrentNation().getRanks().get(nationRank));
    }

}
