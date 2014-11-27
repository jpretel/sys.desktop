package vista.formularios.documentos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRDataSource;
import vista.Sys;
import vista.barras.PanelBarraDocumento;
import vista.combobox.ComboBox;
import vista.contenedores.CntClieprov;
import vista.contenedores.CntMoneda;
import vista.contenedores.cntResponsable;
import vista.controles.CntReferenciaDoc;
import vista.controles.DSGTableModel;
import vista.controles.DSGTextFieldNumber;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.abstractforms.AbstractDocForm;
import vista.formularios.modal.ModalDetalleReferencia;
import vista.formularios.modal.ModalInternalPanel;
import vista.utilitarios.FormValidador;
import vista.utilitarios.StringUtils;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.centralizacion.ContabilizaComprasRecepcion;
import core.centralizacion.ContabilizaSlcCompras;
import core.dao.ClieprovDAO;
import core.dao.CotizacionCompraDAO;
import core.dao.DDOrdenCompraDAO;
import core.dao.DOrdenCompraDAO;
import core.dao.ImpuestoDAO;
import core.dao.KardexSlcCompraDAO;
import core.dao.MonedaDAO;
import core.dao.OrdenCompraDAO;
import core.dao.ProductoDAO;
import core.dao.ProductoImpuestoDAO;
import core.dao.ResponsableDAO;
import core.dao.SolicitudCompraDAO;
import core.dao.UnimedidaDAO;
import core.entity.CotizacionCompra;
import core.entity.DDOrdenCompra;
import core.entity.DDOrdenCompraPK;
import core.entity.DOrdenCompra;
import core.entity.DOrdenCompraPK;
import core.entity.Impuesto;
import core.entity.OrdenCompra;
import core.entity.Producto;
import core.entity.ProductoImpuesto;
import core.entity.SolicitudCompra;
import core.entity.Unimedida;

public class FrmDocOrdenCompra extends AbstractDocForm {
	private static final long serialVersionUID = 1L;
	// private List<DetDocingreso> DetDocingresoL;
	private OrdenCompraDAO ordencompraDAO = new OrdenCompraDAO();
	private DOrdenCompraDAO dordencompraDAO = new DOrdenCompraDAO();
	private DDOrdenCompraDAO ddordenCompraDAO = new DDOrdenCompraDAO();
	private SolicitudCompraDAO solicitudCompraDAO = new SolicitudCompraDAO();
	private CotizacionCompraDAO cotizacionCompraDAO = new CotizacionCompraDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private ProductoImpuestoDAO pimptoDAO = new ProductoImpuestoDAO();
	private ImpuestoDAO impuestoDAO = new ImpuestoDAO();
	private KardexSlcCompraDAO kardexSlcDAO = new KardexSlcCompraDAO();
	private ClieprovDAO clieprovDAO = new ClieprovDAO();

	private TxtProducto txtProducto;
	private JLabel lblResponsable;
	private JLabel lblGlosa;
	private JScrollPane srlConsolidado;
	private cntResponsable cntResponsable;
	private JScrollPane scrlGlosa;
	private JTextArea txtGlosa;
	private JTable tblConsolidado;

	private OrdenCompra ordencompra;
	private List<DOrdenCompra> dordencompras = new ArrayList<DOrdenCompra>();
	private List<DDOrdenCompra> ddordencompras = new ArrayList<DDOrdenCompra>();
	private JLabel lblReferencia;
	private CntReferenciaDoc cntReferenciaDoc;
	private CntMoneda cntMoneda;
	private DSGTextFieldNumber txtTCambio;
	private DSGTextFieldNumber txtTCMoneda;
	private CntClieprov cntClieprov;
	private JLabel lblProveedor;
	private JLabel lblMoneda;
	private JLabel lblTCambio;
	private JLabel lblTCMoneda;

	private ComboBox cboTipoDoc;
	private List<String[]> optionList = new ArrayList<String[]>();

