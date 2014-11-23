package vista.contenedores;
import core.entity.SysFormulario;
public class cntFormulario extends AbstractCntBuscar<SysFormulario> {
	private static final long serialVersionUID = 1L;

	public cntFormulario() {
		super();
		txtDescripcion.setBounds(107, 0, 220, 20);
		txtCodigo.setBounds(0, 0, 110, 20);
		setLayout(null);
	}

	@Override
	public void cargarDatos(SysFormulario entity) {
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdformulario());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(SysFormulario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdformulario().toLowerCase().startsWith(cad) || entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(SysFormulario entity) {
		return new Object[] { entity.getIdformulario(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(SysFormulario entity) {
		return entity.getIdformulario();
	}
}
