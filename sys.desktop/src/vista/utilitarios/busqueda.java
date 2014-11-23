package vista.utilitarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import vista.contenedores.AbstractCntBuscar;

public class busqueda extends modalInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private Object obj;
	
	public busqueda(int i) {
		// TODO Auto-generated constructor stub
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public busqueda(final AbstractCntBuscar cntBuscar, String lista[][]) {
		setClosable(true);
		getContentPane().setLayout(null);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 11, 87, 20);
		getContentPane().add(comboBox);

		comboBox.addItem("Codigo");
		comboBox.addItem("Descripcion");

		textField = new JTextField();
		textField.setBounds(96, 11, 200, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(360, 10, 69, 23);
		getContentPane().add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, e.getClickCount());

			}
		});
		scrollPane.setBounds(10, 43, 414, 197);
		getContentPane().add(scrollPane);

		final DefaultTableModel modelo = new MaestroTableModel();
		final JTable tblLista = new JTable(modelo);
		tblLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (tblLista.getSelectedRow() >= 0) {
						cntBuscar.txtCodigo.setText(modelo.getValueAt(
								tblLista.getSelectedRow(), 0).toString());
						cntBuscar.txtDescripcion.setText(modelo.getValueAt(
								tblLista.getSelectedRow(), 1).toString());
						dispose();
					} else {
						JOptionPane.showMessageDialog(null,
								"Seleccione un Registro");
					}
				}
			}
		});
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnAcptar = new JButton("Acptar");
		btnAcptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tblLista.getSelectedRow() >= 0) {
					cntBuscar.txtCodigo.setText(modelo.getValueAt(
							tblLista.getSelectedRow(), 0).toString());
					cntBuscar.txtDescripcion.setText(modelo.getValueAt(
							tblLista.getSelectedRow(), 1).toString());
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Seleccione un Registro");
				}

			}
		});
		btnAcptar.setBounds(355, 251, 69, 23);
		getContentPane().add(btnAcptar);

		int nFila = lista.length;
		for (int i = 0; i < nFila; i++) {
			modelo.addRow(new Object[] { lista[i][0].toString(),
					lista[i][1].toString() });
		}
	}
}
