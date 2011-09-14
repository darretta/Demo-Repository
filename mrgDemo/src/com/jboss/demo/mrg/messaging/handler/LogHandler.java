package com.jboss.demo.mrg.messaging.handler;



/**
 * Log handler interface.
 */
public interface LogHandler {

    /**
     * Logs the input string.
     * @param s The input string.
     * @throws LoggingException Error logging message.
     */
    public void log(String s) throws LoggingException;

    /**
     * Logs the input string with a postpended newline.
     * @param s The input string.
     * @throws LoggingException Error logging message.
     */
    public void logWithNewline(String s) throws LoggingException;

    /**
     * Logs the stack trace for the input <code>Throwable</code>.
     * @param t The <code>Throwable</code>.
     */
    public void log(Throwable t);
}
