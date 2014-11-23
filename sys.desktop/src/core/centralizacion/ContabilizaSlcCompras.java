package core.centralizacion;

import java.util.ArrayList;
import java.util.List;

import core.dao.CotizacionCompraDAO;
import core.dao.DCotizacionCompraDAO;
import core.dao.DDOrdenCompraDAO;
import core.dao.DOrdenCompraDAO;
import core.dao.DSolicitudCompraDAO;
import core.dao.KardexSlcCompraDAO;
import core.dao.OrdenCompraDAO;
import core.dao.SolicitudCompraDAO;
import core.entity.CotizacionCompra;
import core.entity.DCotizacionCompra;
import core.entity.DDOrdenCompra;
import core.entity.DOrdenCompra;
import core.entity.DSolicitudCompra;
import core.entity.KardexSlcCompra;
import core.entity.OrdenCompra;
import core.entity.SolicitudCompra;
import core.entity.Unimedida;

public class ContabilizaSlcCompras {
	public static boolean ContabilizaSolicitud(long id) {
		SolicitudCompraDAO slcDAO = new SolicitudCompraDAO();
		DSolicitudCompraDAO dslcDAO = new DSolicitudCompraDAO();
		KardexSlcCompraDAO kardexDAO = new KardexSlcCompraDAO();

		SolicitudCompra solicitud = slcDAO.find(id);

		if (solicitud == null) {			
			return false;
		}

		List<DSolicitudCompra> dsolicitud;

		dsolicitud = dslcDAO.getPorSolicitudCompra(solicitud);
		kardexDAO.borrarPorIdSolicitudCompra(id);

		List<KardexSlcCompra> kardex_list = new ArrayList<KardexSlcCompra>();

		for (DSolicitudCompra ds : dsolicitud) {
			
			KardexSlcCompra kardex = new KardexSlcCompra();
			kardex.setSolicitudcompra(solicitud);
			kardex.setProducto(ds.getProducto());
			kardex.setUnimedida(ds.getUnimedida());
			kardex.setFactor(1);
			kardex.setCantidad(ds.getCantidad());
			kardex_list.add(kardex);
		}
		
		kardexDAO.create(kardex_list);

		return true;
	}
	
	public static boolean ContabilizaCotizacion(long id){
		CotizacionCompraDAO ctzaDAO = new CotizacionCompraDAO();
		DCotizacionCompraDAO dctzaDAO = new DCotizacionCompraDAO();
		KardexSlcCompraDAO kardexDAO = new KardexSlcCompraDAO();
		
		CotizacionCompra cotizacion = ctzaDAO.find(id);
		
		if(cotizacion == null){
			return false;
		}
		List<DCotizacionCompra> dcotizacion = dctzaDAO.getPorCotizacionCompra(cotizacion);
		kardexDAO.borrarPorIdCotizacionCompra(id);

		List<KardexSlcCompra> kardex_list = new ArrayList<KardexSlcCompra>();
		for (DCotizacionCompra ds : dcotizacion) {			
			KardexSlcCompra kardex = new KardexSlcCompra();
			kardex.setCotizacioncompra(cotizacion);
			kardex.setProducto(ds.getProducto());
			kardex.setUnimedida(ds.getUnimedida());
			kardex.setFactor(1);
			kardex.setCantidad(ds.getCantidad());
			kardex_list.add(kardex);		
		}
		
		kardexDAO.create(kardex_list);
		return true;
	}

	public static boolean ContabilizaOrdenCompra(long id) {
		OrdenCompraDAO ordDAO = new OrdenCompraDAO();
		DDOrdenCompraDAO ddordDAO = new DDOrdenCompraDAO();
		DOrdenCompraDAO dordDAO = new DOrdenCompraDAO();
		KardexSlcCompraDAO kardexDAO = new KardexSlcCompraDAO();
		SolicitudCompraDAO slcDAO = new SolicitudCompraDAO();
		
		OrdenCompra orden = ordDAO.find(id);

		if (orden == null) {
			return false;
		}

		List<DDOrdenCompra> ddorden;
		List<DOrdenCompra> dorden;
		
		ddorden = ddordDAO.getPorOrdenCompra(orden);
		dorden = dordDAO.getPorOrdenCompra(orden);
		
		kardexDAO.borrarPorIdOrdenCompra(id);

		List<KardexSlcCompra> kardex_list = new ArrayList<KardexSlcCompra>();
			
		for (DDOrdenCompra o : ddorden) {

			if (o.getTipo_referencia().equals("SLC_COMPRA")) {
				//Buscar unidad de medida
				Unimedida unimedida = null;
				for(DOrdenCompra doc : dorden) {
					if (doc.getProducto().getIdproducto().equals(o.getProducto().getIdproducto()))
						unimedida = doc.getUnimedida();
				}
				
				long id_referencia = o.getId_referencia();
				KardexSlcCompra kardex = new KardexSlcCompra();
				SolicitudCompra slc = slcDAO.find(id_referencia);
				kardex.setProducto(o.getProducto());
				
				kardex.setUnimedida(unimedida);
				
				kardex.setCantidad(o.getCantidad());
				kardex.setTipo_referencia("ORD_COMPRA");
				kardex.setSolicitudcompra(slc);
				kardex.setId_referencia(id);
				kardex.setFactor(-1);
				kardex_list.add(kardex);
			}
		}
		
		kardexDAO.create(kardex_list);

		return true;
	}
}
