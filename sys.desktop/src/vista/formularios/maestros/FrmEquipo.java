package vista.formularios.maestros;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import vista.controles.DSGTableModel;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import vista.utilitarios.editores.TableTextEditor;
import core.dao.EquipoDAO;
import core.dao.SubEquipoDAO;
import core.entity.Equipo;
import core.entity.Subequipo;
import core.entity.SubequipoPK;

public class FrmEquipo extends AbstractMaestro{

	private static final long serialVersionUID = 1L;
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	private EquipoDAO edao = new EquipoDAO();
	private SubEquipoDAO sedao = new SubEquipoDAO();
 
	private List<Equipo> equipoL = new ArrayList<Equipo>();
	@SuppressWarnings("unused")
	private List<Subequipo> subequipoEL = new ArrayList<Subequipo>();
	
	private Equipo EquipoE = new Equipo();
	private JTextField txtDescCorta;
	private JTable tblSubEquipo;
	
	private JScrollPane scrollPane_SubEquipo;
	JButton button=new JButton("");
	
	public DSGTableModel getSubEquipoTM() {
		return ((DSGTableModel) tblSubEquipo.getModel());
	}
	
	public FrmEquipo(){
		super("Familia de Equipos");
		setBounds(100, 100, 600, 353);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblCodigo = new JLabel("Codigo");
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));
		
		JLabel lblDescCorta = new JLabel("Descripcion corta");
		
		txtDescCorta = new JTextField();
		txtDescCorta.setColumns(10);
		txtDescCorta.setDocument(new JTextFieldLimit(50, true));
		
		JLabel lblSubEquipos = new JLabel("SubEquipos");
		
		scrollPane_SubEquipo = new JScrollPane();
		tblSubEquipo = new JTable(new DSGTableModel(new String[] {
				"Código" , "Descripción" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "" });
			}
		});
		scrollPane_SubEquipo.setViewportView(tblSubEquipo);
		tblSubEquipo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			
		
		getSubEquipoTM().setNombre_detalle("Código");
		getSubEquipoTM().setObligatorios(0, 1);
		getSubEquipoTM().setRepetidos(0);
		getSubEquipoTM().setScrollAndTable(scrollPane_SubEquipo, tblSubEquipo);
		
		TableColumnModel cModel = tblSubEquipo.getColumnModel();
		cModel.getColumn(0).setCellEditor(new TableTextEditor(3, true));
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_SubEquipo, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(txtCodigo, 108, 108, 108)
							.addGap(130))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblDescCorta, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(txtDescCorta, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSubEquipos, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
									.addGap(147)))
							.addGap(107))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(lblCodigo))
								.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblDescripcion))
								.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblDescCorta))
								.addComponent(txtDescCorta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(8)
							.addComponent(lblSubEquipos)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_SubEquipo, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(6))
		);
		pnlContenido.setLayout(groupLayout);
		
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override					
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setEquipo(getEquipos().get(selectedRow));
						else
							setEquipo(null);
						llenar_datos();						
					}
				});		
		iniciar();
	}
	@Override
	public void nuevo() {
		setEquipo(new Equipo());			
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		try
		{			
			String lcCodigo = this.txtCodigo.getText();				
			edao.crear_editar(getEquipo());
			sedao.borrarPorEquipo(getEquipo());
			int nFilas = this.getSubEquipoTM().getRowCount();
			for(int i = 0;i < nFilas;i++){
				SubequipoPK sgpk1 = new SubequipoPK();
				Subequipo sg1 = new Subequipo();
				sgpk1.setEquipoIdequipo(lcCodigo);
				sgpk1.setIdsubequipo(getSubEquipoTM().getValueAt(i, 0).toString());
				sg1.setDescripcion(getSubEquipoTM().getValueAt(i, 1).toString());
				sg1.setId(sgpk1);
				sg1.setEquipo(getEquipo());			
				sedao.create(sg1);
			}
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	public boolean validar_detalle(){
		return false;
	}

	@Override
	public void eliminar() {
		if (getEquipo() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				sedao.borrarPorEquipo(getEquipo());
				edao.remove(getEquipo());
				iniciar();
			}			
		}

	}
	
	@Override
	public void llenar_datos() {
		if(getEquipo() instanceof Equipo){
			this.txtCodigo.setText(getEquipo().getIdequipo());
			this.txtDescripcion.setText(getEquipo().getDescripcion());
			this.txtDescCorta.setText(getEquipo().getDescCorta());
		}
		else{
			this.txtCodigo.setText(null);
			this.txtDescripcion.setText(null);
			this.txtDescCorta.setText(null);
		}
		llenar_detalle();		
	}
	
	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Equipo Equipo : getEquipos()) {
			model.addRow(new Object[] { Equipo.getIdequipo(), Equipo.getDescripcion() });
		}
		if (getEquipos().size() > 0) {
			setEquipo(getEquipos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
		llenar_detalle();	
	}
	
	private void llenar_detalle() {
		String Codigo = this.txtCodigo.getText();
		getSubEquipoTM().limpiar();
		
		for(Subequipo subEquipoEnt : sedao.findAll()){			
			if(Codigo.equals(subEquipoEnt.getId().getEquipoIdequipo())){
				getSubEquipoTM().addRow(new Object[] { subEquipoEnt.getId().getIdsubequipo(), subEquipoEnt.getDescripcion()});
			}
		}
	}
	@Override
	public void llenar_tablas() {
		setEquipos(edao.findAll());
	}
	
	public List<Equipo> getEquipos() {
		return this.equipoL;
	}

	public void setEquipos(List<Equipo> equipoL) {
		this.equipoL = equipoL;
	}
	
	public void setEquipo(Equipo EquipoE) {
		this.EquipoE = EquipoE;
	}
	
	public Equipo getEquipo(){
		return this.EquipoE;
	}
	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		this.txtDescCorta.setEditable(true);
		tblLista.setEnabled(false);
		getSubEquipoTM().setEditar(true);
	}
	@Override
	public void vista_noedicion() {
		this.txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		this.txtDescCorta.setEditable(false);
		tblLista.setEnabled(true);
		getSubEquipoTM().setEditar(false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub
		
	}
	//Este separado el dao de la vista
	@Override
	public void llenarDesdeVista() {
		getEquipo().setIdequipo(this.txtCodigo.getText());
		getEquipo().setDescripcion(this.txtDescripcion.getText());
		getEquipo().setDescCorta(this.txtDescCorta.getText());		
	}
	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (edao.find(this.txtCodigo.getText().trim()) != null) {
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
		if (this.txtDescCorta.getText().trim().isEmpty()) {

			this.txtDescCorta.requestFocus();
			return false;
		}
		
		if (!validarDetalles()) {
			return false;
		}
		
		return true;
	}
	
	private boolean validarDetalles() {
		return getSubEquipoTM().esValido();
	}
}

