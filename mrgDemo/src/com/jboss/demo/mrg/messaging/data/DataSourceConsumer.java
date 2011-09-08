package com.jboss.demo.mrg.messaging.data;

/**
 * Consumer of a data source.
 * @author Mike Darretta
 */
public interface DataSourceConsumer {

	/**
	 * Updates the data for this consumer.
	 * @param data The data to update.
	 */
	public void update(Object data);
}
