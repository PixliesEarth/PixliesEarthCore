package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

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
        int j = 0;
        for (MenuPage p : MenuPage.values()) {
            ItemStack item = new ItemBuilder(p.icon).setDisplayName(p.title).build();
            if (p.title.equals(page.title)) item = new ItemBuilder(item).addLoreLine("§a§oSelected").setGlow().build();
            hotbar.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                if (!p.equals(page)) open(gui, player, MenuPage.getByDisplayName(p.title));
            }), j, 0);
            j++;
        }
        gui.addPane(hotbar);
        StaticPane menu = new StaticPane(0, 1, 9, 5);
        menu.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        Profile profile = instance.getProfile(player.getUniqueId());
        Nation nation = profile.getCurrentNation();
        switch (page) {
            case MAIN:
                menu.addItem(new GuiItem(new ItemBuilder(ItemStack.deserialize(nation.getFlag())).setDisplayName("§b" + nation.getName()).addLoreLine("§7Members: §b" + nation.getMembers().size()).addLoreLine("§7Era: §b" + Era.getByName(nation.getEra()).getName()).build(), event -> event.setCancelled(true)), 4, 2);
                break;
            case MEMBERS:
                int x = 0;
                int y = 0;
                for (String s : nation.getMembers()) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(s));
                    Profile member = instance.getProfile(op.getUniqueId());
                    if (x + 1 > 8) {
                        y++;
                        x = 0;
                    }
                    ChatColor cc = op.isOnline() ? ChatColor.GREEN : ChatColor.RED;
                    menu.addItem(new GuiItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(op.getUniqueId()).setDisplayName(member.getCurrentNationRank().getPrefix() + cc + op.getName()).addLoreLine("§7§oLeftclick to edit").build(), event -> {
                        event.setCancelled(true);
                        if (!Permission.hasNationPermission(profile, Permission.MODERATE)) return;
                        menu.clear();

                    }), x, y);
                }
                break;
            case PERMISSIONS:
                //TODO PERMISSIONS GUI
                break;
        }
        gui.addPane(menu);
        gui.show(player);
    }

    enum MenuPage {

        MAIN("§eMain", Material.CYAN_BANNER),
        MEMBERS("§cMembers", Material.PLAYER_HEAD),
        PERMISSIONS("§3Permissions", Material.WRITABLE_BOOK);

        String title;
        Material icon;

        MenuPage(String title, Material icon) {
            this.title = title;
            this.icon = icon;
        }

        static MenuPage getByDisplayName(String name) {
            for (MenuPage page : values())
                if (page.title.equalsIgnoreCase(name))
                    return page;
            return null;
        }

    }

}
