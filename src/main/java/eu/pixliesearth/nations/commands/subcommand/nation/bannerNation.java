package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class bannerNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"banner", "setbanner"};
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
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Profile profile = instance.getProfile(player.getUniqueId());
        Nation nation;
        ItemStack inHand;
        switch (args.length) {
            case 0 -> {
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                inHand = player.getInventory().getItemInMainHand();
                if (!inHand.getType().name().contains("BANNER")) {
                    Lang.ITEM_HAS_TO_BE_X.send(player, "%ITEM%;BANNER");
                    return false;
                }
                if (!Permission.hasForeignPermission(profile, Permission.SET_FLAG, profile.getCurrentNation())) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                nation = profile.getCurrentNation();
                nation.setFlag(inHand);
                for (String uuid : nation.getMembers()) {
                    if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) continue;
                    Lang.PLAYER_CHANGED_FLAG.send(Bukkit.getPlayer(UUID.fromString(uuid)), "%PLAYER%;" + player.getDisplayName());
                }
            }
            case 1 -> {
                nation = Nation.getByName(args[0]);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(player);
                    return false;
                }
                inHand = player.getInventory().getItemInMainHand();
                if (!inHand.getType().name().contains("BANNER")) {
                    Lang.ITEM_HAS_TO_BE_X.send(player, "%ITEM%;BANNER");
                    return false;
                }
                if (!Permission.hasForeignPermission(profile, Permission.SET_FLAG, profile.getCurrentNation())) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                nation.setFlag(inHand);
                for (String uuid : nation.getMembers()) {
                    if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) continue;
                    Lang.PLAYER_CHANGED_FLAG.send(Bukkit.getPlayer(UUID.fromString(uuid)), "%PLAYER%;" + player.displayName());
                }
                Lang.PLAYER_CHANGED_FLAG.send(player, "%PLAYER%;" + player.getDisplayName());
            }
        }
        return false;
    }

}
