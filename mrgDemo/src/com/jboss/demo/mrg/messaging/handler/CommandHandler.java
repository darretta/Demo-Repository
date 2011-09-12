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
	 * Returns the process.
	 * @return The process.
	 */
	public Process getProcess() {
		return process;
	}
	
	/**
	 * Executes this command.
	 */
	public void execute() {
		try {
            this.process = Runtime.getRuntime().exec(getCommand());
            handleProcess();
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}

	/**
	 * Executes this command as a thread.
	 */
	@Override
	public void run() {
		execute();
	}
	
	/**
	 * Optional additional processing for command implementations.
	 * This superclass implementation is a no-op.
	 */
	protected void handleProcess() {}
	
	/**
	 * Destroys the process.
	 */
	public void destroyProcess() {
		process.destroy();
	}
	
	/**
	 * Abstract method to return the command to execute.
	 * @return The command to execute.
	 */
	protected abstract String getCommand();
}
