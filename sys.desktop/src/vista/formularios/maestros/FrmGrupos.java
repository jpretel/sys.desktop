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
import core.dao.GrupoDAO;
import core.dao.SubgrupoDAO;
import core.entity.Grupo;
import core.entity.Subgrupo;
import core.entity.SubgrupoPK;

public class FrmGrupos extends AbstractMaestro {

	private static final long serialVersionUID = 1L;
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

	private GrupoDAO gdao = new GrupoDAO();
	private SubgrupoDAO sgDAO = new SubgrupoDAO();
 
	private List<Grupo> grupoL = new ArrayList<Grupo>();
	@SuppressWarnings("unused")
	private List<Subgrupo> subgrupoEL = new ArrayList<Subgrupo>();
	
	private Grupo grupoE = new Grupo();
	private JTextField txtDescCorta;
	private JTable tblSubGrupo;
	
	private JScrollPane scrollPane_SubGrupo;
	JButton button=new JButton("");
	
	public DSGTableModel getSubgrupoTM() {
		return ((DSGTableModel) tblSubGrupo.getModel());
	}
	
	public FrmGrupos(){
		super("Familia de Productos");
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
		
		JLabel lblSubgrupos = new JLabel("SubGrupos");
		
		scrollPane_SubGrupo = new JScrollPane();
		tblSubGrupo = new JTable(new DSGTableModel(new String[] {
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
		scrollPane_SubGrupo.setViewportView(tblSubGrupo);
		tblSubGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			
		
		getSubgrupoTM().setNombre_detalle("Código");
		getSubgrupoTM().setObligatorios(0, 1);
		getSubgrupoTM().setRepetidos(0);
		getSubgrupoTM().setScrollAndTable(scrollPane_SubGrupo, tblSubGrupo);
		
		TableColumnModel cModel = tblSubGrupo.getColumnModel();
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
							.addComponent(scrollPane_SubGrupo, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
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
									.addComponent(lblSubgrupos, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
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
							.addComponent(lblSubgrupos)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_SubGrupo, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
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
							setGrupo(getGrupos().get(selectedRow));
						else
							setGrupo(null);
						llenar_datos();						
					}
				});		
		iniciar();
	}
	@Override
	public void nuevo() {
		setGrupo(new Grupo());			
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
			gdao.crear_editar(getGrupo());
			sgDAO.borrarPorGrupo(getGrupo());
			int nFilas = this.getSubgrupoTM().getRowCount();
			for(int i = 0;i < nFilas;i++){
				SubgrupoPK sgpk1 = new SubgrupoPK();
				Subgrupo sg1 = new Subgrupo();
				sgpk1.setIdgrupo(lcCodigo);
				sgpk1.setIdsubgrupo(getSubgrupoTM().getValueAt(i, 0).toString());
				sg1.setDescripcion(getSubgrupoTM().getValueAt(i, 1).toString());
				sg1.setId(sgpk1);
				sg1.setGrupo(getGrupo());			
				sgDAO.create(sg1);
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
		if (getGrupo() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				sgDAO.borrarPorGrupo(getGrupo());
				gdao.remove(getGrupo());
				iniciar();
			}			
		}

	}
	
	@Override
	public void llenar_datos() {
		limpiarVista();
		if(getGrupo() != null){
			this.txtCodigo.setText(getGrupo().getIdgrupo());
			this.txtDescripcion.setText(getGrupo().getDescripcion());
			this.txtDescCorta.setText(getGrupo().getDescCorta());
			llenar_detalle();		
		}
	}
	
	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Grupo grupo : getGrupos()) {
			model.addRow(new Object[] { grupo.getIdgrupo(), grupo.getDescripcion() });
		}
		if (getGrupos().size() > 0) {
			setGrupo(getGrupos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
		llenar_detalle();	
	}
	
	private void llenar_detalle() {
		String Codigo = this.txtCodigo.getText();
		getSubgrupoTM().limpiar();
		
		for(Subgrupo subgrupoEnt : sgDAO.findAll()){			
			if(Codigo.equals(subgrupoEnt.getId().getIdgrupo())){
				getSubgrupoTM().addRow(new Object[] { subgrupoEnt.getId().getIdsubgrupo(), subgrupoEnt.getDescripcion()});
			}
		}
	}
	@Override
	public void llenar_tablas() {
		setGrupos(gdao.findAll());
	}
	
	public List<Grupo> getGrupos() {
		return this.grupoL;
	}

	public void setGrupos(List<Grupo> grupoL) {
		this.grupoL = grupoL;
	}
	
	public void setGrupo(Grupo grupoE) {
		this.grupoE = grupoE;
	}
	
	public Grupo getGrupo(){
		return this.grupoE;
	}
	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		this.txtDescCorta.setEditable(true);
		tblLista.setEnabled(false);
		getSubgrupoTM().setEditar(true);
	}
	@Override
	public void vista_noedicion() {
		this.txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		this.txtDescCorta.setEditable(false);
		tblLista.setEnabled(true);
		getSubgrupoTM().setEditar(false);
	}

	@Override
	public void limpiarVista() {
		this.txtCodigo.setText(null);
		this.txtDescripcion.setText(null);
		this.txtDescCorta.setText(null);
		getSubgrupoTM().limpiar();
	}
	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub
		
	}
	//Este separado el dao de la vista
	@Override
	public void llenarDesdeVista() {
		getGrupo().setIdgrupo(this.txtCodigo.getText());
		getGrupo().setDescripcion(this.txtDescripcion.getText());
		getGrupo().setDescCorta(this.txtDescCorta.getText());		
	}
	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (gdao.find(this.txtCodigo.getText().trim()) != null) {
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
		return getSubgrupoTM().esValido();
	}
}
