package eu.pixliesearth.core.commands.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("Lobby");
        ((Player) sender).sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
        return false;
    }

}
