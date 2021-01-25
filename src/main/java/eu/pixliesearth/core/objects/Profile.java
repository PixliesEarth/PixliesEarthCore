package eu.pixliesearth.core.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang.WordUtils;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.javacord.api.entity.permission.Role;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.google.gson.Gson;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.core.modules.economy.Receipt;
import eu.pixliesearth.core.scoreboard.ScoreboardAdapter;
import eu.pixliesearth.discord.DiscordIngameRank;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.luckperms.api.model.group.Group;

@Data
@AllArgsConstructor
public class Profile {

    private static Main instance = Main.getInstance();

    private String uniqueId;
    private String discord;
    private double balance;
    private List<String> receipts;
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
    private Map<String, Map<String, String>> timers;
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
            profile.append("balance", 4000.0);
            profile.append("receipts", new ArrayList<>());
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
            profile.append("timers", new HashMap<>());
            profile.append("favoriteColour", "§3");
            profile.append("boardType", ScoreboardAdapter.scoreboardType.STANDARD.name());
            profile.append("lang", "ENG");
            profile.append("blocked", new ArrayList<>());
            profile.append("banned", false);
            profile.append("extras", new HashMap<>());
            Main.getPlayerCollection().insertOne(profile);
            data = new Profile(uuid.toString(), "NONE",4000, new ArrayList<>(),"NONE", new ArrayList<>(), new HashMap<>(), 10.0, "NONE", new ArrayList<>(), new ArrayList<>(), true, "NONE", new ArrayList<>(), "NONE", Sound.BLOCK_NOTE_BLOCK_PLING.name(), true, "f",0, "NONE", new HashMap<>(), "§3", ScoreboardAdapter.scoreboardType.STANDARD.name(), "ENG", new ArrayList<>(), new HashMap<>(), new HashMap<>());
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
        profile.append("balance", balance);
        profile.append("receipts", receipts);
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
        instance.getUtilLists().profiles.put(UUID.fromString(uniqueId), this);
    }

    public String getDisplayName() {
        if (nickname.equalsIgnoreCase("NONE") || nickname.equalsIgnoreCase(""))
            return getAsOfflinePlayer().getName();
        return nickname;
    }

    public void setNickname(String s) {
        this.nickname = s.replace("&", "§");
    }

    public boolean inNation() {
        return !nationId.equalsIgnoreCase("NONE");
    }

    public boolean isInNation() {
        return inNation();
    }

    public String getBalanceFormatted() {
        return "§b" + Methods.formatNumber((long) balance) + "§r" + EmojiParser.parseToUnicode(":moneybag:");
    }

    public void addToNation(String id, Rank rank) {
        if (inNation()) return;
        this.nationId = id;
        this.nationRank = rank.getName();
        Nation nation = Nation.getById(id);
        nation.getMembers().add(uniqueId);
        nation.save();
        save();
    }

    public boolean leaveNation() {
        if (!inNation())
            return false;
        Nation nation = getCurrentNation();
        nation.getMembers().remove(uniqueId);
        nation.save();
        this.nationId = "NONE";
        save();
        instance.getUtilLists().inspectors.remove(getUUID());
        for (Player p : nation.getOnlineMemberSet())
            p.sendMessage(Lang.PLAYER_LEFT_NATION.get(p).replace("%PLAYER%", getAsOfflinePlayer().getName()));
        return true;
    }

    public boolean leaveEmergency() {
        if (!inNation())
            return false;
        this.nationId = "NONE";
        save();
        instance.getUtilLists().inspectors.remove(getUUID());
        return true;
    }

    public OfflinePlayer getAsOfflinePlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uniqueId));
    }

    public boolean isLeader() {
        return nationRank.equalsIgnoreCase("leader");
    }

    public boolean isInWar() { return instance.getUtilLists().playersInWar.containsKey(this.getUUID()); }

    public void removeFromNation() {
        if (!inNation()) return;
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

    public boolean canAfford(double amount) {
        return balance - amount > 0;
    }

    public Nation getCurrentNation() {
        if (!inNation())
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

    public UUID getUUID() {
        return UUID.fromString(uniqueId);
    }

    public String getPlayTimeFormatted() {
        int time = getAsOfflinePlayer().getStatistic(Statistic.PLAY_ONE_MINUTE);
        time /= 1200;
        int days = time / 1440;
        time %= 1440;
        int hours = time / 60;
        time %= 60;
        int minutes = time;

        String msg = "";
        if (days > 0) {
            msg += days + "d";
        }
        if (hours > 0) {
            msg += hours + "h";
        }
        msg += minutes + "m";
        return msg;
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
        // long cooldown = (long) Energy.calculateTime(player.getLocation(), location);
        // if (cooldown < 1.0)
            // cooldown = (long) 1.0;
        long cooldown = 5;
        teleport(location, locationName, Energy.calculateNeeded(player.getLocation(), location), cooldown);
    }

    public void teleport(Location location, String locationName, double manaNeeded, long cooldown) {
        Player player = getAsPlayer();
        if (isStaff()) {
            player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.sendMessage(Lang.TELEPORTATION_SUCESS.get(player).replace("%LOCATION%", locationName));
            return;
        }
        if (timers.containsKey("Free TP")) {
            teleportToLocation(player, 0, cooldown, location, locationName);
            return;
        }
        if (manaNeeded > energy) {
            player.sendMessage(Lang.NOT_ENOUGH_ENERGY.get(player));
            return;
        }
        teleportToLocation(player, manaNeeded, cooldown, location, locationName);
    }

    public void teleportToLocation(Player player, double manaNeeded, long cooldown, Location location, String locationName) {
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
                player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.sendMessage(Lang.TELEPORTATION_SUCESS.get(player).replace("%LOCATION%", locationName));
                double energyCost = manaNeeded;
                if (SkillHandler.getSkillHandler().getLevelOf(player.getUniqueId(), "Pixlies:Traveling")>=10) {
                    energyCost -= (0.1*(Math.floor(SkillHandler.getSkillHandler().getLevelOf(player.getUniqueId(), "Pixlies:Traveling")/10.0)));
                    if (energyCost<0) {
                        energyCost = 0.1;
                    }
                }
                Energy.take(this, manaNeeded);
            }
        }, cooldown * 20);
    }

    public void setTimer(String name, long duration) {
        this.timers.put(name, new Timer(duration).toMap());
    }

    public void addTimer(String name, Timer timer) {
        this.timers.put(name, timer.toMap());
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
            Role rank = MiniMick.getApi().getServerById("589958750866112512").get().getRoleById(DiscordIngameRank.getGroupRoleMap().get(getRank().getName())).get();
            rank.addUser(MiniMick.getApi().getUserById(discord).get());
        } catch (Exception ignored) {}
    }

    public void openPingSoundGui() {
        Player player = getAsPlayer();
        Gui gui = new Gui(Main.getInstance(), 3, "§bChoose your ping sound");
        PaginatedPane pane = new PaginatedPane(0, 0, 9, 3);
        List<GuiItem> guiItems = new ArrayList<>();
        for (Sound sound : Methods.soundsForPing())
            guiItems.add(new GuiItem(new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§b" + WordUtils.capitalize(sound.name().replace("BLOCK_NOTE_", "NOTE_").toLowerCase().replace("_", " "))).addLoreLine(" ").addLoreLine("§f§lLEFT §7§oto select").addLoreLine("§f§lRIGHT §7§oto play sound").build(), event -> {
                event.setCancelled(true);
                if (event.isLeftClick()) {
                    setMessageSound(sound.name());
                    save();
                    gui.update();
                    player.sendMessage(Lang.EARTH + "§7You just changed your notification sound to §b" + sound.name() + "§7.");
                } else if (event.isRightClick()) {
                    player.playSound(player.getLocation(), sound, 1, 1);
                }
            }));
        pane.populateWithGuiItems(guiItems);
        gui.addPane(pane);
        gui.show(getAsPlayer());
    }

    public void openLangGui() {
        Player player = getAsPlayer();
        Gui gui = new Gui(Main.getInstance(), 3, "§bChoose your language");
        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f")).setDisplayName("§eDeutsch").build(), e -> {
            e.setCancelled(true);
            setLang("DE");
            save();
            gui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 0, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4")).setDisplayName("§eEnglish").build(), e -> {
            e.setCancelled(true);
            setLang("ENG");
            save();
            gui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 1, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setDisplayName("§efrançais").build(), e -> {
            e.setCancelled(true);
            setLang("FR");
            save();
            gui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 2, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/32bd4521983309e0ad76c1ee29874287957ec3d96f8d889324da8c887e485ea8")).setDisplayName("§eEspañol").build(), e -> {
            e.setCancelled(true);
            setLang("ES");
            save();
            gui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 3, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/c23cf210edea396f2f5dfbced69848434f93404eefeabf54b23c073b090adf")).setDisplayName("§eNederlands").build(), e -> {
            e.setCancelled(true);
            setLang("NL");
            save();
            gui.update();
            player.sendMessage(Lang.LANGUAGE_CHANGED.get(player));
        }), 4, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/7d86242b0d97ece9994660f3974d72df7b887f630a4530dadc5b1ab7c2134aec")).setDisplayName("§eSvenska").build(), e -> {
            e.setCancelled(true);
            setLang("SWE");
            save();
            gui.update();
            Lang.LANGUAGE_CHANGED.send(player);
        }), 5, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/5cd9badf1972583b663b44b1e027255de8f275aa1e89defcf77782ba6fcc652")).setDisplayName("§eفارسی").build(), e -> {
            e.setCancelled(true);
            setLang("FA");
            save();
            gui.update();
            Lang.LANGUAGE_CHANGED.send(player);
        }), 6, 0);
        pane.addItem(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/ebd51f4693af174e6fe1979233d23a40bb987398e3891665fafd2ba567b5a53a")).setDisplayName("§ePortuguês").build(), e -> {
            e.setCancelled(true);
            setLang("PT");
            save();
            gui.update();
            Lang.LANGUAGE_CHANGED.send(player);
        }), 7, 0);
        gui.addPane(pane);
        gui.show(player);
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
