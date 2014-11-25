package vista.formularios.maestros;

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

import vista.controles.DSGTextFieldNumber;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.ImpuestoDAO;
import core.entity.Impuesto;

public class FrmImpuesto extends AbstractMaestro {

	private static final long serialVersionUID = 1L;
	
	private Impuesto impuesto;

	private ImpuestoDAO impuestoDAO = new ImpuestoDAO();

	private List<Impuesto> impuestos = new ArrayList<Impuesto>();

	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	protected DSGTextFieldNumber txtValor;
	protected JLabel lblValor;
	
	public FrmImpuesto() {
		super("Impuestos");
		
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(227, 11, 46, 14);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(276, 8, 122, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(227, 36, 75, 14);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 207, 273);

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(286, 33, 122, 20);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));
		
		this.txtValor = new DSGTextFieldNumber(3);
		
		this.lblValor = new JLabel("Valor");

		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCdigo)
						.addComponent(lblDescripcin)
						.addComponent(this.lblValor, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(this.txtDescripcion, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(this.txtCodigo, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
							.addGap(44))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(this.txtValor, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
							.addGap(77)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCdigo)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(this.txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(this.txtDescripcion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDescripcin))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.txtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.lblValor)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
					.addContainerGap())
		);
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setImpuesto(getImpuestos().get(selectedRow));						
						else
							setImpuesto(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setImpuesto(new Impuesto());
		txtCodigo.requestFocus();
		txtValor.setValue(0);
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {		
		impuestoDAO.crear_editar(getImpuesto());
	}

	@Override
	public void eliminar() {
		if (getImpuesto() != null) {
			
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");			
			if (seleccion == 0){
				//logEliminar(getImpuesto());
				//Historial.validar("Eliminar", getArea().historial(), getTitle() );
				impuestoDAO.remove(getImpuesto());
				iniciar();
			}			
		}
		
		setEstado(VISTA);
		vista_noedicion();
	}
	
	@Override
	public void llenar_datos() {
		if (getImpuesto() != null) {
			txtCodigo.setText(getImpuesto().getIdimpuesto());
			txtDescripcion.setText(getImpuesto().getDescripcion());
			txtValor.setValue(getImpuesto().getTasa());
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtValor.setValue(0);
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Impuesto area : getImpuestos()) {
			model.addRow(new Object[] { area.getIdimpuesto(), area.getDescripcion() });
		}
		if (getImpuestos().size() > 0) {
			setImpuesto(getImpuestos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setImpuestos(impuestoDAO.findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		txtValor.setEditable(true);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtValor.setEditable(false);
		tblLista.setEnabled(true);
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
		getImpuesto().setIdimpuesto(txtCodigo.getText());
		getImpuesto().setDescripcion(txtDescripcion.getText());
		getImpuesto().setTasa(Float.parseFloat(txtValor.getText()));
	}

	@Override
	public boolean isValidaVista() {
		
		if(!TextFieldObligatorios(txtDescripcion, txtCodigo))
			return false;
		
		if (getEstado().equals(NUEVO)) {
			if (impuestoDAO.find(this.txtCodigo.getText().trim()) != null) {
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

	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}

	public List<Impuesto> getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(List<Impuesto> impuestos) {
		this.impuestos = impuestos;
	}
}
