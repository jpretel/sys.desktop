package vista.formularios.reportes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Tuple;
import javax.swing.JLabel;

import vista.contenedores.cntAlmacen;
import vista.contenedores.cntSucursal;
import vista.controles.DSGDatePicker;
import vista.formularios.abstractforms.AbstractReporte;
import core.dao.AlmacenDAO;
import core.dao.KardexDAO;
import core.dao.SucursalDAO;
import core.entity.Almacen;
import core.entity.Producto;
import core.entity.Sucursal;

public class FrmSaldosAlmacen extends AbstractReporte {

	private KardexDAO kardexDAO = new KardexDAO();
	private SucursalDAO sucursalDAO = new SucursalDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();

	public FrmSaldosAlmacen() {
		Calendar calendar = Calendar.getInstance();

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

		this.lblHasta = new JLabel("Hasta");
		this.lblHasta.setBounds(10, 11, 46, 14);
		pnlFiltro.add(this.lblHasta);

		this.dpHasta = new DSGDatePicker();
		this.dpHasta.setBounds(10, 22, 147, 22);
		pnlFiltro.add(this.dpHasta);
		setTitle("Saldo por Almacen");
		cntSucursal.setData(sucursalDAO.findAll());

		cntAlmacen.txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusLost(e);
				cntAlmacen.setData(almacenDAO.getPorSucursal(cntSucursal
						.getSeleccionado()));
			}
		});
		dpHasta.setDate(calendar.getTime());

	}

	private static final long serialVersionUID = 1L;
	private cntAlmacen cntAlmacen;
	private cntSucursal cntSucursal;
	private JLabel lblSucursal;
	private JLabel lblAlmacen;
	private JLabel lblHasta;
	private DSGDatePicker dpHasta;

	@Override
	public Object[][] getData() {
		Sucursal sucursal;
		Almacen almacen;
		Calendar cHasta = Calendar.getInstance();

		int hasta;

		sucursal = cntSucursal.getSeleccionado();
		almacen = cntAlmacen.getSeleccionado();

		cHasta.setTime(dpHasta.getDate());

		hasta = cHasta.get(Calendar.YEAR) * 10000
				+ (cHasta.get(Calendar.MONTH) + 1) * 100
				+ cHasta.get(Calendar.DAY_OF_MONTH);
		// Stock Inicial

		List<Tuple> saldos = kardexDAO.getSaldosSucursalAlmacen(hasta,
				sucursal, almacen);

		Object data[][] = new Object[saldos.size()][getCabeceras().length];

		Object[] fila;

		int i = -1;
		int j = 0;
		for (Tuple t : saldos) {

			Producto p = (Producto) t.get("producto");
			float cantidad = (float) t.get("cantidad");
			i++;
			fila = data[i];

			j = 0;
			fila[j] = p.getIdproducto();
			j++;
			fila[j] = p.getDescripcion();
			j++;
			fila[j] = p.getSubgrupo().getGrupo().getDescripcion();
			j++;
			fila[j] = p.getSubgrupo().getDescripcion();
			j++;
			fila[j] = p.getUnimedida().getDescripcion();
			j++;
			fila[j] = cantidad;
		}

		return data;
	}

	@Override
	public String[] getCabeceras() {
		return new String[] { "Cod. Prod.", "Desc. Prod", "Grupo", "Sub grupo",
				"U. M.", "Saldo" };
	}

	@Override
	public boolean isFiltrosValidos() {
		return true;
	}
}
