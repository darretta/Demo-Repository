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
import com.jboss.demo.mrg.messaging.handler.JTextAreaLogHandler;
import com.jboss.demo.mrg.messaging.handler.LogHandler;
import com.jboss.demo.mrg.messaging.handler.LoggingException;
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
    
    /** 
     * Collection of command handlers to close at exit. This is a fail-safe means
     * to ensure all processes are killed at application exit.
     */
    private Collection<CommandHandler> handlers;

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
        cppClient.setEnabled(Properties.getProperties().getBooleanProperty(
        		Properties.ENABLE_CPP_CLIENT_BTN_STR));
        
        final ClientUIComponent jmsClient = new ClientUIComponent(ClientType.JMS, "JMS");
        contentPane.add(jmsClient);
        putComponent(jmsClient, contentPane, 10, cppClient, 25, layout);
        jmsClient.setEnabled(Properties.getProperties().getBooleanProperty(
        		Properties.ENABLE_JMS_CLIENT_BTN_STR));

        final ClientUIComponent pythonClient = new ClientUIComponent(ClientType.PYTHON, "Python");
        contentPane.add(pythonClient);
        putComponent(pythonClient, contentPane, 10, jmsClient, 25, layout);
        pythonClient.setEnabled(Properties.getProperties().getBooleanProperty(
        		Properties.ENABLE_PYTHON_CLIENT_BTN_STR));

        final LabelTextFieldComponent numBrokersComp = new LabelTextFieldComponent(
              "Number of brokers: ", "1", 10);
        contentPane.add(numBrokersComp);
        putComponent(numBrokersComp, contentPane, 10, pythonClient, 25, layout);

        final LabelTextFieldComponent numMessagesPerClientComp = new LabelTextFieldComponent(
              "Number of messages per client thread: ", 
              Properties.getProperties().getStringProperty(Properties.DEFAULT_NUM_MSGS_PER_CLIENT_STR),
              10);
        contentPane.add(numMessagesPerClientComp);
        putComponent(numMessagesPerClientComp, contentPane, 10, numBrokersComp, 25, layout);
        
        // Set to visible only when one of the client buttons are selected.
        numMessagesPerClientComp.setVisible(false);

        final JButton executeBtn = new JButton("Execute");
        contentPane.add(executeBtn);
        putComponent(executeBtn, contentPane, 10, numMessagesPerClientComp, 25, layout);

        final JButton clearBtn = new JButton("Clear");
        contentPane.add(clearBtn);
        layout.putConstraint(SpringLayout.WEST, clearBtn,
                        10, SpringLayout.EAST, executeBtn);
        layout.putConstraint(SpringLayout.SOUTH, clearBtn,
                        31, SpringLayout.SOUTH, numMessagesPerClientComp);

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
        			try {
        			    i.next().destroyProcess();
        			} catch (Exception e2) {
        				e2.printStackTrace();
        			}
        		}
        	}
        });
        
        cppClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                numMessagesPerClientComp.setVisible(cppClient.isSelected() || pythonClient.isSelected() || jmsClient.isSelected());
            }
        });

        jmsClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                numMessagesPerClientComp.setVisible(cppClient.isSelected() || pythonClient.isSelected() || jmsClient.isSelected());
            }
        });

        pythonClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                numMessagesPerClientComp.setVisible(cppClient.isSelected() || pythonClient.isSelected() || jmsClient.isSelected());
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
        
        final LogHandler logHandler = new JTextAreaLogHandler(logTextArea);

		executeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Verify that the client and broker values are valid numbers.
					try {
						Integer.parseInt(numBrokersComp.getText());
						Integer.parseInt(numMessagesPerClientComp.getText());
						cppClient.getNumActiveClientThreads();
						jmsClient.getNumActiveClientThreads();
						pythonClient.getNumActiveClientThreads();
					} catch (NumberFormatException nfe) {
						try {
						    logHandler.logWithNewline("Please ensure the number of brokers " +
								"and/or number of messages per client is a valid number!");
						} catch (LoggingException le) {
							le.printStackTrace();
						}
						return;
					}
					
					final int numBrokers = Integer.parseInt(numBrokersComp.getText());
					Component parent = thisFrame;
					
					for (int x = 0; x < numBrokers; x++) {
						ClusteredBrokerHandler handler = new ClusteredBrokerHandler(logHandler);
						handlers.add(handler);
						handler.execute();

						LineGraph lineGraph = new LineGraph();
						lineGraph.addHandler(handler);
						
						String hostname = Properties.getProperties().getStringProperty(
								Properties.DEFAULT_HOSTNAME_STR);
						String ipAddress = InetAddress.getByName(hostname).getHostAddress();

						bindGraphToSource(lineGraph,
								ipAddress,
								handler.getPort(),
								QpidQueueStatsOutputDataSource.ENQ_RATE_COLUMN,
								"Enqueue Rate",
								logHandler);
						bindGraphToSource(lineGraph,
								ipAddress,
								handler.getPort(),
								QpidQueueStatsOutputDataSource.DEQ_RATE_COLUMN,
								"Dequeue Rate",
								logHandler);
						LineGraphFrame lgf = new LineGraphFrame(parent,
								lineGraph, ipAddress + ":" + handler.getPort());
						new Thread(lgf).start();
						parent = lgf;
					}

					if (cppClient.isEnabled()) {
						for (int x = 0; x < cppClient
								.getNumActiveClientThreads(); x++) {
							CommandHandler handler = new QpidPerfTestHandler(
									Integer.valueOf(numMessagesPerClientComp.getText())
											.intValue(), logHandler);
							handlers.add(handler);
							handler.setRetryLimitInMillis(Properties.getProperties().getIntegerProperty(
									Properties.QPID_PERF_TEST_CMD_RETRY_TIME_LIMIT_IN_MILLIS_STR));
							handler.execute();
						}
					}
				} catch (UnknownHostException uhe) {
					logHandler.log(uhe);
				}
			}
		});
        
        this.pack();
    }
    
    /**
     * Binds a graph to it's data sources.
     * @param lineGraph The line graph to bind to.
     * @param ipAddress The address of the data source.
     * @param port The port number for the data source.
     * @param columnNumber The column number of the qpid-queue-stats output.
     * @param graphName The name of the graph for presentation.
     * @param logHandler The log handler to manage output.
     */
    private void bindGraphToSource(LineGraph lineGraph, String ipAddress, int port, 
    		int columnNumber, String graphName, LogHandler logHandler) {

		CommandHandler qpidQueueStatsHandler = new QpidQueueStatsHandler(ipAddress, port, logHandler);
		lineGraph.addHandler(qpidQueueStatsHandler);
		handlers.add(qpidQueueStatsHandler);
		qpidQueueStatsHandler.execute();

		QpidQueueStatsOutputDataSource source = new QpidQueueStatsOutputDataSource(
				qpidQueueStatsHandler.getProcess().getInputStream(), columnNumber, logHandler);

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
}
