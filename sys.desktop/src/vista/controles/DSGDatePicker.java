package vista.controles;

import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXDatePicker;

public class DSGDatePicker extends JXDatePicker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3597089055657010041L;
	
	private SimpleDateFormat formater;
	
	public DSGDatePicker() {
		super();
		formater = new SimpleDateFormat("dd/MM/yyyy");
		setFormats(formater);
	}

	public SimpleDateFormat getFormater() {
		return formater;
	}

	public void setFormater(SimpleDateFormat formater) {
		this.formater = formater;
	}
}
