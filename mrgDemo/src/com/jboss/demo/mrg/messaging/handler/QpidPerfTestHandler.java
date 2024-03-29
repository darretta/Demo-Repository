package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Handler for the qpid-perftest command.
 * @author Mike Darretta
 */
public class QpidPerfTestHandler extends AbstractProducerHandler {

	/**
	 * Constructor for the default number of messages.
	 * @param logHandler The log handler.
	 */
	public QpidPerfTestHandler(LogHandler logHandler) {
		super(logHandler);
	}
	
	/**
	 * Constructor for a specific number of messages to send.
	 * @param numMessages The number of messages to send.
	 * @param logHandler The log handler.
	 */
	public QpidPerfTestHandler(int numMessages, LogHandler logHandler) {
		super(numMessages, logHandler);
	}
	
	/**
	 * Returns the command string.
	 * The command string.
	 */
	@Override
	protected String getCommand() {
		return Properties.getProperties().getProperty(
				Properties.QPID_PERF_TEST_CMD_STR) + " --count " + numMessages;
	}
}
