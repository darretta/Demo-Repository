package com.jboss.demo.mrg.messaging;

import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import com.jboss.demo.mrg.messaging.data.DataSourceController;
import com.jboss.demo.mrg.messaging.data.QpidQueueStatsOutputDataSource;
import com.jboss.demo.mrg.messaging.graphics.ClientUIComponent;
import com.jboss.demo.mrg.messaging.graphics.GraphPoints;
import com.jboss.demo.mrg.messaging.graphics.LabelTextFieldComponent;
import com.jboss.demo.mrg.messaging.graphics.LineGraph;
import com.jboss.demo.mrg.messaging.graphics.ClientUIComponent.ClientType;
import com.jboss.demo.mrg.messaging.handler.ClusteredBrokerHandler;
import com.jboss.demo.mrg.messaging.handler.CommandHandler;
import com.jboss.demo.mrg.messaging.handler.QpidPerfTestHandler;
import com.jboss.demo.mrg.messaging.handler.QpidQueueStatsHandler;

/**
 * Main frame instance.
 */
public class MainFrame extends JFrame {

	/** The serial version UID */
	private static final long serialVersionUID = -8549128876341319644L;

	/** The log text area (not currently used) */
	private JTextArea logTextArea;
    
	/** Handle to this object for use in anonymous methods */
    private Component thisFrame = this;
    
    /** Collection of command handlers to close at exit */
    private Collection<CommandHandler> handlers;
    
    /** The current broker port number, initialized to the default broker port */
    private int currentPort = Properties.getProperties().getIntegerProperty(
    		Properties.DEFAULT_BROKER_PORT_STR);

    /**
     * Frame constructor.
     * @param name The frame name.
     * @throws HeadlessException
     */
    public MainFrame(String name) throws HeadlessException {
        super(name);
        this.handlers = new ArrayList<CommandHandler>();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                 
        setLayout();
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
    }

    /**
     * Encapsulates layout construction.
     */
    private void setLayout() {
        Container contentPane = this.getContentPane();

        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel clientLabel = new JLabel("Clients to execute: ");
        contentPane.add(clientLabel);
        putComponent(clientLabel, contentPane, 10, contentPane, 10, layout);

        final ClientUIComponent cppClient = new ClientUIComponent(ClientType.CPP, "C++");
        contentPane.add(cppClient);
        putComponent(cppClient, contentPane, 10, clientLabel, 25, layout);

        final ClientUIComponent jmsClient = new ClientUIComponent(ClientType.JMS, "JMS");
        contentPane.add(jmsClient);
        putComponent(jmsClient, contentPane, 10, cppClient, 25, layout);

        final ClientUIComponent pythonClient = new ClientUIComponent(ClientType.PYTHON, "Python");
        contentPane.add(pythonClient);
        putComponent(pythonClient, contentPane, 10, jmsClient, 25, layout);

        final LabelTextFieldComponent threads = new LabelTextFieldComponent(
              "Number of brokers: ", "1", 10);
        contentPane.add(threads);
        putComponent(threads, contentPane, 10, pythonClient, 25, layout);

        final LabelTextFieldComponent messages = new LabelTextFieldComponent(
              "Number of messages per client thread: ", "10000", 10);
        contentPane.add(messages);
        putComponent(messages, contentPane, 10, threads, 25, layout);

        final JButton executeBtn = new JButton("Execute");
        contentPane.add(executeBtn);
        putComponent(executeBtn, contentPane, 10, messages, 25, layout);
        /*layout.putConstraint(SpringLayout.SOUTH, executeBtn,
                        35, SpringLayout.SOUTH, messages);*/

        final JButton clearBtn = new JButton("Clear");
        contentPane.add(clearBtn);
        layout.putConstraint(SpringLayout.WEST, clearBtn,
                        10, SpringLayout.EAST, executeBtn);
        layout.putConstraint(SpringLayout.SOUTH, clearBtn,
                        31, SpringLayout.SOUTH, messages);

        logTextArea = new JTextArea(35,70);
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        contentPane.add(logScrollPane);
        putComponent(logScrollPane, contentPane, 10, executeBtn, 35, layout);
        
        // Prepare the content pane for spring action.
        layout.putConstraint(SpringLayout.EAST, contentPane,
        		10, SpringLayout.EAST, logScrollPane);
        layout.putConstraint(SpringLayout.SOUTH, contentPane,
        		10, SpringLayout.SOUTH, logScrollPane);
        
        
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		Iterator<CommandHandler> i = handlers.iterator();
        		while (i.hasNext()) {
        			i.next().destroyProcess();
        		}
        	}
        });

        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(
                         clearBtn, "Are you sure you want to clear the text output?", 
                         "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    logTextArea.setText("");
                }
            }
        });

		executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// LogHandler logHandler = new
					// JTextAreaLogHandler(logTextArea);
					// final int numMessagesPerThread =
					// Integer.parseInt(messages.getText());
					final int numBrokers = Integer.parseInt(threads.getText());

					// Collection<ClientType> clients =
					// resolveClients(new ClientUIComponent[] { cppClient,
					// jmsClient, pythonClient });

					Component parent = thisFrame;
					
					for (int x = 0; x < numBrokers; x++) {
						CommandHandler handler = new ClusteredBrokerHandler(currentPort);
						handlers.add(handler);
						handler.execute();

						LineGraph lineGraph = new LineGraph(handler);
						
						String hostname = Properties.getProperties().getStringProperty(
								Properties.DEFAULT_HOSTNAME_STR);
						String ipAddress = InetAddress.getByName(hostname).getHostAddress();

						bindGraphToSource(lineGraph,
								ipAddress,
								currentPort,
								QpidQueueStatsOutputDataSource.ENQ_RATE_COLUMN,
								"Enqueue Rate");
						bindGraphToSource(lineGraph,
								ipAddress,
								currentPort,
								QpidQueueStatsOutputDataSource.DEQ_RATE_COLUMN,
								"Dequeue Rate");
						LineGraphFrame lgf = new LineGraphFrame(parent,
								lineGraph, ipAddress + ":" + (currentPort++));
						new Thread(lgf).start();
						parent = lgf;
					}

					if (cppClient.isEnabled()) {
						for (int x = 0; x < cppClient
								.getNumActiveClientThreads(); x++) {
							CommandHandler handler = new QpidPerfTestHandler(
									Integer.valueOf(messages.getText())
											.intValue());
							handlers.add(handler);
							handler.execute();
						}
					}
					/*
					 * if (cppRadioBtn.isSelected()) { new
					 * ClientInvoker(ClientInvoker.ClientEnum.CPP_CLIENT,
					 * directory.getTextField().getText(), numMessagesPerThread,
					 * numThreads, logHandler).start(); } else if
					 * (javaRadioBtn.isSelected()) { new
					 * ClientInvoker(ClientInvoker.ClientEnum.JAVA_CLIENT,
					 * directory.getTextField().getText(), numMessagesPerThread,
					 * numThreads, logHandler).start(); } else { new
					 * ClientInvoker(ClientInvoker.ClientEnum.JMS_CLIENT,
					 * directory.getTextField().getText(), numMessagesPerThread,
					 * numThreads, logHandler).start(); }
					 */
				} catch (UnknownHostException uhe) {
					uhe.printStackTrace();
				}
			}
		});
        
        this.pack();
    }
    
