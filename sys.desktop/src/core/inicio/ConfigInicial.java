package core.inicio;

import static core.security.Encryption.decrypt;
import static core.security.Encryption.encrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import vista.Sys;



public class ConfigInicial {
	
	public static String cfg_servidor;
	public static String cfg_basedatos;
	public static String cfg_url;
	public static String cfg_usuario;
	public static String cfg_clave;
	
	public static String[] LlenarConfig() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			prop.load(input);

			String servidor = prop.getProperty("Servidor", "");
			String baseDatos = prop.getProperty("BaseDatos", "");
			String clave = prop.getProperty("Clave", "");
			String usuario = prop.getProperty("Usuario", "");
			String gestor = prop.getProperty("SGBD", "");

			if (servidor.equals("") || baseDatos.equals("")
					|| usuario.equals("")) {
				return null;
			} else {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				usuario = decrypt(usuario);
				clave = decrypt(clave);
				return new String[] {servidor, baseDatos, usuario, clave, gestor};
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} 
	}

	public static void CrearConfig() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			String usuario = "root";
			String clave = "root";

			usuario = encrypt(usuario);
			clave = encrypt(clave);

			output = new FileOutputStream("config.properties");
			prop.setProperty("Servidor", "localhost:3306");
			prop.setProperty("BaseDatos", "bd");
			prop.setProperty("Usuario", usuario);
			prop.setProperty("Clave", clave);
			prop.setProperty("SGBD", clave);
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static void CrearConfig(SysCfgInicio cfgInicio){
		Properties prop = new Properties();
		OutputStream output = null;

		try {


			output = new FileOutputStream(Sys.SYS_CONFIG);
			prop.setProperty("Servidor", cfgInicio.getServidor());
			prop.setProperty("BaseDatos", cfgInicio.getBase_datos());
			prop.setProperty("Usuario", encrypt(cfgInicio.getUsuario()));
			prop.setProperty("Clave", encrypt(cfgInicio.getClave()));
			prop.setProperty("SGBD", cfgInicio.getGestor());
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	public static void main(String[] args) {
		ConfigInicial.CrearConfig();
		ConfigInicial.LlenarConfig();
	}

}
