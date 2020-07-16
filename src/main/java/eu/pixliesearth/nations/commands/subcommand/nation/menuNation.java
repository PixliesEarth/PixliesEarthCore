package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

public class menuNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"menu", "gui"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return Collections.emptyMap();
    }

    @Override
    public boolean staff() {
        return false;
    }

    static final String defaultTitle = "§bNations-menu §8| ";

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Gui gui = new Gui(instance, 6, "§bNations-menu");
        Player player = (Player) sender;
        open(gui, player, MenuPage.MAIN);
        return false;
    }

    void open(Gui gui, Player player, MenuPage page) {
        gui.setTitle(defaultTitle + page.title);
        StaticPane hotbar = new StaticPane(0, 0, 9, 1);
        int i = 0;
        for (MenuPage p : MenuPage.values()) {
            hotbar.addItem(new GuiItem(new ItemBuilder(p.icon).setDisplayName(defaultTitle + p.title).build(), event -> {
                open(gui, player, MenuPage.getByDisplayName(defaultTitle + p.title));
            }), i, 0);
            i++;
        }
        StaticPane menu = new StaticPane(0, 1, 9, 5);
        switch (page) {
            case MAIN:
                
                break;
        }
        gui.show(player);
    }

    enum MenuPage {

        MAIN("§eMain", Material.NETHER_STAR),
        MEMBERS("§cMembers", Material.PLAYER_HEAD),
        ;

        String title;
        Material icon;

        MenuPage(String title, Material icon) {
            this.title = title;
            this.icon = icon;
        }

        static MenuPage getByDisplayName(String name) {
            for (MenuPage page : values())
                if (page.title.equals(menuNation.defaultTitle + name))
                    return page;
            return null;
        }

    }

}
