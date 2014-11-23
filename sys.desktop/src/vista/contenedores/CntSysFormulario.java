package vista.contenedores;

import core.entity.SysFormulario;

public class CntSysFormulario extends AbstractCntBuscar<SysFormulario> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(SysFormulario entity) {
		txtCodigo.setText(entity.getIdformulario());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(SysFormulario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdformulario().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
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
