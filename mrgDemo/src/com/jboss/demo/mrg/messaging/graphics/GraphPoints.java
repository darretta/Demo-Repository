package com.jboss.demo.mrg.messaging.graphics;

import java.util.Collection;
import java.util.LinkedList;

import com.jboss.demo.mrg.messaging.data.DataSourceConsumer;

/**
 * Encapsulates a set of graph points. This class acts as a consumer of a
 * data source that supplies the point data.
 * @author Mike Darretta
 */
public class GraphPoints implements DataSourceConsumer {

	/** The graph point data */
    private LinkedList<Integer> data;
    
    /** The upper limit of this number of data points */
    private int maxSize;
    
    /** The name of the data source */
    private String name;
    
    /**
     * Constructor.
     * @param maxSize The upper limit of this number of data points.
     * @param name The name of the data source.
     */
    public GraphPoints(int maxSize, String name) {
    	this.maxSize = maxSize;
    	this.name = name;
    	this.data = new LinkedList<Integer>();
    	init();
    }
    
    /**
     * Constructor with data.
     * @param data The collection of data points.
     * @param maxSize The upper limit of this number of data points.
     * @param name The name of the data source.
     */
	public GraphPoints(Collection<Integer> data, int maxSize, String name) {
        this.data = new LinkedList<Integer> (data);
        this.maxSize = maxSize;
        this.name = name;
    }

	/**
	 * Initializes this data points to a set of non-renderable values.
	 */
    protected void init() {
    	for (int x=0; x < maxSize; x++) {
    		data.add(Integer.MIN_VALUE);
    	}
    }

    /**
     * Returns the data source name.
     * @return The data source name.
     */
    public String getName() {
		return name;
	}

    /**
     * Sets the data source name.
     * @param name The data source name.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the maximum data point size limit.
	 * @return The maximum data point size limit.
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * Sets the maximum data point size limit.
	 * @param maxSize The maximum data point size limit.
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Returns the data points as an array of integers.
	 * @return The data points as an array of integers.
	 */
    public Integer[] getData() {
        Integer[] intData = (Integer[]) data.toArray(new Integer[] {});
        return intData;
    }
    
    /**
     * Adds the value as a new data point. If the maximum data point upper limit has been reached,
     * the new data point will replace the old in a FIFO scheme.
     * @param value The new data point.
     */
    public void addData(int value) {
    	if (data.size() >= maxSize) {
    	    data.removeFirst();
     	}
    	data.addLast(value);
    }

    /**
     * Returns a string representation of this object.
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        Integer[] data = getData();
        StringBuffer buffer = new StringBuffer("[");
        for (int x=0; x < data.length; x++) {
            buffer.append(data[x] + " ");
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * Updates this data source with the input value.
     * @param o The input value, which needs to be of type <code>Integer</code>.
     */
    public void update(Object o) {
    	
    	if (!(o instanceof Integer)) {
    		throw new RuntimeException("Bad data input of type " + o.getClass());
    	}
    	addData(((Integer) o).intValue());
    }
}

