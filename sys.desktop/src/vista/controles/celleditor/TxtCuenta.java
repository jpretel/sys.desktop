package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Cuenta;

public abstract class TxtCuenta extends JXTableTextField<Cuenta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtCuenta(JTable tabla, int ubicacion) {
		super(new String[] { "Cuenta", "Descripción" }, new int[] { 50, 130 },
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Cuenta entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdcuenta().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Cuenta entity) {
		return new Object[] { entity.getIdcuenta(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Cuenta entity) {
		return entity.getIdcuenta();
	}
}
