package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Encapsulates a line axis.
 * @author Mike Darretta
 */
public class Axis implements Renderable {
	
	/** The starting coordinate */
	protected Coordinate startingCoordinate;
	
	/** The ending coordinate */
	protected Coordinate endingCoordinate;
	
	/**
	 * Constructor.
	 * @param startingCoordinate The starting coordinate.
	 * @param endingCoordinate The ending coordinate.
	 */
	public Axis(Coordinate startingCoordinate, Coordinate endingCoordinate) {
		this.startingCoordinate = startingCoordinate;
		this.endingCoordinate = endingCoordinate;
	}
	
	/**
	 * Renders this axis.
	 * @param g2 The graphics object.
	 */
	@Override
	public void render(Graphics2D g2) {
        g2.draw(new Line2D.Double(
        		startingCoordinate.getXPos(), 
        		startingCoordinate.getYPos(),
        		endingCoordinate.getXPos(),
        		endingCoordinate.getYPos()));
	}
}
