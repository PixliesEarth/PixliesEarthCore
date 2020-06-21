package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AdoptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Main instance = Main.getInstance();
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
        if (targetUUID == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(player);
            return false;
        }
        if (profile.areRelated(targetUUID)) {
            Lang.ALREADY_RELATED.send(player);
            return false;
        }
        if (args[0].equalsIgnoreCase(player.getName())) {
            Lang.
        }
        Profile target = instance.getProfile(targetUUID);
        switch (args.length) {
            case 1:
                if (target.getRelations().get(profile.getUniqueId()).equals("REQ=ADOPT")) {
                    Lang.ALREADY_REQUESTED_ADOPTION.send(player);
                    return false;
                }
                target.getRelations().put(profile.getUniqueId(), "REQ=ADOPT");
                target.save();
                Lang.REQUESTED_ADOPTION.send(player, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
                if (target.isOnline()) {
                    Player targetPlayer = target.getAsOfflinePlayer().getPlayer();
                    Lang.PLAYER_SENT_ADOPTION_REQ.send(targetPlayer, "%PLAYER%;" + player.getName());
                    TextComponent accept = new TextComponent("§a" + Lang.ACCEPT.get(targetPlayer));
                    TextComponent deny = new TextComponent("§c" + Lang.DENY.get(targetPlayer));
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/adopt " + player.getName() + " accept"));
                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/adopt " + player.getName() + " deny"));
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick me to accept").create()));
                    deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClick me to deny").create()));
                    assert targetPlayer != null;
                    targetPlayer.spigot().sendMessage(accept);
                    targetPlayer.spigot().sendMessage(deny);
                }
                break;
            case 2:
                if (!profile.getRelations().get(targetUUID.toString()).equals("REQ=ADOPT")) {
                    Lang.NO_OPEN_X_REQUEST.send(player, "%X%;" + "Adoption");
                    return false;
                }
                if (args[1].equalsIgnoreCase("accept")) {
                    profile.getRelations().put(targetUUID.toString(), "parent");
                    target.getRelations().put(profile.getUniqueId(), "child");
                    profile.save();
                    target.save();
                    Lang.PLAYER_X_ADOPTED_Y.broadcast("%X%;" + target.getAsOfflinePlayer().getName(), "%Y%;" + player.getName());
                } else if (args[1].equalsIgnoreCase("deny")) {
                    profile.getRelations().remove(targetUUID.toString());
                    profile.save();
                    Lang.DENIED_X_REQUEST.send(player, "%X%;Adoption");
                }
                break;
        }

        return false;
    }

}
