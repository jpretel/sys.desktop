package vista.formularios.reportes;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Tuple;
import javax.swing.JLabel;

import vista.controles.DSGDatePicker;
import vista.formularios.abstractforms.AbstractReporte;
import vista.utilitarios.StringUtils;
import core.dao.KardexRequerimientoDAO;
import core.entity.Producto;
import core.entity.Requerimiento;

public class FrmSaldosRequerimiento extends AbstractReporte {

	private KardexRequerimientoDAO kardexDAO = new KardexRequerimientoDAO();

	public FrmSaldosRequerimiento() {
		Calendar calendar = Calendar.getInstance();
		
		this.lblDesde = new JLabel("Desde");
		this.lblDesde.setBounds(10, 11, 46, 14);
		pnlFiltro.add(this.lblDesde);
		
		this.dpDesde = new DSGDatePicker();
		this.dpDesde.setBounds(10, 22, 147, 22);
		pnlFiltro.add(this.dpDesde);

		this.lblHasta = new JLabel("Hasta");
		this.lblHasta.setBounds(190, 11, 46, 14);
		pnlFiltro.add(this.lblHasta);

		this.dpHasta = new DSGDatePicker();
		this.dpHasta.setBounds(190, 22, 147, 22);
		pnlFiltro.add(this.dpHasta);
		setTitle("Saldo por Almacen");
		dpHasta.setDate(calendar.getTime());
		dpDesde.setDate(calendar.getTime());

	}

	private static final long serialVersionUID = 1L;
	private JLabel lblHasta;
	private DSGDatePicker dpHasta;
	private JLabel lblDesde;
	private DSGDatePicker dpDesde;

	@Override
	public Object[][] getData() {
		Calendar cHasta = Calendar.getInstance();

		int hasta, desde;


		cHasta.setTime(dpHasta.getDate());

		hasta = cHasta.get(Calendar.YEAR) * 10000
				+ (cHasta.get(Calendar.MONTH) + 1) * 100
				+ cHasta.get(Calendar.DAY_OF_MONTH);
		
		cHasta.setTime(dpDesde.getDate());
		
		desde = cHasta.get(Calendar.YEAR) * 10000
				+ (cHasta.get(Calendar.MONTH) + 1) * 100
				+ cHasta.get(Calendar.DAY_OF_MONTH); 
		// Stock Inicial

		List<Tuple> saldos = kardexDAO.getSaldosFecha(desde, hasta);

		Object data[][] = new Object[saldos.size()][getCabeceras().length];

		Object[] fila;
		
		Calendar c = Calendar.getInstance();
		
		int i = -1;
		int j = 0;
		for (Tuple t : saldos) {
			
			Requerimiento r = (Requerimiento) t.get("requerimiento");
			Producto p = (Producto) t.get("producto");
			float cantidad = (float) t.get("cantidad");
			
			c.set(Calendar.YEAR, r.getAnio());
			c.set(Calendar.MONTH, r.getMes() - 1);
			c.set(Calendar.DAY_OF_MONTH, r.getDia());
			
			i++;
			fila = data[i];
			
			j = 0;
			fila[j] = r.getSerie();
			
			j++;
			fila[j] = StringUtils._padl(r.getNumero(), 8, '0');
			j++;
			fila[j] = r.getSucursal().getDescripcion();
			j++;
			fila[j] = r.getAlmacen().getDescripcion();
			j++;
			fila[j] = c.getTime(); //r.getFecha();
			j++;
			fila[j] = p.getIdproducto();
			j++;
			fila[j] = p.getDescripcion();
			j++;
			fila[j] = p.getSubgrupo().getGrupo().getDescripcion();
			j++;
			fila[j] = p.getSubgrupo().getDescripcion();
			j++;
			fila[j] = p.getMarca().getDescripcion();
			j++;
			fila[j] = p.getUnimedida().getDescripcion();
			j++;
			fila[j] = cantidad;
		}

		return data;
	}

	@Override
	public String[] getCabeceras() {
		return new String[] { "Serie", "Número", "Sucursal","Almacen", "Fecha", "Cod. Prod.", "Desc. Prod", "Grupo", "Sub grupo",
				"Marca", "U. M.", "Saldo" };
	}

	@Override
	public boolean isFiltrosValidos() {
		return true;
	}
}
