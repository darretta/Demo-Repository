package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Graphics2D;

/**
 * Interface for a renderable graphics object.
 * @author Mike Darretta
 */
public interface Renderable {

	/**
	 * Renders this object.
	 * @param g2 The graphics object.
	 */
	public void render(Graphics2D g2);
}
