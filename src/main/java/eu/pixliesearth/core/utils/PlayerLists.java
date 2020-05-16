package eu.pixliesearth.core.utils;

import eu.pixliesearth.core.objects.Profile;

import java.util.*;

public class PlayerLists {

    public Set<UUID> staffMode;

    public Map<UUID, Profile> profiles;

    public Map<UUID, CooldownMap> chatCooldown;

    public Map<UUID, UUID> tpaRequests;

    public Map<UUID, CooldownMap> teleportCooldown;

    public Map<String, UUID> discordcodes;

    public Set<UUID> warpAdder;

    public Map<UUID, String> nationDisbander;

    public PlayerLists() {
        staffMode = new HashSet<>();
        profiles = new HashMap<>();;
        chatCooldown = new HashMap<>();
        tpaRequests = new HashMap<>();
        teleportCooldown = new HashMap<>();
        discordcodes = new HashMap<>();
        warpAdder = new HashSet<>();
        nationDisbander = new HashMap<>();
    }

}
