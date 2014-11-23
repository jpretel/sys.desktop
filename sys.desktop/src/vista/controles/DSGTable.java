package vista.controles;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DSGTable extends JTable {
	private static final long serialVersionUID = -2655613140121211556L;
	protected final static String DOWN_EVENT = "down_event";
	public DSGTable(DSGTableModel dsgTableModel) {
		super(dsgTableModel);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellSelectionEnabled(true);
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		getColumnModel().getSelectionModel().addListSelectionListener(
				new ExploreSelectionListener());
		
		KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
		
		getInputMap().put(down, DOWN_EVENT);
        
        getActionMap().put(DOWN_EVENT, new AbstractAction(DOWN_EVENT) {
			private static final long serialVersionUID = -2813699112427137160L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
            
        });
        
	}

	private class ExploreSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				int row = getSelectedRow();
				int col = getSelectedColumn();
				// Make sure we start with legal values.
				while (col < 0)
					col++;
				while (row < 0)
					row++;
				// Find the next editable cell.
				// while (!isCellEditable(row, col)) {
				// col++;
				// if (col > getColumnCount() - 1) {
				// col = 1;
				// row = (row == getRowCount() - 1) ? 1 : row + 1;
				// }
				// }
				// Select the cell in the table.
				final int r = row, c = col;
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						changeSelection(r, c, false, false);
					}
				});
				if (isCellEditable(row, col)) {
					editCellAt(row, col);
					editorComp.requestFocusInWindow();
					((JTextField) editorComp).selectAll();
				}
			}
		}
	}

}
