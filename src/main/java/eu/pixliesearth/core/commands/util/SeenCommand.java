package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SeenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        boolean allowed = false;
        if (!(sender instanceof Player)) allowed = true;
        if (sender.hasPermission("earth.seen")) allowed = true;
        if (!allowed) {
            sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("§cWrong syntax! §e/seen <player>");
            return false;
        }
        if (Bukkit.getPlayerUniqueId(args[0]) == null) {
            sender.sendMessage("§aEARTH §8| §7This user does not exist.");
            return false;
        }
        Profile user = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(args[0]));
        if (user == null) {
            sender.sendMessage("§aEARTH §8| §7This user has never been on the server before.");
            return false;
        }
        long lastSeen = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[0])).getLastSeen();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultDate = new Date(lastSeen);
        SimpleLocation lastAt = SimpleLocation.fromString(user.getLastAt());
        sender.sendMessage("§7The last time §b" + Bukkit.getOfflinePlayer(UUID.fromString(user.getUniqueId())).getName() + " §7has been seen was at §6" + format(lastAt.getX()) + "§7, §6" + format(lastAt.getY()) + "§7, §6" + format(lastAt.getZ()) + " §7in the world §6" + lastAt.getWorld() + " §7at §6" + sdf.format(resultDate) + "§8(§7CET§8)§7.");
        return false;
    }

    private String format(double d) {
        String dS = d + "";
        return String.format(dS, "##.##");
    }

}
