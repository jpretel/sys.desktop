package vista.formularios.modal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vista.controles.DSGTableModel;
import vista.controles.ModalInternalFrame;

public class ModalDetalleReferencia extends ModalInternalFrame {
	Object[][] data;
	public DSGTableModel model;
	public ModalDetalleReferencia(JInternalFrame parent,
			final DSGTableModel modelo, Object[][] data) {
		super(parent);
		this.model = modelo;
		setSize(new Dimension(570, 350));
		setPreferredSize(new Dimension(200, 200));
		setTitle("Detalle de Referencia");

		this.pnlNorte = new JPanel();
		this.pnlNorte.setPreferredSize(new Dimension(10, 40));
		this.pnlNorte.setMinimumSize(new Dimension(70, 10));
		getContentPane().add(this.pnlNorte, BorderLayout.NORTH);
		this.pnlNorte.setLayout(null);

		this.lblDetalleDe = new JLabel("Detalle de:");
		this.lblDetalleDe.setBounds(10, 11, 52, 14);
		this.pnlNorte.add(this.lblDetalleDe);

		this.pnlSur = new JPanel();
		this.pnlSur.setPreferredSize(new Dimension(10, 40));
		this.pnlSur.setMinimumSize(new Dimension(70, 10));
		getContentPane().add(this.pnlSur, BorderLayout.SOUTH);
		this.pnlSur.setLayout(null);

		this.btnCancelar = new JButton("Cancelar");
		this.btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model = null;
				dispose();
			}
		});

		this.btnCancelar.setBounds(451, 11, 89, 23);
		this.pnlSur.add(this.btnCancelar);

		this.btnAceptar = new JButton("Aceptar");
		this.btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validaModelo()) {
					dispose();
				}
			}
		});

		this.btnAceptar.setBounds(338, 11, 89, 23);
		this.pnlSur.add(this.btnAceptar);

		this.scrlDetalle = new JScrollPane();
		getContentPane().add(this.scrlDetalle, BorderLayout.CENTER);

		this.table = new JTable(model);
		this.scrlDetalle.setViewportView(this.table);

		for (Object[] o : data) {
			model.addRow(o);
		}

	}

	public boolean validaModelo() {
		return true;
	}

	private static final long serialVersionUID = 1L;
	private JPanel pnlNorte;
	private JPanel pnlSur;
	private JScrollPane scrlDetalle;
	private JTable table;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JLabel lblDetalleDe;

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}
