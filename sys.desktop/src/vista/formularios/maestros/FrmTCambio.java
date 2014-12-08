package vista.formularios.maestros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;

import vista.Sys;
import vista.contenedores.CntMoneda;
import vista.controles.DSGTableModel;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.FormValidador;
import vista.utilitarios.ObjetoWeb;
import vista.utilitarios.TipoCambio;
import vista.utilitarios.renderers.DDMMYYYYRenderer;
import core.dao.MonedaDAO;
import core.dao.TCambioDAO;
import core.entity.Moneda;
import core.entity.TCambio;

public class FrmTCambio extends AbstractMaestro {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Calendar calendar = Calendar.getInstance();

	private List<String[]> optionList = new ArrayList<String[]>();
	protected CntMoneda cntMoneda;
	protected JLabel lblMoneda;
	protected JLabel lblAo;
	protected JLabel lblMes;
	protected JSpinner spinner;
	protected JComboBox<String> comboBox;
	protected JButton btnActualizar;
	protected JScrollPane scrollPane;
	private JTable tblDetalle;
	private TCambioDAO tcambioDAO = new TCambioDAO();
	private List<TCambio> tipocambio = new ArrayList<TCambio>();

	public FrmTCambio() {
		super("Tipo de Cambio");

		optionList.add(new String[] { "I", "Ingreso" });
		optionList.add(new String[] { "S", "Salida" });

		this.cntMoneda = new CntMoneda();

		this.lblMoneda = new JLabel("Moneda");

		this.lblAo = new JLabel("A\u00F1o");

		this.lblMes = new JLabel("Mes");

		this.spinner = new JSpinner();

		this.spinner.setValue(calendar.get(Calendar.YEAR));
		this.comboBox = new JComboBox<String>();

		this.comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
				"Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre" }));

		this.comboBox.setSelectedIndex(calendar.get(Calendar.MONTH));

		this.btnActualizar = new JButton("Actualizar");
		this.btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizar();
			}
		});
		tblDetalle = new JTable(new DSGTableModel(new String[] { "Fecha",
				"Compra", "Venta" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				return getEditar();
			}

			@Override
			public void addRow() {
				// TODO Auto-generated method stub

			}
		});

		TableColumnModel tc = tblDetalle.getColumnModel();
		tc.getColumn(0).setCellRenderer(new DDMMYYYYRenderer());

		if (Sys.moneda_ex != null) {
			cntMoneda.setSeleccionado(Sys.moneda_ex);
			cntMoneda.cargarDatos(cntMoneda.getSeleccionado());
			this.llenar();
		}

		this.comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenar();
			}
		});

		this.spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				llenar();
			}
		});
		this.cntMoneda.txtCodigo.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				llenar();
			}
		});

		this.scrollPane = new JScrollPane(tblDetalle);
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																this.scrollPane,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																519,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.lblMoneda)
																		.addGap(11)
																		.addComponent(
																				this.cntMoneda,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.lblAo,
																				GroupLayout.PREFERRED_SIZE,
																				38,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				this.spinner,
																				GroupLayout.PREFERRED_SIZE,
																				75,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				this.lblMes)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				this.comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				80,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				210,
																				Short.MAX_VALUE)
																		.addComponent(
																				this.btnActualizar)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																this.lblMoneda)
														.addComponent(
																this.cntMoneda,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																this.lblAo)
														.addComponent(
																this.spinner,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.lblMes)
														.addComponent(
																this.comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.btnActualizar))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(this.scrollPane,
												GroupLayout.DEFAULT_SIZE, 217,
												Short.MAX_VALUE)
										.addContainerGap()));
		pnlContenido.setLayout(groupLayout);
		iniciar();
	}

	protected void llenar() {
		int anio, mes, max;
		Moneda moneda;
		anio = Integer.valueOf(spinner.getValue().toString());
		mes = comboBox.getSelectedIndex() + 1;
		moneda = cntMoneda.getSeleccionado();
		tipocambio = new ArrayList<TCambio>();
		if (moneda == null) {
			llenar_detalle();
			return;
		}
		calendar.set(Calendar.YEAR, anio);
		calendar.set(Calendar.MONTH, mes - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= max; i++) {
			TCambio tc =  tcambioDAO.getFechaMoneda(anio, mes, i, moneda);

			if (tc == null) {
				tc = new TCambio();
				
				tc.setAnio(anio);
				tc.setMes(mes);
				tc.setDia(i);
				tc.setMoneda(moneda);
				
				tc.setCompra(0);
				tc.setVenta(0);
			}
			tipocambio.add(tc);

		}
		llenar_detalle();
	}

	protected void actualizar() {
		Moneda moneda = cntMoneda.getSeleccionado();
		if (moneda.getTipo() == 1) {
			int anio, mes;
			anio = Integer.valueOf(spinner.getValue().toString());
			mes = comboBox.getSelectedIndex() + 1;

			List<TipoCambio> tcambiosunat = ObjetoWeb.getTipoCambioSunat(anio,
					mes);
			for (TCambio t : tipocambio) {
				if (t.getCompra() == 0 || t.getVenta() == 0) {
					salir: for (TipoCambio t2 : tcambiosunat) {
						if (t2.getDia() == t.getDia()) {
							t.setCompra(t2.getCompra());
							t.setVenta(t2.getVenta());
							break salir;
						}
					}
				}
			}
			llenar_detalle();
		}
	}

	private void llenar_detalle() {
		getDetalleTM().limpiar();
		Calendar calendar = Calendar.getInstance();
		for (TCambio t : tipocambio) {
			calendar.set(Calendar.YEAR, t.getAnio());
			calendar.set(Calendar.MONTH, t.getMes() - 1);
			calendar.set(Calendar.DAY_OF_MONTH, t.getDia());
			getDetalleTM().addRow(
					new Object[] { calendar.getTime(), t.getCompra(),
							t.getVenta() });
		}

	}

	@Override
	public void DoNuevo() {

	};

	@Override
	public void nuevo() {

	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabar() {
		for (TCambio t : tipocambio) {
			tcambioDAO.crear_editar(t);
		}
	}

	@Override
	public void eliminar() {

	}

	public DSGTableModel getDetalleTM() {
		return (DSGTableModel) tblDetalle.getModel();
	}

	@Override
	public void llenar_datos() {

	}

	@Override
	public void llenar_lista() {

	}

	@Override
	public void llenar_tablas() {
		cntMoneda.setData(new MonedaDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		getDetalleTM().setEditar(true);
		comboBox.setEnabled(false);
		spinner.setEnabled(false);
		btnActualizar.setEnabled(true);
		FormValidador.CntEdicion(false, cntMoneda);
	}

	@Override
	public void vista_noedicion() {
		getDetalleTM().setEditar(false);
		comboBox.setEnabled(true);
		spinner.setEnabled(true);
		btnActualizar.setEnabled(false);
		FormValidador.CntEdicion(true, cntMoneda);
	}

	@Override
	public void limpiarVista() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualiza_objeto(Object entidad) {

	}

	@Override
	public void llenarDesdeVista() {
		int rows = tblDetalle.getRowCount();
		Calendar cal = Calendar.getInstance();
		Moneda moneda = cntMoneda.getSeleccionado();
		tipocambio = new ArrayList<TCambio>();
		for (int i = 0; i < rows; i++) {
			TCambio tcambio = new TCambio();
			
			cal.setTime((Date) getDetalleTM().getValueAt(i, 0));
			tcambio.setAnio(cal.get(Calendar.YEAR));
			tcambio.setMes(cal.get(Calendar.MONTH) + 1);
			tcambio.setDia(cal.get(Calendar.DAY_OF_MONTH));
			tcambio.setMoneda(moneda);
			tcambio.setCompra(Float.valueOf(getDetalleTM().getValueAt(i, 1)
					.toString()));
			tcambio.setVenta(Float.valueOf(getDetalleTM().getValueAt(i, 2)
					.toString()));
			tipocambio.add(tcambio);
		}
	}

	@Override
	public boolean isValidaVista() {

		return true;
	}
}
