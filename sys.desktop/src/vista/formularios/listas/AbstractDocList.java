package vista.formularios.listas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import vista.Sys;
import vista.controles.ComboBox;
import vista.controles.DSGDatePicker;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTextFieldCorrelativo;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import core.entity.Documento;

public abstract class AbstractDocList extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ComboBox<Documento> cboDocumento = new ComboBox<Documento>();
	protected DSGTextFieldCorrelativo txtSerie;
	protected DSGTextFieldCorrelativo txtSerie1;
	private DSGDatePicker txtDesde;
	private DSGDatePicker txtHasta;
	protected JScrollPane pnlDocumentos = new JScrollPane();
	protected DSGTableList tblDocumentos;
	protected DSGTableModelList modelo_lista;

	private static final int _ancho = 20;
	private static final int _alto = 20;

	protected String[] cabeceras;
	protected Object[][] data;
	protected JLabel lblDocumento;
	private JTextField txtNumero;
	private JLabel label;
	protected String instancia;

	/**
	 * Crea la lista del documento con los filtros por defecto.
	 */
	public AbstractDocList(String titulo, String instancia) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		this.instancia = instancia;
		getContentPane().add(pnlDocumentos, BorderLayout.CENTER);
		JPanel pnlFiltros = new JPanel();

		pnlFiltros.setPreferredSize(new Dimension(0, 70));

		pnlFiltros
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(pnlFiltros, BorderLayout.NORTH);

		JLabel lblDesde = new JLabel("Desde");

		txtDesde = new DSGDatePicker();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);
		txtDesde.setDate(c.getTime());

		JLabel lblHasta = new JLabel("Hasta");

		txtHasta = new DSGDatePicker();
		txtHasta.setDate(new Date());

		lblDocumento = new JLabel("Documento");

		JLabel lblNmero = new JLabel("Correlativo");
		txtSerie1 = new DSGTextFieldCorrelativo(4);
		txtNumero = new DSGTextFieldCorrelativo(8);
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarLista();
			}
		});

		GroupLayout gl_pnlFiltros = new GroupLayout(pnlFiltros);
		gl_pnlFiltros
				.setHorizontalGroup(gl_pnlFiltros
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_pnlFiltros
										.createSequentialGroup()
										.addGap(22)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																txtDesde,
																GroupLayout.PREFERRED_SIZE,
																116,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblDesde,
																GroupLayout.PREFERRED_SIZE,
																45,
																GroupLayout.PREFERRED_SIZE))
										.addGap(26)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																txtHasta,
																GroupLayout.PREFERRED_SIZE,
																104,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblHasta,
																GroupLayout.PREFERRED_SIZE,
																37,
																GroupLayout.PREFERRED_SIZE))
										.addGap(36)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblDocumento)
														.addComponent(
																cboDocumento,
																GroupLayout.PREFERRED_SIZE,
																101,
																GroupLayout.PREFERRED_SIZE))
										.addGap(12)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNmero,
																GroupLayout.PREFERRED_SIZE,
																66,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtSerie1,
																GroupLayout.PREFERRED_SIZE,
																62,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addGap(9)
																		.addComponent(
																				txtNumero,
																				GroupLayout.PREFERRED_SIZE,
																				81,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																getLabel(),
																GroupLayout.PREFERRED_SIZE,
																23,
																GroupLayout.PREFERRED_SIZE))
										.addGap(57)
										.addComponent(btnActualizar,
												GroupLayout.PREFERRED_SIZE,
												101, GroupLayout.PREFERRED_SIZE)));
		gl_pnlFiltros
				.setVerticalGroup(gl_pnlFiltros
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_pnlFiltros
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblNmero)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																txtSerie1,
																GroupLayout.PREFERRED_SIZE,
																23,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtNumero,
																GroupLayout.PREFERRED_SIZE,
																23,
																GroupLayout.PREFERRED_SIZE))
										.addGap(11))
						.addGroup(
								Alignment.TRAILING,
								gl_pnlFiltros.createSequentialGroup()
										.addContainerGap(31, Short.MAX_VALUE)
										.addComponent(btnActualizar).addGap(13))
						.addGroup(
								gl_pnlFiltros
										.createSequentialGroup()
										.addGap(13)
										.addComponent(lblDesde)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(txtDesde,
												GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addGap(11))
						.addGroup(
								gl_pnlFiltros
										.createSequentialGroup()
										.addGap(11)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblHasta)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				txtHasta,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblDocumento)
																		.addGap(7)
																		.addComponent(
																				cboDocumento,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(12))
						.addGroup(
								gl_pnlFiltros.createSequentialGroup()
										.addGap(35).addComponent(getLabel())
										.addGap(18)));
		pnlFiltros.setLayout(gl_pnlFiltros);

		JPanel pnlOpciones = new JPanel();
		pnlOpciones.setPreferredSize(new Dimension(120, 0));
		pnlOpciones.setBorder(new TitledBorder(null, "Opciones",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		getContentPane().add(pnlOpciones, BorderLayout.WEST);
		pnlOpciones
				.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
						.decode("pref:grow"), }, new RowSpec[] {
						FormFactory.LINE_GAP_ROWSPEC, FormFactory.PREF_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC, FormFactory.PREF_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nuevo();
			}
		});
		pnlOpciones.add(btnCrear, "1, 2, fill, fill");

		btnCrear.setIcon(new ImageIcon(new ImageIcon(AbstractDocList.class
				.getResource("/main/resources/iconos/nuevo.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));

		JButton btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(AbstractDocList.class
				.getResource("/main/resources/iconos/editar_lista3.png")));
		btnEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		pnlOpciones.add(btnEditar, "1, 4");
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnImprimir
				.setIcon(new ImageIcon(new ImageIcon(AbstractDocList.class
						.getResource("/main/resources/iconos/printer.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));

		pnlOpciones.add(btnImprimir, "1, 6");

		JButton btnVer = new JButton("Abrir");
		pnlOpciones.add(btnVer, "1, 8, fill, fill");

		btnVer.setIcon(new ImageIcon(new ImageIcon(AbstractDocList.class
				.getResource("/main/resources/iconos/abrir.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnVer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ver();
			}
		});
		setBounds(100, 100, 763, 325);
	}

	public void llenarLista() {
		int idesde, ihasta, inumero = 0, anio_desde, mes_desde, dia_desde, anio_hasta, mes_hasta, dia_hasta;
		Calendar desde = Calendar.getInstance();
		if (txtDesde.getDate() == null) {
			anio_desde = 0;
			mes_desde = 0;
			dia_desde = 0;
		} else {
			desde.setTime(txtDesde.getDate());
			anio_desde = desde.get(Calendar.YEAR);
			mes_desde = desde.get(Calendar.MONTH) + 1;
			dia_desde = desde.get(Calendar.DAY_OF_MONTH);
		}
		Calendar hasta = Calendar.getInstance();
		if (txtHasta.getDate() == null) {
			anio_hasta = 0;
			mes_hasta = 0;
			dia_hasta = 0;
		} else {
			hasta.setTime(txtHasta.getDate());
			anio_hasta = hasta.get(Calendar.YEAR);
			mes_hasta = hasta.get(Calendar.MONTH) + 1;
			dia_hasta = hasta.get(Calendar.DAY_OF_MONTH);
		}
		idesde = (anio_desde * 10000) + (mes_desde * 100) + dia_desde;
		ihasta = (anio_hasta * 10000) + (mes_hasta * 100) + dia_hasta;
		String serie = this.txtSerie1.getText().trim().length() == 0 ? ""
				: this.txtSerie1.getText().trim();
		if (this.txtNumero.getText().trim().length() > 0)
			inumero = Integer.parseInt(this.txtNumero.getText().trim());
		modelo_lista.limpiar();
		for (Object[] data : getData(idesde, ihasta, serie, inumero)) {
			modelo_lista.addRow(data);
		}
	}
	
	public abstract Object getPK();

	private AbstractDocForm nuevaInstancia() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String urlClase = this.instancia;
		AbstractDocForm frame = (AbstractDocForm) Class.forName(urlClase)
				.newInstance();
		frame.setVisible(true);
		Sys.desktoppane.add(frame);
		try {
			frame.setSelected(true);
			frame.moveToFront();
		} catch (PropertyVetoException e) {
			frame = null;
			e.printStackTrace();
		}
		return frame;
	}

	public abstract Object[][] getData(int idesde, int ihasta, String serie,
			int numero);

	public void nuevo() {
		try {
			AbstractDocForm form = nuevaInstancia();
			form.DoNuevo();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void editar() {
		Object pk = getPK();
		if (pk != null) {
			try {
				AbstractDocForm form = nuevaInstancia();
				form.actualiza_objeto(pk);
				form.editar();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ver() {
		Object pk = getPK();
		if (pk != null) {
			try {
				AbstractDocForm form = nuevaInstancia();
				form.actualiza_objeto(pk);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("-");
		}
		return label;
	}
}
