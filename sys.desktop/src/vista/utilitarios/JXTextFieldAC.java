package vista.utilitarios;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import vista.controles.IBusqueda;

public class JXTextFieldAC extends JXTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	private List<IBusqueda> data;
	private List<SugerenciaBusqueda> sugerencias = new ArrayList<SugerenciaBusqueda>();
	private JWindow autoSuggestionPopUpWindow;
	private JTable table;
	private JScrollPane scrollPane;
	private int indice = -1;
	private IBusqueda seleccionado;
	private int iSeleccionado;
	private int[] anchos;
	private String[] cabeceras;

	public JXTextFieldAC(Window mainWindow, String[] cabeceras, int [] anchos,
			List<IBusqueda> data) {
		this.anchos = anchos;
		this.data = data;
		this.cabeceras = cabeceras;
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {
				checkForAndShowSuggestions();
			}

			@Override
			public void removeUpdate(DocumentEvent de) {
				checkForAndShowSuggestions();
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				checkForAndShowSuggestions();
			}
		});
		indice = -1;
		autoSuggestionPopUpWindow = new JWindow(mainWindow);
		autoSuggestionPopUpWindow.setOpacity(0.8f);

		scrollPane = new JScrollPane();
		autoSuggestionPopUpWindow.add(scrollPane);
		table = new JTable();
		new DefaultTableModel(cabeceras, 0);

		scrollPane.setViewportView(table);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					indice = table.getSelectedRow();
					if(validarDatos())
					autoSuggestionPopUpWindow.setVisible(false);
				}
			}
		});

		table.addFocusListener(this);
		addFocusListener(this);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == 40) {
					table.requestFocus();
					if (table.getSelectedRow() == -1
							&& autoSuggestionPopUpWindow.isVisible()) {
					}
				}

				if (ev.getKeyCode() == 27) {
					autoSuggestionPopUpWindow.setVisible(false);
				}

				if (ev.getModifiers() == 2 && ev.getKeyCode() == 70) {
					checkForAndShowSuggestions();
				}

			}
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ev) {

				if (ev.getKeyCode() == 10) {
					indice = table.getSelectedRow();
					validarDatos();
				}
				if (ev.getKeyCode() == 38 && table.getSelectedRow() == 0) {
					JXTextFieldAC.this.requestFocus();
				}
			}
		});

	}

	public void checkForAndShowSuggestions() {
		String typedWord = getText();
		sugerencias = new ArrayList<SugerenciaBusqueda>();
		indice = -1;
		boolean added = wordTyped(typedWord);
		if (!added) {
			indice = -1;
			autoSuggestionPopUpWindow.setVisible(false);
		} else {
			showPopUpWindow();
		}
	}

	public boolean wordTyped(String typedWord) {
		if (typedWord.isEmpty() || typedWord.length() < getMinimoBusqueda()) {
			return false;
		}

		boolean haySugerencias = false;

		for (int i = 0; i < data.size(); i++) {
			IBusqueda b = data.get(i);
			if (b.coincideBusqueda(typedWord)) {
				sugerencias.add(new SugerenciaBusqueda(i, b.datoBusqueda()));
				haySugerencias = true;
			}
		}

		return haySugerencias;
	}

	public int getMinimoBusqueda() {
		return 3;
	}

	private void showPopUpWindow() {

		Object[][] data = new Object[sugerencias.size()][2];

		for (int i = 0; i < sugerencias.size(); i++) {
			SugerenciaBusqueda s = sugerencias.get(i);
			data[i] = s.dato;
		}

		DefaultTableModel model = new DefaultTableModel(data, cabeceras) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setModel(model);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		int windowX = getLocationOnScreen().x;
		int windowY = getLocationOnScreen().y + getHeight();

		autoSuggestionPopUpWindow.setLocation(windowX, windowY);
		autoSuggestionPopUpWindow.setMinimumSize(new Dimension(this.getParent().getWidth(), anchos[1]));
		autoSuggestionPopUpWindow.setAutoRequestFocus(false);
		autoSuggestionPopUpWindow.setVisible(true);

	}

	private boolean validarDatos() {
		iSeleccionado = -1;
		if (indice > -1 && sugerencias.size() > 0
				&& indice <= sugerencias.size()) {
			iSeleccionado = sugerencias.get(indice).indice;
			seleccionado = data.get(iSeleccionado);
			cargaDatos();
			return true;
		} else {
			return false;
		}
	}

	public void cargaDatos() {
		setText(seleccionado.toString());
	}

	public IBusqueda getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(IBusqueda seleccionado) {
		this.seleccionado = seleccionado;
	}

	public int getiSeleccionado() {
		return iSeleccionado;
	}

	public void setiSeleccionado(int iSeleccionado) {
		this.iSeleccionado = iSeleccionado;
	}

	@Override
	public void focusGained(FocusEvent e) {
		revisaFoco('G', e);
	}

	@Override
	public void focusLost(FocusEvent e) {
		revisaFoco('L', e);
	}

	private void revisaFoco(char tipo, FocusEvent e) {
		boolean band = false;
		if (tipo == 'G') {
			if (e.getComponent() == table) {
				band = true;
			}
		} else {
			if ((e.getComponent() == table && e.getOppositeComponent() == this)
					|| (e.getComponent() == this && e.getOppositeComponent() == table)) {
				band = true;
			}
		}
		if (!band) {
			autoSuggestionPopUpWindow.setVisible(false);
		}
	}
}

class SugerenciaBusqueda {
	public int indice;
	public String[] dato;

	public SugerenciaBusqueda(int indice, String[] dato) {
		this.indice = indice;
		this.dato = dato;
	}
}
