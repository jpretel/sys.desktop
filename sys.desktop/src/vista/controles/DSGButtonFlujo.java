package vista.controles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import core.entity.Flujo;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class DSGButtonFlujo extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Flujo flujo;

	public DSGButtonFlujo() {
		setFont(new Font("Tahoma", Font.BOLD, 13));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setText("Ninguno");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showMenu();
			}
		});
	}

	public void showMenu() {
		JPopupMenu menu = getMenu();
		menu.show(this, 0, this.getHeight());
	}

	public Flujo getFlujo() {
		return flujo;
	}

	public void setFlujo(Flujo flujo) {
		if (flujo != null) {
			setText(flujo.getDescripcion());
		} else {
			setText("Ninguno");
		}
		this.flujo = flujo;
	}

	public Flujo getFlujoAnterior() {
		System.out
				.println("Sobreescribir flujoAnterior() para mostrar Flujo anterior");
		return null;
	}

	public Flujo getFlujoSiguiente() {
		System.out
				.println("Sobreescribir flujoSiguiente() para mostrar Flujo siguiente");
		return null;
	}

	protected JPopupMenu getMenu() {

		Flujo anterior = getFlujoAnterior();
		Flujo siguiente = getFlujoSiguiente();

		JPopupMenu menu = new JPopupMenu();
		
		JMenuItem item;
		menu.add(item = new JMenuItem("Ver Hist. de Aprobación"));

		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Aún falta implementar...");
			}
		});

		if (anterior != null) {
			menu.add(item = new JMenuItem(anterior.getDescripcion()));
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					actualizaEstado(anterior);
				}
			});
		}

		if (siguiente != null) {
			menu.add(item = new JMenuItem(siguiente.getDescripcion()));
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					actualizaEstado(siguiente);
				}
			});
		}

		return menu;
	}

	public void actualizaEstado(Flujo flujo) {
		System.out.println("Sobreescribir actualizaEstado(Flujo flujo);");
	}
}
