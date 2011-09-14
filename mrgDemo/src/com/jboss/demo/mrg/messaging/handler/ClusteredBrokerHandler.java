package com.jboss.demo.mrg.messaging.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Handler for a clustered QPID broker.
 * <br>
 * Since port management for the entire cluster has been assigned to this class,
 * the class constructors do not include the <code>port</code> attribute as
 * defined by the superclass. This attribute is resolved for every new instantiation.
 * @author Mike Darretta
 */
public class ClusteredBrokerHandler extends BrokerHandler {
	
	/** The cluster name */
	protected String clusterName;
	
	/** The data directory */
	protected String dataDir;
	
	/** The default cluster name */
	public static final String DEFAULT_CLUSTER_NAME="demo-cluster";
	
	/** The default data directory prefix */
	public static final String DEFAULT_DATA_DIR_PREFIX="/tmp/qpid-data-dir-";
	
	protected static Collection<Integer> portsInUse;
	
	/**
	 * Constructor. This is identical to <code>this(DEFAULT_CLUSTER_NAME)</code>.
	 * @param logHandler The log handler.
	 */
	public ClusteredBrokerHandler(LogHandler logHandler) {
		this(DEFAULT_CLUSTER_NAME, logHandler);
	}
	
	/**
	 * Constructor for a cluster name and port. This implementation uses an auto-generated 
	 * data directory name.
	 * @param clusterName The cluster name.
	 * @param logHandler The log handler.
	 */
	public ClusteredBrokerHandler(String clusterName, LogHandler logHandler) {
		this(clusterName, null, logHandler);
		this.dataDir = DEFAULT_DATA_DIR_PREFIX + port + "-" + this.hashCode();
	}
	
	/**
	 * Constructor for non-default attribute values.
	 * @param clusterName The cluster name.
	 * @param dataDir The data directory.
	 * @param logHandler The log handler.
	 */
	public ClusteredBrokerHandler(String clusterName, String dataDir, LogHandler logHandler) {
		super(DEFAULT_PORT, logHandler);
		this.clusterName = clusterName;
		this.dataDir = dataDir;
		this.port = resolveNextAvailablePort();
	}

	/**
	 * Returns the cluster name.
	 * @return The cluster name.
	 */
	public String getClusterName() {
		return clusterName;
	}
	
	/**
	 * Returns the data directory.
	 * @return The data directory.
	 */
	public String getDataDir() {
		return dataDir;
	}
	
	/**
	 * Resolves the next available port. The algorithm assumes the next available
	 * port to be the <code>DEFAULT_PORT</code>. If the <code>DEFAULT_PORT</code> is 
	 * not available, then the port resolves one digit above the highest numbered
	 * port in use. 
	 * <br>
	 * This method is synchronized for thread safety.
	 * @return The next available port per the described algorithm.
	 */
	protected synchronized int resolveNextAvailablePort() {
		int port = DEFAULT_PORT;
		
		if (portsInUse == null) {
			portsInUse = new ArrayList<Integer> ();
		} else if (portsInUse.isEmpty() ||
				!portsInUse.contains(DEFAULT_PORT)) {
			// Do nothing
		} else {
			int portInUse;
			Iterator<Integer> i = portsInUse.iterator();
			while (i.hasNext()) {
				portInUse = i.next();
				if (portInUse > port) {
					port = portInUse;
				}
			}
			
			// Set the port to one digit above the highest port in use.
			port++;
		}
		
		portsInUse.add(port);
		
		return port;
	}

	/**
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return super.getCommand() + " --cluster-name=" + clusterName + " --data-dir=" + dataDir;
	}
	
	/**
	 * Destroys the process. This overriding method attempts to remove
	 * the temporary data directory as well.
	 */
	@Override
	public void destroyProcess() {
		super.destroyProcess();
		DataDirRemoverHandler ddhHandler = 
			new DataDirRemoverHandler(getLogHandler());
		ddhHandler.setRetryLimitInMillis(2000);
		ddhHandler.execute();
		portsInUse.remove(port);
	}
	
	/**
	 * Inner class command handler to remove the data directory.
	 * @author Mike Darretta
	 */
	protected class DataDirRemoverHandler extends CommandHandler {
		
		/**
		 * Constructor.
		 * @param handler The log handler.
		 */
		protected DataDirRemoverHandler(LogHandler handler) {
			super(handler);
		}
		
		/**
		 * Returns the command to execute.
		 * @return The command to execute.
		 */
		@Override
		protected String getCommand() {
			return "rm -rf " + dataDir;
		}
	}
}
