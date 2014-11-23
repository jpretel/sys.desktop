package vista.formularios;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import vista.contenedores.CntClieprov;
import vista.contenedores.CntMoneda;
import vista.contenedores.cntResponsable;
import vista.controles.DSGTableModel;
import vista.controles.DSGTextFieldCorrelativo;
import vista.controles.DSGTextFieldNumber;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.listas.AbstractDocForm;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.centralizacion.ContabilizaSlcCompras;
import core.dao.ClieprovDAO;
import core.dao.CotizacionCompraDAO;
import core.dao.DCotizacionCompraDAO;
import core.dao.DSolicitudCotizacionDAO;
import core.dao.ImpuestoDAO;
import core.dao.MonedaDAO;
import core.dao.ProductoDAO;
import core.dao.ProductoImpuestoDAO;
import core.dao.ResponsableDAO;
import core.dao.SolicitudCotizacionDAO;
import core.dao.UnimedidaDAO;
import core.entity.CotizacionCompra;
import core.entity.DCotizacionCompra;
import core.entity.DCotizacionCompraPK;
import core.entity.DSolicitudCotizacion;
import core.entity.Impuesto;
import core.entity.Producto;
import core.entity.ProductoImpuesto;
import core.entity.SolicitudCotizacion;
import core.entity.Unimedida;
//import dao.KardexSlcCompraDAO;

