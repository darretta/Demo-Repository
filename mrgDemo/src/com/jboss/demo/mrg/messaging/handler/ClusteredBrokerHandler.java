package com.jboss.demo.mrg.messaging.handler;

/**
 * Handler for a clustered QPID broker.
 * @author Mike Darretta
 */
public class ClusteredBrokerHandler extends BrokerHandler {
	
	/** The cluster name */
	protected String clusterName;
	
	/**
	 * Constructor. This is identical to <code>this(clusterName, 5672)</code>.
	 * @param clusterName The cluster name.
	 */
	public ClusteredBrokerHandler(String clusterName) {
		super();
		this.clusterName = clusterName;
	}
	
	/**
	 * Constructor.
	 * @param clusterName The cluster name.
	 * @param port The broker port.
	 */
	public ClusteredBrokerHandler(String clusterName, int port) {
		super(port);
		this.clusterName = clusterName;
	}

	/**
	 * Returns the cluster name.
	 * @return The cluster name.
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return super.getCommand() + " --cluster-name=" + clusterName;
	}

}
