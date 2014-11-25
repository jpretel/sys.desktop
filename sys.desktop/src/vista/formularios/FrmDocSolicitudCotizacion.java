package vista.formularios;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.JRDataSource;
import vista.Sys;
import vista.barras.PanelBarraDocumento;
import vista.contenedores.CntClieprov;
import vista.controles.DSGTableModel;
import vista.controles.DSGTextFieldCorrelativo;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.abstractforms.AbstractDocForm;
import vista.formularios.modal.ModalDetalleReferencia;
import vista.formularios.modal.ModalInternalPanel;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.dao.ClieprovDAO;
import core.dao.DSolicitudCompraDAO;
import core.dao.DSolicitudCotizacionDAO;
import core.dao.ProductoDAO;
import core.dao.SolicitudCompraDAO;
import core.dao.SolicitudCotizacionDAO;
import core.dao.UnimedidaDAO;
import core.entity.DSolicitudCompra;
import core.entity.DSolicitudCotizacion;
import core.entity.DSolicitudCotizacionPK;
import core.entity.Producto;
import core.entity.SolicitudCompra;
import core.entity.SolicitudCotizacion;
import core.entity.Unimedida;

public class FrmDocSolicitudCotizacion extends AbstractDocForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SolicitudCotizacionDAO solicitudDAO = new SolicitudCotizacionDAO();
	private DSolicitudCotizacionDAO dsolicitudDAO = new DSolicitudCotizacionDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private ClieprovDAO clieprovDAO = new ClieprovDAO();
	private SolicitudCompraDAO sCompraDAO = new SolicitudCompraDAO();
	private DSolicitudCompraDAO dCompraDAO = new DSolicitudCompraDAO();

	private TxtProducto txtProducto;
	private JLabel lblGlosa;
	private JScrollPane scrollPaneDetalle;
	private JScrollPane scrlGlosa;
	private JTextArea txtGlosa;
	private JTable tblDetalle;

	private SolicitudCotizacion solicitud;
	private List<DSolicitudCotizacion> dsolicitud = new ArrayList<DSolicitudCotizacion>();

	private JLabel lblProveedor;
	private CntClieprov cntClieprov;
	private DSGTextFieldCorrelativo txtSerieRef;
	private DSGTextFieldCorrelativo txtNumeroRef;
	private JButton btnRefSal;
	private JLabel lblSolicitudDeCompra;

	public FrmDocSolicitudCotizacion() {
		super("Solicitud de Cotizacion");
		setSize(new Dimension(700, 370));
		setPreferredSize(new Dimension(700, 380));
		setMinimumSize(new Dimension(700, 380));

		txtFecha.setBounds(245, 11, 101, 22);
		txtNumero.setBounds(116, 12, 80, 20);
		txtSerie.setBounds(72, 12, 44, 20);

		setEstado(VISTA);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 874, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGap(0, 681, Short.MAX_VALUE));

		this.lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(397, 12, 32, 16);

		this.scrollPaneDetalle = new JScrollPane((Component) null);
		this.scrollPaneDetalle.setBounds(10, 94, 664, 234);

		tblDetalle = new JTable(new DSGTableModel(new String[] {
				"Cód. Producto", "Producto", "Cod. Medida", "Medida",
				"Cantidad" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (solicitud.getSolicitudcompra() != null)
					return false;
				if (column == 1 || column == 2 || column == 3)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				if (validaCabecera())
					addRow(new Object[] { "", "", "", "", 0.0 });
				else
					JOptionPane.showMessageDialog(null,
							"Faltan datos en la cabecera");
			}

			@Override
			public boolean isValidaAgregar() {
				if (solicitud.getSolicitudcompra() != null)
					return false;
				return true;
			};

			@Override
			public boolean isValidaEliminacion(int row) {
				if (solicitud.getSolicitudcompra() != null)
					return false;
				return true;
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
					String idproducto = getDetalleTM().getValueAt(row, 0)
							.toString();
					txtProducto.refresValue(idproducto);
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

		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setRepetidos(0);
		getDetalleTM().setScrollAndTable(scrollPaneDetalle, tblDetalle);

		this.tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPaneDetalle.setViewportView(this.tblDetalle);

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(433, 12, 227, 45);

		this.txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);
		pnlPrincipal.setLayout(null);
		pnlPrincipal.add(this.lblGlosa);
		pnlPrincipal.add(this.scrlGlosa);
		pnlPrincipal.add(this.scrollPaneDetalle);

		this.lblProveedor = new JLabel("Proveedor");
		this.lblProveedor.setBounds(10, 63, 59, 16);
		pnlPrincipal.add(this.lblProveedor);

		this.cntClieprov = new CntClieprov();
		this.cntClieprov.setBounds(72, 63, 318, 20);
		pnlPrincipal.add(this.cntClieprov);

		cntClieprov.setData(clieprovDAO.findAll());

		this.txtSerieRef = new DSGTextFieldCorrelativo(4);
		this.txtSerieRef.setColumns(10);
		this.txtSerieRef.setBounds(514, 63, 44, 20);
		pnlPrincipal.add(this.txtSerieRef);

		this.txtNumeroRef = new DSGTextFieldCorrelativo(8);
		this.txtNumeroRef.setColumns(10);
		this.txtNumeroRef.setBounds(558, 63, 80, 20);
		pnlPrincipal.add(this.txtNumeroRef);

		this.btnRefSal = new JButton();
		this.btnRefSal.setIcon(new ImageIcon(new ImageIcon(getClass()
				.getResource("/main/resources/iconos/search.png")).getImage()
				.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)));

		btnRefSal.setMargin(new Insets(0, 0, 0, 0));

		this.btnRefSal.setBounds(640, 63, 20, 20);
		this.btnRefSal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				llenarDetalle();
			}
		});

		pnlPrincipal.add(this.btnRefSal);

		this.lblSolicitudDeCompra = new JLabel("Solicitud de Compra");
		this.lblSolicitudDeCompra.setBounds(400, 64, 104, 16);
		pnlPrincipal.add(this.lblSolicitudDeCompra);

		txtProducto.updateCellEditor();
		txtProducto.setData(productoDAO.findAll());

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				actualizaNumero(txtSerie.getText());
			}

			private void actualizaNumero(String serie) {
				int numero = solicitudDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero.setValue(numero);
					txtFecha.requestFocus();
				}
			}
		});

		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setObligatorios(0, 1, 2, 3);
		getDetalleTM().setRepetidos(0);

		TableColumnModel tc = tblDetalle.getColumnModel();

		tc.getColumn(4).setCellEditor(new FloatEditor(2));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(2));

		iniciar();
	}

	@Override
	public void nuevo() {
		int anio, mes, dia;

		Calendar c = Calendar.getInstance();
		anio = c.get(Calendar.YEAR);
		mes = c.get(Calendar.MONTH);
		dia = c.get(Calendar.DAY_OF_MONTH);

		solicitud = new SolicitudCotizacion();
		solicitud.setAnio(anio);
		solicitud.setMes(mes);
		solicitud.setDia(dia);
		solicitud.setIdsolicitudcotizacion(System.nanoTime());
		txtSerie.requestFocus();
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		solicitudDAO.crear_editar(solicitud);

		for (DSolicitudCotizacion d : dsolicitudDAO.aEliminar(solicitud,
				dsolicitud)) {
			dsolicitudDAO.remove(d);
		}

		for (DSolicitudCotizacion d : dsolicitud) {
			if (dsolicitudDAO.find(d.getId()) == null) {
				dsolicitudDAO.create(d);
			} else {
				dsolicitudDAO.edit(d);
			}
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_datos() {
		limpiarVista();

		if (solicitud != null) {
			txtNumero.setValue(solicitud.getNumero());
			txtSerie.setText(solicitud.getSerie());
			cntClieprov.setText((solicitud.getClieprov() == null) ? ""
					: solicitud.getClieprov().getIdclieprov());
			cntClieprov.llenar();
			txtGlosa.setText(solicitud.getGlosa());

			if (solicitud.getSolicitudcompra() == null) {
				txtSerieRef.setText("");
				txtNumeroRef.setValue("");
			} else {
				txtSerieRef.setText(solicitud.getSolicitudcompra().getSerie());
				txtNumeroRef.setValue(solicitud.getSolicitudcompra()
						.getNumero());
			}
			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.YEAR, solicitud.getAnio());

			cal.set(Calendar.MONTH, solicitud.getMes() - 1);

			cal.set(Calendar.DAY_OF_MONTH, solicitud.getDia());

			txtFecha.setDate(cal.getTime());

			dsolicitud = dsolicitudDAO.getPorSolicitud(solicitud);

			for (DSolicitudCotizacion d : dsolicitud) {
				Producto p = d.getProducto();
				Unimedida u = d.getUnimedida();

				getDetalleTM().addRow(
						new Object[] { p.getIdproducto(), p.getDescripcion(),
								u.getIdunimedida(), u.getDescripcion(),
								d.getCantidad() });
			}

		} else {
			dsolicitud = new ArrayList<DSolicitudCotizacion>();
		}
	}

	@Override
	public void llenar_lista() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_tablas() {

	}

	@Override
	public void vista_edicion() {
		this.txtSerie.setEditable(true);
		this.txtNumero.setEditable(true);
		this.txtFecha.setEditable(true);
		this.txtGlosa.setEditable(true);
		this.btnRefSal.setEnabled(true);
		FormValidador.TextFieldsEdicion(true, txtSerieRef, txtNumeroRef);
		FormValidador.CntEdicion(true, cntClieprov);
		getDetalleTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		this.txtSerie.setEditable(false);
		this.txtNumero.setEditable(false);
		this.txtFecha.setEditable(false);
		this.txtGlosa.setEditable(false);
		this.btnRefSal.setEnabled(false);
		FormValidador.TextFieldsEdicion(false, txtSerieRef, txtNumeroRef);
		FormValidador.CntEdicion(false, cntClieprov);
		getDetalleTM().setEditar(false);
	}

	@Override
	public void init() {

	}

	@Override
	public void actualiza_objeto(Object id) {
		setSolicitud(solicitudDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		Calendar c = Calendar.getInstance();
		c.setTime(txtFecha.getDate());

		Long idoc = solicitud.getIdsolicitudcotizacion();
		// getIngreso().setGrupoCentralizacion(cntGrupoCentralizacion.getSeleccionado());
		solicitud.setSerie(this.txtSerie.getText());
		solicitud.setNumero(Integer.parseInt(this.txtNumero.getText()));
		solicitud.setDia(c.get(Calendar.DAY_OF_MONTH));
		solicitud.setMes(c.get(Calendar.MONTH) + 1);
		solicitud.setAnio(c.get(Calendar.YEAR));
		solicitud.setFecha((c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ c.get(Calendar.DAY_OF_MONTH));
		solicitud.setClieprov(cntClieprov.getSeleccionado());
		solicitud.setGlosa(txtGlosa.getText());

		dsolicitud = new ArrayList<DSolicitudCotizacion>();

		int rows = getDetalleTM().getRowCount();

		for (int row = 0; row < rows; row++) {
			DSolicitudCotizacion d = new DSolicitudCotizacion();
			DSolicitudCotizacionPK id = new DSolicitudCotizacionPK();

			String idproducto, idunimedida;
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

			float cantidad;

			cantidad = Float.parseFloat(getDetalleTM().getValueAt(row, 4)
					.toString());

			Producto p = productoDAO.find(idproducto);
			Unimedida u = unimedidaDAO.find(idunimedida);

			id.setIdsolicitudcotizacion(idoc);
			id.setItem(row + 1);

			d.setId(id);
			d.setProducto(p);
			d.setUnimedida(u);
			d.setCantidad(cantidad);

			dsolicitud.add(d);
		}
	}

	private void llenarDetalle() {
		String serie = this.txtSerieRef.getText();
		int numero;
		try {
			numero = Integer.valueOf(this.txtNumeroRef.getText());
		} catch (Exception e) {
			numero = 0;
		}
		int nFilas = getDetalleTM().getRowCount();
		int opc = -1;
		if (nFilas > 0) {
			opc = JOptionPane
					.showConfirmDialog(
							null,
							"Se borrara el detalle actual y cargara el detalle de la solicitud",
							"¿Desea Continuar?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			solicitud.setSolicitudcompra(null);
		}
		if (opc == -1 || opc == 0) {

			getDetalleTM().setEditar(estado.equals(NUEVO));

			getDetalleTM().limpiar();
			if (serie.trim().length() > 0 && numero > 0) {

				SolicitudCompra sCompra = sCompraDAO.getPorSerieNumero(serie,
						numero);
				List<DSolicitudCompra> dCompra = new ArrayList<DSolicitudCompra>();
				if (sCompra != null) {
					dCompra = dCompraDAO.getPorSolicitudCompra(sCompra);

					Object[][] data = new Object[dCompra.size()][6];
					int i = 0;
					for (DSolicitudCompra d : dCompra) {
						data[i][0] = true;
						data[i][1] = d.getProducto().getIdproducto();
						data[i][2] = d.getProducto().getDescripcion();
						data[i][3] = d.getUnimedida().getIdunimedida();
						data[i][4] = d.getUnimedida().getDescripcion();
						data[i][5] = d.getCantidad();
						i++;
					}

					DSGTableModel model = new DSGTableModel(new String[] {
							"Elegir", "Cód Producto", "Producto", "Cod. U.M.",
							"U.M.", "Cantidad" }) {
						private static final long serialVersionUID = 1L;

						@Override
						public boolean evaluaEdicion(int row, int column) {
							if (column == 0 && !estado.equals(VISTA))
								return true;
							return false;
						}

						@Override
						public void addRow() {
						}

						@Override
						public Class<?> getColumnClass(int c) {
							if (c == 0) {
								return Boolean.class;
							}
							return super.getColumnClass(c);
						}
					};

					ModalDetalleReferencia modal = new ModalDetalleReferencia(
							model, data) {
						private static final long serialVersionUID = 1L;

						@Override
						public boolean validaModelo() {
							return true;
						}
					};

					TableColumnModel tc = modal.getTable().getColumnModel();

					tc.getColumn(5).setCellRenderer(new FloatRenderer(3));

					modal.getBtnAceptar().setEnabled(true);
					if (getEstado().equals(VISTA)) {
						modal.getBtnAceptar().setEnabled(false);
					} else {
						if (estado.equals(VISTA))
							modal.getBtnAceptar().setEnabled(false);
					}

					ModalInternalPanel.showInternalDialog(this, modal, null);

					if (modal.model != null) {
						getDetalleTM().setEditar(false);
						solicitud.setSolicitudcompra(sCompra);
						int rows = data.length;

						for (int row = 0; row < rows; row++) {
							boolean band;

							band = Boolean.parseBoolean(modal.model.getValueAt(
									row, 0).toString());
							if (band) {
								String idproducto, producto, idmedida, medida;
								float cantidad;

								idproducto = modal.model.getValueAt(row, 1)
										.toString();
								producto = modal.model.getValueAt(row, 2)
										.toString();
								idmedida = modal.model.getValueAt(row, 3)
										.toString();
								medida = modal.model.getValueAt(row, 4)
										.toString();
								try {
									cantidad = Float.parseFloat(modal.model
											.getValueAt(row, 5).toString());
								} catch (Exception e) {
									cantidad = 0.0F;
								}

								getDetalleTM().addRow(
										new Object[] { idproducto, producto,
												idmedida, medida, cantidad });
							}
						}
					}
				} else {
					UtilMensajes.mensaje_alterta("DOC_NO_ENCONTRADO");
				}
			}
		}
	}

	@Override
	public boolean isValidaVista() {
		boolean band = validaCabecera();
		return band;
	}

	public boolean validaCabecera() {
		if (!FormValidador.CntObligatorios(cntClieprov))
			return false;
		return true;
	}

	@Override
	protected void limpiarVista() {
		txtNumero.setValue(0);
		txtSerie.setText("");
		cntClieprov.setText("");
		cntClieprov.llenar();
		txtGlosa.setText("");

		txtSerieRef.setText("");
		txtNumeroRef.setValue("");

		txtFecha.setDate(Calendar.getInstance().getTime());

		getDetalleTM().limpiar();
	}

	public DSGTableModel getDetalleTM() {
		return ((DSGTableModel) tblDetalle.getModel());
	}

	public SolicitudCotizacion getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudCotizacion solicitud) {
		this.solicitud = solicitud;
	}

	public List<DSolicitudCotizacion> getDsolicitud() {
		return dsolicitud;
	}

	public void setDsolicitud(List<DSolicitudCotizacion> dsolicitud) {
		this.dsolicitud = dsolicitud;
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
		return "SolCot" + this.txtSerie.getText() + "-"
				+ this.txtNumero.getText() + "_" + format.format(c.getTime());
	}

	@Override
	protected String getNombreReporte() {
		return "SolicitudCotizacion";
	}

	@Override
	protected Map<String, Object> getParamsReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empresa", Sys.empresa);
		map.put("solicitudcotizacion", solicitud);
		return map;
	}

	@Override
	protected JRDataSource getDataSourceReport() {
		List<DSolicitudCotizacion> doc = dsolicitudDAO
				.getPorSolicitud(solicitud);
		return new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
				doc);
	}
}
