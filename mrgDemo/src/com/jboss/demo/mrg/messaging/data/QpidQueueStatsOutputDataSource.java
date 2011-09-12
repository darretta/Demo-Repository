package com.jboss.demo.mrg.messaging.data;

import java.io.InputStream;
import java.util.StringTokenizer;

import com.jboss.demo.mrg.messaging.data.DataSourceConsumer;

/**
 * Data source for the qpid-queue-stats command.
 * @author Mike Darretta
 */
public class QpidQueueStatsOutputDataSource extends OutputDataSource {
	
	/**
	 * Constructor.
	 * @param inputStream The input stream.
	 */
	public QpidQueueStatsOutputDataSource(InputStream inputStream) {
		super(inputStream);
	}
	
	/**
	 * Extension of the <code>manage</code> method to add processing
	 * instructions for this class.
	 * @param consumer The consumer.
	 * @param line The line to process.
	 */
	@Override
	public void processLine(DataSourceConsumer consumer, String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		String token = null;
		
		if (tokenizer.countTokens() > 3) {
			for (int x = 0; x < 4; x++) {
				token = tokenizer.nextToken();
			}

			consumer.update(Integer.valueOf(token));
		}
	}
}
