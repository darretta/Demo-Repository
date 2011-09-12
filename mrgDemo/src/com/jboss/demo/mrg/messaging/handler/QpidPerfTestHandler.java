package com.jboss.demo.mrg.messaging.handler;

/**
 * Handler for the qpid-perftest command.
 * @author Mike Darretta
 */
public class QpidPerfTestHandler extends CommandHandler {
	
	/** The number of messages to send */
	protected int numMessages;
	
	/** The default number of messages to send */
	public static final int DEFAULT_NUM_MESSAGES = 500000;

	/**
	 * Default constructor for the default number of messages.
	 */
	public QpidPerfTestHandler() {
		this(DEFAULT_NUM_MESSAGES);
	}
	
	/**
	 * Constructor for a specific number of messages to send.
	 * @param numMessages The number of messages to send.
	 */
	public QpidPerfTestHandler(int numMessages) {
		this.numMessages = numMessages;
	}
	
	/**
	 * Returns the number of messages to send.
	 * @return The number of messages to send.
	 */
	public int getNumMessages() {
		return numMessages;
	}
	
	/**
	 * Returns the command string.
	 * The command string.
	 */
	@Override
	protected String getCommand() {
		return "qpid-perftest --count " + numMessages;
	}
}
