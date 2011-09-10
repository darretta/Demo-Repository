package com.jboss.demo.mrg.messaging.handler;

/**
 * Abstract handler for handling system commands.
 * @author Mike Darretta
 */
public abstract class CommandHandler extends Thread {
	
	/** The command process */
	protected Process process;

	/**
	 * Default constructor.
	 */
	public CommandHandler() {}

	/**
	 * Executes this thread.
	 */
	@Override
	public void run() {
		try {
            this.process = Runtime.getRuntime().exec(getCommand());
            handleProcess();
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}
	
	/**
	 * Optional additional processing for command implementations.
	 * This superclass implementation is a no-op.
	 */
	protected void handleProcess() {}
	
	/**
	 * Destroys the process.
	 */
	protected void destroyProcess() {
		process.destroy();
	}
	
	/**
	 * Abstract method to return the command to execute.
	 * @return The command to execute.
	 */
	protected abstract String getCommand();
}
