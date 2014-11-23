package vista.formularios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import vista.formularios.listas.AbstractDocList;
import vista.formularios.listas.DSGTableList;
import vista.formularios.listas.DSGTableModelList;
import vista.utilitarios.StringUtils;
import core.dao.SolicitudCotizacionDAO;
import core.entity.SolicitudCotizacion;

public class FrmListaSolicitudCotizacion extends AbstractDocList {

	// private solicitudcompraDAO solicitudcompraDAO = new solicitudcompraDAO();
	private SolicitudCotizacionDAO solicitudDAO = new SolicitudCotizacionDAO();
	private List<SolicitudCotizacion> lista = new ArrayList<SolicitudCotizacion>();

	public FrmListaSolicitudCotizacion() {
		super("Lista de Solicitud de Cotización",
				"vista.formularios.FrmDocSolicitudCotizacion");
		cboDocumento.setVisible(false);
		lblDocumento.setVisible(false);
		cabeceras = new String[] { "Fecha", "Serie", "Numero", "Proveedor",
				"Glosa" };
		tblDocumentos = new DSGTableList() {
			private static final long serialVersionUID = 1L;

			@Override
			public void DoDobleClick(int row) {
				ver();
			}
		};

		modelo_lista = new DSGTableModelList(cabeceras);
		tblDocumentos.setModel(modelo_lista);

		pnlDocumentos.setViewportView(tblDocumentos);
		// llenarLista();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getData(int idesde, int ihasta, String serie, int numero) {
		lista = solicitudDAO.getFiltro(idesde, ihasta, serie, numero);
		data = new Object[lista.size()][3];
		int i = 0;
		for (SolicitudCotizacion oc : lista) {
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.YEAR, oc.getAnio());
			c.set(Calendar.MONTH, oc.getMes() - 1);
			c.set(Calendar.DAY_OF_MONTH, oc.getDia());

			String cnumero = StringUtils._padl(oc.getNumero(), 8, '0');

			data[i] = new Object[] {
					c.getTime(),
					oc.getSerie(),
					cnumero,
					(oc.getClieprov() == null) ? "" : oc.getClieprov()
							.getRazonSocial(), oc.getGlosa(),
					oc.getIdsolicitudcotizacion() };
			i++;
		}
		return data;
	}

	@Override
	public Object getPK() {
		int row = tblDocumentos.getSelectedRow();
		if (row > -1) {
			return data[row][data[0].length - 1];
		}
		return null;
	}
}
