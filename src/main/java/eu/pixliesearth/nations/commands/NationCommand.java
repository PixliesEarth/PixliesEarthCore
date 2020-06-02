package eu.pixliesearth.nations.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import eu.pixliesearth.Main;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.claimNation;
import eu.pixliesearth.nations.commands.subcommand.nation.createNation;
import eu.pixliesearth.nations.commands.subcommand.nation.disbandNation;
import me.lucko.commodore.Commodore;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class NationCommand implements CommandExecutor, TabExecutor {

    public Set<SubCommand> getSubCommands() {
        // ADD ALL SUBCOMMANDS INTO THIS HASHSET<>();
        Set<SubCommand> subCommands = new HashSet<>();
        subCommands.add(new createNation());
        subCommands.add(new disbandNation());
        subCommands.add(new claimNation());
        return subCommands;
    }

    public Map<String, SubCommand> subMap() {
        Map<String, SubCommand> map = new HashMap<>();
        for (SubCommand sub : getSubCommands())
            for (String s : sub.aliases())
                map.put(s, sub);
        return map;
    }

    public Set<String> getSubCommandAliases() {
        // ADD ALL SUBCOMMAND ALIASES INTO THIS HASHSET
        Set<String> subCommandAliases = new HashSet<>();
        for (SubCommand sub : getSubCommands())
            subCommandAliases.addAll(Arrays.asList(sub.aliases()));
        return subCommandAliases;
    }

    private static Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {

        /*
                GO THROUGH ALL SUBCOMMANDS AND THEIR ALIASES,
                IF ONE OF THEM MATCHES WITH THE STRINGS[0],
                EXECUTE SUBCOMMAND.
         */
        boolean found = false;
        if (strings.length > 0) {

            List<String> list = new ArrayList<>(Arrays.asList(strings));
            list.remove(strings[0]);
            String[] args = list.toArray(new String[0]);

            for (SubCommand sub : getSubCommands())
                for (String s : sub.aliases())
                    if (strings[0].equalsIgnoreCase(s) ) {
                        found = true;
                        if (sub.staff() && sender instanceof Player) {
                            if (instance.getPlayerLists().staffMode.contains(((Player) sender).getUniqueId())) {
                                sub.execute(sender, args);
                            } else {
                                sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
                            }
                        } else {
                            sub.execute(sender, args);
                        }
                    }
            if (!found)
                sendHelp(sender);
        } else {
            sendHelp(sender);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        if (args.length == 1)
            StringUtil.copyPartialMatches(args[0], getSubCommandAliases(), completions);
        if (args.length == 2 && subMap().get(args[0]) != null) {
            StringUtil.copyPartialMatches(args[1], Arrays.asList(subMap().get(args[0]).autocompletion()), completions);
        }

        Collections.sort(completions);

        return completions;
    }

    //TODO IMPLEMENT BRIGADER/COMMODORE
/*    private static void registerCompletions(Commodore commodore, PluginCommand command) {
        commodore.register(command, LiteralArgumentBuilder.literal("nations")
                .then(RequiredArgumentBuilder.argument("some-argument", StringArgumentType.string()))
                .then(RequiredArgumentBuilder.argument("some-other-argument", BoolArgumentType.bool()))
        );
    }*/

    public void sendHelp(CommandSender sender) {
        sender.sendMessage("Coming soon");
    }

}
