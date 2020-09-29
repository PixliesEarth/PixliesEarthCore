package eu.pixliesearth.core.objects;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.discord.DiscordIngameRank;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.luckperms.api.model.group.Group;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.javacord.api.entity.permission.Role;

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
        Main.getPlayerCollection().replaceOne(found, profile);
    }

    public void save() {
        instance.getRedissonClient().getBucket("profile:" + uniqueId).set(instance.getGson().toJson(this));
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

    public boolean isLeader() {
        return nationRank.equalsIgnoreCase("leader");
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

    public boolean isMarried() { return !marriagePartner.equals("NONE"); }

    public int canAddHomes() {
        return instance.getConfig().getInt("modules.homesystem." + getRank().getName());
    }

    public void addForeignPermission(Nation host, Permission permission) {
        extras.put("PERMISSION:" + host.getNationId() + ":" + permission.name(), true);
        host.getExtras().put("FOREIGN-PM:PLAYER:" + uniqueId + ":" + permission.name(), true);
        host.save();
        save();
    }

    public void removeForeignPermission(Nation host, Permission permission) {
        extras.remove("PERMISSION:" + host.getNationId() + ":" + permission.name());
        host.getExtras().remove("FOREIGN-PM:PLAYER:" + uniqueId + ":" + permission.name());
        host.save();
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

    public void teleport(Location location, String locationName) {
        Player player = getAsPlayer();
        if (Energy.calculateNeeded(player.getLocation(), location) > energy) {
            player.sendMessage(Lang.NOT_ENOUGH_ENERGY.get(player));
            return;
        }
        long cooldown = (long) Energy.calculateTime(player.getLocation(), location);
        if (cooldown < 1.0)
            cooldown = (long) 1.0;
        Timer timer = new Timer(cooldown * 1000);
        timers.put("Teleport", timer.toMap());
        save();
        player.sendMessage(Lang.YOU_WILL_BE_TPD.get(player).replace("%LOCATION%", locationName).replace("%TIME%", Methods.getTimeAsString(cooldown * 1000, true)));
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (timers.containsKey("Teleport")) {
                timers.remove("Teleport");
                save();
                player.teleport(location);
                Energy.take(instance.getProfile(player.getUniqueId()), Energy.calculateNeeded(player.getLocation(), location));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.sendMessage(Lang.TELEPORTATION_SUCESS.get(player).replace("%LOCATION%", locationName));
            }
        }, cooldown * 20);
    }

    public boolean isStaff() {
        return instance.getUtilLists().staffMode.contains(UUID.fromString(uniqueId));
    }

    public Player getAsPlayer() {
        return getAsOfflinePlayer().isOnline() ? getAsOfflinePlayer().getPlayer() : null;
    }

    public Group getRank() {
        return instance.getLuckPerms().getGroupManager().getGroup(instance.getLuckPerms().getUserManager().getUser(UUID.fromString(uniqueId)).getPrimaryGroup());
    }

    public boolean discordIsSynced() {
        return !discord.equalsIgnoreCase("NONE");
    }

    public void syncDiscordAndIngameRoles() {
        if (!discordIsSynced()) return;
        try {
            Role rank = MiniMick.getApi().getServerById("589958750866112512").get().getRoleById(DiscordIngameRank.groupRoleMap().get(getRank().getName())).get();
            rank.addUser(MiniMick.getApi().getUserById(discord).get());
        } catch (Exception ignored) {}
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
