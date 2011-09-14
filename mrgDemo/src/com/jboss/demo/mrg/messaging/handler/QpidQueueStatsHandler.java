package com.jboss.demo.mrg.messaging.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jboss.demo.mrg.messaging.Properties;

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
	 * Default constructors assumes default hostname's IP address and the 
	 * <code>BrokerHandler.DEFAULT_PORT</code> for the port.
	 * @param logHandler The log handler.
	 * @throws UnknownHostException If the host IP address cannot be resolved.
	 */
	public QpidQueueStatsHandler(LogHandler logHandler) throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostAddress(), BrokerHandler.DEFAULT_PORT, logHandler);
	}
	
	/**
	 * Constructor for an IP address. This assumes a port of <code>BrokerHandler.DEFAULT_PORT</code>.
	 * @param ipAddress The IP address.
	 * @param logHandler The log handler.
	 */
	public QpidQueueStatsHandler(String ipAddress, LogHandler logHandler) {
		this(ipAddress, BrokerHandler.DEFAULT_PORT, logHandler);
	}
	
	/**
	 * Constructor for a port. This assumes the default hostname's IP address.
	 * @param port The port.
	 * @param logHandler The log handler.
	 * @throws UnknownHostException If the IP address cannot be resolved.
	 */
	public QpidQueueStatsHandler(int port, LogHandler logHandler) throws UnknownHostException {
		this(InetAddress.getLocalHost().getHostAddress(), port, logHandler);
	}
	
	/**
	 * Constructor for an IP address and port.
	 * @param ipAddress The IP address.
	 * @param port The port.
	 * @param logHandler The log handler.
	 */
	public QpidQueueStatsHandler(String ipAddress, int port, LogHandler logHandler) {
		super(logHandler);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * Returns the handled command.
	 * @return The handled command.
	 */
	@Override
	protected String getCommand() {
		return Properties.getProperties().getProperty(
				Properties.QPID_QUEUE_STATS_CMD_STR) + " -a " + ipAddress + ":" + port;
	}
}
