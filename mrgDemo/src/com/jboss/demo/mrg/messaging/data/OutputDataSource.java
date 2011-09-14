package com.jboss.demo.mrg.messaging.data;

import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract encapsulation of an output data source.
 * @author Mike Darretta
 */
public abstract class OutputDataSource implements DataSource {
	
	/** Data source input stream */
	protected InputStream inputStream;
	
	/** New line indicator */
	public static final char NEWLINE = '\n';

	/**
	 * Constructor.
	 * @param inputStream The input stream.
	 */
	public OutputDataSource(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Returns the input stream.
	 * @return The input stream.
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * Manages the data for the input consumer.
	 * @param consumer The input consumer.
	 */
	@Override
	public void manage(DataSourceConsumer consumer) {	
		try {
			String line = readLine();
			while (true) {
				processLine(consumer, line);
				line = readLine();
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * Abstract extension of the <code>manage</code> method to add processing
	 * instructions for concrete implementations.
	 * @param consumer The consumer.
	 * @param line The line to process.
	 */
	protected abstract void processLine(DataSourceConsumer consumer, String line);
		
	/**
	 * Reads a line from the output data source.
	 * @return The read line, up to a newline indication.
	 * @throws IOException Error reading line.
	 */
	protected String readLine() throws IOException {
		StringBuffer buffer = new StringBuffer();
		char ch = (char) inputStream.read();
		while (ch != NEWLINE) {
			buffer.append(ch);
			ch = (char) inputStream.read();
		}
		
		return buffer.toString();
	}
}
