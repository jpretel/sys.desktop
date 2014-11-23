package core.centralizacion;
import java.util.ArrayList;
import java.util.List;

import core.dao.DOrdenCompraDAO;
import core.dao.DetDocIngresoDAO;
import core.dao.DocingresoDAO;
import core.dao.KardexCompraRecepcionDAO;
import core.dao.OrdenCompraDAO;
import core.entity.DOrdenCompra;
import core.entity.DetDocingreso;
import core.entity.Docingreso;
import core.entity.KardexCompraRecepcion;
import core.entity.OrdenCompra;

public class ContabilizaComprasRecepcion {
	
	public static boolean ContabilizaCompra (long id) {
		OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
		DOrdenCompraDAO dordenCompraDAO = new DOrdenCompraDAO();
		KardexCompraRecepcionDAO kardexCompraRecepcionDAO = new KardexCompraRecepcionDAO();
		
		OrdenCompra ordenCompra = ordenCompraDAO.find(id);
		
		kardexCompraRecepcionDAO.borrarPorReferencia(id);
		List<KardexCompraRecepcion> kardex = new ArrayList<KardexCompraRecepcion>();
		
		for(DOrdenCompra dordenCompra : dordenCompraDAO.getPorOrdenCompra(ordenCompra)){
			KardexCompraRecepcion kdx = new KardexCompraRecepcion();
			kdx.setIdordencompra(id);
			kdx.setIdreferencia(id);
			kdx.setFactor(1);
			kdx.setCantidad(dordenCompra.getCantidad());
			kdx.setProducto(dordenCompra.getProducto());
			kdx.setUnimedida(dordenCompra.getUnimedida());
			kardex.add(kdx);
		}
		kardexCompraRecepcionDAO.create(kardex);
		return true;
	}
	
	
	public static boolean ContabilizaRecepcion (long id) {
		
		DocingresoDAO docingresoDAO = new DocingresoDAO();
		DetDocIngresoDAO detdocingresoDAO = new DetDocIngresoDAO();
		
		KardexCompraRecepcionDAO kardexCompraRecepcionDAO = new KardexCompraRecepcionDAO();
		
		kardexCompraRecepcionDAO.borrarPorReferencia(id);
		
		List<KardexCompraRecepcion> kardex = new ArrayList<KardexCompraRecepcion>();
		Docingreso docingreso = docingresoDAO.find(id);
		for(DetDocingreso detdocingreso: detdocingresoDAO.getPorIdIngreso(docingreso)){
			KardexCompraRecepcion kdx = new KardexCompraRecepcion();
			kdx.setIdordencompra(detdocingreso.getIdreferencia());
			kdx.setIdreferencia(id);
			kdx.setFactor(-1);
			kdx.setCantidad(detdocingreso.getCantidad());
			kdx.setProducto(detdocingreso.getProducto());
			kdx.setUnimedida(detdocingreso.getUnimedida());
			kardex.add(kdx);
		}
		
		kardexCompraRecepcionDAO.create(kardex);
		return true;
	}
}
