package vista.formularios.maestros;
import java.util.List;

import vista.formularios.abstractforms.AbstractMaestroLista;
import core.dao.ClieprovDAO;
import core.entity.Clieprov;

public class FrmListaClieProv extends AbstractMaestroLista {
	private static final long serialVersionUID = 1L;
	
	private ClieprovDAO cdao = new ClieprovDAO();
	private Object[] obj;
	private List<Clieprov> clieprovL = cdao.findAll();
	private Clieprov clieprov;
	public FrmListaClieProv() {
		super("Lista de Clientes");
		String columnas[] = {"Codigo", "RUC", "Razon Social", "Direccion"};
		super.inicia_Lista(columnas, obj);
		llenar_lista();
	}
	
	@Override
	public void irFormulario(String opcion) {		
		if (RetornarPk() instanceof Object){
			clieprov = cdao.find(RetornarPk());
		}
		FrmClieprov frmclieprov = new FrmClieprov();
		init(frmclieprov, opcion, clieprov);
	}

	@Override
	public void llenar_lista() {
		modeloLista.limpiar();
		for (Clieprov c: clieprovL) {
			modeloLista.addRow(new Object[]{c.getIdclieprov(),c.getRuc(),c.getRazonSocial(),c.getDireccion()});
		}
	}
}
