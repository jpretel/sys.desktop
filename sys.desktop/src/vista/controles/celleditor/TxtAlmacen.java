package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Almacen;

public abstract class TxtAlmacen extends JXTableTextField<Almacen> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtAlmacen(JTable tabla, int ubicacion) {
		super(new String[] { "Almacen", "Descripción" },
				new int[] { 50, 130 }, tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Almacen entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getId().getIdalmacen().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Almacen entity) {
		return new Object[] { entity.getId().getIdalmacen(),
				entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Almacen entity) {
		return entity.getId().getIdalmacen();
	}
}
