package vista.utilitarios.renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import core.dao.RequerimientoDAO;
import core.entity.Requerimiento;

public class ReferenciaDOCRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		RequerimientoDAO reqDAO = new RequerimientoDAO();

		String valor = "N/A";
		ReferenciaDOC referencia;
		if (value instanceof ReferenciaDOC && value != null) {
			referencia = (ReferenciaDOC) value;

			if (referencia.getTipo_referencia().equals("REQINTERNO")) {
				Requerimiento req = reqDAO.find(referencia.getIdreferencia());
				valor = "REQ." + req.getSerie() + "-" + req.getNumero();
			}

		}

		setText(valor);

		return this;
	}
}