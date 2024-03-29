package eu.pixliesearth.nations.commands.subcommand.nation;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationElection;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public class electionNation extends SubCommand implements Constants {

    @Override
    public String[] aliases() {
        return new String[]{"election", "elections"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        if (args.length == 2) {
            returner.put("create", 1);
            returner.put("addoption", 1);
        } else if (args.length == 3 && args[1].equalsIgnoreCase("addoption")) {
            returner.put("option", 2);
        } else if (args.length == 4 && args[1].equalsIgnoreCase("addoption")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (profile.isInNation()) {
                    for (String s : profile.getCurrentNation().getElections().keySet())
                        returner.put(s, 3);
                }
            }
        }
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Nation nation = profile.getCurrentNation();
        if (args.length == 0) {
            ChestGui gui = new ChestGui(6, "§b" + nation.getName() + " §7");

            StaticPane background = new StaticPane(0, 0, 9, 6, Pane.Priority.LOWEST);
            background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));

            PaginatedPane electionPane = new PaginatedPane(0, 0, 9, 5, Pane.Priority.HIGHEST);

            List<GuiItem> filler = new ArrayList<>();
            for (NationElection election : nation.getElections().values()) {
                ItemBuilder builder = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/74b89ad06d318f0ae1eeaf660fea78c34eb55d05f01e1cf999f331fb32d38942"));
                builder.setDisplayName("§b" + election.getTopic());
                if (!election.ended()) builder.addLoreLine("§c§l" + new Timer(election.getStart() + election.getDuration(), false).getRemainingAsString() + " §7left");
                builder.addLoreLine("§7ID: §b" + election.getId());
                builder.addLoreLine("§7Started by: §b" + Bukkit.getOfflinePlayer(election.getStartedBy()).getName());
                builder.addLoreAll(election.getOptionsFormatted());
                filler.add(new GuiItem(builder.build(), e -> {
                    e.setCancelled(true);
                    ChestGui voteGui = new ChestGui(3, "§b" + election.getTopic());

                    PaginatedPane optionsPane = new PaginatedPane(0, 0, 9, 3);
                    List<GuiItem> optionsFiller = new ArrayList<>();
                    for (Map.Entry<String, String> entry : election.getOptions().entrySet()) {
                        optionsFiller.add(new GuiItem(new ItemBuilder(Methods.getSBWoolByCC(entry.getKey())).setDisplayName(entry.getKey() + entry.getValue()).build(), e1 -> {
                            e1.setCancelled(true);
                            election.vote(player, entry.getValue());
                            player.closeInventory();
                        }));
                    }
                    optionsPane.populateWithGuiItems(optionsFiller);
                    voteGui.addPane(optionsPane);

                    voteGui.show(player);
                }));
            }
            electionPane.populateWithGuiItems(filler);

            if (electionPane.getPages() > 1) {
                background.addItem(new GuiItem(nextButtonMick, event -> {
                    event.setCancelled(true);
                    try {
                        electionPane.setPage(electionPane.getPage() + 1);
                        gui.update();
                    } catch (Exception ignored) {
                    }
                }), 8, 5);
                background.addItem(new GuiItem(backButtonMick, event -> {
                    event.setCancelled(true);
                    try {
                        electionPane.setPage(electionPane.getPage() - 1);
                        gui.update();
                    } catch (Exception ignored) {
                    }
                }), 0, 5);
            }

            gui.addPane(background);
            gui.addPane(electionPane);

            gui.show(player);
        } else {
            if (!Permission.MANAGE_ELECTIONS.hasPermission(sender)) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
            if (args[0].equalsIgnoreCase("create")) {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < args.length; i++) builder.append(args[i]).append(" ");
                String topic = builder.toString();
                if (topic.length() == 0) {
                    Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n election create <topic>");
                    return false;
                }
                NationElection election = NationElection.create(topic, player);
                nation.addElection(election);
                Lang.ELECTION_CREATED.send(sender, "%TOPIC%;" + topic + " §8(§b" + election.getId() + "§8)");
                if (MiniMick.getConfigs().containsKey(nation.getNationId())) {
                    try {
                        ServerTextChannel channel = MiniMick.getApi().getServerTextChannelById(MiniMick.getConfigs().get(nation.getNationId()).getUpdatesChannel()).get();
                        channel.sendMessage(
                                new EmbedBuilder()
                                        .setTitle(election.getTopic())
                                        .setDescription("Join earth to vote!")
                                        .setAuthor("Election", "", "https://storage.googleapis.com/gweb-uniblog-publish-prod/original_images/Learn_How_to_Vote_1024_x_1024_eWuQLoY.png")
                                        .setColor(Color.CYAN)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (args[0].equalsIgnoreCase("addoption")) {
                if (args.length != 3) {
                    Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n election addoption <option> <electionid>");
                    return false;
                }
                NationElection election = nation.getElections().get(args[2]);
                if (election == null) {
                    Lang.X_DOESNT_EXIST.send(sender, "%X%;Election");
                    return false;
                }
                if (election.getOptions().size() + 1 > NationElection.colorOptions.length) {
                    sender.sendMessage(Lang.NATION + "§7You have reached the max amount of election options.");
                    return false;
                }
                election.addOption(args[1]);
                nation.addElection(election);
                Lang.PLAYER_ADDED_X.send(sender, "%Y%;Election option", "%PLAYER%;" + sender.getName(), "%X%;" + args[1]);
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%/n election delete <electionId>");
                    return false;
                }
                NationElection election = nation.getElections().get(args[1]);
                if (election == null) {
                    Lang.X_DOESNT_EXIST.send(sender, "%X%;Election");
                    return false;
                }
                nation.deleteElection(args[1]);
                Lang.PLAYER_REMOVED_X.send(sender, "%X%;Election", "%Y%;" + election.getTopic());
            }
        }
        return true;
    }

}
