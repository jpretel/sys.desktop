package vista.contenedores;

import core.entity.Producto;

public class CntProducto extends AbstractCntBuscar<Producto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Producto entity) {
		txtCodigo.setText(entity.getIdproducto());
		txtDescripcion.setText(entity.getDescripcion());
	}

	public CntProducto() {
		super(new String[] { "Cód Prod.", "Desc Prod.", "U.M." }, new int[] {
				90, 100, 60 });
	}
	
	@Override
	public boolean coincideBusqueda(Producto entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdproducto().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Producto entity) {
		return new Object[] { entity.getIdproducto(), entity.getDescripcion(),
				entity.getUnimedida().getDescripcion() };
	}

	@Override
	public String getEntityCode(Producto entity) {
		return entity.getIdproducto();
	}
}
