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
import vista.utilitarios.FormValidador;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.AreaDAO;
import core.entity.Area;

public class FrmAreas extends AbstractMaestro {

	private static final long serialVersionUID = 1L;
	String bkEntidad = null;
	private Area area;

	private AreaDAO areaDAO = new AreaDAO();

	private List<Area> areaL = new ArrayList<Area>();

	public List<Area> getAreaL() {
		return areaL;
	}

	public void setAreaL(List<Area> areaL) {
		this.areaL = areaL;
	}

	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	public FrmAreas() {
		super("Areas");

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(227, 11, 46, 14);

		txtCodigo = new JTextField();
		this.txtCodigo.setName("C\u00F3digo");
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
		this.txtDescripcion.setName("Descripci\u00F3n");
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
							setArea(getAreaL().get(selectedRow));
						else
							setArea(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setArea(new Area());
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		
		getArea().setIdarea(txtCodigo.getText());
		getArea().setDescripcion(txtDescripcion.getText());

		getAreaDAO().crear_editar(getArea());
	}

	@Override
	public void eliminar() {
		if (getArea() != null) {

			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			if (seleccion == 0) {
				areaDAO.remove(getArea());
				iniciar();
			}
		}

		setEstado(VISTA);
		vista_noedicion();
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getArea() != null) {
			txtCodigo.setText(getArea().getIdarea());
			txtDescripcion.setText(getArea().getDescripcion());
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Area area : getAreaL()) {
			model.addRow(new Object[] { area.getIdarea(), area.getDescripcion() });
		}
		if (getAreaL().size() > 0) {
			setArea(getAreaL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setAreaL(getAreaDAO().findAll());
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
		txtDescripcion.setText("");
		txtCodigo.setText("");
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenarDesdeVista() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {

		if (!FormValidador.TextFieldObligatorios(txtCodigo, txtDescripcion))
			return false;
		
		if (getEstado().equals(NUEVO)) {
			if (areaDAO.find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		return true;
	}

	@Override
	public void limpiarDetalle() {
		// TODO Auto-generated method stub

	}

}
