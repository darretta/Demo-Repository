package com.jboss.demo.mrg.messaging.graphics;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Label/text field pair component.
 * @author Mike Darretta
 */
public class LabelTextFieldComponent extends JComponent {

	private static final long serialVersionUID = -6983643607331287279L;
	private JLabel label;
    private JTextField textField;

    /**
     * Constructor.
     * @param labelName Component label name.
     */
    public LabelTextFieldComponent(String labelName) {
        this(labelName, "", 10);
    }

    /**
     * Constructor.
     * @param labelName Component label name.
     * @param defaultText Default text.
     * @param textFieldSize Text field size.
     */
    public LabelTextFieldComponent(String labelName, String defaultText, int textFieldSize) {
        super();
        this.label = new JLabel(labelName);
        this.textField = new JTextField(textFieldSize);
        this.textField.setText(defaultText);
        init();
    }

    /**
     * Initializes this component.
     */
    private void init() {
        this.setLayout(new GridLayout());

        this.add(label);
        this.add(textField);
    }
    
    /**
     * Sets the visibility of the underlying components.
     * @param isVisible True if this component is to be visible.
     */
    public void setVisible(boolean isVisible) {
    	label.setVisible(isVisible);
    	textField.setVisible(isVisible);
    }

    /**
     * Returns the text portion of this component.
     * @return The text portion of this component.
     */
    public String getText() {
        return textField.getText();
    }
}
