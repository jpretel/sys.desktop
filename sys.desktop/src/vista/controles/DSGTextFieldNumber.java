package vista.controles;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import vista.utilitarios.StringUtils;

public class DSGTextFieldNumber extends JFormattedTextField {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5421974600205609603L;
	private static final String prefix = "###,###,###,##0.";
	private static DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();

	// private DecimalFormat format = new DecimalFormat("###,###,###,##0.00");

	public DSGTextFieldNumber(int decimals) {
		decimalSymbols.setDecimalSeparator('.');
		decimalSymbols.setGroupingSeparator(',');

		DefaultFormatterFactory factory = new DefaultFormatterFactory(
				new NumberFormatter(
						new DecimalFormat(prefix
								+ StringUtils._replicate(decimals, '0'),
								decimalSymbols)));

		setFormatterFactory(factory);

		setHorizontalAlignment(SwingConstants.RIGHT);

	}
}
