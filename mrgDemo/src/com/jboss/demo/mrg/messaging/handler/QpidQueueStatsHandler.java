package com.jboss.demo.mrg.messaging.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Handler for the qpid-queue-stats command.
 * @author Mike Darretta
 */
public class QpidQueueStatsHandler extends CommandHandler {
	
	/** The broker hostname from which to collect stats */
	protected String hostname;
	
	/** The broker port from which to collect stats */
	protected int port;
	
	/**
	 * Default constructors assumes localhost as the hostname as the 
	 * <code>BrokerHandler.DEFAULT_PORT</code> for the port.
	 * @throws UnknownHostException If the hostname cannot be resolved.
	 */
	public QpidQueueStatsHandler() throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostName(), BrokerHandler.DEFAULT_PORT);
	}
	
	/**
	 * Constructor for a hostname. This assumes a port of <code>BrokerHandler.DEFAULT_PORT</code>.
	 * @param hostname The hostname.
	 */
	public QpidQueueStatsHandler(String hostname) {
		this(hostname, BrokerHandler.DEFAULT_PORT);
	}
	
	/**
	 * Constructor for a port. This assumes a hostname of localhost.
	 * @param port The port.
	 * @throws UnknownHostException If the hostname cannot be resolved.
	 */
	public QpidQueueStatsHandler(int port) throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostName(), port);
	}
	
	/**
	 * Constructor for a hostname and port.
	 * @param hostname The hostname.
	 * @param port The port.
	 */
	public QpidQueueStatsHandler(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * Returns the handled command.
	 * @return The handled command.
	 */
	@Override
	protected String getCommand() {
		return "qpid-queue-stats -a " + hostname + ":" + port;
	}
}
