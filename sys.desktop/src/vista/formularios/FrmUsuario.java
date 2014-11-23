package vista.formularios;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.contenedores.CntGrupoUsuario;
import vista.utilitarios.MaestroTableModel;
import core.dao.GrupoUsuarioDAO;
import core.dao.UsuarioDAO;
import core.entity.Usuario;
import core.security.Encryption;

public class FrmUsuario extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	private List<Usuario> usuarios = new ArrayList<Usuario>();

	private JTextField txtidUsuario = new JTextField();
	private JTextField txtNombres = new JTextField();
	private JPasswordField txtClave = new JPasswordField();

	JLabel lblId = new JLabel("Cod. Usuario:");
	JLabel lblUsuario = new JLabel("Nombre:");
	JLabel lblCLave = new JLabel("Clave:");

	private JTable tblLista = new JTable(new MaestroTableModel());
	JScrollPane scrollPane = new JScrollPane();
	private JPasswordField txtClaveR;
	protected CntGrupoUsuario cntGrupoUsuario;

	public FrmUsuario() {
		super("Usuarios");
		getBarra().setBounds(0, 0, 0, 0);

		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblGrupoUsuario = new JLabel("Grupo Usuario");

		txtClaveR = new JPasswordField();

		JLabel lblRepitaLaClave = new JLabel("Repita Clave:");

		this.cntGrupoUsuario = new CntGrupoUsuario();
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCLave, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRepitaLaClave, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGrupoUsuario, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(this.txtClaveR, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
								.addComponent(txtClave, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
							.addGap(109))
						.addComponent(txtNombres, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
						.addComponent(txtidUsuario, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.cntGrupoUsuario, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblId)
								.addComponent(txtidUsuario, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUsuario)
								.addComponent(txtNombres, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCLave)
								.addComponent(txtClave, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRepitaLaClave)
								.addComponent(this.txtClaveR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblGrupoUsuario)
								.addComponent(this.cntGrupoUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
					.addContainerGap())
		);
		pnlContenido.setLayout(groupLayout);
		// pnlContenido.setFocusTraversalPolicy(new FocusTraversalOnArray(
		// new Component[] { scrollPane, tblLista, lblId, txtidUsuario,
		// lblUsuario, txtClave, lblCLave, txtNombres, lblGrupoUsuario,
		// txtidgrupo }));

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setusuario(getusuarios().get(selectedRow));
						else
							setusuario(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setusuario(new Usuario());
		getusuario().setClave("");
	}

	@Override
	public void anular() {

	}

	public void grabar() {
		getusuarioDAO().crear_editar(getusuario());
	}

	@Override
	public void llenar_datos() {
		if (getusuario() != null) {
			txtidUsuario.setText(getusuario().getIdusuario());
			txtNombres.setText(getusuario().getNombres());
			cntGrupoUsuario.txtCodigo
					.setText((getusuario().getGrupoUsuario() == null) ? ""
							: getusuario().getGrupoUsuario().getIdgrupoUsuario());
			cntGrupoUsuario.llenar();
			txtClave.setText(Encryption.decrypt(getusuario().getClave()));
			txtClaveR.setText(Encryption.decrypt(getusuario().getClave()));

		} else {
			txtidUsuario.setText("");
			txtNombres.setText("");
			txtClave.setText("");
			cntGrupoUsuario.txtCodigo.setText("");
			cntGrupoUsuario.txtDescripcion.setText("");
		}
	}

	@Override
	public void llenar_lista() {

		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Usuario obj : getusuarios()) {
			model.addRow(new Object[] { obj.getIdusuario(), obj.getNombres() });
		}
		if (getusuarios().size() > 0) {
			setusuario(getusuarios().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setusuarios(getusuarioDAO().findAll());
		cntGrupoUsuario.setData(new GrupoUsuarioDAO().findAll());
	}

	public List<Usuario> getusuarios() {
		return usuarios;
	}

	public void setusuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtidUsuario.setEditable(true);
		TextFieldsEdicion(true, txtNombres, txtClave, txtClaveR,
				cntGrupoUsuario.txtCodigo);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		TextFieldsEdicion(false, txtidUsuario, txtNombres, txtClave, txtClaveR,
				cntGrupoUsuario.txtCodigo);
		tblLista.setEnabled(true);
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
	public void llenarDesdeVista() {
		// GrupoUsuario u = new GrupoUsuario();
		// u.setIdgrupoUsuario(txtidgrupo.getText());

		getusuario().setIdusuario(txtidUsuario.getText());
		getusuario().setClave(
				Encryption.encrypt(new String(txtClave.getPassword())));
		getusuario().setNombres(txtNombres.getText());
		getusuario().setGrupoUsuario(cntGrupoUsuario.getSeleccionado());
	}

	@Override
	public boolean isValidaVista() {
		if (txtidUsuario.getText().trim().isEmpty()) {
			return false;
		}

		if (new String(txtClave.getPassword()).isEmpty()) {
			return false;
		}

		if (!TextFieldObligatorios(txtNombres, cntGrupoUsuario.txtCodigo)) {
			return false;
		}
		return true;
	}

	@Override
	public void eliminar() {
		if (getusuario() != null) {
			getusuarioDAO().remove(getusuario());
		}
	}

	public Usuario getusuario() {
		return usuario;
	}

	public void setusuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getusuarioDAO() {
		return usuarioDAO;
	}

	public void setusuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}
