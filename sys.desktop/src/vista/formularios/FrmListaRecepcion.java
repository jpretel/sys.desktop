package vista.formularios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import vista.formularios.abstractforms.AbstractDocList;
import vista.formularios.listas.DSGTableList;
import vista.formularios.listas.DSGTableModelList;
import vista.utilitarios.StringUtils;
import core.dao.DocingresoDAO;
import core.entity.Docingreso;

public class FrmListaRecepcion extends AbstractDocList {
	private static final long serialVersionUID = 1L;
	private DocingresoDAO docIngresoDAO = new DocingresoDAO();
	private List<Docingreso> lista = new ArrayList<Docingreso>();

	public FrmListaRecepcion() {
		super("Lista de Notas de Ingreso", "vista.formularios.FrmDocRecepcion");
		cboDocumento.setVisible(false);
		lblDocumento.setVisible(false);
		cabeceras = new String[] { "Id", "Fecha", "Serie", "Numero",
				"Responsable", "Sucursal", "Almacen" };
		tblDocumentos = new DSGTableList(6) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void DoDobleClick(int row) {
				ver();
			}
		};

		modelo_lista = new DSGTableModelList(cabeceras);
		tblDocumentos.setModel(modelo_lista);

		pnlDocumentos.setViewportView(tblDocumentos);
		llenarLista();
	}


	@Override
	public Object[][] getData(int idesde, int ihasta, String serie, int numero) {
		lista = docIngresoDAO.getFiltro(idesde, ihasta, serie, numero);
		data = new Object[lista.size()][3];
		int i = 0;
		for (Docingreso ingreso : lista) {
			Calendar c = GregorianCalendar.getInstance();
			c.set(ingreso.getAnio(), ingreso.getMes() - 1, ingreso.getDia());
			String cnumero = StringUtils._padl(ingreso.getNumero(), 8, '0');
			data[i] = new Object[] { ingreso.getIddocingreso(), c.getTime(),
					ingreso.getSerie(), cnumero,
					ingreso.getResponsable().getNombre(),
					ingreso.getAlmacen().getSucursal().getDescripcion(),
					ingreso.getAlmacen().getDescripcion() };
			i++;
		}
		return data;
	}

	@Override
	public Object getPK() {
		int row = tblDocumentos.getSelectedRow();
		if (row > -1)
			return data[row][0];
		return null;
	}
}
