package vista.contenedores;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import core.entity.Usuario;

public class CntUsuario extends AbstractCntBuscar<Usuario> {
	public CntUsuario() {
		GridBagLayout gridBagLayout = new GridBagLayout();

		gridBagLayout.columnWidths = new int[] { 96, 106, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_txtCodigo = new GridBagConstraints();

		gbc_txtCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCodigo.gridx = 0;
		gbc_txtCodigo.gridy = 0;
		add(txtCodigo, gbc_txtCodigo);
		
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 0;
		add(txtDescripcion, gbc_txtDescripcion);
		
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 0;
		add(btnBuscar, gbc_btnBuscar);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void cargarDatos(Usuario entity) {
		txtCodigo.setText(entity.getIdusuario());
		txtDescripcion.setText(entity.getNombres());
	}

	@Override
	public boolean coincideBusqueda(Usuario entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdusuario().toLowerCase().startsWith(cad)
				|| entity.getNombres().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Usuario entity) {
		return new Object[] { entity.getIdusuario(), entity.getNombres() };
	}

	@Override
	public String getEntityCode(Usuario entity) {
		return entity.getIdusuario();
	}
}
