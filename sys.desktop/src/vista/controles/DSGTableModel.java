package vista.controles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import vista.utilitarios.JTableUtils;
import vista.utilitarios.UtilMensajes;

public abstract class DSGTableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean editar = false;

	private String nombre_detalle = "";

	private int[] obligatorios;
	private int[] repetidos;

	private DSGVNDetalle[] valNumeros;

	private JScrollPane scrollPane;
	private JTable table;

	private String[] cabeceras;

	public DSGTableModel() {
		this("Campo 1", "Campo 2");
	}

	public DSGTableModel(String... cabeceras) {
		this.cabeceras = cabeceras;
		for (String c : cabeceras) {
			addColumn(c);
		}

	}

	public boolean isCellEditable(int row, int column) {
		return evaluaEdicion(row, column);
	}

	public abstract boolean evaluaEdicion(int row, int column);

	public void limpiar() {
		while (getRowCount() != 0) {
			removeRow(0);
		}
	}

	public boolean esValido() {
		if (obligatorios != null) {
			for (int i = 0; i < getRowCount(); i++) {
				for (int columna : obligatorios) {
					if (getValueAt(i, columna) == null
							|| getValueAt(i, columna).toString().isEmpty()) {
						UtilMensajes.mensaje_alterta("DETALLE_REQUERIDO",
								getNombre_detalle().toUpperCase(),
								this.cabeceras[columna].toUpperCase(), String
										.valueOf(i + 1).toString());
						return false;
					}
				}
			}
		}

		if (repetidos != null) {
			for (int i = 0; i < getRowCount(); i++) {
				String cad1 = getValoresUnidos(i, repetidos);
				for (int j = i + 1; j < getRowCount(); j++) {
					String cad2 = getValoresUnidos(j, repetidos);
					if (cad1.equals(cad2)) {
						UtilMensajes.mensaje_alterta(
								(repetidos.length == 1) ? "DETALLE_NOIGUAL"
										: "DETALLE_NOIGUALES",
								getNombre_detalle().toUpperCase(),
								getConcatenaCabeceras(this.repetidos), String
										.valueOf(i + 1).toString(), String
										.valueOf(j + 1).toString());
						return false;
					}
				}
			}

			if (valNumeros != null) {
				for (DSGVNDetalle val : valNumeros) {
					if (!val.validarModelo(this, cabeceras)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private String getValoresUnidos(int i, int[] columnas) {
		String cadena = "";
		for (int columna : columnas) {
			String valor = "";
			valor = (getValueAt(i, columna) == null) ? "" : getValueAt(i,
					columna).toString().trim();
			cadena = cadena.concat(valor);
		}
		return cadena;
	}

	private String getConcatenaCabeceras(int[] columnas) {
		String cadena = "";
		if (columnas.length == 1) {
			cadena = cabeceras[columnas[0]].toUpperCase();
		} else
			for (int i = 0; i < columnas.length; i++) {
				cadena = cadena.concat(((i == 0) ? ""
						: (i == columnas.length - 1) ? " y " : ", ")
						.concat(cabeceras[columnas[i]].toUpperCase()));
			}
		return cadena;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		int columnCount;
		// dataModel is an object of the data Model class(default or abstract)
		columnCount = getRowCount();
		if (columnCount <= 1) {
			return String.class;
		}
		try {
			return getValueAt(0, c).getClass();
		} catch (Exception e) {
			return String.class;
		}
	}

	@Override
	public void addRow(Object[] rowData) {
		super.addRow(rowData);
		refrescarRowHeader();
	}

	@Override
	public void removeRow(int row) {
		super.removeRow(row);
		refrescarRowHeader();
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public boolean getEditar() {
		return this.editar;
	}

	public String getNombre_detalle() {
		return nombre_detalle;
	}

	public void setNombre_detalle(String nombre_detalle) {
		this.nombre_detalle = nombre_detalle;
	}

	public void setObligatorios(int... obligatorios) {
		this.obligatorios = obligatorios;
	}

	public void setRepetidos(int... repetidos) {
		this.repetidos = repetidos;
	}

	public void setValidaNumero(DSGVNDetalle... validadores) {
		this.valNumeros = validadores;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void setScrollAndTable(JScrollPane scrollPane, JTable table) {
		this.scrollPane = scrollPane;
		this.table = table;
		refrescarRowHeader();
	}

	private void refrescarRowHeader() {
		if (getScrollPane() != null && getTable() != null) {
			getScrollPane().setRowHeaderView(
					JTableUtils.buildRowHeader(getTable(), this));
			JButton boton;
			getScrollPane().setCorner(JScrollPane.UPPER_LEFT_CORNER,
					boton = new JButton() {
						private static final long serialVersionUID = 1L;

						{

							addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (getEditar() && isValidaAgregar())
										addRow();
								}
							});
						}
					});
			boton.setIcon(new ImageIcon(new ImageIcon(DSGTableModel.class
					.getResource("/main/resources/iconos/mas.png")).getImage()
					.getScaledInstance(12, 12, java.awt.Image.SCALE_DEFAULT)));

			InputMap inputMap = getTable().getInputMap(
					JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			inputMap.put(insertarKey, "insertar");
			inputMap.put(borrarKey, "borrar");

			getTable().getActionMap().put("insertar", new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					if (getEditar())
						addRow();
				}
			});

			getTable().getActionMap().put("borrar", new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					int row = getTable().getSelectedRow();
					if (getEditar() && row > -1)
						remRow(row);
				}
			});

		}
	}

	KeyStroke insertarKey = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
			InputEvent.CTRL_MASK);

	KeyStroke borrarKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
			InputEvent.CTRL_MASK);

	public void remRow(int row) {
		if (isValidaEliminacion(row)) {
			int seleccion = JOptionPane.showOptionDialog(null,
					"Desea Eliminar el Registro Seleccionado",
					"Informacion del Sistema", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Si",
							"No" }, "Si");
			if (seleccion == 0) {
				removeRow(row);
			}
		}
	}
	
	
	public boolean isValidaAgregar() {
		return true;
	}

	public boolean isValidaEliminacion(int row) {
		return true;
	}

	public abstract void addRow();
}