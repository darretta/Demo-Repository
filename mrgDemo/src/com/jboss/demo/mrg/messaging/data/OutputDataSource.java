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
		String line = readLine();
		while (true) {
			if (isValid(line)) {
			    processLine(consumer, line);
			}
			line = readLine();
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
	 * Determines if the input line is a valid entry.
	 * @param line The line to validate.
	 * @return True if the line is valid.
	 */
	protected boolean isValid(String line) {
		boolean isValid = true;
		try {
			Integer.parseInt(line);
		} catch (Exception e) {
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Determines if the input character is a valid character.
	 * @param ch The input character.
	 * @return True if the input character is valid.
	 */
	protected boolean isValid(char ch) {
		int i = (int) ch;
		return (i > 47 && i < 58);
	}
	
	/**
	 * Reads a line from the output data source.
	 * @return The read line, up to a newline indication.
	 */
	protected String readLine() {
		StringBuffer buffer = new StringBuffer();
		try {
		    char ch = (char) inputStream.read();
			while (ch != NEWLINE && isValid(ch)) {
				buffer.append(ch);
				ch = (char) inputStream.read();
				try { 
					Thread.sleep(1000);
				} catch (Exception e) {}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return buffer.toString();
	}
}
