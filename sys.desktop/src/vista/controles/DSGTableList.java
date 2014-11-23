package vista.controles;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

public abstract class DSGTableList extends JXTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2075530596851124058L;
	private int estadoColumn = -1;

	public DSGTableList() {
		super();
		setColumnControlVisible(true);
		addListener();
	}

	public DSGTableList(int estadoColumn) {
		super();
		this.estadoColumn = estadoColumn;
		this.setColumnControlVisible(true);
		addListener();
	}

	private void addListener() {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = getSelectedRow();
					if (row > -1) {
						row = convertRowIndexToModel(row);
						DoDobleClick(row);
					}
				}
			}
		});
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component c = super.prepareRenderer(renderer, row, column);

		if (estadoColumn != -1)
			if (!isRowSelected(row)) {
				c.setBackground(getBackground());
				int modelRow = convertRowIndexToModel(row);
				String type = (String) getModel().getValueAt(modelRow,
						estadoColumn);
				if ("Anulado".equals(type))
					c.setForeground(Color.RED);
				else
					c.setForeground(UIManager.getColor("Table.cellForeground"));
			}

		return c;
	}

	public abstract void DoDobleClick(int row);
}
