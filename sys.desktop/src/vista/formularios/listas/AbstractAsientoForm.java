package vista.formularios.listas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import vista.barras.IFormDocumento;
import vista.barras.PanelBarraDocumento;
import vista.contenedores.CntMoneda;
import vista.contenedores.CntSubdiario;
import vista.controles.DSGDatePicker;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTextFieldCorrelativo;
import vista.controles.DSGTextFieldNumber;

public abstract class AbstractAsientoForm extends DSGInternalFrame implements
		IFormDocumento {
	private static final long serialVersionUID = 1L;
	
	private PanelBarraDocumento barra;
	protected JPanel pnlPrincipal;
	
	private String estado;
	protected DSGTextFieldNumber txtTCambio;
	protected DSGTextFieldNumber txtTCMoneda;
	protected JLabel lblTcMoneda;
	protected JLabel lblTipoCambio;
	protected JXDatePicker txtFecha;
	protected DSGTextFieldCorrelativo txtNumerador;
	protected CntMoneda cntMoneda;
	protected CntSubdiario cntSubdiario;
	private JTextField txtDebe;
	private JTextField txtHaber;

	public AbstractAsientoForm(String titulo) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		barra = new PanelBarraDocumento();
		barra.setMinimumSize(new Dimension(770, 50));
		barra.setPreferredSize(new Dimension(770, 37));
		barra.setBounds(0, 0, 770, 42);
		barra.setFormMaestro(this);
		FlowLayout flowLayout = (FlowLayout) barra.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(barra, BorderLayout.NORTH);

		pnlPrincipal = new JPanel();
		pnlPrincipal.setMinimumSize(new Dimension(770, 500));
		pnlPrincipal.setPreferredSize(new Dimension(770, 500));
		pnlPrincipal.setBounds(0, 40, 770, 42);
		// pnlPrincipal.setLayout(getLayout());
		getContentPane().add(pnlPrincipal);
		JLabel lblNumero = new JLabel("Correlativo");
		lblNumero.setBounds(321, 11, 73, 14);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(10, 11, 47, 14);

		txtFecha = new DSGDatePicker();
		txtFecha.setBounds(10, 24, 123, 22);
		txtFecha.getEditor().setLocation(0, 8);

		txtTCambio = new DSGTextFieldNumber(3);
		txtTCambio.setBounds(632, 25, 82, 20);
		txtTCambio.setMinimumSize(new Dimension(30, 20));
		txtTCambio.setPreferredSize(new Dimension(30, 20));
		txtTCambio.setColumns(10);

		lblTipoCambio = new JLabel("T. Cambio");
		lblTipoCambio.setBounds(632, 11, 73, 14);

		lblTcMoneda = new JLabel("T.C. Moneda");
		lblTcMoneda.setBounds(717, 11, 71, 14);

		txtTCMoneda = new DSGTextFieldNumber(4);
		txtTCMoneda.setBounds(717, 25, 71, 20);
		txtTCMoneda.setColumns(10);

		txtNumerador = new DSGTextFieldCorrelativo(10);
		txtNumerador.setBounds(321, 27, 106, 20);
		txtNumerador.setColumns(10);

		JLabel lblMoneda = new JLabel("Moneda");
		lblMoneda.setBounds(433, 11, 62, 14);

		pnlPrincipal.setLayout(null);
		pnlPrincipal.add(lblNumero);
		pnlPrincipal.add(lblFecha);
		pnlPrincipal.add(txtFecha);
		pnlPrincipal.add(txtTCambio);
		pnlPrincipal.add(lblTipoCambio);
		pnlPrincipal.add(lblTcMoneda);
		pnlPrincipal.add(txtTCMoneda);
		pnlPrincipal.add(txtNumerador);
		pnlPrincipal.add(lblMoneda);
		cntMoneda = new CntMoneda();
		cntMoneda.setBounds(433, 26, 187, 20);
		pnlPrincipal.add(cntMoneda);

		cntSubdiario = new CntSubdiario();
		cntSubdiario.setBounds(142, 26, 174, 20);
		pnlPrincipal.add(cntSubdiario);

		JLabel lblSubDiario = new JLabel("Sub Diario");
		lblSubDiario.setBounds(142, 11, 53, 14);
		pnlPrincipal.add(lblSubDiario);

		JScrollPane scrlDetalle = new JScrollPane();
		scrlDetalle.setBounds(10, 118, 778, 203);
		pnlPrincipal.add(scrlDetalle);

		JLabel lblGlosa = new JLabel("Glosa");
		lblGlosa.setBounds(389, 59, 42, 14);
		pnlPrincipal.add(lblGlosa);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(433, 58, 355, 47);
		pnlPrincipal.add(scrollPane);

		JTextArea txtGlosa = new JTextArea();
		scrollPane.setViewportView(txtGlosa);

		JLabel lblDebe = new JLabel("Debe");
		lblDebe.setBounds(485, 339, 62, 14);
		pnlPrincipal.add(lblDebe);

		txtDebe = new JTextField();
		txtDebe.setColumns(10);
		txtDebe.setBounds(519, 336, 99, 20);
		pnlPrincipal.add(txtDebe);

		txtHaber = new JTextField();
		txtHaber.setColumns(10);
		txtHaber.setBounds(689, 336, 99, 20);
		pnlPrincipal.add(txtHaber);

		JLabel lblHaber = new JLabel("Haber");
		lblHaber.setBounds(645, 339, 38, 14);
		pnlPrincipal.add(lblHaber);
		setBounds(100, 100, 810, 438);
	}

	public void iniciar() {
		actualizar_tablas();
		llenar_datos();
		getBarra().enVista();
		vista_noedicion();
	}

	public abstract void nuevo();

	public void editar() {
		setEstado(EDICION);
		getBarra().enEdicion();
		vista_edicion();
	}

	public abstract void anular();

	public abstract void grabar();

	public abstract void eliminar();

	public abstract void llenar_datos();

	public abstract void cargarDatos(Object id);

	public abstract void vista_edicion();

	public abstract void vista_noedicion();

	public abstract void actualizar_tablas();
	
	//public abstract boolean validaEdicion();

	public void actualiza_objeto(Object id, String estado) {
		actualizar_tablas();
		cargarDatos(id);
		llenar_datos();
		
		getBarra().enVista();
		vista_noedicion();
		
		if (estado.equals(EDICION)) {
			editar();
		}
	}

	public void cancelar() {
		llenar_datos();
		setEstado(VISTA);
		vista_noedicion();
		getBarra().enVista();
	}

	public void DoGrabar() {
		boolean esVistaValido;
		esVistaValido = isValidaVista();
		if (esVistaValido) {
			llenarDesdeVista();
			grabar();
			setEstado(VISTA);
			getBarra().enVista();
			vista_noedicion();
			llenar_datos();
		}
	}

	public void DoNuevo() {
		nuevo();
		setEstado(NUEVO);
		getBarra().enEdicion();
		llenar_datos();
		vista_edicion();
	}

	public void DoEliminar() {
		eliminar();
		setEstado(VISTA);
		getBarra().enVista();
		vista_noedicion();
		llenar_datos();
	}

	public abstract void llenarDesdeVista();

	public abstract boolean isValidaVista();

	public PanelBarraDocumento getBarra() {
		return barra;
	}

	public void setBarra(PanelBarraDocumento barra) {
		this.barra = barra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
