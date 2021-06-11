package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.warsystem.War;
import eu.pixliesearth.warsystem.WarParticipant;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvitePlayerCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "invite";
    }

    @Override
    public String getCommandUsage() {
        return "Invite player to a war.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_WAR)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        War war = instance.getCurrentWar();
        if (war == null || !war.getDefenderInstance().getMembers().contains(player.getUniqueId().toString()) && !war.getAggressorInstance().getMembers().contains(player.getUniqueId().toString())) {
            player.sendMessage(Lang.WAR + "§cYou are not in a war.");
            return false;
        }
        if (war.isRunning() && !war.getTimers().containsKey("gracePeriod")) {
            player.sendMessage(Lang.WAR + "§7The war has already started.");
            return false;
        }
        Player target = Bukkit.getPlayer(parameters[0]);
        if (target == null) {
            player.sendMessage(Lang.WAR + "§7This player is not online.");
            return false;
        }
        Profile targetProfile = instance.getProfile(target.getUniqueId());
        if (!targetProfile.isInNation()) {
            Lang.PLAYER_NOT_IN_NATION.send(commandSender);
            return false;
        }
        if (war.getPlayers().containsKey(target.getUniqueId())) {
            player.sendMessage(Lang.WAR + "§7This player is already in the war.");
            return false;
        }
        if (targetProfile.getNationId().equals(profile.getNationId())) {
            war.addPlayer(target, new WarParticipant(targetProfile.getNationId(), war.getPlayers().get(player.getUniqueId()).getSide(), war.getId()));
            war.broadcastInGame(Lang.WAR + "§6" + player.getName() + " §7just invited" + target.getName() + " to the war.");
        } else {
            instance.getUtilLists().invitationsToWar.put(target.getUniqueId(), new WarParticipant(targetProfile.getNationId(), war.getPlayers().get(player.getUniqueId()).getSide(), war.getId()));
            player.sendMessage(Lang.WAR + "§7Invitation sent.");
            target.sendMessage(Lang.WAR + "§6" + player.getName() + " §7just invited you to the §b" + Nation.getById(war.getMainAggressor()).getName() + "§7-§b" + Nation.getById(war.getMainDefender()).getName() + " §7war. /war accept");
        }
        return true;
    }

}
