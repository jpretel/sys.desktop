package vista.contenedores;
import core.dao.SysOpcionDAO;
import core.entity.SysFormulario;

public class txtidformulario extends AbstractTxtBuscar<SysFormulario>{
	SysOpcionDAO sysopcdao = new SysOpcionDAO(); 
	private static final long serialVersionUID = 1L;
	private String descripcion;
	
	public txtidformulario() {
		super();
		refrescar();
	}

	@Override
	public void cargarDatos(SysFormulario entity) {
		if(entity == null )
			txtCodigo.setText("");
		else{			
			setText(entity.getIdformulario());
			setDescripcion(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(SysFormulario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdformulario().toLowerCase().startsWith(cad) || entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		else
			return false;
	}

	@Override
	public Object[] entity2Object(SysFormulario entity) {
		return new Object[] { entity.getIdformulario(),entity.getDescripcion()};
	}

	@Override
	public void refrescar() {
		//setData(sysopcdao.findAll());
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
