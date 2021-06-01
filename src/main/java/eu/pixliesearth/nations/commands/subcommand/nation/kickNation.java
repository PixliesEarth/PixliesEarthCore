package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class kickNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"kick"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        return Collections.emptyMap();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = instance.getProfile(player.getUniqueId());
            if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !profile.isInNation()) {
                Lang.NOT_IN_A_NATION.send(player);
                return false;
            }
            UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
            if (targetUUID == null) {
                Lang.PLAYER_DOES_NOT_EXIST.send(player);
                return false;
            }
            Profile target = instance.getProfile(targetUUID);
            if (!target.isInNation()) {
                Lang.PLAYER_NOT_IN_NATION.send(player);
                return false;
            }
            if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.MODERATE, target.getCurrentNation())) {
                Lang.NO_PERMISSIONS.send(player);
                return false;
            }
            if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !target.getNationId().equals(profile.getNationId())) {
                Lang.PLAYER_IS_NOT_IN_SAME_NATION_AS_YOU.send(player);
                return false;
            }
            if (target.getCurrentNationRank().getPriority() == 666.0) {
                Lang.CANT_KICK_LEADER.send(player);
                return false;
            }
            final String targetnation = target.getNationId();
            for (Player member : Nation.getById(targetnation).getOnlineMemberSet())
                if (member.getUniqueId() != player.getUniqueId())
                    Lang.PLAYER_KICKED_FROM_NATION.send(member, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
            target.leaveNation();
            Lang.PLAYER_KICKED_FROM_NATION.send(player, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
            if (MiniMick.getConfigs().containsKey(targetnation)) {
                try {
                    ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(targetnation).getUpdatesChannel()).get();
                    channel.sendMessage(
                            new EmbedBuilder()
                                    .setTitle(player.getName() + " was kicked from your nation.")
                                    .setAuthor(player.getName(), "", "https://minotar.net/avatar/" + player.getUniqueId())
                                    .setColor(Color.RED)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
            if (targetUUID == null) {
                Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                return false;
            }
            Profile target = instance.getProfile(targetUUID);
            if (!target.isInNation()) {
                Lang.PLAYER_NOT_IN_NATION.send(sender);
                return false;
            }
            final String targetnation = target.getNationId();
            for (Player member : Nation.getById(targetnation).getOnlineMemberSet())
                Lang.PLAYER_KICKED_FROM_NATION.send(member, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
            target.leaveNation();
            Lang.PLAYER_KICKED_FROM_NATION.send(sender, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
            if (MiniMick.getConfigs().containsKey(targetnation)) {
                try {
                    ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(targetnation).getUpdatesChannel()).get();
                    channel.sendMessage(
                            new EmbedBuilder()
                                    .setTitle(target.getAsOfflinePlayer().getName() + " was kicked from your nation.")
                                    .setAuthor(target.getAsOfflinePlayer().getName(), "", "https://minotar.net/avatar/" + targetUUID)
                                    .setColor(Color.RED)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
