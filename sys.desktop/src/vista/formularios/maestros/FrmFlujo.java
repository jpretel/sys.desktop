package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import core.dao.FlujoDAO;
import core.entity.Flujo;

public class FrmFlujo extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private Flujo flujo;

	private FlujoDAO flujoDAO = new FlujoDAO();

	private List<Flujo> flujos = new ArrayList<Flujo>();

	public Flujo getFlujo() {
		return flujo;
	}

	public void setFlujo(Flujo flujo) {
		this.flujo = flujo;
	}

	public FlujoDAO getFlujoDAO() {
		return flujoDAO;
	}

	public void setFlujoDAO(FlujoDAO flujoDAO) {
		this.flujoDAO = flujoDAO;
	}

	public List<Flujo> getFlujos() {
		return flujos;
	}

	public void setFlujos(List<Flujo> flujos) {
		this.flujos = flujos;
	}

	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	public FrmFlujo() {
		super("Flujos de Aprobación");

		getBarra().setFormMaestro(this);

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(227, 11, 46, 14);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(276, 8, 122, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(227, 36, 75, 14);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 207, 273);

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(286, 33, 122, 20);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));
		
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 226,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblCdigo)
														.addComponent(
																lblDescripcin))
										.addGap(5)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																txtDescripcion,
																GroupLayout.DEFAULT_SIZE,
																142,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtCodigo,
																				GroupLayout.DEFAULT_SIZE,
																				98,
																				Short.MAX_VALUE)
																		.addGap(44)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(26)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblCdigo)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												txtCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																txtDescripcion,
																																GroupLayout.PREFERRED_SIZE,
																																22,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblDescripcin)))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				370,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setFlujo(getFlujos().get(selectedRow));
						else
							setFlujo(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setFlujo(new Flujo());
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		getFlujoDAO().crear_editar(getFlujo());
	}

	@Override
	public void llenarDesdeVista() {
		getFlujo().setIdflujo(txtCodigo.getText());
		getFlujo().setDescripcion(txtDescripcion.getText());
	};

	@Override
	public void eliminar() {
		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getFlujo() != null) {
			txtCodigo.setText(getFlujo().getIdflujo());
			txtDescripcion.setText(getFlujo().getDescripcion());
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Flujo obj : getFlujos()) {
			model.addRow(new Object[] { obj.getIdflujo(), obj.getDescripcion() });
		}
		if (getFlujos().size() > 0) {
			setFlujo(getFlujos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setFlujos(getFlujoDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		tblLista.setEnabled(true);
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {
		if (txtCodigo.getText().trim().isEmpty())
			return false;
		if (txtDescripcion.getText().trim().isEmpty())
			return false;
		return true;
	}

}
