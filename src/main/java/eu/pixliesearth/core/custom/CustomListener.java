package eu.pixliesearth.core.custom;

import eu.pixliesearth.Main;
import org.bukkit.event.Listener;

public class CustomListener implements Listener {

	protected static final Main instance = Main.getInstance();

	/**
	 * Called when the server is shutting down!
	 * 
	 * @param customFeatureLoader The currently active {@link CustomFeatureLoader}
	 * @param customFeatureHandler The {@link CustomFeatureLoader}'s {@link CustomFeatureHandler}
	 */
	public void onServerShutdown(CustomFeatureLoader customFeatureLoader, CustomFeatureHandler customFeatureHandler) {
		
	}

}
