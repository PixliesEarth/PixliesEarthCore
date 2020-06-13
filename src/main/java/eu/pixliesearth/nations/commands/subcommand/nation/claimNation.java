package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.RegisterSub;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@RegisterSub(
        command = "nations"
)
public class claimNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"claim"};
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("auto", 1);
        returner.put("one", 1);
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 2);
        return returner;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Chunk c = player.getLocation().getChunk();
        if (NationChunk.get(c) != null) {
            player.sendMessage(Lang.ALREADY_CLAIMED.get(player));
            return false;
        }
        switch (args.length) {
            case 1:
                if (!profile.isInNation()) {
                    player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
                    return false;
                }
                //TODO PERMISSION SYSTEM
                if (args[0].equalsIgnoreCase("one")) {
                    NationChunk nc = new NationChunk(profile.getNationId(), c.getWorld().getName(), c.getX(), c.getZ());
                    TerritoryChangeEvent event = new TerritoryChangeEvent(player, nc, TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        nc.claim();
                        for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                            members.sendMessage(Lang.PLAYER_CLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX() + "").replace("%Z%", c.getZ() + ""));
                        System.out.println("§bChunk claimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
                    }
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().claimAuto.put(player.getUniqueId(), profile.getNationId());
                        player.sendMessage(Lang.AUTOCLAIM_ENABLED.get(player));
                    }
                }
                break;
            case 2:
                Nation nation = Nation.getByName(args[1]);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(player);
                    return false;
                }
                if (!instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                if (args[0].equalsIgnoreCase("one")) {
                    NationChunk nc = new NationChunk(nation.getNationId(), c.getWorld().getName(), c.getX(), c.getZ());
                    TerritoryChangeEvent event = new TerritoryChangeEvent(player, nc, TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_OTHER);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        nc.claim();
                        for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                            members.sendMessage(Lang.PLAYER_CLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX() + "").replace("%Z%", c.getZ() + ""));
                        player.sendMessage(Lang.PLAYER_CLAIMED.get(player).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX() + "").replace("%Z%", c.getZ() + ""));
                        System.out.println("§bChunk claimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
                    }
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().claimAuto.put(player.getUniqueId(), nation.getNationId());
                        player.sendMessage(Lang.AUTOCLAIM_ENABLED.get(player));
                    }
                }

                break;
        }

        return false;
    }

}
