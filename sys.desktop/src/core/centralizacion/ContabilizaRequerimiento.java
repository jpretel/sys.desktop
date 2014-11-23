package core.centralizacion;

import java.util.ArrayList;
import java.util.List;

import core.dao.DRequerimientoDAO;
import core.dao.DetDocSalidaDAO;
import core.dao.DocsalidaDAO;
import core.dao.KardexRequerimientoDAO;
import core.dao.RequerimientoDAO;
import core.entity.DRequerimiento;
import core.entity.DetDocsalida;
import core.entity.Docsalida;
import core.entity.KardexRequerimiento;
import core.entity.Requerimiento;

public class ContabilizaRequerimiento {

	public static boolean ContabilizarRequerimiento(long id) {
		RequerimientoDAO requerimientoDAO = new RequerimientoDAO();
		DRequerimientoDAO drequerimientoDAO = new DRequerimientoDAO();
		KardexRequerimientoDAO kardexDAO = new KardexRequerimientoDAO();

		Requerimiento requerimiento = requerimientoDAO.find(id);

		if (requerimiento == null) {
			return false;
		}

		List<DRequerimiento> drequerimiento;

		drequerimiento = drequerimientoDAO.getPorRequerimiento(requerimiento);
		kardexDAO.borrarPorRequerimiento(requerimiento);

		List<KardexRequerimiento> kardex_list = new ArrayList<KardexRequerimiento>();

		for (DRequerimiento ds : drequerimiento) {

			KardexRequerimiento kardex = new KardexRequerimiento();
			kardex.setRequerimiento(requerimiento);
			kardex.setProducto(ds.getProducto());
			kardex.setUnimedida(ds.getUnimedida());
			kardex.setFactor(1);
			kardex.setCantidad(ds.getCantidad());
			kardex_list.add(kardex);
		}

		kardexDAO.create(kardex_list);

		return true;
	}

	public static boolean ContabilizarDocSalida(long id) {
		DocsalidaDAO salidaDAO = new DocsalidaDAO();
		DetDocSalidaDAO dsalidaDAO = new DetDocSalidaDAO();
		KardexRequerimientoDAO kardexDAO = new KardexRequerimientoDAO();
		RequerimientoDAO requerimientoDAO = new RequerimientoDAO();
		Docsalida salida = salidaDAO.find(id);

		if (salida == null) {
			return false;
		}

		List<DetDocsalida> dsalida;

		dsalida = dsalidaDAO.getPorIdSalida(salida);
		kardexDAO.borrarPorIdReferencia(salida.getIddocsalida());

		List<KardexRequerimiento> kardex_list = new ArrayList<KardexRequerimiento>();

		for (DetDocsalida ds : dsalida) {
			if (ds.getTipo_referencia() != null)
				if (ds.getTipo_referencia().equals("REQINTERNO")) {
					Requerimiento requerimiento = requerimientoDAO.find(ds
							.getId_referencia());

					KardexRequerimiento kardex = new KardexRequerimiento();
					kardex.setRequerimiento(requerimiento);
					kardex.setProducto(ds.getProducto());
					kardex.setUnimedida(ds.getUnimedida());
					kardex.setTipo_referencia(ds.getTipo_referencia());
					kardex.setId_referencia(salida.getIddocsalida());
					kardex.setFactor(-1);
					kardex.setCantidad(ds.getCantidad());
					kardex_list.add(kardex);
				}
		}

		kardexDAO.create(kardex_list);

		return true;
	}
}
