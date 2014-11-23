package vista.utilitarios;

import java.util.Calendar;
import java.util.UUID;

public class StringUtils {
	public static String _left(String cadena, int longitud) {
		cadena = cadena.trim();
		if (cadena.length() <= longitud)
			return cadena;
		return cadena.substring(0, longitud);
	}

	public static String _right(String cadena, int longitud) {
		cadena = cadena.trim();

		cadena = cadena.trim();
		if (cadena.length() <= longitud)
			return cadena;
		return cadena.substring(cadena.length() - longitud);
	}

	public static String _padl(int valor, int longitud, char car){
		String cad = String.valueOf(valor).trim();
		
		if (cad.length() < longitud) {
			cad = _replicate(longitud - cad.length(), car) + cad;
		}
		
		return cad;
	}
	
	public static String _padl(String valor, int longitud, char car){
		String cad = valor.trim();
		
		if (cad.length() < longitud) {
			cad = _replicate(longitud - cad.length(), car) + cad;
		}
		
		return cad;
	}

	public static String _replicate(int cantidad, String ret) {
		String cad = "";

		for (int i = 0; i < cantidad; i++) {
			cad = cad.concat(ret);
		}
		return cad;
	}

	public static String _replicate(int cantidad, char ret) {
		String cad = "";

		for (int i = 0; i < cantidad; i++) {
			cad = cad.concat(String.valueOf(ret));
		}
		return cad;
	}

	public static String prueba() {
		Calendar c;
		c = Calendar.getInstance();
		System.out.println(c.getTimeInMillis());
		// byte[] bytesOfMessage = yourString.getBytes("UTF-8");
		// MessageDigest md = MessageDigest.getInstance("MD5");
		// byte[] thedigest = md.digest(bytesOfMessage);
		return null;
	}

	public static void main(String[] args) {
		String cadena = "Holaaabbb";
		System.out.println(_left(cadena, 3));

		String input = "some input stringss";
		int hashCode = input.hashCode();
		System.out.println("input hash code = " + hashCode);

		UUID idOne = UUID.randomUUID();
		UUID idTwo = UUID.randomUUID();
		log("UUID One: " + idOne);
		log("UUID Two: " + idTwo);
		
		System.out.println(_padl(1, 3, '0'));
	}

	private static void log(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}
}
