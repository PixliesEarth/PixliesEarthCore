package eu.pixliesearth.nations.commands.subcommand.nation;

import com.google.gson.Gson;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.settlements.Settlement;
import eu.pixliesearth.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class settlementsCommand extends SubCommand implements Listener {

    @Override
    public String[] aliases() {
        return new String[]{"settlements", "home", "homes"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("travel", 1);
        returner.put("remove", 1);
        returner.put("add", 1);
        if (sender instanceof Player player) {
            Profile profile = instance.getProfile(player.getUniqueId());
            if (profile.isInNation()) {
                for (String s : profile.getCurrentNation().getSettlements().keySet())
                    returner.put(s, 2);
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
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        switch (args.length) {
            case 0:
                Inventory inventory = Bukkit.createInventory(null, 9 * 3, Component.text("§bSettlements"));
                for (String s : nation.getSettlements().values()) {
                    Settlement st = new Gson().fromJson(s, Settlement.class);
                    if (st.isCapital()) {
                        inventory.addItem(new ItemBuilder(Material.BELL).setDisplayName("§b" + st.getName()).addLoreLine("§eCapital of the nation").addLoreLine("§7Cost: §e" + Energy.calculateNeeded(player.getLocation(), st.getAsBukkitLocation()) + "§6★").addLoreLine("§7§oClick me to teleport...").build());
                    } else {
                        inventory.addItem(new ItemBuilder(Material.CAMPFIRE).setDisplayName("§b" + st.getName()).addLoreLine("§7Cost: §e" + Energy.calculateNeeded(player.getLocation(), st.getAsBukkitLocation()) + "§6★").addLoreLine("§7§oClick me to teleport...").build());
                    }
                }
                player.openInventory(inventory);
                break;
            case 2:
                if (args[0].equalsIgnoreCase("add")) {
                    if (!Permission.hasNationPermission(profile, Permission.MANAGE_SETTLEMENTS)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    String settlementName = args[1].replaceAll("[^a-zA-Z0-9]", "");
                    if (settlementName.length() < 3 || settlementName.length() > 15) {
                        sender.sendMessage(Lang.NATION + "§7Name has to be longer than 3 and short than 15 characters.");
                        return false;
                    }
                    if (nation.getSettlements().containsKey(settlementName)) {
                        Lang.SETTLEMENT_ALREADY_EXISTS.send(player);
                        return false;
                    }
                    if (!nation.getExtras().containsKey("settlements") && nation.getSettlements().size() == 3 || nation.getExtras().containsKey("settlements") && nation.getSettlements().size() + 1 >= (Double) nation.getExtras().get("settlements")) {
                        player.sendMessage(Lang.NATION + "§7You have reached your limit to set settlements.");
                        return false;
                    }
                    NationChunk nc = NationChunk.get(player.getChunk());
                    if (nc == null || !nc.getNationId().equals(nation.getNationId())) {
                        Lang.SETTLEMENT_HAS_TO_BE_IN_TERRITORY.send(player);
                        return false;
                    }
                    Settlement settlement = new Settlement(settlementName, new SimpleLocation(player.getLocation()).parseString(), false);
                    nation.getSettlements().put(settlementName, new Gson().toJson(settlement));
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet())
                        Lang.PLAYER_SET_SETTLEMENT.send(member, "%PLAYER%;" + player.getName(), "%SETTLEMENT%;" + args[1]);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (!Permission.hasNationPermission(profile, Permission.MANAGE_SETTLEMENTS)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    if (!nation.getSettlements().containsKey(args[1])) {
                        Lang.SETTLEMENT_DOESNT_EXIST.send(player);
                        return false;
                    }
                    nation.getSettlements().remove(args[1]);
                    nation.save();
                    for (Player member : nation.getOnlineMemberSet())
                        Lang.PLAYER_REMOVED_SETTLEMENT.send(member, "%PLAYER%;" + player.getName(), "%SETTLEMENT%;" + args[1]);
                }
                break;
        }
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        if (event.getView().getTitle().equals("§bSettlements")) {
            event.setCancelled(true);
            if (item.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) return;
            Player player = (Player) event.getWhoClicked();
            Profile profile = instance.getProfile(player.getUniqueId());
            if (!profile.isInNation()) player.closeInventory();
            Nation nation = profile.getCurrentNation();
            if (item.getType().equals(Material.CAMPFIRE) || item.getType().equals(Material.BELL)) {
                Settlement settlement = new Gson().fromJson(nation.getSettlements().get(item.getItemMeta().getDisplayName().replace("§b", "")), Settlement.class);
                settlement.teleport(player);
            }
        }
    }

}
