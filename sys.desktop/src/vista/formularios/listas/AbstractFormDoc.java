package vista.formularios.listas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import vista.barras.IFormDocumento;
import vista.barras.PanelBarraDocumento;
import vista.contenedores.CntMoneda;
import vista.controles.DSGDatePicker;
import vista.controles.DSGTextFieldCorrelativo;

public abstract class AbstractFormDoc extends JInternalFrame implements IFormDocumento{
	private static final long serialVersionUID = 1L;
	protected JTextField txtNumero;
	private PanelBarraDocumento barra;
	protected JPanel pnlPrincipal;
	protected static final String EDICION = "EDICION";
	protected static final String VISTA = "VISTA";
	protected static final String NUEVO = "NUEVO";
	private String estado;	
	protected DSGTextFieldCorrelativo txtNumero_2;
	protected JTextField txtTipoCambio;
	protected JTextField txtTcMoneda;
	protected JLabel lblTcMoneda;
	protected JLabel lblTipoCambio;
	protected DSGDatePicker txtFecha;
	protected JTextField txtSerie;
	protected CntMoneda cntMoneda;
	public AbstractFormDoc(String titulo) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		int AnchoCabecera = 850;
		barra = new PanelBarraDocumento();
		barra.setMinimumSize(new Dimension(AnchoCabecera, 30));
		barra.setPreferredSize(new Dimension(AnchoCabecera, 30));
		barra.setBounds(0, 0, AnchoCabecera, 42);
		barra.setFormMaestro(this);
		FlowLayout flowLayout = (FlowLayout) barra.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(barra, BorderLayout.NORTH);
		
		pnlPrincipal = new JPanel();
		pnlPrincipal.setMinimumSize(new Dimension(AnchoCabecera, 500));
		pnlPrincipal.setPreferredSize(new Dimension(AnchoCabecera, 500));
		pnlPrincipal.setBounds(0, 40, AnchoCabecera, 70);
		
		getContentPane().add(pnlPrincipal);
		JLabel lblNumero = new JLabel("Correlativo");
		
		txtNumero_2 = new DSGTextFieldCorrelativo(8);
		txtNumero_2.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha");
		
		txtFecha = new DSGDatePicker();
		txtFecha.getEditor().setLocation(0, 8);
		txtFecha.setDate(new Date());
		
		txtTipoCambio = new JTextField();
		txtTipoCambio.setMinimumSize(new Dimension(30, 20));
		txtTipoCambio.setPreferredSize(new Dimension(30, 20));
		txtTipoCambio.setColumns(10);
		
		lblTipoCambio = new JLabel("Tipo de Cambio");
		
		lblTcMoneda = new JLabel("T.C. Moneda");
		
		txtTcMoneda = new JTextField();
		txtTcMoneda.setColumns(10);
		
		txtSerie = new JTextField();
		txtSerie.setColumns(10);
		JLabel lblMoneda = new JLabel("Moneda");
		
		setBounds(100, 100, 854, 465);
		this.cntMoneda.txtDescripcion.setColumns(12);
		
		this.cntMoneda = new CntMoneda();
		GroupLayout gl_pnlPrincipal = new GroupLayout(pnlPrincipal);
		gl_pnlPrincipal.setHorizontalGroup(
			gl_pnlPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNumero)
					.addGap(9)
					.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addGap(43)
							.addComponent(this.txtNumero_2, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addComponent(this.txtSerie, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(79)))
					.addGap(10)
					.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addGap(39)
							.addComponent(this.txtFecha, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
						.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(lblMoneda, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.cntMoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(this.lblTcMoneda)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.txtTcMoneda, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(this.lblTipoCambio)
					.addGap(9)
					.addComponent(this.txtTipoCambio, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlPrincipal.setVerticalGroup(
			gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPrincipal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
								.addComponent(this.txtNumero_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.BASELINE)
									.addComponent(this.txtSerie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNumero))))
						.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
							.addComponent(this.txtFecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_pnlPrincipal.createSequentialGroup()
								.addGap(5)
								.addComponent(lblFecha)))
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addGap(5)
							.addComponent(lblMoneda))
						.addGroup(gl_pnlPrincipal.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.LEADING)
								.addComponent(this.cntMoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlPrincipal.createParallelGroup(Alignment.BASELINE)
									.addComponent(this.txtTcMoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(this.lblTcMoneda)
									.addComponent(this.lblTipoCambio)
									.addComponent(this.txtTipoCambio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(346, Short.MAX_VALUE))
		);
		pnlPrincipal.setLayout(gl_pnlPrincipal);
	}
	
	public void iniciar() {
		llenar_tablas();
		llenar_lista();
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
	public abstract void llenar_lista();
	public abstract void llenar_tablas();
	public abstract void vista_edicion();
	public abstract void vista_noedicion();
	public abstract void init();
	public abstract void actualiza_objeto(Object entidad);
	
	public void cancelar () {
		llenar_tablas();
		llenar_lista();
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
			llenar_tablas();
			llenar_lista();
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
		llenar_tablas();
		llenar_lista();
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
