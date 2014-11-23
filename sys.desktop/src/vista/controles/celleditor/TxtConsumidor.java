package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Consumidor;

public abstract class TxtConsumidor extends JXTableTextField<Consumidor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtConsumidor(JTable tabla, int ubicacion) {
		super(new String[] { "Producto", "Descripción" },
				new int[] { 50, 130 }, tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Consumidor entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdconsumidor().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Consumidor entity) {
		return new Object[] { entity.getIdconsumidor(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Consumidor entity) {
		return entity.getIdconsumidor();
	}
}
