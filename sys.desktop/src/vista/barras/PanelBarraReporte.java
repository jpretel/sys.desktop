package vista.barras;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBarraReporte extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnVistaPrevia;
	private JButton btnImprimir;
	private JButton btnExportar;
	private JButton btnSalir;
	private IFormReporte frmReporte;
	private static final int _ancho = 16;
	private static final int _alto = 16;
	private JButton btnConsultar;

	public PanelBarraReporte() {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		btnExportar = new JButton("");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrmReporte().doExportar();
			}
		});

		btnImprimir = new JButton("");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrmReporte().doImprimir();
			}
		});

		btnVistaPrevia = new JButton("");
		btnVistaPrevia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrmReporte().doExportar();
			}
		});
		
		btnConsultar = new JButton("");
		btnConsultar.setIcon(new ImageIcon(new ImageIcon(PanelBarraReporte.class
				.getResource("/main/resources/iconos/consultar.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnConsultar.setToolTipText("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmReporte.doConsultar();
			}
		});
		add(this.btnConsultar);
		
		btnVistaPrevia.setIcon(new ImageIcon(new ImageIcon(PanelBarraReporte.class
				.getResource("/main/resources/iconos/vista_previa.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnVistaPrevia.setToolTipText("Vista Previa");
		add(btnVistaPrevia);
		btnImprimir
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraReporte.class
						.getResource("/main/resources/iconos/printer.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnImprimir.setToolTipText("Imprimir");
		add(btnImprimir);
		btnExportar
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraReporte.class
						.getResource("/main/resources/iconos/exportar.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnExportar.setToolTipText("Exportar");
		add(btnExportar);

		btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getFormMaestro().salir();
			}
		});
		btnSalir.setIcon(new ImageIcon(new ImageIcon(PanelBarraReporte.class
				.getResource("/main/resources/iconos/salir.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnSalir.setToolTipText("Salir");
		add(btnSalir);
	}

	public IFormReporte getFrmReporte() {
		return frmReporte;
	}

	public void setFrmReporte(IFormReporte frmReporte) {
		this.frmReporte = frmReporte;
	}

	public JButton getBtnVistaPrevia() {
		return btnVistaPrevia;
	}

	public void setBtnVistaPrevia(JButton btnVistaPrevia) {
		this.btnVistaPrevia = btnVistaPrevia;
	}

	public JButton getBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(JButton btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public JButton getBtnExportar() {
		return btnExportar;
	}

	public void setBtnExportar(JButton btnExportar) {
		this.btnExportar = btnExportar;
	}
}
