package vista.formularios.maestros;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import vista.contenedores.cntGrupo;
import vista.contenedores.cntMarca;
import vista.contenedores.cntMedida;
import vista.contenedores.cntSubGrupo;
import vista.controles.DSGTableModel;
import vista.controles.DSGVNDetalle;
import vista.controles.JTextFieldLimit;
import vista.controles.celleditor.TxtAlmacen;
import vista.controles.celleditor.TxtImpuesto;
import vista.controles.celleditor.TxtSucursal;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.dao.AlmacenDAO;
import core.dao.ClieprovDAO;
import core.dao.GrupoDAO;
import core.dao.ImpuestoDAO;
import core.dao.MarcaDAO;
import core.dao.ProductoDAO;
import core.dao.ProductoImpuestoDAO;
import core.dao.ProductoStockMinimoDAO;
import core.dao.SubgrupoDAO;
import core.dao.SucursalDAO;
import core.dao.UnimedidaDAO;
import core.entity.Almacen;
import core.entity.Grupo;
import core.entity.Impuesto;
import core.entity.Producto;
import core.entity.ProductoImpuesto;
import core.entity.ProductoStockMinimo;
import core.entity.Subgrupo;
import core.entity.Sucursal;
import vista.contenedores.CntClieprov;

public class FrmProductos extends AbstractMaestro {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	public final cntGrupo cntgrupo;
	public final cntSubGrupo cntSubGrupo;
	public final cntMarca cntmarca;
	public final cntMedida cntmedida;
	private Producto producto;// = new Producto()
	private ProductoDAO productoDAO = new ProductoDAO();
	private ImpuestoDAO impuestoDAO = new ImpuestoDAO();
	private TxtImpuesto txtimpuesto;
	private List<ProductoImpuesto> impuestos;
	private List<ProductoStockMinimo> stockMinimo;
	private ProductoImpuestoDAO productoimpuestoDAO = new ProductoImpuestoDAO();
	private ProductoStockMinimoDAO productoStockMinimoDAO = new ProductoStockMinimoDAO();

	private JTextField txtnomcorto;
	private JScrollPane scrlImpuestos;
	private JTable tblImpuestos;
	private JTable tblStockMinimo;
	private JCheckBox chkServicio;
	private JLabel lblImpuestos;
	private JLabel lblStockMnimo;
	private JScrollPane scrlStockMinimo;
	private TxtSucursal txtsucursal;
	private TxtAlmacen txtalmacen;
	private SucursalDAO sucursalDAO = new SucursalDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private JLabel lblCodigoAlternativo;
	private JTextField txtCodAlternativo;
	private JLabel lblNomOriginal;
	private JTextField txtNomOriginal;
	private CntClieprov cntFabricante;
	private JLabel lblFabricante;

	private ClieprovDAO clieprovDAO = new ClieprovDAO();

