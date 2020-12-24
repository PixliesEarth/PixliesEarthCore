package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    private String banner;
    private double xpPoints;
    private double money;
    private String leader;
    private String dynmapFill;
    private String dynmapBorder;
    private String created;
    private String currency;
    private Map<String, Map<String, Object>> ranks;
    private List<String> flags;
    private List<String> members;
    private List<String> chunks;
    private List<String> allyRequests;
    private List<String> allies;
    private List<String> pacts;
    private List<String> upgrades;
    private Map<String, String> settlements;
    private Map<String, Object> extras;

    public double getXpPoints() { return Methods.round(xpPoints, 2); }

    public Nation create() {
        ranks.put("admin", Rank.ADMIN().toMap());
        ranks.put("member", Rank.MEMBER().toMap());
        ranks.put("newbie", new Rank("newbie", "§a*", 111, new ArrayList<>()).toMap());
        ranks.put("leader", new Rank("leader", "§c+", 666, new ArrayList<>()).toMap());
        save();
        return this;
    }

    // ADVANCED METHODS
    public void backup() {
        Document nation = new Document("nationId", nationId);
        Document found = Main.getNationCollection().find(nation).first();
        nation.append("name", name);
        nation.append("description", description);
        nation.append("era", era);
        nation.append("ideology", ideology);
        nation.append("religion", religion);
        nation.append("banner", banner);
        nation.append("xpPoints", xpPoints);
        nation.append("money", money);
        nation.append("leader", leader);
        nation.append("dynmapFill", dynmapFill);
        nation.append("dynmapBorder", dynmapBorder);
        nation.append("created", created);
        nation.append("currency", currency);
        nation.append("ranks", ranks);
        nation.append("flags", flags);
        nation.append("members", members);
        nation.append("chunks", chunks);
        nation.append("allyRequests", allyRequests);
        nation.append("allies", allies);
        nation.append("pacts", pacts);
        nation.append("upgrades", upgrades);
        nation.append("settlements", settlements);
        nation.append("extras", extras);
        if (found != null) {
            Main.getNationCollection().deleteOne(found);
        }
        Main.getNationCollection().insertOne(nation);
    }

    public ItemStack getFlag() {
        return (ItemStack) InventoryUtils.deserialize(banner);
    }

    public void setFlag(ItemStack flag) {
        setBanner(InventoryUtils.serialize(flag));
        save();
    }

    public Nation save() {
        NationManager.nations.put(nationId, this);
        NationManager.names.put(name, nationId);
        return this;
    }

    public String getLeaderName() {
        if (leader.equals("NONE"))
            return "Server";
        return Bukkit.getOfflinePlayer(UUID.fromString(leader)).getName();
    }

    public void remove() {
        List<String> memberList = new ArrayList<>(members);
        for (String member : memberList)
            Main.getInstance().getProfile(UUID.fromString(member)).removeFromNation();
        Iterator<String> iter = chunks.iterator();
        while(iter.hasNext()) {
            String it = iter.next();
            NationChunk nc = NationChunk.fromString(it);
            iter.remove();
            nc.unclaim();
        }
        Document found = Main.getNationCollection().find(new Document("nationId", nationId)).first();
        if (found != null)
            Main.getNationCollection().deleteOne(found);
        NationManager.nations.remove(nationId);
        NationManager.names.remove(name);
    }

    public void unclaimAll() {
        Iterator<String> iter = chunks.iterator();
        while(iter.hasNext()) {
            String it = iter.next();
            NationChunk nc = NationChunk.fromString(it);
            iter.remove();
            nc.unclaim();
        }
    }

    public int getClaimingPower() {
        return getMaxClaimingPower() - chunks.size();
    }

    public int getMaxClaimingPower() {
        int actualPower = Era.getByName(era).getChunksPerPlayer() * members.size();
        return Math.min(actualPower, Main.getInstance().getFastConf().getMaxClaimSize());
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

    public double getGDP() {
        return Main.getInstance().getNationsTop().getTopMap().get(nationId).getGDP();
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

    public void merge(Nation nation) {
        final Main instance = Main.getInstance();

        for (Map.Entry<String, Map<String, Object>> entry : ranks.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("leader")) continue;
            if (entry.getKey().equalsIgnoreCase("admin")) continue;
            if (entry.getKey().equalsIgnoreCase("member")) continue;
            if (entry.getKey().equalsIgnoreCase("newbie")) continue;
            nation.getRanks().put(entry.getKey(), entry.getValue());
        }
        List<String> newMembers = new ArrayList<>(members);
        for (String member : newMembers) {
            Profile profile = instance.getProfile(UUID.fromString(member));
            if (profile.isLeader()) {
                profile.leaveNation();
                profile.addToNation(nation.getNationId(), Rank.ADMIN());
                continue;
            }
            final Rank rank = profile.getCurrentNationRank();
            profile.leaveNation();
            profile.addToNation(nation.getNationId(), rank);
        }
        final List<String> chunks1 = new ArrayList<>(this.getChunks());
        this.unclaimAll();
        for (String s : chunks1)
            NationChunk.fromString(s.replace(this.getNationId(), nation.getNationId())).claim();
        // nation.deposit(this.getMoney());
        nation.setXpPoints(nation.getXpPoints() + this.getXpPoints());
        nation.save();
        this.remove();
    }

    public Rank getRankByPriority(int priority) {
        for (Map.Entry<String, Map<String, Object>> entry : ranks.entrySet()) {
            Rank r = Rank.get(entry.getValue());
            if (r.getPriority() == priority)
                return r;
        }
        return null;
    }

    public List<Profile> getProfilesByRank(Rank rank) {
        List<Profile> profiles = new ArrayList<>();
        for (String s : members) {
            Profile profile = Main.getInstance().getProfile(UUID.fromString(s));
            if (profile.getNationRank().equalsIgnoreCase(rank.getName()))
                profiles.add(profile);
        }
        return profiles;
    }

    public int broadcastMembers(String message) {
        int i = 0;
        for (Player player : getOnlineMemberSet()) {
            player.sendMessage(message);
            i++;
        }
        return i;
    }

    public int broadcastMembers(Lang lang) {
        int i = 0;
        for (Player player : getOnlineMemberSet()) {
            lang.send(player);
            i++;
        }
        return i;
    }

    public boolean isAlliedWith(String nationId) {
        return allies.contains(nationId);
    }

    public void deposit(double amount) {
        money = money + amount;
        save();
    }

    public boolean withdraw(double amount) {
        if (amount > money) return false;
        money = money - amount;
        return true;
    }

    public void addForeignPermission(Nation host, Permission permission) {
        extras.put("PERMISSION:" + host.getNationId() + ":" + permission.name(), true);
        host.getExtras().put("FOREIGN-PM:NATION:" + nationId + ":" + permission.name(), true);
        host.save();
        save();
    }

    public void removeForeignPermission(Nation host, Permission permission) {
        extras.remove("PERMISSION:" + host.getNationId() + ":" + permission.name());
        host.getExtras().remove("FOREIGN-PM:NATION:" + nationId + ":" + permission.name());
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

    public int getPoints() {
        return (int) (members.size() * money);
    }

    public Era getCurrentEra() { return Era.valueOf(era.toUpperCase()); }

    public boolean hasPoliticalPower(double amount) {
        return xpPoints - amount > 0;
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
