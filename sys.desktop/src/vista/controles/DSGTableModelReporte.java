package vista.controles;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DSGTableModelReporte extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] cabeceras;
	private JScrollPane scrollPane;
	private JTable table;
	
	public DSGTableModelReporte() {
		this(new String[] {"Dato 01", "Dato 02", "Dato 03"});
	}
	
	public DSGTableModelReporte(String[] cabeceras) {
		this.cabeceras = cabeceras;
		for (String c : this.cabeceras) {
			addColumn(c);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void limpiar() {
		while (getRowCount() != 0) {
			removeRow(0);
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		int columnCount;
		columnCount = getRowCount();
		if (columnCount < 1) {
			return String.class;
		}
		try {
			return getValueAt(0, c).getClass();
		} catch (Exception e) {
			return Object.class;
		}
		
	}
	
//	private void refrescarRowHeader() {
//		if (getScrollPane() != null && getTable() != null) {
//			getScrollPane().setRowHeaderView(
//					JTableUtils.buildRowHeader(getTable(), this));
//		}
//	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}
