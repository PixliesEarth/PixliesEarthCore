package eu.pixliesearth.localization;

import com.mysql.fabric.HashShardMapping;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public enum Lang {

    // WORDS
    PLAYER("", new HashMap<>()),
    COMBAT("", new HashMap<>()),
    YOUR_PROFILE("", new HashMap<>()),
    LANGUAGE("", new HashMap<>()),
    CHOOSE_LANG("", new HashMap<>()),
    CHOOSE_COLOUR("", new HashMap<>()),
    WILDERNESS("", new HashMap<>()),
    ACCEPT("", new HashMap<>()),
    DENY("", new HashMap<>()),

    // GENERAL
    WILDERNESS_SUBTITLE("", new HashMap<>()),
    SAFEZONE_SUBTITLE("", new HashMap<>()),
    WARZONE_SUBTITLE("", new HashMap<>()),
    NO_PERMISSIONS(Lang.EARTH, new HashMap<>()),
    PLAYER_DOES_NOT_EXIST(Lang.EARTH, new HashMap<>()),
    ONLY_PLAYERS_EXEC(Lang.EARTH, new HashMap<>()),
    UNALLOWED_CHARS_IN_ARGS(Lang.EARTH, new HashMap<>()),
    WRONG_USAGE(Lang.EARTH, new HashMap<>()),
    CHAT_MUTED(Lang.EARTH, new HashMap<>()),
    CHAT_UNMUTED(Lang.EARTH, new HashMap<>()),
    CHAT_IS_MUTED_ATM(Lang.EARTH, new HashMap<>()),
    CHAT_COOLDOWN("", new HashMap<>()),
    PROFILE_LOADED(Lang.EARTH, new HashMap<>()),
    LANGUAGE_CHANGED(Lang.EARTH, new HashMap<>()),
    CHANGED_SCOREBOARDTYPE(Lang.EARTH, new HashMap<>()),
    CHANGED_FAV_COL(Lang.EARTH, new HashMap<>()),
    PLAYER_JOINED_FIRST_TIME(Lang.EARTH, new HashMap<>()),
    YOU_ARE_ALREADY_MARRIED(Lang.EARTH, new HashMap<>()),
    PARTNER_IS_ALREADY_MARRIED(Lang.EARTH, new HashMap<>()),
    ALREADY_SENT_MARRIAGE_REQUEST(Lang.EARTH, new HashMap<>()),
    YOU_ARE_NOW_MARRIED(Lang.EARTH, new HashMap<>()),
    SENT_MARRIAGE_REQUEST(Lang.EARTH, new HashMap<>()),
    RECEIVED_MARRIAGE_REQ(Lang.EARTH, new HashMap<>()),
    YOU_ARE_NOT_MARRIED(Lang.EARTH, new HashMap<>()),
    YOU_CANT_MARRY_YOURSELF(Lang.EARTH, new HashMap<>()),
    YOU_GOT_DIVORCED(Lang.EARTH, new HashMap<>()),
    YOU_ARE_MARRIED_WITH("", new HashMap<>()),
    CANT_MARRY_RELATED(Lang.EARTH, new HashMap<>()),
    PLAYERS_HAD_SEX(Lang.EARTH, new HashMap<>()),
    ALREADY_RELATED(Lang.EARTH, new HashMap<>()),
    ALREADY_REQUESTED_ADOPTION(Lang.EARTH, new HashMap<>()),
    REQUESTED_ADOPTION(Lang.EARTH, new HashMap<>()),
    PLAYER_SENT_ADOPTION_REQ(Lang.EARTH, new HashMap<>()),
    NO_OPEN_X_REQUEST(Lang.EARTH, new HashMap<>()),
    PLAYER_X_ADOPTED_Y(Lang.EARTH, new HashMap<>()),
    DENIED_X_REQUEST(Lang.EARTH, new HashMap<>()),
    CANT_ADOPT_YOURSELF(Lang.EARTH, new HashMap<>()),
    SUDO(Lang.EARTH, new HashMap<>()),
    PLAYER_ALREADY_BLOCKED(Lang.EARTH, new HashMap<>()),
    BLOCKED_PLAYER(Lang.EARTH, new HashMap<>()),
    PLAYER_BLOCKED_YOU(Lang.EARTH, new HashMap<>()),
    YOU_CANT_BLOCK_YOURSELF(Lang.EARTH, new HashMap<>()),
    NICKNAME_TURNED_OFF(Lang.EARTH, new HashMap<>()),
    CANT_NICK_LIKE_A_PLAYER(Lang.EARTH, new HashMap<>()),
    INVALID_INPUT(Lang.EARTH, new HashMap<>()),
    CHANGED_NICKNAME(Lang.EARTH, new HashMap<>()),
    CHANGED_PLAYER_NICKNAME(Lang.EARTH, new HashMap<>()),
    CLICK_TO_PM("", new HashMap<>()),
    STRIKED_PLAYER(Lang.EARTH, new HashMap<>()),
    PLAYER_BOOSTED(Lang.EARTH, new HashMap<>()),
    NOT_ENOUGH_BOOSTS(Lang.EARTH, new HashMap<>()),
    BOOST_EXPIRED(Lang.EARTH, new HashMap<>()),
    ITEM_HAS_TO_BE_X(Lang.EARTH, new HashMap<>()),
    X_DOESNT_EXIST(Lang.EARTH, new HashMap<>()),
    X_ALREADY_EXISTS(Lang.EARTH, new HashMap<>()),
    MAX_HOMESIZE_REACHED(Lang.EARTH, new HashMap<>()),
    HOME_ADDED(Lang.EARTH, new HashMap<>()),
    HOME_REMOVED(Lang.EARTH, new HashMap<>()),
    PLAYER_REMOVED_X(Lang.EARTH, new HashMap<>()),
    PLAYER_ADDED_X(Lang.EARTH, new HashMap<>()),

    // NATIONS
    NOT_IN_A_NATION(Lang.NATION, new HashMap<>()),
    ALREADY_CLAIMED(Lang.NATION, new HashMap<>()),
    NOT_CLAIMED(Lang.NATION, new HashMap<>()),
    WRONG_USAGE_NATIONS(Lang.NATION, new HashMap<>()),
    NATION_WITH_NAME_ALREADY_EXISTS(Lang.NATION, new HashMap<>()),
    NATION_NAME_UNVALID(Lang.NATION, new HashMap<>()),
    PLAYER_FORMED_NATION(Lang.NATION, new HashMap<>()),
    ALREADY_IN_NATION(Lang.NATION, new HashMap<>()),
    NATION_DELEATION_CONIIRMATION(Lang.NATION, new HashMap<>()),
    PLAYER_CLAIMED(Lang.NATION, new HashMap<>()),
    PLAYER_UNCLAIMED(Lang.NATION, new HashMap<>()),
    CHUNK_NOT_YOURS(Lang.NATION, new HashMap<>()),
    AUTOCLAIM_ENABLED(Lang.NATION, new HashMap<>()),
    AUTOCLAIM_DISABLED(Lang.NATION, new HashMap<>()),
    AUTOUNCLAIM_ENABLED(Lang.NATION, new HashMap<>()),
    AUTOUNCLAIM_DISABLED(Lang.NATION, new HashMap<>()),
    NATION_DOESNT_EXIST(Lang.NATION, new HashMap<>()),
    PLAYER_NAMED_NATION_NAME(Lang.NATION, new HashMap<>()),
    PLAYER_CHANGED_DESCRIPTION(Lang.NATION, new HashMap<>()),
    INVITE_PLAYER_ALREADY_IN_NATION(Lang.NATION, new HashMap<>()),
    SUCCESSFULLY_INVITED(Lang.NATION, new HashMap<>()),
    YOU_HAVE_BEEN_INVITED(Lang.NATION, new HashMap<>()),
    YOU_HAVE_X_INVITES_OPEN(Lang.NATION, new HashMap<>()),
    REMOVED_INVITE(Lang.NATION, new HashMap<>()),
    PLAYER_NEVER_INVITED(Lang.NATION, new HashMap<>()),
    PLAYER_ALREADY_INVITED(Lang.NATION, new HashMap<>()),
    YOU_ARE_ALREADY_IN_NATION(Lang.NATION, new HashMap<>()),
    PLAYER_JOINED_NATION(Lang.NATION, new HashMap<>()),
    YOU_DONT_HAVE_OPEN_INV(Lang.NATION, new HashMap<>()),
    LEADER_CANT_LEAVE_NATION(Lang.NATION, new HashMap<>()),
    PLAYER_LEFT_NATION(Lang.NATION, new HashMap<>()),
    YOU_LEFT_NATION(Lang.NATION, new HashMap<>()),
    RANK_DOESNT_EXIST(Lang.NATION, new HashMap<>()),
    SCOREBOARDMAP_ENABLED(Lang.NATION, new HashMap<>()),
    SCOREBOARDMAP_DISABLED(Lang.NATION, new HashMap<>()),
    PLAYER_IS_NOT_IN_SAME_NATION_AS_YOU(Lang.NATION, new HashMap<>()),
    PLAYER_KICKED_FROM_NATION(Lang.NATION, new HashMap<>()),
    PLAYER_NOT_IN_NATION(Lang.NATION, new HashMap<>()),
    CANT_KICK_LEADER(Lang.NATION, new HashMap<>()),
    YOU_HAVE_TO_BE_LEADER(Lang.NATION, new HashMap<>()),
    PLAYER_TRANSFERED_LEADERSHIP(Lang.NATION, new HashMap<>()),
    CLAIMFILL_LIMIT_REACHED(Lang.NATION, new HashMap<>()),
    PLAYER_CLAIMFILLED(Lang.NATION, new HashMap<>()),
    PLAYER_CLAIMLINED(Lang.NATION, new HashMap<>()),
    UNCLAIMFILL_LIMIT_REACHED(Lang.NATION, new HashMap<>()),
    PLAYER_UNCLAIMFILLED(Lang.NATION, new HashMap<>()),
    RANK_ALREADY_EXISTS(Lang.NATION, new HashMap<>()),
    RANK_CREATED(Lang.NATION, new HashMap<>()),
    CANT_INTERACT_TERRITORY(Lang.NATION, new HashMap<>()),
    CHANGED_PLAYERS_NATION_RANK(Lang.NATION, new HashMap<>()),
    CANT_SET_RANK_WITH_HIGHER_OR_EQUAL_PRIORITY(Lang.NATION, new HashMap<>()),
    YOU_DELETED_NATION_RANK(Lang.NATION, new HashMap<>()),
    PERMISSION_DOESNT_EXIST(Lang.NATION, new HashMap<>()),
    RANK_ALREADY_HAS_PERMISSION(Lang.NATION, new HashMap<>()),
    ADDED_PERMISSION_TO_RANK(Lang.NATION, new HashMap<>()),
    RANK_DOES_NOT_HAVE_PERMISSION(Lang.NATION, new HashMap<>()),
    REMOVED_PERMISSION_FROM_RANK(Lang.NATION, new HashMap<>()),
    PLAYER_CLAIM_ALLED(Lang.NATION, new HashMap<>()),
    NOT_ENOUGH_POWER_TO_CLAIM(Lang.NATION, new HashMap<>()),
    ALREADY_ALLIED(Lang.NATION, new HashMap<>()),
    SENT_ALLY_REQUEST(Lang.NATION, new HashMap<>()),
    RECEIVED_ALLY_REQUEST(Lang.NATION, new HashMap<>()),
    YOU_ARE_NOW_ALLIED(Lang.NATION, new HashMap<>()),
    NOT_ALLIED(Lang.NATION, new HashMap<>()),
    NEUTRALED_NATION(Lang.NATION, new HashMap<>()),
    TWO_NATIONS_ARE_THE_SAME(Lang.NATION, new HashMap<>()),
    CHANGED_CHATTYPE(Lang.NATION, new HashMap<>()),
    SETTLEMENT_ALREADY_EXISTS(Lang.NATION, new HashMap<>()),
    SETTLEMENT_HAS_TO_BE_IN_TERRITORY(Lang.NATION, new HashMap<>()),
    PLAYER_SET_SETTLEMENT(Lang.NATION, new HashMap<>()),
    SETTLEMENT_DOESNT_EXIST(Lang.NATION, new HashMap<>()),
    PLAYER_REMOVED_SETTLEMENT(Lang.NATION, new HashMap<>()),
    NATION_BALANCE(Lang.NATION, new HashMap<>()),
    NOT_ENOUGH_MONEY_IN_NATION(Lang.NATION, new HashMap<>()),
    WITHDREW_MONEY_FROM_NATION(Lang.NATION, new HashMap<>()),
    PLAYER_CHANGED_FLAG(Lang.NATION, new HashMap<>()),
    CANT_PURCHASE_UPGRADE(Lang.NATION, new HashMap<>()),
    PLAYER_PURCHASED_NATION_UPGRADE(Lang.NATION, new HashMap<>()),
    X_ALREADY_HAS_PERMISSION(Lang.NATION, new HashMap<>()),
    ADDED_PERMISSION_TO_X(Lang.NATION, new HashMap<>()),
    X_DOESN_NOT_HAVE_PERMISSION(Lang.NATION, new HashMap<>()),
    REMOVED_PERMISSION_FROM_X(Lang.NATION, new HashMap<>()),
    ADDED_CHUNK_ACCESS_TO_X(Lang.NATION, new HashMap<>()),
    REMOVED_CHUNK_ACCESS_FROM_X(Lang.NATION, new HashMap<>()),
    BOTH_PLAYERS_IN_THE_SAME_NATION(Lang.NATION, new HashMap<>()),
    MACHINES_NEED_TO_BE_IN_CLAIMED_TERRITORY("", new HashMap<>()),
    NOT_ENOUGH_XP_POINTS(Lang.NATION, new HashMap<>()),
    NATION_REACHED_NEW_ERA("", new HashMap<>()),

    // ECONOMY
    BALANCE_YOU(Lang.EARTH, new HashMap<>()),
    BALANCE_OTHERS(Lang.EARTH, new HashMap<>()),
    SET_PLAYER_BALANCE(Lang.EARTH, new HashMap<>()),
    PAID_HIMSELF(Lang.EARTH, new HashMap<>()),
    PAY_AMT_BELOW_MIN(Lang.EARTH, new HashMap<>()),
    NOT_ENOUGH_MONEY(Lang.EARTH, new HashMap<>()),
    UNEXPECTED_ECO_ERROR(Lang.EARTH, new HashMap<>()),
    PAID_PLAYER_MONEY(Lang.EARTH, new HashMap<>()),
    RECEIVED_MONEY_FROM_PLAYER(Lang.EARTH, new HashMap<>()),
    PLAYER_DOESNT_HAVE_ENOUGH_MONEY(Lang.EARTH, new HashMap<>()),
    TOOK_MONEY_FROM_PLAYER(Lang.EARTH, new HashMap<>()),
    GAVE_MONEY_TO_PLAYER(Lang.EARTH, new HashMap<>()),
    DEPOSIT_MONEY_INTO_NATION(Lang.NATION, new HashMap<>()),

    // SUICIDE
    SMSG_1("", new HashMap<>()),
    SMSG_2("", new HashMap<>()),
    SMSG_3("", new HashMap<>()),

    //VANISH
    VANISH_ON(Lang.EARTH, new HashMap<>()),
    VANISH_OFF(Lang.EARTH, new HashMap<>()),
    VANISH_ACTIONBAR("", new HashMap<>()),
    VANISH_ON_BY_OTHER(Lang.EARTH, new HashMap<>()),
    VANISH_OFF_BY_OTHER(Lang.EARTH, new HashMap<>()),

    // TELEPORTATION
    NOT_ENOUGH_ENERGY(Lang.EARTH, new HashMap<>()),
    YOU_WILL_BE_TPD(Lang.EARTH, new HashMap<>()),
    TELEPORTATION_SUCESS(Lang.EARTH, new HashMap<>()),
    TELEPORTATION_FAILURE(Lang.EARTH, new HashMap<>()),
    ALREADY_HAVE_A_REQ(Lang.EARTH, new HashMap<>()),
    NO_OPEN_TPA_REQUEST(Lang.EARTH, new HashMap<>()),
    TPA_REQUESTER_WENT_OFF(Lang.EARTH, new HashMap<>()),
    TPA_REQUEST_ACCEPTED(Lang.EARTH, new HashMap<>()),
    TPA_REQUEST_DENIED(Lang.EARTH, new HashMap<>()),
    TPA_REQ(Lang.EARTH, new HashMap<>()),
    SENT_TPA_REQ(Lang.EARTH, new HashMap<>()),
    CANT_SEND_REQ_TO_YOURSELF(Lang.EARTH, new HashMap<>()),
    CANT_SEND_REQ_AGAIN(Lang.EARTH, new HashMap<>()),
    RECEIVER_DENIED_TPA_REQ(Lang.EARTH, new HashMap<>()),
    TP_HERE(Lang.EARTH, new HashMap<>()),

    // DISCORD LINKING
    DC_ALREADY_SYNCED(Lang.DISCORD, new HashMap<>()),
    DC_ALREADY_HAVE_CODE(Lang.DISCORD, new HashMap<>()),
    DC_VERIFICATION_CODE(Lang.DISCORD, new HashMap<>()),

    // PIXLIECOINS
    PC_BALANCE(Lang.PIXLIECOINS, new HashMap<>()),
    PC_BALANCE_OTHERS(Lang.PIXLIECOINS, new HashMap<>()),
    PC_ADDED_BALANCE(Lang.PIXLIECOINS, new HashMap<>()),
    PC_TOOK_BALANCE(Lang.PIXLIECOINS, new HashMap<>()),

    // GAMEMODE
    GAMEMODE_CHANGED(Lang.EARTH, new HashMap<>()),
    GAMEMODE_CHANGED_OTHER(Lang.EARTH, new HashMap<>()),
    GAMEMODE_CHANGED_BY_OTHER(Lang.EARTH, new HashMap<>()),

    // SKULL
    SKULL_GIVEN_OWN(Lang.EARTH, new HashMap<>()),
    SKULL_GIVEN(Lang.EARTH, new HashMap<>()),

    // SHOP
    PURCHASED_ITEMS(Lang.EARTH, new HashMap<>()),

    //GUNS
    GUN_GIVEN(Lang.GUNS, new HashMap<>()),
    GUN_GIVEN_OTHER(Lang.GUNS, new HashMap<>()),
    GUN_RECIEVED(Lang.GUNS, new HashMap<>()),
    GUN_DOESNT_EXIST(Lang.GUNS, new HashMap<>()),

    //CUSTOM ITEMS
    CUSTOM_DOESNT_EXIST(Lang.CUSTOMITEMS, new HashMap<>()),
    CUSTOM_GIVE_SELF(Lang.CUSTOMITEMS, new HashMap<>()),
    CUSTOM_GIVE_OTHER(Lang.CUSTOMITEMS, new HashMap<>()),
    CUSTOM_SLINGSHOT_RELOADING_ACTIONBAR("", new HashMap<>()),
    CUSTOM_GIVEN_BY_OTHER(Lang.CUSTOMITEMS, new HashMap<>()),
    //CRAFT/ANVIL/ENCHANT/BEACON PROTECT
    CANT_PUT_IN_INV(Lang.EARTH, new HashMap<>()),
    //Explosive Pickaxe
    VERY_SMART(Lang.EARTH, new HashMap<>()),

    //Warsystem
    GULAGED(Lang.WAR, new HashMap<>()),
    WON_GULAG(Lang.WAR, new HashMap<>()),
    GULAG_CAP(Lang.WAR, new HashMap<>()),
    GULAG_COMMAND(Lang.WAR, new HashMap<>()),
    GULAG_COUNTDOWN(Lang.WAR, new HashMap<>()),
    GULAG_TIMED_OUT(Lang.WAR, new HashMap<>()),
    GULAG_SKIP_NOT_AWAITING(Lang.WAR, new HashMap<>()),
    GULAG_SKIPPED(Lang.WAR, new HashMap<>()),
    GULAG_BYPASS_BAN(Lang.WAR, new HashMap<>()),
    GULAG_SET_SPECTATOR_SPAWN(Lang.WAR, new HashMap<>()),
    GULAG_SET_FIGHTER_SPAWN1(Lang.WAR, new HashMap<>()),
    GULAG_SET_FIGHTER_SPAWN2(Lang.WAR, new HashMap<>()),
    GULAG_SET_CAP(Lang.WAR, new HashMap<>());

    private String PREFIX;
    private Map<String, String> languages;

    public static final String EARTH = "§x§2§E§D§C§3§E§lE§x§3§0§C§A§3§E§lA§x§3§1§B§F§3§E§lR§x§3§8§B§2§4§3§lT§x§3§7§A§3§4§1§lH §8| ";
    public static final String NATION = "§x§2§F§D§1§E§5§lN§x§3§4§C§6§D§8§lA§x§3§7§B§A§C§A§lT§x§3§5§B§0§B§F§lI§x§3§3§A§3§B§0§lO§x§3§2§9§9§A§5§lN §8| ";
    public static final String DISCORD = "§3DISCORD §8| ";
    public static final String PIXLIECOINS = "§3PIXLIECOINS §8| ";
    public static final String GUNS = "§aGUNS §8| ";
    public static final String CUSTOMITEMS = "§dCUSTOM ITEMS §8| ";
    public static final String WAR = "§4WAR §8| ";

    Lang(String PREFIX, Map<String, String> languages) {
        this.PREFIX = PREFIX;
        this.languages = languages;
    }

    public String get(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());
/*            switch (profile.getLang()) {
                case "DE":
                    return PREFIX + languages.get("DE").replace("&", "§");
                case "ENG":
                    return PREFIX + languages.get("ENG").replace("&", "§");
                case "FR":
                    return PREFIX + languages.get("FR").replace("&", "§");
                case "ES":
                    return PREFIX + languages.get("ES").replace("&", "§");
                case "GBENG":
                    return PREFIX + languages.get("ENG").replace("&", "§").replace("t", " ");
            }*/
            if (languages.containsKey(profile.getLang()))
                return PREFIX + ChatColor.translateAlternateColorCodes('&', languages.get(profile.getLang()));
            return PREFIX + languages.get("ENG").replace("&", "§");
        }
        return PREFIX + languages.get("ENG").replace("&", "§");
    }

    public boolean send(CommandSender sender) {
        if (sender == null)
            return false;
        sender.sendMessage(get(sender));
        return true;
    }

    public void broadcast(Map<String, String> placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String s = get(player);
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                s = s.replace(entry.getKey(), entry.getValue());
            }
            player.sendMessage(s);
        }
    }

    public void broadcast(String... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers())
            send(player, placeholders);
    }

    public void setLanguage(Map<String, String> languages) {
        this.languages = languages;
    }

    /**
     * SEND MESSAGE TO PLAYER WITH PLACEHOLDERS
     * @param sender the command sender to send to
     * @param placeholders get declared by writing TOREPLACE;REPLACED
     * @return if the action was successful
     */
    public boolean send(CommandSender sender, String... placeholders) {
        if (sender == null) return false;
        String send = get(sender);
        for (String s : placeholders) {
            String[] pSplit = s.split(";");
            send = send.replace(pSplit[0], pSplit[1]);
        }
        sender.sendMessage(send);
        return true;
    }

    public static void init() {
        int loaded = 0;
        for (File file : new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/languages/").listFiles()) {
            if (!file.getName().endsWith(".yml"))
                continue;
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            String langName = file.getName().replace("LANG_", "").replace(".yml", "").toUpperCase();
            for (String s : cfg.getConfigurationSection("").getKeys(true)) {
                Map<String, String> map = new HashMap<>(Lang.valueOf(s).languages);
                map.put(langName, cfg.getString(s));
                Lang.valueOf(s).setLanguage(map);
            }
            loaded++;
        }
        Main.getInstance().getLogger().info("§7Added §b" + loaded + " §7languages.");
    }

}
