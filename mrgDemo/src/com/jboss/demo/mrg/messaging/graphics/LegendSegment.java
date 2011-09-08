package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * Encapsulate a graph legend segment.
 * @author Mike Darretta
 */
public class LegendSegment implements Renderable {
	
	/** The segment coordinates */
	protected Coordinate coordinate;
	
	/** The segment label */
	protected String label;
	
	/** The segment color */
	protected Color color;
	
	/**
	 * Constructor.
	 * @param coordinate The segment coordinates.
	 * @param label The segment label.
	 * @param color The segment color.
	 */
	public LegendSegment(Coordinate coordinate, String label, Color color) {
		this.coordinate = coordinate;
		this.label = label;
		this.color = color;
	}

	/**
	 * Renders this segment.
	 * @param g2 The graphics object.
	 */
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);
	    g2.fill(new Rectangle2D.Double(coordinate.getXPos(), coordinate.getYPos(), 15, 10));
	    g2.drawString(label, (int) coordinate.getXPos()+20, (int) coordinate.getYPos()+10);
	}
}