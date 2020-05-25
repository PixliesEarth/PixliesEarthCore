package eu.pixliesearth.localization;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Lang {

    // GENERAL
    NO_PERMISSIONS(Lang.EARTH, "§cInsufficient permissions.",
            "§cNicht genügend Rechte."),
    PLAYER_DOES_NOT_EXIST(Lang.EARTH, "&7This player &cdoes not &7exist.",
            "&7Dieser spieler war &cnoch nie &7auf dem server."),
    ONLY_PLAYERS_EXEC(Lang.EARTH, "§cThis command can only be executed from players.",
            "§cDieser Befehl kann nur von Spielern ausgeführt werden."),
    UNALLOWED_CHARS_IN_ARGS(Lang.EARTH, "&7Unallowed characters in argument.",
            "&7Die Eingabe beinhaltet verbotene symbole."),
    YOU_WILL_BE_TPD(Lang.EARTH, "&7You will be teleported to &b%LOCATION% in &3%TIME%&7, don't move.",
            "&7Du wirst in &3%TIME% &7nach &b%LOCATION% &7teleportiert, bitte bewege dich nicht."),
    TELEPORTATION_SUCESS(Lang.EARTH, "&7You have been teleported to &b%LOCATION%&7.",
            "&7Du wurdest nach &b%LOCATION% &7teleportiert."),
    TELEPORTATION_FAILURE(Lang.EARTH, "&cTeleportation was cancelled due to your inability to stand still.",
            "&cDie teleportation wurde aufgrund deiner Inkompetenz still zustehen abgebrochen."),
    WRONG_USAGE(Lang.EARTH, "&c&lWRONG USAGE! &e%USAGE%",
            "&c&lFALSCHE BENUTZUNG! &e%USAGE%"),
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
    // SUICIDE
    SMSG_1("", "&6%PLAYER% &7just stabbed himself!",
            "&6%PLAYER% &7hat sich gerade selbst erstochen!"),
    SMSG_2("", "&7&o\"I see the light...\" ~&6%PLAYER% &7&obefore he took his own life...",
            "&7&o\"Ich sehe das Licht!\" ~&6%PLAYER% &7&obevor er sein eigenes Leben nahm..."),
    SMSG_3("", "&7Everything was just too much for &6%PLAYER%&7, so he killed himself...",
            "&7Alles war einfach zuviel für &6%PLAYER%&7, deswegen hat er sich selber umgebracht...");



    private String PREFIX;
    private String ENG;
    private String DE;

    private static final String EARTH = "§aEARTH §8| ";
    private static final String ECONOMY = "§aECONOMY §8| ";
    private static final String NATION = "§bNATION §8| ";

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

}
