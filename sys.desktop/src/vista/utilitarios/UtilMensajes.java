package vista.utilitarios;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vista.Sys;

public class UtilMensajes {
	private static String titulo = "BGC ERP";

	private static String getMensaje(String mensaje) {
		try {
			return Sys.mensajes.getProperty(mensaje);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Los Mensajes no cargaron correctamente, comunique con al Administrador del Sistema!",
							titulo, JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

	public static void mensaje_error(String mensaje) {

		JOptionPane.showMessageDialog(null, getMensaje(mensaje), titulo,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void mensaje_error(String mensaje, String... params) {
		String grandTotal = String.format(getMensaje(mensaje), params);
		JOptionPane.showMessageDialog(null, grandTotal, titulo,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void mensaje_alterta(String mensaje) {
		JOptionPane.showMessageDialog(null, getMensaje(mensaje), titulo,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void mensaje_alterta(JFrame frame, String mensaje) {
		JOptionPane.showMessageDialog(frame, getMensaje(mensaje), titulo,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void mensaje_alterta(String mensaje, String... params) {
		String grandTotal = String.format(getMensaje(mensaje), params);
		JOptionPane.showMessageDialog(null, grandTotal, titulo,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static int mensaje_sino(String mensaje) {
		int seleccion = JOptionPane.showOptionDialog(null, getMensaje(mensaje),
				titulo, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { "Si", "No" }, "Si");

		return seleccion;
	}

	public static int msj_error(String mensaje) {
		int seleccion = JOptionPane.showOptionDialog(null, getMensaje(mensaje),
				titulo, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { "Si", "No" }, "Si");

		return seleccion;
	}
}
