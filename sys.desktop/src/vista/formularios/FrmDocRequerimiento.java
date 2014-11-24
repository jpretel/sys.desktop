package vista.formularios;

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

import vista.contenedores.cntAlmacen;
import vista.contenedores.cntResponsable;
import vista.contenedores.cntSucursal;
import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtConsumidor;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.listas.AbstractDocForm;
import vista.utilitarios.FormValidador;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.centralizacion.ContabilizaRequerimiento;
import core.dao.AlmacenDAO;
import core.dao.ConsumidorDAO;
import core.dao.DRequerimientoDAO;
import core.dao.FlujoAprobacionDAO;
import core.dao.KardexRequerimientoDAO;
import core.dao.ProductoDAO;
import core.dao.RequerimientoDAO;
import core.dao.ResponsableDAO;
import core.dao.SucursalDAO;
import core.dao.SysFormularioDAO;
import core.dao.UnimedidaDAO;
import core.entity.Consumidor;
import core.entity.DRequerimiento;
import core.entity.DRequerimientoPK;
import core.entity.Flujo;
import core.entity.Producto;
import core.entity.Requerimiento;
import core.entity.Sucursal;
import core.entity.SysFormulario;
import core.entity.Unimedida;
import vista.controles.DSGButtonFlujo;

public class FrmDocRequerimiento extends AbstractDocForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RequerimientoDAO requerimientoDAO = new RequerimientoDAO();
	private DRequerimientoDAO drequerimientoDAO = new DRequerimientoDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private ConsumidorDAO consumidorDAO = new ConsumidorDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private SucursalDAO sucursalDAO = new SucursalDAO();
	private ResponsableDAO responsableDAO = new ResponsableDAO();
	private KardexRequerimientoDAO kardexReqDAO = new KardexRequerimientoDAO();

	private TxtProducto txtProducto;
	private TxtConsumidor txtConsumidor;
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
	
	private FlujoAprobacionDAO flujoDAO = new FlujoAprobacionDAO();

	private Requerimiento requerimiento;
	private List<DRequerimiento> drequerimiento = new ArrayList<DRequerimiento>();
	private DSGButtonFlujo btnFlujo;
	
	private SysFormulario sysFormulario;
	private SysFormularioDAO sysFormularioDAO = new SysFormularioDAO();
	
	public FrmDocRequerimiento() {
		super("Requerimiento");
		
		sysFormulario = sysFormularioDAO.getPorOpcion("FrmListaRequerimiento");
		
		txtFecha.setBounds(245, 11, 101, 22);
		txtNumero.setBounds(116, 12, 80, 20);
		txtSerie.setBounds(72, 12, 44, 20);

		setEstado(VISTA);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 874, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 681, Short.MAX_VALUE));

		this.lblResponsable = new JLabel("Responsable");
		this.lblResponsable.setBounds(10, 106, 74, 16);

		this.lblSucursal = new JLabel("Sucursal");
		this.lblSucursal.setBounds(10, 43, 51, 16);

		this.lblAlmacen = new JLabel("Almacen");
		this.lblAlmacen.setBounds(10, 70, 50, 16);

		this.lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(399, 43, 32, 16);

		this.scrollPaneDetalle = new JScrollPane((Component) null);
		this.scrollPaneDetalle.setBounds(10, 133, 824, 243);

		tblDetalle = new JTable(new DSGTableModel(new String[] {
				"Cód. Producto", "Producto", "Cod. Medida", "Medida",
				"Cantidad", "Cód. Consumidor", "Consumidor" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 2 || column == 3 || column == 6)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				if (validaCabecera())
					addRow(new Object[] { "", "", "", "", 0.0, "", "" });
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
					String idconsumidor;
					try {
						idconsumidor = getDetalleTM().getValueAt(row, 5)
								.toString();
					} catch (Exception e) {
						idconsumidor = "";
					}
					txtProducto.refresValue(idproducto);
					txtConsumidor.refresValue(idconsumidor);
				}
			}
		};

		txtProducto = new TxtProducto(tblDetalle, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Producto entity) {

				int row = tblDetalle.getSelectedRow();

				if (entity == null) {
					getDetalleTM().setValueAt("", row, 0);
					getDetalleTM().setValueAt("", row, 1);
					getDetalleTM().setValueAt("", row, 2);
					getDetalleTM().setValueAt("", row, 3);

				} else {

					setText(entity.getIdproducto());
					getDetalleTM().setValueAt(entity.getIdproducto(), row, 0);
					getDetalleTM().setValueAt(entity.getDescripcion(), row, 1);
					getDetalleTM().setValueAt(
							entity.getUnimedida().getIdunimedida(), row, 2);
					getDetalleTM().setValueAt(
							entity.getUnimedida().getDescripcion(), row, 3);

				}
				setSeleccionado(null);
			}
		};

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.findAll());

		txtConsumidor = new TxtConsumidor(tblDetalle, 5) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Consumidor entity) {
				int row = tblDetalle.getSelectedRow();

				if (entity == null) {
					getDetalleTM().setValueAt("", row, 5);
					getDetalleTM().setValueAt("", row, 6);

				} else {

					setText(entity.getIdconsumidor());
					getDetalleTM().setValueAt(entity.getIdconsumidor(), row, 5);
					getDetalleTM().setValueAt(entity.getDescripcion(), row, 6);
				}
				setSeleccionado(null);
			}
		};
		txtConsumidor.updateCellEditor();
		txtConsumidor.setData(consumidorDAO.findAll());

		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setRepetidos(0);
		getDetalleTM().setScrollAndTable(scrollPaneDetalle, tblDetalle);

		this.tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPaneDetalle.setViewportView(this.tblDetalle);

		this.cntResponsable = new cntResponsable();
		this.cntResponsable.setBounds(72, 102, 309, 20);

		this.cntSucursal = new cntSucursal();
		this.cntSucursal.setBounds(72, 43, 309, 20);

		this.cntAlmacen = new cntAlmacen();
		this.cntAlmacen.setBounds(72, 70, 309, 20);
		cntAlmacen.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				cntAlmacen.setData(almacenDAO.getPorSucursal(cntSucursal
						.getSeleccionado()));
			}
		});

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(436, 43, 395, 79);

		this.txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);
		pnlPrincipal.setLayout(null);
		pnlPrincipal.add(this.lblSucursal);
		pnlPrincipal.add(this.cntSucursal);
		pnlPrincipal.add(this.lblAlmacen);
		pnlPrincipal.add(this.cntAlmacen);
		pnlPrincipal.add(this.lblResponsable);
		pnlPrincipal.add(this.cntResponsable);
		pnlPrincipal.add(this.lblGlosa);
		pnlPrincipal.add(this.scrlGlosa);
		pnlPrincipal.add(this.scrollPaneDetalle);

		this.btnFlujo = new DSGButtonFlujo() {
			private static final long serialVersionUID = 1L;
			@Override
			public Flujo getFlujoAnterior() {
				return flujoDAO.getEstadoAnterior(sysFormulario, requerimiento.getFlujo());
			}
			
			@Override
			public Flujo getFlujoSiguiente() {
				return flujoDAO.getEstadoSiguiente(sysFormulario, requerimiento.getFlujo());
			}
			
			@Override
			public void actualizaEstado(Flujo flujo) {
				requerimiento.setFlujo(flujo);
				DoGrabar();
			}
		};
		this.btnFlujo.setBounds(709, 11, 122, 24);
		pnlPrincipal.add(this.btnFlujo);

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.findAll());

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				actualizaNumero(txtSerie.getText());
			}

			private void actualizaNumero(String serie) {
				int numero = requerimientoDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero.setValue(numero);
					txtFecha.requestFocus();
				}
			}
		});

		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setObligatorios(0, 1, 2, 3, 4);
		getDetalleTM().setRepetidos(0);

		TableColumnModel tc = tblDetalle.getColumnModel();

		tc.getColumn(4).setCellEditor(new FloatEditor(2));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(2));

		iniciar();
	}

	@Override
	public void nuevo() {
		Calendar c = Calendar.getInstance();

		setRequerimiento(new Requerimiento());
		getRequerimiento().setIdrequerimiento(System.nanoTime());
		requerimiento.setAnio(c.get(Calendar.YEAR));
		requerimiento.setMes(c.get(Calendar.MONTH) + 1);
		requerimiento.setDia(c.get(Calendar.DAY_OF_MONTH));
		txtSerie.requestFocus();
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		kardexReqDAO.borrarPorRequerimiento(getRequerimiento());
		requerimientoDAO.crear_editar(getRequerimiento());

		for (DRequerimiento d : drequerimientoDAO.aEliminar(getRequerimiento(),
				drequerimiento)) {
			drequerimientoDAO.remove(d);
		}

		for (DRequerimiento d : drequerimiento) {
			if (drequerimientoDAO.find(d.getId()) == null) {
				drequerimientoDAO.create(d);
			} else {
				drequerimientoDAO.edit(d);
			}
		}
		ContabilizaRequerimiento.ContabilizarRequerimiento(requerimiento
				.getIdrequerimiento());
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_datos() {
		limpiarVista();

		if (getRequerimiento() != null) {
			this.txtNumero.setValue(getRequerimiento().getNumero());
			this.txtSerie.setText(getRequerimiento().getSerie());
			btnFlujo.setFlujo(requerimiento.getFlujo());
			
			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.YEAR, getRequerimiento().getAnio());

			cal.set(Calendar.MONTH, getRequerimiento().getMes() - 1);

			cal.set(Calendar.DAY_OF_MONTH, getRequerimiento().getDia());

			txtFecha.setDate(cal.getTime());

			cntResponsable.txtCodigo.setText((getRequerimiento()
					.getResponsable() == null) ? "" : getRequerimiento()
					.getResponsable().getIdresponsable());
			cntResponsable.llenar();
			cntSucursal.txtCodigo
					.setText((getRequerimiento().getSucursal() == null) ? ""
							: getRequerimiento().getSucursal().getIdsucursal());
			Sucursal s = getRequerimiento().getSucursal();
			cntSucursal.llenar();
			if (s == null) {
				cntAlmacen.setData(null);
			} else {
				cntAlmacen.setData(almacenDAO.getPorSucursal(s));
			}
			cntAlmacen.txtCodigo
					.setText((getRequerimiento().getAlmacen() == null) ? ""
							: getRequerimiento().getAlmacen().getId()
									.getIdalmacen());
			cntAlmacen.llenar();

			drequerimiento = drequerimientoDAO
					.getPorRequerimiento(getRequerimiento());

			for (DRequerimiento d : drequerimiento) {
				String idconsumidor = null, consumidor = null;
				Producto p = d.getProducto();
				Unimedida u = d.getUnimedida();
				Consumidor cons = d.getConsumidor();
				if (cons != null) {
					idconsumidor = cons.getIdconsumidor();
					consumidor = cons.getDescripcion();
				}
				getDetalleTM().addRow(
						new Object[] { p.getIdproducto(), p.getDescripcion(),
								u.getIdunimedida(), u.getDescripcion(),
								d.getCantidad(), idconsumidor, consumidor });
			}

		} else {
			drequerimiento = new ArrayList<DRequerimiento>();
		}
	}

	@Override
	public void llenar_lista() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_tablas() {
		cntSucursal.setData(sucursalDAO.findAll());
		cntResponsable.setData(responsableDAO.findAll());
	}

	@Override
	public void vista_edicion() {
		this.txtSerie.setEditable(true);
		this.txtNumero.setEditable(true);
		this.txtFecha.setEditable(true);
		this.txtGlosa.setEditable(true);
		FormValidador.CntEdicion(true, this.cntResponsable, this.cntAlmacen,
				this.cntSucursal);
		getDetalleTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		this.txtSerie.setEditable(false);
		this.txtNumero.setEditable(false);
		this.txtFecha.setEditable(false);
		this.txtGlosa.setEditable(false);
		FormValidador.CntEdicion(false, this.cntResponsable, this.cntAlmacen,
				this.cntSucursal);
		getDetalleTM().setEditar(false);
	}

	@Override
	public void init() {

	}

	@Override
	public void actualiza_objeto(Object id) {
		setRequerimiento(requerimientoDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		Calendar c = Calendar.getInstance();
		c.setTime(txtFecha.getDate());

		Long idoc = getRequerimiento().getIdrequerimiento();
		// getIngreso().setGrupoCentralizacion(cntGrupoCentralizacion.getSeleccionado());
		getRequerimiento().setSerie(this.txtSerie.getText());
		getRequerimiento()
				.setNumero(Integer.parseInt(this.txtNumero.getText()));
		getRequerimiento()
				.setResponsable(this.cntResponsable.getSeleccionado());
		getRequerimiento().setSucursal(cntSucursal.getSeleccionado());
		getRequerimiento().setAlmacen(this.cntAlmacen.getSeleccionado());
		getRequerimiento().setDia(c.get(Calendar.DAY_OF_MONTH));
		getRequerimiento().setMes(c.get(Calendar.MONTH) + 1);
		getRequerimiento().setAnio(c.get(Calendar.YEAR));
		getRequerimiento().setFecha(
				(c.get(Calendar.YEAR) * 10000)
						+ ((c.get(Calendar.MONTH) + 1) * 100)
						+ c.get(Calendar.DAY_OF_MONTH));
		getRequerimiento().setGlosa(txtGlosa.getText());

		drequerimiento = new ArrayList<DRequerimiento>();

		int rows = getDetalleTM().getRowCount();

		for (int row = 0; row < rows; row++) {
			DRequerimiento d = new DRequerimiento();
			DRequerimientoPK id = new DRequerimientoPK();

			String idproducto, idunimedida, idconsumidor;
			try {
				idproducto = getDetalleTM().getValueAt(row, 0).toString();
			} catch (Exception e) {
				idproducto = "";
			}

			try {
				idunimedida = getDetalleTM().getValueAt(row, 2).toString();
			} catch (Exception e) {
				idunimedida = "";
			}

			try {
				idconsumidor = getDetalleTM().getValueAt(row, 5).toString();
			} catch (Exception e) {
				idconsumidor = "";
			}

			float cantidad;

			cantidad = Float.parseFloat(getDetalleTM().getValueAt(row, 4)
					.toString());

			Producto p = productoDAO.find(idproducto);
			Unimedida u = unimedidaDAO.find(idunimedida);
			Consumidor cons = consumidorDAO.find(idconsumidor);

			id.setIdrequerimiento(idoc);
			id.setItem(row + 1);

			d.setId(id);
			d.setProducto(p);
			d.setUnimedida(u);
			d.setCantidad(cantidad);
			d.setConsumidor(cons);

			drequerimiento.add(d);
		}
	}

	@Override
	public boolean isValidaVista() {
		boolean band = validaCabecera();
		return band;
	}

	public boolean validaCabecera() {

		return FormValidador.TextFieldObligatorios(cntResponsable.txtCodigo,
				cntSucursal.txtCodigo, cntAlmacen.txtCodigo);
	}

	@Override
	protected void limpiarVista() {
		txtFecha.setDate(Calendar.getInstance().getTime());

		cntResponsable.txtCodigo.setText("");
		cntResponsable.llenar();
		cntSucursal.txtCodigo.setText("");
		cntSucursal.llenar();
		cntAlmacen.setData(null);

		cntAlmacen.txtCodigo.setText("");
		cntAlmacen.llenar();
		btnFlujo.setFlujo(null);
		getDetalleTM().limpiar();
	}

	public DSGTableModel getDetalleTM() {
		return ((DSGTableModel) tblDetalle.getModel());
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public List<DRequerimiento> getDrequerimiento() {
		return drequerimiento;
	}

	public void setDrequerimiento(List<DRequerimiento> drequerimiento) {
		this.drequerimiento = drequerimiento;
	}
}
