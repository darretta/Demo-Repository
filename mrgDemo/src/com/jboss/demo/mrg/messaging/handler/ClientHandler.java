package com.jboss.demo.mrg.messaging.handler;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Abstract handler for handling QPID client threads.
 * @author Mike Darretta
 */
public abstract class ClientHandler extends CommandHandler {
 
	/** The number of client threads */
    protected int numThreads;
    
    /** The number of messages per thread */
    protected int numMessagesPerThread;
    
    /** The default number of client threads */
    public static final int DEFAULT_NUM_THREADS = 
    	(Integer) Properties.getProperties().getProperty(
    		Properties.DEFAULT_NUM_THREADS_PER_CLIENT_STR);
    	
    
    /** The default number of messages per thread */
    public static final int DEFAULT_NUM_MESSAGES_PER_THREAD =
    	(Integer) Properties.getProperties().getProperty(
    		Properties.DEFAULT_NUM_MSGS_PER_CLIENT_STR);
    
    /**
     * Default constructor assumes default number of clients and
     * messages per thread.
     */
    public ClientHandler() {
    	this(DEFAULT_NUM_THREADS, DEFAULT_NUM_MESSAGES_PER_THREAD);
    }

    /**
     * Constructor.
     * @param numThreads The number of client threads.
     * @param numMessagesPerThread The number of messages per thread.
     */
    public ClientHandler(int numThreads, int numMessagesPerThread) {
        this.numThreads = numThreads;
        this.numMessagesPerThread = numMessagesPerThread;
    }
    
    /**
     * Returns the number of client threads.
     * @return The number of client threads.
     */
    public int getNumThreads() {
    	return numThreads;
    }
    
    /**
     * Returns the number of messages per thread.
     * @return The number of messages per thread.
     */
    public int getNumMessagesPerThread() {
    	return numMessagesPerThread;
    }
}
