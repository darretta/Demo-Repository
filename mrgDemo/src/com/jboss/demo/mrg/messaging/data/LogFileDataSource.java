package com.jboss.demo.mrg.messaging.data;

import java.io.IOException;
import java.io.Reader;

/**
 * Log file data source.
 * @author Mike Darretta
 */
public class LogFileDataSource implements DataSource {
	
	/** Data source reader */
	private Reader reader;
	
	/** Broker ID */
	private int brokerId;
	
	/** New line indicator */
	public static final char NEWLINE = '\n';

	/**
	 * Constructor for a reader.
	 * @param reader The reader.
	 * @param brokerId The source broker ID.
	 */
	public LogFileDataSource(Reader reader, int brokerId) {
		this.reader = reader;
		this.brokerId = brokerId;
	}
	
	/**
	 * Returns the source broker ID.
	 * @return The source broker ID.
	 */
	public int getBrokerId() {
		return brokerId;
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
			    Integer i = Integer.valueOf(line);
			    consumer.update(i);
			}
			line = readLine();
		}
	}
	
	public void stop() {}
	
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
	 * Reads a line from the log file.
	 * @return The read line, up to a newline indication.
	 */
	protected String readLine() {
		StringBuffer buffer = new StringBuffer();
		try {
		    char ch = (char) reader.read();
			while (ch != NEWLINE && isValid(ch)) {
				buffer.append(ch);
				ch = (char) reader.read();
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
