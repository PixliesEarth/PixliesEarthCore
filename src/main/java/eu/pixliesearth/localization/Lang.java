package eu.pixliesearth.localization;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Lang {

    NO_PERMISSIONS("§cInsufficient permissions.", "§cNicht genügend Rechte."),
    ONLY_PLAYERS_EXEC("§cThis command can only be executed from players.", "§cDieser Befehl kann nur von Spielern ausgeführt werden."),
    NOT_IN_A_NATION("§cYou need to be in a nation to perform this command.", "§cUm diesen Befehl ausführen zu können, musst du in eine Nation sein."),
    ALREADY_CLAIMED("§7This chunk is §calready §7claimed.", "§7Dieser Chunk §cwurde schon §7geclaimed.");

    private String ENG;
    private String DE;

    Lang(String ENG, String DE) {
        this.ENG = ENG;
        this.DE = DE;
    }

    public String get(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());
            if (profile.getLang().equalsIgnoreCase("DE"))
                return DE;
            if (profile.getLang().equalsIgnoreCase("ENG"))
                return ENG;
        }
        return ENG;
    }

}
