package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.SysFormulario;

public abstract class TxtSysFormulario extends JXTableTextField<SysFormulario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtSysFormulario(JTable tabla, int ubicacion) {
		super(new String[] { "Formulario", "Descripción", "Clase" },
				new int[] { 50, 130, 90 }, tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(SysFormulario entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdformulario().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(SysFormulario entity) {
		return new Object[] { entity.getIdformulario(),
				entity.getDescripcion(), entity.getClase() };
	}

	@Override
	public String getEntityCode(SysFormulario entity) {
		return entity.getIdformulario();
	}
}
