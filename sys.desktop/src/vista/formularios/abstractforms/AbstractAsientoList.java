package vista.formularios.abstractforms;

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
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;

import vista.contenedores.CntSubdiario;
import vista.controles.DSGDatePicker;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTableList;
import vista.controles.DSGTableModelList;
import vista.controles.DSGTextFieldCorrelativo;
import vista.utilitarios.renderers.DDMMYYYYRenderer;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import core.entity.Subdiario;

public abstract class AbstractAsientoList extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DSGTextFieldCorrelativo txtNumero;
	protected JButton btnActualizar;

	protected DSGDatePicker txtDesde;
	protected DSGDatePicker txtHasta;
	private JScrollPane pnlDocumentos;
	protected DSGTableList tblDocumentos;

	protected DSGTableModelList docModel;
	// private List<Object[]> datos;

	private static final int _ancho = 20;
	private static final int _alto = 20;
	protected CntSubdiario cntSubdiario;

	protected String[] cabeceras = new String[] { "Periodo", "Fecha",
			"Subdiario", "Número", "Moneda", "Debe", "Haber", "Estado" };

	protected JLabel lblSubdiario;

	/**
	 * Crea la lista del documento con los filtros por defecto.
	 */
	public AbstractAsientoList(String titulo) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		// getContentPane().setLayout(new BorderLayout(0, 0));
		// calendar.set(Calendar.DATE, 1);

		tblDocumentos = new DSGTableList(7){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void DoDobleClick(int row) {
				AbstractAsientoList.this.AbrirFormulario(row, "VISTA");
			}
		};
		docModel = new DSGTableModelList(cabeceras);
		tblDocumentos.setModel(docModel);

		pnlDocumentos = new JScrollPane(tblDocumentos,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tblDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// .setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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
		txtDesde.setDate(c.getTime());// .setDate(Calendar.DAY_OF_MONTH);

		JLabel lblHasta = new JLabel("Hasta");

		txtHasta = new DSGDatePicker();

		txtHasta.setDate(new Date());
		// txtHasta.setDate(calendar.getTime());

		lblSubdiario = new JLabel("Subdiario");

		JLabel lblNmero = new JLabel("N\u00FAmero");

		txtNumero = new DSGTextFieldCorrelativo(10);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				llenarLista();
			}
		});

		cntSubdiario = new CntSubdiario();

		actualiza_tablas();
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
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				txtDesde,
																				GroupLayout.DEFAULT_SIZE,
																				91,
																				Short.MAX_VALUE)
																		.addGap(6))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblDesde,
																				GroupLayout.DEFAULT_SIZE,
																				45,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				txtHasta,
																				GroupLayout.DEFAULT_SIZE,
																				90,
																				Short.MAX_VALUE)
																		.addGap(6))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblHasta,
																				GroupLayout.DEFAULT_SIZE,
																				37,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				cntSubdiario,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(6))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblSubdiario,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				txtNumero,
																				GroupLayout.DEFAULT_SIZE,
																				126,
																				Short.MAX_VALUE)
																		.addGap(30)
																		.addComponent(
																				btnActualizar,
																				GroupLayout.DEFAULT_SIZE,
																				102,
																				Short.MAX_VALUE)
																		.addGap(6))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addComponent(
																				lblNmero,
																				GroupLayout.PREFERRED_SIZE,
																				0,
																				Short.MAX_VALUE)
																		.addContainerGap()))));
		gl_pnlFiltros
				.setVerticalGroup(gl_pnlFiltros
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_pnlFiltros
										.createSequentialGroup()
										.addGap(12)
										.addGroup(
												gl_pnlFiltros
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addGroup(
																				gl_pnlFiltros
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblHasta)
																						.addComponent(
																								lblDesde))
																		.addGap(4)
																		.addComponent(
																				txtDesde,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addGap(18)
																		.addComponent(
																				txtHasta,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_pnlFiltros
																		.createSequentialGroup()
																		.addGroup(
																				gl_pnlFiltros
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								lblNmero)
																						.addGroup(
																								gl_pnlFiltros
																										.createSequentialGroup()
																										.addComponent(
																												lblSubdiario)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)))
																		.addGroup(
																				gl_pnlFiltros
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_pnlFiltros
																										.createSequentialGroup()
																										.addGap(5)
																										.addComponent(
																												cntSubdiario,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								gl_pnlFiltros
																										.createSequentialGroup()
																										.addGap(4)
																										.addGroup(
																												gl_pnlFiltros
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																txtNumero,
																																GroupLayout.PREFERRED_SIZE,
																																23,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																btnActualizar))))))
										.addContainerGap(13, Short.MAX_VALUE)));
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

		btnCrear.setIcon(new ImageIcon(new ImageIcon(AbstractAsientoList.class
				.getResource("/main/resources/iconos/nuevo.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));

		JButton btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(AbstractAsientoList.class
				.getResource("/main/resources/iconos/editar_lista3.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tblDocumentos.getSelectedRow();
				if (row > -1) {
					row = tblDocumentos.convertRowIndexToModel(row);
					editar(row);
				}
			}
		});
		pnlOpciones.add(btnEditar, "1, 4");

		JButton btnImprimir = new JButton("Imprimir");

		btnImprimir
				.setIcon(new ImageIcon(new ImageIcon(AbstractAsientoList.class
						.getResource("/main/resources/iconos/printer.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imprimir();
			}
		});
		pnlOpciones.add(btnImprimir, "1, 6");

		JButton btnVer = new JButton("Abrir");
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tblDocumentos.getSelectedRow();
				if (row > -1) {
					row = tblDocumentos.convertRowIndexToModel(row);
					ver(row);
				}
			}
		});
		pnlOpciones.add(btnVer, "1, 8, fill, fill");

		btnVer.setIcon(new ImageIcon(new ImageIcon(AbstractAsientoList.class
				.getResource("/main/resources/iconos/abrir.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		setBounds(100, 100, 701, 325);
	}

	/*
	 * *
	 * Metodo que retorna cabeceras de la lista
	 * 
	 * @return Cabeceras que se mostrarán en la cabecera de la lista / public
	 * abstract String[] getCabeceras();
	 */
	/**
	 * Llena la lista del formulario con los datos de los documentos, usa un
	 * tipo de datos <b> List </b> que deberá ser llenado desde se tomará la
	 * posicion cero para el Objeto de tipo <b>ID</b> de la entidad, para poder
	 * llamarla en el formulario de edición una clase DAO que herede de
	 * <b>Abstract DAO</b> en caso de agregar más parametros sobreescribir esta
	 * clase.
	 */
	public void llenarLista() {
		Calendar desde, hasta;
		String numero;
		Subdiario subdiario;
		int numerador;
		desde = Calendar.getInstance();
		hasta = Calendar.getInstance();

		int anio_desde, mes_desde, dia_desde, anio_hasta, mes_hasta, dia_hasta, idesde, ihasta;

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

		idesde = anio_desde * 10000 + mes_desde * 100 + dia_desde;
		ihasta = anio_hasta * 10000 + mes_hasta * 100 + dia_hasta;

		numero = txtNumero.getText();
		subdiario = cntSubdiario.getSeleccionado();
		try {
			numerador = Integer.parseInt(numero);
		} catch (NumberFormatException nfe) {
			numerador = 0;
		}
		
		docModel.limpiar();
		for (Object [] data : getData(idesde, ihasta,
				numerador, subdiario)) {
			docModel.addRow(data);
		}
		
		 
		TableColumnModel tc = tblDocumentos.getColumnModel();
		tc.getColumn(1).setCellRenderer(new DDMMYYYYRenderer());
		tc.getColumn(0).setMinWidth(60);
		tc.getColumn(1).setMinWidth(75);
		tc.getColumn(2).setMinWidth(150);
		tc.getColumn(3).setMinWidth(90);
		tc.getColumn(4).setMinWidth(130);
		tc.getColumn(5).setMinWidth(150);
		tc.getColumn(6).setMinWidth(130);
		tc.getColumn(7).setMinWidth(40);

	}

	public abstract Object[][] getData(int idesde, int ihasta, int numero,
			Subdiario subdiario);

	public void init(AbstractDocForm obj, String opcion, Object entidad) {
		if (obj instanceof AbstractDocForm) {
			getDesktopPane().add(obj);
			if (opcion.equals("NUEVO"))
				obj.DoNuevo();
			if (opcion.equals("VISTA")) {
				obj.actualiza_objeto(entidad);
			}
			if (opcion.equals("EDICION")) {
				obj.actualiza_objeto(entidad);
				obj.editar();
			}
			try {
				obj.setSelected(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void moveToFront() {
		super.moveToFront();
		actualiza_tablas();
	}

	public abstract void actualiza_tablas();

	public abstract void nuevo();
	
	protected abstract void editar(int row);

	protected abstract void imprimir();

	protected abstract void ver(int row);
	
	
	public abstract void AbrirFormulario(int row, String opcion);
}
