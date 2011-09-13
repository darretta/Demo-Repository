package com.jboss.demo.mrg.messaging;

import java.io.FileReader;
import java.io.Reader;

/**
 * Demo properties file.
 * @author Mike Darretta
 */
public class Properties {
	
	/** The property key prefix */
	private static final String PROP_NAME_PREFIX = "com.jboss.demo.mrg.messaging.";
	
	/** Demo name key */
	public static final String DEMO_NAME_STR = PROP_NAME_PREFIX + "DemoName";
	
	/** Default demo name */
	private static final String DEMO_NAME = "MRG Messaging 2.0 Demo";

	/** Hostname key */
	public static final String DEFAULT_HOSTNAME_STR = PROP_NAME_PREFIX + "Hostname";
	
	/** Default hostname */
	private static final String DEFAULT_HOSTNAME = "localhost";
	
	/** Show CPP client button key */
	public static final String DEFAULT_SHOW_CPP_CLIENT_BTN_STR = PROP_NAME_PREFIX + "ShowCPPClientBtn";
	
	/** Default show CPP client button */
	private static final boolean DEFAULT_SHOW_CPP_CLIENT_BTN = true;
	
	/** Show JMS client button key */
	public static final String DEFAULT_SHOW_JMS_CLIENT_BTN_STR = PROP_NAME_PREFIX + "ShowJMSClientBtn";
	
	/** Default show JMS client button */
	private static final boolean DEFAULT_SHOW_JMS_CLIENT_BTN = true;
	
	/** Show Python client button key*/
	public static final String DEFAULT_SHOW_PYTHON_CLIENT_BTN_STR = PROP_NAME_PREFIX + "ShowPythonClientBtn";
		
	/** Default show Python client button */
	private static final boolean DEFAULT_SHOW_PYTHON_CLIENT_BTN = true;
	
	/** Number of brokers key */
	public static final String DEFAULT_NUM_BROKERS_STR = PROP_NAME_PREFIX + "DefaultNumBrokers";
	
	/** Default number of brokers */
	private static final int DEFAULT_NUM_BROKERS = 1;
	
	/** Number of threads per client key */
	public static final String DEFAULT_NUM_THREADS_PER_CLIENT_STR = PROP_NAME_PREFIX + "DefaultNumThreadsPerClient";
	
	/** Default number of threads per client */
	private static final int DEFAULT_NUM_THREADS_PER_CLIENT = 1;
	
	/** Number of messages per client key */
	public static final String DEFAULT_NUM_MSGS_PER_CLIENT_STR = PROP_NAME_PREFIX + "DefaultNumMsgsPerClient";
	
	/** Default number of messages per client */
	private static final int DEFAULT_NUM_MSGS_PER_CLIENT = 500000;
	
	/** Broker port key */
	public static final String DEFAULT_BROKER_PORT_STR = PROP_NAME_PREFIX + "DefaultBrokerPort";
	
	/** Default broker port */
	private static final int DEFAULT_BROKER_PORT = 5672;
	
	/** QPID perf-test command key */
	public static final String QPID_PERF_TEST_CMD_STR = PROP_NAME_PREFIX + "QpidPerfTestCmd";
	
	/** Default QPID perf-test command */
	private static final String QPID_PERF_TEST_CMD = "qpid-perftest";
	
	/** QPID queue stats command key */
	public static final String QPID_QUEUE_STATS_CMD_STR = PROP_NAME_PREFIX + "QpidQueueStatsCmd";
	
	/** Default QPID queue stats command */
	private static final String QPID_QUEUE_STATS_CMD = "qpid-queue-stats";
	
	/** QPID command key */
	public static final String QPID_CMD_STR = PROP_NAME_PREFIX + "QpidCmd";
	
	/** Default QPID command */
	private static final String QPID_CMD = "qpidd";
	
	/** The underlying properties */
	private static java.util.Properties properties = new java.util.Properties();
	
	/** Singleton instance */
	private static Properties instance = new Properties();
	
	/**
	 * Default constructor privatized to prevent instantiation.
	 */
	private Properties() {
		init();
	}
	
	/**
	 * Initializes the properties. First attempt is to initialize via the
	 * properties file. If that fails, the default properties are used.
	 */
	protected static void init() {
		try {
			Reader reader = new FileReader("mrg-demo.properties");
			properties.load(reader);
		} catch (Exception e) {
			System.out.println(
					"Could not read properties file....Using default property settings instead.");
			loadStaticProperties();
		}
	}
	
	/**
	 * Initializes the properties per the default properties. This is intended for
	 * use strictly if the properties file is inaccessible.
	 */
	protected static void loadStaticProperties() {

		properties.put(DEMO_NAME_STR, DEMO_NAME);
		properties.put(DEFAULT_HOSTNAME_STR, DEFAULT_HOSTNAME);
		properties.put(DEFAULT_SHOW_CPP_CLIENT_BTN_STR, DEFAULT_SHOW_CPP_CLIENT_BTN);
		properties.put(DEFAULT_SHOW_JMS_CLIENT_BTN_STR, DEFAULT_SHOW_JMS_CLIENT_BTN);
		properties.put(DEFAULT_SHOW_PYTHON_CLIENT_BTN_STR, DEFAULT_SHOW_PYTHON_CLIENT_BTN);
		properties.put(DEFAULT_NUM_BROKERS_STR, DEFAULT_NUM_BROKERS);
		properties.put(DEFAULT_NUM_THREADS_PER_CLIENT_STR, DEFAULT_NUM_THREADS_PER_CLIENT);
		properties.put(DEFAULT_NUM_MSGS_PER_CLIENT_STR, DEFAULT_NUM_MSGS_PER_CLIENT);
		properties.put(DEFAULT_BROKER_PORT_STR, DEFAULT_BROKER_PORT);
		properties.put(QPID_PERF_TEST_CMD_STR, QPID_PERF_TEST_CMD);
		properties.put(QPID_QUEUE_STATS_CMD_STR, QPID_QUEUE_STATS_CMD);
		properties.put(QPID_CMD_STR, QPID_CMD);
	}
	
	/**
	 * Returns the singleton instance.
	 * @return The singleton instance.
	 */
	public static Properties getProperties() {
		return instance;
	}
	
	/**
	 * Returns the property value for the particular key.
	 * @param propertyName The property key.
	 * @return The property value.
	 */
	public Object getProperty(String propertyName) {
		return properties.get(propertyName);
	}
}