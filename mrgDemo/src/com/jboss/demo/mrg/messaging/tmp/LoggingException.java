package com.jboss.demo.mrg.messaging.tmp;

/**
 * Logging exception.
 */
public class LoggingException extends Exception {
	
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
