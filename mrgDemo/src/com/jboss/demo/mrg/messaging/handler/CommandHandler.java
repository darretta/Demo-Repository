package com.jboss.demo.mrg.messaging.handler;

/**
 * Abstract handler for handling system commands.
 * @author Mike Darretta
 */
public abstract class CommandHandler extends Thread {

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
            Process p = Runtime.getRuntime().exec(getCommand());
            handleProcess(p);
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}
	
	/**
	 * Optional additional processing for command implementations.
	 * This superclass implementation is a no-op.
	 * @param p The process.
	 */
	protected void handleProcess(Process p) {}
	
	/**
	 * Optional additional processing to handle a subsequent
	 * end of command execution. This should <strong>not</strong> override
	 * the <code>finalize</code> method. The superclass implementation is a no-op.
	 */
	protected void doFinalize() {}
	
	/**
	 * Abstract method to return the command to execute.
	 * @return The command to execute.
	 */
	protected abstract String getCommand();
}
