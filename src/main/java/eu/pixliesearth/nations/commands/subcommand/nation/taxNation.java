package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class taxNation extends SubCommand {

    public String[] aliases() {
        return new String[]{"tax"};
    }

    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        if (sender instanceof Player player) {
            Profile profile = instance.getProfile(player.getUniqueId());
            if (!profile.isInNation()) return map;
            Nation nation = profile.getCurrentNation();
            if (args.length == 2) {
                map.put("set", 1);
                map.put("disable", 1);
            } else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("set")) {
                    map.put("§cPERCENTAGE", 2);
                }
            }
        }
        return map;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Profile profile = instance.getProfile(player.getUniqueId());
            if (!profile.isInNation()) {
                Lang.NOT_IN_A_NATION.send(player);
                return false;
            }
            if (!Permission.MANAGE_TAX.hasPermission(player)) {
                Lang.NO_PERMISSIONS.send(player);
                return false;
            }
            if (args.length == 0) {
                Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n tax <set/disable> (percentage)");
                return false;
            }
            Nation nation = profile.getCurrentNation();
            switch (args[0].toLowerCase()) {
                case "set":
                    if (args.length > 1) {
                        try {
                            double percentage = Double.parseDouble(args[1]);
                            if (percentage > 70.0) {
                                sender.sendMessage(Lang.NATION + "§7You can't tax your people higher than §b70%§7.");
                                return false;
                            }
                            nation.setTaxationPercentage(percentage);
                            sender.sendMessage(Lang.NATION + "§7You changed the taxation percentage to §b" + percentage + "%§7.");
                            nation.broadcastDiscord("Taxation rate is now at " + percentage + "%", player.getName(), "https://minotar.net/avatar/" + player.getUniqueId(), Color.CYAN);
                        } catch (Exception e) {
                            sender.sendMessage(Lang.NATION + "§7You need to provide a tax percentage.");
                        }
                    }
                    break;

                case "disable":
                    nation.setTaxationEnabled(false);
                    sender.sendMessage(Lang.NATION + "§7You disabled taxation in your country.");
                    nation.broadcastDiscord("Taxation has been disabled.", player.getName(), "https://minotar.net/avatar/" + player.getUniqueId(), Color.CYAN);
                    break;
            }

        }
        return false;
    }
    
}
