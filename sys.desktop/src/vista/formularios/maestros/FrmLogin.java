package vista.formularios.maestros;

import static vista.utilitarios.UtilMensajes.mensaje_alterta;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vista.Sys;
import core.dao.GuardarUsuarioDAO;
import core.dao.UsuarioDAO;
import core.entity.GuardarUsuario;
import core.entity.GuardarUsuarioPK;
import core.entity.Usuario;
import core.security.Encryption;

public class FrmLogin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField txtClave;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private List<ChangeListener> listenerList = new ArrayList<ChangeListener>();
	private JCheckBox chkGuardar;

	GuardarUsuario gu;
	GuardarUsuarioPK gupk;
	GuardarUsuarioDAO gudao = new GuardarUsuarioDAO();
	InetAddress localHost;

	public FrmLogin() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(330, 150));
		getContentPane().setMinimumSize(new Dimension(600, 600));
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Inicio de Sesi\u00F3n");
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(10, 36, 46, 14);
		getContentPane().add(lblClave);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(83, 8, 217, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		txtClave = new JPasswordField();
		txtClave.setBounds(83, 33, 217, 20);
		getContentPane().add(txtClave);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(62, 92, 89, 23);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSesion();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		getContentPane().add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(190, 92, 89, 23);
		getContentPane().add(btnCancelar);

		chkGuardar = new JCheckBox("Guardar datos");
		chkGuardar.setBounds(79, 60, 221, 20);
		getContentPane().add(chkGuardar);
		
		setIconImage(new ImageIcon(
				getClass().getResource("/main/resources/iconos/logo.png")).getImage());

		try {
			datosGuardados();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	private void datosGuardados() throws UnknownHostException {

		localHost = InetAddress.getLocalHost();

		for (GuardarUsuario gusu : gudao.findAll()) {
			if (gusu.getId().getNamehost().equals(localHost.getHostName())) {
				txtUsuario.setText(gusu.getUsuario());
				txtClave.setText(Encryption.decrypt(gusu.getContra()));
				chkGuardar.setSelected(true);
			}
		}
	}

	private void iniciarSesion() throws UnknownHostException {
		Sys.usuario = null;
		String idusuario, clave;
		idusuario = txtUsuario.getText();
		clave = new String(txtClave.getPassword());

		localHost = InetAddress.getLocalHost();
		gu = new GuardarUsuario();
		gupk = new GuardarUsuarioPK();

		Usuario usuario = getUsuarioDAO().getPorUsuarioClave(idusuario,
				Encryption.encrypt(clave));

		if (usuario != null) {
			Sys.usuario = usuario;
			ChangeEvent ce = new ChangeEvent(this);
			for (ChangeListener listener : listenerList) {
				listener.stateChanged(ce);
			}

			gupk.setIdusuario(usuario.getIdusuario());
			gupk.setNamehost(localHost.getHostName());
			gu.setId(gupk);
			gu.setUsuario(idusuario);
			gu.setContra(Encryption.encrypt(clave));

			gudao.borrar();
			if (chkGuardar.isSelected()) {
				gudao.create(gu);
			}

			this.dispose();

		} else {
			mensaje_alterta(this, "CLAVEUSUARIO_INC");
		}
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void addChangeListener(ChangeListener listener) {
		listenerList.add(listener);
	}
}
