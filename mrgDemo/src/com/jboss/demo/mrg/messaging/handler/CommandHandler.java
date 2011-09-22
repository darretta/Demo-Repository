package com.jboss.demo.mrg.messaging.handler;

/**
 * Abstract handler for handling system commands.
 * @author Mike Darretta
 */
public abstract class CommandHandler extends Thread {
	
	/** The command process */
	protected Process process;
	
	/** 
	 * The retry time limit in milliseconds. This is useful for
	 * processes that may face race conditions against other processes.
	 * A typical retry limit may be 5000 milliseconds (5 seconds).
	 * This value defaults to 0 (no retries allowed).
	 */
	protected int retryLimitInMillis = 0;
	
	/** The log handler */
	protected LogHandler logHandler;

	/**
	 * Constructor.
	 * @param logHandler The log handler.
	 */
	public CommandHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}
	
	/**
	 * Returns the process.
	 * @return The process.
	 */
	public Process getProcess() {
		return process;
	}
	
	/**
	 * Returns the log handler.
	 * @return The log handler.
	 */
	public LogHandler getLogHandler() {
		return logHandler;
	}
	
	/**
	 * Executes this command.
	 */
	public void execute() {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + retryLimitInMillis;
	    boolean done = false;
		try {
			while (!done) {
                this.process = Runtime.getRuntime().exec(getCommand());
                Thread.sleep(100);
                try {
                	int exitValue = process.exitValue();
                	
                	// No exception means process exited! Attempt a retry...
                	if (System.currentTimeMillis() < endTime) {
                		logHandler.logWithNewline("[" + getCommand() + "] exited with value of " + exitValue + "...retrying....");
                	} else {
                		logHandler.logWithNewline("[" + getCommand() + "] did not start within the allotted time...exiting....");
                		done = true;
                	}
                } catch (IllegalThreadStateException itse) {
                	// This exception is only thrown when the process has NOT exited...i.e., it is running.
                	logHandler.logWithNewline("[" + getCommand() + "] started successfully!");
                	done = true;
                    handleProcess();
                }
			}
	        logHandler.logWithNewline("Exiting thread [" + this.getThreadGroup() + "]");
        } catch (Throwable t) {
            logHandler.log(t);
        }
	}
	
	/**
	 * Sets the retry limit in milliseconds.
	 * @param retryLimitInMillis The retry limit in milliseconds.
	 */
	public void setRetryLimitInMillis(int retryLimitInMillis) {
		this.retryLimitInMillis = retryLimitInMillis;
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
		try {
		    logHandler.logWithNewline("Stopping [" + getCommand() + "].");
		} catch (LoggingException le) {
			le.printStackTrace();
		}
		process.destroy();
	}
	
	/**
	 * Abstract method to return the command to execute.
	 * @return The command to execute.
	 */
	protected abstract String getCommand();
}
