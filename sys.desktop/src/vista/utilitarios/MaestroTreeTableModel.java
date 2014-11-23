package vista.utilitarios;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public abstract class MaestroTreeTableModel extends AbstractTreeTableModel{
	
	private String[] cabeceras;
	
	public MaestroTreeTableModel(String[] cabeceras) {
		this.cabeceras = cabeceras;
	}
	
	@Override
	public int getColumnCount() {
		return this.cabeceras.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return this.cabeceras[column];
	}
}
