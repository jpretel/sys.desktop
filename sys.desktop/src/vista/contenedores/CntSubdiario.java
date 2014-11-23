package vista.contenedores;

import core.entity.Subdiario;

public class CntSubdiario extends AbstractCntBuscar<Subdiario> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Subdiario entity) {
		txtCodigo.setText(entity.getIdsubdiario());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(Subdiario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdsubdiario().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Subdiario entity) {
		return new Object[] { entity.getIdsubdiario(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Subdiario entity) {
		return entity.getIdsubdiario();
	}
}