public class FrmDocCotizacionCompra extends AbstractDocForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CotizacionCompraDAO cotizacioncompraDAO = new CotizacionCompraDAO();
	private DCotizacionCompraDAO dcotizacioncompraDAO = new DCotizacionCompraDAO();

	private ProductoDAO productoDAO = new ProductoDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private ProductoImpuestoDAO pimptoDAO = new ProductoImpuestoDAO();
	private ImpuestoDAO impuestoDAO = new ImpuestoDAO();
	private ClieprovDAO clieprovDAO = new ClieprovDAO();
	private SolicitudCotizacionDAO sCotizacionDAO = new SolicitudCotizacionDAO();
	private DSolicitudCotizacionDAO sDCotizacionDAO = new DSolicitudCotizacionDAO();

	private TxtProducto txtProducto;
	private JLabel lblResponsable;
	private JLabel lblGlosa;
	private JScrollPane srlConsolidado;
	private cntResponsable cntResponsable;
	private JScrollPane scrlGlosa;
	private JTextArea txtGlosa;
	private JTable tblConsolidado;

	private CotizacionCompra cotizacioncompra;
	private List<DCotizacionCompra> dcotizacioncompras = new ArrayList<DCotizacionCompra>();

	private CntMoneda cntMoneda;
	private DSGTextFieldNumber txtTCambio;
	private CntClieprov cntClieprov;
	private JLabel lblProveedor;
	private JButton btnFindCot;
	private DSGTextFieldCorrelativo txtNumeroSol;
	private DSGTextFieldCorrelativo txtSerieSol;
	private JLabel lblSolicitudDeCotizacin;
	private JLabel lblMoneda;
	private JLabel lblTCambio;

	public FrmDocCotizacionCompra() {
		super("Cotizacion de Compra");
		txtFecha.setBounds(245, 11, 89, 22);

		setEstado(VISTA);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 874, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 681, Short.MAX_VALUE));

		this.lblResponsable = new JLabel("Responsable");
		this.lblResponsable.setBounds(10, 74, 74, 16);
		pnlPrincipal.add(this.lblResponsable);

		this.lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(399, 43, 32, 16);
		pnlPrincipal.add(this.lblGlosa);

		this.cntResponsable = new cntResponsable();

		this.cntResponsable.setBounds(72, 74, 309, 20);
		pnlPrincipal.add(this.cntResponsable);

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(436, 43, 395, 52);
		pnlPrincipal.add(this.scrlGlosa);

		this.txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);

		this.cntMoneda = new CntMoneda();
		this.cntMoneda.setBounds(387, 12, 153, 20);
		pnlPrincipal.add(this.cntMoneda);

		this.txtTCambio = new DSGTextFieldNumber(4);
		this.txtTCambio.setBounds(605, 12, 63, 20);
		pnlPrincipal.add(this.txtTCambio);

		/*
		 * Tabla de Consolidado
		 */

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				actualizaNumero(txtSerie.getText());
			}

			private void actualizaNumero(String serie) {
				int numero = cotizacioncompraDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero_2.setValue(numero);
					txtFecha.requestFocus();
				}
			}
		});

		this.srlConsolidado = new JScrollPane((Component) null);
		this.srlConsolidado.setBounds(10, 134, 821, 250);
		pnlPrincipal.add(this.srlConsolidado);
		tblConsolidado = new JTable(new DSGTableModel(new String[] {
				"Cód. Producto", "Producto", "Cod. Medida", "Medida",
				"Cantidad", "P. Unit.", "V. Venta", "%Dscto", "Dscto.",
				"% Impto", "Impto.", "Total" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 2 || column == 3 || column == 6
						|| column == 8 || column == 10 || column == 11)
					return false;

				return getEditar();
			}

			@Override
			public void addRow() {
				if (validaCabecera())
					addRow(new Object[] { "", "", "", "", 0.00, 0.00, 0.00,
							0.00, 0.00, 0.00, 0.00, 0.00 });
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
					String idproducto = getConsolidadoTM().getValueAt(row, 0)
							.toString();

					txtProducto.refresValue(idproducto);
					actualiza_detalle();
				}
			}
		};

		txtProducto = new TxtProducto(tblConsolidado, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Producto entity) {

				int row = tblConsolidado.getSelectedRow(), col = 0;
				float antimpto = 0.0F;
				if (entity == null) {
					getConsolidadoTM().setValueAt("", row, 0);
					getConsolidadoTM().setValueAt("", row, 1);
					getConsolidadoTM().setValueAt("", row, 2);
					getConsolidadoTM().setValueAt("", row, 3);

					getConsolidadoTM().setValueAt(0.0F, row, 9);
				} else {
					List<ProductoImpuesto> imptos = pimptoDAO
							.getPorProducto(entity);
					float impto = 0.0F;

					for (ProductoImpuesto pi : imptos) {
						Impuesto i = impuestoDAO.find(pi.getId()
								.getIdimpuesto());
						impto += i.getTasa();
					}

					setText(entity.getIdproducto());
					getConsolidadoTM().setValueAt(entity.getIdproducto(), row,
							col);
					getConsolidadoTM().setValueAt(entity.getDescripcion(), row,
							col + 1);
					getConsolidadoTM().setValueAt(
							entity.getUnimedida().getIdunimedida(), row,
							col + 2);
					getConsolidadoTM().setValueAt(
							entity.getUnimedida().getDescripcion(), row,
							col + 3);
					try {
						antimpto = Float.parseFloat(getConsolidadoTM()
								.getValueAt(row, 9).toString());

					} catch (Exception e) {
						antimpto = 0.0F;
					}

					if (antimpto == 0.0)
						getConsolidadoTM().setValueAt(impto, row, 9);

				}
				setSeleccionado(null);
			}
		};
		getConsolidadoTM().setScrollAndTable(srlConsolidado, tblConsolidado);

		this.tblConsolidado
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.srlConsolidado.setViewportView(this.tblConsolidado);

		this.cntClieprov = new CntClieprov();
		this.cntClieprov.setBounds(72, 43, 309, 20);
		this.cntClieprov.setData(clieprovDAO.findAll());
		pnlPrincipal.add(this.cntClieprov);

		this.lblProveedor = new JLabel("Proveedor");
		this.lblProveedor.setBounds(11, 43, 50, 16);
		pnlPrincipal.add(this.lblProveedor);

		this.btnFindCot = new JButton();
		this.btnFindCot.setIcon(new ImageIcon(new ImageIcon(getClass()
				.getResource("/main/resources/iconos/search.png")).getImage()
				.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)));

		btnFindCot.setMargin(new Insets(0, 0, 0, 0));
		btnFindCot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				llenarDetalle();
				
			}
		});
		
		this.btnFindCot.setBounds(250, 103, 20, 20);

		pnlPrincipal.add(this.btnFindCot);

		this.txtNumeroSol = new DSGTextFieldCorrelativo(8);
		this.txtNumeroSol.setColumns(10);
		this.txtNumeroSol.setBounds(168, 103, 80, 20);
		pnlPrincipal.add(this.txtNumeroSol);

		this.txtSerieSol = new DSGTextFieldCorrelativo(4);
		this.txtSerieSol.setColumns(10);
		this.txtSerieSol.setBounds(124, 103, 44, 20);
		pnlPrincipal.add(this.txtSerieSol);

		this.lblSolicitudDeCotizacin = new JLabel(
				"Solicitud de Cotizaci\u00F3n");
		this.lblSolicitudDeCotizacin.setBounds(10, 104, 123, 16);
		pnlPrincipal.add(this.lblSolicitudDeCotizacin);
		
		this.lblMoneda = new JLabel("Moneda");
		this.lblMoneda.setBounds(344, 15, 50, 16);
		pnlPrincipal.add(this.lblMoneda);
		
		this.lblTCambio = new JLabel("T. Cambio");
		this.lblTCambio.setBounds(550, 15, 50, 16);
		pnlPrincipal.add(this.lblTCambio);

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.findAll());
		getConsolidadoTM().setNombre_detalle("Detalle de Productos");
		getConsolidadoTM().setRepetidos(0);

		TableColumnModel tc = tblConsolidado.getColumnModel();

		getConsolidadoTM().setNombre_detalle("Consolidado de Productos");
		getConsolidadoTM().setRepetidos(0);

		tc.getColumn(4).setCellEditor(new FloatEditor(3));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(3));

		for (int i : new int[] { 5, 6, 7, 8, 9, 10, 11 }) {
			tc.getColumn(i).setCellEditor(new FloatEditor(2));
			tc.getColumn(i).setCellRenderer(new FloatRenderer(2));
		}

		iniciar();
	}

	protected void llenarDetalle() {
		String serie = this.txtSerieSol.getText();
		int numero;
		try {
			numero = Integer.valueOf(this.txtNumeroSol.getText());
		} catch (Exception e) {
			numero = 0;
		}
		int nFilas = getConsolidadoTM().getRowCount();
		int opc = -1;
		if (nFilas > 0) {
			opc = JOptionPane
					.showConfirmDialog(
							null,
							"Se borrara el detalle actual y cargara el detalle de la solicitud",
							"¿Desea Continuar?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			cotizacioncompra.setSolicitud(null);
		}
		if (opc == -1 || opc == 0) {
			getConsolidadoTM().limpiar();
			if (serie.trim().length() > 0 && numero > 0) {

				SolicitudCotizacion sCotizacion = sCotizacionDAO
						.getPorSerieNumero(serie, numero);
				List<DSolicitudCotizacion> ds = new ArrayList<DSolicitudCotizacion>();
				if (sCotizacion != null) {
					ds = sDCotizacionDAO.getPorSolicitud(sCotizacion);

					// Llenar proveedor
					cntClieprov.setText(sCotizacion.getClieprov()
							.getIdclieprov());
					cntClieprov.llenar();
					cotizacioncompra.setSolicitud(sCotizacion);
					for (DSolicitudCotizacion s : ds) {
						Producto p = s.getProducto();
						Unimedida u = s.getUnimedida();
						getConsolidadoTM().addRow(
								new Object[] { p.getIdproducto(),
										p.getDescripcion(), u.getIdunimedida(),
										u.getDescripcion(), s.getCantidad() });

					}
				} else {
					UtilMensajes.mensaje_alterta("DOC_NO_ENCONTRADO");
				}
			}
		}
	}

	@Override
	public void nuevo() {
		setOrdencompra(new CotizacionCompra());
		getCotizacioncompra().setIdcotizacioncompra(System.nanoTime());
		cotizacioncompra.setTcmoneda(1F);
		txtSerie.requestFocus();
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		cotizacioncompraDAO.crear_editar(getCotizacioncompra());

		// kardexSlcDAO.borrarPorIdOrdenCompra(ordencompra.getIdordencompra());

		for (DCotizacionCompra d : dcotizacioncompraDAO.aEliminar(
				getCotizacioncompra(), dcotizacioncompras)) {
			dcotizacioncompraDAO.remove(d);
		}

		for (DCotizacionCompra d : dcotizacioncompras) {
			if (dcotizacioncompraDAO.find(d.getId()) == null) {
				dcotizacioncompraDAO.create(d);
			} else {
				dcotizacioncompraDAO.edit(d);
			}
		}
		ContabilizaSlcCompras.ContabilizaCotizacion(getCotizacioncompra()
				.getIdcotizacioncompra());
	}

	@Override
	public void eliminar() {
		int opcion = UtilMensajes.mensaje_sino("DESEA_ELIMINAR_DOC");
		if (opcion == 0) {
			dcotizacioncompraDAO.borrarPorCotizacionCompra(cotizacioncompra);
			cotizacioncompraDAO.remove(cotizacioncompra);
			cotizacioncompra = null;
		}
	}

	@Override
	public void llenar_datos() {
		limpiarVista();

		if (getCotizacioncompra() != null) {
			this.txtNumero_2.setValue(getCotizacioncompra().getNumero());
			this.txtSerie.setText(getCotizacioncompra().getSerie());
			this.txtTCambio.setValue(getCotizacioncompra().getTcambio());
			// this.cntGrupoCentralizacion.txtCodigo.setText(getCotizacioncompra().getGrupoCentralizacion().getIdgcentralizacion());
			// this.cntGrupoCentralizacion.txtDescripcion.setText(getCotizacioncompra().getGrupoCentralizacion().getDescripcion());
			cntMoneda.txtCodigo
					.setText((getCotizacioncompra().getMoneda() == null) ? ""
							: getCotizacioncompra().getMoneda().getIdmoneda());
			cntMoneda.llenar();
			cntResponsable.txtCodigo.setText((getCotizacioncompra()
					.getResponsable() == null) ? "" : getCotizacioncompra()
					.getResponsable().getIdresponsable());
			cntResponsable.llenar();

			cntClieprov.txtCodigo
					.setText((getCotizacioncompra().getClieprov() == null) ? ""
							: getCotizacioncompra().getClieprov()
									.getIdclieprov());
			cntClieprov.llenar();

			if (cotizacioncompra.getSolicitud() != null) {
				txtSerieSol.setText(cotizacioncompra.getSolicitud().getSerie());
				txtNumeroSol.setValue(cotizacioncompra.getSolicitud()
						.getNumero());
			}

			dcotizacioncompras = dcotizacioncompraDAO
					.getPorCotizacionCompra(getCotizacioncompra());

			for (DCotizacionCompra d : dcotizacioncompras) {
				Producto p = d.getProducto();
				Unimedida u = d.getUnimedida();

				getConsolidadoTM().addRow(
						new Object[] { p.getIdproducto(), p.getDescripcion(),
								u.getIdunimedida(), u.getDescripcion(),
								d.getCantidad(), d.getPrecio_unitario(),
								d.getVventa(), d.getPdescuento(),
								d.getDescuento(), d.getPimpuesto(),
								d.getImpuesto(), d.getImporte() });
			}
		} else {
			dcotizacioncompras = new ArrayList<DCotizacionCompra>();
		}
	}

	private void actualiza_detalle() {
		int row = tblConsolidado.getSelectedRow();
		if (row > -1) {

			float pimpuesto, pdscto, cantidad, pu;
			float vventa, impuesto, dscto, importe;
			cantidad = Float.parseFloat(getConsolidadoTM().getValueAt(row, 4)
					.toString());
			try {
				pu = Float.parseFloat(getConsolidadoTM().getValueAt(row, 5)
						.toString());
			} catch (Exception e) {
				pu = 0.0F;
			}
			try {
				pdscto = Float.parseFloat(getConsolidadoTM().getValueAt(row, 7)
						.toString());
			} catch (Exception e) {
				pdscto = 0.0F;
			}
			try {
				pimpuesto = Float.parseFloat(getConsolidadoTM().getValueAt(row,
						9).toString());
			} catch (Exception e) {
				pimpuesto = 0.0F;
			}

			vventa = cantidad * pu;
			dscto = vventa * pdscto / 100.00F;
			impuesto = (vventa - dscto) * pimpuesto / 100.00F;
			importe = vventa - dscto + impuesto;

			getConsolidadoTM().setValueAt(vventa, row, 6);
			getConsolidadoTM().setValueAt(dscto, row, 8);
			getConsolidadoTM().setValueAt(impuesto, row, 10);
			getConsolidadoTM().setValueAt(importe, row, 11);
			// getConsolidadoTM().setValueAt(new ReferenciaDOC(), row, 12);
		}
	}

	@Override
	public void llenar_lista() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_tablas() {
		cntMoneda.setData(new MonedaDAO().findAll());
		cntResponsable.setData(new ResponsableDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		this.txtFecha.setEditable(true);
		this.txtGlosa.setEditable(true);
		FormValidador.TextFieldsEdicion(true, txtSerie, txtNumero_2,
				txtTCambio, txtSerieSol, txtNumeroSol);
		FormValidador.CntEdicion(true, this.cntMoneda, this.cntResponsable,
				this.cntClieprov);
		getConsolidadoTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		this.txtFecha.setEditable(false);
		this.txtGlosa.setEditable(false);
		FormValidador.TextFieldsEdicion(false, txtSerie, txtNumero_2,
				txtTCambio, txtSerieSol, txtNumeroSol);
		FormValidador.CntEdicion(false, this.cntMoneda, this.cntResponsable,
				this.cntClieprov);
		getConsolidadoTM().setEditar(false);
	}

	@Override
	public void init() {

	}

	@Override
	public void actualiza_objeto(Object id) {
		setOrdencompra(cotizacioncompraDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		Calendar c = Calendar.getInstance();
		c.setTime(txtFecha.getDate());
		Long idoc = getCotizacioncompra().getIdcotizacioncompra();
		// getIngreso().setGrupoCentralizacion(cntGrupoCentralizacion.getSeleccionado());
		getCotizacioncompra().setSerie(this.txtSerie.getText());
		getCotizacioncompra().setNumero(
				Integer.parseInt(this.txtNumero_2.getText()));
		getCotizacioncompra().setMoneda(cntMoneda.getSeleccionado());
		getCotizacioncompra().setResponsable(
				this.cntResponsable.getSeleccionado());
		getCotizacioncompra().setClieprov(this.cntClieprov.getSeleccionado());
		getCotizacioncompra().setDia(c.get(Calendar.DAY_OF_MONTH));
		getCotizacioncompra().setMes(c.get(Calendar.MONTH) + 1);
		getCotizacioncompra().setAnio(c.get(Calendar.YEAR));
		getCotizacioncompra().setFecha(
				(c.get(Calendar.YEAR) * 10000)
						+ ((c.get(Calendar.MONTH) + 1) * 100)
						+ c.get(Calendar.DAY_OF_MONTH));
		getCotizacioncompra().setGlosa(txtGlosa.getText());
		getCotizacioncompra()
				.setTcambio(Float.parseFloat(txtTCambio.getText()));
		dcotizacioncompras = new ArrayList<DCotizacionCompra>();

		int rows = getConsolidadoTM().getRowCount();

		for (int row = 0; row < rows; row++) {
			DCotizacionCompra d = new DCotizacionCompra();
			DCotizacionCompraPK id = new DCotizacionCompraPK();

			String idproducto, idunimedida;

			idproducto = getConsolidadoTM().getValueAt(row, 0).toString();
			idunimedida = getConsolidadoTM().getValueAt(row, 2).toString();

			float cantidad, precio_unitario, vventa, pdescuento, descuento, pimpuesto, impuesto, importe;
			
			try {
				cantidad = Float.parseFloat(getConsolidadoTM().getValueAt(row, 4)
						.toString());				
			} catch (Exception e) {
				cantidad = 0;
			}
			try {
				precio_unitario = Float.parseFloat(getConsolidadoTM().getValueAt(
						row, 5).toString());				
			} catch (Exception e) {
				precio_unitario = 0;
			}
			try {
				vventa = Float.parseFloat(getConsolidadoTM().getValueAt(row, 6)
						.toString());				
			} catch (Exception e) {
				vventa = 0;
			}
			
			try {
				pdescuento = Float.parseFloat(getConsolidadoTM().getValueAt(row, 7)
						.toString());
			} catch (Exception e) {
				pdescuento = 0;
			}
			
			try {
				descuento = 0;
			} catch (Exception e) {
				descuento = Float.parseFloat(getConsolidadoTM().getValueAt(row, 8)
						.toString());
			}
			try {
				
				pimpuesto = Float.parseFloat(getConsolidadoTM().getValueAt(row, 9)
						.toString());
			} catch (Exception e) {
				pimpuesto = 0;
			}
			try {
				impuesto = Float.parseFloat(getConsolidadoTM().getValueAt(row, 10)
						.toString());
			} catch (Exception e) {
				impuesto = 0;
			}
			try {
				importe = Float.parseFloat(getConsolidadoTM().getValueAt(row, 11)
						.toString());				
			} catch (Exception e) {
				importe = 0;
			}

			Producto p = productoDAO.find(idproducto);
			Unimedida u = unimedidaDAO.find(idunimedida);

			id.setIdcotizacioncompra(idoc);
			id.setItem(row + 1);

			d.setId(id);
			d.setProducto(p);
			d.setUnimedida(u);
			d.setCantidad(cantidad);
			d.setPrecio_unitario(precio_unitario);
			d.setVventa(vventa);
			d.setPdescuento(pdescuento);
			d.setDescuento(descuento);
			d.setPimpuesto(pimpuesto);
			d.setImpuesto(impuesto);
			d.setImporte(importe);

			dcotizacioncompras.add(d);
		}
	}

	@Override
	public boolean isValidaVista() {
		boolean band = validaCabecera();
		return band;
	}

	public boolean validaCabecera() {

		if (!FormValidador.CntObligatorios(cntMoneda, cntResponsable,
				cntClieprov))
			return false;

		return true;
	}

	@Override
	protected void limpiarVista() {
		this.txtNumero_2.setValue("");
		this.txtSerie.setText("");
		this.txtTCambio.setValue(0);
		txtSerieSol.setText("");
		txtNumeroSol.setText("");
		cntMoneda.txtCodigo.setText("");
		cntMoneda.llenar();
		cntResponsable.txtCodigo.setText("");
		cntResponsable.llenar();

		cntClieprov.txtCodigo.setText("");
		cntClieprov.llenar();

		getConsolidadoTM().limpiar();
	};

	public DSGTableModel getConsolidadoTM() {
		return ((DSGTableModel) tblConsolidado.getModel());
	}

	public CotizacionCompra getCotizacioncompra() {
		return cotizacioncompra;
	}

	public void setOrdencompra(CotizacionCompra cotizacioncompra) {
		this.cotizacioncompra = cotizacioncompra;
	}
}
