package com.jboss.demo.mrg.messaging.graphics;

/**
 * Encapsulates a graph coordinate.
 * @author Mike Darretta
 */
public class Coordinate {
	
	/** The X position */
	private double xPos;
	
	/** The Y position */
	private double yPos;
	
	/**
	 * Constructor.
	 * @param xPos The X position.
	 * @param yPos The Y position.
	 */
	public Coordinate(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Returns the X position.
	 * @return The X position.
	 */
	public double getXPos() {
		return xPos;
	}

	/**
	 * Sets the X position.
	 * @param xPos The X position.
	 */
	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	/**
	 * Returns the Y position.
	 * @return The Y position.
	 */
	public double getYPos() {
		return yPos;
	}

	/**
	 * Sets the Y position.
	 * @param yPos The Y position.
	 */
	public void setYPos(double yPos) {
		this.yPos = yPos;
	}
}
