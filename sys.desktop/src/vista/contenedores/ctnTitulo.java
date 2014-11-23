package vista.contenedores;

import core.entity.SysTitulo;

public class ctnTitulo extends AbstractCntBuscar<SysTitulo> {
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(SysTitulo entity) {
		txtCodigo.setText(entity.getId().getIdtitulo());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(SysTitulo entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getId().getIdtitulo().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(SysTitulo entity) {
		return new Object[] { entity.getId().getIdtitulo(),
				entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(SysTitulo entity) {
		return entity.getId().getIdtitulo();
	}
}
