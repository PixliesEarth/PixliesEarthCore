package eu.pixliesearth.core.interfaces;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.reflections.Reflections;

import java.util.*;

public interface Module {

    String name();

    boolean enabled();

    Main instance = Main.getInstance();

    FileConfiguration config = instance.getConfig();

    static Set<Module> getModules() {
        Set<Module> returnable = new HashSet<>();
        returnable.add(new WarpSystem());
        returnable.add(new ChatSystem());
        returnable.add(new PrivateMessage());
        returnable.add(new EconomySystem());
        return returnable;
    }

    static Module getByName(String name) {
        for (Module modules : getModules())
            if (modules.name().equalsIgnoreCase(name))
                return modules;
        return null;
    }



}
