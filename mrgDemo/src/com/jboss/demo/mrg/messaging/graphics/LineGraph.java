package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import com.jboss.demo.mrg.messaging.handler.CommandHandler;

/**
 * Line graph panel.
 * Mike Darretta
 */
public class LineGraph extends JPanel { 

	/** Serial version UID */
	private static final long serialVersionUID = -1978038232845014444L;

	/** The points for the line graph */
	protected Collection<GraphPoints> points;
	
	/** The optional command handler that this graph is bound to */
	protected CommandHandler handler;

	/** Padding from the panel edge for rendering chart */
	protected final int PAD = 40;
    
	/**
	 * Default constructor. This version creates an empty set of graph points
	 * and a null command handler.
	 */
    public LineGraph() {
    	this(null, null);
    }
    
    /**
     * Constructor for a command handler. This version creates an empty points object.
     * @param handler The command handler.
     */
    public LineGraph(CommandHandler handler) {
    	this(null, handler);
    }

    /**
     * Constructor for a set of graph points. This is identical to <code>this(graphPoints, null)</code>.
     * @param graphPoints The graph points.
     */
    public LineGraph(GraphPoints graphPoints) {
    	this(graphPoints, null);
    }
    
    /**
     * Constructor.
     * @param graphPoints The set of graph points.
     * @param handler The command handler.
     */
    public LineGraph(GraphPoints graphPoints, CommandHandler handler) {
    	this.handler = handler;
    	Collection<GraphPoints> points = new ArrayList<GraphPoints> ();
    	if (graphPoints != null) {
    	    points.add(graphPoints);
    	}
        this.points = points;
    }
    
    /**
     * Constructor for a collection of graph points (for a multi-line chart).
     * @param points The collection of graph points.
     */
    public LineGraph(Collection<GraphPoints> points) {
    	this.points = points;
    }
    
    /**
     * Returns the collection of graph points.
     * @return The graph points.
     */
    public Collection<GraphPoints> getPoints() {
    	return points;
    }
    
    /**
     * Sets the graph points.
     * @param points The graph points.
     */
    public void setPoints(Collection<GraphPoints> points) {
    	this.points = points;
    }

    /**
     * Adds a new set of points to the points collection.
     * @param points The points to add to the collection.
     */
    public void addPoints(GraphPoints points) {
        this.points.add(points);
    }

    /**
     * Paints the component.
     * @param g The graphics object.
     */
    protected void paintComponent(Graphics g) { 
	
        super.paintComponent(g); 
         
        Graphics2D g2 = (Graphics2D) g; 

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON); 

        int height = getHeight(); 
        int width = getWidth(); 

        GraphUtils.renderYAxis(g2, height, PAD, "Execution Time");
        GraphUtils.renderXAxis(g2, height, width, PAD, "Samples");

        AxisCoordinates xAxisCoordinates = new XAxisCoordinates(0, points.size(), 0.0, 10);
        AxisCoordinates yAxisCoordinates = new YAxisCoordinates(getMaxDataValue());
                
        GraphUtils.renderXCoordinates(g2, xAxisCoordinates,
        		new Coordinate(PAD, this.getHeight()-PAD), new Coordinate(width-PAD, height-PAD));
        GraphUtils.renderYCoordinates(g2, yAxisCoordinates, 
        		new Coordinate(PAD, PAD), new Coordinate(PAD, height-PAD));
        
        Iterator<GraphPoints> i = points.iterator();
        int x=0;
        while (i.hasNext()) {
        	Integer[] data = i.next().getData();
        	double scale = (double) (height - PAD) / yAxisCoordinates.getMaxCoordinate();
        	GraphUtils.renderLines(g2, height, width, PAD, scale, data, GraphUtils.colors[x++], false);
        }
        
        GraphUtils.renderLegend(g2, getWidth()-PAD-60, getHeight()-PAD-30, points);
    }
    
    /**
     * Destroys any existing owning command via the optional command handler.
     */
    public void exit() {
    	if (handler != null) {
    		handler.destroyProcess();
    	}
    }
    
    /**
     * Returns the maximum data value within the collection of graph points.
     * @return The maximum data value.
     */
    private int getMaxDataValue() { 
    	int maxValue = 0;
        Iterator<GraphPoints> i = points.iterator();
        while (i.hasNext()) {
        	int dataMaxValue = getMaxDataValue(i.next().getData());
        	if (maxValue < dataMaxValue) {
        		maxValue = dataMaxValue;
        	}
        }
        
        return maxValue;
    }
    
    /**
     * Returns the maximum data value for a particular set of graph data points.
     * @param data The graph data points.
     * @return The maximum data value.
     */
    private int getMaxDataValue(Integer[] data) { 
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < data.length; i++) { 
        	int value = data[i].intValue();
            if (max < value) { 
                max = value;
            } 
        } 
        return max; 
    }
}
