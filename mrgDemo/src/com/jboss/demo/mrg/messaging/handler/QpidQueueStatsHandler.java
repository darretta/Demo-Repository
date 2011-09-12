package com.jboss.demo.mrg.messaging.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Handler for the qpid-queue-stats command.
 * @author Mike Darretta
 */
public class QpidQueueStatsHandler extends CommandHandler {
	
	/** The broker IP address from which to collect stats */
	protected String ipAddress;
	
	/** The broker port from which to collect stats */
	protected int port;
	
	/**
	 * Default constructors assumes localhost's IP address and the 
	 * <code>BrokerHandler.DEFAULT_PORT</code> for the port.
	 * @throws UnknownHostException If the host IP address cannot be resolved.
	 */
	public QpidQueueStatsHandler() throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostAddress(), BrokerHandler.DEFAULT_PORT);
	}
	
	/**
	 * Constructor for an IP address. This assumes a port of <code>BrokerHandler.DEFAULT_PORT</code>.
	 * @param ipAddress The IP address.
	 */
	public QpidQueueStatsHandler(String ipAddress) {
		this(ipAddress, BrokerHandler.DEFAULT_PORT);
	}
	
	/**
	 * Constructor for a port. This assumes the local host's IP address.
	 * @param port The port.
	 * @throws UnknownHostException If the IP address cannot be resolved.
	 */
	public QpidQueueStatsHandler(int port) throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostAddress(), port);
	}
	
	/**
	 * Constructor for an IP address and port.
	 * @param ipAddress The IP address.
	 * @param port The port.
	 */
	public QpidQueueStatsHandler(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * Returns the handled command.
	 * @return The handled command.
	 */
	@Override
	protected String getCommand() {
		return "qpid-queue-stats -a " + ipAddress + ":" + port;
	}
}
