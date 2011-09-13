package com.jboss.demo.mrg.messaging;

/**
 * Main driver.
 */
public class Main {

    /**
     * Main method.
     * @param argv No expected arguments.
     */
    public static final void main(String[] argv) {
        MainFrame frame = new MainFrame(
        	(String) Properties.getProperties().getProperty(
        		Properties.DEMO_NAME_STR));
        frame.setVisible(true);
    }
}
