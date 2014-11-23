/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @version 28/06/2007
 * @author BorSanZa - Borja Sánchez Zamorano (borja.sanchez@iver.es)
 */
public class ListViewComponent extends JComponent implements MouseListener, MouseMotionListener, ActionListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 6177600314634665863L;

	/**
	 * Lista de los tipos de vista existentes
	 */
	private ArrayList   painters       = new ArrayList();
	private ArrayList   paintersMenu   = new ArrayList();

	/**
	 * Lista de items
	 */
	private ArrayList   items          = new ArrayList();

	/**
	 * Selecciona el tipo de vista
	 */
	private int         view           = 0;

	/**
	 * Booleano para saber si se permite la multiselección
	 */
	private boolean     multiSelect    = false;

	private Image       image          = null;
	private int         width          = 0;
	private int         height         = 0;
	private Graphics2D  widgetGraphics = null;
	private JMenu       jMenu          = null;
	private ButtonGroup buttonGroup    = null;
	private JPopupMenu  jPopupMenu     = null;

	private JTextField  jRenameEdit    = null;


	private int         itemEdited     = -1;
	private int         lastSelected   = -1;
	private int         cursorPos      = -1;


	private boolean     editable       = false;

	private ArrayList   actionCommandListeners = new ArrayList();


	/**
	 * Construye un <code>ListViewComponent</code>
	 *
	 */
	public ListViewComponent() {
		setFocusable(true);

		initialize();
	}

	/**
	 * Inicializa el <code>ListViewComponent</code>
	 */
	private void initialize() {
		addListViewPainter(new PaintList(items));
		addListViewPainter(new SmallIcon(items));
		addListViewPainter(new LargeIcon(items));

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Obtiene que vista se esta usando en el componente
	 * @return
	 */
	public int getView() {
		return view;
	}

	/**
	 * Define que vista es la que se va a usar
	 * @param view
	 */
	public void setView(int view) {
		this.view = view;
	}

	/**
	 * Agrega una vista al componente
	 * @param item
	 */
	public void addListViewPainter(IListViewPainter item) {
		painters.add(item);

		JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem();
		getButtonGroup().add(jRadioButtonMenuItem);

		jRadioButtonMenuItem.setText(item.getName());
		if (paintersMenu.size() == 0)
			jRadioButtonMenuItem.setSelected(true);
		getJMenu().add(jRadioButtonMenuItem);

		jRadioButtonMenuItem.addActionListener(this);

		paintersMenu.add(jRadioButtonMenuItem);
	}

	/**
	 * Agrega un item al componente
	 * @param item
	 */
	public void addItem(ListViewItem item) {
		addItem(item, false);
	}

	/**
	 * Agrega un item al componente, si acceptRepeatNames es false no se aceptaran
	 * nombres repetidos
	 * @param item
	 * @param acceptRepeatNames
	 */
	public void addItem(ListViewItem item, boolean acceptRepeatNames) {
		items.add(item);
		if (!acceptRepeatNames)
			changeName(item.getName(), items.size() - 1);
		repaint();
		viewItem(items.size() - 1);
	}

	/**
	 * Agrega el item en la posicion especificada de la lista.
	 * @param pos
	 * @param item
	 */
	public void addItem(int pos, ListViewItem item) {
		items.add(pos, item);
		changeName(item.getName(), pos);
		repaint();
		viewItem(pos);
	}

	/**
	 * Agrega un item al componente
	 * @param item
	 */
	public void removeItem(int index) {
		items.remove(index);
		repaint();
	}

	/**
	 * Borra todos los items seleccionados
	 */
	public void removeSelecteds() {
		for (int i = (items.size()-1); i>=0; i--)
			if (((ListViewItem) items.get(i)).isSelected())
				items.remove(i);

		repaint();
	}

	/**
	 * Devuelve un ArrayList con todos los items
	 * @return
	 */
	public ArrayList getItems() {
		return items;
	}

	private Graphics2D getWidgetGraphics() {
		getWidgetImage();
		return widgetGraphics;
	}

	private Image getWidgetImage() {
		int width2 = getVisibleRect().width;
		int height2 = getVisibleRect().height;
		if (width2 <= 0)
			width2 = 1;
		if (height2 <= 0)
			height2=1;

		if ((width != width2) || (height != height2)) {
			image = createImage(width2, height2);
			if (image == null)
				return null;
			widgetGraphics = (Graphics2D) image.getGraphics();
		}

		width = width2;
		height = height2;
		return image;
	}

	/**
	 * Redibujar el componente en el graphics temporal
	 */
	private void redrawBuffer() {
		getWidgetGraphics().translate(-getVisibleRect().x, -getVisibleRect().y);
		getWidgetGraphics().setColor(Color.white);
		getWidgetGraphics().fillRect(getVisibleRect().x, getVisibleRect().y, getVisibleRect().width, getVisibleRect().height);

		((IListViewPainter) painters.get(view)).paint((Graphics2D) getWidgetGraphics(), getVisibleRect());
		getWidgetGraphics().translate(getVisibleRect().x, getVisibleRect().y);
	}

	public void paint(Graphics g) {

		redrawBuffer();

		if (image != null)
			g.drawImage(image, getVisibleRect().x, getVisibleRect().y, this);

		Dimension size = getPreferredSize();
		Dimension aux = ((IListViewPainter) painters.get(view)).getPreferredSize();
		if (!size.equals(aux)) {
			setPreferredSize(aux);
			setSize(aux);
		}
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		if (multiSelect == false) {
			for (int i = 0; i<items.size(); i++)
				((ListViewItem) items.get(i)).setSelected(false);
			if ((lastSelected != -1) && (lastSelected < items.size()))
				((ListViewItem) items.get(lastSelected)).setSelected(true);
		}

		this.multiSelect = multiSelect;
		repaint();
	}

	private int getItem(int x, int y) {
		Point point = new Point(x, y);
		Rectangle rectangle = null;
		for (int i = 0; i < items.size(); i++) {
			rectangle = ((ListViewItem) items.get(i)).getItemRectangle();
			if ((rectangle != null) && (rectangle.getBounds().contains(point)))
				return i;
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		requestFocus();

		try {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK)
				return;

			cursorPos = getItem(e.getX(), e.getY());
			if (cursorPos == -1)
				return;

			if (isMultiSelect()) {
				if ((e.getModifiers() & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK) {
					int pos1 = cursorPos;
					int pos2 = lastSelected;
					if (pos2 < pos1) {
						pos1 = lastSelected;
						pos2 = cursorPos;
					}

					if ((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK)
						for (int i = 0; i < items.size(); i++)
							((ListViewItem) items.get(i)).setSelected(false);

					for (int i = pos1; i <= pos2; i++)
						((ListViewItem) items.get(i)).setSelected(true);
					return;
				}

				lastSelected = cursorPos;

				if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
					((ListViewItem) items.get(cursorPos)).setSelected(!((ListViewItem) items.get(cursorPos)).isSelected());
					return;
				}

				for (int i = 0; i < items.size(); i++)
					((ListViewItem) items.get(i)).setSelected(false);

				((ListViewItem) items.get(cursorPos)).setSelected(true);
			} else {
				boolean selected = true;

				lastSelected = cursorPos;

				if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK)
					selected = !((ListViewItem) items.get(cursorPos)).isSelected();

				for (int i = 0; i < items.size(); i++)
					((ListViewItem) items.get(i)).setSelected(false);

				((ListViewItem) items.get(cursorPos)).setSelected(selected);
			}
		} finally {
			repaint();
		}
	}

	/**
	 * Establece que un item debe estar visible en la vista
	 * @param pos
	 */
	private void viewItem(int pos) {
		if (getParent() instanceof JViewport) {
			JViewport jViewport = (JViewport) getParent();

			if (jViewport.getParent() instanceof JScrollPane) {
				ListViewItem lvi = ((ListViewItem) items.get(pos));
				Rectangle rectangle = lvi.getItemRectangle();
				if (rectangle == null)
					return;
				rectangle.setLocation((int) rectangle.getX() - getVisibleRect().x, (int) rectangle.getY() - getVisibleRect().y);
				jViewport.scrollRectToVisible(rectangle);
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK)
			return;

		if (isMultiSelect()) {
			if ((e.getModifiers() & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK)
				return;
			if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK)
				return;
		}

		int itemSelected = getItem(e.getX(), e.getY());

		if (itemSelected == -1)
			return;

		lastSelected = itemSelected;
		cursorPos = itemSelected;

		for (int i = 0; i<items.size(); i++)
			((ListViewItem) items.get(i)).setSelected(false);

		((ListViewItem) items.get(itemSelected)).setSelected(true);

		repaint();

		viewItem(itemSelected);
	}

	private JPopupMenu getPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			getJMenu().setText("View");

			jPopupMenu.add(getJMenu());
		}
		return jPopupMenu;
	}

	public void mouseReleased(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)
			getPopupMenu().show (this, e.getX (), e.getY () );

		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			fireSelectionValueChanged(1, 1, false);
		}
	}

	public void mouseMoved(MouseEvent e) {
		int itemSelected = getItem(e.getX(), e.getY());

		if (itemSelected == -1) {
			setToolTipText(null);
			return;
		}
		if (((ListViewItem) items.get(itemSelected)).isShowTooltip())
			setToolTipText(((ListViewItem) items.get(itemSelected)).getName());
		else
			setToolTipText(null);
	}

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null)
			buttonGroup = new ButtonGroup();
		return buttonGroup;
	}

	private JMenu getJMenu() {
		if (jMenu == null)
			jMenu = new JMenu();
		return jMenu;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		int pos = paintersMenu.indexOf(e.getSource());
		view = pos;
		repaint();
	}

	/**
	 * Returns an array of the values for the selected cells. The returned values
	 * are sorted in increasing index order.
	 *
	 * @return the selected values or an empty list if nothing is selected
	 */
	public ListViewItem[] getSelectedValues() {
		int cont = 0;
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected())
				cont++;
		}
		ListViewItem[] values = new ListViewItem[cont];
		cont = 0;
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected()) {
				values[cont] = (ListViewItem) items.get(i);
				cont++;
			}
		}

		return values;
	}

	/**
	 * Returns the first selected index; returns -1 if there is no selected item.
	 *
	 * @return the value of <code>getMinSelectionIndex</code>
	 */
	public int getSelectedIndex() {
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected())
				return i;
		}
		return -1;
	}


	/**
	 * Select the index value
	 *
	 * @return the value of <code>getMinSelectionIndex</code>
	 */
	public void setSelectedIndex(int value) {
		for (int i = 0; i < items.size(); i++) {
			((ListViewItem) items.get(i)).setSelected(i == value);
		}
	}

	/**
	 * Returns the first selected value, or <code>null</code> if the selection
	 * is empty.
	 *
	 * @return the first selected value
	 */
	public ListViewItem getSelectedValue() {
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected())
				return (ListViewItem) items.get(i);
		}
		return null;
	}

	/**
	 * Returns an array of all of the selected indices in increasing order.
	 *
	 * @return all of the selected indices, in increasing order
	 */
	public int[] getSelectedIndices() {
		int cont = 0;
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected())
				cont++;
		}
		int[] values = new int[cont];
		cont = 0;
		for (int i = 0; i < items.size(); i++) {
			if (((ListViewItem) items.get(i)).isSelected()) {
				values[cont] = i;
				cont++;
			}
		}

		return values;
	}

	/**
	 * Añadir un listener a la lista de eventos
	 * @param listener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		if (!actionCommandListeners.contains(listener))
			actionCommandListeners.add(listener);
	}

	/**
	 * Borrar un listener de la lista de eventos
	 * @param listener
	 */
	public void removeListSelectionListener(ListSelectionListener listener) {
		actionCommandListeners.remove(listener);
	}

	/**
	 * Invocar a los eventos asociados al componente
	 */
	private void fireSelectionValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
		Iterator acIterator = actionCommandListeners.iterator();
		ListSelectionEvent e = null;
		while (acIterator.hasNext()) {
			ListSelectionListener listener = (ListSelectionListener) acIterator.next();
			if (e == null)
				e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
			listener.valueChanged(e);
		}
	}

	public void renameItem(int item) {
		if (!isEditable())
			return;

		if ((item >= 0) && (item < items.size())) {
			if (((ListViewItem) items.get(item)).isSelected()) {
				Rectangle rectangle = ((ListViewItem) items.get(item)).getNameRectangle();

				if (rectangle != null) {
					itemEdited = item;
					((ListViewItem) items.get(itemEdited)).setSelected(false);
					repaint();
					this.setLayout(null);
					getJRenameEdit().setText(((ListViewItem) items.get(item)).getName());
					this.add(getJRenameEdit());
					getJRenameEdit().setBounds(rectangle);
					getJRenameEdit().addFocusListener(this);
					getJRenameEdit().addKeyListener(this);
					getJRenameEdit().requestFocus();
					getJRenameEdit().setSelectionStart(0);
					getJRenameEdit().setSelectionEnd(getJRenameEdit().getText().length());
				}
			}
		}
	}

	public JTextField getJRenameEdit() {
		if (jRenameEdit == null) {
			jRenameEdit = new JTextField();
		}
		return jRenameEdit;
	}

	public void changeName(String newName, int pos) {
		if (newName.length() == 0)
			return;
		String newNameAux = newName;
		boolean isItem;
		int newNumber = 0;
		do {
			isItem = false;
			for (int i = 0; i < items.size(); i++) {
				if ((i != pos) && (((ListViewItem) items.get(i)).getName().equals(newNameAux))) {
					isItem = true;
					newNumber++;
					newNameAux = newName + "_" + newNumber;
					break;
				}
			}
		} while (isItem);
		((ListViewItem) items.get(pos)).setName(newNameAux);
	}

	public void closeRenameEdit() {
		if (jRenameEdit == null)
			return;

		if (itemEdited != -1) {
			changeName(getJRenameEdit().getText(), itemEdited);

			((ListViewItem) items.get(cursorPos)).setSelected(true);
			itemEdited = -1;
			repaint();
		}
		this.remove(getJRenameEdit());
		jRenameEdit = null;
		this.requestFocus();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getSource() == getJRenameEdit()) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					getJRenameEdit().setText(((ListViewItem) items.get(itemEdited)).getName());
					closeRenameEdit();
					break;
				case KeyEvent.VK_ENTER:
					closeRenameEdit();
					break;
			}

			return;
		}
		if (e.getSource() == this) {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				renameItem(cursorPos);
			}
		}
	}

	public void focusLost(FocusEvent e) {
		closeRenameEdit();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this)
			// Si es doble click y hay algún elemento seleccionado en la lista lo eliminamos
			if (e.getClickCount() == 2) {
				renameItem(cursorPos);
			}
	}

	/**
	 * Devuelve si se puede cambiar el nombre de los items
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Define si se puede cambiar el nombre de los items
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void focusGained(FocusEvent e) {}
}