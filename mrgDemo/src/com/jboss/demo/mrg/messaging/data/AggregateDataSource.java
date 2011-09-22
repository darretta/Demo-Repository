package com.jboss.demo.mrg.messaging.data;

/**
 * Special data source for collecting aggregate data from multiple data sources.
 * This data source allows for multiple data source threads to update an 
 * aggregate data attribute which in returned to the data consumer.
 * @author Mike Darretta
 *
 */
public class AggregateDataSource implements DataSource {
	
	/** The aggregate data for all reporting data sources */
	protected int aggregateData;
	
	/** The number of expected report data sources */
	protected int numReportingDataSources;
	
	/** The current number of reporting data sources */
	protected int currNumReportingDataSources;
	
	/**
	 * Constructor.
	 * @param numReportingDataSources The number of expected reporting
	 * data sources.
	 */
	public AggregateDataSource(int numReportingDataSources) {
		this.numReportingDataSources = numReportingDataSources;
	}

	/**
	 * Updates the consumer with the aggregate data. When all expected data sources
	 * report, the value is released to the consumer and reset to 0.
	 * @todo This method does not guarantee that that each expected data source reports before
	 * data is delivered to the consumer. Since it employs a simple counter, it is possible that
	 * a single thread could account for more than one data source report.
	 * @param consumer The data consumer.
	 */
	@Override
	public void manage(DataSourceConsumer consumer) {
		
		while(true) {
			if (currNumReportingDataSources == numReportingDataSources) {
			    consumer.update(aggregateData);
			    reset();
			} else if (currNumReportingDataSources > numReportingDataSources) {
				reset();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				// Do nothing.
			}
		}
	}
	
	/**
	 * Updates the aggregate data value. This method is synchronized to
	 * ensure thread updates are properly handled.
	 * @param data The data value.
	 */
	protected synchronized void updateData(int data) {
		this.aggregateData += data;
		this.currNumReportingDataSources++;
	}
	
	/**
	 * Resets the reporting attributes.
	 */
	protected void reset() {
		this.aggregateData = 0;
		this.currNumReportingDataSources = 0;
	}
	
	/**
	 * Returns the number of expected reporting data sources.
	 * @return The number of expected reporting data sources.
	 */
	public int getNumReportingDataSource() {
		return this.numReportingDataSources;
	}
}
