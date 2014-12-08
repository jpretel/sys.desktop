package vista.formularios.maestros;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.ObjetoWeb;
import vista.utilitarios.UtilMensajes;
import core.dao.ClieprovDAO;
import core.entity.Clieprov;

public class FrmClieprov extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private ClieprovDAO cdao = new ClieprovDAO();

	public ClieprovDAO getCdao() {
		return cdao;
	}

	public void setCdao(ClieprovDAO cdao) {
		this.cdao = cdao;
	}

	private Clieprov clieprov;

	public Clieprov getClieprov() {
		return clieprov;
	}

	public void setClieprov(Clieprov clieprov) {
		this.clieprov = clieprov;
	}

	private JTextField txtRazon_Social;
	private JTextField txtCodigo;
	private JTextField txtDireccion;
	private JTextField txtRuc;

	private JCheckBox chkCliente;
	private JCheckBox chkProveedor;

	String bkEntidad = null;
	private JCheckBox chkRetencion;
	private JCheckBox chkPercepcion;
	private JCheckBox chkBuenContribuyente;

	public FrmClieprov() {
		super("Edición de Clientes y Proveedores");
		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 10, 51, 14);

		txtCodigo = new JTextField();
		this.txtCodigo.setName("C\u00F3digo");
		this.txtCodigo.setBounds(80, 10, 98, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(11));

		JLabel lblRazon_Social = new JLabel("Razón Social");
		lblRazon_Social.setBounds(10, 41, 66, 14);

		txtRazon_Social = new JTextField();
		this.txtRazon_Social.setName("Raz\u00F3n Social");
		this.txtRazon_Social.setBounds(80, 38, 294, 20);
		txtRazon_Social.setColumns(10);

		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(10, 67, 50, 14);

		txtDireccion = new JTextField();
		this.txtDireccion.setName("Direcci\u00F3n");
		this.txtDireccion.setBounds(80, 64, 455, 20);
		txtDireccion.setColumns(10);

		JLabel lblRuc = new JLabel("RUC");
		lblRuc.setBounds(10, 94, 30, 14);

		txtRuc = new JTextField();
		this.txtRuc.setName("Ruc");
		this.txtRuc.setBounds(80, 91, 98, 20);
		txtRuc.setColumns(10);
		txtRuc.setDocument(new JTextFieldLimit(11));

		JButton button = new JButton("Consulta RUC");
		button.setBounds(0, 0, 0, 0);

		JButton btnConsultaRuc = new JButton("Consultar RUC");
		btnConsultaRuc.setBounds(196, 90, 108, 23);
		btnConsultaRuc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				consultar_ruc();
			}
		});

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(183, 14, 24, 14);

		chkCliente = new JCheckBox("Cliente");
		this.chkCliente.setBounds(213, 10, 59, 23);
		chkProveedor = new JCheckBox("Proveedor");
		this.chkProveedor.setBounds(272, 10, 75, 23);
		pnlContenido.setLayout(null);
		pnlContenido.add(button);
		pnlContenido.add(lblCodigo);
		pnlContenido.add(this.txtCodigo);
		pnlContenido.add(lblTipo);
		pnlContenido.add(this.chkCliente);
		pnlContenido.add(this.chkProveedor);
		pnlContenido.add(lblRazon_Social);
		pnlContenido.add(this.txtRazon_Social);
		pnlContenido.add(lblDireccion);
		pnlContenido.add(this.txtDireccion);
		pnlContenido.add(lblRuc);
		pnlContenido.add(this.txtRuc);
		pnlContenido.add(btnConsultaRuc);

		this.chkRetencion = new JCheckBox("Agente de Retenci\u00F3n");
		this.chkRetencion.setBounds(6, 128, 127, 23);
		pnlContenido.add(this.chkRetencion);

		this.chkPercepcion = new JCheckBox("Agente de Percepci\u00F3n");
		this.chkPercepcion.setBounds(151, 128, 143, 23);
		pnlContenido.add(this.chkPercepcion);

		this.chkBuenContribuyente = new JCheckBox("Buen Contribuyente");
		this.chkBuenContribuyente.setBounds(322, 128, 127, 23);
		pnlContenido.add(this.chkBuenContribuyente);
		iniciar();
	}

	@Override
	public void nuevo() {
		clieprov = new Clieprov();
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		try {
			if (getClieprov() instanceof Clieprov) {
				getCdao().crear_editar(getClieprov());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	@Override
	public void eliminar() {
		if (getClieprov() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getCdao().remove(getClieprov());
				// iniciar();
			}
		}
		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getClieprov() != null) {
			txtCodigo.setText(getClieprov().getIdclieprov());
			txtRazon_Social.setText(getClieprov().getRazonSocial());
			txtDireccion.setText(getClieprov().getDireccion());
			txtRuc.setText(getClieprov().getRuc());
			chkCliente.setSelected((getClieprov().getEs_cliente() == 1));
			chkProveedor.setSelected((getClieprov().getEs_proveedor() == 1));
			chkPercepcion
					.setSelected((getClieprov().getAgente_percepcion() == 1));
			chkRetencion
					.setSelected((getClieprov().getAgente_retencion() == 1));
			chkBuenContribuyente.setSelected((getClieprov()
					.getBuen_contribuyente() == 1));
		}
	}

	@Override
	public void llenar_lista() {
	}

	@Override
	public void llenar_tablas() {
		// setClieprovL(getCdao().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);

		FormValidador.TextFieldsEdicion(true, txtRazon_Social, txtDireccion,
				txtRuc);

		chkCliente.setEnabled(true);
		chkProveedor.setEnabled(true);
		chkBuenContribuyente.setEnabled(true);
		chkPercepcion.setEnabled(true);
		chkRetencion.setEnabled(true);
	}

	@Override
	public void vista_noedicion() {
		FormValidador.TextFieldsEdicion(false, txtCodigo, txtRazon_Social,
				txtDireccion, txtRuc);
		chkCliente.setEnabled(false);
		chkProveedor.setEnabled(false);
		chkBuenContribuyente.setEnabled(false);
		chkPercepcion.setEnabled(false);
		chkRetencion.setEnabled(false);
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText("");
		txtRazon_Social.setText("");
		txtDireccion.setText("");
		txtRuc.setText("");
		chkCliente.setSelected(false);
		chkProveedor.setSelected(false);
		chkPercepcion.setSelected(false);
		chkRetencion.setSelected(false);
		chkBuenContribuyente.setSelected(false);
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		setClieprov((Clieprov) entidad);
		llenar_datos();
		vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		if (getCdao().find(txtCodigo.getText()) != null) {
			bkEntidad = getCdao().find(txtCodigo.getText()).historial();
		}

		getClieprov().setIdclieprov(txtCodigo.getText());
		getClieprov().setRazonSocial(txtRazon_Social.getText());
		getClieprov().setDireccion(txtDireccion.getText());
		getClieprov().setRuc(txtRuc.getText());
		getClieprov().setEs_cliente(chkCliente.isSelected() ? 1 : 0);
		getClieprov().setEs_proveedor(chkProveedor.isSelected() ? 1 : 0);

		getClieprov().setAgente_percepcion(chkPercepcion.isSelected() ? 1 : 0);
		getClieprov().setAgente_retencion(chkRetencion.isSelected() ? 1 : 0);
		getClieprov().setBuen_contribuyente(
				chkBuenContribuyente.isSelected() ? 1 : 0);
	}

	@Override
	public boolean isValidaVista() {
		FormValidador.TextFieldObligatorios(txtCodigo, txtDireccion,
				txtRazon_Social);

		if (getEstado().equals(NUEVO)) {
			if (getCdao().find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		if (!chkCliente.isSelected() && !chkProveedor.isSelected()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Tipo");
			this.chkCliente.requestFocus();
			return false;
		}
		return true;
	}

	private void consultar_ruc() {
		clieprov = ObjetoWeb.ConsultaRUC(txtRuc.getText().toString());
		setClieprov(clieprov);
		txtCodigo.setText(getClieprov().getIdclieprov());
		txtRazon_Social.setText(getClieprov().getRazonSocial());
		txtDireccion.setText(getClieprov().getDireccion());
		txtRuc.setText(getClieprov().getRuc());
	}
}
