package vista.formularios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXTable;

import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtSysFormulario;
import vista.utilitarios.MaestroTableModel;
import core.dao.GrupoUsuarioDAO;
import core.dao.GrupoUsuarioPrivilegioDAO;
import core.dao.SysFormularioDAO;
import core.entity.GrupoUsuario;
import core.entity.GrupoUsuarioPrivilegio;
import core.entity.GrupoUsuarioPrivilegioPK;
import core.entity.SysFormulario;

public class FrmGrupoUsuario extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private GrupoUsuario grupoUsuario;

	private GrupoUsuarioDAO grupoUsuarioDAO = new GrupoUsuarioDAO();
	private GrupoUsuarioPrivilegioDAO privilegioDAO = new GrupoUsuarioPrivilegioDAO();
	private SysFormularioDAO formularioDAO = new SysFormularioDAO();
	private List<GrupoUsuario> gruposUsuario = new ArrayList<GrupoUsuario>();
	private List<GrupoUsuarioPrivilegio> privilegios = new ArrayList<GrupoUsuarioPrivilegio>();

	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JCheckBox chkEsAdministrador;
	private JXTable tblOpciones;
	private TxtSysFormulario txtFormulario;

	JScrollPane scrollPane2 = new JScrollPane();
	private JLabel lblIngreseOpciones;

	public FrmGrupoUsuario() {
		super("Perfil de Grupos");

		getBarra().setFormMaestro(this);

		JLabel lblCdigo = new JLabel("C\u00F3digo");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");

		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);

		chkEsAdministrador = new JCheckBox("Es Administrador");
		chkEsAdministrador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chkEsAdministrador.isSelected()) {
					getDetalleTM().setEditar(false);
				}
			}
		});

		tblOpciones = new JXTable(new DSGTableModel(new String[] { "Código",
				"Descripción", "Ver", "Crear", "Modificar", "Eliminar" }) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", true, true, true, true });
			}

			@Override
			public Class<?> getColumnClass(int column) {
				// TODO Auto-generated method stub
				if (column >= 2) {
					return Boolean.class;
				}
				return super.getColumnClass(column);
			}

		});

		// tblOpciones.setTreeTableModel(mttm);
		tblOpciones.setFillsViewportHeight(true);
		scrollPane2.setViewportView(tblOpciones);
		tblOpciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		getDetalleTM().setNombre_detalle("Código");
		getDetalleTM().setObligatorios(0, 1);
		getDetalleTM().setRepetidos(0);
		getDetalleTM().setScrollAndTable(scrollPane2, tblOpciones);

		txtFormulario = new TxtSysFormulario(tblOpciones, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(SysFormulario entity) {
				int row = tblOpciones.getSelectedRow();
				if (entity == null) {
					getDetalleTM().setValueAt("", row, 0);
					getDetalleTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdformulario());
					getDetalleTM().setValueAt(entity.getIdformulario(), row, 0);
					getDetalleTM().setValueAt(entity.getDescripcion(), row, 1);
				}
				setSeleccionado(null);
			}
		};

		txtFormulario.updateCellEditor();

		txtFormulario.setData(new SysFormularioDAO().findAll());

		lblIngreseOpciones = new JLabel("Ingrese Opciones");
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(scrollPane,
												GroupLayout.PREFERRED_SIZE,
												251, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(6)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(4)
																										.addComponent(
																												lblCdigo)
																										.addGap(26)
																										.addComponent(
																												this.txtCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(4)
																										.addComponent(
																												lblDescripcin)
																										.addGap(5)
																										.addComponent(
																												this.txtDescripcion,
																												GroupLayout.DEFAULT_SIZE,
																												353,
																												Short.MAX_VALUE)
																										.addContainerGap())
																						.addComponent(
																								this.chkEsAdministrador)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(4)
																										.addComponent(
																												this.lblIngreseOpciones,
																												GroupLayout.PREFERRED_SIZE,
																												101,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				scrollPane2,
																				GroupLayout.DEFAULT_SIZE,
																				412,
																				Short.MAX_VALUE)
																		.addContainerGap()))));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(26)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblCdigo)
																						.addComponent(
																								this.txtCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(10)
																										.addComponent(
																												lblDescripcin))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(6)
																										.addComponent(
																												this.txtDescripcion,
																												GroupLayout.PREFERRED_SIZE,
																												22,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(7)
																		.addComponent(
																				this.chkEsAdministrador)
																		.addGap(7)
																		.addComponent(
																				this.lblIngreseOpciones)
																		.addGap(3)
																		.addComponent(
																				scrollPane2,
																				GroupLayout.DEFAULT_SIZE,
																				141,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				298,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setGrupoUsuario(getGruposUsuario().get(selectedRow));
						else
							setGrupoUsuario(null);
						llenar_datos();
					}
				});
		iniciar();
		// getDetalleTM().setEditar(true);

	}

	@Override
	public void nuevo() {
		setGrupoUsuario(new GrupoUsuario());
		txtCodigo.requestFocus();
	}

	@Override
	public void grabar() {
		getGrupoUsuarioDAO().crear_editar(getGrupoUsuario());
		
		for(GrupoUsuarioPrivilegio p : getPrivilegios()) {
			privilegioDAO.crear_editar(p);
		}
	}

	@Override
	public void llenarDesdeVista() {
		String idgrupo = txtCodigo.getText();
		getGrupoUsuario().setIdgrupoUsuario(idgrupo);
		getGrupoUsuario().setDescripcion(txtDescripcion.getText());
		getGrupoUsuario().setEsAdministrador(
				chkEsAdministrador.isSelected() ? 1 : 0);
		int rows = tblOpciones.getRowCount();
		privilegios = new ArrayList<GrupoUsuarioPrivilegio>();
		for (int i = 0; i < rows; i++) {
			GrupoUsuarioPrivilegio p = new GrupoUsuarioPrivilegio();
			GrupoUsuarioPrivilegioPK id = new GrupoUsuarioPrivilegioPK();

			id.setIdgrupo_usuario(idgrupo);
			id.setIdformulario(getDetalleTM().getValueAt(i, 0).toString());

			p.setId(id);

			p.setVer(((boolean) getDetalleTM().getValueAt(i, 2)) ? 1 : 0);
			p.setCrear(((boolean) getDetalleTM().getValueAt(i, 3)) ? 1 : 0);
			p.setModificar(((boolean) getDetalleTM().getValueAt(i, 4)) ? 1 : 0);
			p.setEliminar(((boolean) getDetalleTM().getValueAt(i, 5)) ? 1 : 0);
			privilegios.add(p);
		}
	};

	@Override
	public void eliminar() {
		if (getGrupoUsuario() != null) {
			getGrupoUsuarioDAO().remove(getGrupoUsuario());
		}
	}

	@Override
	public void llenar_datos() {
		getDetalleTM().limpiar();
		if (getGrupoUsuario() != null) {
			txtCodigo.setText(getGrupoUsuario().getIdgrupoUsuario());
			txtDescripcion.setText(getGrupoUsuario().getDescripcion());
			chkEsAdministrador.setSelected(getGrupoUsuario()
					.getEsAdministrador() == 1);
			setPrivilegios(privilegioDAO.getPorGrupoUsuario(getGrupoUsuario()));
			for (GrupoUsuarioPrivilegio obj : getPrivilegios()) {
				boolean ver, crear, modificar, eliminar;
				ver = (obj.getVer() == 1);
				crear = (obj.getCrear() == 1);
				modificar = (obj.getModificar() == 1);
				eliminar = (obj.getEliminar() == 1);
				SysFormulario formulario = formularioDAO.find(obj.getId().getIdformulario());
				getDetalleTM().addRow(
						new Object[] {
								formulario.getIdformulario(),
								formulario.getDescripcion(), ver,
								crear, modificar, eliminar });
			}
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			chkEsAdministrador.setSelected(true);
		}
	}

	@Override
	public void llenar_lista() {

		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (GrupoUsuario obj : getGruposUsuario()) {
			model.addRow(new Object[] { obj.getIdgrupoUsuario(),
					obj.getDescripcion() });
		}
		if (getGruposUsuario().size() > 0) {
			setGrupoUsuario(getGruposUsuario().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setGruposUsuario(getGrupoUsuarioDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		chkEsAdministrador.setEnabled(true);
		tblLista.setEnabled(false);
		getDetalleTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		chkEsAdministrador.setEnabled(false);
		tblLista.setEnabled(true);
		getDetalleTM().setEditar(false);
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void limpiarDetalle() {
		getDetalleTM().limpiar();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {
		if (txtCodigo.getText().trim().isEmpty())
			return false;
		if (txtDescripcion.getText().trim().isEmpty())
			return false;
		return true;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public GrupoUsuarioDAO getGrupoUsuarioDAO() {
		return grupoUsuarioDAO;
	}

	public void setGrupoUsuarioDAO(GrupoUsuarioDAO grupoUsuarioDAO) {
		this.grupoUsuarioDAO = grupoUsuarioDAO;
	}

	public List<GrupoUsuario> getGruposUsuario() {
		return gruposUsuario;
	}

	public void setGruposUsuario(List<GrupoUsuario> gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}

	public DSGTableModel getDetalleTM() {
		return ((DSGTableModel) tblOpciones.getModel());
	}

	public List<GrupoUsuarioPrivilegio> getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(List<GrupoUsuarioPrivilegio> privilegios) {
		this.privilegios = privilegios;
	}

}
