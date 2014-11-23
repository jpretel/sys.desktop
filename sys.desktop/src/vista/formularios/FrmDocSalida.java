package vista.formularios;

import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Tuple;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import vista.Sys;
import vista.contenedores.CntConcepto;
import vista.contenedores.CntGrupoCentralizacion;
import vista.contenedores.CntMoneda;
import vista.contenedores.cntAlmacen;
import vista.contenedores.cntResponsable;
import vista.contenedores.cntSucursal;
import vista.controles.CntReferenciaDoc;
import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.listas.AbstractDocForm;
import vista.formularios.modal.ModalDetalleReferencia;
import vista.utilitarios.FormValidador;
import vista.utilitarios.StringUtils;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import vista.utilitarios.renderers.ReferenciaDOC;
import vista.utilitarios.renderers.ReferenciaDOCRenderer;
import core.centralizacion.ContabilizaAlmacen;
import core.centralizacion.ContabilizaRequerimiento;
import core.dao.AlmacenDAO;
import core.dao.ConceptoDAO;
import core.dao.DetDocSalidaDAO;
import core.dao.DocsalidaDAO;
import core.dao.GrupoCentralizacionDAO;
import core.dao.KardexRequerimientoDAO;
import core.dao.MonedaDAO;
import core.dao.ProductoDAO;
import core.dao.RequerimientoDAO;
import core.dao.ResponsableDAO;
import core.dao.SucursalDAO;
import core.dao.UnimedidaDAO;
import core.entity.Almacen;
import core.entity.DetDocsalida;
import core.entity.DetDocsalidaPK;
import core.entity.Docsalida;
import core.entity.Producto;
import core.entity.Requerimiento;
import core.entity.Sucursal;
import core.entity.Unimedida;

