package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Home;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Home home;
        switch (args.length) {
            case 0:
                player.sendMessage(Methods.getCenteredMessage("§8-= §bYour homes §8=-"));
                for (String s : profile.getHomes()) {
                    home = Home.fromString(s);
                    TextComponent comp = new TextComponent("§8* §b" + home.getName());
                    comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7§oClick to teleport...")));
                    comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + home.getName()));
                    player.sendMessage(comp);
                }
                break;
            case 1:
                home = Home.getByName(profile, args[0]);
                if (home == null) {
                    Lang.X_DOESNT_EXIST.send(player, "%X%;Home");
                    return false;
                }
                profile.teleport(home.asLocation(), home.getName());
                break;
            case 2:
                if (args[0].equalsIgnoreCase("add")) {
                    if (Home.getByName(profile, args[1]) != null) {
                        Lang.X_ALREADY_EXISTS.send(player, "%X%;Home");
                        return false;
                    }
                    if (profile.getHomes().size() + 1 > profile.canAddHomes() && !profile.isStaff()) {
                        Lang.MAX_HOMESIZE_REACHED.send(player);
                        return false;
                    }
                    home = new Home(args[1], player.getLocation());
                    profile.getHomes().add(home.serialize());
                    profile.save();
                    Lang.HOME_ADDED.send(player, "%HOME%;" + home.getName());
                } else if (args[0].equalsIgnoreCase("remove")) {
                    home = Home.getByName(profile, args[1]);
                    if (home == null) {
                        Lang.X_DOESNT_EXIST.send(player, "%X%;Home");
                        return false;
                    }
                    profile.getHomes().remove(home.serialize());
                    profile.save();
                    Lang.HOME_REMOVED.send(player, "%HOME%;" + home.getName());
                }
                break;
        }
        return false;
    }

}
