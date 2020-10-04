package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.boosts.DoubleExpBoost;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BoostCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (args.length) {
            default:
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                Gui boostGui = new Gui(Main.getInstance(), 3, "&dBoosters");
                StaticPane pane = new StaticPane(0, 0, 9, 3);
                boolean alreadyDoubleExp = instance.getUtilLists().boosts.containsKey(Boost.BoostType.DOUBLE_EXP);
                ItemStack doubleExp = alreadyDoubleExp ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).addLoreLine("§c§oSomeone already boosted").build() : new ItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayName("§bDouble-EXP §8(§d2B§8)").addLoreLine("§7With this booster").addLoreLine("§7everyone on the server").addLoreLine("§7will get double-EXP").addLoreLine("§7for §a10 minutes§7!").build();
                pane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
                pane.addItem(new GuiItem(doubleExp, event -> {
                    event.setCancelled(true);
                    if (!alreadyDoubleExp) {
                        if (profile.getBoosts() < 2) {
                            Lang.NOT_ENOUGH_BOOSTS.send(player);
                            return;
                        }
                        instance.getUtilLists().boosts.put(Boost.BoostType.DOUBLE_EXP, new DoubleExpBoost());
                        Lang.PLAYER_BOOSTED.broadcast("%PLAYER%;" + player.getDisplayName(), "%BOOST%;Double-EXP");
                    }
                }), 2, 1);
                boostGui.addPane(pane);
                boostGui.show(player);
                break;
            case 3:
                if (!sender.hasPermission("earth.admin")) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                    if (targetUUID == null) {
                        Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                        return false;
                    }
                    Profile target = instance.getProfile(targetUUID);
                    if (!StringUtils.isNumeric(args[2])) {
                        Lang.INVALID_INPUT.send(sender);
                        return false;
                    }
                    target.setBoosts(target.getBoosts() + Integer.parseInt(args[2]));
                    target.save();
                    sender.sendMessage("§aDone.");
                }
                break;
        }
        return false;
    }

}
