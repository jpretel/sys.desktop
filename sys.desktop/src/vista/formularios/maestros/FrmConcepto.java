package vista.formularios.maestros;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.combobox.ComboBox;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.ConceptoDAO;
import core.entity.Concepto;

public class FrmConcepto extends AbstractMaestro {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtNombreCorto;
	private JTable tblLista;
	private JCheckBox chkTransferencia;
	private JCheckBox chkCentraliza;
	private JCheckBox chkSolicitaCompra;
	private JCheckBox chckSolicitaRecepcion;
	private int EsTransferencia, centraliza, solicita_compra,
			solicita_recepcion;
	private Concepto concepto;

	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	private ConceptoDAO mdao = new ConceptoDAO();
	private List<Concepto> motivoL = mdao.findAll();

	public List<Concepto> getMotivoL() {
		return motivoL;
	}

	public void setConceptoL(List<Concepto> motivoL) {
		this.motivoL = motivoL;
	}

	private ComboBox cboTipo;
	private List<String[]> optionList = new ArrayList<String[]>();
	private JTabbedPane tabbedPane;

	public FrmConcepto() {
		super("Conceptos");

		JLabel lblCodigo = new JLabel("Codigo");

		JLabel lblDescripcion = new JLabel("Descripcion");

		JLabel lblNomenclatura = new JLabel("Nombre Corto");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(200, true));

		txtNombreCorto = new JTextField();
		txtNombreCorto.setColumns(10);
		txtNombreCorto.setDocument(new JTextFieldLimit(100, true));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblTipo = new JLabel("Tipo");

		optionList.add(new String[] { "I", "Ingreso" });
		optionList.add(new String[] { "S", "Salida" });

