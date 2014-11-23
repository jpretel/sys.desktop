package vista.formularios.abstractforms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	private DSGTextFieldNumber txtTCambio;
	private DSGTextFieldNumber txtTCMoneda;
	private JLabel lblTcMoneda;
	private JLabel lblTipoCambio;
	private JXDatePicker txtFecha;
	private DSGTextFieldCorrelativo txtNumerador;
	private CntMoneda cntMoneda;
	private CntSubdiario cntSubdiario;
	private JScrollPane scrlDetalle;
	private DSGTextFieldNumber txtDebe;
	private DSGTextFieldNumber txtHaber;
	private JTextArea txtGlosa;
	private DSGTextFieldNumber txtHaberEx;
	protected DSGTextFieldNumber txtDebeEx;
	protected DSGTextFieldNumber txtHaberOf;
	protected DSGTextFieldNumber txtDebeOf;
	protected Object id;
	protected JLabel lblNumero;
	protected JLabel lblFecha;
	protected JLabel lblMoneda;
	protected JLabel lblSubDiario;
	protected JLabel lblGlosa;
	protected JLabel lblHaber;
	protected JLabel lblDebe;
	protected JLabel lblDebeOf;
	protected JLabel lblHaberOf;
	protected JLabel lblDebeEx;
	protected JLabel lblHaberEx;
	protected JScrollPane scrollPane;

	public AbstractAsientoForm(String titulo) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		setBounds(100, 100, 810, 451);
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
		pnlPrincipal.setLayout(getLayout());
		getContentPane().add(pnlPrincipal, BorderLayout.CENTER);
		lblNumero = new JLabel("Correlativo");
		this.lblNumero.setBounds(354, 6, 62, 20);

		lblFecha = new JLabel("Fecha");
		this.lblFecha.setBounds(7, 6, 34, 20);

		txtFecha = new DSGDatePicker();
		this.txtFecha.setBounds(9, 31, 108, 24);
		txtFecha.getEditor().setLocation(0, 8);

		txtTCambio = new DSGTextFieldNumber(3);
		this.txtTCambio.setBounds(660, 32, 56, 20);
		txtTCambio.setMinimumSize(new Dimension(30, 20));
		txtTCambio.setPreferredSize(new Dimension(30, 20));
		txtTCambio.setColumns(10);

		lblTipoCambio = new JLabel("T. Cambio");
		this.lblTipoCambio.setBounds(660, 6, 56, 20);

		lblTcMoneda = new JLabel("T.C. Moneda");
		this.lblTcMoneda.setBounds(724, 6, 69, 20);

		txtTCMoneda = new DSGTextFieldNumber(4);
		this.txtTCMoneda.setBounds(724, 32, 69, 20);
		txtTCMoneda.setColumns(10);

		txtNumerador = new DSGTextFieldCorrelativo(10);
		this.txtNumerador.setBounds(354, 32, 62, 20);
		txtNumerador.setColumns(10);

		lblMoneda = new JLabel("Moneda");
		this.lblMoneda.setBounds(420, 6, 45, 20);
		cntMoneda = new CntMoneda();
		this.cntMoneda.setBounds(420, 31, 236, 22);

		cntSubdiario = new CntSubdiario();
		this.cntSubdiario.setBounds(135, 31, 215, 22);

		lblSubDiario = new JLabel("Sub Diario");
		this.lblSubDiario.setBounds(135, 6, 58, 20);

		scrlDetalle = new JScrollPane();
		this.scrlDetalle.setBounds(7, 119, 786, 211);

		lblGlosa = new JLabel("Glosa");
		this.lblGlosa.setBounds(384, 64, 32, 16);

		scrollPane = new JScrollPane();
		this.scrollPane.setBounds(420, 64, 373, 47);

		txtGlosa = new JTextArea();
		scrollPane.setViewportView(txtGlosa);

		lblDebe = new JLabel("Debe");
		this.lblDebe.setBounds(13, 334, 116, 16);

		txtDebe = new DSGTextFieldNumber(2);
		this.txtDebe.setBounds(12, 356, 114, 20);
		txtDebe.setColumns(10);

		txtHaber = new DSGTextFieldNumber(2);
		this.txtHaber.setBounds(135, 356, 114, 20);
		txtHaber.setColumns(10);

		lblHaber = new JLabel("Haber");
		this.lblHaber.setBounds(135, 334, 34, 16);

		lblDebeOf = new JLabel("Debe Of.");
		this.lblDebeOf.setBounds(251, 334, 108, 16);

		txtDebeOf = new DSGTextFieldNumber(2);
		this.txtDebeOf.setBounds(253, 356, 114, 20);
		txtDebeOf.setColumns(10);

		lblHaberOf = new JLabel("Haber Of.");
		this.lblHaberOf.setBounds(371, 334, 53, 16);

		txtHaberOf = new DSGTextFieldNumber(2);
		this.txtHaberOf.setBounds(371, 356, 114, 20);
		txtHaberOf.setColumns(10);

		lblDebeEx = new JLabel("Debe Ex.");
		this.lblDebeEx.setBounds(501, 334, 77, 16);

		txtDebeEx = new DSGTextFieldNumber(2);
		this.txtDebeEx.setBounds(501, 356, 114, 20);
		txtDebeEx.setColumns(10);

		lblHaberEx = new JLabel("Haber Ex.");
		this.lblHaberEx.setBounds(619, 334, 97, 16);

		txtHaberEx = new DSGTextFieldNumber(2);
		this.txtHaberEx.setBounds(619, 356, 114, 20);
		txtHaberEx.setColumns(10);

		this.pnlPrincipal.setLayout(null);
		this.pnlPrincipal.setLayout(null);
		this.pnlPrincipal.setLayout(null);
		this.pnlPrincipal.add(this.lblFecha);
		this.pnlPrincipal.add(this.lblSubDiario);
		this.pnlPrincipal.add(this.lblNumero);
		this.pnlPrincipal.add(this.lblMoneda);
		this.pnlPrincipal.add(this.lblTipoCambio);
		this.pnlPrincipal.add(this.lblTcMoneda);
		this.pnlPrincipal.add(this.txtFecha);
		this.pnlPrincipal.add(this.cntSubdiario);
		this.pnlPrincipal.add(this.txtNumerador);
		this.pnlPrincipal.add(this.cntMoneda);
		this.pnlPrincipal.add(this.txtTCambio);
		this.pnlPrincipal.add(this.txtTCMoneda);
		this.pnlPrincipal.add(this.lblGlosa);
		this.pnlPrincipal.add(this.scrollPane);
		this.pnlPrincipal.add(this.scrlDetalle);
		this.pnlPrincipal.add(this.lblDebe);
		this.pnlPrincipal.add(this.lblHaber);
		this.pnlPrincipal.add(this.lblDebeOf);
		this.pnlPrincipal.add(this.lblHaberOf);
		this.pnlPrincipal.add(this.lblDebeEx);
		this.pnlPrincipal.add(this.lblHaberEx);
		this.pnlPrincipal.add(this.txtDebe);
		this.pnlPrincipal.add(this.txtHaber);
		this.pnlPrincipal.add(this.txtDebeOf);
		this.pnlPrincipal.add(this.txtHaberOf);
		this.pnlPrincipal.add(this.txtDebeEx);
		this.pnlPrincipal.add(this.txtHaberEx);
	}

	public void iniciar() {
		actualizar_tablas();
		llenar_datos();
		getBarra().enVista();
		vista_noedicion();
	}

	public abstract void nuevo();

	@Override
	public void editar() {
		if (isValidaEdicion()) {
			setEstado(EDICION);
			getBarra().enEdicion();
			vista_edicion();
		}
	}

	@Override
	public abstract void anular();

	public abstract void grabar();

	@Override
	public abstract void eliminar();

	public abstract void llenar_datos();

	public abstract void cargarDatos(Object id);

	public abstract void vista_edicion();

	public abstract void vista_noedicion();

	public abstract void actualizar_tablas();

	public abstract boolean isValidaEdicion();

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

	@Override
	public void cancelar() {
		cargarDatos(this.id);
		llenar_datos();
		setEstado(VISTA);
		vista_noedicion();
		getBarra().enVista();
	}

	@Override
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

	@Override
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

	@Override
	public void doVerAsiento() {
		System.out.println("opcion no disponible");
	}

	public void doSalir() {
		this.dispose();
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

	public JPanel getPnlPrincipal() {
		return pnlPrincipal;
	}

	public void setPnlPrincipal(JPanel pnlPrincipal) {
		this.pnlPrincipal = pnlPrincipal;
	}

	public DSGTextFieldNumber getTxtTCambio() {
		return txtTCambio;
	}

	public void setTxtTCambio(DSGTextFieldNumber txtTCambio) {
		this.txtTCambio = txtTCambio;
	}

	public DSGTextFieldNumber getTxtTCMoneda() {
		return txtTCMoneda;
	}

	public void setTxtTCMoneda(DSGTextFieldNumber txtTCMoneda) {
		this.txtTCMoneda = txtTCMoneda;
	}

	public JLabel getLblTcMoneda() {
		return lblTcMoneda;
	}

	public void setLblTcMoneda(JLabel lblTcMoneda) {
		this.lblTcMoneda = lblTcMoneda;
	}

	public JLabel getLblTipoCambio() {
		return lblTipoCambio;
	}

	public void setLblTipoCambio(JLabel lblTipoCambio) {
		this.lblTipoCambio = lblTipoCambio;
	}

	public JXDatePicker getTxtFecha() {
		return txtFecha;
	}

	public void setTxtFecha(JXDatePicker txtFecha) {
		this.txtFecha = txtFecha;
	}

	public DSGTextFieldCorrelativo getTxtNumerador() {
		return txtNumerador;
	}

	public void setTxtNumerador(DSGTextFieldCorrelativo txtNumerador) {
		this.txtNumerador = txtNumerador;
	}

	public CntMoneda getCntMoneda() {
		return cntMoneda;
	}

	public void setCntMoneda(CntMoneda cntMoneda) {
		this.cntMoneda = cntMoneda;
	}

	public CntSubdiario getCntSubdiario() {
		return cntSubdiario;
	}

	public void setCntSubdiario(CntSubdiario cntSubdiario) {
		this.cntSubdiario = cntSubdiario;
	}

	public JScrollPane getScrlDetalle() {
		return scrlDetalle;
	}

	public void setScrlDetalle(JScrollPane scrlDetalle) {
		this.scrlDetalle = scrlDetalle;
	}

	public DSGTextFieldNumber getTxtDebe() {
		return txtDebe;
	}

	public void setTxtDebe(DSGTextFieldNumber txtDebe) {
		this.txtDebe = txtDebe;
	}

	public DSGTextFieldNumber getTxtHaber() {
		return txtHaber;
	}

	public void setTxtHaber(DSGTextFieldNumber txtHaber) {
		this.txtHaber = txtHaber;
	}

	public JTextArea getTxtGlosa() {
		return txtGlosa;
	}

	public void setTxtGlosa(JTextArea txtGlosa) {
		this.txtGlosa = txtGlosa;
	}

	public DSGTextFieldNumber getTxtHaberEx() {
		return txtHaberEx;
	}

	public void setTxtHaberEx(DSGTextFieldNumber txtHaberEx) {
		this.txtHaberEx = txtHaberEx;
	}

	public DSGTextFieldNumber getTxtDebeEx() {
		return txtDebeEx;
	}

	public void setTxtDebeEx(DSGTextFieldNumber txtDebeEx) {
		this.txtDebeEx = txtDebeEx;
	}

	public DSGTextFieldNumber getTxtHaberOf() {
		return txtHaberOf;
	}

	public void setTxtHaberOf(DSGTextFieldNumber txtHaberOf) {
		this.txtHaberOf = txtHaberOf;
	}

	public DSGTextFieldNumber getTxtDebeOf() {
		return txtDebeOf;
	}

	public void setTxtDebeOf(DSGTextFieldNumber txtDebeOf) {
		this.txtDebeOf = txtDebeOf;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public JLabel getLblNumero() {
		return lblNumero;
	}

	public void setLblNumero(JLabel lblNumero) {
		this.lblNumero = lblNumero;
	}

	public JLabel getLblFecha() {
		return lblFecha;
	}

	public void setLblFecha(JLabel lblFecha) {
		this.lblFecha = lblFecha;
	}

	public JLabel getLblMoneda() {
		return lblMoneda;
	}

	public void setLblMoneda(JLabel lblMoneda) {
		this.lblMoneda = lblMoneda;
	}

	public JLabel getLblSubDiario() {
		return lblSubDiario;
	}

	public void setLblSubDiario(JLabel lblSubDiario) {
		this.lblSubDiario = lblSubDiario;
	}

	public JLabel getLblGlosa() {
		return lblGlosa;
	}

	public void setLblGlosa(JLabel lblGlosa) {
		this.lblGlosa = lblGlosa;
	}

	public JLabel getLblHaber() {
		return lblHaber;
	}

	public void setLblHaber(JLabel lblHaber) {
		this.lblHaber = lblHaber;
	}

	public JLabel getLblDebe() {
		return lblDebe;
	}

	public void setLblDebe(JLabel lblDebe) {
		this.lblDebe = lblDebe;
	}

	public JLabel getLblDebeOf() {
		return lblDebeOf;
	}

	public void setLblDebeOf(JLabel lblDebeOf) {
		this.lblDebeOf = lblDebeOf;
	}

	public JLabel getLblHaberOf() {
		return lblHaberOf;
	}

	public void setLblHaberOf(JLabel lblHaberOf) {
		this.lblHaberOf = lblHaberOf;
	}

	public JLabel getLblDebeEx() {
		return lblDebeEx;
	}

	public void setLblDebeEx(JLabel lblDebeEx) {
		this.lblDebeEx = lblDebeEx;
	}

	public JLabel getLblHaberEx() {
		return lblHaberEx;
	}

	public void setLblHaberEx(JLabel lblHaberEx) {
		this.lblHaberEx = lblHaberEx;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void doExportaExcel() {

	};

	public void doExportaPDF() {

	};

	public void doExportaOdt() {

	};

	public void doExportaOds() {

	};
	
	public void doExportaWord() {

	};
	
	public void doPrevio(){
		
	};
	
	public void doImprimir(){
		
	};
}
