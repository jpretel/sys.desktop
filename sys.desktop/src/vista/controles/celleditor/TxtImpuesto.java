package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Impuesto;

public abstract class TxtImpuesto extends JXTableTextField<Impuesto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtImpuesto(JTable tabla, int ubicacion) {
		super(new String[] { "Impuesto", "Descripción" }, new int[] { 50, 130 },
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Impuesto entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdimpuesto().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Impuesto entity) {
		return new Object[] { entity.getIdimpuesto(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Impuesto entity) {
		return entity.getIdimpuesto();
	}
}
