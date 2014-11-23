package vista.contenedores;
import core.entity.Concepto;

public class CntConcepto extends AbstractCntBuscar<Concepto> {
	public CntConcepto() {
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Concepto entity) {
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdconcepto());
			txtDescripcion.setText(entity.getDescripcion());
		}
	}

	@Override
	public boolean coincideBusqueda(Concepto entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdconcepto().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Concepto entity) {
		return new Object[] { entity.getIdconcepto(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Concepto entity) {
		return entity.getIdconcepto();
	}
}