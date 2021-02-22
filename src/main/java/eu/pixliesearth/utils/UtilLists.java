package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordWebhook;
import eu.pixliesearth.nations.commands.subcommand.nation.chatNation;
import eu.pixliesearth.pixliefun.PixliesFunGUI;
import eu.pixliesearth.warsystem.War;
import eu.pixliesearth.warsystem.WarParticipant;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UtilLists {

    public Set<UUID> staffMode;

    public Map<UUID, UUID> tpaRequests;

    public Map<String, UUID> discordcodes;

    public Set<UUID> warpAdder;

    public Map<UUID, String> nationDisbander;

    public List<UUID> afk;

    public List<UUID> vanishList;

    public Map<Entity, Double> ammos;

    public Map<UUID, String> claimAuto;

    public List<UUID> unclaimAuto;

    public List<UUID> reloading;

    public Map<Block, ItemStack[]> deathChests;

    public Map<UUID, String> chatQueue;

    public Map<String, String> dynmapQueue;

    public DiscordWebhook webhook;

    public Map<Boost.BoostType, Boost> boosts;

    public List<UUID> scoreboardMaps;

    public List<UUID> claimFill;

    public List<UUID> unclaimFill;

    public List<UUID> awaitingGulag1;

    public List<UUID> awaitingGulag2;

    public Map<UUID, UUID> fightingGulag;

    public List<UUID> wasGulag;

    public List<UUID> dynmapSetters;

    public List<UUID> royalGifters;

    public Map<UUID, chatNation.ChatType> chatTypes;

    public Map<UUID, Integer> craftingTables;

    public Map<UUID, Timer> waitingGuns;

    public Map<UUID, Profile> profiles;

    public Map<String, War> wars;

    public List<UUID> inGulag;

    public List<UUID> bannedInWar;

    public Map<UUID, War> playersInWar;

    public Map<UUID, WarParticipant> invitationsToWar;

    public List<UUID> inspectors;

    public List<EmbedBuilder> embedsToSend;

    public Map<UUID, Location> lastLocation;

    public Map<UUID, UUID> lastMessageSender;

    public Map<UUID, Integer> scoreboardFrames;

    public List<UUID> cringers;

    public Map<UUID, PixliesFunGUI> pixliesFunGUIMap;

    public UtilLists() {
        staffMode = new HashSet<>();
        profiles = new HashMap<>();
        tpaRequests = new HashMap<>();
        discordcodes = new HashMap<>();
        warpAdder = new HashSet<>();
        nationDisbander = new HashMap<>();
        afk = new ArrayList<>();
        vanishList = new ArrayList<>();
        ammos = new HashMap<>();
        claimAuto = new HashMap<>();
        unclaimAuto = new ArrayList<>();
        reloading = new ArrayList<>();
        deathChests = new HashMap<>();
        chatQueue = new ConcurrentHashMap<>();
        dynmapQueue = new ConcurrentHashMap<>();
        webhook = new DiscordWebhook(Main.getInstance().getConfig().getString("webhook"));
        boosts = new HashMap<>();
        scoreboardMaps = new ArrayList<>();
        claimFill = new ArrayList<>();
        unclaimFill = new ArrayList<>();
        awaitingGulag1 = new ArrayList<>();
        awaitingGulag2 = new ArrayList<>();
        fightingGulag = new HashMap<>();
        wasGulag = new ArrayList<>();
        dynmapSetters = new ArrayList<>();
        royalGifters = new ArrayList<>();
        chatTypes = new HashMap<>();
        craftingTables = new HashMap<>();
        waitingGuns = new ConcurrentHashMap<>();
        wars = new ConcurrentHashMap<>();
        inGulag = new ArrayList<>();
        bannedInWar = new ArrayList<>();
        playersInWar = new HashMap<>();
        invitationsToWar = new HashMap<>();
        inspectors = new ArrayList<>();
        embedsToSend = new ArrayList<>();
        lastLocation = new HashMap<>();
        lastMessageSender = new HashMap<>();
        scoreboardFrames = new HashMap<>();
        cringers = new ArrayList<>();
        pixliesFunGUIMap = new HashMap<>();
    }

}
