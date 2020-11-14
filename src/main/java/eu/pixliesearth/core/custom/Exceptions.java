package eu.pixliesearth.core.custom;

import org.bukkit.event.Listener;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class that stores exceptions</h3>
 *
 * @apiNote Don't call this unless you need to, used to show issues that occur with more specific information
 *
 */
@Deprecated
public class Exceptions {
	/**
	 * 
	 * @author BradBot_1
	 * 
	 * <h3>This has not been fully documented yet!</h3>
	 * 
	 * TODO: notes
	 * 
	 */
	public static class ListenerRegistrationException extends RuntimeException {
		/**
		 * The serial id
		 */
		private static final long serialVersionUID = -2105088990192203786L;
		/**
		 * Creates an exception based on the {@link Listener} provided
		 * 
		 * @param l The {@link Listener} that has caused an exception
		 */
		public ListenerRegistrationException(Listener l) {
			super("Unable to register an event listener"
					+"Class: "+l.getClass().getPackage()+"."+l.getClass().getName()
					+"-End of exception-"
					);
		}
		/**
		 * Creates an exception based on the {@link Listener} class provided
		 * 
		 * @param c The {@link Listener} class that has caused an exception
		 */
		public ListenerRegistrationException(Class<?> c) {
			super("Unable to register an event listener"
					+"Class: "+c.getClass().getPackage()+"."+c.getName()
					+"-End of exception-"
					);
		}
	}
	
	public static class CustomItemCreationException extends RuntimeException {
		/**
		 * The serial id
		 */
		private static final long serialVersionUID = -283013667601881018L;
		/**
		 * Creates an exception based on a {@link CustomItem} that is provided
		 * 
		 * @param c The {@link CustomItem} that failed to be created
		 */
		public CustomItemCreationException(CustomItem c) {
			super("Unable to create a custom item"
					+"UUID: "+c.getUUID()
					+"Name: "+c.getDefaultDisplayName()
					+"Class: "+c.getClass().getPackage()+"."+c.getClass().getName()
					+"-End of exception-"
					);
		}
		/**
		 * Creates an exception based on a {@link CustomItem} class that is provided
		 * 
		 * @param c The {@link CustomItem} class that could not be made into an item
		 */
		public CustomItemCreationException(Class<? extends CustomItem> c) {
			super("Unable to create a custom item"
					+"Class: "+c.getPackage()+"."+c.getClass().getName()
					+"-End of exception-"
					);
		}
		/**
		 * 
		 * @param c The {@link CustomItem} that failed to be created
		 * @param note Extra information about the error
		 */
		public CustomItemCreationException(CustomItem c, String note) {
			super("Unable to create a custom item"
					+"UUID: "+c.getUUID()
					+"Name: "+c.getDefaultDisplayName()
					+"Class: "+c.getClass().getPackage()+"."+c.getClass().getName()
					+"Note: "+note
					+"-End of exception-"
					);
		}
	}
}
