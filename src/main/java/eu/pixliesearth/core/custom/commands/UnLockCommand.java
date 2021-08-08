package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class UnLockCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "unlock";
    }

    @Override
    public String getCommandDescription() {
        return "Unlock chests";
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!(sender instanceof Player player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Block block = player.getTargetBlock(5);
        if (block == null || !block.getType().equals(Material.CHEST)) {
            sender.sendMessage(Lang.EARTH + "§7You have to look at a chest.");
            return false;
        }
        TileState state = (TileState) block.getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(instance, "private-chests");

        if (!container.getOrDefault(key, PersistentDataType.STRING, "EMPTY").equals(player.getUniqueId().toString()) && !instance.isStaff(player)) {
            player.sendMessage(Lang.EARTH + "§7This is not your chest.");
            return false;
        }

        container.remove(key);
        state.update();

        player.sendMessage(Lang.EARTH + "§7You §asuccessfully §7unlocked your chest.");
        return true;
    }

}
