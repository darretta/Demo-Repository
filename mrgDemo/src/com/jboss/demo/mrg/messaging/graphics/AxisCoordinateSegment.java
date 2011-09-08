package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Encapsulates an line axis coordinate segment.
 * @author Mike Darretta
 */
public class AxisCoordinateSegment implements Renderable {
	
	/** The starting coordinate */
	protected Coordinate startingCoordinate;
	
	/** The ending coordinate */
	protected Coordinate endingCoordinate;
	
	/** The (optional) coordinate label; may be <code>null</code>. */
	protected String label;
	
	/** The segment color */
	protected Color color;

	/**
	 * Constructor.
	 * @param startingCoordinate The starting coordinate.
	 * @param endingCoordinate The ending coordinate.
	 * @param label The (optional) coordinate label; may be <code>null</code>.
	 * @param color The segment color.
	 */
	public AxisCoordinateSegment(Coordinate startingCoordinate, Coordinate endingCoordinate, 
			String label, Color color) {
		this.startingCoordinate = startingCoordinate;
		this.endingCoordinate = endingCoordinate;
		this.label = label;
		this.color = color;
	}
	
	/**
	 * Renders this segment.
	 * @param g2 The graphics object.
	 */
	@Override
	public void render(Graphics2D g2) {
		g2.draw(new Line2D.Double(
				startingCoordinate.getXPos(), startingCoordinate.getYPos(),
				endingCoordinate.getXPos(), endingCoordinate.getYPos()));
		
		if (label != null) {
			Font font = g2.getFont();
			g2.setFont(font.deriveFont(10.0F));
			int width = g2.getFontMetrics().stringWidth(label);
		    g2.drawString(label, (int) (startingCoordinate.getXPos()-width), (int) endingCoordinate.getYPos());
		    g2.setFont(font);
		}
	}
}
