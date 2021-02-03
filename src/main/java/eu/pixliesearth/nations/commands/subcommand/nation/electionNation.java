package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationElection;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class electionNation extends SubCommand implements Constants {

    @Override
    public String[] aliases() {
        return new String[]{"election", "elections"};
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
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Nation nation = profile.getCurrentNation();
        Gui gui = new Gui(instance, 6, "§b" + nation.getName() + " §7");

        StaticPane background = new StaticPane(0, 0, 9, 6);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), e -> e.setCancelled(true));

        PaginatedPane electionPane = new PaginatedPane(0, 0, 9, 5);

        Set<GuiItem> filler = new HashSet<>();
        for (NationElection election : nation.getElections())
            filler.add(new GuiItem(new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/74b89ad06d318f0ae1eeaf660fea78c34eb55d05f01e1cf999f331fb32d38942")).setDisplayName("§b" + election.getTopic()).addLoreAll(election.getOptionsFormatted()).build()));

        if (electionPane.getPages() > 1) {
            background.addItem(new GuiItem(nextButtonMick, event -> {
                event.setCancelled(true);
                try {
                    electionPane.setPage(electionPane.getPage() + 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 8, 0);
            background.addItem(new GuiItem(backButtonMick, event -> {
                event.setCancelled(true);
                try {
                    electionPane.setPage(electionPane.getPage() - 1);
                    gui.update();
                } catch (Exception ignored) {}
            }), 0, 0);
        }

        gui.addPane(background);
        gui.addPane(electionPane);

        gui.show(player);
        return true;
    }

}
