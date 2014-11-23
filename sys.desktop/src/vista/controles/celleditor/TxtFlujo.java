package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Flujo;

public abstract class TxtFlujo extends JXTableTextField<Flujo> {

	public TxtFlujo(JTable tabla, int ubicacion) {
		super(new String[] { "Flujo", "Descripción" }, new int[] { 50, 130 },
				tabla, ubicacion);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean coincideBusqueda(Flujo entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdflujo().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Flujo entity) {
		return new Object[] { entity.getIdflujo(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Flujo entity) {
		return entity.getIdflujo();
	}
}
