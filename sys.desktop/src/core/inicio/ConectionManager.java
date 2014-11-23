package core.inicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConectionManager {

	public static String MYSQL = "MYSQL";
	public static String SQLSERVER = "SQLSERVER";
	public static String POSTGRES = "POSTGRES";

	public static boolean isConexionOK(SysCfgInicio cfgInicio, JFrame frame) {

		if (cfgInicio.getGestor().equals(MYSQL)) {
			return verificaMySql(cfgInicio, frame);
		}
		if (cfgInicio.getGestor().equals(SQLSERVER)) {
			return verificaSQL(cfgInicio, frame);
		}
		if (cfgInicio.getGestor().equals(POSTGRES)) {
			return verificaPostgres(cfgInicio, frame);
		}

		return verificaMySql(cfgInicio, frame);
	}

	private static boolean verificaMySql(SysCfgInicio cfgInicio, JFrame frame) {
		Connection conexion = null;
		boolean isExito = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(cfgInicio.getURL(),
					cfgInicio.getUsuario(), cfgInicio.getClave());
			isExito = true;
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(frame,
					"No se encontró driver para MYSQL");
			return false;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(frame,
					"Error al conectar con el servidor");
			JOptionPane.showMessageDialog(frame, ex.getMessage());
			return false;
		}

		try {
			conexion.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame,
					"Error al desconectar con el servidor");
			JOptionPane.showMessageDialog(frame, ex.getMessage());
			return false;
		}
		return isExito;
	}

	private static boolean verificaSQL(SysCfgInicio cfgInicio, JFrame frame) {
		Connection conexion = null;
		boolean isExito = false;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexion = DriverManager.getConnection(cfgInicio.getURL(),
					cfgInicio.getUsuario(), cfgInicio.getClave());
			isExito = true;
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(frame,
					"No se encontró driver para MYSQL");
			return false;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(frame,
					"Error al conectar con el servidor");
			JOptionPane.showMessageDialog(frame, ex.getMessage());
			return false;
		}

		try {
			conexion.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame,
					"Error al desconectar con el servidor");
			JOptionPane.showMessageDialog(frame, ex.getMessage());
			isExito = false;
		}
		return isExito;
	}

	private static boolean verificaPostgres(SysCfgInicio cfgInicio, JFrame frame) {
		return false;
	}
}
