package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.DiscordWebhook;
import eu.pixliesearth.nations.commands.subcommand.nation.chatNation;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class UtilLists {

    public Set<UUID> staffMode;

    public Map<UUID, Profile> profiles;

    public Map<UUID, UUID> tpaRequests;

    public Map<String, UUID> discordcodes;

    public Set<UUID> warpAdder;

    public Map<UUID, String> nationDisbander;

    public List<UUID> afk;

    public Map<UUID, AfkMap> locationMap;

    public List<UUID> vanishList;

    public Map<Entity, Double> ammos;

    public Map<UUID, String> claimAuto;

    public Map<UUID, String> unclaimAuto;

    public List<UUID> reloading;

    public Map<Block, ItemStack[]> deathChests;

    public Map<UUID, String> chatQueue;

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

    public UtilLists() {
        staffMode = new HashSet<>();
        profiles = new HashMap<>();
        tpaRequests = new HashMap<>();
        discordcodes = new HashMap<>();
        warpAdder = new HashSet<>();
        nationDisbander = new HashMap<>();
        afk = new ArrayList<>();
        locationMap = new HashMap<>();
        vanishList = new ArrayList<>();
        ammos = new HashMap<>();
        claimAuto = new HashMap<>();
        unclaimAuto = new HashMap<>();
        reloading = new ArrayList<>();
        deathChests = new HashMap<>();
        chatQueue = new HashMap<>();
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
    }

}
