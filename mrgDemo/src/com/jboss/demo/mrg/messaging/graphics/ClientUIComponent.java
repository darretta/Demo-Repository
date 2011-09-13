package com.jboss.demo.mrg.messaging.graphics;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jboss.demo.mrg.messaging.Properties;

/**
 * Client UI selection component.
 * @author Mike Darretta
 */
public class ClientUIComponent extends JComponent {
	
	/** Client type enumerator. */
	public enum ClientType { 
		
		/** CPP client type. */
		CPP("C++"), 
		
		/** JMS client type. */
		JMS("JMS"), 
		
		/** Python client type. */
		PYTHON("Python");
		
		/** The string client name value. */
		private final String nameVal;
		
		/**
		 * Constructor.
		 * @param nameVal The string name value.
		 */
		ClientType(String nameVal) {
			this.nameVal = nameVal;
		}

		/**
		 * Returns the string name value.
		 * @return The string name value.
		 */
		public String getNameVal() {
			return this.nameVal;
		}
	}

	/** Serial version UID */
	private static final long serialVersionUID = -7779005987107687751L;
	
	/** Client type checkbox */
	private JCheckBox clientTypeCheckBox;
	
	/** Number of threads (clients) text field */
    private JTextField numThreadsTextField;
    
    /** Number of threads (clients) label */
    private JLabel numThreadsLabel;
    
    /** The client type */
    private ClientType clientType;

    /**
     * Constructor.
     * @param labelName Component label name.
     */
    public ClientUIComponent(ClientType clientType, String labelName) {
        super();
        this.clientType = clientType;
        this.clientTypeCheckBox = new JCheckBox(labelName);
        this.numThreadsTextField = new JTextField(5);
        this.numThreadsLabel = new JLabel(" client thread(s)");
        init();
    }

    /**
     * Initializes this component.
     */
    private void init() {
        this.setLayout(new GridLayout());

        numThreadsTextField.setText(Properties.getProperties().getStringProperty(
        		Properties.DEFAULT_NUM_THREADS_PER_CLIENT_STR));

        this.add(clientTypeCheckBox);
        this.add(numThreadsTextField);
        this.add(numThreadsLabel);

        numThreadsTextField.setVisible(false);
        numThreadsLabel.setVisible(false);

        clientTypeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numThreadsTextField.setVisible(clientTypeCheckBox.isSelected());
                numThreadsLabel.setVisible(clientTypeCheckBox.isSelected());
            }
        });
    }

    /**
     * Returns the client type.
     * @return The client type.
     */
    public ClientType getClientType() {
		return clientType;
	}

	/**
     * Returns whether this component is selected.
     * @return True if this component is selected.
     */
    public boolean isSelected() {
        return clientTypeCheckBox.isSelected();
    }
    
    /**
     * Sets the enabled of this component.
     * @param isEnabled True if the component is enabled.
     */
    public void setEnabled(boolean isEnabled) {
    	this.clientTypeCheckBox.setEnabled(isEnabled);
    	this.numThreadsTextField.setEnabled(isEnabled);
    	this.numThreadsLabel.setEnabled(isEnabled);
    }
    
    /**
     * Returns the number of active client threads.
     * @return The number of active client threads.
     */
    public int getNumActiveClientThreads() {
    	int numActiveClientThreads = 0;
    	if (isSelected()) {
    		numActiveClientThreads = Integer.parseInt(numThreadsTextField.getText());
    	}
    	
    	return numActiveClientThreads;
    }
}
