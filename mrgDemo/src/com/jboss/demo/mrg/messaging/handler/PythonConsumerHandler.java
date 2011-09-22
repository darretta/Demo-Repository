package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Handler for the AMQP 1.0 reference implementation Python consumer.
 * @author Mike Darretta
 */
public class PythonConsumerHandler extends CommandHandler {

	/** The number of messages to consume */
	protected int numMessages;
	
	/**
	 * Constructor.
	 * @param numMessages The number of messages to consume. This should nominally
	 * be equal to the number of messages produced.
	 * @param logHandler The log handler.
	 */
	public PythonConsumerHandler(int numMessages, LogHandler logHandler) {
		super(logHandler);
		this.numMessages = numMessages;
	}

	/**
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return Properties.getProperties().getProperty(
				Properties.AMQP_1_0_PYTHON_CLIENT_CONSUMER_CMD_STR) + " queue1 --count " + numMessages;
	}
}
