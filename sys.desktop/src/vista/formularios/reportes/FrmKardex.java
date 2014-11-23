package vista.formularios.reportes;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;

import vista.contenedores.CntProducto;
import vista.contenedores.cntAlmacen;
import vista.contenedores.cntSucursal;
import vista.controles.DSGDatePicker;
import vista.formularios.abstractforms.AbstractReporte;
import vista.utilitarios.FormValidador;
import vista.utilitarios.StringUtils;
import core.dao.AlmacenDAO;
import core.dao.DocingresoDAO;
import core.dao.DocsalidaDAO;
import core.dao.KardexDAO;
import core.dao.ProductoDAO;
import core.dao.SucursalDAO;
import core.entity.Almacen;
import core.entity.Docingreso;
import core.entity.Docsalida;
import core.entity.Kardex;
import core.entity.Producto;
import core.entity.Sucursal;

public class FrmKardex extends AbstractReporte {

	private KardexDAO kardexDAO = new KardexDAO();
	private ProductoDAO productoDAO = new ProductoDAO();
	private SucursalDAO sucursalDAO = new SucursalDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private DocingresoDAO docingresoDAO = new DocingresoDAO();
	private DocsalidaDAO docsalidaDAO = new DocsalidaDAO();

	public FrmKardex() {
		Calendar calendar = Calendar.getInstance();
		pnlFiltro.setPreferredSize(new Dimension(10, 90));

		this.cntProducto = new CntProducto();
		this.cntProducto.setBounds(10, 23, 192, 20);
		pnlFiltro.add(this.cntProducto);

		this.lblProducto = new JLabel("Producto");
		this.lblProducto.setBounds(10, 11, 86, 14);
		pnlFiltro.add(this.lblProducto);

		this.cntAlmacen = new cntAlmacen();
		this.cntAlmacen.setBounds(431, 23, 192, 20);
		pnlFiltro.add(this.cntAlmacen);

		this.cntSucursal = new cntSucursal();
		this.cntSucursal.setBounds(229, 23, 192, 20);
		pnlFiltro.add(this.cntSucursal);

		this.lblSucursal = new JLabel("Sucursal");
		this.lblSucursal.setBounds(229, 11, 86, 14);
		pnlFiltro.add(this.lblSucursal);

		this.lblAlmacen = new JLabel("Almacen");
		this.lblAlmacen.setBounds(430, 11, 86, 14);
		pnlFiltro.add(this.lblAlmacen);

		this.lblDesde = new JLabel("Desde");
		this.lblDesde.setBounds(10, 53, 46, 14);
		pnlFiltro.add(this.lblDesde);

		this.lblHasta = new JLabel("Hasta");
		this.lblHasta.setBounds(229, 53, 46, 14);
		pnlFiltro.add(this.lblHasta);

		this.dpDesde = new DSGDatePicker();
		this.dpDesde.setBounds(49, 54, 147, 22);
		pnlFiltro.add(this.dpDesde);

		this.dpHasta = new DSGDatePicker();
		this.dpHasta.setBounds(268, 54, 147, 22);
		pnlFiltro.add(this.dpHasta);
		setTitle("Kardex de Productos");

		cntProducto.setData(productoDAO.findAll());
		cntSucursal.setData(sucursalDAO.findAll());

		cntAlmacen.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusLost(e);
				cntAlmacen.setData(almacenDAO.getPorSucursal(cntSucursal
						.getSeleccionado()));
			}
		});

		dpDesde.setDate(calendar.getTime());
		dpHasta.setDate(calendar.getTime());

	}

	private static final long serialVersionUID = 1L;
	private CntProducto cntProducto;
	private JLabel lblProducto;
	private cntAlmacen cntAlmacen;
	private cntSucursal cntSucursal;
	private JLabel lblSucursal;
	private JLabel lblAlmacen;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private DSGDatePicker dpDesde;
	private DSGDatePicker dpHasta;

	@Override
	public Object[][] getData() {
		Sucursal sucursal;
		Almacen almacen;
		Producto producto;
		Calendar cDesde = Calendar.getInstance();
		Calendar cHasta = Calendar.getInstance();
		Calendar cMovimiento = Calendar.getInstance();
		int desde, hasta;

		sucursal = cntSucursal.getSeleccionado();
		almacen = cntAlmacen.getSeleccionado();
		producto = cntProducto.getSeleccionado();

		cDesde.setTime(dpDesde.getDate());

		desde = cDesde.get(Calendar.YEAR) * 10000
				+ (cDesde.get(Calendar.MONTH) + 1) * 100
				+ cDesde.get(Calendar.DAY_OF_MONTH);

		cHasta.setTime(dpHasta.getDate());

		hasta = cHasta.get(Calendar.YEAR) * 10000
				+ (cHasta.get(Calendar.MONTH) + 1) * 100
				+ cHasta.get(Calendar.DAY_OF_MONTH);
		// Stock Inicial

		float stockInicial = 0.0F, precioInicial = 0.0F, stock = 0.0F, cantidad = 0.0F;

		stockInicial = kardexDAO.getSaldoAntesDe(desde, producto, sucursal,
				almacen);

		List<Kardex> movimientos = kardexDAO.getMovimientos(desde, hasta,
				producto, sucursal, almacen);

		Object data[][] = new Object[movimientos.size() + 1][getCabeceras().length];

		Object[] fila = data[0];
		int j = 0;
		fila[j] = (sucursal == null) ? "Todas" : sucursal.getDescripcion();
		j++;
		fila[j] = (almacen == null) ? "Todas" : almacen.getDescripcion();
		j++;
		fila[j] = cHasta.getTime();
		j++;
		fila[j] = "";
		j++;
		fila[j] = "(Saldo Inicial)";
		j++;
		fila[j] = 0.0;
		j++;
		fila[j] = 0.0;
		j++;
		fila[j] = stockInicial;
		j++;
		fila[j] = precioInicial;
		j++;
		fila[j] = stockInicial * precioInicial;

		stock = stockInicial;
		int i = 0;
		for (Kardex m : movimientos) {
			String tipo_documento, documento;
			cantidad = m.getCantidad() * m.getFactor();

			stock += cantidad;
			cMovimiento.set(Calendar.YEAR, m.getAnio());
			cMovimiento.set(Calendar.MONTH, m.getMes() - 1);
			cMovimiento.set(Calendar.DAY_OF_MONTH, m.getDia());
			i++;

			if (m.getTipo() == 'I') {
				Docingreso ingreso = docingresoDAO.find(m.getIdreferencia());
				documento = ingreso.getSerie() + "-"
						+ StringUtils._padl(ingreso.getNumero(), 8, '0');
				tipo_documento = "N. Ing.";
			} else {
				Docsalida salida = docsalidaDAO.find(m.getIdreferencia());
				documento = salida.getSerie() + "-"
						+ StringUtils._padl(salida.getNumero(), 8, '0');
				tipo_documento = "N. Sal.";
			}

			fila = data[i];
			j = 0;
			fila[j] = (sucursal == null) ? "Todas" : sucursal.getDescripcion();
			j++;
			fila[j] = (almacen == null) ? "Todas" : almacen.getDescripcion();
			j++;
			fila[j] = cMovimiento.getTime();
			j++;
			fila[j] = tipo_documento;
			j++;
			fila[j] = documento;
			j++;
			fila[j] = (cantidad > 0) ? cantidad : 0;
			j++;
			fila[j] = (cantidad < 0) ? Math.abs(cantidad) : 0;
			j++;
			fila[j] = stock;
			j++;
			fila[j] = m.getPrecio();
			j++;
			fila[j] = stock * m.getPrecio();
		}

		return data;
	}

	@Override
	public String[] getCabeceras() {
		return new String[] { "Sucursal", "Almacen", "Fecha", "T.Doc.",
				"Documento", "Entra", "Sale", "Stock", "P. U.", "Total" };
	}

	@Override
	public boolean isFiltrosValidos() {
		if (!FormValidador.CntObligatorios(cntProducto))
			return false;
		return true;
	}
}
