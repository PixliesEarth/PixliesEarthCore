package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class chunkNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"chunk", "chunkmenu"};
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        NationChunk nc = NationChunk.get(player.getChunk());
        if (nc == null || !nc.getNationId().equals(profile.getNationId())) {
            Lang.CHUNK_NOT_YOURS.send(player);
            return false;
        }
        ChestGui chestGui = new ChestGui(3, "Chunk-Menu");

        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());

        chestGui.show(player);
        return true;
    }

}
