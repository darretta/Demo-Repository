package com.jboss.demo.mrg.messaging.data;

/**
 * Data source encapsulation.
 * @author Mike Darretta
 */
public interface DataSource {
    
	/**
	 * Binds data source object to the input consumer.
	 * @param consumer The consumer bound to this data source object.
	 */
	public void manage(DataSourceConsumer consumer);
	
	public void stop();
}
