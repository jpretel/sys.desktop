package vista.utilitarios.editores;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import vista.utilitarios.StringUtils;

public class FloatEditor extends DefaultCellEditor {
	
	private static final long serialVersionUID = 1L;

	private static final String prefix = "###,###,###,##0.";
	private static DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
	private DefaultFormatterFactory factory;

	public FloatEditor(int decimals) {

		super(new JFormattedTextField());

		decimalSymbols.setDecimalSeparator('.');
		decimalSymbols.setGroupingSeparator(',');
		// setHorizontalAlignment(RIGHT);

		factory = new DefaultFormatterFactory(
				new NumberFormatter(
						new DecimalFormat(prefix
								+ StringUtils._replicate(decimals, '0'),
								decimalSymbols)));
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		JFormattedTextField editor = (JFormattedTextField) super
				.getTableCellEditorComponent(table, value, isSelected, row,
						column);
		editor.setHorizontalAlignment(SwingConstants.RIGHT);
		if (value != null) {
			editor.setValue(null);
			editor.setFormatterFactory(factory);
			
			float valor;
			try {
				String cad = value.toString();
				cad = cad.replaceAll(",", "");
				valor = Float.parseFloat(cad);
			} catch (NumberFormatException e) {
				valor = 0.0F;
			} catch (Exception e) {
				valor = 0.0F;
			}
			editor.setValue(valor);
			editor.selectAll();
		}
		return editor;
	}
	
	
}