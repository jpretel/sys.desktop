package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Producto;

public abstract class TxtProducto extends JXTableTextField<Producto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtProducto(JTable tabla, int ubicacion) {
		super(new String[] { "Producto", "Descripción", "UM"}, new int[] { 50, 290, 40},
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Producto entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdproducto().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Producto entity) {
		return new Object[] { entity.getIdproducto(), entity.getDescripcion(), entity.getUnimedida().getIdunimedida()};
	}

	@Override
	public String getEntityCode(Producto entity) {
		return entity.getIdproducto();
	}
}
