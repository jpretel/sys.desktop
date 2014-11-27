package vista.formularios.abstractforms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import vista.MainFrame;
import vista.Sys;
import vista.barras.IFormDocumento;
import vista.barras.PanelBarraDocumento;
import vista.controles.DSGDatePicker;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTextFieldCorrelativo;
import vista.utilitarios.UtilMensajes;

public abstract class AbstractDocForm extends DSGInternalFrame implements
		IFormDocumento {
	private static final long serialVersionUID = 1L;
	protected PanelBarraDocumento barra;
	protected JPanel pnlPrincipal;
	protected String estado;
	protected DSGTextFieldCorrelativo txtNumero;
	protected DSGDatePicker txtFecha;
	protected DSGTextFieldCorrelativo txtSerie;

	public AbstractDocForm(String titulo) {
		setTitle(titulo);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		initBarra();
		int AnchoCabecera = 850;

		pnlPrincipal = new JPanel();
		pnlPrincipal.setMinimumSize(new Dimension(AnchoCabecera, 500));
		pnlPrincipal.setPreferredSize(new Dimension(AnchoCabecera, 500));
		pnlPrincipal.setBounds(0, 40, AnchoCabecera, 70);

		getContentPane().add(pnlPrincipal);
		JLabel lblNumero = new JLabel("Correlativo");
		lblNumero.setBounds(10, 15, 53, 14);

		txtNumero = new DSGTextFieldCorrelativo(8);
		this.txtNumero.setBounds(116, 12, 80, 20);
		txtNumero.setColumns(10);

		txtFecha = new DSGDatePicker();
		this.txtFecha.setBounds(245, 11, 101, 22);
		txtFecha.getEditor().setLocation(0, 8);
		txtFecha.setDate(new Date());

		setEstado(VISTA);
		setBounds(100, 100, 854, 465);

		this.pnlPrincipal.setLayout(null);
		this.pnlPrincipal.add(lblNumero);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(206, 15, 53, 14);
		this.pnlPrincipal.add(lblFecha);

		txtSerie = new DSGTextFieldCorrelativo(4);
		this.txtSerie.setBounds(72, 12, 44, 20);
		txtSerie.setColumns(10);
		this.pnlPrincipal.add(this.txtSerie);
		this.pnlPrincipal.add(this.txtNumero);
		this.pnlPrincipal.add(this.txtFecha);
	}

	public void initBarra() {
		int AnchoCabecera = 850;
		barra = new PanelBarraDocumento();
		barra.setMinimumSize(new Dimension(AnchoCabecera, 40));
		barra.setPreferredSize(new Dimension(AnchoCabecera, 40));
		barra.setBounds(0, 0, AnchoCabecera, 42);
		barra.setFormMaestro(this);
		FlowLayout flowLayout = (FlowLayout) barra.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(barra, BorderLayout.NORTH);
	}

	public void iniciar() {
		llenar_tablas();
		llenar_datos();
		getBarra().enVista();
		vista_noedicion();
	}

	public abstract void nuevo();

	public void editar() {
		setEstado(EDICION);
		getBarra().enEdicion();
		vista_edicion();
	}

	public abstract void anular();

	public abstract void grabar();

	public abstract void eliminar();

	public abstract void llenar_datos();

	public abstract void llenar_tablas();

	public abstract void vista_edicion();

	public abstract void vista_noedicion();

	public abstract void actualiza_objeto(Object id);

	public void cancelar() {
		llenar_tablas();
		llenar_datos();
		setEstado(VISTA);
		vista_noedicion();
		getBarra().enVista();
	}

	public void DoGrabar() {
		boolean esVistaValido;
		esVistaValido = isValidaVista();
		if (esVistaValido) {
			llenarDesdeVista();
			grabar();
			setEstado(VISTA);
			getBarra().enVista();
			vista_noedicion();
			llenar_tablas();
			llenar_datos();
		}
	}

	public void DoNuevo() {
		nuevo();
		setEstado(NUEVO);
		getBarra().enEdicion();
		llenar_datos();
		vista_edicion();
	}

	public void DoEliminar() {
		int opcion = UtilMensajes.mensaje_sino("DESEA_ELIMINAR_DOC");
		if (opcion == 0) {
			eliminar();
			setEstado(VISTA);
			getBarra().enVista();
			vista_noedicion();
			actualiza_objeto(null);
			llenar_datos();
		}
	}

	public abstract void llenarDesdeVista();

	public abstract boolean isValidaVista();

	public void doVerAsiento() {

	}

	public void doSalir() {

		int seleccion = UtilMensajes.mensaje_sino("CERRAR_DOCUMENTO");
		if (seleccion == 0) {
			this.dispose();
		}

	}

	private JasperPrint getPrintReport() {
		String reporte = Sys.empresa.getRuta_reportes() + "\\"
				+ getNombreReporte() + ".jrxml";

		try {
			final JasperReport report = JasperCompileManager
					.compileReport(reporte);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report,
					getParamsReport(), getDataSourceReport());
			jasperPrint.setName(getNombreArchivo());
			return jasperPrint;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Sys.mainF, "No se pudo abrir el archivo: " + reporte);
			return null;
		}
	}

	public void doExportaExcel() {

		File xlsx = new File(getExportar() + ".xlsx");

		JasperPrint print = getPrintReport();

		if (print != null) {

			JRXlsxExporter exporter = new JRXlsxExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
					xlsx));
			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}
			abrirCarpeta();
		}
	};

	public void doExportaPDF() {
		JasperPrint print = getPrintReport();

		if (print != null) {
			final String target = getExportar() + ".pdf";

			try {
				JasperExportManager.exportReportToPdfFile(print, target);
			} catch (JRException e) {
				e.printStackTrace();
			}
			abrirCarpeta();
		}
	};

	public void doExportaOdt() {

		JasperPrint print = getPrintReport();

		File doc = new File(getExportar() + ".odt");

		if (print != null) {

			JROdtExporter exporter = new JROdtExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(doc));
			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}

			abrirCarpeta();
		}
	};

	public void doExportaOds() {

		JasperPrint print = getPrintReport();

		if (print != null) {
			File doc = new File(getExportar() + ".ods");

			JROdsExporter exporter = new JROdsExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(doc));
			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}

			abrirCarpeta();
		}
	};

	public void doExportaWord() {
		JasperPrint print = getPrintReport();

		if (print != null) {
			File doc = new File(getExportar() + ".docx");

			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(doc));
			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}

			abrirCarpeta();
		}
	};

	@Override
	public void doPrevio() {
		JasperPrint print = getPrintReport();

		if (print != null) {
			JasperViewer jv = new JasperViewer(print, false);
			jv.setTitle("Vista Previa de " + getNombreArchivo());
			jv.setIconImage(new ImageIcon(MainFrame.class
					.getResource("/main/resources/iconos/logo.png")).getImage());
			jv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jv.setVisible(true);
		}
	}

	@Override
	public void doImprimir() {
		JasperPrint print = getPrintReport();

		if (print != null) {
			try {
				JasperPrintManager.printReport(print, true);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	private void abrirCarpeta() {
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"explorer.exe " + Sys.empresa.getRuta_exportar());
			p.waitFor();
		} catch (IOException e) {

		} catch (InterruptedException e) {

		}
	}

	protected JRDataSource getDataSourceReport() {
		JOptionPane
				.showMessageDialog(
						null,
						"Sobreescribir el metodo getDataSourceReport(); - Data source enviado al formato de impresión");
		return new JREmptyDataSource();
	}

	protected Map<String, Object> getParamsReport() {
		JOptionPane
				.showMessageDialog(
						null,
						"Sobreescribir el metodo getParamsReport(); - Parametros enviado al formato de impresión");
		return new HashMap<String, Object>();
	}

	protected String getNombreArchivo() {
		JOptionPane
				.showMessageDialog(
						null,
						"Sobreescribir el metodo getNombreArchivo(); - Nombre del Archivo que se va a generar (Sin Extension)");
		return "Documento";
	}

	protected String getNombreReporte() {
		JOptionPane
				.showMessageDialog(
						null,
						"Sobreescribir el metodo getNombreReporte(); - Nombre del Archivo jasperreport sin compilar <.jasper> (SinExtension - Puede usar carpetas '\\')");
		return "Documento";
	}

	protected abstract void limpiarVista();

	private String getExportar() {
		return Sys.empresa.getRuta_exportar() + "\\" + getNombreArchivo();
	}

	public PanelBarraDocumento getBarra() {
		return barra;
	}

	public void setBarra(PanelBarraDocumento barra) {
		this.barra = barra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
