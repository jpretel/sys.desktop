package vista.controles;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import vista.utilitarios.StringUtils;

public class DSGTextFieldCorrelativo extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int total;
	public DSGTextFieldCorrelativo(int total){
		super();
		this.total = total;
		setDocument(new JTextFieldLimit(total, true));
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (getText() != null && !getText().trim().isEmpty())
					setText(StringUtils._padl(getText(), DSGTextFieldCorrelativo.this.total, '0'));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				selectAll();
			}
		});
	}
	
	public void setValue(Object value){
		if(value == null)
			setText(null);
		else
			setText((String) value);
	}
	
	public void setValue(int value){
		setText(StringUtils._padl(String.valueOf(value), DSGTextFieldCorrelativo.this.total, '0'));
	}
}