	public FrmProductos() {
		super("Productos");
		setSize(new Dimension(582, 386));

		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		tabPanel.setBounds(10, 11, 550, 305);

		JPanel panel_1 = new JPanel();
		tabPanel.addTab("Datos Generales del Producto", null, panel_1, null);
		panel_1.setLayout(null);
		JLabel lblGrupoDeProductos = new JLabel("Familia");
		lblGrupoDeProductos.setBounds(5, 10, 46, 14);
		panel_1.add(lblGrupoDeProductos);

		cntgrupo = new cntGrupo();

		cntgrupo.setBounds(61, 9, 216, 20);
		panel_1.add(cntgrupo);

		JLabel lblSubgrupoDeProductos = new JLabel("Sub Grupo");
		lblSubgrupoDeProductos.setBounds(5, 37, 61, 14);
		panel_1.add(lblSubgrupoDeProductos);

		cntSubGrupo = new cntSubGrupo();

		cntSubGrupo.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				actualizaSubGrupo();
			}
		});

		cntSubGrupo.setBounds(61, 34, 256, 20);
		panel_1.add(cntSubGrupo);

		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setBounds(5, 65, 46, 14);
		panel_1.add(lblNewLabel);

		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(5, 93, 61, 14);
		panel_1.add(lblDescripcion);

		JLabel lblNombreCorto = new JLabel("Nombre Corto");
		lblNombreCorto.setBounds(5, 119, 90, 14);
		panel_1.add(lblNombreCorto);

		txtCodigo = new JTextField();
		this.txtCodigo.setName("C\u00F3digo del Producto");
		txtCodigo.setBounds(63, 62, 96, 20);
		panel_1.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(20, true));

		txtDescripcion = new JTextField();
		this.txtDescripcion.setName("Descripci\u00F3n del Producto");
		txtDescripcion.setBounds(63, 90, 198, 20);
		panel_1.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(70, true));

		txtnomcorto = new JTextField();
		this.txtnomcorto.setName("Nombre Corto del Producto");
		txtnomcorto.setBounds(84, 116, 86, 20);
		panel_1.add(txtnomcorto);
		txtnomcorto.setColumns(10);
		txtnomcorto.setDocument(new JTextFieldLimit(30, true));

		JLabel lblUnidadDeMedida = new JLabel("Und. de Medida");
		lblUnidadDeMedida.setBounds(5, 144, 75, 14);
		panel_1.add(lblUnidadDeMedida);

		cntmedida = new cntMedida();

		cntmedida.setBounds(84, 141, 164, 20);
		panel_1.add(cntmedida);

		JLabel lblMarcas = new JLabel("Marca");
		lblMarcas.setBounds(258, 144, 46, 14);
		panel_1.add(lblMarcas);

		cntmarca = new cntMarca();

		cntmarca.setBounds(297, 141, 164, 20);
		panel_1.add(cntmarca);

		this.lblCodigoAlternativo = new JLabel("Codigo Alternativo");
		this.lblCodigoAlternativo.setBounds(169, 65, 90, 14);
		panel_1.add(this.lblCodigoAlternativo);

		this.txtCodAlternativo = new JTextField();
		this.txtCodAlternativo.setName("C\u00F3digo del Producto");
		this.txtCodAlternativo.setColumns(10);
		this.txtCodAlternativo.setBounds(269, 62, 96, 20);
		panel_1.add(this.txtCodAlternativo);

		this.lblNomOriginal = new JLabel("Nom. Original");
		this.lblNomOriginal.setBounds(271, 93, 74, 14);
		panel_1.add(this.lblNomOriginal);

		this.txtNomOriginal = new JTextField();
		this.txtNomOriginal.setName("Descripci\u00F3n del Producto");
		this.txtNomOriginal.setColumns(10);
		this.txtNomOriginal.setBounds(343, 90, 192, 20);
		panel_1.add(this.txtNomOriginal);

		this.cntFabricante = new CntClieprov();
		this.cntFabricante.setBounds(84, 172, 241, 23);
		this.cntFabricante.setData(clieprovDAO.findAll());
		panel_1.add(this.cntFabricante);

		this.lblFabricante = new JLabel("Fabricante");
		this.lblFabricante.setBounds(5, 176, 75, 14);
		panel_1.add(this.lblFabricante);

		JPanel panel = new JPanel();
		tabPanel.addTab("Propiedades del Producto", null, panel, null);
		panel.setLayout(null);

		JCheckBox chckbxEsDescrte = new JCheckBox("Es Descarte");
		chckbxEsDescrte.setBounds(283, 33, 83, 23);
		panel.add(chckbxEsDescrte);

		JCheckBox chckbxEsProductoVenta = new JCheckBox("Es Producto Venta");
		chckbxEsProductoVenta.setBounds(283, 7, 113, 23);
		panel.add(chckbxEsProductoVenta);

		JCheckBox chckbxEsProductoTerminado = new JCheckBox(
				"Es Producto Terminado");
		chckbxEsProductoTerminado.setBounds(283, 59, 135, 23);
		panel.add(chckbxEsProductoTerminado);

		this.scrlImpuestos = new JScrollPane();
		this.scrlImpuestos.setBounds(10, 27, 267, 85);
		panel.add(this.scrlImpuestos);

		this.tblImpuestos = new JTable(new DSGTableModel(new String[] {
				"C�d Impuesto", "Impuesto" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "" });
			}
		}) {
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					String idimpuesto = getImpuestoTM().getValueAt(row, 0)
							.toString();
					txtimpuesto.refresValue(idimpuesto);
				}
			}
		};

		getImpuestoTM().setNombre_detalle("Impuestos");
		getImpuestoTM().setObligatorios(0);
		getImpuestoTM().setRepetidos(0);
		getImpuestoTM().setScrollAndTable(scrlImpuestos, tblImpuestos);

		txtimpuesto = new TxtImpuesto(tblImpuestos, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Impuesto entity) {
				int row = tblImpuestos.getSelectedRow();
				if (entity == null) {
					getImpuestoTM().setValueAt("", row, 0);
					getImpuestoTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdimpuesto());
					getImpuestoTM().setValueAt(entity.getIdimpuesto(), row, 0);
					getImpuestoTM().setValueAt(entity.getDescripcion(), row, 1);
				}
				setSeleccionado(null);
			}
		};
		txtimpuesto.updateCellEditor();
		pnlContenido.setLayout(null);
		this.scrlImpuestos.setViewportView(this.tblImpuestos);

		this.chkServicio = new JCheckBox("Es Servicio");
		this.chkServicio.setBounds(283, 85, 97, 23);
		panel.add(this.chkServicio);

		this.lblImpuestos = new JLabel("Impuestos");
		this.lblImpuestos.setBounds(10, 11, 75, 14);
		panel.add(this.lblImpuestos);

		this.lblStockMnimo = new JLabel("Stock M\u00EDnimo");
		this.lblStockMnimo.setBounds(10, 123, 75, 14);
		panel.add(this.lblStockMnimo);

		this.scrlStockMinimo = new JScrollPane();
		this.scrlStockMinimo.setBounds(10, 148, 413, 118);
		panel.add(this.scrlStockMinimo);

		tblStockMinimo = new JTable(new DSGTableModel(new String[] {
				"C�d. Sucursal", "Sucursal", "C�d Almacen", "Almacen",
				"M�nimo", "Reposici�n" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 3)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", "", "", 0.0F, 0.0F });
			}

		}) {
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					Sucursal sucursal = null;
					String idsucursal = getStockMinimoTM().getValueAt(row, 0)
							.toString();
					String idalmacen = getStockMinimoTM().getValueAt(row, 2)
							.toString();
					txtsucursal.refresValue(idsucursal);
					sucursal = sucursalDAO.find(idsucursal);
					if (sucursal == null)
						txtalmacen.setData(null);
					else
						txtalmacen.setData(almacenDAO.getPorSucursal(sucursal));
					txtalmacen.refresValue(idalmacen);
				}
			}
		};
		scrlStockMinimo.setViewportView(tblStockMinimo);

		txtsucursal = new TxtSucursal(tblStockMinimo, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Sucursal entity) {
				int row = tblStockMinimo.getSelectedRow();
				if (entity == null) {
					setText("");
					getStockMinimoTM().setValueAt("", row, 0);
					getStockMinimoTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdsucursal());
					getStockMinimoTM().setValueAt(entity.getIdsucursal(), row,
							0);
					getStockMinimoTM().setValueAt(entity.getDescripcion(), row,
							1);
				}
				setSeleccionado(null);
			}
		};
		txtsucursal.updateCellEditor();
		txtsucursal.setData(sucursalDAO.findAll());
		txtalmacen = new TxtAlmacen(tblStockMinimo, 2) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Almacen entity) {
				int row = tblStockMinimo.getSelectedRow();
				if (entity == null) {
					setText("");
					getStockMinimoTM().setValueAt("", row, 2);
					getStockMinimoTM().setValueAt("", row, 3);
				} else {
					setText(entity.getId().getIdalmacen());
					getStockMinimoTM().setValueAt(
							entity.getId().getIdalmacen(), row, 2);
					getStockMinimoTM().setValueAt(entity.getDescripcion(), row,
							3);
				}
				setSeleccionado(null);
			}
		};
		txtalmacen.updateCellEditor();
		getStockMinimoTM().setNombre_detalle("Stock M�nimo");
		getStockMinimoTM().setScrollAndTable(scrlStockMinimo, tblStockMinimo);
		getStockMinimoTM().setObligatorios(0, 1, 2, 3);
		getStockMinimoTM().setRepetidos(0, 2);
		getStockMinimoTM().setValidaNumero(
				new DSGVNDetalle(DSGVNDetalle.Operador.MAYOR, 0, 4));

		TableColumnModel tc = tblStockMinimo.getColumnModel();

		tc.getColumn(4).setCellEditor(new FloatEditor(6));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(6));

		tc.getColumn(5).setCellEditor(new FloatEditor(6));
		tc.getColumn(5).setCellRenderer(new FloatRenderer(6));

		pnlContenido.add(tabPanel);

	}

	@Override
	public void actualiza_objeto(Object prod) {
		setProducto((Producto) prod);
		llenar_datos();
		vista_noedicion();
	}

	public void grabar() {
		productoDAO.crear_editar(producto);
		productoimpuestoDAO.borrarPorProducto(producto);
		productoStockMinimoDAO.borrarPorProducto(producto);
		
		productoimpuestoDAO.create(impuestos);
		productoStockMinimoDAO.create(stockMinimo);
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		chkServicio.setEnabled(true);
		getImpuestoTM().setEditar(true);
		getStockMinimoTM().setEditar(true);
		TextFieldsEdicion(true, txtDescripcion, txtnomcorto, txtCodAlternativo,
				txtNomOriginal);
		FormValidador.CntEdicion(true, cntgrupo, cntSubGrupo, cntmedida,
				cntmarca, cntFabricante);
	}

	@Override
	public void vista_noedicion() {

		getImpuestoTM().setEditar(false);
		getStockMinimoTM().setEditar(false);
		TextFieldsEdicion(false, txtCodigo, txtDescripcion, txtnomcorto,
				txtCodAlternativo, txtNomOriginal);

		FormValidador.CntEdicion(false, cntgrupo, cntSubGrupo, cntmedida,
				cntmarca, cntFabricante);
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (!getEstado().equals(NUEVO)) {
			Subgrupo sg = getProducto().getSubgrupo();
			Grupo g = (sg == null) ? null : sg.getGrupo();

			txtCodigo.setText(this.getProducto().getIdproducto());
			txtDescripcion.setText(this.getProducto().getDescripcion());
			txtnomcorto.setText(this.getProducto().getDescCorta());
			cntmarca.setSeleccionado(getProducto().getMarca());
			cntmedida.setSeleccionado(getProducto().getUnimedida());
			cntgrupo.setSeleccionado(g);
			cntSubGrupo.setSeleccionado(sg);

			cntFabricante.setText((producto.getFabricante() == null) ? ""
					: producto.getFabricante().getIdclieprov());
			cntFabricante.llenar();

			txtCodAlternativo.setText(producto.getCodigo_alternativo());
			txtNomOriginal.setText(producto.getNombre_original());

			chkServicio.setSelected((getProducto().getEsServicio() == 1));
			impuestos = productoimpuestoDAO.getPorProducto(getProducto());

			stockMinimo = productoStockMinimoDAO.getPorProducto(getProducto());

			for (ProductoImpuesto i : impuestos) {
				Impuesto im = i.getImpuesto();
				getImpuestoTM()
						.addRow(new Object[] { im.getIdimpuesto(),
								im.getDescripcion() });
			}

			for (ProductoStockMinimo stock : stockMinimo) {
				Sucursal sucursal = stock.getSucursal();
				Almacen almacen = stock.getAlmacen();
				getStockMinimoTM().addRow(
						new Object[] { sucursal.getIdsucursal(),
								sucursal.getDescripcion(),
								almacen.getId().getIdalmacen(),
								almacen.getDescripcion(), stock.getCantidad(),
								stock.getReposicion() });
			}
		}
		llenar_tablas();
	}

	public void actualizaSubGrupo() {
		cntSubGrupo.setData(new SubgrupoDAO().findAllbyGrupo(cntgrupo
				.getSeleccionado()));
	}

	public void limpiarVista() {
		this.cntgrupo.txtCodigo.setText(null);
		this.cntgrupo.txtDescripcion.setText(null);
		this.cntSubGrupo.txtCodigo.setText(null);
		this.cntSubGrupo.txtDescripcion.setText(null);
		this.cntmarca.txtCodigo.setText(null);
		this.cntmarca.txtDescripcion.setText(null);
		this.cntmedida.txtCodigo.setText(null);
		this.cntmedida.txtDescripcion.setText(null);
		this.txtCodigo.setText(null);
		this.txtDescripcion.setText(null);
		this.txtnomcorto.setText(null);
		this.chkServicio.setSelected(false);
		impuestos = new ArrayList<ProductoImpuesto>();
		getImpuestoTM().limpiar();
		getStockMinimoTM().limpiar();
	}

	@Override
	public void llenar_lista() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_tablas() {
		cntgrupo.setData(new GrupoDAO().findAll());
		cntmarca.setData(new MarcaDAO().findAll());
		cntmedida.setData(new UnimedidaDAO().findAll());
		txtimpuesto.setData(new ImpuestoDAO().findAll());
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void nuevo() {
		producto = new Producto();
	}

	@Override
	public void llenarDesdeVista() {
		/*
		 * if (productoDAO.find(txtCodigo.getText()) != null) { bkEntidad =
		 * productoDAO.find(txtCodigo.getText()).historial(); }
		 */

		String idproducto;
		idproducto = this.txtCodigo.getText();
		producto.setIdproducto(idproducto);
		producto.setDescripcion(this.txtDescripcion.getText());
		producto.setDescCorta(this.txtnomcorto.getText());
		producto.setSubgrupo(this.cntSubGrupo.getSeleccionado());
		producto.setUnimedida(this.cntmedida.getSeleccionado());
		producto.setMarca(this.cntmarca.getSeleccionado());
		producto.setFabricante(this.cntFabricante.getSeleccionado());
		producto.setCodigo_alternativo(this.txtCodAlternativo.getText());
		producto.setNombre_original(this.txtNomOriginal.getText());

		getProducto().setEsServicio(this.chkServicio.isSelected() ? 1 : 0);
		int rows = tblImpuestos.getRowCount();

		impuestos = new ArrayList<ProductoImpuesto>();

		for (int i = 0; i < rows; i++) {
			Impuesto impto = impuestoDAO.find(getImpuestoTM().getValueAt(i, 0).toString());
			ProductoImpuesto pi = new ProductoImpuesto();
			pi.setProducto(producto);
			pi.setImpuesto(impto);
			impuestos.add(pi);
		}

		rows = getStockMinimoTM().getRowCount();

		stockMinimo = new ArrayList<ProductoStockMinimo>();

		for (int i = 0; i < rows; i++) {
			String idsucursal, idalmacen;
			float cantidad, reposicion;

			ProductoStockMinimo ps = new ProductoStockMinimo();
			
			idsucursal = getStockMinimoTM().getValueAt(i, 0).toString();
			idalmacen = getStockMinimoTM().getValueAt(i, 2).toString();

			cantidad = Float.parseFloat(getStockMinimoTM().getValueAt(i, 4)
					.toString());
			reposicion = Float.parseFloat(getStockMinimoTM().getValueAt(i, 5)
					.toString());
			
			Sucursal sucursal;
			Almacen almacen;
			
			sucursal = sucursalDAO.find(idsucursal);
			almacen = almacenDAO.find(idalmacen);
			
			ps.setSucursal(sucursal);
			ps.setAlmacen(almacen);
			ps.setProducto(producto);
			ps.setCantidad(cantidad);
			ps.setReposicion(reposicion);
			
			stockMinimo.add(ps);
		}
	}

	@Override
	public boolean isValidaVista() {
		if (!FormValidador.CntObligatorios(cntgrupo, cntSubGrupo)) {
			return false;
		}

		if (!FormValidador.TextFieldObligatorios(txtCodigo, txtDescripcion,
				txtnomcorto))
			return false;

		if (getEstado().equals(NUEVO)) {
			if (productoDAO.find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		if (!FormValidador.CntObligatorios(cntmedida)) {
			return false;
		}

		if (!getImpuestoTM().esValido()) {
			return false;
		}
		if (!getStockMinimoTM().esValido()) {
			return false;
		}

		return true;
	}

	public DSGTableModel getImpuestoTM() {
		return (DSGTableModel) tblImpuestos.getModel();
	}

	public DSGTableModel getStockMinimoTM() {
		return (DSGTableModel) tblStockMinimo.getModel();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
}