package vista.formularios.maestros;

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

import vista.contenedores.CntSubdiario;
import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.GrupoCentralizacionDAO;
import core.dao.SubdiarioDAO;
import core.entity.GrupoCentralizacion;

public class FrmGrupoCentralizacion extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private GrupoCentralizacion grupo;

	private GrupoCentralizacionDAO grupoDAO = new GrupoCentralizacionDAO();

	private List<GrupoCentralizacion> grupos = new ArrayList<GrupoCentralizacion>();
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	CntSubdiario cntSubdiario;

	public FrmGrupoCentralizacion() {
		super("Grupo de Centralización");
		initGUI();
	}

	private void initGUI() {

		JLabel lblCdigo = new JLabel("C\u00F3digo");

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
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		JLabel lblTipoDeAnlisis = new JLabel("Sub Diario");

		cntSubdiario = new CntSubdiario();
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(12)
										.addComponent(scrollPane,
												GroupLayout.PREFERRED_SIZE,
												257, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblDescripcin,
																GroupLayout.PREFERRED_SIZE,
																68,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblTipoDeAnlisis,
																GroupLayout.PREFERRED_SIZE,
																60,
																GroupLayout.PREFERRED_SIZE))
										.addGap(5)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																cntSubdiario,
																GroupLayout.DEFAULT_SIZE,
																225,
																Short.MAX_VALUE)
														.addComponent(
																txtDescripcion,
																GroupLayout.DEFAULT_SIZE,
																255,
																Short.MAX_VALUE))
										.addGap(10))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(287)
										.addComponent(lblCdigo,
												GroupLayout.PREFERRED_SIZE, 39,
												GroupLayout.PREFERRED_SIZE)
										.addGap(34)
										.addComponent(txtCodigo,
												GroupLayout.DEFAULT_SIZE, 116,
												Short.MAX_VALUE).addGap(149)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(12)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 228,
												Short.MAX_VALUE).addGap(17))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(26)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblCdigo,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtCodigo,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(6)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblDescripcin,
																				GroupLayout.PREFERRED_SIZE,
																				16,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																txtDescripcion,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE))
										.addGap(11)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblTipoDeAnlisis,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cntSubdiario,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE))));
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
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
		setGrupo(new GrupoCentralizacion());
		txtCodigo.requestFocus();
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		getGrupoDAO().crear_editar(getGrupo());
	}

	@Override
	public void llenarDesdeVista() {
		getGrupo().setIdgcentralizacion(txtCodigo.getText());
		getGrupo().setDescripcion(txtDescripcion.getText());
		getGrupo().setSubdiario(cntSubdiario.getSeleccionado());

	};

	@Override
	public void eliminar() {
		if (getGrupo() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getGrupoDAO().remove(getGrupo());
				iniciar();
			}
		}
		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getGrupo() != null) {
			txtCodigo.setText(grupo.getIdgcentralizacion());
			txtDescripcion.setText(grupo.getDescripcion());
			cntSubdiario.txtCodigo.setText((grupo.getSubdiario() == null) ? ""
					: grupo.getSubdiario().getIdsubdiario());
			cntSubdiario.llenar();
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (GrupoCentralizacion obj : getGrupos()) {
			model.addRow(new Object[] { obj.getIdgcentralizacion(),
					obj.getDescripcion() });
		}
		if (getGrupos().size() > 0) {
			setGrupo(getGrupos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setGrupos(getGrupoDAO().findAll());
		actualiza_tablas();
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		FormValidador.CntEdicion(true, cntSubdiario);
		tblLista.setEnabled(false);
		TextFieldsEdicion(true, txtDescripcion);
	}

	@Override
	public void vista_noedicion() {
		TextFieldsEdicion(false, txtCodigo, txtDescripcion);
		FormValidador.CntEdicion(false, cntSubdiario);
		tblLista.setEnabled(true);
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		cntSubdiario.setSeleccionado(null);
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidaVista() {

		if (!TextFieldObligatorios(txtCodigo, txtDescripcion,
				cntSubdiario.txtCodigo))
			return false;

		if (getEstado().equals(NUEVO)) {
			if (getGrupoDAO().find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}

		return true;
	}

	@Override
	public void actualiza_tablas() {
		if (cntSubdiario != null)
			cntSubdiario.setData(new SubdiarioDAO().findAll());
	}

	public GrupoCentralizacion getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoCentralizacion grupo) {
		this.grupo = grupo;
	}

	public GrupoCentralizacionDAO getGrupoDAO() {
		return grupoDAO;
	}

	public void setGrupoDAO(GrupoCentralizacionDAO grupoDAO) {
		this.grupoDAO = grupoDAO;
	}

	public List<GrupoCentralizacion> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoCentralizacion> grupos) {
		this.grupos = grupos;
	}
}
