package com.jboss.demo.mrg.messaging.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

/**
 * Encapsulates an axis label.
 * @author Mike Darretta
 */
public class AxisLabel implements Renderable {
	
	/** Label orientation enumerated type. */
	public enum Orientation {
		
		/** Horizontal orientation */
		HORIZONTAL, 
		
		/** Vertical orientation */
		VERTICAL;
		
		/**
		 * Returns if orientation is horizontal.
		 * @return True if horizontal.
		 */
		public boolean isHorizontal() {
			return this == HORIZONTAL;
		}
		
		/**
		 * Returns if orientation is vertical.
		 * @return True if vertical.
		 */
		public boolean isVertical() {
			return this == VERTICAL;
		}
	}
	
	/** The label to render */
	private String label;
	
	/** The label orientation */
	private Orientation orientation;
	
	/** The starting X position */
	private double xPos;
	
	/** The starting Y position */
	private double yPos;
	
	/**
	 * Constructor.
	 * @param label The label to render.
	 * @param orientation The label orientation.
	 * @param xPos The starting X position.
	 * @param yPos The starting Y position.
	 */
	public AxisLabel(String label, Orientation orientation, double xPos, double yPos) {
		this.label = label;
		this.orientation = orientation;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Renders the label.
	 * @param g2 The graphics object.
	 */
	@Override
	public void render(Graphics2D g2) {
		if (orientation.isHorizontal()) {
			g2.drawString(label, (int) xPos, (int) yPos);
		} else {
	        Font font = g2.getFont();
	        FontRenderContext frc = g2.getFontRenderContext(); 
	        LineMetrics lm = font.getLineMetrics("0", frc); 
	        float h = lm.getAscent() + lm.getDescent();
	        
			for (int i = 0; i < label.length(); i++) { 
				String letter = String.valueOf(label.charAt(i)); 
				g2.drawString(letter, (int) xPos, (int) yPos);
                yPos += h;				
			}
		}
	}
	
	/**
	 * Static utility method to calculate a default starting X position for a centered axis label.
	 * @param g2 The Graphics object.
	 * @param label The axis label.
	 * @param orientation The orientation.
	 * @param chartWidth The chart width.
	 * @param chartPadding The chart padding.
	 * @return The starting X position.
	 */
	public static double calculateStartingXPos(Graphics2D g2, String label, Orientation orientation, int chartWidth, int chartPadding) {
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext(); 
        double x = 0;

        if (orientation.isHorizontal()) {
	        double w = font.getStringBounds(label, frc).getWidth(); 
	        x = (chartWidth - w) / 2; 
		} else {
            String letter = String.valueOf(label.charAt(0)); 
            double w = font.getStringBounds(letter, frc).getWidth(); 
            x = (chartPadding - w) / 4; 
		}
        
        return x;
	}
	
	/**
	 * Static utility method to calculate a default starting Y position for a centered axis label.
	 * @param g2 The Graphics object.
	 * @param label The axis label.
	 * @param orientation The orientation.
	 * @param chartHeight The chart height.
	 * @param chartPadding The chart padding.
	 * @return The starting Y position.
	 */
	public static double calculateStartingYPos(Graphics2D g2, String label, Orientation orientation, int chartHeight, int chartPadding) {
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext(); 
        LineMetrics lm = font.getLineMetrics("0", frc); 
        double y = 0;
        double h = lm.getAscent() + lm.getDescent();
        
        if (orientation.isHorizontal()) {
	        y = chartHeight - chartPadding + (chartPadding - h) / 2 + lm.getAscent(); 
		} else {
	        y = chartPadding + ((chartHeight - 2 * chartPadding) - label.length() * h) / 2 + lm.getAscent(); 
		}
        
        return y;
	}
}
