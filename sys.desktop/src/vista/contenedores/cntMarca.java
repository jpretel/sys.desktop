package vista.contenedores;

import core.entity.Marca;

public class cntMarca extends AbstractCntBuscar<Marca> {
	private static final long serialVersionUID = 1L;

	public cntMarca() {
		super();
	}

	@Override
	public void cargarDatos(Marca entity) {
		if(entity != null){
			txtCodigo.setText(entity.getIdmarca());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(Marca entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdmarca().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Marca entity) {
		return new Object[] { entity.getIdmarca(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Marca entity) {
		return entity.getIdmarca();
	}
}
