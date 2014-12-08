package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.CuentaDAO;
import core.entity.Cuenta;

public class FrmCuentas extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private Cuenta cuenta;

	private CuentaDAO cuentaDAO = new CuentaDAO();

	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JCheckBox chckbxProducto;
	private JCheckBox chckbxConsumidor;
	private JCheckBox chckbxDocumento;

	public FrmCuentas() {
		super("Cuentas Contables");
		initGUI();
	}

	private void initGUI() {

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(287, 26, 39, 16);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(360, 26, 129, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(10, true));

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(287, 55, 68, 16);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 257, 228);

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(360, 52, 173, 22);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		pnlContenido.setLayout(null);
		pnlContenido.add(scrollPane);
		pnlContenido.add(lblCdigo);
		pnlContenido.add(lblDescripcin);
		pnlContenido.add(this.txtDescripcion);
		pnlContenido.add(this.txtCodigo);

		JLabel lblTipoDeAnlisis = new JLabel("Tipo de An\u00E1lisis");
		lblTipoDeAnlisis.setBounds(287, 95, 99, 16);
		pnlContenido.add(lblTipoDeAnlisis);

		chckbxProducto = new JCheckBox("Producto");
		chckbxProducto.setBounds(289, 121, 97, 23);
		pnlContenido.add(chckbxProducto);

		chckbxConsumidor = new JCheckBox("Consumidor");
		chckbxConsumidor.setBounds(401, 121, 97, 23);
		pnlContenido.add(chckbxConsumidor);

		chckbxDocumento = new JCheckBox("Documento");
		chckbxDocumento.setBounds(287, 150, 97, 23);
		pnlContenido.add(chckbxDocumento);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setCuenta(getCuentas().get(selectedRow));
						else
							setCuenta(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setCuenta(new Cuenta());
		txtCodigo.requestFocus();
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		getCuentaDAO().crear_editar(getCuenta());
	}

	@Override
	public void llenarDesdeVista() {
		getCuenta().setIdcuenta(txtCodigo.getText());
		getCuenta().setDescripcion(txtDescripcion.getText());
		getCuenta().setA_cosumidor((chckbxConsumidor.isSelected()) ? 1 : 0);
		getCuenta().setA_producto((chckbxProducto.isSelected()) ? 1 : 0);
		getCuenta().setA_documento((chckbxDocumento.isSelected()) ? 1 : 0);
	};

	@Override
	public void eliminar() {
		if (getCuenta() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getCuentaDAO().remove(getCuenta());
				iniciar();
			}
		}
		setEstado(VISTA);
		vista_noedicion();
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public CuentaDAO getCuentaDAO() {
		return cuentaDAO;
	}

	public void setCuentaDAO(CuentaDAO cuentaDAO) {
		this.cuentaDAO = cuentaDAO;
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getCuenta() != null) {
			txtCodigo.setText(getCuenta().getIdcuenta());
			txtDescripcion.setText(getCuenta().getDescripcion());
			chckbxConsumidor.setSelected(getCuenta().getA_cosumidor() == 1);
			chckbxProducto.setSelected(getCuenta().getA_producto() == 1);
			chckbxDocumento.setSelected(getCuenta().getA_documento() == 1);
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Cuenta cuenta : getCuentas()) {
			model.addRow(new Object[] { cuenta.getIdcuenta(),
					cuenta.getDescripcion() });
		}
		if (getCuentas().size() > 0) {
			setCuenta(getCuentas().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setCuentas(getCuentaDAO().findAll());
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);

		tblLista.setEnabled(false);
		chckbxConsumidor.setEnabled(true);
		chckbxDocumento.setEnabled(true);
		chckbxProducto.setEnabled(true);
		TextFieldsEdicion(true, txtDescripcion);
	}

	@Override
	public void vista_noedicion() {
		TextFieldsEdicion(false, txtCodigo, txtDescripcion);
		chckbxConsumidor.setEnabled(false);
		chckbxDocumento.setEnabled(false);
		chckbxProducto.setEnabled(false);
		tblLista.setEnabled(true);
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		chckbxConsumidor.setSelected(false);
		chckbxProducto.setSelected(false);
		chckbxDocumento.setSelected(false);
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {

		if (!TextFieldObligatorios(txtCodigo, txtDescripcion))
			return false;

		if (getEstado().equals(NUEVO)) {
			if (getCuentaDAO().find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		return true;
	}
}
