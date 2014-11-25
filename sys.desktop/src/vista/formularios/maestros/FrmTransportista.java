package vista.formularios.maestros;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.TransportistaDAO;
import core.entity.Transportista;

public class FrmTransportista extends AbstractMaestro{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblLista;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	
	private Transportista transportista = new Transportista();
	private TransportistaDAO tdao = new TransportistaDAO();
	private List<Transportista> TransportistaL = new ArrayList<Transportista>();
	private JTextField txtCategoria;
	private JTextField txtLicencia;
	private JTextField txtFechVenc;

	public FrmTransportista() {
		super("Transportista");
		
		tblLista = new JTable();
		
		JLabel lblNewLabel = new JLabel("DNI:");
		lblNewLabel.setBounds(252, 14, 46, 14);
		
		txtDNI = new JTextField();
		txtDNI.setBounds(308, 11, 112, 20);
		txtDNI.setColumns(10);
		this.txtDNI.setName("DNI");
		txtDNI.setDocument(new JTextFieldLimit(8, true));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(252, 42, 46, 14);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(308, 39, 221, 20);
		txtNombre.setColumns(10);
		this.txtNombre.setName("Nombre");
		txtNombre.setDocument(new JTextFieldLimit(50, true));
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(252, 70, 59, 14);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(308, 67, 221, 20);
		txtDireccion.setColumns(10);
		this.txtDireccion.setName("Dirección");
		txtDireccion.setDocument(new JTextFieldLimit(100, true));
		
		JLabel lblBrevete = new JLabel("Brevete");
		lblBrevete.setBounds(252, 98, 46, 14);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 232, 236);
		
		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(252, 124, 59, 14);
		
		txtCategoria = new JTextField();
		txtCategoria.setBounds(308, 121, 108, 20);
		txtCategoria.setName("Categoría");
		txtCategoria.setColumns(10);
		txtCategoria.setDocument(new JTextFieldLimit(10, true));
		
		JLabel lblNumLicencia = new JLabel("Num. Licen:");
		lblNumLicencia.setBounds(252, 155, 56, 14);
		
		txtLicencia = new JTextField();
		txtLicencia.setBounds(308, 152, 221, 20);
		txtLicencia.setName("Numero de Licencia");
		txtLicencia.setColumns(10);
		txtLicencia.setDocument(new JTextFieldLimit(20, true));
		
		JLabel lblFecVenc = new JLabel("Fec. Venc:");
		lblFecVenc.setBounds(252, 186, 59, 14);
		
		txtFechVenc = new JTextField();
		txtFechVenc.setBounds(308, 180, 111, 20);
		txtFechVenc.setName("Fecha de Vencimiento");
		txtFechVenc.setColumns(10);
		txtFechVenc.setDocument(new JTextFieldLimit(10, true));
		pnlContenido.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(308, 106, 221, 7);
		pnlContenido.add(separator);
		pnlContenido.add(scrollPane);
		pnlContenido.add(lblCategoria);
		pnlContenido.add(lblNewLabel);
		pnlContenido.add(txtDNI);
		pnlContenido.add(lblNombre);
		pnlContenido.add(txtNombre);
		pnlContenido.add(lblDireccin);
		pnlContenido.add(lblBrevete);
		pnlContenido.add(txtDireccion);
		pnlContenido.add(lblFecVenc);
		pnlContenido.add(lblNumLicencia);
		pnlContenido.add(txtFechVenc);
		pnlContenido.add(txtCategoria);
		pnlContenido.add(txtLicencia);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(308, 211, 221, 7);
		pnlContenido.add(separator_1);
		
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setTransportista(getTransportistaL().get(selectedRow));
						else
							setTransportista(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setTransportista(new Transportista());
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		
		getTdao().crear_editar(getTransportista());
	}

	@Override
	public void eliminar() {
		if (getTransportista() != null) {

			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			if (seleccion == 0) {				
				tdao.remove(getTransportista());
				iniciar();
			}
		}

		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		if (getTransportista() != null) {
			txtDNI.setText(getTransportista().getDni());
			txtNombre.setText(getTransportista().getNombre());
			txtDireccion.setText(getTransportista().getDireccion());
			txtCategoria.setText(getTransportista().getTipoBrevete());
			txtLicencia.setText(getTransportista().getNumLic());
			txtFechVenc.setText(getTransportista().getFechVenc());
		} else {
			txtDNI.setText("");
			txtNombre.setText("");
			txtDireccion.setText("");
			txtCategoria.setText("");
			txtLicencia.setText("");
			txtFechVenc.setText("");
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Transportista transportista : getTransportistaL()) {
			model.addRow(new Object[] { transportista.getDni(), transportista.getNombre() });
		}
		if (getTransportistaL().size() > 0) {
			setTransportista(getTransportistaL().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setTransportistaL(getTdao().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtDNI.setEditable(true);
		txtNombre.setEditable(true);
		txtDireccion.setEditable(true);
		txtCategoria.setEditable(true);
		txtFechVenc.setEditable(true);
		txtLicencia.setEditable(true);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		txtDNI.setEditable(false);
		txtNombre.setEditable(false);
		txtDireccion.setEditable(false);
		txtCategoria.setEditable(false);
		txtFechVenc.setEditable(false);
		txtLicencia.setEditable(false);
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

		getTransportista().setDni(txtDNI.getText().trim());
		getTransportista().setDireccion(txtDireccion.getText());
		getTransportista().setNombre(txtNombre.getText());
		getTransportista().setTipoBrevete(txtCategoria.getText());
		getTransportista().setNumLic(txtLicencia.getText());
		getTransportista().setFechVenc(txtFechVenc.getText());
		
	}

	@Override
	public boolean isValidaVista() {

		if (!FormValidador.TextFieldObligatorios(txtDNI, txtNombre, txtDireccion, txtCategoria))
			return false;
		
		if (getEstado().equals(NUEVO)) {
			System.out.println("Probando: " + getTdao().find(txtDNI.getText()));
			if (getTdao().find(txtDNI.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtDNI.requestFocus();
				return false;
			}
		}
		
		if (tdao.find(this.txtDNI.getText().trim()) != null) {
			UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
			this.txtDNI.requestFocus();
			return false;
		}
		
		return true;
	}

	public JTable getTblLista() {
		return tblLista;
	}

	public void setTblLista(JTable tblLista) {
		this.tblLista = tblLista;
	}

	public Transportista getTransportista() {
		return transportista;
	}

	public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}

	public TransportistaDAO getTdao() {
		return tdao;
	}

	public void setTdao(TransportistaDAO tdao) {
		this.tdao = tdao;
	}

	public List<Transportista> getTransportistaL() {
		return TransportistaL;
	}

	public void setTransportistaL(List<Transportista> transportistaL) {
		TransportistaL = transportistaL;
	}
}
