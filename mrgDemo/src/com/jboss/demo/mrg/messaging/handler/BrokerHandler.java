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
	 * Constructor uses the default broker port.
	 * @param logHandler The log handler.
	 */
	public BrokerHandler(LogHandler logHandler) {
		this(DEFAULT_PORT, logHandler);
	}
	
	/**
	 * Constructor.
	 * @param port The broker port.
	 * @param logHandler The log handler.
	 */
	public BrokerHandler(int port, LogHandler logHandler) {
		super(logHandler);
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
