package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DivorceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (!profile.isMarried()) {
            player.sendMessage(Lang.YOU_ARE_NOT_MARRIED.get(sender));
            return false;
        }
        Profile partner = Main.getInstance().getProfile(UUID.fromString(profile.getMarriagePartner()));
        partner.setMarriagePartner("NONE");
        profile.setMarriagePartner("NONE");
        profile.save();
        partner.save();
        player.sendMessage(Lang.YOU_GOT_DIVORCED.get(player));
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(partner.getUniqueId()));
        if (target.isOnline())
            target.getPlayer().sendMessage(Lang.YOU_GOT_DIVORCED.get(target.getPlayer()));

        MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage("\uD83D\uDC94 **Sad! " + player.getName() + "** & **" + target.getName() + "** just got divorced :(");
        return false;
    }

}