	public FrmDocOrdenCompra() {
		super("Orden de Compra");
		txtFecha.setBounds(245, 11, 89, 22);

		setEstado(VISTA);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 874, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 681, Short.MAX_VALUE));

		this.lblResponsable = new JLabel("Responsable");
		this.lblResponsable.setBounds(10, 75, 74, 16);
		pnlPrincipal.add(this.lblResponsable);

		this.lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(399, 43, 32, 16);
		pnlPrincipal.add(this.lblGlosa);

		this.cntResponsable = new cntResponsable();

		this.cntResponsable.setBounds(71, 75, 309, 20);
		pnlPrincipal.add(this.cntResponsable);

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(436, 43, 395, 52);
		pnlPrincipal.add(this.scrlGlosa);

		this.txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);

		this.lblReferencia = new JLabel("Referencia");
		this.lblReferencia.setBounds(10, 102, 74, 16);
		pnlPrincipal.add(this.lblReferencia);

		optionList.add(new String[] { "S", "Solicitud de Compra" });
		optionList.add(new String[] { "C", "Cotizacion de Compra" });
		cboTipoDoc = new vista.combobox.ComboBox(optionList, 1);
		cboTipoDoc.setBounds(72, 100, 125, 20);
		pnlPrincipal.add(cboTipoDoc);

		cboTipoDoc.setSelectedIndex(0);

		this.cntReferenciaDoc = new CntReferenciaDoc() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buscarReferencia() {
				String serie;
				int numero;

				serie = this.txtSerie.getText().trim();
				try {
					numero = Integer.parseInt(this.txtNumero.getText());
				} catch (Exception e) {
					numero = 0;
				}

				if (numero > 0 && !serie.isEmpty()) {
					String tipo = optionList.get(cboTipoDoc.getSelectedIndex())[0]
							.toString();
					// referenciarSolicitudCotizacionCompra(serie, numero,
					// sucursal, almacen,tipo);
					if (tipo == "S") {
						referenciarSolicitudCompra(serie, numero);
					} else {
						referenciarCotizacionCompra(serie, numero);
					}
					txtSerie.setText("");
					txtNumero.setText("");
				} else {
					UtilMensajes.mensaje_alterta("COMPL_SERIE_NUMERO_BUSQUEDA");
					txtSerie.requestFocus();
				}
			}

			@Override
			public Object[][] getData() {
				Object[][] data = null;
				// Referencia a solicitudes
				List<Long> solicitudes = new ArrayList<Long>();

				// Referencia a Cotizaciones
				List<Long> cotizaciones = new ArrayList<Long>();

				for (DDOrdenCompra o : ddordencompras) {
					String tipo_referencia = o.getTipo_referencia();
					if (tipo_referencia.equals("SLC_COMPRA")) {
						boolean haySlc = false;
						salir: for (long id : solicitudes) {
							if (id == o.getId_referencia()) {
								haySlc = true;
								break salir;
							}
						}
						if (!haySlc) {
							solicitudes.add(o.getId_referencia());
						}
					}

					if (tipo_referencia.equals("COT_COMPRA")) {
						boolean hayCot = false;
						salir: for (long id : cotizaciones) {
							if (id == o.getId_referencia()) {
								hayCot = true;
								break salir;
							}
						}
						if (!hayCot) {
							cotizaciones.add(o.getId_referencia());
						}
					}
				}
				int total = solicitudes.size() + cotizaciones.size(), i = 0, nSol = solicitudes
						.size();
				data = new Object[total][5];
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

				for (Long id : solicitudes) {
					SolicitudCompra slc = solicitudCompraDAO.find(id);
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, slc.getAnio());
					calendar.set(Calendar.MONTH, slc.getMes() - 1);
					calendar.set(Calendar.DAY_OF_MONTH, slc.getDia());
					if (slc != null) {
						data[i][0] = "Sol. Compra";
						data[i][1] = slc.getSerie() + "-"
								+ StringUtils._padl(slc.getNumero(), 8, '0');
						data[i][2] = formater.format(calendar.getTime()); // slc.getFecha();
						data[i][3] = "SLC_COMPRA";
						data[i][4] = slc;
					}
					i++;
				}

				i = 0;
				for (Long id : cotizaciones) {
					CotizacionCompra cot = cotizacionCompraDAO.find(id);
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.YEAR, cot.getAnio());
					calendar.set(Calendar.MONTH, cot.getMes() - 1);
					calendar.set(Calendar.DAY_OF_MONTH, cot.getDia());
					if (cot != null) {
						data[nSol + i][0] = "Cot. Compra";
						data[nSol + i][1] = cot.getSerie() + "-"
								+ StringUtils._padl(cot.getNumero(), 8, '0');
						data[nSol + i][2] = formater.format(calendar.getTime()); // slc.getFecha();
						data[nSol + i][3] = "COT_COMPRA";
						data[nSol + i][4] = cot;
					}
					i++;
				}
				return data;
			}

			@Override
			public void mostrarDetalleRef(Object[] row) {
				if (String.valueOf(row[3]).equals("SLC_COMPRA")) {
					SolicitudCompra slc = (SolicitudCompra) row[4];
					referenciarSolicitudCompra(slc, getEstado());
				}
				if (String.valueOf(row[3]).equals("COT_COMPRA")) {
					CotizacionCompra slc = (CotizacionCompra) row[4];
					referenciarCotizacionCompra(slc, getEstado());
				}
			}
		};

		this.cntReferenciaDoc.setBounds(200, 100, 185, 23);
		pnlPrincipal.add(this.cntReferenciaDoc);

		this.cntMoneda = new CntMoneda();
		this.cntMoneda.setBounds(388, 12, 139, 20);
		pnlPrincipal.add(this.cntMoneda);

		this.txtTCambio = new DSGTextFieldNumber(4);
		this.txtTCambio.setBounds(594, 12, 74, 20);
		pnlPrincipal.add(this.txtTCambio);

		this.txtTCMoneda = new DSGTextFieldNumber(4);
		this.txtTCMoneda.setBounds(757, 12, 74, 20);
		pnlPrincipal.add(this.txtTCMoneda);
		this.txtTCMoneda.setValue(1);

		/*
		 * Tabla de Consolidado
		 */

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				actualizaNumero(txtSerie.getText());
			}

			private void actualizaNumero(String serie) {
				int numero = ordencompraDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero.setValue(numero);
					txtFecha.requestFocus();
				}
			}
		});

		this.srlConsolidado = new JScrollPane((Component) null);
		this.srlConsolidado.setBounds(10, 148, 821, 236);
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
				// Evalua si producto esta en una referencia
				if (column == 4) {
					String idproducto = getValueAt(row, 0).toString().trim();
					for (DDOrdenCompra o : ddordencompras) {
						if (o.getProducto().getIdproducto().equals(idproducto))
							return false;
					}
				}
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

			@Override
			public boolean isValidaEliminacion(int row) {
				boolean band = true;

				String idproducto = getValueAt(row, 0).toString();

				for (DDOrdenCompra o : ddordencompras) {
					if (o.getProducto().getIdproducto().equals(idproducto)) {
						UtilMensajes
								.mensaje_alterta("NO_ELIM_LINEA_REFERENCIA");
						return false;
					}
				}

				return band;
			};
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
		this.cntClieprov.setBounds(71, 43, 309, 20);
		this.cntClieprov.setData(clieprovDAO.findAll());
		pnlPrincipal.add(this.cntClieprov);

		this.lblProveedor = new JLabel("Proveedor");
		this.lblProveedor.setBounds(11, 47, 50, 16);
		pnlPrincipal.add(this.lblProveedor);

		this.lblMoneda = new JLabel("Moneda");
		this.lblMoneda.setBounds(344, 14, 52, 16);
		pnlPrincipal.add(this.lblMoneda);

		this.lblTCambio = new JLabel("T. Cambio");
		this.lblTCambio.setBounds(537, 15, 52, 16);
		pnlPrincipal.add(this.lblTCambio);

		this.lblTCMoneda = new JLabel("T. C. Moneda");
		this.lblTCMoneda.setBounds(678, 15, 74, 16);
		pnlPrincipal.add(this.lblTCMoneda);

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.findAll());
		getConsolidadoTM().setNombre_detalle("Detalle de Productos");
		getConsolidadoTM().setRepetidos(0);

		TableColumnModel tc = tblConsolidado.getColumnModel();

		getConsolidadoTM().setNombre_detalle("Consolidado de Productos");
		getConsolidadoTM().setRepetidos(0);

		tc.getColumn(4).setCellEditor(new FloatEditor(3));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(3));

		tc.getColumn(5).setCellEditor(new FloatEditor(2));
		tc.getColumn(5).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(6).setCellEditor(new FloatEditor(2));
		tc.getColumn(6).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(7).setCellEditor(new FloatEditor(2));
		tc.getColumn(7).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(8).setCellEditor(new FloatEditor(2));
		tc.getColumn(8).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(9).setCellEditor(new FloatEditor(2));
		tc.getColumn(9).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(10).setCellEditor(new FloatEditor(2));
		tc.getColumn(10).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(11).setCellEditor(new FloatEditor(2));
		tc.getColumn(11).setCellRenderer(new FloatRenderer(2));

		iniciar();
	}

	@Override
	public void nuevo() {
		Calendar c = Calendar.getInstance();
		
		ordencompra = new OrdenCompra();
		ordencompra.setIdordencompra(System.nanoTime());
		ordencompra.setTcmoneda(1F);
		ordencompra.setAnio(c.get(Calendar.YEAR));
		ordencompra.setMes(c.get(Calendar.MONDAY)+1);
		ordencompra.setDia(c.get(Calendar.DAY_OF_MONTH));
		txtSerie.requestFocus();
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		ordencompraDAO.crear_editar(getOrdencompra());

		kardexSlcDAO.borrarPorIdOrdenCompra(ordencompra.getIdordencompra());

		ddordenCompraDAO.borrarPorOrdenCompra(getOrdencompra());

		for (DOrdenCompra d : dordencompraDAO.aEliminar(getOrdencompra(),
				dordencompras)) {
			dordencompraDAO.remove(d);
		}

		for (DOrdenCompra d : dordencompras) {
			if (dordencompraDAO.find(d.getId()) == null) {
				dordencompraDAO.create(d);
			} else {
				dordencompraDAO.edit(d);
			}
		}

		int i = 1;
		for (DDOrdenCompra o : ddordencompras) {
			DDOrdenCompra ddo = new DDOrdenCompra();
			DDOrdenCompraPK id = new DDOrdenCompraPK();
			id.setIdordencompra(ordencompra.getIdordencompra());
			id.setItem(i);
			ddo.setId(id);
			ddo.setCantidad(o.getCantidad());
			ddo.setId_referencia(o.getId_referencia());
			ddo.setTipo_referencia(o.getTipo_referencia());
			ddo.setProducto(o.getProducto());
			ddordenCompraDAO.create(ddo);
			i++;
		}

		ContabilizaSlcCompras.ContabilizaOrdenCompra(getOrdencompra()
				.getIdordencompra());

		ContabilizaComprasRecepcion.ContabilizaCompra(ordencompra
				.getIdordencompra());
	}

	@Override
	public void eliminar() {
		int opcion = UtilMensajes.mensaje_sino("DESEA_ELIMINAR_DOC");
		if (opcion == 0) {
			// Borrar Kardex
			kardexSlcDAO.borrarPorIdOrdenCompra(ordencompra.getIdordencompra());

			// Borrar Detalle y Consolidado
			ddordenCompraDAO.borrarPorOrdenCompra(ordencompra);
			dordencompraDAO.borrarPorOrdenCompra(ordencompra);
			// Borrar Cabecera
			ordencompraDAO.remove(ordencompra);
			ordencompra = null;
		}
	}

	@Override
	public void llenar_datos() {
		limpiarVista();

		if (getOrdencompra() != null) {
			txtNumero.setValue(getOrdencompra().getNumero());
			txtSerie.setText(getOrdencompra().getSerie());
			txtTCambio.setValue(getOrdencompra().getTcambio());
			txtTCMoneda.setValue(getOrdencompra().getTcmoneda());
			cntMoneda.txtCodigo
					.setText((getOrdencompra().getMoneda() == null) ? ""
							: getOrdencompra().getMoneda().getIdmoneda());
			cntMoneda.llenar();
			cntResponsable.txtCodigo
					.setText((getOrdencompra().getResponsable() == null) ? ""
							: getOrdencompra().getResponsable()
									.getIdresponsable());
			cntResponsable.llenar();

			cntClieprov.txtCodigo
					.setText((getOrdencompra().getClieprov() == null) ? ""
							: getOrdencompra().getClieprov().getIdclieprov());
			cntClieprov.llenar();

			dordencompras = dordencompraDAO.getPorOrdenCompra(getOrdencompra());
			ddordencompras = ddordenCompraDAO
					.getPorOrdenCompra(getOrdencompra());

			for (DOrdenCompra d : dordencompras) {
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

			// List<DetDocingreso> detDocIngresoL =
			// detDocingresoDAO.getPorIdIngreso(Long.parseLong(getIngreso().getIddocingreso()));
			// for(DetDocingreso ingreso : detDocIngresoL){
			// getConsolidadoTM().addRow(new
			// Object[]{ingreso.getId().getIdproducto(),ingreso.getDescripcion(),ingreso.getIdmedida(),"",ingreso.getCantidad(),ingreso.getPrecio(),ingreso.getPrecio()});
			// }
		} else {
			dordencompras = new ArrayList<DOrdenCompra>();
			ddordencompras = new ArrayList<DDOrdenCompra>();
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
	public void llenar_tablas() {
		cntMoneda.setData(new MonedaDAO().findAll());
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
				this.cntClieprov);
		getConsolidadoTM().setEditar(true);
		this.cntReferenciaDoc.setEditar(true);
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
				this.cntClieprov);
		getConsolidadoTM().setEditar(false);
		this.cntReferenciaDoc.setEditar(false);
	}

	@Override
	public void actualiza_objeto(Object id) {
		setOrdencompra(ordencompraDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		Calendar c = Calendar.getInstance();
		c.setTime(txtFecha.getDate());
		Long idoc = getOrdencompra().getIdordencompra();
		// getIngreso().setGrupoCentralizacion(cntGrupoCentralizacion.getSeleccionado());
		getOrdencompra().setSerie(this.txtSerie.getText());
		getOrdencompra().setNumero(Integer.parseInt(this.txtNumero.getText()));
		getOrdencompra().setMoneda(cntMoneda.getSeleccionado());
		getOrdencompra().setResponsable(this.cntResponsable.getSeleccionado());
		getOrdencompra().setClieprov(this.cntClieprov.getSeleccionado());
		getOrdencompra().setDia(c.get(Calendar.DAY_OF_MONTH));
		getOrdencompra().setMes(c.get(Calendar.MONTH) + 1);
		getOrdencompra().setAnio(c.get(Calendar.YEAR));
		getOrdencompra().setFecha(
				(c.get(Calendar.YEAR) * 10000)
						+ ((c.get(Calendar.MONTH) + 1) * 100)
						+ c.get(Calendar.DAY_OF_MONTH));
		getOrdencompra().setGlosa(txtGlosa.getText());
		getOrdencompra().setTcambio(Float.parseFloat(txtTCambio.getText()));
		getOrdencompra().setTcmoneda(Float.parseFloat(txtTCMoneda.getText()));
		dordencompras = new ArrayList<DOrdenCompra>();

		int rows = getConsolidadoTM().getRowCount();

		for (int row = 0; row < rows; row++) {
			DOrdenCompra d = new DOrdenCompra();
			DOrdenCompraPK id = new DOrdenCompraPK();

			String idproducto, idunimedida;

			idproducto = getConsolidadoTM().getValueAt(row, 0).toString();
			idunimedida = getConsolidadoTM().getValueAt(row, 2).toString();

			float cantidad, precio_unitario, vventa, pdescuento, descuento, pimpuesto, impuesto, importe;

			cantidad = Float.parseFloat(getConsolidadoTM().getValueAt(row, 4)
					.toString());
			precio_unitario = Float.parseFloat(getConsolidadoTM().getValueAt(
					row, 5).toString());

			vventa = Float.parseFloat(getConsolidadoTM().getValueAt(row, 6)
					.toString());

			pdescuento = Float.parseFloat(getConsolidadoTM().getValueAt(row, 7)
					.toString());

			descuento = Float.parseFloat(getConsolidadoTM().getValueAt(row, 8)
					.toString());

			pimpuesto = Float.parseFloat(getConsolidadoTM().getValueAt(row, 9)
					.toString());

			impuesto = Float.parseFloat(getConsolidadoTM().getValueAt(row, 10)
					.toString());

			importe = Float.parseFloat(getConsolidadoTM().getValueAt(row, 11)
					.toString());

			Producto p = productoDAO.find(idproducto);
			Unimedida u = unimedidaDAO.find(idunimedida);

			id.setIdordencompra(idoc);
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

			dordencompras.add(d);
		}
		int i = 1;
		for (DDOrdenCompra o : ddordencompras) {
			DDOrdenCompraPK id = new DDOrdenCompraPK();
			id.setIdordencompra(idoc);
			id.setItem(i);
			o.setId(id);

			i++;
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

	private void referenciarSolicitudCompra(String serie, int numero) {

		SolicitudCompra solicitudCompra = solicitudCompraDAO.getPorSerieNumero(
				serie, numero);

		if (solicitudCompra != null) {
			referenciarSolicitudCompra(solicitudCompra, "EDICION");
		} else {
			UtilMensajes.mensaje_alterta("DOC_NO_ENCONTRADO");
		}
	}

	private void referenciarCotizacionCompra(String serie, int numero) {

		CotizacionCompra cotizacionCompra = cotizacionCompraDAO
				.getPorSerieNumero(serie, numero);

		if (cotizacionCompra != null) {
			referenciarCotizacionCompra(cotizacionCompra, "EDICION");
		} else {
			UtilMensajes.mensaje_alterta("DOC_NO_ENCONTRADO");
		}
	}

	private void referenciarSolicitudCompra(SolicitudCompra solicitudCompra,
			final String estado) {
		List<Tuple> saldos = new KardexSlcCompraDAO().getSaldoPorOrigen(
				solicitudCompra.getIdsolicitudcompra(), getOrdencompra());

		if (saldos != null && saldos.size() > 0) {

			Object[][] data = new Object[saldos.size()][5];
			int i = 0;
			for (Tuple t : saldos) {

				Producto p = (Producto) t.get("producto");
				Unimedida u = (Unimedida) t.get("unimedida");
				float cantidad = (float) t.get("cantidad");
				data[i][0] = p.getIdproducto();
				data[i][1] = p.getDescripcion();
				data[i][2] = u.getDescripcion();
				data[i][3] = cantidad;
				data[i][4] = cantidadProducto(p, "SLC_COMPRA",
						solicitudCompra.getIdsolicitudcompra());
				i++;
			}

			DSGTableModel model = new DSGTableModel(new String[] {
					"Cód Producto", "Producto", "U.M.", "Saldo", "Cantidad" }) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean evaluaEdicion(int row, int column) {
					if (column == 4 && !estado.equals(VISTA))
						return true;
					return false;
				}

				@Override
				public void addRow() {
				}
			};

			ModalDetalleReferencia modal = new ModalDetalleReferencia(model,
					data) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validaModelo() {
					for (int i = 0; i < this.model.getRowCount(); i++) {
						String producto;
						producto = this.model.getValueAt(i, 1).toString();

						float saldo = 0.0F, cantidad = 0.0F;
						try {
							saldo = Float.parseFloat(this.model
									.getValueAt(i, 3).toString());
						} catch (Exception e) {
							saldo = 0;
						}
						try {
							cantidad = Float.parseFloat(this.model.getValueAt(
									i, 4).toString());
						} catch (Exception e) {
							cantidad = 0;
						}
						if (cantidad > saldo) {
							UtilMensajes.mensaje_alterta(
									"CANTIDAD_MENOR_SALDO", producto);
							return false;
						}
					}
					return true;
				}
			};

			TableColumnModel tc = modal.getTable().getColumnModel();

			tc.getColumn(3).setCellRenderer(new FloatRenderer(3));

			tc.getColumn(4).setCellRenderer(new FloatRenderer(3));
			tc.getColumn(4).setCellEditor(new FloatEditor(3));

			modal.getBtnAceptar().setEnabled(true);
			if (getEstado().equals(VISTA)) {
				modal.getBtnAceptar().setEnabled(false);
			} else {
				if (estado.equals(VISTA))
					modal.getBtnAceptar().setEnabled(false);
			}
			
			ModalInternalPanel.showInternalDialog(this, modal, null);

			if (modal.model != null) {
				int rows = data.length;
				// Borrar los referenciados a la solicitud
				int size = ddordencompras.size();
				for (int ii = 0; ii < size; ii++) {
					DDOrdenCompra o = ddordencompras.get(ii);

					if (o.getTipo_referencia().equals("SLC_COMPRA")
							&& o.getId_referencia() == solicitudCompra
									.getIdsolicitudcompra()) {

						ddordencompras.remove(ii);
						ii = 0;
						size = ddordencompras.size();
					}
				}

				for (int row = 0; row < rows; row++) {
					String idproducto;
					float cantidad;

					idproducto = modal.model.getValueAt(row, 0).toString();
					try {
						cantidad = Float.parseFloat(modal.model.getValueAt(row,
								4).toString());
					} catch (Exception e) {
						cantidad = 0.0F;
					}

					// Agregar los que tienen cantidad dif. de cero

					// Reiniciar los productos del consolidado
					salir: for (int iii = 0; iii < getConsolidadoTM()
							.getRowCount(); iii++) {
						if (getConsolidadoTM().getValueAt(iii, 0).toString()
								.trim().equals(idproducto)) {
							getConsolidadoTM().setValueAt(0.0F, iii, 4);
							break salir;
						}
					}

					if (cantidad > 0) {
						DDOrdenCompra dd = new DDOrdenCompra();
						Producto p = productoDAO.find(idproducto);
						dd.setTipo_referencia("SLC_COMPRA");
						dd.setId_referencia(solicitudCompra
								.getIdsolicitudcompra());
						dd.setProducto(p);
						dd.setCantidad(cantidad);
						ddordencompras.add(dd);
					}
				}
			}
			actualizarConsolidado();
		} else {
			UtilMensajes.mensaje_alterta("DOC_ATENDIDO_TOTAL");
		}
	}

	private void referenciarCotizacionCompra(CotizacionCompra cotizacionCompra,
			final String estado) {
		List<Tuple> saldos = new KardexSlcCompraDAO().getSaldoPorOrigen(
				cotizacionCompra.getIdcotizacioncompra(), getOrdencompra());

		if (saldos != null && saldos.size() > 0) {

			Object[][] data = new Object[saldos.size()][5];
			int i = 0;
			for (Tuple t : saldos) {

				Producto p = (Producto) t.get("producto");
				Unimedida u = (Unimedida) t.get("unimedida");
				float cantidad = (float) t.get("cantidad");
				data[i][0] = p.getIdproducto();
				data[i][1] = p.getDescripcion();
				data[i][2] = u.getDescripcion();
				data[i][3] = cantidad;
				data[i][4] = cantidadProducto(p, "COT_COMPRA",
						cotizacionCompra.getIdcotizacioncompra());
				i++;
			}

			DSGTableModel model = new DSGTableModel(new String[] {
					"Cód Producto", "Producto", "U.M.", "Saldo", "Cantidad" }) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean evaluaEdicion(int row, int column) {
					if (column == 4 && !estado.equals(VISTA))
						return true;
					return false;
				}

				@Override
				public void addRow() {
				}
			};

			ModalDetalleReferencia modal = new ModalDetalleReferencia(model, data) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validaModelo() {
					for (int i = 0; i < this.model.getRowCount(); i++) {
						String producto;
						producto = this.model.getValueAt(i, 1).toString();

						float saldo = 0.0F, cantidad = 0.0F;
						try {
							saldo = Float.parseFloat(this.model
									.getValueAt(i, 3).toString());
						} catch (Exception e) {
							saldo = 0;
						}
						try {
							cantidad = Float.parseFloat(this.model.getValueAt(
									i, 4).toString());
						} catch (Exception e) {
							cantidad = 0;
						}
						if (cantidad > saldo) {
							UtilMensajes.mensaje_alterta(
									"CANTIDAD_MENOR_SALDO", producto);
							return false;
						}
					}
					return true;
				}
			};

			TableColumnModel tc = modal.getTable().getColumnModel();

			tc.getColumn(3).setCellRenderer(new FloatRenderer(3));

			tc.getColumn(4).setCellRenderer(new FloatRenderer(3));
			tc.getColumn(4).setCellEditor(new FloatEditor(3));

			modal.getBtnAceptar().setEnabled(true);
			if (getEstado().equals(VISTA)) {
				modal.getBtnAceptar().setEnabled(false);
			} else {
				if (estado.equals(VISTA))
					modal.getBtnAceptar().setEnabled(false);
			}
//			modal.setModal(true);
			// Sys.desktoppane.add(modal);
//			modal.setVisible(true);
			ModalInternalPanel.showInternalDialog(this, modal, null);

			if (modal.model != null) {
				int rows = data.length;
				// Borrar los referenciados a la solicitud
				int size = ddordencompras.size();
				for (int ii = 0; ii < size; ii++) {
					DDOrdenCompra o = ddordencompras.get(ii);

					if (o.getTipo_referencia().equals("COT_COMPRA")
							&& o.getId_referencia() == cotizacionCompra
									.getIdcotizacioncompra()) {

						ddordencompras.remove(ii);
						ii = 0;
						size = ddordencompras.size();
					}
				}

				for (int row = 0; row < rows; row++) {
					String idproducto;
					float cantidad;

					idproducto = modal.model.getValueAt(row, 0).toString();
					try {
						cantidad = Float.parseFloat(modal.model.getValueAt(row,
								4).toString());
					} catch (Exception e) {
						cantidad = 0.0F;
					}

					// Agregar los que tienen cantidad dif. de cero

					// Reiniciar los productos del consolidado
					salir: for (int iii = 0; iii < getConsolidadoTM()
							.getRowCount(); iii++) {
						if (getConsolidadoTM().getValueAt(iii, 0).toString()
								.trim().equals(idproducto)) {
							getConsolidadoTM().setValueAt(0.0F, iii, 4);
							break salir;
						}
					}

					if (cantidad > 0) {
						DDOrdenCompra dd = new DDOrdenCompra();
						Producto p = productoDAO.find(idproducto);
						dd.setTipo_referencia("COT_COMPRA");
						dd.setId_referencia(cotizacionCompra
								.getIdcotizacioncompra());
						dd.setProducto(p);
						dd.setCantidad(cantidad);
						ddordencompras.add(dd);
					}
				}
			}
			actualizarConsolidado();
		} else {
			UtilMensajes.mensaje_alterta("DOC_ATENDIDO_TOTAL");
		}
	}

	private void actualizarConsolidado() {
		// A los productos que estan, agregar los referenciados
		for (DDOrdenCompra o : ddordencompras) {
			String idproducto = o.getProducto().getIdproducto();
			// Reiniciar los productos del consolidado
			salir: for (int iii = 0; iii < getConsolidadoTM().getRowCount(); iii++) {
				if (getConsolidadoTM().getValueAt(iii, 0).toString().trim()
						.equals(idproducto)) {
					getConsolidadoTM().setValueAt(0.0F, iii, 4);
					break salir;
				}
			}
		}
		for (DDOrdenCompra o : ddordencompras) {

			String idproducto = o.getProducto().getIdproducto();
			Producto producto = o.getProducto();
			float cantidad = o.getCantidad();
			boolean hayProducto = false;
			// Buscar si existe actualizar cantidad, sino agregar
			salir: for (int i = 0; i < getConsolidadoTM().getRowCount(); i++) {
				if (idproducto.equals(getConsolidadoTM().getValueAt(i, 0)
						.toString().trim())) {
					float cant_ant = Float.parseFloat(getConsolidadoTM()
							.getValueAt(i, 4).toString());
					getConsolidadoTM().setValueAt(cant_ant + cantidad, i, 4);
					hayProducto = true;
					break salir;
				}
			}

			if (!hayProducto) {
				List<ProductoImpuesto> imptos = pimptoDAO
						.getPorProducto(producto);
				float impto = 0.0F;

				for (ProductoImpuesto pi : imptos) {
					Impuesto i = impuestoDAO.find(pi.getId().getIdimpuesto());
					impto += i.getTasa();
				}
				Object[] rowData = new Object[] { producto.getIdproducto(),
						producto.getDescripcion(),
						producto.getUnimedida().getIdunimedida(),
						producto.getUnimedida().getDescripcion(), cantidad,
						0.00, 0.00, 0.00, 0.00, impto, 0.00, 0.00 };
				getConsolidadoTM().addRow(rowData);
			}
		}
		for (int row = 0; row < tblConsolidado.getRowCount(); row++) {
			tblConsolidado.setRowSelectionInterval(0, 0);
			actualiza_detalle();
		}
	}

	private float cantidadProducto(Producto producto, String tipo_referencia,
			long id_referencia) {
		float cantidad = 0.0F;
		for (DDOrdenCompra o : ddordencompras) {
			if (o.getTipo_referencia().equals(tipo_referencia)
					&& o.getId_referencia() == id_referencia
					&& o.getProducto().getIdproducto()
							.equals(producto.getIdproducto()))
				cantidad = o.getCantidad();
		}
		return cantidad;
	}

	@Override
	protected void limpiarVista() {
		txtNumero.setValue(0);
		txtSerie.setText("");
		txtTCambio.setValue(0);
		txtTCMoneda.setValue(0);
		cntMoneda.llenar();
		cntResponsable.txtCodigo.setText("");
		cntResponsable.llenar();

		cntClieprov.setText("");
		cntClieprov.llenar();

		getConsolidadoTM().limpiar();
	}

	public DSGTableModel getConsolidadoTM() {
		return ((DSGTableModel) tblConsolidado.getModel());
	}

	public OrdenCompra getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(OrdenCompra ordencompra) {
		this.ordencompra = ordencompra;
	}

	@Override
	public void initBarra() {
		int AnchoCabecera = 850;
		barra = new PanelBarraDocumento('E');
		barra.setMinimumSize(new Dimension(AnchoCabecera, 40));
		barra.setPreferredSize(new Dimension(AnchoCabecera, 40));
		barra.setBounds(0, 0, AnchoCabecera, 42);
		barra.setFormMaestro(this);
		FlowLayout flowLayout = (FlowLayout) barra.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(barra, BorderLayout.NORTH);
	}

	@Override
	protected String getNombreArchivo() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return "OC " + this.txtSerie.getText() + "-" + this.txtNumero.getText()
				+ "_" + format.format(c.getTime());
	}

	@Override
	protected String getNombreReporte() {
		return "OrdenCompra";
	}

	@Override
	protected Map<String, Object> getParamsReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empresa", Sys.empresa);
		map.put("ordencompra", ordencompra);
		return map;
	}

	@Override
	protected JRDataSource getDataSourceReport() {
		List<DOrdenCompra> doc = dordencompraDAO.getPorOrdenCompra(ordencompra);
		return new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
				doc);
	}
}
