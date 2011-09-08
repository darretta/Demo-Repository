package com.jboss.demo.mrg.messaging.handler;

/**
 * Handler for a C++ client.
 * @author Mike Darretta
 */
public class CppClientHandler extends ClientHandler {
	
	/**
	 * Constructor.
	 * @param numThreads The number of client threads.
	 * @param numMessagesPerThread The number of messages per thread.
	 */
	public CppClientHandler(int numThreads, int numMessagesPerThread) {
		super(numThreads, numMessagesPerThread);
	}

	/**
	 * Returns the command to execute.
	 * @return The command to execute.
	 */
	@Override
	protected String getCommand() {
		return null;
	}
}
