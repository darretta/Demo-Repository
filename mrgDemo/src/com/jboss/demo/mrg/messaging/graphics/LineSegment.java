package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;

/**
 * Encapsulates a segment of a line graph.
 * @author Mike Darretta
 *
 */
public class LineSegment implements Renderable {
	
	/** The start coordinate */
	protected Coordinate startingCoordinate;
	
	/** The ending coordinate */
	protected Coordinate endingCoordinate;
	
	/** The segment graph point value */
	protected int value;
	
	/** The segment line color */
	protected Paint color;
	
	/** True if the graph point value should be rendered */
	protected boolean doLabel;
	
	/**
	 * Constructor.
	 * @param startingCoordinate The starting coordinate.
	 * @param endingCoordinate The ending coordinate.
	 * @param color The segment line color.
	 * @param doLabel True if the graph point value should be rendered.
	 */
	protected LineSegment(Coordinate startingCoordinate, Coordinate endingCoordinate, 
			int value, Paint color, boolean doLabel) {
		this.startingCoordinate = startingCoordinate;
		this.endingCoordinate = endingCoordinate;
		this.value = value;
		this.color = color;
		this.doLabel = doLabel;
	}
	
	/**
	 * Renders this line segment.
	 * @param g2 The graphics object.
	 */
	@Override
	public void render(Graphics2D g2) {			
		g2.setPaint(color);
		g2.draw(new Line2D.Double(startingCoordinate.getXPos(), startingCoordinate.getYPos(),
				endingCoordinate.getXPos(), endingCoordinate.getYPos()));
		// Render labels
		if (doLabel) {
			g2.setPaint(Color.BLACK);
			g2.drawString("" + value, (int) startingCoordinate.getXPos(), (int) startingCoordinate.getYPos());
		}
	}
}
