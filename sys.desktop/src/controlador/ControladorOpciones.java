package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import com.scrollabledesktop.JScrollableDesktopPane;

public class ControladorOpciones {

	private JScrollableDesktopPane desktopPane;

	public ActionListener actionAbrirFormulario(final String opcion) {
		try {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						abrirFormulario(opcion);
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			};
		} catch (Exception e) {
			return null;
		}
	}

	private void abrirFormulario(String urlClase) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		JInternalFrame frame = (JInternalFrame) Class.forName(urlClase)
				.newInstance();
		frame.setVisible(true);
		getDesktopPane().add(frame);
		try {
			frame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public ActionListener returnAction(final String opcion) {
		try {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						iniciarFormulario(opcion);
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			};
		} catch (Exception e) {
			return null;
		}
	}

	private void iniciarFormulario(String clase) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String urlClase = "vista.formularios." + clase.trim();
		JInternalFrame frame = (JInternalFrame) Class.forName(urlClase)
				.newInstance();
		frame.setVisible(true);
		getDesktopPane().add(frame);
		try {
			frame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public JScrollableDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(JScrollableDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}
}
