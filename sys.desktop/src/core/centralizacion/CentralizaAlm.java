package core.centralizacion;

import java.util.ArrayList;
import java.util.List;

import core.dao.AsientoDAO;
import core.dao.CfgCentralizaAlmDAO;
import core.dao.DAsientoDAO;
import core.dao.DocingresoDAO;
import core.dao.KardexDAO;
import core.dao.TCambioDAO;
import core.entity.Asiento;
import core.entity.CfgCentralizaAlm;
import core.entity.Concepto;
import core.entity.DAsiento;
import core.entity.Docingreso;
import core.entity.Kardex;
import core.entity.Moneda;
import core.entity.Producto;
import core.entity.Subdiario;

public class CentralizaAlm {

	public static Asiento CentralizaAlmacen(Asiento asiento, long id, int anio,
			int mes, int dia, float tcambio, float tcmoneda, Moneda moneda,
			Concepto concepto, Subdiario subdiario) {

		CfgCentralizaAlmDAO cfgDAO = new CfgCentralizaAlmDAO();
		KardexDAO kardexDAO = new KardexDAO();
		AsientoDAO asientoDAO = new AsientoDAO();
		DAsientoDAO dasientoDAO = new DAsientoDAO();
		long idasiento;
		if (asiento == null) {
			idasiento = System.nanoTime();
			asiento = new Asiento();
			asiento.setIdasiento(idasiento);
		} else {
			idasiento = asiento.getIdasiento();
		}
		List<DAsiento> dasiento = new ArrayList<DAsiento>();

		asiento.setAnio(anio);
		asiento.setMes(mes);
		asiento.setDia(dia);
		asiento.setFecha(anio * 10000 + mes * 100 + dia);
		asiento.setEstado(1);
		asiento.setMoneda(moneda);
		asiento.setTcambio(tcambio);
		asiento.setTcmoneda(tcmoneda);
		asiento.setNumerador(0);
		asiento.setTipo('A');
		asiento.setSubdiario(subdiario);
		
		for (Kardex det : kardexDAO.getPorIngresoSalidaL(id)) {
			float precio = det.getPrecio();
			float cantidad = det.getCantidad();
			float total = 0;

			Producto prod = det.getProducto();
			total = precio * cantidad;

			CfgCentralizaAlm cfg = cfgDAO.getPorConceptoGrupoSubGrupo(concepto,
					prod.getSubgrupo().getGrupo(), prod.getSubgrupo());

			if (cfg == null) {
				System.out.println("No tiene configuracion contable "
						+ concepto.getDescripcion() + " "
						+ prod.getSubgrupo().getGrupo().getIdgrupo() + " "
						+ prod.getSubgrupo().getId().getIdsubgrupo());
				return null;
			}

			// Insertar cuenta de Debe
			DAsiento da = new DAsiento();
			
			da.setCuenta(cfg.getCta_debe());

			LlenarDebeHaber(moneda.getTipo(), da, total, tcambio, tcmoneda, 'D');

			if (cfg.getCta_debe().getA_producto() == 1) {
				da.setProducto(prod);
				da.setCantidad(cantidad);
			}

			dasiento.add(da);
			
			da = new DAsiento();
			
			da.setCuenta(cfg.getCta_haber());

			LlenarDebeHaber(moneda.getTipo(), da, total, tcambio, tcmoneda, 'H');

			if (cfg.getCta_haber().getA_producto() == 1) {
				da.setProducto(prod);
				da.setCantidad(cantidad);
			}

			dasiento.add(da);

		}

		asientoDAO.crear_editar(asiento);
		dasientoDAO.borrarPorAsiento(asiento);
		dasientoDAO.create(dasiento);

		return asiento;

	}

	public static void CentralizaIngreso(long idingreso) {

		float tcambio, tcmoneda;

		TCambioDAO tcambioDAO = new TCambioDAO();

		DocingresoDAO docingresoDAO = new DocingresoDAO();
		Docingreso ingreso = docingresoDAO.find(idingreso);

		tcambio = 2.8F;
				//tcambioDAO.getFechaMoneda(ingreso.getMoneda(),
				//ingreso.getAnio(), ingreso.getMes(), ingreso.getDia()).getCompra();
		tcmoneda = 1.0F; //
		
		Asiento asiento = CentralizaAlmacen(ingreso.getAsiento(), idingreso,
				ingreso.getAnio(), ingreso.getMes(), ingreso.getDia(),
				tcambio, tcmoneda,
				ingreso.getMoneda(), ingreso.getConcepto(), ingreso
						.getGrupoCentralizacion().getSubdiario());
		ingreso.setAsiento(asiento);

		docingresoDAO.crear_editar(ingreso);
	}

	public static void LlenarDebeHaber(int tipo_moneda, DAsiento da,
			float importe, float tcambio, float tcmoneda, char columna) {
		switch (columna) {
		case 'D':
			if (importe < 0) {
				da.setDebe(0);
				da.setDebe_of(0);
				da.setDebe_ex(0);

				da.setHaber(Math.abs(importe));

				if (tipo_moneda == 0)
					da.setHaber_of(Math.abs(importe));
				else if (tipo_moneda == 1)
					da.setHaber_of(Math.abs(importe) / tcambio);
				else
					da.setHaber_of(Math.abs(importe) / tcambio / tcmoneda);

				if (tipo_moneda == 0)
					da.setHaber_ex(Math.abs(importe) * tcambio);
				else if (tipo_moneda == 1)
					da.setHaber_ex(Math.abs(importe));
				else
					da.setHaber_ex(Math.abs(importe) / tcmoneda);

			} else {
				da.setHaber(0);
				da.setHaber_of(0);
				da.setHaber_ex(0);

				da.setDebe(Math.abs(importe));

				if (tipo_moneda == 0)
					da.setDebe_of(importe);
				else if (tipo_moneda == 1)
					da.setDebe_of(importe / tcambio);
				else
					da.setDebe_of(importe / tcambio / tcmoneda);

				if (tipo_moneda == 0)
					da.setDebe_ex(importe * tcambio);
				else if (tipo_moneda == 1)
					da.setDebe_ex(importe);
				else
					da.setDebe_ex(importe / tcmoneda);
			}
			break;

		default:

			if (importe < 0) {
				da.setHaber(0);
				da.setHaber_of(0);
				da.setHaber_ex(0);

				da.setDebe(Math.abs(importe));

				if (tipo_moneda == 0)
					da.setDebe_of(Math.abs(importe));
				else if (tipo_moneda == 1)
					da.setDebe_of(Math.abs(importe) / tcambio);
				else
					da.setDebe_of(Math.abs(importe) / tcambio / tcmoneda);

				if (tipo_moneda == 0)
					da.setDebe_ex(Math.abs(importe) * tcambio);
				else if (tipo_moneda == 1)
					da.setDebe_ex(Math.abs(importe));
				else
					da.setDebe_ex(Math.abs(importe) / tcmoneda);

			} else {
				da.setDebe(0);
				da.setDebe_of(0);
				da.setDebe_ex(0);

				da.setHaber(Math.abs(importe));

				if (tipo_moneda == 0)
					da.setHaber_of(importe);
				else if (tipo_moneda == 1)
					da.setHaber_of(importe / tcambio);
				else
					da.setHaber_of(importe / tcambio / tcmoneda);

				if (tipo_moneda == 0)
					da.setHaber_ex(importe * tcambio);
				else if (tipo_moneda == 1)
					da.setHaber_ex(importe);
				else
					da.setHaber_ex(importe / tcmoneda);
			}
			break;
		}
	}
}
