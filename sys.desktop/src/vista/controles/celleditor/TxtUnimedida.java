package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Unimedida;

public abstract class TxtUnimedida extends JXTableTextField<Unimedida> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtUnimedida(JTable tabla, int ubicacion) {
		super(new String[] { "Medida", "Descripción" }, new int[] { 50, 130 },
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Unimedida entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdunimedida().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
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
