package vista.controles;

import javax.swing.table.DefaultTableModel;

public class DSGTableModelList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DSGTableModelList() {
		this(new String[] {"Documento", "Fecha"});
	}
	public DSGTableModelList(String[] cabecera) {
		int t = cabecera.length;
		for (int i = 0; i < t; i++) {
			addColumn(cabecera[i]);
		}
	}

	public void limpiar() {
		while (getRowCount() != 0) {
			removeRow(0);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		if (getRowCount() == 0) {
			return Object.class;
		} else {
			Object cellValue = getValueAt(0, c);
			return cellValue.getClass();
		}
	}
}
