package vista.contenedores;

import core.entity.Grupo;

public class cntGrupo extends AbstractCntBuscar<Grupo> {
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Grupo entity) {
		txtCodigo.setText(entity.getIdgrupo());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(Grupo entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdgrupo().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad)) {
			return true;
		}
		return false;
	}

	@Override
	public Object[] entity2Object(Grupo entity) {
		return new Object[] { entity.getIdgrupo(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Grupo entity) {
		return entity.getIdgrupo();
	}
}
