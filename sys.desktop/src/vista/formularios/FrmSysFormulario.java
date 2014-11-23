package vista.formularios;

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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.barras.PanelBarraMaestro;
import vista.controles.JTextFieldLimit;
import vista.utilitarios.FormValidador;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.SysFormularioDAO;
import core.entity.SysFormulario;

public class FrmSysFormulario extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private SysFormulario formulario;

	private SysFormularioDAO formularioDAO = new SysFormularioDAO();

	private List<SysFormulario> formularios = new ArrayList<SysFormulario>();

	private JFileChooser fc=null;
	private BufferedImage imagen;
	
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JLabel lblOpcin;
	private JTextField txtOpcion;
	private JLabel lblImgen;
	private JTextField txtImagen;
	private JButton btnImg = new JButton("");

	public FrmSysFormulario() {
		super("Formularios");

		JLabel lblCdigo = new JLabel("C\u00F3digo");

		txtCodigo = new JTextField();
		this.txtCodigo.setName("C\u00F3digo");
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(15, true));

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");

		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		this.txtDescripcion.setName("Descripci\u00F3n");
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, false));

		this.lblOpcin = new JLabel("Opci\u00F3n");

		this.txtOpcion = new JTextField();
		this.txtOpcion.setName("Descripci\u00F3n");
		this.txtOpcion.setColumns(10);

		this.lblImgen = new JLabel("Imagen");

		this.txtImagen = new JTextField();
		this.txtImagen.setName("Descripci\u00F3n");
		this.txtImagen.setColumns(10);
				
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCdigo)
						.addComponent(lblDescripcin)
						.addComponent(lblOpcin, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblImgen, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addComponent(txtImagen, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(162)
							.addComponent(btnImg, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtOpcion, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
					.addGap(11))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addComponent(lblCdigo)
					.addGap(16)
					.addComponent(lblDescripcin)
					.addGap(19)
					.addComponent(lblOpcin)
					.addGap(19)
					.addComponent(lblImgen))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(txtOpcion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtImagen, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnImg, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
		);
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setFormulario(getFormularios().get(selectedRow));
						else
							setFormulario(null);
						llenar_datos();
					}
				});
		iniciar();
	}
	
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
	
	public void guardarImg() throws MalformedURLException, IOException{		
		
		String url = CargarURL(fc.getSelectedFile().toURL().toString());
		String extension = url.substring(url.length()-3);
		
		ImageIO.write(imagen, extension, new File("src//main/resources/iconos/"+ url));
		
	}
	
	public static String CargarURL(String url){
		int aux;
		for(int i = url.length()-1; i<=url.length();i--){
			if(url.charAt(i) == '/'){
				aux = i;
				url = url.substring(i+1, url.length());
				break;
			}
		}
		
		return url;
	}

	@Override
	public void nuevo() {
		setFormulario(new SysFormulario());
		txtCodigo.requestFocus();
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		formularioDAO.crear_editar(getFormulario());		
		
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

	@Override
	public void eliminar() {
		if (getFormulario() != null) {
			formularioDAO.remove(getFormulario());
		}
	}

	@Override
	public void llenar_datos() {
		if (getFormulario() != null) {
			txtCodigo.setText(getFormulario().getIdformulario());
			txtDescripcion.setText(getFormulario().getDescripcion());
			txtOpcion.setText(getFormulario().getOpcion());
			txtImagen.setText(getFormulario().getImagen());
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtOpcion.setText("");
			txtImagen.setText("");
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (SysFormulario ob : getFormularios()) {
			model.addRow(new Object[] { ob.getIdformulario(),
					ob.getDescripcion() });
		}
		if (getFormularios().size() > 0) {
			setFormulario(getFormularios().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setFormularios(formularioDAO.findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		TextFieldsEdicion(true, txtDescripcion, txtOpcion);
		tblLista.setEnabled(false);
		btnImg.setEnabled(true);
	}

	@Override
	public void vista_noedicion() {
		TextFieldsEdicion(false, txtCodigo, txtDescripcion, txtImagen,
				txtOpcion);
		tblLista.setEnabled(true);
		btnImg.setEnabled(false);
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
		getFormulario().setIdformulario(txtCodigo.getText());
		getFormulario().setDescripcion(txtDescripcion.getText());
		getFormulario().setOpcion(txtOpcion.getText());
		getFormulario().setImagen(txtImagen.getText());
	}

	@Override
	public boolean isValidaVista() {

		if (!FormValidador.TextFieldObligatorios(txtCodigo, txtDescripcion,
				txtOpcion))
			return false;

		if (getEstado().equals(NUEVO)) {
			if (formularioDAO.find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		return true;
	}

	@Override
	public void limpiarDetalle() {
		// TODO Auto-generated method stub

	}

	public SysFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(SysFormulario formulario) {
		this.formulario = formulario;
	}

	public List<SysFormulario> getFormularios() {
		return formularios;
	}

	public void setFormularios(List<SysFormulario> formularios) {
		this.formularios = formularios;
	}
}
