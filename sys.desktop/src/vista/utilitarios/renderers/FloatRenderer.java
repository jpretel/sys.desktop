package vista.utilitarios.renderers;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import vista.utilitarios.StringUtils;

public class FloatRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DecimalFormat nf;// = NumberFormat.getInstance();// .getCurrencyInstance();

	private static final String prefix = "###,###,###,##0.";
	private static DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();

	public FloatRenderer(int decimals) {
		decimalSymbols.setDecimalSeparator('.');
		decimalSymbols.setGroupingSeparator(',');
		setHorizontalAlignment(RIGHT);
		nf = new DecimalFormat(prefix + StringUtils._replicate(decimals, '0'),
				decimalSymbols);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		float valor;
		try {
			String cad = value.toString();
			cad = cad.replaceAll(",", "");
			valor = Float.parseFloat(cad);
		} catch (Exception e) {
			valor = 0;
		}
		setText(nf.format(valor));
		
		return this;
	}
}