package com.jboss.demo.mrg.messaging;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jboss.demo.mrg.messaging.graphics.LineGraph;

/**
 * Frame to render a line graph.
 * @author Mike Darretta
 */
public class LineGraphFrame extends JFrame implements Runnable {
	
	/** The serial version UID */
	private static final long serialVersionUID = 7899922006633528918L;
	
	/** Parent component */
	private Component parent;
	
	/** The line graph */
	private LineGraph graph;

	/**
	 * Constructor.
	 * @param graph The line graph.
	 * @param name The frame name.
	 * @throws HeadlessException
	 */
	public LineGraphFrame(LineGraph graph, String name) throws HeadlessException {
		this(null, graph, name);
	}
	
	/**
	 * Constructor with a parent component.
	 * @param parent The parent component to ensure proper positioning.
	 * @param graph The line graph.
	 * @param name The frame name.
	 * @throws HeadlessException
	 */
	public LineGraphFrame(Component parent, LineGraph graph, String name) throws HeadlessException {
		super();
		this.parent = parent;
		this.graph = graph;
		this.setTitle(name);
	}

	/**
	 * Starts this frame as an optional new thread.
	 */
	public void run() {
		render();
	}
		
	/**
	 * Renders the frame.
	 */
    public void render() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   
        this.setSize(400,400);
        
        if (parent != null) {
            Point location = parent.getLocation();
            double locOff = 100.0;
            location.setLocation(location.getX()+locOff, location.getY());
            this.setLocation(location);
        } else {
        	this.setLocationRelativeTo(null);
        }
        
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		graph.exit();
        	}
        });
        
        this.getContentPane().add(this.graph);
        this.setVisible(true);

        while (true) {
    		this.repaint();
    		
    		try {
    			Thread.sleep(2000);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
	}
}
