package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class accessNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"access", "chunkaccess"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> map = new HashMap<>();
        map.put("player", 1);
        map.put("nation", 1);
        for (Player player : Bukkit.getOnlinePlayers())
            map.put(ChatColor.GOLD + player.getName(), 2);
        for (String s : NationManager.names.keySet())
            map.put(ChatColor.AQUA + s, 2);
        map.put(ChatColor.GREEN + "set", 3);
        map.put(ChatColor.RED + "unset", 3);
        return map;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.FOREIGN_PERMS)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        NationChunk chunk = NationChunk.get(player.getChunk());
        if (chunk == null) {
            Lang.NOT_CLAIMED.send(player);
            return false;
        }
        if (!chunk.getNationId().equals(profile.getNationId())) {
            Lang.CHUNK_NOT_YOURS.send(player);
            return false;
        }
        if (args[0].equalsIgnoreCase("player")) {
            UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
            if (targetUUID == null) {
                Lang.PLAYER_DOES_NOT_EXIST.send(player);
                return false;
            }
            Profile target = instance.getProfile(targetUUID);
            if (target.getNationId().equals(profile.getNationId())) {
                Lang.BOTH_PLAYERS_IN_THE_SAME_NATION.send(player);
                return false;
            }
            if (args[2].equalsIgnoreCase("set")) {
                target.addChunkAccess(chunk);
                Lang.ADDED_CHUNK_ACCESS_TO_X.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName());
            } else if (args[2].equalsIgnoreCase("unset")) {
                target.removeChunkAccess(chunk);
                Lang.REMOVED_CHUNK_ACCESS_FROM_X.send(player, "%X%;§6" + target.getAsOfflinePlayer().getName());
            }
        } else if (args[0].equalsIgnoreCase("nation")) {
            Nation nation = Nation.getByName(args[1]);
            if (nation == null) {
                Lang.NATION_DOESNT_EXIST.send(player);
                return false;
            }
            if (nation.getNationId().equals(profile.getNationId())) {
                Lang.TWO_NATIONS_ARE_THE_SAME.send(player);
                return false;
            }
            if (args[2].equalsIgnoreCase("set")) {
                nation.addChunkAccess(chunk);
                Lang.ADDED_CHUNK_ACCESS_TO_X.send(player, "%X%;§b" + nation.getName());
            } else if (args[2].equalsIgnoreCase("unset")) {
                nation.removeChunkAccess(chunk);
                Lang.REMOVED_CHUNK_ACCESS_FROM_X.send(player, "%X%;§b" + nation.getName());
            }
        }
        return false;
    }

}
