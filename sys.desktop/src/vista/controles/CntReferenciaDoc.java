package vista.controles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CntReferenciaDoc extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DSGTextFieldCorrelativo txtSerie;
	public DSGTextFieldCorrelativo txtNumero;

	private JPopupMenu refWindow;
	private JScrollPane scrollPane;
	private JTable table;
	private String[] cabeceras;
	private int[] anchos;
	private boolean editar;
	private Object[][] data;
	private JButton btnBuscar;
	private JButton btnVer;

	/**
	 * @wbp.parser.constructor
	 */
	public CntReferenciaDoc(String[] cabeceras, int[] anchos) {
		this.cabeceras = cabeceras;
		this.anchos = anchos;
		setForeground(Color.LIGHT_GRAY);
		this.setBounds(152, 11, 188, 25);
		GridBagLayout gridBagLayout = new GridBagLayout();

		gridBagLayout.columnWidths = new int[] { 46, 94, 20, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0, 0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		txtSerie = new DSGTextFieldCorrelativo(4);

		GridBagConstraints gbc_txtCodigo = new GridBagConstraints();
		// gbc_txtCodigo.insets = new Insets(0, 0, 0, 5);

		gbc_txtCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCodigo.gridx = 0;
		gbc_txtCodigo.gridy = 0;
		add(txtSerie, gbc_txtCodigo);

		txtNumero = new DSGTextFieldCorrelativo(8);
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		// gbc_txtDescripcion.insets = new Insets(0, 0, 0, 5);
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 0;
		add(txtNumero, gbc_txtDescripcion);

		this.btnBuscar = new JButton();
		this.btnBuscar.setIcon(new ImageIcon(new ImageIcon(getClass()
				.getResource("/main/resources/iconos/search.png")).getImage()
				.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)));

		btnBuscar.setMargin(new Insets(0, 0, 0, 0));

		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		// gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
		gbc_btnBuscar.anchor = GridBagConstraints.EAST;
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 0;
		add(btnBuscar, gbc_btnBuscar);

		btnVer = new JButton();
		this.btnVer.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(
				"/main/resources/iconos/ver_referencia.png")).getImage()
				.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)));

		this.btnVer.setMargin(new Insets(0, 0, 0, 0));

		GridBagConstraints gbc_btnVer = new GridBagConstraints();
		gbc_btnVer.gridx = 3;
		gbc_btnVer.gridy = 0;
		gbc_btnVer.anchor = GridBagConstraints.EAST;
		gbc_btnBuscar.gridx = 3;
		gbc_btnBuscar.gridy = 0;
		add(btnVer, gbc_btnVer);

		refWindow = new JPopupMenu();//JWindow((Window) Sys.mainF);
//		refWindow.setOpacity(0.95f);

		scrollPane = new JScrollPane();
//		refWindow.getContentPane().add(scrollPane);
		refWindow.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					if (row > -1) {
						refWindow.setVisible(false);
						row = table.convertRowIndexToModel(row);
						mostrarDetalleRef(data[row]);
					}
				}
			}
		});


		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscarReferencia();
			}
		});

		btnVer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (refWindow.isVisible()) {
//					refWindow.dispose();
				} else {
					mostrarReferencias();
				}
			}
		});
	}

	public CntReferenciaDoc() {
		this(new String[] { "Tipo Doc.", "Correlativo", "Fecha" }, new int[] {
				80, 120, 120 });
		setBorder(null);
	}

	public void mostrarReferencias() {
		this.data = getData();
		DefaultTableModel model = new DefaultTableModel(data, cabeceras) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		int ancho = 0;

		for (int i = 0; i < table.getColumnCount(); i++) {
			ancho += anchos[i];
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		table.setModel(model);
		
		refWindow.setMinimumSize(new Dimension(ancho, 170));
		refWindow.setMaximumSize(new Dimension(ancho, 170));
		refWindow.setPreferredSize(new Dimension(ancho, 170));
		refWindow.setSize(new Dimension(ancho, 170));
		refWindow.setBorder(null);
		refWindow.show(this, 0, this.getHeight());//.setVisible(true);
		refWindow.requestFocus();
		table.requestFocus();
	}

	public Object[][] getData() {
		System.out.println("Sobreescribir el método getData();");
		return null;
	}

	public void mostrarDetalleRef(Object[] row) {
		System.out
				.println("Sobreescribir el método mostrarReferencia(Object[] row);");
	}
	
	public void buscarReferencia() {
		System.out.println("Sobreescribir el método: buscarReferencia()");
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		btnBuscar.setEnabled(editar);
		txtNumero.setEditable(editar);
		txtSerie.setEditable(editar);
		this.editar = editar;
	}

}