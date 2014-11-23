package vista.contenedores;

import core.entity.Area;

public class cntArea extends AbstractCntBuscar<Area> {
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Area entity) {
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdarea());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(Area entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdarea().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		else
			return false;
	}

	@Override
	public Object[] entity2Object(Area entity) {
		return new Object[] { entity.getIdarea(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Area entity) {
		return entity.getIdarea();
	}
}
