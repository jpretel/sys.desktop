package vista.controles;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit;
	private boolean upper;

	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
		this.upper = upper;
	}
	
	public JTextFieldLimit(int limit, char tipo) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null)
			return;
		if (this.upper) {
			str = str.toUpperCase();
		}
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		} else{
			Toolkit.getDefaultToolkit().beep();
		}
	}
	

	public static void main(String[] args) {
		JTextFieldLimitTest frame = new JTextFieldLimitTest();
		frame.init();
		frame.setVisible(true);
	}
}

class JTextFieldLimitTest extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField textfield1;

	JLabel label1;

	public void init() {
		setLayout(new FlowLayout());
		label1 = new JLabel("max 10 chars");
		textfield1 = new JTextField(15);
		add(label1);
		add(textfield1);
		textfield1.setDocument(new JTextFieldLimit(3, true));

		setSize(300, 300);
		setVisible(true);
	}
}