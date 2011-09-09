package com.jboss.demo.mrg.messaging.tmp;

import java.io.InputStream;


/**
 * Invoker for client scripts.
 */
class ClientInvoker extends Thread {

    enum ClientEnum {
         CPP_CLIENT("start_cpp_client.sh"),
         JAVA_CLIENT("start_java_client.sh"),
         JMS_CLIENT("start_jms_client.sh");

         private String clientScript;

         ClientEnum(String clientScript) {
             this.clientScript = clientScript;
         }

         String getClientScript() {
             return clientScript;
         }
    } 
 
    private String clientScript;
    private String clientDirectory;
    private int numThreads;
    private int numMessagesPerThread;
    private LogHandler logHandler;

    /**
     * Constructor.
     * @param client The enumerated client to invoke.
     * @param clientDirectory The client directory location.
     * @param numMessagesPerThread The number of messages per thread.
     * @param numThreads The number of threads.
     * @param logHandler The log handler.
     */
    ClientInvoker(ClientEnum client, String clientDirectory, int numMessagesPerThread, 
           int numThreads, LogHandler logHandler) {
        this.clientScript = client.getClientScript();
        this.clientDirectory = clientDirectory;
        this.numMessagesPerThread = numMessagesPerThread;
        this.numThreads = numThreads;
        this.logHandler = logHandler;
    }

    /**
     * The invoker's thread start method.
     */
    public void run() {
        try {
            Process p = Runtime.getRuntime().exec(getCommandToExec());
            InputStream is = p.getInputStream();
            int i = is.read();
            while(i != -1) {
                logHandler.log("" + (char)i);
                i = is.read();
            }

            logHandler.log("=======================================================\n");
        } catch (Throwable t) {
            logHandler.log(t);
        }
    }

    /**
     * Returns the command to execute per the selected client.
     */
    private String getCommandToExec() {
        return clientDirectory + "/" + clientScript + 
            " " + numMessagesPerThread + " " + numThreads;
    }
}