		cboTipo = new vista.combobox.ComboBox(optionList, 1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));

		JPanel panel = new JPanel();
		tabbedPane.addTab("Configuraciones", null, panel, null);

		chkCentraliza = new JCheckBox("Centraliza\r\n");
		panel.add(chkCentraliza);

		chkTransferencia = new JCheckBox("Transferencia");
		chkTransferencia.setVerticalAlignment(SwingConstants.TOP);
		panel.add(chkTransferencia);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Documentos Requeridos", null, panel_1, null);

		chkSolicitaCompra = new JCheckBox("Solicita Orden Compra\r\n");
		panel_1.add(chkSolicitaCompra);

		chckSolicitaRecepcion = new JCheckBox("Solicita Recepcion\r\n\r\n");
		panel_1.add(chckSolicitaRecepcion);
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(10)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 230,
												Short.MAX_VALUE)
										.addGap(10)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblCodigo)
																		.addGap(52)
																		.addComponent(
																				txtCodigo,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblDescripcion)
																		.addGap(31)
																		.addComponent(
																				txtDescripcion,
																				GroupLayout.DEFAULT_SIZE,
																				208,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblNomenclatura)
																		.addGap(18)
																		.addComponent(
																				txtNombreCorto,
																				GroupLayout.DEFAULT_SIZE,
																				123,
																				Short.MAX_VALUE)
																		.addGap(85))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblTipo,
																				GroupLayout.PREFERRED_SIZE,
																				33,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(52)
																		.addComponent(
																				cboTipo,
																				GroupLayout.PREFERRED_SIZE,
																				123,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																tabbedPane,
																GroupLayout.DEFAULT_SIZE,
																303,
																Short.MAX_VALUE))
										.addGap(11)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(11)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																260,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								46,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(13)
																										.addComponent(
																												txtCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(6)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(6)
																										.addComponent(
																												lblDescripcion))
																						.addComponent(
																								txtDescripcion,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(18)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(3)
																										.addComponent(
																												lblNomenclatura))
																						.addComponent(
																								txtNombreCorto,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(18)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(3)
																										.addComponent(
																												lblTipo))
																						.addComponent(
																								cboTipo,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(11)
																		.addComponent(
																				tabbedPane,
																				GroupLayout.DEFAULT_SIZE,
																				101,
																				Short.MAX_VALUE)))
										.addGap(9)));
		pnlContenido.setLayout(groupLayout);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setConcepto(getMotivoL().get(selectedRow));
						else
							setConcepto(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setConcepto(new Concepto());
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		mdao.crear_editar(getConcepto());
	}

	@Override
	public void eliminar() {
		if (getConcepto() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getMdao().remove(getConcepto());
				iniciar();
			}
		}
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		boolean band;
		if (getConcepto() != null) {
			txtCodigo.setText(getConcepto().getIdconcepto());
			txtDescripcion.setText(getConcepto().getDescripcion());
			txtNombreCorto.setText(getConcepto().getNombreCorto());
			if (!getEstado().equals(NUEVO)) {
				int indice = (getConcepto().getTipo().equals("I")) ? 0 : 1;
				cboTipo.setSelectedIndex(indice);
			}
			band = (getConcepto().getEsTransferencia() == 1) ? true : false;
			chkTransferencia.setSelected(band);
			band = (getConcepto().getCentraliza() == 1) ? true : false;
			chkCentraliza.setSelected(band);
			band = (getConcepto().getSolcitaCompra() == 1) ? true : false;
			chkSolicitaCompra.setSelected(band);
			band = (getConcepto().getSolicitaRecepcion() == 1) ? true : false;
			chckSolicitaRecepcion.setSelected(band);
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel modelo = (MaestroTableModel) tblLista.getModel();
		modelo.limpiar();
		for (Concepto motivo : getMotivoL()) {
			modelo.addRow(new Object[] { motivo.getIdconcepto(),
					motivo.getDescripcion() });
		}
		if (getMotivoL().size() > 0) {
			setConcepto(getMotivoL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}

	}

	@Override
	public void llenar_tablas() {
		setConceptoL(getMdao().findAll());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		txtNombreCorto.setEditable(true);
		cboTipo.enable(true);
		chkTransferencia.enable(true);
		chkCentraliza.enable(true);
		chkSolicitaCompra.enable(true);
		chckSolicitaRecepcion.enable(true);
		tblLista.setEnabled(false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtNombreCorto.setEditable(false);
		cboTipo.enable(false);
		chkTransferencia.enable(false);
		chkCentraliza.enable(false);
		chckSolicitaRecepcion.enable(false);
		tblLista.setEnabled(true);
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText(null);
		txtDescripcion.setText(null);
		txtNombreCorto.setText(null);
		chckSolicitaRecepcion.setSelected(false);
		chkSolicitaCompra.setSelected(false);
		chkTransferencia.setSelected(false);
		chkCentraliza.setSelected(false);
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		this.setConcepto(concepto);
		this.llenar_datos();
		this.vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		getConcepto().setIdconcepto(txtCodigo.getText());
		getConcepto().setDescripcion(txtDescripcion.getText());
		getConcepto().setNombreCorto(txtNombreCorto.getText());
		getConcepto().setTipo(((String[]) cboTipo.getSelectedItem())[0]);
		EsTransferencia = (chkTransferencia.isSelected()) ? 1 : 0;
		centraliza = (chkCentraliza.isSelected()) ? 1 : 0;
		solicita_compra = (chkSolicitaCompra.isSelected()) ? 1 : 0;
		solicita_recepcion = (chckSolicitaRecepcion.isSelected()) ? 1 : 0;
		getConcepto().setEsTransferencia(EsTransferencia);
		getConcepto().setCentraliza(centraliza);
		getConcepto().setSolcitaCompra(solicita_compra);
		getConcepto().setSolicitaRecepcion(solicita_recepcion);
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}

		if (getEstado().equals(NUEVO)) {
			if (getMdao().find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		if (this.txtDescripcion.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Descripción");
			this.txtDescripcion.requestFocus();
			return false;
		}

		if (this.txtNombreCorto.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Nombre corto");
			this.txtNombreCorto.requestFocus();
			return false;
		}

		if (cboTipo.getSelectedIndex() == -1) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Tipo");
			this.cboTipo.requestFocus();
			return false;
		}
		return true;
	}

	public ConceptoDAO getMdao() {
		return mdao;
	}

	public void setMdao(ConceptoDAO mdao) {
		this.mdao = mdao;
	}
}
