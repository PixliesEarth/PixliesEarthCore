package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

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
            Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n map <chat/gui>");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "chat":
                renderChatMap(player);
                break;
            case "gui":
                renderGuiMap(player);
                break;
            default:
                Lang.WRONG_USAGE_NATIONS.send(player, "%USAGE%;/n map <chat/gui>");
                break;
        }
        return false;
    }

    public void renderGuiMap(Player player) {
        //TODO
    }

    public void renderChatMap(Player player) {
        final long start = System.currentTimeMillis();
        Profile profile = instance.getProfile(player.getUniqueId());
        List<TextComponent> rows = new ArrayList<>();
        final int height = 6;
        final int width = (height * 2);

        final int playerCX = player.getChunk().getX();
        final int playerCZ = player.getChunk().getZ();
        final World world = player.getWorld();
        for (int row = height; row >= -height; row--) {
            TextComponent comp = new TextComponent();
            for (int x = width; x >= -width; x--) {
                final int chunkX = playerCX - x,
                        chunkZ = playerCZ - row;
                NationChunk nc = NationChunk.get(world.getName(), chunkX, chunkZ);
                if (chunkX == playerCX && chunkZ == playerCZ) {
                    if (profile.isInNation()) {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2☻");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cWilderness\n§7You may claim here.").create()));
                            comp.addExtra(cComp);
                        } else {
                            char colChar = Nation.getRelation(nc.getNationId(), profile.getNationId()).colChar;
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§" + colChar + "☻");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§" + colChar + nation.getName() + "\n" + "§7" + nation.getDescription()).create()));
                            comp.addExtra(cComp);
                        }
                    } else {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2☻");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cWilderness").create()));
                            comp.addExtra(cComp);
                        } else {
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§f☻");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§f" + nation.getName() + "\n" + "§7" + nation.getDescription()).create()));
                            comp.addExtra(cComp);
                        }
                    }
                } else {
                    if (profile.isInNation()) {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cWilderness\n§7You may claim here.").create()));
                            comp.addExtra(cComp);
                        } else {
                            char colChar = Nation.getRelation(nc.getNationId(), profile.getNationId()).colChar;
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§" + colChar + "█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§" + colChar + nation.getName() + "\n" + "§7" + nation.getDescription()).create()));
                            comp.addExtra(cComp);
                        }
                    } else {
                        if (nc == null) {
                            TextComponent cComp = new TextComponent("§2█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cWilderness").create()));
                            comp.addExtra(cComp);
                        } else {
                            Nation nation = nc.getCurrentNation();
                            TextComponent cComp = new TextComponent("§f█");
                            cComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§f" + nation.getName() + "\n" + "§7" + nation.getDescription()).create()));
                            comp.addExtra(cComp);
                        }
                    }
                }
            }
            rows.add(comp);
        }
        for (TextComponent r : rows)
            player.spigot().sendMessage(r);
        player.sendMessage("§7Legend: §b█Yours §8| §d█Ally §8| §2█Wilderness");
        player.sendMessage(System.currentTimeMillis() - start + "ms");
    }

}