//    /**
//     * Resolves all the select client components for thread distinction.
//     * @param clientComponents The client components.
//     * @return An array of client types corresponding to each client thread.
//     */
//    private Collection<ClientType> resolveClients(ClientUIComponent[] clientComponents) {
//    	
//    	Collection<ClientType> clients = new ArrayList<ClientType> ();
//    	for (int x=0; x < clientComponents.length; x++) {
//    		int numClients = clientComponents[x].getNumActiveClientThreads();
//    		for (int y=0; y < numClients; y++) {
//    			clients.add(clientComponents[x].getClientType());
//    		}
//    	}
//    	
//    	return clients;
//    }
    
    /**
     * Binds a graph to it's data sources.
     * @param lineGraph The line graph to bind to.
     * @param ipAddress The address of the data source.
     * @param port The port number for the data source.
     * @param columnNumber The column number of the qpid-queue-stats output.
     * @param graphName The name of the graph for presentation.
     */
    private void bindGraphToSource(LineGraph lineGraph, String ipAddress, int port, 
    		int columnNumber, String graphName) {

		CommandHandler qpidQueueStatsHandler = new QpidQueueStatsHandler(ipAddress, port);
		handlers.add(qpidQueueStatsHandler);
		qpidQueueStatsHandler.execute();
		
		// Wait 100 milliseconds to give the qpid-queue-stats thread a chance to initialize.
		pause(100);

		QpidQueueStatsOutputDataSource source = new QpidQueueStatsOutputDataSource(
				qpidQueueStatsHandler.getProcess().getInputStream(), columnNumber);

		GraphPoints points = new GraphPoints(20, graphName);
		lineGraph.addPoints(points);

		new DataSourceController(source, points).start();
    }

    /**
     * Convenience method for setting up a component per the normal layout settings.
     * @param component The component to add.
     * @param westRelativeComponent The west-most component relative to the new component.
     * @param westRelativeLocation The west edge location for the component.
     * @param northRelativeComponent The north-most component relative to the new component.
     * @param northRelativeLocation The north edge location for the component.
     * @param layout The <code>SpringLayout</code> to add the component to.
     */
    private void putComponent(Component component, Component westRelativeComponent, int westRelativeLocation,
            Component northRelativeComponent, int northRelativeLocation, SpringLayout layout) {

        layout.putConstraint(SpringLayout.WEST, component,
                westRelativeLocation,
                SpringLayout.WEST, westRelativeComponent);
        layout.putConstraint(SpringLayout.NORTH, component,
                northRelativeLocation,
                SpringLayout.NORTH, northRelativeComponent);
    }
    
    /**
     * Utility method to pause thread execution for managing multiple threads.
     * @param milliseconds The number of milliseconds to pause.
     */
    private void pause(int milliseconds) {
    	try {
    		Thread.sleep(milliseconds);
    	} catch (InterruptedException ie) {
    		ie.printStackTrace();
    	}
    }
}
