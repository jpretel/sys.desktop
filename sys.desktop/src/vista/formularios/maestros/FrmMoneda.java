package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import vista.utilitarios.UtilMensajes;
import core.dao.MonedaDAO;
import core.entity.Moneda;

public class FrmMoneda extends AbstractMaestro {

	private static final long serialVersionUID = 1L;

	private Moneda moneda;

	private MonedaDAO monedaDAO = new MonedaDAO();

	private List<Moneda> monedas = new ArrayList<Moneda>();
	private JTable tblLista;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private final ButtonGroup grpTipoMoneda = new ButtonGroup();
	private JTextField txtSimbolo;
	private JRadioButton optNac;
	private JRadioButton optExt;
	private JRadioButton optOtra;

	public FrmMoneda() {
		super("Monedas");

		getBarra().setFormMaestro(this);

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

		optNac = new JRadioButton("Moneda Nacional");
		optNac.setSelected(true);
		optNac.setMnemonic(0);
		grpTipoMoneda.add(optNac);

		optExt = new JRadioButton("Primera moneda Extranjera");
		optExt.setMnemonic(1);
		grpTipoMoneda.add(optExt);

		optOtra = new JRadioButton("Otra Moneda");
		optOtra.setMnemonic(2);
		grpTipoMoneda.add(optOtra);

		JLabel lblSimbolo = new JLabel("Simbolo");

		txtSimbolo = new JTextField();
		txtSimbolo.setColumns(10);
		txtSimbolo.setDocument(new JTextFieldLimit(10, true));
		
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCdigo)
							.addGap(26)
							.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDescripcin)
							.addGap(5)
							.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblSimbolo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(txtSimbolo, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
							.addGap(112))
						.addComponent(optNac)
						.addComponent(optExt)
						.addComponent(optOtra, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
					.addGap(11))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCdigo)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblDescripcin))
						.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblSimbolo))
						.addComponent(txtSimbolo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(optNac)
					.addGap(3)
					.addComponent(optExt)
					.addGap(3)
					.addComponent(optOtra))
		);
		pnlContenido.setLayout(groupLayout);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setMoneda(getMonedas().get(selectedRow));
						else
							setMoneda(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setMoneda(new Moneda());
		this.txtCodigo.requestFocus();
	}

	@Override
	public void anular() {
		vista_noedicion();
	}

	@Override
	public void grabar() {
		getMonedaDAO().crear_editar(getMoneda());
	}

	@Override
	public void llenarDesdeVista() {
		getMoneda().setIdmoneda(txtCodigo.getText());
		getMoneda().setDescripcion(txtDescripcion.getText());
		getMoneda().setSimbolo(txtSimbolo.getText());
		getMoneda().setTipo(grpTipoMoneda.getSelection().getMnemonic());
	};

	@Override
	public void eliminar() {
		if (getMoneda() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				getMonedaDAO().remove(getMoneda());
				iniciar();
			}			
		}
		setEstado(VISTA);
		vista_noedicion();
	}

	@Override
	public void llenar_datos() {
		if (getMoneda() != null) {
			txtCodigo.setText(getMoneda().getIdmoneda());
			txtDescripcion.setText(getMoneda().getDescripcion());
			txtSimbolo.setText(getMoneda().getSimbolo());
			switch (getMoneda().getTipo()) {
			case 0:
				optNac.setSelected(true);
				break;
			case 1:
				optExt.setSelected(true);
				break;
			default:
				optOtra.setSelected(true);
				break;
			}
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtSimbolo.setText("");
		}
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Moneda obj : getMonedas()) {
			model.addRow(new Object[] { obj.getIdmoneda(), obj.getDescripcion() });
		}
		if (getMonedas().size() > 0) {
			setMoneda(getMonedas().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setMonedas(getMonedaDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		txtDescripcion.setEditable(true);
		txtSimbolo.setEditable(true);
		Enumeration<AbstractButton> opts = grpTipoMoneda.getElements();
		while (opts.hasMoreElements()) {
			opts.nextElement().setEnabled(true);
		}
		
		
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		txtSimbolo.setEditable(false);
		Enumeration<AbstractButton> opts = grpTipoMoneda.getElements();
		while (opts.hasMoreElements()) {
			opts.nextElement().setEnabled(false);
		}
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
			if (getMonedaDAO().find(this.txtCodigo.getText().trim()) != null) {
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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public MonedaDAO getMonedaDAO() {
		return monedaDAO;
	}

	public void setMonedaDAO(MonedaDAO monedaDAO) {
		this.monedaDAO = monedaDAO;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}
}
