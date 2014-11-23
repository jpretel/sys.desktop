package vista.contenedores;

import core.entity.GrupoUsuario;

public class CntGrupoUsuario extends AbstractCntBuscar<GrupoUsuario> {
	private static final long serialVersionUID = 1L;

	public CntGrupoUsuario() {
		super();
	}

	@Override
	public void cargarDatos(GrupoUsuario entity) {
		txtCodigo.setText(entity.getIdgrupoUsuario());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(GrupoUsuario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdgrupoUsuario().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(GrupoUsuario entity) {
		return new Object[] { entity.getIdgrupoUsuario(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(GrupoUsuario entity) {
		return entity.getIdgrupoUsuario();
	}
}
