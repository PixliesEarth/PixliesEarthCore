package eu.pixliesearth.nations.commands.subcommand.nation;

import com.google.gson.Gson;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.settlements.Settlement;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class settlementsCommand implements SubCommand, Listener {

    @Override
    public String[] aliases() {
        return new String[]{"settlements"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("travel", 1);
        returner.put("remove", 1);
        returner.put("add", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
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
                Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§bSettlements");
                int i = 12;
                for (int j = 0; j < inventory.getSize(); j++)
                    inventory.setItem(j, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
                for (String s : nation.getSettlements().values()) {
                    Settlement st = new Gson().fromJson(s, Settlement.class);
                    inventory.setItem(i, new ItemBuilder(Material.CAMPFIRE).setDisplayName("§b" + st.getName()).addLoreLine("§7§oClick me to teleport...").build());
                    i++;
                }
                player.openInventory(inventory);
                break;
            case 2:
                if (args[0].equalsIgnoreCase("add")) {
                    if (!Permission.hasNationPermission(profile, Permission.MANAGE_SETTLEMENTS)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    if (nation.getSettlements().containsKey(args[1])) {
                        Lang.SETTLEMENT_ALREADY_EXISTS.send(player);
                        return false;
                    }
                    //TODO HOW MANY CAN BE SET
                    NationChunk nc = NationChunk.get(player.getChunk());
                    if (nc == null || !nc.getNationId().equals(nation.getNationId())) {
                        Lang.SETTLEMENT_HAS_TO_BE_IN_TERRITORY.send(player);
                        return false;
                    }
                    Settlement settlement = new Settlement(args[1], new SimpleLocation(player.getLocation()).parseString(), false);
                    nation.getSettlements().put(args[1], new Gson().toJson(settlement));
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
            if (item.getType().equals(Material.CAMPFIRE)) {
                Settlement settlement = new Gson().fromJson(nation.getSettlements().get(item.getItemMeta().getDisplayName().replace("§b", "")), Settlement.class);
                settlement.teleport(player);
            }
        }
    }

}
