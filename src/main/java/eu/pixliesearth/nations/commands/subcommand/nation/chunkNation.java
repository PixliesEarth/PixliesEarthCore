package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.NationsEntity;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.chunk.NationChunkType;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class chunkNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"chunk", "chunkmenu"};
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
        NationChunk nc = NationChunk.get(player.getChunk());
        if (nc == null || !nc.getNationId().equals(profile.getNationId())) {
            Lang.CHUNK_NOT_YOURS.send(player);
            return false;
        }
        ChestGui chestGui = new ChestGui(3, "Chunk-Menu §b" + nc.getX() + "§7, §b" + nc.getZ());

        StaticPane pane = new StaticPane(0, 0, 9, 3);
        pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));

        GuiItem chunkPermissions = new GuiItem(
                new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/8ad943d063347f95ab9e9fa75792da84ec665ebd22b050bdba519ff7da61db"))
                        .setDisplayName("§cManage Access")
                        .build(),
                e -> {
                    e.setCancelled(true);
                    openAccessorsMenu(player, nc);
                }
        );

        GuiItem chunkType = new GuiItem(
                new ItemBuilder(nc.getType().getIcon())
                        .setDisplayName(nc.getType().getDisplayName())
                        .build(),
                e -> {
                    e.setCancelled(true);
                    if (nc.getType().isChangeAble()) openChunkTypeMenu(player, nc);
                }
        );

        GuiItem unClaim = new GuiItem(
                new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/e9cdb9af38cf41daa53bc8cda7665c509632d14e678f0f19f263f46e541d8a30"))
                        .setDisplayName("§c§lUnclaim this chunk")
                        .build(),
                e -> {
                    e.setCancelled(true);
                    NationChunk.unclaim(player, nc.getWorld(), nc.getX(), nc.getZ(), TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_SELF);
                }
        );

        pane.addItem(chunkPermissions, 2, 1);
        pane.addItem(chunkType, 4, 1);
        pane.addItem(unClaim, 6, 1);

        chestGui.addPane(pane);

        chestGui.show(player);
        return true;
    }

    private void openChunkTypeMenu(Player player, NationChunk chunk) {
        ChestGui gui = new ChestGui(3, "§c§l❌ §7= §cUnselectable §8| §a§l✔ §7= §aSelected");

        PaginatedPane pane = new PaginatedPane(0, 0, 9, 3);

        List<GuiItem> fillers = new ArrayList<>();
        for (NationChunkType type : NationChunkType.values()) {
            fillers.add(new GuiItem(
                    new ItemBuilder(type.getIcon())
                            .setDisplayName((type.isChangeAble() ? "" : "§c§l❌ ") + (chunk.getType() == type ? "§a§l✔ " : "") + type.getDisplayName())
                            .build(),
                    e -> {
                        e.setCancelled(true);
                        if (type.isChangeAble()) {
                            chunk.unclaim();
                            chunk.setType(type);
                            chunk.claim();
                            player.closeInventory();
                        }
                    }
            ));
        }
        pane.populateWithGuiItems(fillers);
        gui.addPane(pane);

        gui.show(player);
    }

    public void openAccessorsMenu(Player player, NationChunk chunk) {
        ChestGui gui = new ChestGui(3, "§bManage Access");

        PaginatedPane pane = new PaginatedPane(0, 0, 9, 2);

        List<GuiItem> fillers = new ArrayList<>();
        for (NationsEntity accessor : chunk.getAccessors()) {
            ItemBuilder builder = new ItemBuilder(accessor instanceof Nation nation ? nation.getFlag() : new ItemStack(Material.PLAYER_HEAD));
            if (accessor instanceof Profile profile) {
                builder.setSkullOwner(profile.getUUID());
                builder.setDisplayName("§6" + accessor.name());
            } else {
                builder.setDisplayName("§b" + accessor.name());
            }
            fillers.add(new GuiItem(
                    builder.build(),
                    e -> e.setCancelled(true)
            ));
        }
        pane.populateWithGuiItems(fillers);
        gui.addPane(pane);

        StaticPane hotbar = new StaticPane(0, 2, 9, 1);
        hotbar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        hotbar.addItem(new GuiItem(Constants.backItem, event -> {
            event.setCancelled(true);
            try {
                pane.setPage(pane.getPage() - 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 0, 0);
        hotbar.addItem(new GuiItem(Constants.nextItem, event -> {
            event.setCancelled(true);
            try {
                pane.setPage(pane.getPage() + 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 8, 0);
        gui.addPane(hotbar);

        gui.show(player);
    }

}
