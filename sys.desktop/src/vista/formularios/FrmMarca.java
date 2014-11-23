package vista.formularios;

import java.awt.Component;
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

import vista.controles.JTextFieldLimit;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.MarcaDAO;
import core.entity.Marca;

public class FrmMarca extends AbstractMaestro {

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtNombreCorto;
	private JTable tblLista;
	private Marca marca;
	private MarcaDAO mdao = new MarcaDAO();
	private List<Marca> Marcas = new ArrayList<Marca>();

	public List<Marca> getMarcas() {
		return Marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		Marcas = marcas;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public FrmMarca() {
		super("Marca de Productos");

		JLabel lblCodigo = new JLabel("Codigo");

		JLabel lblDescripcion = new JLabel("Descripcion");

		JLabel lblNomenclatura = new JLabel("Nombre Corto");

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		txtNombreCorto = new JTextField();
		txtNombreCorto.setColumns(10);
		txtNombreCorto.setDocument(new JTextFieldLimit(30, true));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(10)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 230,
												Short.MAX_VALUE)
										.addGap(10)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				lblCodigo))
														.addComponent(
																lblDescripcion)
														.addComponent(
																lblNomenclatura))
										.addGap(21)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtCodigo)
																		.addGap(105))
														.addComponent(
																txtDescripcion,
																GroupLayout.DEFAULT_SIZE,
																191,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				txtNombreCorto)
																		.addGap(105)))
										.addGap(10)));
		groupLayout.setVerticalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(11)
								.addComponent(scrollPane,
										GroupLayout.DEFAULT_SIZE, 230,
										Short.MAX_VALUE).addGap(15))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(11)
								.addComponent(lblCodigo,
										GroupLayout.PREFERRED_SIZE, 46,
										GroupLayout.PREFERRED_SIZE).addGap(1)
								.addComponent(lblDescripcion).addGap(17)
								.addComponent(lblNomenclatura))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGap(24)
								.addComponent(txtCodigo,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(txtDescripcion,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(txtNombreCorto,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)));
		pnlContenido.setLayout(groupLayout);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setMarca(getMarcas().get(selectedRow));
						else
							setMarca(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	public void grabar(){
		getMdao().crear_editar(getMarca());
	}

	public MarcaDAO getMdao() {
		return mdao;
	}

	public void setMdao(MarcaDAO mdao) {
		this.mdao = mdao;
	}

	public void llenarDesdeVista() {
		
		setMarca(new Marca());
		getMarca().setIdmarca(this.txtCodigo.getText());
		getMarca().setDescripcion(this.txtDescripcion.getText());
		getMarca().setNomcorto(this.txtNombreCorto.getText());
		
	};

	@Override
	public void anular() {
		// TODO Auto-generated method stub
	}

	@Override
	public void llenar_datos() {
		if (getMarca() instanceof Marca) {
			this.txtCodigo.setText(getMarca().getIdmarca());
			this.txtDescripcion.setText(getMarca().getDescripcion());
			this.txtNombreCorto.setText(getMarca().getNomcorto());
		} else {
			this.txtCodigo.setText(null);
			this.txtDescripcion.setText(null);
			this.txtNombreCorto.setText(null);
		}

	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);
		MaestroTableModel modelo = (MaestroTableModel) tblLista.getModel();
		modelo.limpiar();
		for (Marca marca : getMarcas()) {
			modelo.addRow(new Object[] { marca.getIdmarca(),
					marca.getDescripcion() });
		}
		if (getMarcas().size() > 0) {
			setMarca(getMarcas().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setMarcas(mdao.findAll());

	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		txtNombreCorto.setEditable(true);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtNombreCorto.setEditable(false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		marca = (Marca) entidad;
		this.setMarca(marca);
		this.llenar_datos();
		this.vista_noedicion();

	}

	@Override
	public void nuevo() {
		setMarca(new Marca());
	}

	@Override
	public boolean isValidaVista() {

		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}

		if (getEstado().equals(NUEVO)) {
			if (getMdao().find(this.txtCodigo.getText().trim()) != null) {
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

		if (this.txtNombreCorto.getText().trim().isEmpty()) {

			this.txtNombreCorto.requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void eliminar() {
		if (getMarca() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");

			if (seleccion == 0) {
				getMdao().remove(getMarca());
				iniciar();
			}
		}
	}
}
