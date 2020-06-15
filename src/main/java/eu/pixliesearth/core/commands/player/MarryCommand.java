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

public class MarryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (profile.isMarried()) {
            player.sendMessage(Lang.YOU_ARE_ALREADY_MARRIED.get(player));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(Lang.WRONG_USAGE.get(player).replace("%USAGE%", "/marry <player>"));
            return false;
        }
        if (Bukkit.getPlayerUniqueId(args[0]) == null) {
            player.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(player));
            return false;
        }
        if (args[0].equals(player.getName())) {
            player.sendMessage(Lang.YOU_CANT_MARRY_YOURSELF.get(player));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[0]));
        Profile partner = Main.getInstance().getProfile(target.getUniqueId());
        if (profile.areRelated(UUID.fromString(partner.getUniqueId()))) {
            player.sendMessage(Lang.CANT_MARRY_RELATED.get(player));
            return false;
        }
        if (partner.isMarried()) {
            player.sendMessage(Lang.PARTNER_IS_ALREADY_MARRIED.get(player).replace("%PLAYER%", target.getName()));
            return false;
        }
        if (profile.getMarriageRequests().contains(partner.getUniqueId())) {
            profile.setMarriagePartner(partner.getUniqueId());
            partner.setMarriagePartner(profile.getUniqueId());
            profile.getMarriageRequests().remove(partner.getUniqueId());
            if (target.isOnline())
                target.getPlayer().sendMessage(Lang.YOU_ARE_NOW_MARRIED.get(target.getPlayer()).replace("%PLAYER%", player.getName()));
            player.sendMessage(Lang.YOU_ARE_NOW_MARRIED.get(player).replace("%PLAYER%", target.getName()));
            profile.save();
            partner.save();
            MiniMick.getApi().getServerTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get().sendMessage("\uD83D\uDC8D **Congrats! " + player.getName() + "** & **" + target.getName() + "** just got married!");
            return false;
        }
        if (partner.getMarriageRequests().contains(player.getUniqueId().toString())) {
            player.sendMessage(Lang.ALREADY_SENT_MARRIAGE_REQUEST.get(player).replace("%PLAYER%", target.getName()));
            return false;
        }
        partner.getMarriageRequests().add(profile.getUniqueId());
        partner.save();
        player.sendMessage(Lang.SENT_MARRIAGE_REQUEST.get(player).replace("%PLAYER%", target.getName()));
        if (target.isOnline())
            target.getPlayer().sendMessage(Lang.RECEIVED_MARRIAGE_REQ.get(target.getPlayer()).replace("%PLAYER%", player.getName()));
        return false;
    }

}
