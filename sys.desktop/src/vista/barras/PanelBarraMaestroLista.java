package vista.barras;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import vista.formularios.abstractforms.AbstractMaestroLista;

public class PanelBarraMaestroLista extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnBuscar;
	private JButton btnGrabar;
	private JButton btnEliminar;
	private JButton btnSalir;
	private JButton btnImprimir;
	private AbstractMaestroLista formMaestro;
	private static final int _ancho = 18;
	private static final int _alto = 18;

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(JButton btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public JButton getBtnAnular() {
		return btnBuscar;
	}

	public void setBtnAnular(JButton btnAnular) {
		this.btnBuscar = btnAnular;
	}

	public JButton getBtnGrabar() {
		return btnGrabar;
	}

	public void setBtnGrabar(JButton btnGrabar) {
		this.btnGrabar = btnGrabar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public PanelBarraMaestroLista() {
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getFormMaestro().anular();
			}
		});

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFormMaestro().editar();
			}
		});

		btnNuevo = new JButton("");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				getFormMaestro().nuevo();
			}
		});
		
		btnNuevo.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestroLista.class
				.getResource("/main/resources/iconos/nuevo.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnNuevo.setToolTipText("Nuevo");
		add(btnNuevo);
		btnEditar
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestroLista.class
						.getResource("/main/resources/iconos/editar.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnEditar.setToolTipText("Editar");
		add(btnEditar);
		btnBuscar
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestroLista.class
						.getResource("/main/resources/iconos/find.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnBuscar.setToolTipText("Editar");
		add(btnBuscar);

		btnImprimir = new JButton("");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//getFormMaestro().cancelar();
			}
		});
		btnImprimir
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestroLista.class
						.getResource("/main/resources/iconos/printer.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnImprimir.setToolTipText("Cancelar");
		add(btnImprimir);

		btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//getFormMaestro().salir();
			}
		});
		btnSalir.setIcon(new ImageIcon(new ImageIcon(PanelBarraMaestroLista.class
				.getResource("/main/resources/iconos/salir.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnSalir.setToolTipText("Salir");
		add(btnSalir);
	}

	public AbstractMaestroLista getFormMaestro() {
		return formMaestro;
	}

	public void setFormMaestro(AbstractMaestroLista formMaestro) {
		this.formMaestro = formMaestro;
	}
	
	public void enVista() {
		edicion(true);
	}

	public void enEdicion() {
		edicion(false);
	}

	private void edicion(boolean band) {
		getBtnAnular().setEnabled(band);
		getBtnEditar().setEnabled(band);
		getBtnGrabar().setEnabled(!band);
		getBtnEliminar().setEnabled(band);
		getBtnNuevo().setEnabled(band);
	}
}
