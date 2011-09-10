package com.jboss.demo.mrg.messaging.handler;

/**
 * Handler for a clustered QPID broker.
 * @author Mike Darretta
 */
public class ClusteredBrokerHandler extends BrokerHandler {
	
	/** The cluster name */
	protected String clusterName;
	
	/** The data directory */
	protected String dataDir;
	
	/** The QPID cluster member ID */
	protected String memberId;
	
	/** The default cluster name */
	public static final String DEFAULT_CLUSTER_NAME="demo-cluster";
	
	/** The default data directory prefix */
	public static final String DEFAULT_DATA_DIR_PREFIX="/tmp/qpid-data-dir-";
	
	/**
	 * Default constructor. This is identical to <code>this(DEFAULT_CLUSTER_NAME)</code>.
	 */
	public ClusteredBrokerHandler() {
		this(DEFAULT_CLUSTER_NAME);
	}
	
	/**
	 * Constructor for a port. This is identical to <code>this(DEFAULT_CLUSTER_NAME, port)</code>.
	 * @param port The broker port.
	 */
	public ClusteredBrokerHandler(int port) {
		this(DEFAULT_CLUSTER_NAME, port);
	}
	
	/**
	 * Constructor for a cluster name.. This is identical to <code>this(clusterName, DEFAULT_PORT)</code>.
	 * @param clusterName The cluster name.
	 */
	public ClusteredBrokerHandler(String clusterName) {
		this(clusterName, DEFAULT_PORT);
	}
	
	/**
	 * Constructor for a cluster name and port. This implementation uses an auto-generated 
	 * data directory name.
	 * @param clusterName The cluster name.
	 * @param port The broker port.
	 */
	public ClusteredBrokerHandler(String clusterName, int port) {
		this(clusterName, port, null);
		this.dataDir = DEFAULT_DATA_DIR_PREFIX + port + "-" + this.hashCode();
	}
	
	/**
	 * Constructor for non-default attribute values.
	 * @param clusterName The cluster name.
	 * @param port The broker port.
	 * @param dataDir The data directory.
	 */
	public ClusteredBrokerHandler(String clusterName, int port, String dataDir) {
		super(port);
		this.clusterName = clusterName;
		this.dataDir = dataDir;
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
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return super.getCommand() + " --cluster-name=" + clusterName + " --data-dir=" + dataDir;
	}
	
	/**
	 * Additional command processing to extract the QPID cluster member ID.
	 * @param p The command process.
	 */
	/*
	@Override
	protected void handleProcess(Process p) {
		try {
			InputStream is = p.getInputStream();
			StringBuffer buffer = new StringBuffer();
			boolean done = false;
			int i = is.read();
			while (!done) {
				buffer.append(i);
				if (isCandidateString(buffer.toString())) {
					handleProcess(is);
					done = true;
				}
				
				if (!done) {
				    i = is.read();
				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}*/
	
	/**
	 * Returns if the current command input stream is about to identify the 
	 * QPID cluster ID string.
	 * @param s The current command stream output.
	 * @return True if the output is about to identify the 
	 * QPID cluster ID string.
	 */
	/*private boolean isCandidateString(String s) {
		return ((s != null) &&
				(s.indexOf("Members joined") != -1));
	}*/
	
	/**
	 * Additional processing of the command input stream to extract
	 * the QPID cluster ID.
	 * @param is The command input stream.
	 */
	/*private void handleProcess(InputStream is) {
		try {
			StringBuffer buffer = new StringBuffer();
			int i = is.read();
			while (!((char)i == '\n')) {
				buffer.append(i);
				i = is.read();
			}
			
			memberId = buffer.toString().trim();
			
		} catch (IOException io) {
			io.printStackTrace();
		}
		
	}*/

	/**
	 * Post-processing to shut down the broker.
	 */
	/*
	@Override
	protected void doFinalize() {
		// Add something here
	}*/
}
