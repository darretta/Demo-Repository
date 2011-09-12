package com.jboss.demo.mrg.messaging.data;

import java.io.InputStream;
import java.util.StringTokenizer;

import com.jboss.demo.mrg.messaging.data.DataSourceConsumer;

public class QpidQueueStatsOutputDataSource extends OutputDataSource {
	
	public QpidQueueStatsOutputDataSource(InputStream inputStream) {
		super(inputStream);
	}
	
	@Override
	public void processLine(DataSourceConsumer consumer, String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		String token = null;
		for (int x=0; x < 4; x++) {
			token = tokenizer.nextToken();
		}
		
		consumer.update(Integer.valueOf(token));
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
