package vista.formularios.maestros;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import vista.Sys;
import vista.utilitarios.UtilMensajes;
import core.dao.UsuarioDAO;
import core.entity.Usuario;
import core.security.Encryption;

public class FrmCambioClave extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPasswordField txtClave;
	private JPasswordField txtClaveNuevo;
	private JPasswordField txtClaveRepite;

	private Usuario usuario;
	private UsuarioDAO udao = new UsuarioDAO();

	protected JPanel pnlContenido;

	public static void main(String[] args) {
		new FrmCambioClave();
	}

	public FrmCambioClave() {
		setTitle("Cambio de Clave");
		setMaximizable(false);
		setIconifiable(false);
		setClosable(true);
		setVisible(true);
		setResizable(false);
		this.show();

		pnlContenido = new JPanel();
		getContentPane().add(pnlContenido, BorderLayout.CENTER);
		setBounds(100, 100, 330, 152);
		JLabel lblClaveActual = new JLabel("Clave actual:");

		txtClave = new JPasswordField();
		txtClave.setColumns(10);

		JLabel lblNuevaClave = new JLabel("Nueva clave:");

		txtClaveNuevo = new JPasswordField();
		txtClaveNuevo.setColumns(10);

		JLabel lblRepitaClave = new JLabel("Repita clave:");

		txtClaveRepite = new JPasswordField();
		txtClaveRepite.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		JButton btnCambiar = new JButton("Cambiar");
		btnCambiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// System.out.println(Sys.usuario.getIdusuario());
				if (isValidaVista()) {
					Verifica();
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(10)
								.addComponent(lblClaveActual,
										GroupLayout.PREFERRED_SIZE, 71,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(txtClave,
										GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(10)
								.addComponent(lblNuevaClave,
										GroupLayout.PREFERRED_SIZE, 71,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(txtClaveNuevo,
										GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(10)
								.addComponent(lblRepitaClave,
										GroupLayout.PREFERRED_SIZE, 71,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(txtClaveRepite,
										GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(120)
								.addComponent(btnCambiar,
										GroupLayout.PREFERRED_SIZE, 89,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnCancelar,
										GroupLayout.PREFERRED_SIZE, 89,
										GroupLayout.PREFERRED_SIZE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(8)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblClaveActual))
														.addComponent(
																txtClave,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(8)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblNuevaClave))
														.addComponent(
																txtClaveNuevo,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(8)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblRepitaClave))
														.addComponent(
																txtClaveRepite,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(10)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																btnCambiar)
														.addComponent(
																btnCancelar))));
		getContentPane().setLayout(groupLayout);
	}

	public void Verifica() {
		usuario = new Usuario();
		setUsuario(Sys.usuario);

		try {
			String clave = Encryption.decrypt(getUsuario().getClave().trim());

			if (clave.equals(new String(txtClave.getPassword()).trim())) {
				if (new String(txtClaveNuevo.getPassword()).trim().equals(
						new String(txtClaveRepite.getPassword()).trim())) {

					getUsuario().setClave(
							Encryption.pss_encrypt(new String(txtClaveNuevo
									.getPassword()).trim()));
					udao.crear_editar(getUsuario());
					UtilMensajes.mensaje_alterta("CLAVE_CAMBIO_OK");
					Limpiar();

				} else {
					UtilMensajes.mensaje_error("CLAVES_NO_COICIDEN");
					Limpiar();
				}
			} else {
				UtilMensajes.mensaje_error("CLAVE_DIFERENTE");
				Limpiar();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isValidaVista() {
		if (new String(this.txtClave.getPassword()).trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Clave actual");
			this.txtClave.requestFocus();
			return false;
		}

		if (new String(this.txtClaveNuevo.getPassword()).trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Nueva clave");
			this.txtClaveNuevo.requestFocus();
			return false;
		}

		if (new String(this.txtClaveRepite.getPassword()).trim().isEmpty()) {

			this.txtClaveRepite.requestFocus();
			return false;
		}

		return true;
	}

	public void Limpiar() {
		txtClave.setText(null);
		txtClaveNuevo.setText(null);
		txtClaveRepite.setText(null);
		this.txtClave.requestFocus();
	}
}
