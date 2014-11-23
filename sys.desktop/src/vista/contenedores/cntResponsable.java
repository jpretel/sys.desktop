package vista.contenedores;

import core.entity.Responsable;

public class cntResponsable extends AbstractCntBuscar<Responsable> {

	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Responsable entity) {
		txtCodigo.setText(entity.getIdresponsable());
		txtDescripcion.setText(entity.getNombre());

	}

	// opciones de Busqueda
	@Override
	public boolean coincideBusqueda(Responsable entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdresponsable().toLowerCase().startsWith(cad)
				|| entity.getNombre().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	// Informacion que se carga en el Windows Popup
	@Override
	public Object[] entity2Object(Responsable entity) {
		return new Object[] { entity.getIdresponsable(), entity.getNombre() };
	}

	@Override
	public String getEntityCode(Responsable entity) {
		return entity.getIdresponsable();
	}
}
