package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class flagNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"flag", "setflag", "banner", "setbanner"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return Collections.emptyMap();
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
        ItemStack inHand = player.getInventory().getItemInMainHand();
        if (inHand == null || !inHand.getType().name().contains("BANNER")) {
            Lang.ITEM_HAS_TO_BE_X.send(player, "%ITEM%;BANNER");
            return false;
        }
        if (profile.getCurrentNationRank().getPriority() != 666.0) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        nation.setFlag(inHand.serialize());
        nation.save();
        for (String uuid : nation.getMembers()) {
            if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) continue;
            Lang.PLAYER_CHANGED_FLAG.send(Bukkit.getPlayer(UUID.fromString(uuid)), "%PLAYER%;" + player.getDisplayName());
        }
        return false;
    }

}
