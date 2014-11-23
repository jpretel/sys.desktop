package vista.contenedores;

import java.awt.GridBagLayout;

import core.entity.Clieprov;

public class CntClieprov extends AbstractCntBuscar<Clieprov> {

	private static final long serialVersionUID = 1L;

	public CntClieprov() {
		super(new String[] { "Código", "Razón Social", "RUC" }, new int[] { 130,
				250, 90 });
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.columnWidths = new int[]{92, 0, 0};
	}

	@Override
	public void cargarDatos(Clieprov entity) {
		if (entity == null) {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		} else {
			txtCodigo.setText(entity.getIdclieprov());
			txtDescripcion.setText(entity.getRazonSocial());
		}
	}

	@Override
	public boolean coincideBusqueda(Clieprov entity, String cadena) {
		String cad = cadena.toLowerCase();
		if (entity.getIdclieprov().toLowerCase().startsWith(cad)
				|| entity.getRazonSocial().toLowerCase().startsWith(cad))
			return true;
		return false;
	}

	@Override
	public Object[] entity2Object(Clieprov entity) {
		return new Object[] { entity.getIdclieprov(), entity.getRazonSocial(),
				entity.getRuc() };
	}

	@Override
	public String getEntityCode(Clieprov entity) {
		return entity.getIdclieprov();
	}
}