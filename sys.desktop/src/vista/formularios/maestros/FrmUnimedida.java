package vista.formularios.maestros;

 import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import vista.controles.DSGTableModel;
import vista.controles.JTextFieldLimit;
import vista.controles.celleditor.TxtUnimedida;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.FloatEditor;
import vista.utilitarios.renderers.FloatRenderer;
import core.dao.ConversionUMDAO;
import core.dao.UnimedidaDAO;
import core.entity.ConversionUM;
import core.entity.Unimedida;

public class FrmUnimedida extends AbstractMaestro {
	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtNomenclatura;
	private Unimedida unimedida;
	private List<Unimedida> unimedidaL = new ArrayList<Unimedida>();
	private List<ConversionUM> conversiones = new ArrayList<ConversionUM>();
	private ConversionUMDAO convDAO = new ConversionUMDAO();
	String bkEntidad = null;
	private TxtUnimedida txtunimedida;
	private UnimedidaDAO unimedidaDAO = new UnimedidaDAO();

	public List<Unimedida> getUnimedidaL() {
		return unimedidaL;
	}

	public void setUnimedidaL(List<Unimedida> unimedidaL) {
		this.unimedidaL = unimedidaL;
	}

	private UnimedidaDAO udao = new UnimedidaDAO();

	public UnimedidaDAO getUdao() {
		return udao;
	}

	public void setUdao(UnimedidaDAO udao) {
		this.udao = udao;
	}

	private JTable tblLista;
	protected JScrollPane scrlConversion;
	protected JLabel lblConversion;
	private JTable tblConversion;
	private JLabel lblCodSunat;
	private JTextField txtCodSunat;

	public Unimedida getUnimedida() {
		return unimedida;
	}

	public void setUnimedida(Unimedida unimedida) {
		this.unimedida = unimedida;
	}

	public FrmUnimedida() {
		super("Unidad de Medida");

		JLabel lblCodigo = new JLabel("Codigo");

		JLabel lblDescripcion = new JLabel("Descripcion");

		JLabel lblNomenclatura = new JLabel("Nomenclatura");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		txtNomenclatura = new JTextField();
		txtNomenclatura.setColumns(10);
		txtNomenclatura.setDocument(new JTextFieldLimit(50, true));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(0.0f);
		scrollPane.setAlignmentX(0.0f);
		// TODO Auto-generated constructor stub

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);

		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.scrlConversion = new JScrollPane();

		this.lblConversion = new JLabel("Conversion");
		
		this.lblCodSunat = new JLabel("Cod. Sunat");
		
