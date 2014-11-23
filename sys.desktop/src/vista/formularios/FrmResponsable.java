package vista.formularios;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.contenedores.cntArea;
import vista.controles.JTextFieldLimit;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.ResponsableDAO;
import core.entity.Responsable;

public class FrmResponsable extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private Responsable responsable;
	String bkEntidad = null;

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	private ResponsableDAO responsableDAO = new ResponsableDAO();

	public ResponsableDAO getResponsableDAO() {
		return responsableDAO;
	}

	public void setResponsableDAO(ResponsableDAO responsableDAO) {
		this.responsableDAO = responsableDAO;
	}

	private List<Responsable> responsableL = new ArrayList<Responsable>();

	public List<Responsable> getResponsableL() {
		return responsableL;
	}

	public void setResponsableL(List<Responsable> responsableL) {
		this.responsableL = responsableL;
	}

	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;	
	public final cntArea cntarea;

	public FrmResponsable() {
		super("Responsables");		
		pnlContenido.setBounds(0, 0, 0, 0);
		getBarra().setBounds(0, 0, 539, 39);
		
		JLabel lblCdigo = new JLabel("Codigo");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(100, true));
		
		JLabel lblArea = new JLabel("Area");
		cntarea = new cntArea();
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescripcin)
						.addComponent(lblArea, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addComponent(cntarea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
					.addGap(11))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(lblCdigo)
					.addGap(11)
					.addComponent(lblDescripcin)
					.addGap(11)
					.addComponent(lblArea))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(cntarea, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
		);
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setResponsable(getResponsableL().get(selectedRow));						
						else
							setResponsable(null);
						llenar_datos();
					}
				});
		cntarea.setVisible(false);
		lblArea.setVisible(false);
		iniciar();
	}


	@Override
	public void nuevo() {
		setResponsable(new Responsable());
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {	
		/*if(getResponsableDAO().find(getResponsable().getIdresponsable()) != null){
			Historial.validar("Modificar", bkEntidad , getResponsable().historial(), getTitle() );
		}else{			
			Historial.validar("Nuevo", getResponsable().historial(), getTitle());
		}*/	
		getResponsableDAO().crear_editar(getResponsable());
	}

	@Override
	public void eliminar() {
		if (getResponsable() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				getResponsableDAO().remove(getResponsable());
				iniciar();
			}			
		}
		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		if (getResponsable() != null) {
			txtCodigo.setText(getResponsable().getIdresponsable());
			txtDescripcion.setText(getResponsable().getNombre());
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Responsable responsable : getResponsableL()) {
			model.addRow(new Object[] { responsable.getIdresponsable(), responsable.getNombre()});
		}
		if (getResponsableL().size() > 0) {
			setResponsable(getResponsableL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setResponsableL(getResponsableDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		tblLista.setEnabled(false);		
		cntarea.btnBuscar.setEnabled(true);
		cntarea.txtCodigo.setEditable(true);
		cntarea.txtDescripcion.setEditable(true);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		tblLista.setEnabled(true);
		cntarea.btnBuscar.setEnabled(false);
		cntarea.txtCodigo.setEditable(false);
		cntarea.txtDescripcion.setEditable(false);
	}
	


	@Override
	public void init() {
		
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		
	}

	@Override
	public void llenarDesdeVista() {
		if(getResponsableDAO().find(txtCodigo.getText()) != null){
			bkEntidad = getResponsableDAO().find(txtCodigo.getText()).historial();			
		}
		getResponsable().setIdresponsable(txtCodigo.getText());
		getResponsable().setNombre(txtDescripcion.getText());		
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (getResponsableDAO().find(this.txtCodigo.getText().trim()) != null) {
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
		
		return true;
	}

}
