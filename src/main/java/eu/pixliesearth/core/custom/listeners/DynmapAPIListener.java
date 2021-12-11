package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.Main;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

public class DynmapAPIListener extends DynmapCommonAPIListener {

    @Override
    public void apiEnabled(DynmapCommonAPI dynmapCommonAPI) {
        Main.getInstance().getDynmapKernel().onEnable(dynmapCommonAPI);
    }

}
