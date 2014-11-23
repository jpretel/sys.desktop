package vista.contenedores;
import core.entity.SysModulo;

public class ctnModulo extends AbstractCntBuscar<SysModulo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void cargarDatos(SysModulo entity) {
		// TODO Auto-generated method stub
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdmodulo());
			txtDescripcion.setText(entity.getDescripcion());
		}
		
	}

	@Override
	public boolean coincideBusqueda(SysModulo entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdmodulo().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad)) {
			return true;
		}
		return false;
	}

	@Override
	public Object[] entity2Object(SysModulo entity) {
		return new Object[] { entity.getIdmodulo(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(SysModulo entity) {
		return entity.getIdmodulo();
	}

}
