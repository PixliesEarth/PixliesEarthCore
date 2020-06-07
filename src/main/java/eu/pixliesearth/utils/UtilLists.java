package eu.pixliesearth.utils;

import eu.pixliesearth.core.objects.Profile;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;

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

    public Set<UUID> claimAuto;

    public Set<UUID> unclaimAuto;

    public List<UUID> reloading;

    public Set<Chest> deathChests;

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
        claimAuto = new HashSet<>();
        unclaimAuto = new HashSet<>();
        reloading = new ArrayList<>();
    }

}
