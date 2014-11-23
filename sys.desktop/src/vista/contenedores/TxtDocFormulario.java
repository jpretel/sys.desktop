package vista.contenedores;

import core.dao.DocFormularioDAO;
import core.entity.DocFormulario;

public class TxtDocFormulario extends JXTextFieldEntityAC<DocFormulario> {
	DocFormularioDAO docFormularioDAO = new DocFormularioDAO();
	
	private static final long serialVersionUID = 1L;

	public TxtDocFormulario() {
		super(getFormulario(),
				new String[] { "Serie", "Numero" }, new int[] {
						200, 350 });
		refrescar();
	}

	public void cargarDatos(DocFormulario entity) {
		if (entity == null) {
			setText("");
		} else {
			setText(entity.getDocumento().getDocumentoNumeros().get(0).getId().getSerie()+'-'+
					entity.getDocumento().getDocumentoNumeros().get(0).getNumero());
		}
	}

	@Override
	public boolean coincideBusqueda(DocFormulario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getDocumento().getDocumentoNumeros().get(0).getId().getSerie().toLowerCase().startsWith(cad)
				|| entity.getDocumento().getDocumentoNumeros().get(0).getNumero().toLowerCase().startsWith(cad))
			return true;
		else
			return false;
	}

	@Override
	public Object[] entity2Object(DocFormulario entity) {
		return new Object[] {entity.getDocumento().getDocumentoNumeros().get(0).getId().getSerie(), 
				entity.getDocumento().getDocumentoNumeros().get(0).getNumero() };
	}

	public void refrescar() {
		setData(docFormularioDAO.findAll());
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
	public String getEntityCode(DocFormulario entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
