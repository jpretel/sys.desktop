package vista.contenedores;
import core.entity.GrupoCentralizacion;

public class CntGrupoCentralizacion extends AbstractCntBuscar<GrupoCentralizacion> {
	public CntGrupoCentralizacion() {
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(GrupoCentralizacion entity) {
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdgcentralizacion());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(GrupoCentralizacion entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdgcentralizacion().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		else
			return false;
	}

	@Override
	public Object[] entity2Object(GrupoCentralizacion entity) {
		return new Object[] { entity.getIdgcentralizacion(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(GrupoCentralizacion entity) {
		return entity.getIdgcentralizacion();
	}
}
