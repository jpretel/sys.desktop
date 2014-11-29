package vista.formularios.maestros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import core.dao.SysFormularioDAO;
import core.dao.SysGrupoDAO;
import core.dao.SysModuloDAO;
import core.dao.SysOpcionDAO;
import core.dao.SysTituloDAO;
import core.entity.SysFormulario;
import core.entity.SysGrupo;
import core.entity.SysGrupoPK;
import core.entity.SysModulo;
import core.entity.SysOpcion;
import core.entity.SysOpcionPK;
import core.entity.SysTitulo;
import core.entity.SysTituloPK;
import vista.contenedores.CntSysFormulario;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;

import javax.swing.DefaultComboBoxModel;

import java.awt.Font;

public class FrmMenus extends AbstractMaestro {

	private static final long serialVersionUID = 1L;
	private JFileChooser fc = null;
	private BufferedImage imagen;

	private JTree tree;
	private JLabel lblCdigo;
	private JTextField txtCodigo;
	private JLabel lblDescripcin;
	private JTextField txtDescripcion;
	private JLabel lblImgen;
	private JTextField txtImagen;
	private JLabel lblTamao;
	private JLabel lblFormulario;
	private JComboBox<String> cboTamanio;
	private CntSysFormulario cntSysFormulario;

	private final static int _modulo = 0;
	private final static int _titulo = 1;
	private final static int _grupo = 2;
	private final static int _opcion = 3;

	private SysModuloDAO moduloDAO = new SysModuloDAO();
	private SysTituloDAO tituloDAO = new SysTituloDAO();
	private SysGrupoDAO grupoDAO = new SysGrupoDAO();
	private SysOpcionDAO opcionDAO = new SysOpcionDAO();
	private SysFormularioDAO formularioDAO = new SysFormularioDAO();

	private SysModulo sysModulo;
	private SysTitulo sysTitulo;
	private SysGrupo sysGrupo;
	private SysOpcion sysOpcion;

	private int tipo;
	private JButton btnBuscar;
	private JLabel lblOpcion;

	public FrmMenus() {
		super("Menus");
		pnlContenido.setLayout(null);

		setSize(664, 433);

		this.tree = new JTree();
		this.tree.setBounds(10, 11, 278, 344);
		pnlContenido.add(this.tree);

		this.lblCdigo = new JLabel("C\u00F3digo");
		this.lblCdigo.setBounds(298, 64, 46, 14);
		pnlContenido.add(this.lblCdigo);

		this.txtCodigo = new JTextField();
		this.txtCodigo.setBounds(353, 61, 109, 20);
		this.txtCodigo.setColumns(10);
		this.txtCodigo.setDocument(new JTextFieldLimit(10, true));
		pnlContenido.add(this.txtCodigo);

		this.lblDescripcin = new JLabel("Descripci\u00F3n");
		this.lblDescripcin.setBounds(298, 89, 65, 14);
		pnlContenido.add(this.lblDescripcin);

		this.txtDescripcion = new JTextField();
		this.txtDescripcion.setColumns(10);
		this.txtDescripcion.setBounds(363, 86, 178, 20);
		this.txtDescripcion.setDocument(new JTextFieldLimit(50, true));
		pnlContenido.add(this.txtDescripcion);

		this.lblImgen = new JLabel("Im\u00E1gen");
		this.lblImgen.setBounds(298, 117, 46, 14);
		pnlContenido.add(this.lblImgen);

		this.txtImagen = new JTextField();
		this.txtImagen.setColumns(10);
		this.txtImagen.setBounds(339, 114, 178, 20);
		this.txtImagen.setEditable(false);
		pnlContenido.add(this.txtImagen);

		this.lblTamao = new JLabel("Tama\u00F1o");
		this.lblTamao.setBounds(298, 202, 46, 14);
		pnlContenido.add(this.lblTamao);

		this.lblFormulario = new JLabel("Formulario");
		this.lblFormulario.setBounds(298, 174, 65, 14);
		pnlContenido.add(this.lblFormulario);

		this.cboTamanio = new JComboBox<String>();
		this.cboTamanio.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Grande", "Mediano", "Peque\u00F1o" }));
		this.cboTamanio.setBounds(349, 199, 121, 20);
		pnlContenido.add(this.cboTamanio);

		this.cntSysFormulario = new CntSysFormulario();
		this.cntSysFormulario.setBounds(359, 170, 279, 23);
		pnlContenido.add(this.cntSysFormulario);

		cntSysFormulario.setData(formularioDAO.findAll());

		this.btnBuscar = new JButton("Buscar");
		this.btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					cargarImagen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btnBuscar.setBounds(527, 113, 89, 23);
		pnlContenido.add(this.btnBuscar);

		this.lblOpcion = new JLabel("C\u00F3digo");
		this.lblOpcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		this.lblOpcion.setBounds(298, 25, 340, 28);
		pnlContenido.add(this.lblOpcion);

		tree.addTreeSelectionListener(new OidSelectionListener());

		iniciar();
	}

