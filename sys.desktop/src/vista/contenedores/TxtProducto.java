package vista.contenedores;

import core.dao.ProductoDAO;
import core.entity.Producto;

public class TxtProducto extends JXTextFieldEntityAC<Producto> {
	ProductoDAO productoDAO = new ProductoDAO();
	
	private static final long serialVersionUID = 1L;

	public TxtProducto() {
		super(getFormulario(),
				new String[] { "Cod. Producto", "Descripción" }, new int[] {
						200, 350 });
		refrescar();
	}

	public void cargarDatos(Producto entity) {
		if (entity == null) {
			setText("");
		} else {
			setText(entity.getIdproducto());
		}
	}
	@Override
	public boolean coincideBusqueda(Producto entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdproducto().toLowerCase().startsWith(cad)
				|| entity.getDescripcion().toLowerCase().startsWith(cad))
			return true;
		else
			return false;
	}

	@Override
	public Object[] entity2Object(Producto entity) {
		return new Object[] { entity.getIdproducto(), entity.getDescripcion() };
	}

	public void refrescar() {
		setData(productoDAO.findAll());
	}

	@Override
	public void cargaDatos() {
		cargarDatos(getSeleccionado());
	}
	
	@Override
	public int getMinimoBusqueda() {
		return 1;
	}

	@Override
	public String getEntityCode(Producto entity) {
		// TODO Auto-generated method stub
		return entity.getIdproducto();
	}
}