package vista.formularios.documentos;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.contenedores.cntFormulario;
import vista.controles.DSGTableModel;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.DocumentoDAO;
import core.dao.DocumentoNumeroDAO;
import core.entity.Documento;
import core.entity.DocumentoNumero;
import core.entity.DocumentoNumeroPK;

@SuppressWarnings("serial")
public class FrmDocSerieNumero extends AbstractMaestro {
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtNomenclatura;
	private JTextField txtCodSunat;
	private cntFormulario cntformulario;
	private JTable tblNumerador;
	private Documento documento;
	private DocumentoNumero documentoN;
	private DocumentoDAO documentoDAO = new DocumentoDAO();
	private DocumentoNumeroDAO documentoNDAO = new DocumentoNumeroDAO();
	private List<Documento> documentoL = new ArrayList<Documento>();
	private List<DocumentoNumero> documentoNumeroL = new ArrayList<DocumentoNumero>(); 
	public List<DocumentoNumero> getDocumentoNumeroL() {
		return documentoNumeroL;
	}

	public void setDocumentoNumeroL(List<DocumentoNumero> documentoNumeroL) {
		this.documentoNumeroL = documentoNumeroL;
	}

	public FrmDocSerieNumero() {
		super("Documentos Series y Numeros");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblCodigo = new JLabel("Codigo");
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setName("Descripcion");
		
		JLabel lblNomenclatura = new JLabel("Nomenclatura");
		lblNomenclatura.setName("Nomenclatura");
		
		JLabel lblCodSunat = new JLabel("Cod. Sunat");
		lblCodSunat.setName("Codigo Sunat");
		
		JLabel lblFormulario = new JLabel("Formulario");
		lblFormulario.setName("Formulario");
		
		JScrollPane scrollPaneNum = new JScrollPane();
		
		txtCodigo = new JTextField();
		txtCodigo.setName("Codigo");
		txtCodigo.setMinimumSize(new Dimension(40, 20));
		txtCodigo.setDocument(new JTextFieldLimit(3));
		txtCodigo.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setName("Descripcion");
		txtDescripcion.setDocument(new JTextFieldLimit(200));
		txtDescripcion.setColumns(10);
		
		txtNomenclatura = new JTextField();
		txtNomenclatura.setDocument(new JTextFieldLimit(50));
		txtNomenclatura.setColumns(10);
		
		txtCodSunat = new JTextField();
		txtCodSunat.setMinimumSize(new Dimension(40, 20));
		txtCodSunat.setDocument(new JTextFieldLimit(4));
		txtCodSunat.setColumns(10);
		
		cntformulario = new cntFormulario();
		cntformulario.txtCodigo.setColumns(10);
		tblNumerador = new JTable(new DSGTableModel(new String[] {
				"Serie" , "Numero","PtoEmision" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "",""});
			}
		});
		
		scrollPaneNum.setViewportView(tblNumerador);
		tblNumerador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			
	
		getNumeradorTM().setNombre_detalle("Código");
		getNumeradorTM().setObligatorios(0,1,2);
		getNumeradorTM().setRepetidos(0,2);
		getNumeradorTM().setScrollAndTable(scrollPaneNum, tblNumerador);
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNomenclatura, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCodSunat, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFormulario, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNomenclatura, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
						.addComponent(txtCodSunat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(161))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(241)
					.addComponent(scrollPaneNum, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(335)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(cntformulario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(335)
					.addComponent(txtCodigo, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
					.addGap(223))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNomenclatura, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtNomenclatura, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(8)
									.addComponent(lblCodSunat, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addComponent(txtCodSunat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(8)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFormulario, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(cntformulario, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneNum, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
					.addGap(5))
		);
		pnlContenido.setLayout(groupLayout);
		
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override					
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setDocumento(getDocumentoL().get(selectedRow));
						else
							setDocumento(null);
						llenar_datos();						
					}
				});
		this.iniciar();
	}
	

	@Override
	public void nuevo() {
		setDocumento(new Documento());
	}

	@Override
	public void anular() {

	}

	@Override
	public void grabar() {
		documentoDAO.crear_editar(getDocumento());
		documentoNDAO.borrarPorDocumento(getDocumento());
		for(DocumentoNumero documentoN : documentoNumeroL){
			documentoNDAO.create(documentoN);
		}
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void llenar_datos() {
		if(getDocumento() instanceof Documento){
			this.txtCodigo.setText(getDocumento().getIddocumento());
			this.txtDescripcion.setText(getDocumento().getDescripcion());
			this.txtNomenclatura.setText(getDocumento().getNomenclatura());
			this.txtCodSunat.setText(getDocumento().getCodSunat());
			this.cntformulario.txtCodigo.setText(getDocumento().getFormulario());
			this.cntformulario.txtDescripcion.setText(getDocumento().getDescripcion());
			this.llenar_detalle();
		}else{
			this.txtCodigo.setText(null);
			this.txtDescripcion.setText(null);
			this.txtNomenclatura.setText(null);
			this.txtCodSunat.setText(null);
			this.cntformulario.txtCodigo.setText(null);
		}
			
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Documento documento : getDocumentoL()) {
			model.addRow(new Object[] { documento.getIddocumento(), documento.getDescripcion() });
		}
		if (getDocumentoL().size() > 0) {
			setDocumento(getDocumentoL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
		llenar_detalle();

	}
	
	public void llenar_detalle(){
		String Codigo = this.txtCodigo.getText();
		while(getNumeradorTM().getRowCount() != 0){			
			getNumeradorTM().removeRow(0);			
		}		
		for(DocumentoNumero documentoN : documentoNDAO.findAll()){
			if(Codigo.equals(documentoN.getId().getIddocumento())){
				getNumeradorTM().addRow(new Object[] { documentoN.getId().getSerie(),documentoN.getNumero(),documentoN.getId().getIdptoemision()});
			}
		}
	}

	@Override
	public void llenar_tablas() {
		setDocumentoL(documentoDAO.findAll());

	}

	@Override
	public void vista_edicion() {
		TextFieldsEdicion(true,this.txtCodigo,this.txtDescripcion,this.txtCodSunat,this.txtNomenclatura,this.cntformulario.txtCodigo);
		getNumeradorTM().setEditar(true);
	}

	@Override
	public void vista_noedicion() {
		TextFieldsEdicion(false,this.txtCodigo,this.txtDescripcion,this.txtCodSunat,this.txtNomenclatura,this.cntformulario.txtCodigo);
		getNumeradorTM().setEditar(false);
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
		getDocumento().setIddocumento(this.txtCodigo.getText());
		getDocumento().setDescripcion(this.txtDescripcion.getText());
		getDocumento().setNomenclatura(this.txtNomenclatura.getText());
		getDocumento().setCodSunat(this.txtCodSunat.getText());
		getDocumento().setFormulario(this.cntformulario.txtCodigo.getText());
		setDocumentoNumeroL(new ArrayList<DocumentoNumero>());
		for(int i = 0; i < tblNumerador.getRowCount();i++){
			documentoN = new DocumentoNumero();
			DocumentoNumeroPK id = new DocumentoNumeroPK(); 
			documentoN.setDocumento(getDocumento());
			id.setIddocumento(this.txtCodigo.getText());
			id.setSerie(tblNumerador.getValueAt(i, 0).toString());
			documentoN.setNumero(tblNumerador.getValueAt(i, 1).toString());
			id.setIdptoemision(tblNumerador.getValueAt(i, 2).toString());
			documentoN.setId(id);			
			getDocumentoNumeroL().add(documentoN);
		}
	}

	@Override
	public boolean isValidaVista() {
		if (getEstado().equals(NUEVO)) {
			if (documentoDAO.find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}
		if(!TextFieldObligatorios(this.txtCodigo,this.txtDescripcion,this.txtCodSunat,this.txtNomenclatura,this.cntformulario.txtCodigo))
			return false;
		
		if (!validarDetalles()) {
			return false;
		}
		return true;
	}
	
	private boolean validarDetalles() {
		return getNumeradorTM().esValido();
	}
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getDocumentoL() {
		return documentoL;
	}

	public void setDocumentoL(List<Documento> documentoL) {
		this.documentoL = documentoL;
	}

	public DSGTableModel getNumeradorTM(){
		return ((DSGTableModel)tblNumerador.getModel());
	}
		

}
