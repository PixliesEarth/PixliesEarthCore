package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mapNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"map"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("chat", 1);
        returner.put("gui", 1);
        returner.put("scoreboard", 1);
        return returner;
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
        if (args.length == 0) {
            Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n map <chat/gui/scoreboard>");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "chat":
                renderChatMap(player);
                break;
            case "gui":
                renderGuiMap(player);
                break;
            case "scoreboard":
                if (instance.getUtilLists().scoreboardMaps.contains(player.getUniqueId())) {
                    instance.getUtilLists().scoreboardMaps.remove(player.getUniqueId());
                    Lang.SCOREBOARDMAP_DISABLED.send(player);
                } else {
                    instance.getUtilLists().scoreboardMaps.add(player.getUniqueId());
                    Lang.SCOREBOARDMAP_ENABLED.send(player);
                }
                break;
            default:
                Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n map <chat/gui/scoreboard>");
                break;
        }
        return false;
    }

    //<editor-fold desc="Rendering methods">
    public void renderGuiMap(Player player) {
        final long start = System.currentTimeMillis();
        Inventory inv = Bukkit.createInventory(null, 6 * 9, "§bClaim-map");
        Profile profile = instance.getProfile(player.getUniqueId());
        final int height = 2;
        final int width = 4;

        final int playerCX = player.getLocation().getChunk().getX();
        final int playerCZ = player.getLocation().getChunk().getZ();
        final World world = player.getWorld();
        for (int row = height; row >= -height; row--) {
            for (int x = width; x >= -width; x--) {
                final int chunkX = playerCX - x,
                        chunkZ = playerCZ - row;
                NationChunk nc = NationChunk.get(world.getName(), chunkX, chunkZ);
                if (chunkX == playerCX && chunkZ == playerCZ) {
                    if (profile.isInNation()) {
                        if (nc == null) {
                            inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getName()).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§cWilderness").build());
                        } else {
                            char colChar = Nation.getRelation(nc.getNationId(), profile.getNationId()).colChar;
                            Nation nation = nc.getCurrentNation();
                            inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getName()).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§" + colChar + nation.getName()).addLoreLine("§7§o" + nation.getDescription()).build());
                        }
                    } else {
                        if (nc == null) {
                            inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getName()).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§cWilderness").build());
                        } else {
                            Nation nation = nc.getCurrentNation();
                            inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getName()).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§f" + nation.getName()).addLoreLine("§7§o" + nation.getDescription()).build());
                        }
                    }
                } else {
                    if (profile.isInNation()) {
                        if (nc == null) {
                            inv.addItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§cWilderness").build());
                        } else {
                            char colChar = Nation.getRelation(nc.getNationId(), profile.getNationId()).colChar;
                            Nation nation = nc.getCurrentNation();
                            inv.addItem(new ItemBuilder(Methods.getStainedGlassPaneByColChar(colChar)).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§" + colChar + nation.getName()).addLoreLine("§7§o" + nation.getDescription()).build());
                        }
                    } else {
                        if (nc == null) {
                            inv.addItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§cWilderness").build());
                        } else {
                            Nation nation = nc.getCurrentNation();
                            inv.addItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§b" + chunkX + "§8, §b" + chunkZ).addLoreLine("§f" + nation.getName()).addLoreLine("§7§o" + nation.getDescription()).build());
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++)
            inv.setItem(i + 45, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        player.openInventory(inv);
        player.sendMessage(System.currentTimeMillis() - start + "ms");
    }

    public void renderChatMap(Player player) {
        final long start = System.currentTimeMillis();
        Profile profile = instance.getProfile(player.getUniqueId());
        List<TextComponent> rows = new ArrayList<>();
        final int height = 6;
        final int width = (height * 2);

        final int playerCX = player.getLocation().getChunk().getX();
        final int playerCZ = player.getLocation().getChunk().getZ();
        final World world = player.getWorld();
        for (int row = height; row >= -height; row--) {
            TextComponent comp = new TextComponent();
            for (int x = width; x >= -width; x--) {
                final int chunkX = playerCX - x,
                        chunkZ = playerCZ - row;
                NationChunk nc = NationChunk.get(world.getName(), chunkX, chunkZ);
                if (chunkX == playerCX && chunkZ == playerCZ) {
                    if (nc == null) {
                        TextComponent cComp = new TextComponent("§e█");
                        cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cWilderness\n§7You may claim here.")));
                        cComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n claim " + chunkX +  ";" + chunkZ));
                        comp.addExtra(cComp);
                    } else {
                        Nation nation = nc.getCurrentNation();
                        TextComponent cComp = new TextComponent("§e█");
                        cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§f" + nation.getName() + "\n" + "§7" + nation.getDescription())));
                        comp.addExtra(cComp);
                    }
                } else {
                    if (profile.isInNation()) {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cWilderness\n§7You may claim here.")));
                            cComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/n claim " + chunkX +  ";" + chunkZ));
                            comp.addExtra(cComp);
                        } else {
                            char colChar = Nation.getRelation(nc.getNationId(), profile.getNationId()).colChar;
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§" + colChar + "█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§" + colChar + nation.getName() + "\n" + "§7" + nation.getDescription())));
                            comp.addExtra(cComp);
                        }
                    } else {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cWilderness")));
                            comp.addExtra(cComp);
                        } else {
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§f█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§f" + nation.getName() + "\n" + "§7" + nation.getDescription())));
                            comp.addExtra(cComp);
                        }
                    }
                }
            }
            rows.add(comp);
        }
        for (TextComponent r : rows)
            player.spigot().sendMessage(r);
        player.sendMessage("§7Legend: §e█You §8| §b█Yours §8| §d█Ally §8| §2█Wilderness");
        player.sendMessage(System.currentTimeMillis() - start + "ms");
    }
    //</editor-fold>
}