	@Override
	public void nuevo() {
		tipo++;
		limpiarVista();
		llenarTitulo();
	}

	class OidSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			sysModulo = null;
			sysTitulo = null;
			sysGrupo = null;
			sysOpcion = null;
			limpiarVista();

			TreePath path = e.getPath();
			Object[] nodes = path.getPath();
			int finalPath = nodes.length - 2;
			tipo = nodes.length - 2;
			for (int k = 0; k < nodes.length; k++) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[k];
				MenuNode nd = (MenuNode) node.getUserObject();

				// Nivel Modulo
				if (nd.getTipo() == _modulo) {
					sysModulo = (SysModulo) nd.getNodo();
				}
				// Nivel Titulo
				if (nd.getTipo() == _titulo) {
					sysTitulo = (SysTitulo) nd.getNodo();
				}

				// Nivel Grupo
				if (nd.getTipo() == _grupo) {
					sysGrupo = (SysGrupo) nd.getNodo();
				}

				// Nivel Opcion
				if (nd.getTipo() == _opcion) {
					sysOpcion = (SysOpcion) nd.getNodo();
				}
			}

			if (finalPath == _modulo) {
				txtCodigo.setText(sysModulo.getIdmodulo());
				txtDescripcion.setText(sysModulo.getDescripcion());
				txtImagen.setText(sysModulo.getImagen());
			}

			if (finalPath == _titulo) {
				txtCodigo.setText(sysTitulo.getId().getIdtitulo());
				txtDescripcion.setText(sysTitulo.getDescripcion());
				txtImagen.setText(sysTitulo.getImagen());
			}

			if (finalPath == _grupo) {
				txtCodigo.setText(sysGrupo.getId().getIdgrupo());
				txtDescripcion.setText(sysGrupo.getDescripcion());
				txtImagen.setText(sysGrupo.getImagen());
			}

			if (finalPath == _opcion) {
				cntSysFormulario.setText(sysOpcion.getId().getIdformulario());
				cntSysFormulario.llenar();
				cboTamanio.setSelectedIndex(sysOpcion.getPrioridad());
			}
			llenarTitulo();
		}

	}

	class MenuNode {
		private String descripcion;
		private int tipo;
		private Object nodo;

		public MenuNode(String descripcion, int tipo, Object nodo) {
			this.descripcion = descripcion;
			this.tipo = tipo;
			this.nodo = nodo;
		}

		public String toString() {
			return descripcion;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public int getTipo() {
			return tipo;
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}

		public Object getNodo() {
			return nodo;
		}

		public void setNodo(Object nodo) {
			this.nodo = nodo;
		}

	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		if (this.tipo == _modulo) {
			grabar_modulo();
		}

		if (this.tipo == _titulo) {
			grabar_titulo();
		}

		if (this.tipo == _grupo) {
			grabar_grupo();
		}

		if (this.tipo == _opcion) {
			grabar_opcion();
		}
	}

	private void grabar_modulo() {
		String idmodulo, descripcion, imagen;
		idmodulo = this.txtCodigo.getText();
		descripcion = this.txtDescripcion.getText();
		imagen = this.txtImagen.getText();
		SysModulo m = new SysModulo();
		m.setIdmodulo(idmodulo);
		m.setDescripcion(descripcion);
		m.setImagen(imagen);
		moduloDAO.crear_editar(m);
	}

	private void grabar_titulo() {
		String idtitulo, descripcion, imagen;
		idtitulo = this.txtCodigo.getText();
		descripcion = this.txtDescripcion.getText();
		imagen = this.txtImagen.getText();

		SysTitulo t = new SysTitulo();
		SysTituloPK id = new SysTituloPK();
		id.setIdmodulo(sysModulo.getIdmodulo());
		id.setIdtitulo(idtitulo);
		t.setId(id);
		t.setDescripcion(descripcion);
		t.setImagen(imagen);

		tituloDAO.crear_editar(t);
	}

	private void grabar_grupo() {
		String idgrupo, descripcion, imagen;

		idgrupo = this.txtCodigo.getText();
		descripcion = this.txtDescripcion.getText();
		imagen = this.txtImagen.getText();
		SysGrupo g = new SysGrupo();
		SysGrupoPK id = new SysGrupoPK();
		id.setIdmodulo(sysModulo.getIdmodulo());
		id.setIdtitulo(sysTitulo.getId().getIdtitulo());
		id.setIdgrupo(idgrupo);
		g.setId(id);
		g.setDescripcion(descripcion);
		g.setImagen(imagen);

		grupoDAO.crear_editar(g);
	}

	private void grabar_opcion() {
		String idformulario;

		idformulario = cntSysFormulario.getSeleccionado().getIdformulario();

		SysOpcion o = new SysOpcion();
		SysOpcionPK id = new SysOpcionPK();
		id.setIdmodulo(sysModulo.getIdmodulo());
		id.setIdtitulo(sysTitulo.getId().getIdtitulo());
		id.setIdgrupo(sysGrupo.getId().getIdgrupo());
		id.setIdformulario(idformulario);

		o.setId(id);
		o.setPrioridad(cboTamanio.getSelectedIndex());

		opcionDAO.crear_editar(o);
	}

	@Override
	public void eliminar() {
		if (tipo == 3) {
			opcionDAO.remove(sysOpcion);
		}

		if (tipo == 2) {
			for (SysOpcion so : opcionDAO.getPorGrupo(sysGrupo)) {
				opcionDAO.remove(so);
			}
			grupoDAO.remove(sysGrupo);
		}

		if (tipo == 1) {
			for (SysGrupo sg : grupoDAO.getPorTitulo(sysTitulo)) {
				for (SysOpcion so : opcionDAO.getPorGrupo(sg)) {
					opcionDAO.remove(so);
				}
				grupoDAO.remove(sg);
			}
			tituloDAO.remove(sysTitulo);
		}

		if (tipo == 0) {
			for (SysTitulo st : tituloDAO.getPorModulo(sysModulo)) {
				for (SysGrupo sg : grupoDAO.getPorTitulo(st)) {
					for (SysOpcion so : opcionDAO.getPorGrupo(sg)) {
						opcionDAO.remove(so);
					}
					grupoDAO.remove(sg);
				}
				tituloDAO.remove(st);
			}
			moduloDAO.remove(sysModulo);
		}

		sysModulo = null;
		sysTitulo = null;
		sysGrupo = null;
		sysOpcion = null;
	}

	@Override
	public void llenar_datos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_lista() {
		System.out.println("Llenar");
		DefaultMutableTreeNode inicio = new DefaultMutableTreeNode(
				new MenuNode("BGC-ERP", -1, null));
		DefaultTreeModel modelo = new DefaultTreeModel(inicio);

		List<SysModulo> modulos = moduloDAO.findAll();
		for (SysModulo modulo : modulos) {
			DefaultMutableTreeNode mNode = new DefaultMutableTreeNode(
					new MenuNode(modulo.getIdmodulo() + " - "
							+ modulo.getDescripcion(), _modulo, modulo));
			modelo.insertNodeInto(mNode, inicio, inicio.getChildCount());
			// tituos
			List<SysTitulo> titulos = tituloDAO.getPorModulo(modulo);
			for (SysTitulo titulo : titulos) {
				DefaultMutableTreeNode mNodeT = new DefaultMutableTreeNode(
						new MenuNode(titulo.getId().getIdtitulo() + " - "
								+ titulo.getDescripcion(), _titulo, titulo));
				modelo.insertNodeInto(mNodeT, mNode, mNode.getChildCount());

				// grupos

				List<SysGrupo> grupos = grupoDAO.getPorTitulo(titulo);
				for (SysGrupo grupo : grupos) {
					DefaultMutableTreeNode mNodeG = new DefaultMutableTreeNode(
							new MenuNode(grupo.getId().getIdgrupo() + " - "
									+ grupo.getDescripcion(), _grupo, grupo));
					modelo.insertNodeInto(mNodeG, mNodeT,
							mNodeT.getChildCount());

					// Opciones
					List<SysOpcion> opciones = opcionDAO.getPorGrupo(grupo);
					for (SysOpcion opcion : opciones) {
						SysFormulario formulario = formularioDAO.find(opcion
								.getId().getIdformulario());
						DefaultMutableTreeNode mNodeO = new DefaultMutableTreeNode(
								new MenuNode(opcion.getId().getIdformulario()
										+ " - " + formulario.getDescripcion(),
										_opcion, opcion));
						modelo.insertNodeInto(mNodeO, mNodeG,
								mNodeG.getChildCount());
					}
				}
			}
		}

		tree.setModel(modelo);
	}

	@Override
	public void llenar_tablas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void vista_edicion() {

		if (tipo == 3) {
			FormValidador.CntEdicion(true, cntSysFormulario);
			cboTamanio.setEnabled(true);
		} else {
			if (getEstado().equals(NUEVO))
				FormValidador.TextFieldsEdicion(true, txtCodigo);
			FormValidador.TextFieldsEdicion(true, txtDescripcion);
			btnBuscar.setEnabled(true);
		}
		tree.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		FormValidador.TextFieldsEdicion(false, txtCodigo, txtDescripcion);
		FormValidador.CntEdicion(false, cntSysFormulario);
		cboTamanio.setEnabled(false);
		btnBuscar.setEnabled(false);
		tree.setEnabled(true);
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {
		// TODO Auto-generated method stub
		return true;
	}

	public void limpiarVista() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		cntSysFormulario.setText("");
		cntSysFormulario.llenar();
		cboTamanio.setSelectedIndex(-1);
	}

	@SuppressWarnings("deprecation")
	public void cargarImagen() throws IOException {

		fc = new JFileChooser();

		int r = fc.showOpenDialog(null);

		if (r == JFileChooser.APPROVE_OPTION) {
			imagen = ImageIO.read(fc.getSelectedFile().toURL());

			String url = CargarURL(fc.getSelectedFile().toURL().toString());
			String extension = url.substring(url.length() - 3).trim();

			if (extension.equals("jpg") || extension.equals("png")) {
				txtImagen.setText(url);
			} else {
				fc = null;
				UtilMensajes.mensaje_error("IMAGEN_ERROR");
			}
		}
	}

	public void guardarImg() throws MalformedURLException, IOException {

		@SuppressWarnings("deprecation")
		String url = CargarURL(fc.getSelectedFile().toURL().toString());
		String extension = url.substring(url.length() - 3);

		ImageIO.write(imagen, extension, new File("src//main/resources/iconos/"
				+ url));

	}

	private void llenarTitulo() {
		if (getEstado().equals(NUEVO)) {
			switch (this.tipo) {
			case _modulo:
				lblOpcion.setText("NUEVO MÓDULO");
				break;

			case _titulo:
				lblOpcion.setText("NUEVO TITULO");
				break;
			case _grupo:
				lblOpcion.setText("NUEVO GRUPO");
				break;
			case _opcion:
				lblOpcion.setText("NUEVA OPCIÓN");
				break;
			}
		} else {
			switch (this.tipo) {
			case _modulo:
				lblOpcion
						.setText("MÓDULO: ".concat(sysModulo.getDescripcion()));
				break;

			case _titulo:
				lblOpcion
						.setText("TITULO: ".concat(sysTitulo.getDescripcion()));
				break;
			case _grupo:
				lblOpcion.setText("GRUPO: ".concat(sysGrupo.getDescripcion()));
				break;
			case _opcion:
//				SysFormulario f = formularioDAO.find(sysOpcion.getId()
//						.getIdformulario());
//				lblOpcion.setText("OPCIÓN: ".concat(f.getDescripcion()));
				break;
			}
		}
	}

	public static String CargarURL(String url) {
		for (int i = url.length() - 1; i <= url.length(); i--) {
			if (url.charAt(i) == '/') {
				url = url.substring(i + 1, url.length());
				break;
			}
		}

		return url;
	}
}
