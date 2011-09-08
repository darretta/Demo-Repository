package com.jboss.demo.mrg.messaging.graphics;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Abstract encapsulation of axis coordinates.
 * @author Mike Darretta
 */
public abstract class AxisCoordinates {
	
	/** The coordinates */
	protected Collection<Integer> coordinates;
	
	/**
	 * Constructor.
	 * @param minValue The minimum coordinate value.
	 * @param maxValue The maximum coordinate value.
	 * @param axisPadding The axis padding (from the largest real value).
	 * @param numCoordinates The number of coordinates to generate.
	 */
	public AxisCoordinates(int minValue, int maxValue, double axisPadding, int numCoordinates) {
		this.coordinates = new LinkedList<Integer> ();
		initCoordinates(minValue, maxValue, axisPadding, numCoordinates);
	}
	
	/**
	 * Initializes the coordinates.
	 * @param minValue The minimum coordinate value.
	 * @param maxValue The maximum coordinate value.
	 * @param axisPadding The axis padding (from the largest real value).
	 * @param numCoordinates The number of coordinates to generate.
	 */
	protected abstract void initCoordinates(int minValue, int maxValue, double axisPadding, int numCoordinates);

	/**
	 * Returns the number of coordinates.
	 * @return The number of coordinates.
	 */
	public int getNumCoordinates() {
		return coordinates.size();
	}

	/**
	 * Returns the coordinates.
	 * @return The coordinates.
	 */
	public Collection<Integer> getCoordinates() {
		return coordinates;
	}

	/**
	 * Returns the maximum coordinate.
	 * @return The maximum coordinate.
	 */
	public int getMaxCoordinate() {
		Iterator<Integer> i = coordinates.iterator();
		int maxVal = 0;
		
		while (i.hasNext()) {
			int val = i.next().intValue();
			if (val > maxVal) {
				maxVal = val;
			}
		}
		
		return maxVal;
	}
	
	/**
	 * Returns a string version of these coordinates.
	 * @return a string version of these coordinates.
	 */
	@Override
	public String toString() {
		Iterator<Integer> i = coordinates.iterator();
		StringBuffer buffer = null;
		if (i.hasNext()) {
		    buffer = new StringBuffer("[" + i.next().intValue());
		}
		
		while (i.hasNext()) {
			buffer.append("," + i.next().intValue());
		}
		
		buffer.append("]");
		return buffer.toString();
	}
}
