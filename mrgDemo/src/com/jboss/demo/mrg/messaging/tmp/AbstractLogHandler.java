package com.jboss.demo.mrg.messaging.tmp;


/**
 * Abstract log handler implements some of the more 
 * recurring interface method implementations.
 */
public abstract class AbstractLogHandler implements LogHandler {

    /**
     * Abstract interface implementation. Child classes
     * should implement the concrete logging process.
     * @param s The string message to log.
     * @throws LoggingException Error logging message.
     */
    public abstract void log(String s) throws LoggingException;

    /**
     * Logs the input string with a postpended newline.
     * @param s The input string.
     * @throws LoggingException Error logging message.
     */
    public void logWithNewline(String s) throws LoggingException {
        log(s + "\n");
    }

    /**
     * Logs the stack trace for the input <code>Throwable</code>.
     * @param t The <code>Throwable</code>.
     */
    public void log(Throwable t) {
        try {
            Throwable cause = t.getCause();
            if (cause != null) {
                log(cause);
            }

            final StackTraceElement[] stackTrace = t.getStackTrace();
            logWithNewline(t.getMessage());
            for (int i=0; i < stackTrace.length; i++) {
                logWithNewline("       at " + stackTrace[i].toString());
            }
        } catch (LoggingException le) {
            le.printStackTrace();
        }
    }
}
