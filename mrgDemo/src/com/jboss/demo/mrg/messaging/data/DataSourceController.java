package com.jboss.demo.mrg.messaging.data;

/**
 * Controller for the consumption of a data source by a data consumer.
 * @author Mike Darretta
 *
 */
public class DataSourceController extends Thread {
	
	/** The data source */
	private DataSource source;
	
	/** The data consumer */
	private DataSourceConsumer consumer;
	
	/**
	 * Constructor for a data source and consumer.
	 * @param source The data source.
	 * @param consumer The data consumer.
	 */
	public DataSourceController(DataSource source, DataSourceConsumer consumer) {
		this.source = source;
		this.consumer = consumer;
	}

	/**
	 * Binds the consumption of the data from the data source.
	 */
	public void bind() {
		source.manage(consumer);
	}
	
	/**
	 * Executes this controller as an optional thread.
	 */
	@Override
	public void run() {
		bind();
	}
	
	/**
	 * Returns the data source.
	 * @return The data source.
	 */
	public DataSource getSource() {
		return source;
	}

	/**
	 * Returns the data consumer.
	 * @return The data consumer.
	 */
	public DataSourceConsumer getConsumer() {
		return consumer;
	}
}
