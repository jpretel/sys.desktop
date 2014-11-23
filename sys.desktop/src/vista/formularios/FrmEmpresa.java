package vista.formularios;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import vista.Sys;
import vista.controles.DSGInternalFrame;
import vista.controles.JTextFieldLimit;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import core.dao.EmpresaDAO;

public class FrmEmpresa extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EmpresaDAO empresaDAO = new EmpresaDAO();

	protected JPanel pnlContenido;
	protected JTextField txtRS;
	protected JTextField txtRUC;
	protected JTextField txtDireccion;
	private JLabel lblRutaReportes;
	private JTextField txtReportes;
	private JLabel lblRutaExportados;
	private JTextField txtExportados;
	private JButton btnReportes;
	private JButton btnExportar;

	public static void main(String[] args) {
		new FrmEmpresa();
	}

	public FrmEmpresa() {
		setTitle("Empresa");
		setMaximizable(false);
		setIconifiable(false);
		setClosable(true);
		setVisible(true);
		setResizable(false);
		this.show();

		pnlContenido = new JPanel();
		getContentPane().add(pnlContenido, BorderLayout.CENTER);
		setBounds(100, 100, 467, 258);
		JLabel lblClaveActual = new JLabel("Raz\u00F3n Social");

		JLabel lblNuevaClave = new JLabel("Ruc");

		JLabel lblRepitaClave = new JLabel("Direcci\u00F3n");

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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

		this.txtRS = new JTextField();
		this.txtRS.setName("Raz\u00F3n Social");
		this.txtRS.setColumns(10);
		this.txtRS.setDocument(new JTextFieldLimit(200, false));
		this.txtRS.setText(Sys.empresa.getRazon_social());

		this.txtRUC = new JTextField();
		this.txtRUC.setName("R.U.C");
		this.txtRUC.setColumns(10);
		this.txtRUC.setDocument(new JTextFieldLimit(12, false));
		this.txtRUC.setText(Sys.empresa.getRuc());

		this.txtDireccion = new JTextField();
		this.txtDireccion.setName("Direcci\u00F3n");
		this.txtDireccion.setColumns(10);
		this.txtDireccion.setDocument(new JTextFieldLimit(200, false));
		this.txtDireccion.setText(Sys.empresa.getDireccion());

		this.lblRutaReportes = new JLabel("Ruta Reportes");

		this.txtReportes = new JTextField();
		this.txtReportes.setEditable(false);
		this.txtReportes.setText(Sys.empresa.getRuta_reportes());
		this.txtReportes.setName("Direcci\u00F3n");
		this.txtReportes.setColumns(10);

		this.lblRutaExportados = new JLabel("Ruta Exportados");

		this.txtExportados = new JTextField();
		this.txtExportados.setEditable(false);
		this.txtExportados.setText(Sys.empresa.getRuta_exportar());
		this.txtExportados.setName("Direcci\u00F3n");
		this.txtExportados.setColumns(10);

		this.btnReportes = new JButton("...");
		this.btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Seleccione Carpeta");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				int r = chooser.showOpenDialog(null);

				if (r == JFileChooser.APPROVE_OPTION) {
					txtReportes.setText(chooser.getSelectedFile().toString());
				}
			}
		});

		this.btnExportar = new JButton("...");
		this.btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Seleccione Carpeta");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				int r = chooser.showOpenDialog(null);

				if (r == JFileChooser.APPROVE_OPTION) {
					txtExportados.setText(chooser.getSelectedFile().toString());
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								Alignment.LEADING,
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(10)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblClaveActual,
																								GroupLayout.PREFERRED_SIZE,
																								71,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblNuevaClave,
																								GroupLayout.PREFERRED_SIZE,
																								71,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblRepitaClave,
																								GroupLayout.PREFERRED_SIZE,
																								71,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								this.lblRutaReportes,
																								GroupLayout.PREFERRED_SIZE,
																								71,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								this.lblRutaExportados))))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																this.txtRS,
																GroupLayout.PREFERRED_SIZE,
																199,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.txtRUC,
																GroupLayout.PREFERRED_SIZE,
																125,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.txtDireccion,
																GroupLayout.PREFERRED_SIZE,
																226,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.txtExportados,
																GroupLayout.DEFAULT_SIZE,
																294,
																Short.MAX_VALUE)
														.addComponent(
																this.txtReportes))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																this.btnReportes,
																GroupLayout.PREFERRED_SIZE,
																41,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.btnExportar,
																GroupLayout.PREFERRED_SIZE,
																41,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(24, Short.MAX_VALUE))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap(148, Short.MAX_VALUE)
										.addComponent(btnCambiar,
												GroupLayout.PREFERRED_SIZE, 89,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(btnCancelar,
												GroupLayout.PREFERRED_SIZE, 89,
												GroupLayout.PREFERRED_SIZE)
										.addGap(124)));
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
																Alignment.BASELINE)
														.addComponent(
																lblClaveActual)
														.addComponent(
																this.txtRS,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(14)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNuevaClave)
														.addComponent(
																this.txtRUC,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(14)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblRepitaClave)
														.addComponent(
																this.txtDireccion,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(14)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																this.lblRutaReportes)
														.addComponent(
																this.txtReportes,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.btnReportes))
										.addGap(12)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																this.lblRutaExportados)
														.addComponent(
																this.txtExportados,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.btnExportar))
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																btnCambiar)
														.addComponent(
																btnCancelar))
										.addGap(203)));
		getContentPane().setLayout(groupLayout);
	}

	public void Verifica() {

		Sys.empresa.setDireccion(txtDireccion.getText());
		Sys.empresa.setRazon_social(txtRS.getText());
		Sys.empresa.setRuc(txtRUC.getText());
		Sys.empresa.setRuta_exportar(txtExportados.getText());
		Sys.empresa.setRuta_reportes(txtReportes.getText());
		empresaDAO.edit(Sys.empresa);
		UtilMensajes.mensaje_alterta("ACTUALIZA_OK");
		this.dispose();
	}

	public boolean isValidaVista() {
		if (!FormValidador.TextFieldObligatorios(txtDireccion, txtRS, txtRUC,
				txtReportes, txtExportados)) {
			return false;
		}

		return true;
	}

	public void Limpiar() {
		txtDireccion.setText(null);
		txtRS.setText(null);
		txtRUC.setText(null);
		this.txtRS.requestFocus();
	}
}
