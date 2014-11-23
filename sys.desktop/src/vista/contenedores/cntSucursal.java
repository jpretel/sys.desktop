package vista.contenedores;

import core.entity.Sucursal;

public class cntSucursal extends AbstractCntBuscar<Sucursal> {
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Sucursal entity) {
		txtCodigo.setText(entity.getIdsucursal());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(Sucursal entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdsucursal().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad)) {
			return true;
		}
		return false;
	}

	@Override
	public Object[] entity2Object(Sucursal entity) {
		return new Object[] { entity.getIdsucursal(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Sucursal entity) {
		return entity.getIdsucursal();
	}
}
