package vista.utilitarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import core.entity.Clieprov;

public class ObjetoWeb {
	
	public static Clieprov ConsultaRUC(String ruc) {
		String captcha = "";

		try {

			Connection.Response res = Jsoup
					.connect(
							"http://www.sunat.gob.pe/cl-ti-itmrconsruc/captcha")
					.data("accion", "random").method(Method.GET).execute();
			
			Map<String, String> cookie = res.cookies();

			captcha = res.parse().select("body").text();
			
			Document dRuc = Jsoup
					.connect(
							"http://www.sunat.gob.pe/cl-ti-itmrconsruc/jcrS00Alias")
					.data("accion", "consPorRuc").data("nroRuc", ruc)
					.data("actReturn", "1").data("numRnd", captcha)
					.cookies(cookie).get();

			Element table = dRuc.select("TABLE[class = form-table]").first();

			Elements rows = table.select("tr");
			int i = 0;
			Clieprov c = new Clieprov();
			c.setRuc(ruc);
			for (Element e : rows) {
				if (e.children().size() > 1) {
					Element td = e.child(1);
					if (i == 0){
						c.setIdclieprov(td.text().substring(0, 11));
						c.setRazonSocial(td.text().substring(14, td.text().length()));
					}
					if (i == 6 || i == 7)
						if(td.text().length() > 7){
							c.setDireccion(td.text());
							break;
						}							
				}
				i += 1;
			}
			return c;

		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	public static List<TipoCambio> getTipoCambioSunat(int anio, int mes) {

		// http: //
		// www.sunat.gob.pe/cl-at-ittipcam/tcS01Alias?mesElegido=09&anioElegido=2014&mes=01&anho=2014&accion=init&email=
		List<TipoCambio> tc = new ArrayList<TipoCambio>();

		try {

			Map<String, String> m = new HashMap<String, String>();
			m.put("mesElegido", StringUtils._padl(mes, 2, '0'));
			m.put("anioElegido", StringUtils._padl(anio, 4, '0'));
			m.put("mes", StringUtils._padl(mes, 2, '0'));
			m.put("anho", StringUtils._padl(anio, 4, '0'));
			m.put("accion", "init");
			m.put("mail", "");

			Document res = Jsoup
					.connect(
							"http://www.sunat.gob.pe/cl-at-ittipcam/tcS01Alias")
					.data(m).method(Method.GET).get();

			Element table = res.select("TABLE[class = class=\"form-table\"]")
					.get(0);

			Elements rows = table.select("tr");

			for (int i = 1; i < rows.size(); i++) {
				Element e = rows.get(i);

				Elements de = e.select("td");

				for (int j = 0; j < de.size() / 3; j++) {
					tc.add(new TipoCambio(Integer.parseInt(de.get(j * 3 + 0)
							.text()), Float
							.parseFloat(de.get(j * 3 + 1).text()), Float
							.parseFloat(de.get(j * 3 + 2).text())));
				}
			}
			return tc;
			// for (_TipoCambio t : tc) {
			// System.out.println(t.getDia() + " " + t.getCompra() + " "
			// + t.getVenta());
			// }

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(ConsultaRUC("20314727500"));
	
		//getTipoCambioSunat(2014, 9);
	}
}