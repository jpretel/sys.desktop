package vista.utilitarios;

import javax.swing.table.DefaultTableModel;

public class MaestroTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public MaestroTableModel() {
		addColumn("C�digo");
		addColumn("Descripci�n");
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void limpiar() {
		while (getRowCount() != 0) {
			removeRow(0);
		}
	}
}