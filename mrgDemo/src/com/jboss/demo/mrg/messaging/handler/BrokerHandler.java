package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Handler for handling QPID non-clustered brokers.
 * @author Mike Darretta
 */
public class BrokerHandler extends CommandHandler {
	
	/** The broker port */
	protected int port;
	
	/** The default broker port */
	public static final int DEFAULT_PORT = 
		(Integer) Properties.getProperties().getIntegerProperty(
			Properties.DEFAULT_BROKER_PORT_STR);
	
	/**
	 * Default constructor uses the default broker port.
	 */
	public BrokerHandler() {
		this(DEFAULT_PORT);
	}
	
	/**
	 * Constructor.
	 * @param port The broker port.
	 */
	public BrokerHandler(int port) {
		this.port = port;
	}
	
	/**
	 * Returns the broker port.
	 * @return The broker port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return Properties.getProperties().getProperty(
				Properties.QPID_CMD_STR) + " --port=" + port;
	}
}
