package eu.pixliesearth.localization;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public enum OldLang {

    // WORDS
    PLAYER("", "Player",
            "Spieler"),
    YOUR_PROFILE("", "Your profile",
            "Dein Benutzerprofil"),
    LANGUAGE("", "OldLanguage",
            "Sprache"),
    CHOOSE_LANG("", "Choose your language",
            "Wähle deine Sprache aus"),
    CHOOSE_COLOUR("", "Choose a color",
            "Wähle eine Farbe aus"),
    WILDERNESS("", "Wilderness",
            "Wildniss"),

    // GENERAL
    WILDERNESS_SUBTITLE("", "&7It's dangerous to travel alone!",
            "&7Es ist gefährlich alleine zu Reisen!"),
    SAFEZONE_SUBTITLE("", "&7You are safe here.",
            "&7Hier bist du in Sicherheit."),
    NO_PERMISSIONS(OldLang.EARTH, "§cInsufficient permissions.",
            "§cNicht genügend Rechte."),
    PLAYER_DOES_NOT_EXIST(OldLang.EARTH, "&7This player &cdoes not &7exist.",
            "&7Dieser spieler war &cnoch nie &7auf dem server."),
    ONLY_PLAYERS_EXEC(OldLang.EARTH, "§cThis command can only be executed from players.",
            "§cDieser Befehl kann nur von Spielern ausgeführt werden."),
    UNALLOWED_CHARS_IN_ARGS(OldLang.EARTH, "&7Unallowed characters in argument.",
            "&7Die Eingabe beinhaltet verbotene symbole."),
    WRONG_USAGE(OldLang.EARTH, "&c&lWRONG USAGE! &e%USAGE%",
            "&c&lFALSCHE BENUTZUNG! &e%USAGE%"),
    CHAT_MUTED(OldLang.EARTH, "&7The chat has been muted by &6%PLAYER%&7.",
            "&7Der Chat wurde von &%PLAYER% &7gemuted."),
    CHAT_UNMUTED(OldLang.EARTH, "&7The chat has been unmuted by &6%PLAYER%&7.",
            "&7Der Chat wurde von &6%PLAYER% &7freigeschaltet."),
    CHAT_IS_MUTED_ATM(OldLang.EARTH, "&7The chat is muted at the moment.",
            "&7Der Chat ist zurzeit ausgeschaltet."),
    CHAT_COOLDOWN("", "&7You have to wait &3%COOLDOWN% &7to be able to chat again.",
            "&7Du musst &3%COOLDOWN% &7warten um wieder chatten zu können."),
    PROFILE_LOADED(OldLang.EARTH, "&7Your profile has been &aloaded&7. &8(&7%TIME%&8)",
            "&7Dein Profil wurde &ageladen&7. &8(&7%TIME%&8)"),
    LANGUAGE_CHANGED(OldLang.EARTH, "&7You changed your language to &bENGLISH&7.",
            "&7Du hast deine Sprache zu &bDEUTSCH &7geändert."),
    CHANGED_SCOREBOARDTYPE(OldLang.EARTH, "&7You changed your scoreboard-type to &b%TYPE%&7.",
            "&7Du hast den Typ deines Scoreboards zu &b%TYPE% &7geändert."),
    CHANGED_FAV_COL(OldLang.EARTH, "&7You changed your favorite color to %COL%&7.",
            "&7Du hast deine Lieblingsfarbe zu %COL% &7geändert."),
    PLAYER_JOINED_FIRST_TIME(OldLang.EARTH, "&6%PLAYER% &7joined &b&lPixlies&fEarth &7for the first time! &8(&7#%COUNT%&8)",
            "&6%PLAYER% &7ist zum ersten mal auf &b&lPixlies&fEarth&7! &8(&7#%COUNT%&8)"),
    YOU_ARE_ALREADY_MARRIED(OldLang.EARTH, "&7You can't have more than one wifes in this country.",
            "&7In diesem Land kannst du nichtmehr als eine Frau haben."),
    PARTNER_IS_ALREADY_MARRIED(OldLang.EARTH, "&6%PLAYER% &7is &calready &7married.",
            "&6%PLAYER% &cist schon &7verheiratet."),
    ALREADY_SENT_MARRIAGE_REQUEST(OldLang.EARTH, "&7You already proposed to &6%PLAYER%&7.",
            "&7Du hast schon um &6%PLAYER%&7s Hand angehalten."),
    YOU_ARE_NOW_MARRIED(OldLang.EARTH, "&bCongrats! &7You are now married with &6%PLAYER%&7.",
            "&bGlückwunsch! &7Du bist jetzt mit &6%PLAYER% &7verheiratet."),
    SENT_MARRIAGE_REQUEST(OldLang.EARTH, "&7You proposed to &6%PLAYER%&7.",
            "&7Du hast um &6%PLAYER%&7s Hand angehalten."),
    RECEIVED_MARRIAGE_REQ(OldLang.EARTH, "&6%PLAYER% &7just proposed to you. &e/marry %PLAYER%",
            "&6%PLAYER% &7hat soeben um deine Hand angehalten. &e/marry %PLAYER%"),
    YOU_ARE_NOT_MARRIED(OldLang.EARTH, "&7Can't get divorced if you're not married!",
            "&7Du kannst dich nicht scheiden wenn du nicht verheiratet bist."),
    YOU_GOT_DIVORCED(OldLang.EARTH, "&7Well I guess not all marriages workout well... You just got divorced...",
            "&7Wie es aussieht funktionieren nicht alle Ehen... Du hast dich gerade geschieden..."),
    SUDO(OldLang.EARTH, "§7You forced §6%PLAYER% §7to execute §a%COMMAND%§7!",
            "§7Du hast §6%PLAYER% §7gezwungen §a%COMMAND% §7auszuführen!"),

    // NATIONS
    NOT_IN_A_NATION(OldLang.NATION, "§cYou need to be in a nation to perform this command.",
            "§cUm diesen Befehl ausführen zu können, musst du in eine Nation sein."),
    ALREADY_CLAIMED(OldLang.NATION, "§7This chunk is §calready §7claimed.",
            "§7Dieser Chunk §cwurde schon §7geclaimed."),
    NOT_CLAIMED(OldLang.NATION, "&7This chunk &cis not &7claimed.",
            "&7Dieser Chunk &cist &7Frei."),
    WRONG_USAGE_NATIONS(OldLang.NATION, "&cWrong usage! &e%USAGE%",
            "&cFalsche Nutzung! &e%USAGE%"),
    NATION_WITH_NAME_ALREADY_EXISTS(OldLang.NATION, "&7A nation with that name &calready &7exists.",
            "&7Eine Nation mit diesem Namen &cexistiert schon&7."),
    NATION_NAME_UNVALID(OldLang.NATION, "§7The name of your nation can only be alphanumeric, min. §b3 §7and max. §b10 §7characters long.",
            "&7Der Name deiner Nation darf nur Alphanumerisch, minimum §b3 §7und maximum §10 §7Buchstaben lang sein."),
    PLAYER_FORMED_NATION(OldLang.NATION, "§6%PLAYER% §7just formed the nation of §b%NAME%&7.",
            "&6%PLAYER% &7hat gerade die Nation von &b%NAME% &7geformt."),
    ALREADY_IN_NATION(OldLang.NATION, "&7You are &calready &7in a nation.",
            "&7Du &cbist schon &7in einer Nation."),
    NATION_DELEATION_CONIIRMATION(OldLang.NATION, "§7Are you sure that you want to disband your nation? Type §aconfirm §7to disband, if your decision changed, type §ccancel§7.",
            "&7Willst du wirklich deine Nation auflösen? Schreibe &aconfirm &7um es aufzulösen, falls du dich umentschieden hast, schreibe &ccancel&7."),
    PLAYER_CLAIMED(OldLang.NATION, "&6%PLAYER% &7claimed a chunk for your nation at &b%X%&8, &b%Z%&7.",
            "&6%PLAYER% &7hat soeben einen Chunk für deine Nation an &b%X%&8, &b%Z% &7in beansprucht."),
    PLAYER_UNCLAIMED(OldLang.NATION, "&6%PLAYER% &7unclaimed a chunk from your nation at &b%X%&8, &b%Z%&7.",
            "&6%PLAYER% &7hat soeben einen Chunk von deiner Nation aufgegeben."),
    AUTOCLAIM_ENABLED(OldLang.NATION, "&7You just &aenabled &7the auto-claim mode.",
            "&7Du hast soeben den Auto-claim Modus &aaktiviert&7."),
    AUTOCLAIM_DISABLED(OldLang.NATION, "&7You just &cdisabled &7the auto-claim mode.",
            "&7Du hast soeben den Auto-claim Modus &cdeaktiviert&7."),
    AUTOUNCLAIM_ENABLED(OldLang.NATION, "&7You just &aenabled &7the auto-unclaim mode.",
            "&7Du hast soeben den Auto-unclaim Modus &aaktiviert&7."),
    AUTOUNCLAIM_DISABLED(OldLang.NATION, "&7You just &cdisabled &7the auto-unclaim mode.",
            "&7Du hast soeben den Auto-unclaim Modus &cdeaktiviert&7."),
    NATION_DOESNT_EXIST(OldLang.NATION, "&7This nation &cdoes not &7exist!",
            "&7Diese Nation &cexistiert nicht&7!"),
    PLAYER_NAMED_NATION_NAME(OldLang.NATION, "&6%PLAYER% &7renamed &b%OLD% &7to &b%NEW%&7.",
            "&6%PLAYER% &7hat die Nation &b%OLD% &7zu &b%NEW% &7umbenannt."),

    // ECONOMY
    BALANCE_YOU(OldLang.ECONOMY, "§7You have §2§l$§a%BALANCE% §7on your account.",
            "§7Du hast §2§l$§a%BALANCE% §7auf deinem Konto."),
    BALANCE_OTHERS(OldLang.ECONOMY, "&6%PLAYER% &7has &2&l$&a%BALANCE% &7on his account.",
            "&6%PLAYER% &7hat &2&l$&a%BALANCE% &7auf seinem Konto."),
    SET_PLAYER_BALANCE(OldLang.ECONOMY, "&aSuccessfully &7set players balance.",
            "&7Guthaben setzung &aerfolgreich&7."),
    PAID_HIMSELF(OldLang.ECONOMY, "&7Your money was transferred from one pocket to another.",
            "&7Das geld wurde von einer Hosentasche ins andere verlegt."),
    PAY_AMT_BELOW_MIN(OldLang.ECONOMY, "&7The minimum pay-amount is &2&l$&a%AMOUNT%&7.",
            "&7Der minimale Bezahlbetrag beträgt &2&l$&a%AMOUNT%&7."),
    NOT_ENOUGH_MONEY(OldLang.ECONOMY, "&7You &cdo not &7have enough money for this action.",
            "&7Du &chast nicht genügend &7Geld für diese Aktion."),
    UNEXPECTED_ECO_ERROR(OldLang.ECONOMY, "&cThere was an unexpected economy-error. Please report this to the PixliesEarth staff team if you believe that this was server-sided.",
            "&cEs wurde ein Economy-error festgestellt. Falls du denkst dass dies vom System kommt, bitte melde diesen schnellstmöglich an den PixliesEarth support-team."),
    PAID_PLAYER_MONEY(OldLang.ECONOMY, "&7You &asuccessfully &7paid &6%TARGET% &2&l$&a%AMOUNT%&7.",
            "&7Du hast &6%TARGET% &2&l$&a%AMOUNT% &7überwiesen."),
    RECEIVED_MONEY_FROM_PLAYER(OldLang.ECONOMY, "&7You receieved &2&l$&a%AMOUNT% &7from &6%TARGET%&7.",
            "&7Du hast &2&l$&a%AMOUNT% &7von &6%TARGET% &7erhalten."),
    PLAYER_DOESNT_HAVE_ENOUGH_MONEY(OldLang.ECONOMY, "&7The player &cdoes not have enough money &7for this action.",
            "&7Dieser Spieler &chat nicht genug Geld &7für diese Aktion."),
    TOOK_MONEY_FROM_PLAYER(OldLang.ECONOMY, "&aSuccessfully &7withdrew &2&l$&a%AMOUNT% &7from &6%PLAYER%&7's account.",
            "&aErfolgreich &2&l$&a%AMOUNT% &7von dem Konto des Spielers &6%PLAYER% &7abehoben."),
    GAVE_MONEY_TO_PLAYER(OldLang.ECONOMY, "&aSuccessfully &7deposited &2&l$&a%AMOUNT% &7into &6%PLAYER%&7's account.",
            "&aErfolgreich &2&l$&a%AMOUNT% &7auf &6%PLAYER%&7's Konto hinterlegt."),

    // SUICIDE
    SMSG_1("", "&6%PLAYER% &7just stabbed himself!",
            "&6%PLAYER% &7hat sich gerade selbst erstochen!"),
    SMSG_2("", "&7&o\"I see the light...\" ~&6&o%PLAYER% &7&obefore he took his own life...",
            "&7&o\"Ich sehe das Licht!\" ~&6&o%PLAYER% &7&obevor er sein eigenes Leben nahm..."),
    SMSG_3("", "&7Everything was just too much for &6&o%PLAYER%&7, so he killed himself...",
            "&7Alles war einfach zuviel für &6&o%PLAYER%&7, deswegen hat er sich selber umgebracht..."),

    //VANISH
    VANISH_ON(OldLang.EARTH, "&aEnabled &7vanish!",
            "&7Du bist jetzt im &avanish modus&7!"),
    VANISH_OFF(OldLang.EARTH, "&cDisabled &7vanish!",
            "&7Du bist nichtmehr im &cvanish-modus&7!"),
    VANISH_ACTIONBAR("", "&cOther players can't see you!",
    "&cAndere Spieler können dich nicht sehen!"),
    VANISH_ON_BY_OTHER(OldLang.EARTH, "&7You have been set in vanish mode by &6%other%&7.",
            "&7Du wurdest von &6%other% &7in den Vanish-modus versetzt."),
    VANISH_OFF_BY_OTHER(OldLang.EARTH, "&7You have been removed from vanish mode by &6%other%&7.",
            "&7Du wurdest aus dem Vanish-modus von &6%other% &7entfernt. "),

    // TELEPORTATION
    NOT_ENOUGH_ENERGY(OldLang.EARTH, "&7You &cdo not &7have enough energy to teleport to that location.",
            "&7Du &cbrauchst mehr &7Energie um dich zu dieser Location zu teleportieren."),
    YOU_WILL_BE_TPD(OldLang.EARTH, "&7You will be teleported to &b%LOCATION% in &3%TIME%&7, don't move.",
            "&7Du wirst in &3%TIME% &7nach &b%LOCATION% &7teleportiert, bitte bewege dich nicht."),
    TELEPORTATION_SUCESS(OldLang.EARTH, "&7You have been teleported to &b%LOCATION%&7.",
            "&7Du wurdest nach &b%LOCATION% &7teleportiert."),
    TELEPORTATION_FAILURE(OldLang.EARTH, "&cTeleportation was cancelled due to your inability to stand still.",
            "&cDie teleportation wurde aufgrund deiner Inkompetenz still zustehen abgebrochen."),
    ALREADY_HAVE_A_REQ(OldLang.EARTH, "&7You already have an &bteleportation&7-request open.",
            "&7Du hast schon eine offene &bTeleportations &7Anfrage offen."),
    NO_OPEN_TPA_REQUEST(OldLang.EARTH, "&7You &cdo not &7have an open tpa-request.",
            "&7Du hast zurzeit &ckeine &7TPA-Anfrage offen."),
    TPA_REQUESTER_WENT_OFF(OldLang.EARTH, "&7The tpa-requester went &coffline.",
            "&7Der TPA-Anfrager ist nun &coffline."),
    TPA_REQUEST_ACCEPTED(OldLang.EARTH, "&aAccepted &7TPA-Request by &b%REQUESTER%&7.",
            "&7TPA-Anfrage von &b%REQUESTER% &aakzeptiert&7."),
    TPA_REQUEST_DENIED(OldLang.EARTH, "&cDenied &7TPA-Request by &b%REQUESTER%&7.",
            "&7TPA-Anfrage von &b%REQUESTER% &cabgelehnt&7."),
    TPA_REQ(OldLang.EARTH, "&7You received an teleportation-request from &6%PLAYER%&7. &e/tpaccept &8| &c/tpa deny",
            "&7Du hast soeben eine Teleportationsanfrage von &6%PLAYER% &7erhalten. &e/tpaccept &8| &c/tpa deny"),
    SENT_TPA_REQ(OldLang.EARTH, "&7You sent a tpa request to &6%PLAYER%&7.",
            "&7Du hast soeben eine tpa-anfrage an &6%PLAYER% &7gesendet."),
    CANT_SEND_REQ_TO_YOURSELF(OldLang.EARTH, "&7You &ccan't &7send a TPA-request to yourself.",
            "&7Du kannst dir selber &ckeine &7TPA-Anfragen stellen."),
    CANT_SEND_REQ_AGAIN(OldLang.EARTH, "&7You &ccan't &7send a tpa-request to the same person twice.",
            "&7Du darfst der selben Person &cnicht &7zweimal hintereinander eine TPA-Anfrage senden."),
    RECEIVER_DENIED_TPA_REQ(OldLang.EARTH, "&6%PLAYER% &cdenied &7your TPA-request.",
            "&6%PLAYER% &7hat soeben deine TPA-Anfrage &cabgelehnt&7."),
    TP_HERE(OldLang.EARTH, "&7You teleported &6%PLAYER% &7to you!",
            "&7Du hast %PLAYER% &7zu dir teleportiert!"),

    // DISCORD LINKING
    DC_ALREADY_SYNCED(OldLang.DISCORD, "&7Your ingame and discord accounts are already synced.",
            "&7Deine Ingame und Discord accounts sind schon mit einander verbunden."),
    DC_ALREADY_HAVE_CODE(OldLang.DISCORD, "&7You &calready have &7a code: &e%CODE%",
            "&7Du &chast bereits &7einen code: &e%CODE%"),
    DC_VERIFICATION_CODE(OldLang.DISCORD, "§7Your verification code is §b%CODE%§7. Type §e/link §b%CODE% §7in our discord bot channel to complete the verification process.",
            "&7Dein Verifizierungscode ist &b%CODE%&7. Navigiere zu unserem discord und gebe &e/link &b%CODE% &7ein um die Verifizierung abzuschließen."),

    // PIXLIECOINS
    PC_BALANCE(OldLang.PIXLIECOINS, "§7You have §b%AMOUNT%§3⛃ §7on your account.",
            "&7Du hast &b%AMOUNT%§3⛃ &7auf deinem Konto."),
    PC_BALANCE_OTHERS(OldLang.PIXLIECOINS, "&6%PLAYER% &7has &b%AMOUNT%&3⛃ &7on his account.",
            "&6%PLAYER% &7hat &b%AMOUNT%&3⛃ &7auf seinem Konto."),
    PC_ADDED_BALANCE(OldLang.PIXLIECOINS, "&7You just added &b%AMOUNT%&3⛃ &7to &6%PLAYER%&7's account.",
            "&7Du hast soeben &b%AMOUNT%&3⛃ &7auf &6%PLAYER%&7's Konto."),
    PC_TOOK_BALANCE(OldLang.PIXLIECOINS, "&7You just took &b%AMOUNT%&3⛃ &7from &6%PLAYER%&7's account.",
            "&7Du hast soeben &b%AMOUNT%&3⛃ &7von &6%PLAYER%&7s Konto abgehoben."),

    // GAMEMODE
    GAMEMODE_CHANGED(OldLang.EARTH, "§7You changed your gamemode to §d%GAMEMODE%§7!",
            "&7Du hast deinen Gamemode zu &d%GAMEMODE% &7geändert!"),
    GAMEMODE_CHANGED_OTHER(OldLang.EARTH, "§7You changed §6%PLAYER% §7gamemode to §d%GAMEMODE%§7!",
            "&7Du hast &6%PLAYER%&7's Gamemode in &d%GAMEMODE% &7versetzt."),
    GAMEMODE_CHANGED_BY_OTHER(OldLang.EARTH, "§6%PLAYER% §7set your gamemode to §d%GAMEMODE%§7!",
            "&6%PLAYER% &7hat dich in Gamemode &d%GAMEMODE% &7versetzt."),

    // SKULL
    SKULL_GIVEN_OWN(OldLang.EARTH, "&7You gave yourself your own skull!",
            "&7Du hast dir deinen eigenen Schädel gegeben!"),
    SKULL_GIVEN(OldLang.EARTH, "&7You gave yourself the skull of &6%player%&7!",
            "&7Du hast dir den Schädel von &6%player% &7gegeben!"),

    // SHOP
    PURCHASED_ITEMS(OldLang.EARTH, "&7You just purchased &b%AMOUNT%x %ITEM%&7.",
            "&7Du hast soeben &b%AMOUNT%x %ITEM%&7."),

    //GUNS
    GUN_GIVEN(OldLang.GUNS, "§7You gave yourself the §a%gun%§7!",
            "§7Du hast dir die §a%gun% §7gegeben!"),
    GUN_GIVEN_OTHER(OldLang.GUNS, "§7You gave §6%player% §7the §a%gun%§7!",
            "§7Du hast §6%player% §7die §a%gun% &7gegeben!"),
    GUN_RECIEVED(OldLang.GUNS, "&7You recieved the &a%gun%&7!",
            "&7Du hast die &a%gun% bekommen!"),
    GUN_DOESNT_EXIST(OldLang.GUNS, "&7This gun &cdoes not &7exist.",
            "&7Diese Waffe &cexistiert nicht&7."),

    //CUSTOM ITEMS
    CUSTOM_DOESNT_EXIST(OldLang.CUSTOMITEMS, "&7This item &cdoes not &7exist!",
            "&7Diese Waffe &cexistiert nicht &7!"),
    CUSTOM_GIVE_SELF(OldLang.CUSTOMITEMS, "&7You gave yourself a &a%item%&7!",
            "&7Du hast dir selbst ein(e) &a%item% &7gegeben!"),
    CUSTOM_GIVE_OTHER(OldLang.CUSTOMITEMS, "&7You gave &6%player% &7a &a%item%&7!",
            "&7Du hast &6%player% &7ein(e) &a%item% &7gegeben!"),
    CUSTOM_SLINGSHOT_RELOADING_ACTIONBAR("", "&6Loading the stone...",
            "&6Lade gerade den Stein..."),
    CUSTOM_GIVEN_BY_OTHER(OldLang.CUSTOMITEMS, "&6%player% &7gave you a &a%item%&7!",
            "&6%player% &7hat dir ein(e) &a%item% &7gegeben!"),
    //CRAFT/ANVIL/ENCHANT/BEACON PROTECT
    CANT_PUT_IN_INV(OldLang.EARTH, "&7You &care not allowed &7to put this item here!",
            "&7Du darfst das nicht hier lassen!"),
    //Explosive Pickaxe
    VERY_SMART(OldLang.EARTH, "§7Haha very smart!",
            "§7Bist ja hier ein ganz schlauer!");

    private String PREFIX;
    private String ENG;
    private String DE;

    private static final String EARTH = "§aEARTH §8| ";
    private static final String ECONOMY = "§aECONOMY §8| ";
    private static final String NATION = "§bNATION §8| ";
    private static final String DISCORD = "§3DISCORD §8| ";
    private static final String PIXLIECOINS = "§3PIXLIECOINS §8| ";
    private static final String GUNS = "§aGUNS §8| ";
    private static final String CUSTOMITEMS = "§dCUSTOM ITEMS §8| ";

    OldLang(String PREFIX, String ENG, String DE) {
        this.PREFIX = PREFIX;
        this.ENG = ENG;
        this.DE = DE;
    }

    public String get(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());
            switch (profile.getLang()) {
                case "DE":
                    return PREFIX + DE.replace("&", "§");
                case "ENG":
                    return PREFIX + ENG.replace("&", "§");
                case "GBENG":
                    return PREFIX + ENG.replace("&", "§").replace("t", " ");
            }
        }
        return PREFIX + ENG.replace("&", "§");
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

}
