package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Sucursal;

public abstract class TxtSucursal extends JXTableTextField<Sucursal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtSucursal(JTable tabla, int ubicacion) {
		super(new String[] { "Sucursal", "Descripción" },
				new int[] { 50, 130 }, tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Sucursal entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdsucursal().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Sucursal entity) {
		return new Object[] { entity.getIdsucursal(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Sucursal entity) {
		return entity.getIdsucursal();
	}
}
