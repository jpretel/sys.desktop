package vista.formularios.documentos;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import vista.contenedores.CntMoneda;
import vista.contenedores.cntAlmacen;
import vista.contenedores.cntResponsable;
import vista.contenedores.cntSucursal;
import vista.controles.DSGTableModel;
import vista.controles.DSGTextFieldNumber;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.abstractforms.AbstractDocForm;
import vista.utilitarios.FormValidador;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.dao.AlmacenDAO;
import core.dao.DOrdenServicioDAO;
import core.dao.MonedaDAO;
import core.dao.OrdenServicioDAO;
import core.dao.ProductoDAO;
import core.dao.ResponsableDAO;
import core.dao.SucursalDAO;
import core.dao.UnimedidaDAO;
import core.entity.DOrdenServicio;
import core.entity.OrdenServicio;
import core.entity.Producto;
import core.entity.Sucursal;
import core.entity.Unimedida;

public class FrmDocOrdenServicio extends AbstractDocForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private List<DetDocingreso> DetDocingresoL;
	private OrdenServicioDAO ordenDAO = new OrdenServicioDAO();
	private DOrdenServicioDAO dordenservicioDAO = new DOrdenServicioDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();

	private TxtProducto txtProducto;
	private JLabel lblResponsable;
	private JLabel lblSucursal;
	private JLabel lblAlmacen;
	private JLabel lblGlosa;
	private JScrollPane scrollPaneDetalle;
	private cntResponsable cntResponsable;
	private cntSucursal cntSucursal;
	private cntAlmacen cntAlmacen;
	private JScrollPane scrlGlosa;
	private JTextArea txtGlosa;
	private JTable tblDetalle;

	private OrdenServicio ordenservicio;
	private List<DOrdenServicio> dordenservicios = new ArrayList<DOrdenServicio>();
	private CntMoneda cntMoneda;
	private DSGTextFieldNumber txtTCambio;
	private DSGTextFieldNumber txtTCMoneda;

	public FrmDocOrdenServicio() {
		super("Orden de Servicio");

		setEstado(VISTA);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 874, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 681, Short.MAX_VALUE));

		this.lblResponsable = new JLabel("Responsable");
		this.lblResponsable.setBounds(10, 106, 74, 16);
		pnlPrincipal.add(this.lblResponsable);

		this.lblSucursal = new JLabel("Sucursal");
		this.lblSucursal.setBounds(10, 43, 51, 16);
		pnlPrincipal.add(this.lblSucursal);

		this.lblAlmacen = new JLabel("Almacen");
		this.lblAlmacen.setBounds(10, 70, 50, 16);
		pnlPrincipal.add(this.lblAlmacen);

		this.lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(399, 43, 32, 16);
		pnlPrincipal.add(this.lblGlosa);

		this.scrollPaneDetalle = new JScrollPane((Component) null);
		this.scrollPaneDetalle.setBounds(10, 133, 824, 243);
		pnlPrincipal.add(this.scrollPaneDetalle);

		tblDetalle = new JTable(new DSGTableModel(new String[] {
				"Cód. Producto", "Producto", "Cod. Medida", "Medida",
				"Cantidad", "P. Unit.", "Total" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 2 || column == 3 || column == 8
						|| column == 10)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				if (validaCabecera())
					addRow(new Object[] { "", "", "", "", 0.00, 0.00, 0.00 });
				else
					JOptionPane.showMessageDialog(null,
							"Faltan datos en la cabecera");
			}
		}) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					String idproducto = getDetalleTM().getValueAt(row, 0)
							.toString();

					txtProducto.refresValue(idproducto);
					actualiza_detalle();
				}
			}
		};

		txtProducto = new TxtProducto(tblDetalle, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Producto entity) {

				int row = tblDetalle.getSelectedRow(), col = 0;
				if (entity == null) {
					getDetalleTM().setValueAt("", row, 0);
					getDetalleTM().setValueAt("", row, 1);
					getDetalleTM().setValueAt("", row, 2);
					getDetalleTM().setValueAt("", row, 3);
				} else {

					setText(entity.getIdproducto());
					getDetalleTM().setValueAt(entity.getIdproducto(), row, col);
					getDetalleTM().setValueAt(entity.getDescripcion(), row,
							col + 1);
					getDetalleTM().setValueAt(
							entity.getUnimedida().getIdunimedida(), row,
							col + 2);
					getDetalleTM().setValueAt(
							entity.getUnimedida().getDescripcion(), row,
							col + 3);

				}
				setSeleccionado(null);
			}
		};

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.getServicios());
		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setRepetidos(0);
		getDetalleTM().setScrollAndTable(scrollPaneDetalle, tblDetalle);

		this.tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPaneDetalle.setViewportView(this.tblDetalle);

		this.cntResponsable = new cntResponsable();

		this.cntResponsable.setBounds(72, 102, 309, 20);
		pnlPrincipal.add(this.cntResponsable);

		this.cntSucursal = new cntSucursal();

		this.cntSucursal.setBounds(72, 43, 309, 20);
		pnlPrincipal.add(this.cntSucursal);

		this.cntAlmacen = new cntAlmacen();
		cntAlmacen.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				cntAlmacen.setData(almacenDAO.getPorSucursal(cntSucursal
						.getSeleccionado()));
			}
		});

		this.cntAlmacen.setBounds(72, 70, 309, 20);
		pnlPrincipal.add(this.cntAlmacen);

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(436, 43, 395, 79);
		pnlPrincipal.add(this.scrlGlosa);

		this.txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);

		this.cntMoneda = new CntMoneda();
		this.cntMoneda.setBounds(389, 12, 192, 20);
		pnlPrincipal.add(this.cntMoneda);

		this.txtTCambio = new DSGTextFieldNumber(4);
		this.txtTCambio.setBounds(603, 12, 65, 20);
		pnlPrincipal.add(this.txtTCambio);

		this.txtTCMoneda = new DSGTextFieldNumber(4);
		this.txtTCMoneda.setBounds(704, 12, 65, 20);
		pnlPrincipal.add(this.txtTCMoneda);

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				actualizaNumero(txtSerie.getText());
			}

			private void actualizaNumero(String serie) {
				int numero = ordenDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero.setValue(numero);
					txtFecha.requestFocus();
				}
			}
		});
		
		
		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setRepetidos(0);

		TableColumnModel tc = tblDetalle.getColumnModel();

		tc.getColumn(4).setCellEditor(new FloatEditor(3));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(3));

		tc.getColumn(5).setCellEditor(new FloatEditor(2));
		tc.getColumn(5).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(6).setCellEditor(new FloatEditor(2));
		tc.getColumn(6).setCellRenderer(new FloatRenderer(2));

		iniciar();
	}

	@Override
	public void nuevo() {
		setOrdenservicio(new OrdenServicio());
		ordenservicio.setIdordenservicio(System.nanoTime());
		txtSerie.requestFocus();
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		ordenDAO.crear_editar(ordenservicio);
		dordenservicioDAO.borrarPorOrdenServicio(ordenservicio);
		dordenservicioDAO.create(dordenservicios);
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		
		if (ordenservicio != null) {
			txtNumero.setValue(ordenservicio.getNumero());
			txtSerie.setText(ordenservicio.getSerie());
			txtTCambio.setValue(ordenservicio.getTcambio());
			txtTCMoneda.setValue(ordenservicio.getTcmoneda());
			txtGlosa.setText(ordenservicio.getGlosa());
			cntMoneda.txtCodigo
					.setText((ordenservicio.getMoneda() == null) ? ""
							: ordenservicio.getMoneda().getIdmoneda());
			cntMoneda.llenar();
			cntResponsable.txtCodigo.setText((ordenservicio
					.getResponsable() == null) ? "" : ordenservicio
					.getResponsable().getIdresponsable());
			cntResponsable.llenar();
			cntSucursal.txtCodigo
					.setText((ordenservicio.getSucursal() == null) ? ""
							: ordenservicio.getSucursal().getIdsucursal());
			Sucursal s = ordenservicio.getSucursal();
			cntSucursal.llenar();
			if (s == null) {
				cntAlmacen.setData(null);
			} else {
				cntAlmacen.setData(almacenDAO.getPorSucursal(s));
			}
			cntAlmacen.txtCodigo
					.setText((ordenservicio.getAlmacen() == null) ? ""
							: ordenservicio.getAlmacen().getId()
									.getIdalmacen());
			cntAlmacen.llenar();

			dordenservicios = dordenservicioDAO
					.getPorOrdenServicio(ordenservicio);

			for (DOrdenServicio d : dordenservicios) {
				Producto p = d.getProducto();
				Unimedida u = d.getUnimedida();

				getDetalleTM().addRow(
						new Object[] { p.getIdproducto(), p.getDescripcion(),
								u.getIdunimedida(), u.getDescripcion(),
								d.getCantidad(), d.getPrecio_unitario(),
								d.getImporte() });
			}

			// List<DetDocingreso> detDocIngresoL =
			// detDocingresoDAO.getPorIdIngreso(Long.parseLong(getIngreso().getIddocingreso()));
			// for(DetDocingreso ingreso : detDocIngresoL){
			// getDetalleTM().addRow(new
			// Object[]{ingreso.getId().getIdproducto(),ingreso.getDescripcion(),ingreso.getIdmedida(),"",ingreso.getCantidad(),ingreso.getPrecio(),ingreso.getPrecio()});
			// }
		} else {
			dordenservicios = new ArrayList<DOrdenServicio>();
		}
	}

	private void actualiza_detalle() {
		int row = tblDetalle.getSelectedRow();
		if (row > -1) {
			float cantidad, pu, importe;
			cantidad = Float.parseFloat(getDetalleTM().getValueAt(row, 4)
					.toString());
			pu = Float.parseFloat(getDetalleTM().getValueAt(row, 5).toString());

			importe = cantidad * pu;

			getDetalleTM().setValueAt(importe, row, 6);
		}
	}

	@Override
	public void llenar_tablas() {
		cntMoneda.setData(new MonedaDAO().findAll());
		cntSucursal.setData(new SucursalDAO().findAll());
		cntResponsable.setData(new ResponsableDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		this.txtSerie.setEditable(true);
		this.txtNumero.setEditable(true);
		this.txtFecha.setEditable(true);
		this.txtTCMoneda.setEditable(true);
		this.txtTCambio.setEditable(true);
		this.txtGlosa.setEditable(true);
		FormValidador.CntEdicion(true, this.cntMoneda, this.cntResponsable,
				this.cntAlmacen, this.cntSucursal);
		getDetalleTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		this.txtSerie.setEditable(false);
		this.txtNumero.setEditable(false);
		this.txtFecha.setEditable(false);
		this.txtTCMoneda.setEditable(false);
		this.txtTCambio.setEditable(false);
		this.txtGlosa.setEditable(false);
		FormValidador.CntEdicion(false, this.cntMoneda, this.cntResponsable,
				this.cntAlmacen, this.cntSucursal);
		getDetalleTM().setEditar(false);
	}

	@Override
	public void actualiza_objeto(Object id) {
		setOrdenservicio(ordenDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		Calendar c = Calendar.getInstance();
		c.setTime(txtFecha.getDate());
		ordenservicio.setSerie(this.txtSerie.getText());
		ordenservicio.setNumero(
				Integer.parseInt(this.txtNumero.getText()));
		ordenservicio.setMoneda(cntMoneda.getSeleccionado());
		ordenservicio
				.setResponsable(this.cntResponsable.getSeleccionado());
		ordenservicio.setSucursal(cntSucursal.getSeleccionado());
		ordenservicio.setAlmacen(this.cntAlmacen.getSeleccionado());
		ordenservicio.setDia(c.get(Calendar.DAY_OF_MONTH));
		ordenservicio.setMes(c.get(Calendar.MONTH) + 1);
		ordenservicio.setAnio(c.get(Calendar.YEAR));
		ordenservicio.setAniomesdia(
				(c.get(Calendar.YEAR) * 10000)
						+ ((c.get(Calendar.MONTH) + 1) * 100)
						+ c.get(Calendar.DAY_OF_MONTH));
		ordenservicio.setGlosa(txtGlosa.getText());
		ordenservicio.setTcambio(Float.parseFloat(txtTCambio.getText()));
		ordenservicio.setTcmoneda(Float.parseFloat(txtTCMoneda.getText()));
		dordenservicios = new ArrayList<DOrdenServicio>();

		int rows = getDetalleTM().getRowCount();

		for (int row = 0; row < rows; row++) {
			DOrdenServicio d = new DOrdenServicio();
			
			String idproducto, idunimedida;

			idproducto = getDetalleTM().getValueAt(row, 0).toString();
			idunimedida = getDetalleTM().getValueAt(row, 2).toString();

			float cantidad, precio_unitario, importe;

			cantidad = Float.parseFloat(getDetalleTM().getValueAt(row, 4)
					.toString());
			precio_unitario = Float.parseFloat(getDetalleTM()
					.getValueAt(row, 5).toString());

			importe = cantidad * precio_unitario;

			Producto p = productoDAO.find(idproducto);
			Unimedida u = unimedidaDAO.find(idunimedida);
			
			d.setProducto(p);
			d.setOrdenservicio(ordenservicio);
			d.setUnimedida(u);
			d.setCantidad(cantidad);
			d.setPrecio_unitario(precio_unitario);
			d.setImporte(importe);

			dordenservicios.add(d);
		}
	}

	@Override
	public boolean isValidaVista() {
		boolean band = validaCabecera();
		return band;
	}

	public boolean validaCabecera() {
		
		if (!FormValidador.CntObligatorios(cntMoneda, cntResponsable,
				cntSucursal, cntAlmacen))
			return false;

		return true;
	}
	
	@Override
	protected void limpiarVista() {
		txtNumero.setValue(0);
		txtSerie.setText("");
		txtTCambio.setValue(0);
		txtTCMoneda.setValue(1);
		txtGlosa.setText("");
		cntMoneda.txtCodigo
				.setText("");
		cntMoneda.llenar();
		cntResponsable.setText("");
		cntResponsable.llenar();
		cntSucursal
				.setText("");
		cntSucursal.llenar();
		cntAlmacen.setData(null);
		
		cntAlmacen.setText("");
		cntAlmacen.llenar();
		
		getDetalleTM().limpiar();
	}
	
	public DSGTableModel getDetalleTM() {
		return ((DSGTableModel) tblDetalle.getModel());
	}

	public OrdenServicio getOrdenservicio() {
		return ordenservicio;
	}

	public void setOrdenservicio(OrdenServicio ordenservicio) {
		this.ordenservicio = ordenservicio;
	}
}
