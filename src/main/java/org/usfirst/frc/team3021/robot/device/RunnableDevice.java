package org.usfirst.frc.team3021.robot.device;

public abstract class RunnableDevice implements Runnable {
	
	private boolean isFirstPeriodic = true;
	
	private volatile boolean running = true;
	private volatile boolean paused = false;
	private final Object pauseLock = new Object();

	public RunnableDevice() {
		// set the device as initially in the paused
		pause();
		
		// put the device into a thread
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		
		// start the thread to begin executing
		thread.start();
	}

	public boolean isFirstPeriodic() {
		return isFirstPeriodic;
	}
	
	public void play() {
		isFirstPeriodic = true;
		
		synchronized (pauseLock) {
			paused = false;
			pauseLock.notifyAll(); // Unblocks thread
		}
	}

	public void pause() {
		paused = true;
	}
	
	@Override
	public void run() {
		isFirstPeriodic = true;
				
		while (running && !Thread.interrupted()) {
			if (shouldStop()) {
				break;
			}
			
			runPeriodic();
			
			isFirstPeriodic = false;
		}
	}

	protected abstract void runPeriodic();
	
	public void stop() {
		running = false;
	}
	
	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Ignore excpetion;
		}
	}
	
	private boolean shouldStop() {
		
		synchronized (pauseLock) {
			if (!running) {
				// may have changed while waiting to
				// synchronize on pauseLock
				return true;
			}
			
			if (paused) {
				try {
					pauseLock.wait();
				} catch (InterruptedException ex) {
					return true;
				}
				
				if (!running) {
					// running might have changed since we paused
					return true;
				}
			}
		}
		
		return false;
	}

}
