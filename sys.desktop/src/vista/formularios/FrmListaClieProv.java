package vista.formularios;
import java.util.List;

import vista.formularios.abstractforms.AbstractMaestroLista;
import vista.formularios.maestros.FrmClieprov;
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

	@Override
	public void anular() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void grabar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void llenar_datos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void llenar_tablas() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vista_edicion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vista_noedicion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		
		
	}

	@Override
	public void llenarDesdeVista() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValidaVista() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}
}
