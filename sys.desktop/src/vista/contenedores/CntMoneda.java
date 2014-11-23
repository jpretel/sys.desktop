package vista.contenedores;

import core.entity.Moneda;

public class CntMoneda extends AbstractCntBuscar<Moneda> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Moneda entity) {
		txtCodigo.setText(entity.getIdmoneda());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(Moneda entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdmoneda().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Moneda entity) {
		return new Object[] { entity.getIdmoneda(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Moneda entity) {
		return entity.getIdmoneda();
	}
}
