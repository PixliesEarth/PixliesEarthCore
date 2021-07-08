package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class leaveNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"leave", "quit"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        return new HashMap<>();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (profile.isLeader()) {
            final Nation nation = profile.getCurrentNation();
            if (nation.getFlags().contains(NationFlag.PERMANENT.name())) {
                nation.setLeader("NONE");
                nation.save();
                profile.leaveNation();
                player.sendMessage(Lang.YOU_LEFT_NATION.get(player).replace("%NATION%", nation.getName()));
                return false;
            }
            Lang.LEADER_CANT_LEAVE_NATION.send(player);
            return false;
        }
        final Nation nation = profile.getCurrentNation();
        final String nationName = nation.getName();
        profile.leaveNation();
        player.sendMessage(Lang.YOU_LEFT_NATION.get(player).replace("%NATION%", nationName));
        if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
            try {
                ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                channel.sendMessage(
                        new EmbedBuilder()
                                .setTitle(player.getName() + " has left your nation.")
                                .setAuthor(player.getName(), "", "https://minotar.net/avatar/" + player.getUniqueId())
                                .setColor(Color.RED)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
