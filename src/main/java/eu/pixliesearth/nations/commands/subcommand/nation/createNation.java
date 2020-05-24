package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.Religion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class createNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"create", "form"};
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§bNATION §8| §7You have to be a player to execute that command.");
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (profile.isInNation()) {
            player.sendMessage("§bNATION §8| §7You are already in a nation.");
            return false;
        }
        String name = args[0];
        if (name == null) {
            player.sendMessage("§bNATION §8| §7You need to give a name.");
            return false;
        }
        if (Nation.getByName(name) != null) {
            player.sendMessage("§bNATION §8| §7There is already a nation with that name.");
            return false;
        }
        if (name.length() < 3 || name.length() > 10) {
            player.sendMessage("§bNATION §8| §7The name of your nation can only be min. §b3 §7and max. §b10 §7characters long.");
            return false;
        }
        //commented because broken mick = dum dum
       // Nation nation = new Nation(Methods.generateId(7), name, "No description :(", Era.START.getName(), Ideology.DEMOCRACY.name(), Religion.ATHEIST.name(),0,0.0, player.getUniqueId().toString(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        //nation.save();
       // profile.addToNation(nation.getNationId());
        Bukkit.broadcastMessage("§bNATION §8| §7Player §6" + player.getName() + " §7just formed the nation of §b" + name);
        return false;
    }

}
