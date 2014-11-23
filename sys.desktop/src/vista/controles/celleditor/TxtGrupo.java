package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Grupo;

public abstract class TxtGrupo extends JXTableTextField<Grupo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtGrupo(JTable tabla, int ubicacion) {
		super(new String[] { "Código", "Descripción" }, new int[] { 50, 130 },
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Grupo entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getIdgrupo().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Grupo entity) {
		return new Object[] { entity.getIdgrupo(), entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Grupo entity) {
		return entity.getIdgrupo();
	}
}
