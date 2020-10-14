package eu.pixliesearth.nations.managers.dynmap;

import eu.pixliesearth.events.NationCreationEvent;
import eu.pixliesearth.events.NationDisbandEvent;
import eu.pixliesearth.events.TerritoryChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import static eu.pixliesearth.nations.managers.dynmap.commons.Constant.DYNMAP_PLUGIN_NAME;
import static eu.pixliesearth.nations.managers.dynmap.commons.Constant.FACTION_PLUGIN_NAME;

public class OurServerListener implements Listener {

    private final DynmapEngine kernel;

    public OurServerListener(final DynmapEngine kernel) {
        this.kernel = kernel;
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        final Plugin plugin = event.getPlugin();
        final String name = plugin.getDescription().getName();
        if(DYNMAP_PLUGIN_NAME.equals(name) || FACTION_PLUGIN_NAME.equals(name)) {
            if(kernel.getDynmap().isEnabled() && kernel.getFactions().isEnabled()) {
                kernel.activate();
            }
        }
    }

/*    @EventHandler(priority= EventPriority.MONITOR)
    public void onFPlayerJoin(EventFactionsMembershipChange event) {
        if(event.isCancelled())
            return;
        if (kernel.isPlayersets()) {
            kernel.requestUpdatePlayerSet(event.getNewFaction().getId());
        }
    }*/

    @EventHandler(priority=EventPriority.MONITOR)
    public void onNationCreation(NationCreationEvent event) {
        if(event.isCancelled()) {
            return;
        }
        if(kernel.isPlayersets()) {
            kernel.requestUpdatePlayerSet(event.getNation().getNationId());
        }
        kernel.requestUpdateFactions();
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onNationRemoval(NationDisbandEvent event) {
        if(event.isCancelled()) {
            return;
        }
        if(kernel.isPlayersets()) {
            kernel.requestUpdatePlayerSet(event.getNation().getNationId());
        }
        kernel.requestUpdateFactions();
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onFactionChunksChange(TerritoryChangeEvent event) {
        if(event.isCancelled()) {
            return;
        }
        kernel.requestUpdateFactions();
    }

}
