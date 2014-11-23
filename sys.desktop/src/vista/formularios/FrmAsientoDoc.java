package vista.formularios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import vista.controles.DSGTable;
import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtCuenta;
import vista.controles.celleditor.TxtProducto;
import vista.formularios.abstractforms.AbstractAsientoForm;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.dao.AsientoDAO;
import core.dao.CuentaDAO;
import core.dao.DAsientoDAO;
import core.dao.MonedaDAO;
import core.dao.SubdiarioDAO;
import core.entity.Asiento;
import core.entity.Cuenta;
import core.entity.DAsiento;
import core.entity.Producto;

public class FrmAsientoDoc extends AbstractAsientoForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AsientoDAO asientoDAO = new AsientoDAO();
	DAsientoDAO dasientoDAO = new DAsientoDAO();
	private CuentaDAO cuentaDAO = new CuentaDAO();
	MonedaDAO monedaDAO = new MonedaDAO();
	SubdiarioDAO subdiarioDAO = new SubdiarioDAO();
	Calendar calendar = Calendar.getInstance();
	Asiento asiento = null;
	List<DAsiento> dasiento = null;

	private DSGTable tblasiento;
	protected JPanel pnlPrincipal;
	protected TxtCuenta txtCuenta;
	protected TxtProducto txtProducto;

	public FrmAsientoDoc() {
		super("Asiento Contable");
		getTxtFecha().setLocation(10, 31);
		setResizable(false);
		
		tblasiento = new DSGTable(new DSGTableModel(new String[] { "Cód Cuenta",
				"Cuenta", "Debe", "Haber", "Debe Of.", "Haber Of.", "Debe Ex.",
				"Haber Ex.", "Cod. Producto", "Producto" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if ((column >= 4 && column <= 7) || column == 1)
					return false;

				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
						0.0F, "", "", "", 0 });
			}
		}) {
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {

				super.changeSelection(row, column, toggle, extend);

				if (row > -1) {
					String idcuenta = getDAsientoTM().getValueAt(row, 0)
							.toString();
					txtCuenta.refresValue(idcuenta);
				}
			}
		};
		getScrlDetalle().setViewportView(tblasiento);

		tblasiento.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		txtCuenta = new TxtCuenta(tblasiento, 0) {
			private static final long serialVersionUID = -5472714315399894967L;

			@Override
			public void cargaDatos(Cuenta entity) {
				int row = tblasiento.getSelectedRow();
				if (row > -1) {
					if (entity == null) {
						getDAsientoTM().setValueAt("", row, 0);
						getDAsientoTM().setValueAt("", row, 1);
					} else {
						setText(entity.getIdcuenta());
						getDAsientoTM()
								.setValueAt(entity.getIdcuenta(), row, 0);
						getDAsientoTM().setValueAt(entity.getDescripcion(),
								row, 1);
					}
				}
			}
		};
		txtCuenta.updateCellEditor();
		getDAsientoTM().setNombre_detalle("Asiento");
		getDAsientoTM().setObligatorios(0, 1, 2);
		getDAsientoTM().setRepetidos(0);
		getDAsientoTM().setScrollAndTable(getScrlDetalle(), tblasiento);
		
				
		TableColumnModel tc = tblasiento.getColumnModel();

		tc.getColumn(2).setCellEditor(new FloatEditor(2));
		tc.getColumn(2).setCellRenderer(new FloatRenderer(2));
		
		tc.getColumn(3).setCellEditor(new FloatEditor(2));
		tc.getColumn(3).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(4).setCellEditor(new FloatEditor(2));
		tc.getColumn(4).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(5).setCellEditor(new FloatEditor(2));
		tc.getColumn(5).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(6).setCellEditor(new FloatEditor(2));
		tc.getColumn(6).setCellRenderer(new FloatRenderer(2));

		tc.getColumn(7).setCellEditor(new FloatEditor(2));
		tc.getColumn(7).setCellRenderer(new FloatRenderer(2));

		iniciar();

	}

	public DSGTableModel getDAsientoTM() {
		return (DSGTableModel) tblasiento.getModel();
	}

	@Override
	public void nuevo() {
		setAsiento(new Asiento());
		Calendar c = Calendar.getInstance();
		getAsiento().setAnio(c.get(Calendar.YEAR));
		getAsiento().setMes(c.get(Calendar.MONTH) + 1);
		getAsiento().setDia(c.get(Calendar.DAY_OF_MONTH));
		getAsiento().setEstado(1);
		getAsiento().setTipo('M');
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {

	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_datos() {
		setDasiento(new ArrayList<DAsiento>());
		getDAsientoTM().limpiar();
		if (getAsiento() != null) {
			getCntSubdiario().txtCodigo
					.setText((getAsiento().getSubdiario() == null) ? ""
							: getAsiento().getSubdiario().getIdsubdiario());
			getCntSubdiario().llenar();

			getCntMoneda().txtCodigo
					.setText((getAsiento().getMoneda() == null) ? ""
							: getAsiento().getMoneda().getIdmoneda());
			getCntMoneda().llenar();

			getTxtNumerador().setValue(getAsiento().getNumerador());
			calendar.set(getAsiento().getAnio(), getAsiento().getMes() - 1,
					getAsiento().getDia(), 0, 0, 0);
			getTxtFecha().setDate(calendar.getTime());
			getTxtTCambio().setValue(getAsiento().getTcambio());
			getTxtTCMoneda().setValue(getAsiento().getTcmoneda());
			setDasiento(dasientoDAO.getPorAsiento(getAsiento()));
		} else {
			getCntSubdiario().txtCodigo.setText("");
			getCntSubdiario().llenar();
			getCntMoneda().txtCodigo.setText("");
			getCntMoneda().llenar();
			getTxtNumerador().setText("");
			getTxtFecha().setDate(null);
			getTxtTCambio().setValue(0);
			getTxtTCMoneda().setValue(0);
		}
		llenarDetalle();
	}

	public void llenarDetalle() {
		float debe = 0F, haber = 0F, debe_of = 0F, haber_of = 0F, debe_ex = 0F, haber_ex = 0F;
		for (DAsiento d : getDasiento()) {
			String idproducto = "", producto = "";
			debe += d.getDebe();
			haber += d.getHaber();
			debe_of += d.getDebe_of();
			haber_of += d.getHaber_of();
			debe_ex += d.getDebe_ex();
			haber_ex += d.getHaber_ex();
			Producto p = d.getProducto();
			if (p != null) {
				idproducto = p.getIdproducto();
				producto = p.getDescripcion();
			}
			getDAsientoTM().addRow(
					new Object[] { d.getCuenta().getIdcuenta(),
							d.getCuenta().getDescripcion(), d.getDebe(),
							d.getHaber(), d.getDebe_of(), d.getHaber_of(),
							d.getDebe_ex(), d.getHaber_of(), idproducto,
							producto, d.getId().getItem() });
		}
		getTxtDebe().setValue(debe);
		txtDebeOf.setValue(debe_of);
		txtDebeEx.setValue(debe_ex);

		getTxtHaber().setValue(haber);
		txtHaberOf.setValue(haber_of);
		getTxtHaberEx().setValue(haber_ex);
	}

	@Override
	public void cargarDatos(Object id) {
		this.id = id;
		setAsiento(asientoDAO.find(id));
		setDasiento(dasientoDAO.getPorAsiento(getAsiento()));
	}

	@Override
	public void vista_edicion() {
		FormValidador.TextFieldsEdicion(true, getCntMoneda().txtCodigo,
				getCntSubdiario().txtCodigo, getTxtNumerador(),
				getTxtTCambio(), getTxtTCMoneda(), getTxtDebe(), getTxtHaber(),
				txtDebeEx, getTxtHaberEx(), txtDebeOf, txtHaberOf);
		getDAsientoTM().setEditar(true);
		getTxtGlosa().setEditable(true);
		getTxtFecha().setEditable(true);
	}

	@Override
	public void vista_noedicion() {
		FormValidador.TextFieldsEdicion(false, getCntMoneda().txtCodigo,
				getCntSubdiario().txtCodigo, getTxtNumerador(),
				getTxtTCambio(), getTxtTCMoneda(), getTxtDebe(), getTxtHaber(),
				txtDebeEx, getTxtHaberEx(), txtDebeOf, txtHaberOf);
		getDAsientoTM().setEditar(false);
		getTxtFecha().setEditable(false);
		getTxtGlosa().setEditable(false);
	}

	@Override
	public void actualizar_tablas() {
		if (getCntMoneda() != null)
			getCntMoneda().setData(monedaDAO.findAll());

		if (getCntSubdiario() != null)
			getCntSubdiario().setData(subdiarioDAO.findAll());
		if (txtCuenta != null)
			txtCuenta.setData(cuentaDAO.findAll());
	}

	@Override
	public void llenarDesdeVista() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {
		return false;
	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public List<DAsiento> getDasiento() {
		return dasiento;
	}

	public void setDasiento(List<DAsiento> dasiento) {
		this.dasiento = dasiento;
	}

	@Override
	public boolean isValidaEdicion() {
		if (getAsiento() == null) {
			return false;
		}
		if (getAsiento().getTipo() == 'A') {
			UtilMensajes.mensaje_alterta("DOC_ES_DE_ORIGEN");
			return false;
		}
		return true;
	}

}
