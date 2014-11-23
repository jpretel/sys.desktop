package vista.controles.celleditor;

import javax.swing.JTable;

import core.entity.Subgrupo;

public abstract class TxtSubGrupo extends JXTableTextField<Subgrupo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TxtSubGrupo(JTable tabla, int ubicacion) {
		super(new String[] { "Código", "Descripcicón" }, new int[] { 90, 120 },
				tabla, ubicacion);
	}

	@Override
	public boolean coincideBusqueda(Subgrupo entity, String cadena) {
		cadena = cadena.trim().toLowerCase();
		return entity.getId().getIdsubgrupo().toLowerCase().startsWith(cadena)
				|| entity.getDescripcion().toLowerCase().startsWith(cadena);
	}

	@Override
	public Object[] entity2Object(Subgrupo entity) {
		return new Object[] { entity.getId().getIdsubgrupo(),
				entity.getDescripcion() };
	}

	@Override
	public String getEntityCode(Subgrupo entity) {
		return entity.getId().getIdsubgrupo();
	}

}
