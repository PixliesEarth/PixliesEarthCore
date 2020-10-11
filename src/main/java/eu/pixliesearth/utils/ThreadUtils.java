package eu.pixliesearth.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to make running code on separate threads easier</h3>
 *
 */
public class ThreadUtils {
	
	public ThreadUtils() {
		randomiseThreadName();
	}
	
	public ThreadUtils(String threadName) {
		setThreadName("Thread_"+threadName);
	}
	
	public ThreadUtils(Runnable runnable) {
		randomiseThreadName();
		runOnThread(runnable);
	}
	
	public ThreadUtils(String threadName, Runnable runnable) {
		setThreadName("Thread_"+threadName);
		runOnThread(runnable);
	}
	/**
	 * The thread
	 */
	private @Getter @Setter Thread thread;
	/**
	 * The threads name
	 */
	private @Getter @Setter String threadName;
	/**
	 *  Runs the {@link Runnable} provided on the {@link Thread}
	 * 
	 * @param runnable The {@link Runnable} to be ran
	 */
	public void runOnThread(Runnable runnable) {
		if (getThread() == null) {
			setThread(new Thread (runnable, getThreadName()));
			getThread().start();
		} else 
			System.err.println("The thread "+getThreadName()+" is already started!");
	}
	/**
	 * Creates and returns a random thread name
	 * 
	 * @return The random thread name
	 */
	private String getRandomThreadName() {
		return "Thread_"+UUID.randomUUID().toString();
	}
	/**
	 * Creates and sets a random thread name
	 */
	private void randomiseThreadName() {
		setThreadName(getRandomThreadName());
	}
}
