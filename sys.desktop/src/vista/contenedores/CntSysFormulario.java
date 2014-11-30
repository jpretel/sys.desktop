package vista.contenedores;

import core.entity.SysFormulario;
import java.awt.GridBagLayout;

public class CntSysFormulario extends AbstractCntBuscar<SysFormulario> {
	public CntSysFormulario() {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.columnWidths = new int[]{123, 0, 0};
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(SysFormulario entity) {
		txtCodigo.setText(entity.getIdformulario());
		txtDescripcion.setText(entity.getDescripcion());
	}

	@Override
	public boolean coincideBusqueda(SysFormulario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdformulario().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(SysFormulario entity) {
		return new Object[] { entity.getIdformulario(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(SysFormulario entity) {
		return entity.getIdformulario();
	}
}
