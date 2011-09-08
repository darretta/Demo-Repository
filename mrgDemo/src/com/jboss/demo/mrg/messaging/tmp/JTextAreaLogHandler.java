package com.jboss.demo.mrg.messaging.tmp;

import java.awt.Rectangle;
import javax.swing.JTextArea;


/**
 * Log handler for a <code>JTextArea</code>.
 */
public class JTextAreaLogHandler extends AbstractLogHandler {

    private JTextArea textArea;
    private boolean isAutoScroll;
    private boolean isAppend;

    /**
     * Constructor. Equivalent to <code>JTextAreaLogHandler(textArea, true, true)</code>.
     * @param textArea The text area to log to.
     */
    public JTextAreaLogHandler(JTextArea textArea) {
        this(textArea, true, true);
    }

    /**
     * Constructor.
     * @param textArea The text area to log to.
     * @param isAutoScroll True if the text area should auto scroll during logging.
     * @param isAppend True if the log entry should be appended to the current log entries.
     */
    public JTextAreaLogHandler(JTextArea textArea, boolean isAutoScroll, boolean isAppend) {
        this.textArea = textArea;
        this.isAutoScroll = isAutoScroll;
        this.isAppend = isAppend;
    }

    /**
     * Logs the message.
     * @param s The string message to log.
     * @throws LoggingException Error logging message.
     */
    public void log(String s) throws LoggingException {
 
        try {
            if (isAppend) {
                textArea.append(s);
            } else {
                textArea.setText(s);
            }
    
            if (isAutoScroll) {
                textArea.scrollRectToVisible(
                    new Rectangle(0,textArea.getHeight()-1,1,1));
            }
        } catch (Exception e) {
            throw new LoggingException(e);
        }
    }
}
