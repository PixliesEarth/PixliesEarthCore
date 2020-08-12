package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.*;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return;
        }
        Nation nation = profile.getCurrentNation();
        int x;
        int y;
        switch (page) {
            case MAIN:
                menu.addItem(new GuiItem(new ItemBuilder(nation.getFlag()).resetLore().setDisplayName("§b" + nation.getName()).addLoreLine("§7Members: §b" + nation.getMembers().size()).addLoreLine("§7Era: §b" + Era.getByName(nation.getEra()).getName()).build(), event -> event.setCancelled(true)), 4, 2);
                break;
            case MEMBERS:
                x = 0;
                y = 0;
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
                        showMemberMenu(gui, menu, player, member);
                    }), x, y);
                    x++;
                }
                break;
            case PERMISSIONS:
                x = 0;
                y = 0;
                for (Map.Entry<String, Map<String, Object>> ranks : nation.getRanks().entrySet()) {
                    Rank rank = Rank.get(ranks.getValue());
                    if (rank.getName().equalsIgnoreCase("leader")) continue;
                    if (rank.getPriority() >= profile.getCurrentNationRank().getPriority()) continue;
                    if (x + 1 > 8) {
                        y++;
                        x = 0;
                    }
                    menu.addItem(new GuiItem(new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§b" + rank.getName()).addLoreLine("§7§oClick to edit").build(), event -> {
                        event.setCancelled(true);
                        if (Permission.hasNationPermission(profile, Permission.EDIT_RANKS)) showRankMenu(gui, menu, player, rank);
                    }), x, y);
                    x++;
                }
                break;
            case SETTINGS:
                Religion religion = Religion.valueOf(nation.getReligion());
                String religionName = StringUtils.capitalize(religion.name().toLowerCase().replace("_", " "));
                menu.addItem(new GuiItem(new ItemBuilder(religion.getMaterial()).setGlow().setDisplayName("§b§lReligion").addLoreLine("§" + religion.getColour() + religionName).addLoreLine(" ").addLoreLine("§c§oClick to change").build(), event -> {
                    event.setCancelled(true);
                    if (profile.getCurrentNationRank().getPriority() != 666.0) return;
                    StaticPane pane = new StaticPane(0, 1, 9, 5);
                    pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event1 -> event1.setCancelled(true));
                    int x1 = 0;
                    int y1 = 0;
                    for (Religion religion1 : Religion.values()) {
                        String religion1Name = StringUtils.capitalize(religion1.name().toLowerCase().replace("_", " "));
                        ItemBuilder iBuilder = new ItemBuilder(religion1.getMaterial()).setDisplayName("§" + religion1.getColour() + religion1Name);
                        if (religion == religion1)
                            iBuilder.setGlow();
                        if (x1 + 1 > 8) {
                            x1 = 0;
                            y1++;
                        }
                        pane.addItem(new GuiItem(iBuilder.build(), event1 -> {
                            event1.setCancelled(true);
                            nation.setReligion(religion1.name());
                            nation.save();
                            player.closeInventory();
                            open(gui, player, MenuPage.SETTINGS);
                        }), x1, y1);
                        x1++;
                    }
                    gui.addPane(pane);
                    gui.update();
                }), 0, 0);
                Ideology ideology = Ideology.valueOf(nation.getIdeology());
                String ideologyName = StringUtils.capitalize(ideology.name().toLowerCase().replace("_", " "));
                menu.addItem(new GuiItem(new ItemBuilder(ideology.getMaterial()).setGlow().setDisplayName("§b§lIdeology").addLoreLine("§" + ideology.getColour() + ideologyName).addLoreLine(" ").addLoreLine("§c§oClick to change").build(), event -> {
                    event.setCancelled(true);
                    if (profile.getCurrentNationRank().getPriority() != 666.0) return;
                    StaticPane pane = new StaticPane(0, 1, 9, 5);
                    pane.clear();
                    pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event1 -> event1.setCancelled(true));
                    int x1 = 0;
                    int y1 = 0;
                    for (Ideology ideology1 : Ideology.values()) {
                        String ideology1Name = StringUtils.capitalize(ideology1.name().toLowerCase().replace("_", " "));
                        ItemBuilder iBuilder = new ItemBuilder(ideology1.getMaterial()).setDisplayName("§" + ideology1.getColour() + ideology1Name);
                        if (ideology == ideology1)
                            iBuilder.setGlow();
                        if (x1 + 1 > 8) {
                            x1 = 0;
                            y1++;
                        }
                        pane.addItem(new GuiItem(iBuilder.build(), event1 -> {
                            event1.setCancelled(true);
                            nation.setIdeology(ideology1.name());
                            nation.save();
                            player.closeInventory();
                            open(gui, player, MenuPage.SETTINGS);
                        }), x1, y1);
                        x1++;
                    }
                    gui.addPane(pane);
                    gui.update();
                }), 1, 0);
                break;
            case RELATIONS:
                x = 0;
                y = 0;
                for (String s : nation.getAllies()) {
                    Nation ally = Nation.getById(s);
                    if (ally == null) {
                        nation.getAllies().remove(s);
                        nation.save();
                        continue;
                    }
                    if (x + 1 > 8) {
                        y++;
                        x = 0;
                    }
                    ItemStack item = new ItemBuilder(ally.getFlag()).resetLore().setDisplayName("§d" + ally.getName()).addLoreLine("§7§o" + ally.getDescription()).addLoreLine("§7Money: §2§l$§a" + ally.getMoney()).addLoreLine("§c§oClick to neutralize").build();
                    menu.addItem(new GuiItem(item, event -> {
                        event.setCancelled(true);
                        player.performCommand("n neutral " + ally.getName());
                        player.closeInventory();
                    }), x, y);
                    x++;
                }
                break;
            case RESEARCH:
                menu.addItem(new GuiItem(new ItemBuilder(Material.LECTERN).setDisplayName("§aNation-EXP").addLoreLine("§3§l" + nation.getXpPoints()).build(), event -> event.setCancelled(true)), 0, 4);
                //TODO ACTUALLY MAKE THEM WORK
                menu.addItem(new GuiItem(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§bNext Era").addLoreLine("§3HAS§8/§3NEEDED§9XP").build(), event -> event.setCancelled(true)), 8, 4);
                menu.addItem(new GuiItem(new ItemBuilder(Material.WHEAT).setDisplayName("§eAgriculture").setGlow().build(), event -> {event.setCancelled(true); showUpgradeGui(gui, player, NationUpgrade.UpgradeType.AGRICULTURE, menu);}), 2, 2);
                menu.addItem(new GuiItem(new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName("§4Military").setGlow().build(), event -> {event.setCancelled(true); showUpgradeGui(gui, player, NationUpgrade.UpgradeType.MILITARY, menu);}), 4, 2);
                menu.addItem(new GuiItem(new ItemBuilder(Material.FURNACE).setDisplayName("§6Industry").setGlow().build(), event -> {event.setCancelled(true); showUpgradeGui(gui, player, NationUpgrade.UpgradeType.INDUSTRY, menu);}), 6, 2);
                break;
        }
        gui.addPane(menu);
        gui.show(player);
    }

    void showUpgradeGui(Gui gui, Player player, NationUpgrade.UpgradeType type, StaticPane menuPane) {
        menuPane.clear();
        menuPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        gui.addPane(menuPane);
        Profile profile = instance.getProfile(player.getUniqueId());
        Nation nation = profile.getCurrentNation();
        PaginatedPane pagePane = new PaginatedPane(0, 1, 9, 4);
        List<GuiItem> upgradeItems = new ArrayList<>();
        for (NationUpgrade upgrade : NationUpgrade.values()) {
            if (upgrade.getType() != type) continue;
            upgradeItems.add(nation.getUpgrades().contains(upgrade.name()) ? new GuiItem(new ItemBuilder(upgrade.getIcon()).setGlow().setDisplayName("§a" + upgrade.getDisplayName()).addLoreLine("§7Already purchased.").build(), event -> event.setCancelled(true)) : new GuiItem(new ItemBuilder(upgrade.getIcon()).setDisplayName("§c" + upgrade.getDisplayName()).addLoreLine("§7Cost: §b" + upgrade.getCost() + "§3N-XP").build(), event -> {
                event.setCancelled(true);
                player.closeInventory();
                if (nation.getXpPoints() < upgrade.getCost() || !upgrade.getEra().canAccess(nation)) {
                    Lang.CANT_PURCHASE_UPGRADE.send(player);
                    return;
                }
                if (!Permission.hasNationPermission(profile, Permission.PURCHASE_UPGRADES)) {
                    Lang.NO_PERMISSIONS.send(player);
                    return;
                }
                nation.setXpPoints(nation.getXpPoints() - upgrade.getCost());
                nation.getUpgrades().add(upgrade.name());
                nation.save();
                for (String s : nation.getMembers())
                    if (Bukkit.getPlayer(UUID.fromString(s)) != null)
                        Lang.PLAYER_PURCHASED_NATION_UPGRADE.send(Bukkit.getPlayer(UUID.fromString(s)), "%PLAYER%;" + player.getName(), "%UPGRADE%;" + upgrade.getDisplayName());
            }));
        }
        pagePane.populateWithGuiItems(upgradeItems);
        gui.addPane(pagePane);
        StaticPane controlBar = new StaticPane(0, 5, 9, 1);
        controlBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        controlBar.addItem(new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cBack").build(), event -> {
            event.setCancelled(true);
            if (pagePane.getPage() > 1 && pagePane.getPages() > 1) {
                pagePane.setPage(pagePane.getPage() - 1);
                gui.update();
            }
        }), 0, 0);
        controlBar.addItem(new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§aNext").build(), event -> {
            event.setCancelled(true);
            if (pagePane.getPage() < pagePane.getPages()) {
                pagePane.setPage(pagePane.getPage() + 1);
                gui.update();
            }
        }), 8, 0);
        gui.addPane(controlBar);
        gui.show(player);
    }

    void showRankMenu(Gui gui, StaticPane menuPane, Player player, Rank rank) {
        menuPane.clear();
        menuPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        int x = 0;
        int y = 0;
        for (Permission permission : Permission.values()) {
            ItemStack item = rank.getPermissions().contains(permission.name()) ? new ItemBuilder(Material.GREEN_WOOL).setDisplayName("§a" + permission.name() + " §8[§a✔§8]").addLoreLine("§7§oClick to toggle").build() : new ItemBuilder(Material.RED_WOOL).setDisplayName("§c" + permission.name() + " §8[§c✖§8]").addLoreLine("§7§oClick to toggle").build();
            if (x + 1 > 8) {
                y++;
                x = 0;
            }
            menuPane.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                switch (item.getType()) {
                    case GREEN_WOOL:
                        player.performCommand("n rank removepermission " + rank.getName() + " " + permission.name());
                        showRankMenu(gui, menuPane, player, rank);
                        break;
                    case RED_WOOL:
                        player.performCommand("n rank addpermission " + rank.getName() + " " + permission.name());
                        showRankMenu(gui, menuPane, player, rank);
                        break;
                }
            }), x, y);
            x++;
        }
        gui.addPane(menuPane);
        gui.show(player);
    }

    void showMemberMenu(Gui gui, StaticPane menu, Player requester, Profile target) {
        menu.clear();
        menu.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        Profile profile = instance.getProfile(requester.getUniqueId());
        GuiItem kick = new GuiItem(new ItemBuilder(Material.BARRIER).setDisplayName("§cKick from nation").build(), event -> {
            event.setCancelled(true);
            requester.performCommand("n kick " + target.getAsOfflinePlayer().getName());
            open(gui, requester, MenuPage.MEMBERS);
        });
        GuiItem changeRank = new GuiItem(new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§bChange rank").build(), event -> {
            event.setCancelled(true);
            int x = 0;
            int y = 0;
            menu.clear();
            menu.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event1 -> event.setCancelled(true));
            for (Map.Entry<String, Map<String, Object>> ranks : target.getCurrentNation().getRanks().entrySet()) {
                Rank rank = Rank.get(ranks.getValue());
                if (rank.getName().equalsIgnoreCase("leader")) continue;
                if (rank.getPriority() >= profile.getCurrentNationRank().getPriority()) continue;
                if (x + 1 > 8) {
                    y++;
                    x = 0;
                }
                menu.addItem(new GuiItem(new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§b" + rank.getName()).addLoreLine("§7§oClick to assign").build(), event1 -> {
                    event1.setCancelled(true);
                    if (Permission.hasNationPermission(profile, Permission.EDIT_RANKS)) {
                        requester.performCommand("n rank set " + target.getAsOfflinePlayer().getName() + " " + rank.getName());
                        open(gui, requester, MenuPage.MEMBERS);
                    }
                }), x, y);
                x++;
                gui.addPane(menu);
                gui.show(requester);
            }
        });
        menu.addItem(kick, 0, 0);
        menu.addItem(changeRank, 1, 0);
        gui.addPane(menu);
        gui.show(requester);
    }

    enum MenuPage {

        MAIN("§eMain", Material.CYAN_BANNER),
        MEMBERS("§cMembers", Material.PLAYER_HEAD),
        PERMISSIONS("§3Permissions", Material.WRITABLE_BOOK),
        RELATIONS("§9Relations", Material.ENCHANTED_GOLDEN_APPLE),
        SETTINGS("§bSettings", Material.COMMAND_BLOCK),
        RESEARCH("§6Research", Material.LECTERN);

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