		this.txtCodSunat = new JTextField();
		this.txtCodSunat.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(this.lblConversion, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(this.lblCodSunat, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGap(9)
								.addComponent(this.txtCodSunat, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(this.scrlConversion, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNomenclatura, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
								.addGap(9)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(this.txtCodigo, 88, 88, 88)
										.addGap(100))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(this.txtDescripcion, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
										.addGap(5))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(this.txtNomenclatura, 85, 85, 85)
										.addGap(103)))
								.addGap(0)))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblCodigo)
									.addGap(11)
									.addComponent(lblDescripcion)
									.addGap(14)
									.addComponent(lblNomenclatura))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(this.txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(this.txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(this.txtNomenclatura, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(this.lblCodSunat)
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addComponent(this.lblConversion)
									.addGap(2))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(9)
									.addComponent(this.txtCodSunat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(this.scrlConversion, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);

		tblConversion = new JTable(new DSGTableModel(new String[] {
				"Cod Medida", "Medida", "Factor" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", new Float(0.0F) });
			}

		}) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					String idunimedida = getConversionTM().getValueAt(row, 0)
							.toString();

					txtunimedida.refresValue(idunimedida);
				}
			}

		};
		this.scrlConversion.setViewportView(tblConversion);

		txtunimedida = new TxtUnimedida(tblConversion, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Unimedida entity) {
				int row = tblConversion.getSelectedRow();
				if (entity == null) {
					getConversionTM().setValueAt("", row, 0);
					getConversionTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdunimedida());
					getConversionTM().setValueAt(entity.getIdunimedida(), row,
							0);
					getConversionTM().setValueAt(entity.getDescripcion(), row,
							1);
				}
				setSeleccionado(null);
			}
		};

		txtunimedida.setData(unimedidaDAO.findAll());
		txtunimedida.updateCellEditor();
		getConversionTM().setObligatorios(0, 1);
		getConversionTM().setRepetidos(0);
		getConversionTM().setScrollAndTable(scrlConversion, tblConversion);

		TableColumnModel tc = tblConversion.getColumnModel();
		tc.getColumn(2).setCellRenderer(new FloatRenderer(2));
		tc.getColumn(2).setCellEditor(new FloatEditor(2));
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setUnimedida(getUnimedidaL().get(selectedRow));
						else
							setUnimedida(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	public DSGTableModel getConversionTM() {
		return (DSGTableModel) tblConversion.getModel();
	}

	public void grabar() {
		unimedidaDAO.crear_editar(unimedida);
		convDAO.borrarPorUnimedida(unimedida);
		convDAO.create(conversiones);
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_datos() {
		getConversionTM().limpiar();
		if (getUnimedida() != null) {
			this.txtCodigo.setText(this.getUnimedida().getIdunimedida());
			this.txtDescripcion.setText(this.getUnimedida().getDescripcion());
			this.txtNomenclatura.setText(this.getUnimedida().getNomenclatura());
			this.txtCodSunat.setText(this.unimedida.getCod_sunat());
			setConversiones(convDAO.getPorUnimedida(getUnimedida()));
			for (ConversionUM c : getConversiones()) {
				Unimedida m_ref = c.getUnimedida_equiv();

				getConversionTM().addRow(
						new Object[] { m_ref.getIdunimedida(),
								m_ref.getDescripcion(), c.getFactor() });
			}
		} else {
			this.txtCodigo.setText(null);
			this.txtDescripcion.setText(null);
			this.txtNomenclatura.setText(null);
		}

	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Unimedida medida : getUnimedidaL()) {
			model.addRow(new Object[] { medida.getIdunimedida(),
					medida.getDescripcion() });
		}
		if (getUnimedidaL().size() > 0) {
			setUnimedida(getUnimedidaL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}

	}

	@Override
	public void llenar_tablas() {
		setUnimedidaL(udao.findAll());

	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		txtNomenclatura.setEditable(true);
		txtCodSunat.setEditable(true);
		getConversionTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtNomenclatura.setEditable(false);
		txtCodSunat.setEditable(false);
		getConversionTM().setEditar(false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void nuevo() {
		setUnimedida(new Unimedida());
		txtCodigo.requestFocus();
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		unimedida = (Unimedida) entidad;
		this.setUnimedida(unimedida);
		this.llenar_datos();
		this.vista_noedicion();
	}

	@Override
	public void llenarDesdeVista() {
		
		String iddmedida = this.txtCodigo.getText();
		unimedida.setIdunimedida(iddmedida);
		unimedida.setDescripcion(this.txtDescripcion.getText());
		unimedida.setNomenclatura(this.txtNomenclatura.getText());
		unimedida.setCod_sunat(this.txtCodSunat.getText());
		setConversiones(new ArrayList<ConversionUM>());
		int rows = getConversionTM().getRowCount();
		for (int i = 0; i < rows; i++) {
			String idmedida_equiv = getConversionTM().getValueAt(i, 0)
					.toString();
			Unimedida u_equiv = unimedidaDAO.find(idmedida_equiv);
			
			ConversionUM c = new ConversionUM();
			c.setUnimedida(unimedida);
			c.setUnimedida_equiv(u_equiv);
			c.setFactor(Float.parseFloat(getConversionTM().getValueAt(i, 2).toString()));
			getConversiones().add(c);
		}
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (getUdao().find(this.txtCodigo.getText().trim()) != null) {
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
		if (this.txtNomenclatura.getText().trim().isEmpty()) {
			this.txtNomenclatura.requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void eliminar() {
		if (getUnimedida() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getUdao().remove(getUnimedida());
				iniciar();
			}
		}

	}

	public List<ConversionUM> getConversiones() {
		return conversiones;
	}

	public void setConversiones(List<ConversionUM> conversiones) {
		this.conversiones = conversiones;
	}
}
