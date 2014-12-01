package vista.formularios.maestros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.barras.PanelBarraMaestro;
import vista.controles.DSGTableModel;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.SysGrupoDAO;
import core.dao.SysModuloDAO;
import core.dao.SysOpcionDAO;
import core.dao.SysTituloDAO;
import core.entity.SysGrupo;
import core.entity.SysModulo;
import core.entity.SysTitulo;
import core.entity.SysTituloPK;

public class FrmSysModulo extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tblLista;
	SysTituloDAO stdao = new SysTituloDAO();
	List<SysTitulo> lista;
	SysGrupoDAO sgdao = new SysGrupoDAO();
	SysOpcionDAO sodao = new SysOpcionDAO();

	private JFileChooser fc=null;
	private BufferedImage imagen;
	
	private JTable tblTitulo;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	private SysModulo sysModulo;

	private List<SysModulo> sysModulos;
	private List<SysTitulo> sysTitulos;

	private SysModuloDAO sysModuloDAO = new SysModuloDAO();

	private SysTituloDAO sysTituloDAO = new SysTituloDAO();
	private JLabel lblImagen;
	private JTextField txtImagen;
	private JButton btnImg;

	public FrmSysModulo() {
		super("Gestión de Módulos");

		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPaneNum = new JScrollPane();

		tblTitulo = new JTable(new DSGTableModel(new String[] { "Cod. Título",
				"Descripción" }) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { idMas(), "" });
			}
		});
		scrollPaneNum.setViewportView(tblTitulo);
		getTituloTM().setObligatorios(1, 2);
		getTituloTM().setRepetidos(0);
		getTituloTM().setScrollAndTable(scrollPaneNum, tblTitulo);
		tblTitulo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblCdigo = new JLabel("Codigo");

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));
		
		lblImagen = new JLabel("Imagen");
		
		txtImagen = new JTextField();
		txtImagen.setColumns(10);
		
		btnImg = new JButton("");
		
		btnImg.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestro.class
				.getResource("/main/resources/iconos/find.png")).getImage()
				.getScaledInstance(18, 18, java.awt.Image.SCALE_DEFAULT)));
		
		btnImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
					cargarImagen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDescripcin, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblImagen, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtImagen, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(204)
									.addComponent(btnImg, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(scrollPaneNum, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
					.addGap(11))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCdigo))
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDescripcin))
						.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblImagen))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(txtImagen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnImg))
					.addGap(4)
					.addComponent(scrollPaneNum, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(11))
		);
		pnlContenido.setLayout(groupLayout);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setSysModulo(getSysModulos().get(selectedRow));
						else
							setSysModulo(null);
						llenar_datos();
					}
				});
		iniciar();
		cargar();
	}

	@SuppressWarnings("deprecation")
	public void cargarImagen() throws IOException{		
		
		fc=new JFileChooser();
		
	    int r=fc.showOpenDialog(null);
	   
	    
	    if(r==JFileChooser.APPROVE_OPTION){	 	    	
			imagen=ImageIO.read(fc.getSelectedFile().toURL());	
			
			String url = CargarURL(fc.getSelectedFile().toURL().toString());
			String extension = url.substring(url.length()-3).trim();
			
			if(extension.equals("jpg") || extension.equals("png")){
		    	txtImagen.setText(url);
		    }else{		    	
		    	fc = null;
		    	UtilMensajes.mensaje_error("IMAGEN_ERROR");
		    }
	    }    
	}
	
	@SuppressWarnings("deprecation")
	public void guardarImg() throws MalformedURLException, IOException{		
		
		String url = CargarURL(fc.getSelectedFile().toURL().toString());
		String extension = url.substring(url.length()-3);
		
		ImageIO.write(imagen, extension, new File("src//main/resources/iconos/"+ url));
		
	}
	
	public static String CargarURL(String url){
		for(int i = url.length()-1; i<=url.length();i--){
			if(url.charAt(i) == '/'){
				url = url.substring(i+1, url.length());
				break;
			}
		}
		
		return url;
	}
	
	public String idMas() {
		int tot;

		tot = tblTitulo.getRowCount() + 1;

		if (tot < 10) {
			return "00" + tot;
		} else if (tot < 100) {
			return "0" + tot;
		} else {
			return "" + tot;
		}
	}

	@Override
	public void nuevo() {
		setSysModulo(new SysModulo());
		txtCodigo.requestFocus();
		
	}

	@Override
	public void editar() {
		super.editar();
	}

	@Override
	public void anular() {
		iniciar();
	}

	@Override
	public void grabar() {
		getSysModuloDAO().crear_editar(getSysModulo());

		for (SysTitulo obj : sysTituloDAO.aEliminar(getSysModulo(),
				getSysTitulos())) {
			getSysTituloDAO().remove(obj);
		}

		for (SysTitulo obj : getSysTitulos()) {
			getSysTituloDAO().crear_editar(obj);
		}
		
		try {
			if(!(fc==null)){					
				guardarImg();	
				fc = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cargar() {
		lista = new ArrayList<SysTitulo>();

		for (SysTitulo st : stdao.findAll()) {
			lista.add(st);
		}

	}

	@Override
	public void llenarDesdeVista() {
		getSysModulo().setIdmodulo(this.txtCodigo.getText().trim());
		getSysModulo().setDescripcion(this.txtDescripcion.getText().trim());
		getSysModulo().setImagen(this.txtImagen.getText().trim());
		
		setSysTitulos(new ArrayList<SysTitulo>());

		for (int i = 0; i < getTituloTM().getRowCount(); i++) {
			SysTituloPK id = new SysTituloPK();
			SysTitulo obj = new SysTitulo();

			id.setIdmodulo(getSysModulo().getIdmodulo());
			id.setIdtitulo(getTituloTM().getValueAt(i, 0).toString());

			obj.setId(id);
			obj.setDescripcion(getTituloTM().getValueAt(i, 1).toString());
			getSysTitulos().add(obj);
		}

	}

	@Override
	public void llenar_datos() {
		getTituloTM().limpiar();
		setSysTitulos(new ArrayList<SysTitulo>());

		// if (!getEstado().equals(NUEVO) && getSysModulo() != null) {
		if (getSysModulo() != null) {
			txtCodigo.setText(getSysModulo().getIdmodulo());
			txtDescripcion.setText(getSysModulo().getDescripcion());
			txtImagen.setText(getSysModulo().getImagen());
			setSysTitulos(getSysTituloDAO().getPorModulo(getSysModulo()));

			for (SysTitulo obj : getSysTitulos()) {
				getTituloTM().addRow(
						new Object[] { obj.getId().getIdtitulo(),
								obj.getDescripcion() });
			}
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		}
		
		if (getEstado().equals(NUEVO)) {
			ArrayList<String> filas = new ArrayList<String>();
			filas.add("TABLAS");
			filas.add("DOCUMENTOS");
			filas.add("REPORTES");
			for (int i = 0; i < filas.size(); i++) {
				final Object fila[] = { "00" + (i + 1), filas.get(i) };
				getTituloTM().addRow(fila);
			}
		}
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty())
			return false;
		if (this.txtDescripcion.getText().trim().isEmpty())
			return false;
		return true;
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (SysModulo obj : getSysModulos()) {
			model.addRow(new Object[] { obj.getIdmodulo(), obj.getDescripcion() });
		}
		if (getSysModulos().size() > 0) {
			setSysModulo(getSysModulos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setSysModulos(getSysModuloDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		else
			txtCodigo.setEditable(false);
		txtDescripcion.setEditable(true);
		btnImg.setEnabled(true);
		getTituloTM().setEditar(true);

	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtImagen.setEditable(false);
		btnImg.setEnabled(false);
		getTituloTM().setEditar(false);
	}

	@Override
	public void eliminar() {
		boolean exisite = false;
		for (SysGrupo sysGrupo : sgdao.findAll()) {
			if (sysGrupo.getId().getIdmodulo()
					.equals(getSysModulo().getIdmodulo())) {
				exisite = true;
				System.out.println("Probando 1");
				break;
			}
		}

		if (!exisite) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				if (getSysModulo() != null) {
					getSysTituloDAO().borrarPorModulo(getSysModulo());
					getSysModuloDAO().remove(getSysModulo());
					for (SysTitulo s : getSysTitulos()) {
						sysTituloDAO.remove(s);
					}
					iniciar();
				}
			}
		} else {
			UtilMensajes.mensaje_alterta("NO_ELIMINAR");
		}

	}

	private DSGTableModel getTituloTM() {
		return (DSGTableModel) tblTitulo.getModel();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	public SysModulo getSysModulo() {
		return sysModulo;
	}

	public void setSysModulo(SysModulo sysModulo) {
		this.sysModulo = sysModulo;
	}

	public List<SysModulo> getSysModulos() {
		return sysModulos;
	}

	public void setSysModulos(List<SysModulo> sysModulos) {
		this.sysModulos = sysModulos;
	}

	public SysModuloDAO getSysModuloDAO() {
		return sysModuloDAO;
	}

	public void setSysModuloDAO(SysModuloDAO sysModuloDAO) {
		this.sysModuloDAO = sysModuloDAO;
	}

	public SysTituloDAO getSysTituloDAO() {
		return sysTituloDAO;
	}

	public void setSysTituloDAO(SysTituloDAO sysTituloDAO) {
		this.sysTituloDAO = sysTituloDAO;
	}

	public List<SysTitulo> getSysTitulos() {
		return sysTitulos;
	}

	public void setSysTitulos(List<SysTitulo> sysTitulos) {
		this.sysTitulos = sysTitulos;
	}
}