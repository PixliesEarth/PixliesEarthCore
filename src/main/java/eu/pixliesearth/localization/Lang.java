package eu.pixliesearth.localization;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public enum Lang {

    // WORDS
    PLAYER("", "Player",
            "Spieler"),
    COMBAT("", "Combat",
            "Im Kampf"),
    YOUR_PROFILE("", "Your profile",
            "Dein Benutzerprofil"),
    LANGUAGE("", "Language",
            "Sprache"),
    CHOOSE_LANG("", "Choose your language",
            "Wähle deine Sprache aus"),
    CHOOSE_COLOUR("", "Choose a color",
            "Wähle eine Farbe aus"),
    // GENERAL
    NO_PERMISSIONS(Lang.EARTH, "§cInsufficient permissions.",
            "§cNicht genügend Rechte."),
    PLAYER_DOES_NOT_EXIST(Lang.EARTH, "&7This player &cdoes not &7exist.",
            "&7Dieser spieler war &cnoch nie &7auf dem server."),
    ONLY_PLAYERS_EXEC(Lang.EARTH, "§cThis command can only be executed from players.",
            "§cDieser Befehl kann nur von Spielern ausgeführt werden."),
    UNALLOWED_CHARS_IN_ARGS(Lang.EARTH, "&7Unallowed characters in argument.",
            "&7Die Eingabe beinhaltet verbotene symbole."),
    WRONG_USAGE(Lang.EARTH, "&c&lWRONG USAGE! &e%USAGE%",
            "&c&lFALSCHE BENUTZUNG! &e%USAGE%"),
    CHAT_MUTED(Lang.EARTH, "&7The chat has been muted by &6%PLAYER%&7.",
            "&7Der Chat wurde von &%PLAYER% &7gemuted."),
    CHAT_UNMUTED(Lang.EARTH, "&7The chat has been unmuted by &6%PLAYER%&7.",
            "&7Der Chat wurde von &6%PLAYER% &7freigeschaltet."),
    CHAT_IS_MUTED_ATM(Lang.EARTH, "&7The chat is muted at the moment.",
            "&7Der Chat ist zurzeit ausgeschaltet."),
    CHAT_COOLDOWN("", "&7You have to wait &3%COOLDOWN% &7to be able to chat again.",
            "&7Du musst &3%COOLDOWN% &7warten um wieder chatten zu können."),
    PROFILE_LOADED(Lang.EARTH, "&7Your profile has been &aloaded&7.",
            "&7Dein Profil wurde &ageladen&7."),
    LANGUAGE_CHANGED(Lang.EARTH, "&7You changed your language to &bENGLISH&7.",
            "&7Du hast deine Sprache zu &bDEUTSCH &7geändert."),
    CHANGED_SCOREBOARDTYPE(Lang.EARTH, "&7You changed your scoreboard-type to &b%TYPE%&7.",
            "&7Du hast den Typ deines Scoreboards zu &b%TYPE% &7geändert."),
    CHANGED_FAV_COL(Lang.EARTH, "&7You changed your favorite color to %COL%&7.",
            "&7Du hast deine Lieblingsfarbe zu %COL% &7geändert."),
    // NATIONS
    NOT_IN_A_NATION(Lang.NATION, "§cYou need to be in a nation to perform this command.",
            "§cUm diesen Befehl ausführen zu können, musst du in eine Nation sein."),
    ALREADY_CLAIMED(Lang.NATION, "§7This chunk is §calready §7claimed.",
            "§7Dieser Chunk §cwurde schon §7geclaimed."),
    // ECONOMY
    BALANCE_YOU(Lang.ECONOMY, "§7You have §2§l$§a%BALANCE% §7on your account.",
            "§7Du hast §2§l$§a%BALANCE% §7auf deinem Konto."),
    BALANCE_OTHERS(Lang.ECONOMY, "&6%PLAYER% &7has &2&l$&a%BALANCE% &7on his account.",
            "&6%PLAYER% &7hat &2&l$&a%BALANCE% &7auf seinem Konto."),
    SET_PLAYER_BALANCE(Lang.ECONOMY, "&aSuccessfully &7set players balance.",
            "&7Guthaben setzung &aerfolgreich&7."),
    PAID_HIMSELF(Lang.ECONOMY, "&7Your money was transferred from one pocket to another.",
            "&7Das geld wurde von einer Hosentasche ins andere verlegt."),
    PAY_AMT_BELOW_MIN(Lang.ECONOMY, "&7The minimum pay-amount is &2&l$&a%AMOUNT%&7.",
            "&7Der minimale Bezahlbetrag beträgt &2&l$&a%AMOUNT%&7."),
    NOT_ENOUGH_MONEY(Lang.ECONOMY, "&7You &cdo not &7have enough money for this action.",
            "&7Du &chast nicht genügend &7Geld für diese Aktion."),
    UNEXPECTED_ECO_ERROR(Lang.ECONOMY, "&cThere was an unexpected economy-error. Please report this to the PixliesEarth staff team if you believe that this was server-sided.",
            "&cEs wurde ein Economy-error festgestellt. Falls du denkst dass dies vom System kommt, bitte melde diesen schnellstmöglich an den PixliesEarth support-team."),
    PAID_PLAYER_MONEY(Lang.ECONOMY, "&7You &asuccessfully &7paid &6%TARGET% &2&l$&a%AMOUNT%&7.",
            "&7Du hast &6%TARGET% &2&l$&a%AMOUNT% &7überwiesen."),
    RECEIVED_MONEY_FROM_PLAYER(Lang.ECONOMY, "&7You receieved &2&l$&a%AMOUNT% &7from &6%TARGET%&7.",
            "&7Du hast &2&l$&a%AMOUNT% &7von &6%TARGET% &7erhalten."),
    PLAYER_DOESNT_HAVE_ENOUGH_MONEY(Lang.ECONOMY, "&7The player &cdoes not have enough money &7for this action.",
            "&7Dieser Spieler &chat nicht genug Geld &7für diese Aktion."),
    TOOK_MONEY_FROM_PLAYER(Lang.ECONOMY, "&aSuccessfully &7withdrew &2&l$&a%AMOUNT% &7from &6%PLAYER%&7's account.",
            "&aErfolgreich &2&l$&a%AMOUNT% &7von dem Konto des Spielers &6%PLAYER% &7abehoben."),
    GAVE_MONEY_TO_PLAYER(Lang.ECONOMY, "&aSuccessfully &7deposited &2&l$&a%AMOUNT% &7into &6%PLAYER%&7's account.",
            "&aErfolgreich &2&l$&a%AMOUNT% &7auf &6%PLAYER%&7's Konto hinterlegt."),
    // SUICIDE
    SMSG_1("", "&6%PLAYER% &7just stabbed himself!",
            "&6%PLAYER% &7hat sich gerade selbst erstochen!"),
    SMSG_2("", "&7&o\"I see the light...\" ~&6&o%PLAYER% &7&obefore he took his own life...",
            "&7&o\"Ich sehe das Licht!\" ~&6&o%PLAYER% &7&obevor er sein eigenes Leben nahm..."),
    SMSG_3("", "&7Everything was just too much for &6&o%PLAYER%&7, so he killed himself...",
            "&7Alles war einfach zuviel für &6&o%PLAYER%&7, deswegen hat er sich selber umgebracht..."),
    //VANISH
    VANISH_ON(Lang.EARTH, "&aEnabled &7vanish!",
            "&7Du bist jetzt im &avanish modus&7!"),
    VANISH_OFF(Lang.EARTH, "&cDisabled &7vanish!",
            "&7Du bist nichtmehr im &cvanish-modus&7!"),
    VANISH_ACTIONBAR("", "&cOther players can't see you!",
    "&cAndere Spieler können dich nicht sehen!!"),
    VANISH_ON_BY_OTHER(Lang.EARTH, "&7You have been set in vanish mode by &6%other%&7.",
            "&7Du wurdest von &6%other% &7in den Vanish-modus versetzt."
            ),
    VANISH_OFF_BY_OTHER(Lang.EARTH, "&7You have been removed from vanish mode by &6%other%&7.",
            "&7Du wurdest aus dem Vanish-modus von &6%other% &7entfernt. "),
    // TELEPORTATION
    NOT_ENOUGH_ENERGY(Lang.EARTH, "&7You &cdo not &7have enough energy to teleport to that location.",
            "&7Du &cbrauchst mehr &7Energie um dich zu dieser Location zu teleportieren."),
    YOU_WILL_BE_TPD(Lang.EARTH, "&7You will be teleported to &b%LOCATION% in &3%TIME%&7, don't move.",
            "&7Du wirst in &3%TIME% &7nach &b%LOCATION% &7teleportiert, bitte bewege dich nicht."),
    TELEPORTATION_SUCESS(Lang.EARTH, "&7You have been teleported to &b%LOCATION%&7.",
            "&7Du wurdest nach &b%LOCATION% &7teleportiert."),
    TELEPORTATION_FAILURE(Lang.EARTH, "&cTeleportation was cancelled due to your inability to stand still.",
            "&cDie teleportation wurde aufgrund deiner Inkompetenz still zustehen abgebrochen."),
    ALREADY_HAVE_A_REQ(Lang.EARTH, "&7You already have an &bteleportation&7-request open.",
            "&7Du hast schon eine offene &bTeleportations &7Anfrage offen."),
    NO_OPEN_TPA_REQUEST(Lang.EARTH, "&7You &cdo not &7have an open tpa-request.",
            "&7Du hast zurzeit &ckeine &7TPA-Anfrage offen."),
    TPA_REQUESTER_WENT_OFF(Lang.EARTH, "&7The tpa-requester went &coffline.",
            "&7Der TPA-Anfrager ist nun &coffline."),
    TPA_REQUEST_ACCEPTED(Lang.EARTH, "&aAccepted &7TPA-Request by &b%REQUESTER%&7.",
            "&7TPA-Anfrage von &b%REQUESTER% &aakzeptiert&7."),
    TPA_REQUEST_DENIED(Lang.EARTH, "&cDenied &7TPA-Request by &b%REQUESTER%&7.",
            "&7TPA-Anfrage von &b%REQUESTER% &cabgelehnt&7."),
    // DISCORD LINKING
    DC_ALREADY_SYNCED(Lang.DISCORD, "&7Your ingame and discord accounts are already synced.",
            "&7Deine Ingame und Discord accounts sind schon mit einander verbunden."),
    DC_ALREADY_HAVE_CODE(Lang.DISCORD, "&7You &calready have &7a code: &e%CODE%",
            "&7Du &chast bereits &7einen code: &e%CODE%"),
    DC_VERIFICATION_CODE(Lang.DISCORD, "§7Your verification code is §b%CODE%§7. Type §e/link §b%CODE% §7in our discord bot channel to complete the verification process.",
            "&7Dein Verifizierungscode ist &b%CODE%&7. Navigiere zu unserem discord und gebe &e/link &b%CODE% &7ein um die Verifizierung abzuschließen."),
    // PIXLIECOINS
    PC_BALANCE(Lang.PIXLIECOINS, "§7You have §b%AMOUNT%§3⛃ §7on your account.",
            "&7Du hast &b%AMOUNT%§3⛃ &7auf deinem Konto."),
    // GAMEMODE
    GAMEMODE_CHANGED(Lang.EARTH, "§7You changed your gamemode to §d%GAMEMODE%§7!",
            "&7Du hast deinen Gamemode zu &d%GAMEMODE% &7geändert!"),
    GAMEMODE_CHANGED_OTHER(Lang.EARTH, "§7You changed §6%PLAYER% §7gamemode to §d%GAMEMODE%§7!",
            "&7Du hast &6%PLAYER%&7's Gamemode in &d%GAMEMODE% &7versetzt."),
    GAMEMODE_CHANGED_BY_OTHER(Lang.EARTH, "§6%PLAYER% §7set your gamemode to §d%GAMEMODE%§7!",
            "&6%PLAYER% &7hat dich in Gamemode &d%GAMEMODE% &7versetzt.");

    private String PREFIX;
    private String ENG;
    private String DE;

    private static final String EARTH = "§aEARTH §8| ";
    private static final String ECONOMY = "§aECONOMY §8| ";
    private static final String NATION = "§bNATION §8| ";
    private static final String DISCORD = "§3DISCORD §8| ";
    private static final String PIXLIECOINS = "§3PIXLIECOINS §8| ";

    Lang(String PREFIX, String ENG, String DE) {
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

    public void broadcast(Map<String, String> placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String s = get(player);
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                s.replace(entry.getKey(), entry.getValue());
            }
            player.sendMessage(s);
        }
    }

}
