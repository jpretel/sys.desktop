package vista.contenedores;

import core.entity.Unimedida;

public class cntMedida extends AbstractCntBuscar<Unimedida> {
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Unimedida entity) {
		if(entity != null){
			txtCodigo.setText(entity.getIdunimedida());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(Unimedida entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdunimedida().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Unimedida entity) {
		return new Object[] { entity.getIdunimedida(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Unimedida entity) {
		return entity.getIdunimedida();
	}
}
