package vista.controles.celleditor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public abstract class JXTableTextField<T> extends
		vista.contenedores.JXTextFieldEntityAC<T> {

	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private int ubicacion;

	public JXTableTextField(String[] cabeceras, int[] anchos, JTable tabla,
			int ubicacion) {
		super(getFormulario(), cabeceras, anchos);
		this.tabla = tabla;
		this.ubicacion = ubicacion;
	}

	public void refresValue(String value) {
		setText(value);
		setEntityPorCodigo();
		cargaDatos();
		autoSuggestionPopUpWindow.setVisible(false);
	}
	
	public void updateCellEditor() {
		tabla.getColumnModel().getColumn(ubicacion)
				.setCellEditor(new JTextCellEditorAC(this));
	}

	@Override
	public void cargaDatos() {
		cargaDatos(getSeleccionado());
	}

	public abstract void cargaDatos(T entity);
	
}

class JTextCellEditorAC extends AbstractCellEditor implements TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComponent component;

	public JTextCellEditorAC(JXTableTextField<?> text) {
		this.component = text;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int rowIndex, int vColIndex) {

		((JXTableTextField<?>) component).setText((String) value);
		return component;
	}

	public Object getCellEditorValue() {
		return ((JXTableTextField<?>) component).getText();
	}
}