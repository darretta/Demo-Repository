package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Handler for the AMQP 1.0 reference implementation Python consumer.
 * @author Mike Darretta
 */
public class PythonProducerHandler extends AbstractProducerHandler {

	/**
	 * Constructor for the default number of messages.
	 * @param logHandler The log handler.
	 */
	public PythonProducerHandler(LogHandler logHandler) {
		super(logHandler);
	}
	
	/**
	 * Constructor for a specific number of messages to send.
	 * @param numMessages The number of messages to send.
	 * @param logHandler The log handler.
	 */
	public PythonProducerHandler(int numMessages, LogHandler logHandler) {
		super(numMessages, logHandler);
	}
	
	/**
	 * Returns the command.
	 * @return The command.
	 */
	@Override
	protected String getCommand() {
		return Properties.getProperties().getProperty(
				Properties.AMQP_1_0_PYTHON_CLIENT_PRODUCER_CMD_STR) + " queue1 --count " + numMessages +
				    " Python client message";
	}
}
