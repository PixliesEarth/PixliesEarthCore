package eu.pixliesearth.warsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GulagSetSpawnTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        final List<String> completions = new ArrayList<>();
        //TODO: fix im retarded
        if(args.length == 1){

            if(args[0].toLowerCase().startsWith("s")){
                completions.add("spectator");
                return completions;
            }
            if(args[0].toLowerCase().startsWith("c")){
                completions.add("cap");
                return completions;
            }
            if(args[0].toLowerCase().startsWith("f")){
                if(args[0].toLowerCase().endsWith("1")){
                    completions.add("fighter1");
                    return completions;
                }else if(args[0].toLowerCase().endsWith("2")){
                    completions.add("fighter2");
                    return completions;
                }else {
                    completions.add("fighter1");
                    completions.add("fighter2");
                    return completions;
                    }
                }else{
                completions.add("spectator");
                completions.add("fighter1");
                completions.add("fighter2");
                completions.add("cap");
                return completions;
            }

        }else{
            return null;
        }

    }
}
