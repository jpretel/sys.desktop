package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.SubdiarioDAO;
import core.entity.Subdiario;

public class FrmSubdiario extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private Subdiario subdiario;

	private SubdiarioDAO subdiarioDAO = new SubdiarioDAO();

	private List<Subdiario> subdiarios = new ArrayList<Subdiario>();
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JCheckBox chkEsDeclarable;
	public FrmSubdiario() {
		super("SubDiarios");

		getBarra().setFormMaestro(this);

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
		
		chkEsDeclarable = new JCheckBox("Es declarable");

		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCdigo)
								.addComponent(lblDescripcin))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtCodigo, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
									.addGap(44))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chkEsDeclarable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(153)))
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
									.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDescripcin))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chkEsDeclarable))
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
							setSubdiario(getSubdiarios().get(selectedRow));
						else
							setSubdiario(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setSubdiario(new Subdiario());
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		getSubdiarioDAO().crear_editar(getSubdiario());
	}

	@Override
	public void llenarDesdeVista() {
		getSubdiario().setIdsubdiario(txtCodigo.getText());
		getSubdiario().setDescripcion(txtDescripcion.getText());
		getSubdiario().setEsDeclarable(chkEsDeclarable.isSelected()? 1 : 0);
	};

	@Override
	public void eliminar() {
		if (getSubdiario()!= null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				getSubdiarioDAO().remove(getSubdiario());
				iniciar();
			}			
		}
	}

	@Override
	public void llenar_datos() {
		if (getSubdiario() != null) {
			txtCodigo.setText(getSubdiario().getIdsubdiario());
			txtDescripcion.setText(getSubdiario().getDescripcion());
			chkEsDeclarable.setSelected(getSubdiario().getEsDeclarable() == 1);
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			chkEsDeclarable.setSelected(true);
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Subdiario obj: getSubdiarios()) {
			model.addRow(new Object[] { obj.getIdsubdiario(), obj.getDescripcion() });
		}
		if (getSubdiarios().size() > 0) {
			setSubdiario(getSubdiarios().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setSubdiarios(getSubdiarioDAO().findAll());
	}
	
	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		chkEsDeclarable.setEnabled(true);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		chkEsDeclarable.setEnabled(false);
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
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		
		if (getEstado().equals(NUEVO)) {
			if (getSubdiarioDAO().find(this.txtCodigo.getText().trim()) != null) {
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

	public SubdiarioDAO getSubdiarioDAO() {
		return subdiarioDAO;
	}

	public void setSubdiarioDAO(SubdiarioDAO subdiarioDAO) {
		this.subdiarioDAO = subdiarioDAO;
	}

	public Subdiario getSubdiario() {
		return subdiario;
	}

	public void setSubdiario(Subdiario subdiario) {
		this.subdiario = subdiario;
	}

	public List<Subdiario> getSubdiarios() {
		return subdiarios;
	}

	public void setSubdiarios(List<Subdiario> subdiarios) {
		this.subdiarios = subdiarios;
	}
}
