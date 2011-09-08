package com.jboss.demo.mrg.messaging.graphics;

/**
 * Encapsulates X-axis coordinates.
 * @author Mike Darretta
 */
public class XAxisCoordinates extends AxisCoordinates {
		
	/**
	 * Constructor for a maximum coordinate value.
	 * @param maxValue The maximum value.
	 */
	public XAxisCoordinates(int maxValue) {
		this(0, maxValue, 0.0, 10);
	}
	
	/**
	 * Constructor.
	 * @param minValue The minimum coordinate value.
	 * @param maxValue The maximum coordinate value.
	 * @param axisPadding The axis padding (from the largest real value).
	 * @param numCoordinates The number of coordinates.
	 */
	public XAxisCoordinates(int minValue, int maxValue, double axisPadding, int numCoordinates) {
		super(minValue, maxValue, axisPadding, numCoordinates);
	}
		
	/**
	 * Initializes the coordinates.
	 * @param minValue The minimum coordinate value.
	 * @param maxValue The maximum coordinate value.
	 * @param axisPadding The axis padding (from the largest real value).
	 * @param numCoordinates The number of coordinates to generate.
	 */
	@Override
	protected void initCoordinates(int minValue, int maxValue, double axisPadding, int numCoordinates) {
		coordinates.add(new Integer(minValue));
		double currCoordinate = (double) minValue;
		double interval = (double) maxValue / (double) numCoordinates;
		for (int x=1; x < numCoordinates; x++) {
			currCoordinate += interval;
			coordinates.add(new Integer((int) currCoordinate));
		}
	}
}
