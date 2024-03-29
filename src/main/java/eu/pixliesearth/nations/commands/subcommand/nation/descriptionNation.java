package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class descriptionNation extends SubCommand {
    @Override
    public String[] aliases() {
        return new String[]{"description", "setdescription", "desc", "setdesc"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("§bDESCRIPTION", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.DESCRIPTION)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) stringBuilder.append(arg).append(" ");
        Nation nation = profile.getCurrentNation();
        nation.setDescription(stringBuilder.toString());
        nation.save();
        for (Player np : nation.getOnlineMemberSet())
            np.sendMessage(Lang.PLAYER_CHANGED_DESCRIPTION.get(np).replace("%PLAYER%", player.getName()).replace("%DESC%", nation.getDescription()));
        if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
            try {
                ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                channel.sendMessage(
                        new EmbedBuilder()
                                .setTitle(player.getName() + " just changed the description of your nation to:")
                                .setDescription(stringBuilder.toString())
                                .setAuthor(player.getName(), "", "https://minotar.net/avatar/" + player.getUniqueId())
                                .setColor(Color.CYAN)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
