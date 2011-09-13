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
        	Properties.getProperties().getStringProperty(
        		Properties.DEMO_NAME_STR));
        frame.setVisible(true);
    }
}
