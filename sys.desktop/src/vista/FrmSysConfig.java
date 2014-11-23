package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vista.combobox.ComboBox;
import core.inicio.ConectionManager;
import core.inicio.SysCfgInicio;

public class FrmSysConfig extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtServidor;
	private JTextField txtUsuario;
	private JPasswordField txtClave;
	private JTextField txtBD;

	private SysCfgInicio cfgInicio;

	private List<ChangeListener> listenerList = new ArrayList<ChangeListener>();
	private JLabel lblBaseDeDatos_1;
	private ComboBox comboBox;
	private List<String[]> optionList = new ArrayList<String[]>();
	/**
	 * Create the frame.
	 */
	public FrmSysConfig() {
		setAlwaysOnTop(true);
		setMinimumSize(new Dimension(350, 240));
		getContentPane().setMinimumSize(new Dimension(600, 600));
		setIconImage(new ImageIcon(
				getClass().getResource("/main/resources/iconos/logo.png")).getImage());
		
		setResizable(false);
		setTitle("Configuraci\u00F3n Inicial");
		
		JLabel lblServidor = new JLabel("Servidor");
		lblServidor.setBounds(24, 58, 46, 14);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(24, 83, 46, 14);

		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(24, 112, 46, 14);

		txtServidor = new JTextField();
		txtServidor.setBounds(109, 55, 225, 20);
		txtServidor.setColumns(10);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(109, 80, 149, 20);
		txtUsuario.setColumns(10);

		txtClave = new JPasswordField();
		txtClave.setBounds(109, 109, 149, 20);

		JButton btnAceptar = new JButton("Agregar");
		btnAceptar.setBounds(52, 168, 94, 23);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isValido()) {
					ChangeEvent ce = new ChangeEvent(this);
					for (ChangeListener listener : listenerList) {
						listener.stateChanged(ce);
					}
					
				} else {
					System.out.println("Roche");
				}
			}
		});

		JLabel lblBaseDeDatos = new JLabel("Base de Datos");
		lblBaseDeDatos.setBounds(24, 133, 80, 24);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(224, 168, 94, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrar();
			}
		});

		txtBD = new JTextField();
		txtBD.setBounds(108, 135, 106, 20);
		txtBD.setColumns(10);
		getContentPane().setLayout(null);
		getContentPane().add(lblServidor);
		getContentPane().add(txtServidor);
		getContentPane().add(btnAceptar);
		getContentPane().add(btnCancelar);
		getContentPane().add(lblBaseDeDatos);
		getContentPane().add(txtBD);
		getContentPane().add(lblUsuario);
		getContentPane().add(lblClave);
		getContentPane().add(txtClave);
		getContentPane().add(txtUsuario);
		
		this.lblBaseDeDatos_1 = new JLabel("Gestor de BD");
		this.lblBaseDeDatos_1.setBounds(24, 30, 80, 14);
		getContentPane().add(this.lblBaseDeDatos_1);
		
		optionList.add(new String[]{ConectionManager.SQLSERVER,"SQL Server"});
		optionList.add(new String[]{ConectionManager.MYSQL,"MYSQL"});
		comboBox = new ComboBox(optionList,1); 
		
		this.comboBox.setBounds(109, 27, 132, 20);
		getContentPane().add(this.comboBox);
		//getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblServidor, txtServidor, lblUsuario, txtUsuario, lblClave, txtClave, lblBaseDeDatos, txtBD, btnAceptar, btnCancelar}));
	}

	private boolean isCamposValidos() {
		
		if (comboBox.getSelectedIndex()==-1) {
			return false;
		}
		if (txtServidor.getText().trim().isEmpty()) {
			return false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			return false;
		}
		
		if (txtUsuario.getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean isValido() {
		if (!isCamposValidos()) {
			return false;
		}
		
		setCfgInicio(new SysCfgInicio());
		
		String gestor = optionList.get(comboBox.getSelectedIndex())[0].toString();
		
		getCfgInicio().setGestor(gestor);
		getCfgInicio().setServidor(txtServidor.getText().trim());
		getCfgInicio().setUsuario(txtUsuario.getText().trim());
		getCfgInicio().setClave(new String(txtClave.getPassword()));
		getCfgInicio().setBase_datos(txtBD.getText().trim());
		
		if (!ConectionManager.isConexionOK(cfgInicio, this)) {
			return false;
		}

		return true;
	}

	public SysCfgInicio getCfgInicio() {
		return cfgInicio;
	}

	public void setCfgInicio(SysCfgInicio cfgInicio) {
		this.cfgInicio = cfgInicio;
	}

	public void addChangeListener(ChangeListener listener) {
		listenerList.add(listener);
	}
	
	private void cerrar(){
		this.dispose();
	}
}
