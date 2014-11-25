package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import vista.controles.DSGTableModel;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.TableTextEditor;
import core.dao.AlmacenDAO;
import core.dao.SucursalDAO;
import core.entity.Almacen;
import core.entity.AlmacenPK;
import core.entity.Sucursal;

public class FrmSucursal extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tblLista;

	private JTable tblAlmacenes;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	private Sucursal sucursal;

	private List<Sucursal> sucursales;
	private List<Almacen> almacenes;

	private SucursalDAO sucursalDAO = new SucursalDAO();

	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private JTextField txtDescCorta;
	private JScrollPane scrollPaneNum;

	public FrmSucursal() {
		super("Sucursal / Almacen");

		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblAlmacenes = new JTable(new DSGTableModel(new String[] {
				"Cod. Almacen", "Descripción", "Desc. Corta" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", "" });
			}
		});

		tblAlmacenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneNum = new JScrollPane(tblAlmacenes);

		getAlmacenesTM().setNombre_detalle("Almacenes");
		getAlmacenesTM().setObligatorios(0, 1, 2);
		getAlmacenesTM().setRepetidos(0);
		getAlmacenesTM().setScrollAndTable(scrollPaneNum, tblAlmacenes);
		
		TableColumnModel cModel = tblAlmacenes.getColumnModel();
		cModel.getColumn(0).setCellEditor(new TableTextEditor(3, true));
		
		JLabel lblCdigo = new JLabel("Código");

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(4, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		JLabel lblDescCorta = new JLabel("Desc. Corta");

		txtDescCorta = new JTextField();
		txtDescCorta.setColumns(10);
		txtDescCorta.setDocument(new JTextFieldLimit(50, true));
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(10)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 199,
												Short.MAX_VALUE)
										.addGap(4)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblCdigo,
																				GroupLayout.PREFERRED_SIZE,
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(19)
																		.addComponent(
																				txtCodigo,
																				GroupLayout.PREFERRED_SIZE,
																				67,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblDescripcin,
																				GroupLayout.PREFERRED_SIZE,
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(19)
																		.addComponent(
																				txtDescripcion,
																				GroupLayout.PREFERRED_SIZE,
																				184,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblDescCorta,
																				GroupLayout.PREFERRED_SIZE,
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(19)
																		.addComponent(
																				txtDescCorta,
																				GroupLayout.PREFERRED_SIZE,
																				96,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(2)
																		.addComponent(
																				scrollPaneNum,
																				GroupLayout.DEFAULT_SIZE,
																				314,
																				Short.MAX_VALUE)))
										.addGap(10)));
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
																		.addGap(11)
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				274,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(15)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(3)
																										.addComponent(
																												lblCdigo))
																						.addComponent(
																								txtCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(4)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(3)
																										.addComponent(
																												lblDescripcin))
																						.addComponent(
																								txtDescripcion,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(5)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(3)
																										.addComponent(
																												lblDescCorta))
																						.addComponent(
																								txtDescCorta,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(8)
																		.addComponent(
																				scrollPaneNum,
																				GroupLayout.DEFAULT_SIZE,
																				193,
																				Short.MAX_VALUE)))
										.addGap(11)));
		pnlContenido.setLayout(groupLayout);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setSucursal(getSucursales().get(selectedRow));
						else
							setSucursal(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setSucursal(new Sucursal());
		txtCodigo.requestFocus();
	}

	@Override
	public void anular() {

	}

	@Override
	public void grabar() {
		sucursalDAO.crear_editar(getSucursal());

		for (Almacen obj : almacenDAO.aEliminar(getSucursal(), getAlmacenes())) {
			almacenDAO.remove(obj);
		}
		System.out.println("Crear-editar");
		for (Almacen obj : getAlmacenes()) {
			almacenDAO.crear_editar(obj);
		}
	}

	@Override
	public void llenarDesdeVista() {
		getSucursal().setIdsucursal(this.txtCodigo.getText().trim());
		getSucursal().setDescripcion(this.txtDescripcion.getText().trim());
		getSucursal().setNombre_corto(this.txtDescCorta.getText().trim());

		setAlmacenes(new ArrayList<Almacen>());

		for (int i = 0; i < getAlmacenesTM().getRowCount(); i++) {
			AlmacenPK id = new AlmacenPK();
			Almacen alm = new Almacen();

			id.setIdsucursal(getSucursal().getIdsucursal());
			id.setIdalmacen(this.getAlmacenesTM().getValueAt(i, 0).toString());
			
			alm.setId(id);
			alm.setDescripcion(this.getAlmacenesTM().getValueAt(i, 1)
					.toString());
			alm.setNombre_corto(this.getAlmacenesTM().getValueAt(i, 2)
					.toString());
			getAlmacenes().add(alm);
		}
	}

	@Override
	public void llenar_datos() {
		getAlmacenesTM().limpiar();
		setAlmacenes(new ArrayList<Almacen>());
		if (getSucursal() != null) {
			txtCodigo.setText(getSucursal().getIdsucursal());
			txtDescripcion.setText(getSucursal().getDescripcion());
			txtDescCorta.setText(getSucursal().getNombre_corto());
			setAlmacenes(almacenDAO.getPorSucursal(getSucursal()));

			for (Almacen alm : getAlmacenes()) {
				getAlmacenesTM().addRow(
						new Object[] { alm.getId().getIdalmacen(),
								alm.getDescripcion(), alm.getNombre_corto() });
			}

		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtDescCorta.setText("");
		}
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (sucursalDAO.find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}
		if (this.txtDescripcion.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Descripción");
			this.txtDescripcion.requestFocus();
			return false;
		}
		if (this.txtDescCorta.getText().trim().isEmpty()) {

			this.txtDescCorta.requestFocus();
			return false;
		}

		if (!validarDetalles()) {
			return false;
		}
		return true;
	}

	private boolean validarDetalles() {
		return getAlmacenesTM().esValido();
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Sucursal obj : getSucursales()) {
			model.addRow(new Object[] { obj.getIdsucursal(),
					obj.getDescripcion() });
		}
		if (getSucursales().size() > 0) {
			setSucursal(getSucursales().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setSucursales(sucursalDAO.findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		else
			txtCodigo.setEditable(false);
		txtDescripcion.setEditable(true);
		txtDescCorta.setEditable(true);
		getAlmacenesTM().setEditar(true);

	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtDescCorta.setEditable(false);
		getAlmacenesTM().setEditar(false);
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public SucursalDAO getSucursalDAO() {
		return sucursalDAO;
	}

	public void setSucursalDAO(SucursalDAO sucursalDAO) {
		this.sucursalDAO = sucursalDAO;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	public DSGTableModel getAlmacenesTM() {
		return ((DSGTableModel) tblAlmacenes.getModel());
	}

	public List<Almacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(List<Almacen> almacenes) {
		this.almacenes = almacenes;
	}

	public AlmacenDAO getAlmacenDAO() {
		return almacenDAO;
	}

	public void setAlmacenDAO(AlmacenDAO almacenDAO) {
		this.almacenDAO = almacenDAO;
	}

	@Override
	public void eliminar() {
		if (getSucursal() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getAlmacenDAO().borrarPorSucursal(getSucursal());
				getSucursalDAO().remove(getSucursal());

			}
		}
	}
}