package vista.formularios.documentos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import vista.formularios.abstractforms.AbstractDocList;
import vista.formularios.listas.DSGTableList;
import vista.formularios.listas.DSGTableModelList;
import vista.utilitarios.StringUtils;
import core.dao.DocsalidaDAO;
import core.entity.Docsalida;


/*Falta Aplicar los filtros y verificar los botones edicion y impresion*/
public class FrmListaSalida extends AbstractDocList {
	private DocsalidaDAO docSalidaDAO = new DocsalidaDAO();
	private List<Docsalida> lista = new ArrayList<Docsalida>();
	private Object[][] data;	
	public FrmListaSalida() {
		super("Lista de Notas de Salidas", "vista.formularios.documentos.FrmDocSalida");
		cboDocumento.setVisible(false);
		lblDocumento.setVisible(false);
		cabeceras = new String[] {"Id","Fecha","Serie", "Numero","Responsable","Sucursal","Almacen"};
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
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Object[][] getData(int idesde, int ihasta,String serie, int numero) {
		lista = docSalidaDAO.getFiltro(idesde, ihasta, serie, numero);
		data = new Object[lista.size()][3];
		int i = 0;
		for (Docsalida salida : lista) {
			Calendar c = GregorianCalendar.getInstance();
			c.set(salida.getAnio(), salida.getMes() - 1, salida.getDia());
			String cnumero = StringUtils._padl(salida.getNumero(), 8, '0');
			data[i] = new Object[] {salida.getIddocsalida(),c.getTime(),salida.getSerie(),cnumero,salida.getResponsable().getNombre(),
					salida.getAlmacen().getSucursal().getDescripcion(),salida.getAlmacen().getDescripcion()};
			i++;
		}
		return data;
	}

	@Override
	public Object getPK() {
		int row = tblDocumentos.getSelectedRow();
		if (row > -1) {
			return data[row][0];
		}
		return null;
	}
}
