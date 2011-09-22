package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Abstract handler for client producer commands.
 * @author Mike Darretta
 */
public abstract class AbstractProducerHandler extends CommandHandler {
	
	/** The number of messages to send */
	protected int numMessages;
	
	/** The default number of messages to send */
	public static final int DEFAULT_NUM_MESSAGES = 
		(Integer) Properties.getProperties().getIntegerProperty(
				Properties.DEFAULT_NUM_MSGS_PER_CLIENT_STR);

	/**
	 * Constructor for the default number of messages.
	 * @param logHandler The log handler.
	 */
	public AbstractProducerHandler(LogHandler logHandler) {
		this(DEFAULT_NUM_MESSAGES, logHandler);
	}
	
	/**
	 * Constructor for a specific number of messages to send.
	 * @param numMessages The number of messages to send.
	 * @param logHandler The log handler.
	 */
	public AbstractProducerHandler(int numMessages, LogHandler logHandler) {
		super(logHandler);
		this.numMessages = numMessages;
	}
	
	/**
	 * Returns the number of messages to send.
	 * @return The number of messages to send.
	 */
	public int getNumMessages() {
		return numMessages;
	}
}
