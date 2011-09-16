package com.jboss.demo.mrg.messaging.data;

/**
 * Special data source for collecting aggregate data from multiple data sources.
 * This data source allows for multiple data source threads to update an 
 * aggregate data attribute which in returned to the data consumer.
 * @author Mike Darretta
 *
 */
public class AggregateDataSource implements DataSource {
	
	/** The aggregate of all reporting data sources */
	protected int aggregateData;

	/**
	 * Updates the consumer with the aggregate data, then
	 * resets the data value to zero.
	 */
	@Override
	public void manage(DataSourceConsumer consumer) {
		while(true) {
			consumer.update(aggregateData);
			updateData(0);
		}
	}
	
	/**
	 * Updates the aggregate data value. This method is synchronized to
	 * ensure thread updates are properly handled.
	 * @param data The data value.
	 */
	protected synchronized void updateData(int data) {
		this.aggregateData += data;
	}
}
