package eu.pixliesearth.core.utils;

import eu.pixliesearth.core.objects.Profile;

import java.util.*;

public class PlayerLists {

    public Set<UUID> staffMode;

    public Map<UUID, Profile> profiles;

    public Map<UUID, UUID> tpaRequests;

    public Map<String, UUID> discordcodes;

    public Set<UUID> warpAdder;

    public Map<UUID, String> nationDisbander;

    public List<UUID> afk;

    public Map<UUID, AfkMap> locationMap;

    public List<UUID> vanishList;

    public PlayerLists() {
        staffMode = new HashSet<>();
        profiles = new HashMap<>();
        tpaRequests = new HashMap<>();
        discordcodes = new HashMap<>();
        warpAdder = new HashSet<>();
        nationDisbander = new HashMap<>();
        afk = new ArrayList<>();
        locationMap = new HashMap<>();
        vanishList = new ArrayList<>();
    }

}