public class FrmDocSalida extends AbstractDocForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblDetalle;
	private JScrollPane scrollPaneDetalle;
	private List<DetDocsalida> DetDocsalidaL;
	private DocsalidaDAO docSalidaDAO = new DocsalidaDAO();
	private DetDocSalidaDAO detDocsalidaDAO = new DetDocSalidaDAO();
	private MonedaDAO monedaDAO = new MonedaDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private RequerimientoDAO requerimientoDAO = new RequerimientoDAO();

	private CntConcepto cntConcepto;
	private cntResponsable cntResponsable;
	private cntSucursal cntSucursal;
	private cntAlmacen cntAlmacen;
	private cntSucursal cntSucursal_dest;
	private cntAlmacen cntAlmacen_dest;
	private JTextArea txtGlosa;
	private Docsalida salida;
	private Calendar calendar = Calendar.getInstance();
	private CntGrupoCentralizacion cntGrupoCentralizacion;
	private JScrollPane scrlGlosa;
	private JLabel lblOperacin;
	private TxtProducto txtProducto;
	private JLabel lblMoneda;
	private CntMoneda cntMoneda;
	private JLabel label_1;
	private CntReferenciaDoc cntReferenciaDoc;

	public FrmDocSalida() {
		super("Nota de Salida");

		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setBounds(359, 43, 54, 16);
		pnlPrincipal.add(lblConcepto);

		JLabel lblResponsable = new JLabel("Responsable");
		lblResponsable.setBounds(12, 133, 74, 16);
		pnlPrincipal.add(lblResponsable);

		JLabel lblSucursal = new JLabel("Sucursal");
		lblSucursal.setBounds(12, 71, 51, 16);
		pnlPrincipal.add(lblSucursal);

		JLabel lblAlmacen = new JLabel("Almacen");
		lblAlmacen.setBounds(465, 71, 50, 16);
		pnlPrincipal.add(lblAlmacen);

		JLabel lblGlosa = new JLabel("Observacion");
		lblGlosa.setBounds(465, 133, 68, 16);
		pnlPrincipal.add(lblGlosa);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(540, 5, 0, 16);
		pnlPrincipal.add(textArea);

		tblDetalle = new JTable(new DSGTableModel(new String[] {
				"Cód. Producto", "Producto", "Cód. Medida", "Medida",
				"Cantidad", "Referencia" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 3 || column == 5)
					return false;
				if (column == 4) {
					ReferenciaDOC ref = (ReferenciaDOC) getValueAt(row, 5);
					if (ref != null) {
						return false;
					}
				}
				return getEditar();
			}

			@Override
			public void addRow() {
				if (validaCabecera())
					addRow(new Object[] { "", "", "", "", 0, null });
				else
					JOptionPane.showMessageDialog(null,
							"Faltan datos en la cabecera");
			}
		}) {
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

		tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneDetalle = new JScrollPane(tblDetalle);
		scrollPaneDetalle.setBounds(12, 197, 824, 187);

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
			}
		};
		txtProducto.updateCellEditor();

		getDetalleTM().setNombre_detalle("Detalle de Productos");
		getDetalleTM().setObligatorios(0, 1, 4);
		getDetalleTM().setRepetidos(0);
		getDetalleTM().setScrollAndTable(scrollPaneDetalle, tblDetalle);

		TableColumnModel tc = tblDetalle.getColumnModel();

		tc.getColumn(4).setCellEditor(new FloatEditor(3));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(3));

		tc.getColumn(5).setCellRenderer(new ReferenciaDOCRenderer());

		pnlPrincipal.add(scrollPaneDetalle);

		cntConcepto = new CntConcepto();

		cntConcepto.setBounds(410, 40, 338, 20);
		pnlPrincipal.add(cntConcepto);

		cntConcepto.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent evt) {
				if (cntConcepto.txtCodigo.getText().trim().length() > 0) {
					if (cntConcepto.getSeleccionado().getEsTransferencia() == 1) {
						cntSucursal_dest.setEditable(true);
						cntAlmacen_dest.setEditable(true);
					} else {
						cntSucursal_dest.txtCodigo.setText("");
						cntSucursal_dest.setEditable(false);
						cntAlmacen_dest.txtCodigo.setText("");
						cntAlmacen_dest.setEditable(false);
					}
				}
			}
		});

		cntResponsable = new cntResponsable();
		cntResponsable.setBounds(116, 129, 297, 20);
		pnlPrincipal.add(cntResponsable);

		cntSucursal = new cntSucursal();
		cntSucursal.btnBuscar.setLocation(90, 3);
		cntSucursal.setBounds(116, 71, 297, 20);
		pnlPrincipal.add(cntSucursal);

		cntAlmacen = new cntAlmacen();
		cntAlmacen.setBounds(557, 71, 278, 20);
		pnlPrincipal.add(cntAlmacen);

		txtSerie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg) {
				String serie = txtSerie.getText().trim();
				if (serie.length() > 0) {
					String texto = org.codehaus.plexus.util.StringUtils.repeat(
							"0", 4 - serie.length()) + serie;
					txtSerie.setText(texto);
					actualizaNumero(txtSerie.getText());
				}
			}

			private void actualizaNumero(String serie) {
				int numero = docSalidaDAO.getPorSerie(serie);
				numero = numero + 1;
				if (numero > 0) {
					txtNumero_2.setText(String.valueOf(numero));
					txtNumero_2.requestFocus();
					txtFecha.requestFocus();
				}
			}
		});

		cntSucursal.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (cntSucursal.txtCodigo.getText().trim().length() > 0) {
					cntAlmacen.setData(new AlmacenDAO()
							.getPorSucursal(cntSucursal.getSeleccionado()));
				}
			}
		});

		cntSucursal_dest = new cntSucursal();

		cntSucursal_dest.setBounds(116, 102, 297, 20);
		pnlPrincipal.add(cntSucursal_dest);

		JLabel lblSucursalDestino = new JLabel("Sucursal Destino");
		lblSucursalDestino.setBounds(12, 98, 85, 16);
		pnlPrincipal.add(lblSucursalDestino);

		JLabel lblAlmacenDestino = new JLabel("Almacen Destino");
		lblAlmacenDestino.setBounds(465, 106, 85, 16);
		pnlPrincipal.add(lblAlmacenDestino);

		/* cntAlmacen cntAlmacen_ = new cntAlmacen(); */
		cntAlmacen_dest = new cntAlmacen();

		cntAlmacen_dest.setBounds(557, 102, 278, 20);
		pnlPrincipal.add(cntAlmacen_dest);

		cntSucursal_dest.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (cntSucursal_dest.txtCodigo.getText().trim().length() > 0) {
					cntAlmacen_dest.setData(new AlmacenDAO()
							.getPorSucursal(cntSucursal_dest.getSeleccionado()));
				}
			}
		});

		this.cntGrupoCentralizacion = new CntGrupoCentralizacion();
		this.cntGrupoCentralizacion.setBounds(72, 43, 237, 20);
		pnlPrincipal.add(this.cntGrupoCentralizacion);

		this.scrlGlosa = new JScrollPane();
		this.scrlGlosa.setBounds(557, 133, 279, 53);
		pnlPrincipal.add(this.scrlGlosa);

		txtGlosa = new JTextArea();
		this.scrlGlosa.setViewportView(this.txtGlosa);

		this.lblOperacin = new JLabel("Operaci\u00F3n");
		this.lblOperacin.setBounds(9, 45, 54, 16);
		pnlPrincipal.add(this.lblOperacin);

		this.lblMoneda = new JLabel("Moneda");
		this.lblMoneda.setBounds(369, 15, 54, 16);
		pnlPrincipal.add(this.lblMoneda);

		this.cntMoneda = new CntMoneda();
		this.cntMoneda.setBounds(434, 12, 192, 20);
		pnlPrincipal.add(this.cntMoneda);

		this.label_1 = new JLabel("Referencia");
		this.label_1.setBounds(12, 170, 54, 16);
		pnlPrincipal.add(this.label_1);

		this.cntReferenciaDoc = new CntReferenciaDoc() {
			private static final long serialVersionUID = 1L;

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
					referenciarRequerimiento(serie, numero);
					txtSerie.setText("");
					txtNumero.setText("");
				} else {
					UtilMensajes.mensaje_alterta("COMPL_SERIE_NUMERO_BUSQUEDA");
					txtSerie.requestFocus();
				}
			}

			public Object[][] getData() {
				return (Object[][]) null;
			}

			public void mostrarDetalleRef(Object[] row) {
			}
		};
		GridBagLayout gridBagLayout = (GridBagLayout) this.cntReferenciaDoc
				.getLayout();
		gridBagLayout.rowWeights = new double[] { 0.0 };
		gridBagLayout.rowHeights = new int[] { 20 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0 };
		gridBagLayout.columnWidths = new int[] { 46, 94, 20, 20 };
		this.cntReferenciaDoc.setBounds(76, 168, 180, 20);
		pnlPrincipal.add(this.cntReferenciaDoc);
		iniciar();
	}

	protected void referenciarRequerimiento(String serie, int numero) {
		Requerimiento requerimiento = requerimientoDAO.getPorSerieNumero(serie,
				numero);

		if (requerimiento != null) {
			referenciarRequerimiento(requerimiento, "EDICION");
		} else {
			UtilMensajes.mensaje_alterta("DOC_NO_ENCONTRADO");
		}
	}

	private void referenciarRequerimiento(Requerimiento requerimiento,
			final String estado) {
		List<Tuple> saldos = new KardexRequerimientoDAO()
				.getSaldoRequerimiento(requerimiento, salida);

		if (saldos != null && saldos.size() > 0) {

			Object[][] data = new Object[saldos.size()][4];
			int i = 0;
			for (Tuple t : saldos) {

				Producto p = (Producto) t.get("producto");
				// Unimedida u = (Unimedida) t.get("unimedida");
				float cantidad = (float) t.get("cantidad");
				data[i][0] = p.getIdproducto();
				data[i][1] = p.getDescripcion();
				data[i][2] = cantidad;
				data[i][3] = cantidadProducto(p, "REQINTERNO",
						requerimiento.getIdrequerimiento());
				i++;
			}

			final DSGTableModel modelo = new DSGTableModel(new String[] {
					"Cód Producto", "Producto", "Saldo", "Cantidad" }) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean evaluaEdicion(int row, int column) {
					if (column == 3 && !estado.equals(VISTA))
						return true;
					return false;
				}

				@Override
				public void addRow() {
				}
			};

			ModalDetalleReferencia modal = new ModalDetalleReferencia(this,
					modelo, data) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validaModelo() {
					for (int i = 0; i < this.model.getRowCount(); i++) {
						String producto;
						producto = this.model.getValueAt(i, 1).toString();

						float saldo = 0.0F, cantidad = 0.0F;
						try {
							saldo = Float.parseFloat(this.model
									.getValueAt(i, 2).toString());
						} catch (Exception e) {
							saldo = 0;
						}
						try {
							cantidad = Float.parseFloat(this.model.getValueAt(
									i, 3).toString());
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

			tc.getColumn(2).setCellRenderer(new FloatRenderer(3));

			tc.getColumn(3).setCellRenderer(new FloatRenderer(3));
			tc.getColumn(3).setCellEditor(new FloatEditor(3));

			modal.getBtnAceptar().setEnabled(true);
			if (getEstado().equals(VISTA)) {
				modal.getBtnAceptar().setEnabled(false);
			} else {
				if (estado.equals(VISTA))
					modal.getBtnAceptar().setEnabled(false);
			}
			modal.setModal(true);
			Sys.desktoppane.add(modal);
			modal.setVisible(true);

			if (modal.model != null) {
				int rows = data.length;
				// Borrar los referenciados al requerimiento
				int size = getDetalleTM().getRowCount();
				for (int ii = 0; ii < size; ii++) {
					ReferenciaDOC ref = (ReferenciaDOC) getDetalleTM()
							.getValueAt(ii, 7);

					if (ref != null) {
						if (ref.getIdreferencia() == requerimiento
								.getIdrequerimiento()) {
							getDetalleTM().removeRow(ii);
							ii = 0;
							size = getDetalleTM().getRowCount();
						}
					}
				}

				for (int row = 0; row < rows; row++) {
					String idproducto;
					float cantidad;

					idproducto = modal.model.getValueAt(row, 0).toString();
					try {
						cantidad = Float.parseFloat(modal.model.getValueAt(row,
								3).toString());
					} catch (Exception e) {
						cantidad = 0.0F;
					}

					// Agregar los que tienen cantidad dif. de cero

					// Reiniciar los productos del consolidado
					salir: for (int iii = 0; iii < getDetalleTM().getRowCount(); iii++) {
						if (getDetalleTM().getValueAt(iii, 0).toString().trim()
								.equals(idproducto)) {
							getDetalleTM().setValueAt(0.0F, iii, 4);
							break salir;
						}
					}

					if (cantidad > 0) {
						ReferenciaDOC ref = new ReferenciaDOC();
						Producto p = productoDAO.find(idproducto);

						ref.setIdreferencia(requerimiento.getIdrequerimiento());
						ref.setTipo_referencia("REQINTERNO");

						getDetalleTM().addRow(
								new Object[] { idproducto, p.getDescripcion(),
										p.getUnimedida().getIdunimedida(),
										p.getUnimedida().getDescripcion(),
										cantidad, 0, 0, ref });
					}

				}
			}
		} else {
			UtilMensajes.mensaje_alterta("DOC_ATENDIDO_TOTAL");
		}
	}

	private float cantidadProducto(Producto p, String string,
			long idrequerimiento) {
		float cantidad = 0.0F;
		int rows = getDetalleTM().getRowCount();
		for (int row = 0; row < rows; row++) {
			ReferenciaDOC ref = (ReferenciaDOC) getDetalleTM().getValueAt(row,
					7);

			String idproducto;
			idproducto = getDetalleTM().getValueAt(row, 0).toString();
			if (ref != null) {
				if (ref.getIdreferencia() == idrequerimiento) {

					if (p.getIdproducto().equals(idproducto)) {
						try {
							cantidad = Float.parseFloat(getDetalleTM()
									.getValueAt(row, 4).toString());
						} catch (Exception e) {
							cantidad = 0.0F;
						}
					}
				}
			}
		}
		return cantidad;
	}

	@Override
	public void nuevo() {
		setSalida(new Docsalida());
		getSalida().setIddocsalida(System.nanoTime());
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		docSalidaDAO.crear_editar(getSalida());
		for (DetDocsalida det : getDetDocsalidaL()) {
			detDocsalidaDAO.crear_editar(det);
		}

		ContabilizaAlmacen.ContabilizarSalida(getSalida());
		ContabilizaRequerimiento.ContabilizarDocSalida(getSalida()
				.getIddocsalida());
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getSalida() instanceof Docsalida && !getEstado().equals("NUEVO")) {
			String numero = StringUtils._padl(getSalida().getNumero(), 8, '0');

			Sucursal sucursal, sucursal_dest;
			Almacen almacen, almacen_dest;

			sucursal = getSalida().getSucursal();
			sucursal_dest = getSalida().getSucursal_dest();

			almacen = getSalida().getAlmacen();
			almacen_dest = getSalida().getAlmacen_dest();

			this.txtNumero_2.setText(numero);
			this.txtSerie.setText(getSalida().getSerie());
			this.cntGrupoCentralizacion.txtCodigo.setText(getSalida()
					.getGrupoCentralizacion().getIdgcentralizacion());
			this.cntGrupoCentralizacion.llenar();

			this.cntMoneda.txtCodigo.setText(getSalida().getMoneda()
					.getIdmoneda());
			this.cntMoneda.llenar();

			this.cntConcepto.txtCodigo.setText(getSalida().getConcepto()
					.getIdconcepto());
			this.cntConcepto.llenar();
			// this.cntConcepto.txtDescripcion.setText(getSalida().getConcepto().getDescripcion());
			if (cntConcepto.getSeleccionado().getEsTransferencia() == 1) {
				cntSucursal_dest.txtCodigo.setEditable(true);
				cntAlmacen_dest.txtCodigo.setEditable(true);
			}

			this.cntResponsable.txtCodigo.setText(getSalida().getResponsable()
					.getIdresponsable());
			this.cntResponsable.llenar();
			// this.cntResponsable.txtDescripcion.setText(getSalida().getResponsable().getNombre());
			// this.cntSucursal.setSeleccionado(getSalida().getAlmacen().getSucursal());
			if (sucursal == null) {
				this.cntSucursal.txtCodigo.setText("");
				this.cntAlmacen.setData(null);
			} else {
				this.cntSucursal.txtCodigo.setText(sucursal.getIdsucursal());
				this.cntAlmacen.setData(almacenDAO.getPorSucursal(sucursal));
			}
			this.cntSucursal.llenar();

			this.cntAlmacen.txtCodigo.setText((almacen == null) ? "" : almacen
					.getId().getIdalmacen());
			this.cntAlmacen.llenar();

			if (sucursal_dest == null) {
				this.cntSucursal_dest.txtCodigo.setText("");
				this.cntAlmacen_dest.setData(null);
			} else {
				this.cntSucursal_dest.txtCodigo.setText(sucursal_dest
						.getIdsucursal());
				this.cntAlmacen_dest.setData(almacenDAO
						.getPorSucursal(sucursal_dest));
			}
			this.cntSucursal_dest.llenar();

			this.cntAlmacen_dest.txtCodigo.setText((almacen_dest == null) ? ""
					: almacen_dest.getId().getIdalmacen());
			this.cntAlmacen_dest.llenar();

			this.txtGlosa.setText(getSalida().getGlosa());
			calendar.set(salida.getAnio(), salida.getMes() - 1, salida.getDia());
			this.txtFecha.setDate(calendar.getTime());
			List<DetDocsalida> detDocSalidaL = detDocsalidaDAO
					.getPorIdSalida(getSalida());
			getDetalleTM().limpiar();
			for (DetDocsalida salida : detDocSalidaL) {
				Producto producto = salida.getProducto();
				String tipo_referencia;
				ReferenciaDOC ref = null;
				tipo_referencia = (salida.getTipo_referencia() == null) ? ""
						: salida.getTipo_referencia();

				if (!tipo_referencia.isEmpty()) {
					ref = new ReferenciaDOC();
					ref.setTipo_referencia(tipo_referencia);
					ref.setIdreferencia(salida.getId_referencia());
				}

				getDetalleTM().addRow(
						new Object[] { producto.getIdproducto(),
								producto.getDescripcion(),
								salida.getUnimedida().getIdunimedida(),
								salida.getUnimedida().getDescripcion(),
								salida.getCantidad(), ref });
			}
		}
	}

	public void CalculaImporte(float cantidad, float precio) {
		float xImporte = cantidad * precio;
		getDetalleTM().setValueAt(xImporte, tblDetalle.getSelectedRow(), 6);
	}

	@Override
	public void llenar_lista() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_tablas() {
		cntGrupoCentralizacion.setData(new GrupoCentralizacionDAO().findAll());
		cntMoneda.setData(monedaDAO.findAll());
		cntSucursal.setData(new SucursalDAO().findAll());
		cntConcepto.setData(new ConceptoDAO().getPorTipo("S"));
		cntResponsable.setData(new ResponsableDAO().findAll());
		txtProducto.setData(new ProductoDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		this.txtSerie.setEditable(true);
		this.txtNumero_2.setEditable(true);
		this.txtFecha.setEditable(true);
		this.cntGrupoCentralizacion.txtCodigo.setEditable(true);
		this.cntMoneda.txtCodigo.setEditable(true);
		this.cntConcepto.txtCodigo.setEditable(true);
		this.cntResponsable.txtCodigo.setEditable(true);
		this.cntSucursal.txtCodigo.setEditable(true);
		this.cntAlmacen.txtCodigo.setEditable(true);
		this.txtGlosa.setEditable(true);
		this.cntReferenciaDoc.setEditar(true);
		getDetalleTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		this.txtSerie.setEditable(false);
		this.txtNumero_2.setEditable(false);
		this.txtFecha.setEditable(false);
		this.cntGrupoCentralizacion.txtCodigo.setEditable(false);
		this.cntMoneda.txtCodigo.setEditable(false);
		this.cntConcepto.txtCodigo.setEditable(false);
		this.cntResponsable.txtCodigo.setEditable(false);
		this.cntSucursal.txtCodigo.setEditable(false);
		this.cntAlmacen.txtCodigo.setEditable(false);
		this.txtGlosa.setEditable(false);
		this.cntSucursal_dest.txtCodigo.setEditable(false);
		this.cntAlmacen_dest.txtCodigo.setEditable(false);
		this.cntReferenciaDoc.setEditar(false);
		getDetalleTM().setEditar(false);
	}

	@Override
	public void init() {

	}

	@Override
	public void llenarDesdeVista() {
		Long id = getSalida().getIddocsalida();

		Calendar cal = Calendar.getInstance();

		cal.setTime(this.txtFecha.getDate());

		getSalida().setIddocsalida(id);
		getSalida().setGrupoCentralizacion(
				cntGrupoCentralizacion.getSeleccionado());
		getSalida().setSerie(this.txtSerie.getText());
		getSalida().setNumero(Integer.parseInt(this.txtNumero_2.getText()));
		getSalida().setConcepto(this.cntConcepto.getSeleccionado());
		getSalida().setMoneda(cntMoneda.getSeleccionado());
		getSalida().setResponsable(this.cntResponsable.getSeleccionado());
		getSalida().setSucursal(this.cntSucursal.getSeleccionado());
		getSalida().setAlmacen(this.cntAlmacen.getSeleccionado());
		getSalida().setDia(cal.get(Calendar.DAY_OF_MONTH));
		getSalida().setMes(cal.get(Calendar.MONTH) + 1);
		getSalida().setAnio(cal.get(Calendar.YEAR));
		getSalida()
				.setFecha(
						((cal.get(Calendar.DAY_OF_MONTH)) * 10000)
								+ ((cal.get(Calendar.MONTH) + 1) * 100)
								+ Calendar.YEAR);
		getSalida().setGlosa(txtGlosa.getText());
		getSalida().setSucursal_dest(cntSucursal_dest.getSeleccionado());
		getSalida().setAlmacen_dest(cntAlmacen_dest.getSeleccionado());
		setDetDocsalidaL(new ArrayList<DetDocsalida>());
		for (int i = 0; i < getDetalleTM().getRowCount(); i++) {
			String idunimedida, idproducto;
			Producto p;
			Unimedida u;
			idproducto = getDetalleTM().getValueAt(i, 0).toString();
			idunimedida = getDetalleTM().getValueAt(i, 2).toString();

			p = productoDAO.find(idproducto);
			u = unimedidaDAO.find(idunimedida);

			DetDocsalidaPK detPK = new DetDocsalidaPK();
			DetDocsalida det = new DetDocsalida();
			detPK.setIdsalida(id);
			detPK.setItem(i + 1);
			det.setId(detPK);
			det.setDocsalida(getSalida());
			det.setProducto(p);
			det.setUnimedida(u);
			det.setCantidad(Float.parseFloat((getDetalleTM().getValueAt(i, 4)
					.toString())));
			/*
			 * Referencia
			 */
			ReferenciaDOC ref = (ReferenciaDOC) getDetalleTM().getValueAt(i, 5);
			if (ref != null) {
				det.setTipo_referencia(ref.getTipo_referencia());
				det.setId_referencia(ref.getIdreferencia());
			}
			getDetDocsalidaL().add(det);
		}
	}

	@Override
	public boolean isValidaVista() {
		boolean band = validaCabecera();
		if (band) {
			band = validarDetalle();
		}
		return band;
	}

	public boolean validaCabecera() {

		return FormValidador.CntObligatorios(cntGrupoCentralizacion, cntMoneda,
				cntConcepto, cntResponsable, cntSucursal, cntAlmacen);
	}

	public boolean validarDetalle() {
		return getDetalleTM().esValido();
	}

	@Override
	protected void limpiarVista() {
		this.txtNumero_2.setText("");
		this.txtSerie.setText("");
		this.cntGrupoCentralizacion.txtCodigo.setText("");
		this.cntGrupoCentralizacion.llenar();

		this.cntMoneda.txtCodigo.setText("");
		this.cntMoneda.llenar();

		this.cntConcepto.txtCodigo.setText("");
		this.cntConcepto.llenar();

		this.cntResponsable.txtCodigo.setText("");
		this.cntResponsable.llenar();

		this.cntSucursal.txtCodigo.setText("");
		this.cntSucursal.llenar();

		this.cntAlmacen.setData(null);
		this.cntAlmacen.setText("");
		this.cntAlmacen.llenar();

		this.cntSucursal_dest.setText("");
		this.cntSucursal_dest.llenar();
		this.cntAlmacen_dest.setData(null);

		this.cntAlmacen_dest.setText("");
		this.cntAlmacen_dest.llenar();

		this.txtGlosa.setText("");

		this.txtFecha.setDate(Calendar.getInstance().getTime());

		getDetalleTM().limpiar();

	}

	public DSGTableModel getDetalleTM() {
		return ((DSGTableModel) tblDetalle.getModel());
	}

	public List<DetDocsalida> getDetDocsalidaL() {
		return DetDocsalidaL;
	}

	public void setDetDocsalidaL(List<DetDocsalida> detDocsalidaL) {
		DetDocsalidaL = detDocsalidaL;
	}

	public Docsalida getSalida() {
		return salida;
	}

	public void setSalida(Docsalida salida) {
		this.salida = salida;
	}

	@Override
	public void actualiza_objeto(Object id) {
		setSalida(docSalidaDAO.find(id));
		llenar_datos();

		getBarra().enVista();
		vista_noedicion();
	}
}
