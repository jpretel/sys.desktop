package vista.barras;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class PanelBarraDocumento extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnAnular;
	private JButton btnGrabar;
	private JButton btnEliminar;
	private JButton btnSalir;
	private JButton btnVerAsiento;
	private JButton btnPrevio;
	private JButton btnExportar;
	private JButton btnImprimir;
	private JButton btnCancelar;
	private IFormDocumento formMaestro;
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
		return btnAnular;
	}

	public void setBtnAnular(JButton btnAnular) {
		this.btnAnular = btnAnular;
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

	/*
	 * Adicionales: A: Ver Asiento E: Exportar Documento
	 */
	public PanelBarraDocumento(char... adicionales) {

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAnular = new JButton("");
		btnAnular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFormMaestro().anular();
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
				getFormMaestro().DoNuevo();
			}
		});

		btnNuevo.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
				.getResource("/main/resources/iconos/nuevo.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnNuevo.setToolTipText("Nuevo");
		add(btnNuevo);
		btnEditar
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
						.getResource("/main/resources/iconos/editar.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnEditar.setToolTipText("Editar");
		add(btnEditar);
		btnAnular
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
						.getResource("/main/resources/iconos/anular.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnAnular.setToolTipText("Editar");
		add(btnAnular);

		btnEliminar = new JButton("");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFormMaestro().eliminar();
			}
		});

		btnGrabar = new JButton("");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFormMaestro().DoGrabar();
			}
		});

		btnCancelar = new JButton("");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFormMaestro().cancelar();
			}
		});

		btnCancelar.setIcon(new ImageIcon(new ImageIcon(
				PanelBarraDocumento.class
						.getResource("/main/resources/iconos/cancelar.png"))
				.getImage().getScaledInstance(_ancho, _alto,
						java.awt.Image.SCALE_DEFAULT)));
		btnCancelar.setToolTipText("Cancelar");
		add(btnCancelar);

		btnGrabar
				.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
						.getResource("/main/resources/iconos/grabar.png"))
						.getImage().getScaledInstance(_ancho, _alto,
								java.awt.Image.SCALE_DEFAULT)));
		btnGrabar.setToolTipText("Grabar");
		add(btnGrabar);

		btnVerAsiento = new JButton("");
		btnVerAsiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFormMaestro().doVerAsiento();
			}
		});
		btnVerAsiento.setIcon(new ImageIcon(new ImageIcon(
				PanelBarraDocumento.class
						.getResource("/main/resources/iconos/verasiento.png"))
				.getImage().getScaledInstance(_ancho, _alto,
						java.awt.Image.SCALE_DEFAULT)));

		btnExportar = new JButton("");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Component c = (Component) arg0.getSource();
				getMenu().show(btnExportar, 0, c.getHeight());
			}
		});
		btnExportar
				.setIcon(new ImageIcon(
						new ImageIcon(
								PanelBarraDocumento.class
										.getResource("/main/resources/iconos/export_document.png"))
								.getImage().getScaledInstance(_ancho, _alto,
										java.awt.Image.SCALE_DEFAULT)));

		btnPrevio = new JButton("");
		btnPrevio.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
				.getResource("/main/resources/iconos/vistaprevia.png"))
				.getImage().getScaledInstance(_ancho, _alto,
						java.awt.Image.SCALE_DEFAULT)));
		btnPrevio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFormMaestro().doPrevio();
			}
		});
		
		btnImprimir = new JButton("");
		btnImprimir.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
				.getResource("/main/resources/iconos/printer.png"))
				.getImage().getScaledInstance(_ancho, _alto,
						java.awt.Image.SCALE_DEFAULT)));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFormMaestro().doImprimir();
			}
		});
		if (adicionales != null) {
			salir: for (char tipo : adicionales) {
				if (tipo == 'A') {
					add(btnVerAsiento);
					break salir;
				}
				if (tipo == 'E') {
					add(btnImprimir);
					add(btnPrevio);
					add(btnExportar);
					break salir;
				}
			}
		}

		btnEliminar.setIcon(new ImageIcon(new ImageIcon(
				PanelBarraDocumento.class
						.getResource("/main/resources/iconos/eliminar.png"))
				.getImage().getScaledInstance(_ancho, _alto,
						java.awt.Image.SCALE_DEFAULT)));
		btnEliminar.setToolTipText("Eliminar");
		add(btnEliminar);

		btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFormMaestro().doSalir();
			}
		});
		btnSalir.setIcon(new ImageIcon(new ImageIcon(PanelBarraDocumento.class
				.getResource("/main/resources/iconos/salir.png")).getImage()
				.getScaledInstance(_ancho, _alto, java.awt.Image.SCALE_DEFAULT)));
		btnSalir.setToolTipText("Salir");
		add(btnSalir);
		llenarToolTipText();
	}
	
	private void llenarToolTipText() {
		btnNuevo.setToolTipText("Nuevo");
		btnEditar.setToolTipText("Editar");
		btnAnular.setToolTipText("Anular");
		btnGrabar.setToolTipText("Grabar");
		btnEliminar.setToolTipText("Eliminar");
		btnSalir.setToolTipText("Salir");
		btnVerAsiento.setToolTipText("Ver Asiento");
		btnPrevio.setToolTipText("Vista Previa");
		btnExportar.setToolTipText("Exportar");
		btnImprimir.setToolTipText("Imprimir");
		btnCancelar.setToolTipText("Cancelar");
	}
	
	public IFormDocumento getFormMaestro() {
		return formMaestro;
	}

	public void setFormMaestro(IFormDocumento formMaestro) {
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
		getBtnExportar().setEnabled(band);
		getBtnPrevio().setEnabled(band);
		getBtnImprimir().setEnabled(band);
		getBtnVerAsiento().setEnabled(band);
	}

	public JButton getBtnVerAsiento() {
		return btnVerAsiento;
	}

	public void setBtnVerAsiento(JButton btnVerAsiento) {
		this.btnVerAsiento = btnVerAsiento;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnExportar() {
		return btnExportar;
	}

	public void setBtnExportar(JButton btnExportar) {
		this.btnExportar = btnExportar;
	}

	public JPopupMenu getMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem mExcel = new JMenuItem("MS Excel");
		mExcel.setIcon(getIconResized("/main/resources/iconos/export_excel.png"));
		popupMenu.add(mExcel);

		JMenuItem mWord = new JMenuItem("MS Word");
		mWord.setIcon(getIconResized("/main/resources/iconos/export_word.png"));
		popupMenu.add(mWord);

		JMenuItem mPDF = new JMenuItem("PDF");
		mPDF.setIcon(getIconResized("/main/resources/iconos/export_pdf.png"));
		popupMenu.add(mPDF);

		JMenuItem mOdt = new JMenuItem("ODT");
		mOdt.setIcon(getIconResized("/main/resources/iconos/export_odt.png"));
		popupMenu.add(mOdt);

		JMenuItem mOds = new JMenuItem("ODS");
		mOds.setIcon(getIconResized("/main/resources/iconos/export_ods.png"));
		popupMenu.add(mOds);

		mExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				formMaestro.doExportaExcel();
			}
		});

		mWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				formMaestro.doExportaWord();
			}
		});

		mPDF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				formMaestro.doExportaPDF();
			}
		});

		mOdt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				formMaestro.doExportaOdt();
			}
		});

		mOds.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				formMaestro.doExportaOds();
			}
		});

		return popupMenu;
	}

	private static Icon getIconResized(String path) {
		return new ImageIcon(new ImageIcon(
				PanelBarraDocumento.class.getResource(path)).getImage()
				.getScaledInstance(25, 25, java.awt.Image.SCALE_DEFAULT));
	}

	public JButton getBtnPrevio() {
		return btnPrevio;
	}

	public void setBtnPrevio(JButton btnPrevio) {
		this.btnPrevio = btnPrevio;
	}

	public JButton getBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(JButton btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
}
