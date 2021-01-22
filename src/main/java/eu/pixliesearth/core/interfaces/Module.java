package eu.pixliesearth.core.interfaces;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.modules.ChatSystem;
import eu.pixliesearth.core.modules.PrivateMessage;
import eu.pixliesearth.core.modules.WarpSystem;
import eu.pixliesearth.core.modules.economy.EconomySystem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public interface Module {

    /**
     * @return Identifier of the module
     */
    String name();

    /**
     * @return Is the module enabled?
     */
    boolean isEnabled();

    /**
     * Plugin instance
     */
    Main instance = Main.getInstance();

    /**
     * config of the plugin for checks
     */
    FileConfiguration config = instance.getConfig();

    /**
     * @return All modules
     */
    static Set<Module> getModules() {
        Set<Module> returnable = new HashSet<>();
        returnable.add(new WarpSystem());
        returnable.add(new ChatSystem());
        returnable.add(new PrivateMessage());
        returnable.add(new EconomySystem());
        return returnable;
    }

    /**
     * @param name search-keyword
     * @return a module
     */
    static Module getByName(String name) {
        for (Module modules : getModules())
            if (modules.name().equalsIgnoreCase(name))
                return modules;
        return null;
    }

}
