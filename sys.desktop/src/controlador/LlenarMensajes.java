package controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LlenarMensajes {

	public static void main(String[] args) {
		new LlenarMensajes().CargarMensajes();
	}

	private TrustManager[] get_trust_mgr() {
		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String t) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String t) {
			}
		} };
		return certs;
	}

	private void CargarMensajes() {
		String https_url = "https://docs.google.com/spreadsheets/d/1urYmOv48aZ-NhZl6C5mEHQM9iC6tWUJRat9z2tG9TYE/pubhtml";
		URL url;
		try {

			// Create a context that doesn't check certificates.
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null, // key manager
					trust_mgr, // trust manager
					new SecureRandom()); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx
					.getSocketFactory());

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String host, SSLSession sess) {
					if (host.equals("localhost"))
						return true;
					else
						return false;
				}
			});

			print_https_cert(con);
			Document res = Jsoup
					.connect(
							"https://docs.google.com/spreadsheets/d/1urYmOv48aZ-NhZl6C5mEHQM9iC6tWUJRat9z2tG9TYE/pubhtml")
					.get();

			Elements cuerpo = res.body().select("div[id*=sheets-viewport")
					.select("tbody").select("tr");

			Properties p_espanol = new Properties();
			Properties p_ingles = new Properties();
			OutputStream espanol = null;
			OutputStream ingles = null;
			espanol = new FileOutputStream("Espanol.properties");
			ingles = new FileOutputStream("Ingles.properties");
			for (Element e : cuerpo) {
				String clave = e.child(1).text();
				String valor = e.child(2).text();
				String valoringles = (e.children().size() < 4) ? "" : e
						.child(3).text();
				System.out.println(clave + " / " + valor + " / " + valoringles);
				p_espanol.setProperty(clave, valor);
				p_ingles.setProperty(clave, valoringles);
			}
			p_espanol.store(espanol, null);
			p_ingles.store(ingles, null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	private void print_https_cert(HttpsURLConnection con) {
		if (con != null) {

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}