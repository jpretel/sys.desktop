package vista.formularios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import vista.formularios.listas.AbstractDocList;
import vista.formularios.listas.DSGTableList;
import vista.formularios.listas.DSGTableModelList;
import vista.utilitarios.StringUtils;
import core.dao.OrdenCompraDAO;
import core.entity.OrdenCompra;

/*Falta Aplicar los filtros y verificar los botones edicion y impresion*/
public class FrmListaOrdenCompra extends AbstractDocList {
	private OrdenCompraDAO ordencompraDAO = new OrdenCompraDAO();
	private List<OrdenCompra> lista = new ArrayList<OrdenCompra>();

	public FrmListaOrdenCompra() {
		super("Lista de Ordenes de Compra", "vista.formularios.FrmDocOrdenCompra");
		cboDocumento.setVisible(false);
		lblDocumento.setVisible(false);
		cabeceras = new String[] { "Fecha", "Serie", "Numero", "Responsable",
				"Razón Social" };
		tblDocumentos = new DSGTableList(4) {
			private static final long serialVersionUID = 1L;

			@Override
			public void DoDobleClick(int row) {
				ver();
			}
		};

		modelo_lista = new DSGTableModelList(cabeceras);
		tblDocumentos.setModel(modelo_lista);

		pnlDocumentos.setViewportView(tblDocumentos);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getData(int idesde, int ihasta, String serie, int numero) {
		lista = ordencompraDAO.getFiltro(idesde, ihasta, serie, numero);
		data = new Object[lista.size()][3];
		int i = 0;
		for (OrdenCompra oc : lista) {
			Calendar c = GregorianCalendar.getInstance();
			c.set(Calendar.YEAR, oc.getAnio());
			c.set(Calendar.MONTH, oc.getMes() - 1);
			c.set(Calendar.DAY_OF_MONTH, oc.getDia());

			String cnumero = StringUtils._padl(oc.getNumero(), 8, '0');

			data[i] = new Object[] {
					c.getTime(),
					oc.getSerie(),
					cnumero,
					oc.getResponsable().getNombre(),
					(oc.getClieprov() == null ? "" : oc.getClieprov()
							.getRazonSocial()), oc.getIdordencompra() };
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
