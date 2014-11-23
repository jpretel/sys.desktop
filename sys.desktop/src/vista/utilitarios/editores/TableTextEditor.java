package vista.utilitarios.editores;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import vista.controles.JTextFieldLimit;

public class TableTextEditor extends AbstractCellEditor implements
		TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField text = new JTextField();

	public TableTextEditor(int length, boolean upper) {
		text.setDocument(new JTextFieldLimit(length, upper));
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		text.setText(value.toString());
		return text;
	}

	public Object getCellEditorValue() {
		return text.getText();
	}
}