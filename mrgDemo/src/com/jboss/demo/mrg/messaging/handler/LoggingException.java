package com.jboss.demo.mrg.messaging.handler;

/**
 * Logging exception.
 * @author Mike Darretta
 */
public class LoggingException extends Exception {
	
	/** The serial version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a string.
	 * @param s The string message.
	 */
    public LoggingException(String s) {
        super(s);
    }

    /**
     * Constructor for an embedded <code>Throwable</code>.
     * @param t The <code>Throwable</code>.
     */
    public LoggingException(Throwable t) {
        super(t);
    }
}
