package vista;

import static org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority.LOW;
import static org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority.MEDIUM;
import static org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority.TOP;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntrySecondary;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;

import vista.utilitarios.MenuController;

import com.scrollabledesktop.JScrollableDesktopPane;

import controlador.ControladorOpciones;
import core.dao.SysFormularioDAO;
import core.dao.SysModuloDAO;
import core.entity.SysFormulario;
import core.entity.SysGrupo;
import core.entity.SysModulo;
import core.entity.SysOpcion;
import core.entity.SysTitulo;

public class MainFrame extends JRibbonFrame {
	static JScrollableDesktopPane desktopPane;
	static ControladorOpciones cOpciones;

	static SysModuloDAO moduloDAO = new SysModuloDAO();
	SysFormularioDAO formularioDAO = new SysFormularioDAO();
	static {
		cOpciones = new ControladorOpciones();
	}

	public MainFrame() {

		desktopPane = new JScrollableDesktopPane();

		getContentPane().add(desktopPane, BorderLayout.CENTER);
		Sys.desktoppane = desktopPane;
		// Se establece el tamaño minimo del MainFrame
		setMinimumSize(new Dimension(800, 600));

		cOpciones.setDesktopPane(getDesktopPane());

		setTitle(Sys.empresa.getRazon_social() + " :: BRIGHT GLOBAL CHANGE ERP");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		IniciarRibbon();

		JToolBar tlbPie = new JToolBar();
		tlbPie.setRollover(true);
		tlbPie.setFloatable(false);
		getContentPane().add(tlbPie, BorderLayout.SOUTH);

		// this.btnVentanas = new JButton("Ventanas Activas");
		// this.btnVentanas.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// JPopupMenu menu = getMenuVentanas();
		// menu.show(btnVentanas, 0, 0);
		// menu.show(btnVentanas, 0, - menu.getHeight());
		// }
		// });
		// tlbPie.add(this.btnVentanas);

		this.separator = new JSeparator();
		this.separator.setPreferredSize(new Dimension(20, 2));
		this.separator.setMinimumSize(new Dimension(50, 0));
		this.separator.setMaximumSize(new Dimension(50, 32767));
		tlbPie.add(this.separator);

		JLabel label = new JLabel(Sys.usuario.getIdusuario());
		tlbPie.add(label);

		tlbPie.add(new JSeparator());

		// Vtr Fecha Sistema
		Date fecha = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		JLabel lblFecha = new JLabel();
		lblFecha.setText(sdf.format(fecha));
		tlbPie.add(lblFecha);

		// getContentPane().add(desktopPane, BorderLayout.CENTER);

		pack();
		setVisible(true);

	}

	public static JScrollableDesktopPane getDesktopPane() {
		return desktopPane;
	}

	private void IniciarRibbon() {
		SysModulo moduloIncial = null;

		RibbonApplicationMenu ribbon = new RibbonApplicationMenu();

		RibbonApplicationMenuEntryPrimary modulo_popup = new RibbonApplicationMenuEntryPrimary(
				getResizableIconFromResource("/main/resources/salir.png"),
				"Modulo", null, CommandButtonKind.POPUP_ONLY);

		List<SysModulo> modulos = moduloDAO.findAll();
		RibbonApplicationMenuEntrySecondary[] modulos_vec = new RibbonApplicationMenuEntrySecondary[modulos
				.size()];

		int i = -1;
		for (final SysModulo m : modulos) {
			i++;
			if (i == 0) {
				moduloIncial = m;
			}

			String url = null;
			if (m.getImagen() == null
					|| m.getImagen().toString().trim().length() < 1) {
				url = "/main/resources/salir.png";
			} else {
				url = "/main/resources/" + m.getImagen().toString().trim();
			}

			RibbonApplicationMenuEntrySecondary secondary = new RibbonApplicationMenuEntrySecondary(
					getResizableIconFromResource16x16(url), m.getDescripcion(),
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							CreaRibbonMenu(MenuController
									.getTitulosPorModulo(m));
						}
					}, CommandButtonKind.ACTION_ONLY);
			modulos_vec[i] = secondary;
		}

		modulo_popup.addSecondaryMenuGroup("Lista de Módulos", modulos_vec);

		ribbon.addMenuEntry(modulo_popup);

		ribbon.addMenuSeparator();

		RibbonApplicationMenuEntryPrimary nn = new RibbonApplicationMenuEntryPrimary(
				getResizableIconFromResource("/main/resources/favoritos.png"),
				"DashBoard", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// CreaRibbonMenu();
					}
				}, CommandButtonKind.ACTION_ONLY);

		ribbon.addMenuEntry(nn);

		RibbonApplicationMenuEntryPrimary config_popup = new RibbonApplicationMenuEntryPrimary(
				getResizableIconFromResource("/main/resources/iconos/config_inicial.png"),
				"Configuración Inicial", null, CommandButtonKind.POPUP_ONLY);

		RibbonApplicationMenuEntrySecondary[] configs = new RibbonApplicationMenuEntrySecondary[4];

		configs[0] = new RibbonApplicationMenuEntrySecondary(
				getResizableIconFromResource16x16("/main/resources/iconos/empresa.png"),
				"Empresa",
				cOpciones
						.actionAbrirFormulario("vista.formularios.maestros.FrmEmpresa"),
				CommandButtonKind.ACTION_ONLY);

		configs[1] = new RibbonApplicationMenuEntrySecondary(
				getResizableIconFromResource16x16("/main/resources/iconos/formulario.png"),
				"Gestion de Formularios",
				cOpciones
						.actionAbrirFormulario("vista.formularios.maestros.FrmSysFormulario"),
				CommandButtonKind.ACTION_ONLY);

		configs[2] = new RibbonApplicationMenuEntrySecondary(
				getResizableIconFromResource16x16("/main/resources/iconos/menu.png"),
				"Gestion de Modulos y Menús",
				cOpciones
						.actionAbrirFormulario("vista.formularios.maestros.FrmMenus"),
				CommandButtonKind.ACTION_ONLY);

		configs[3] = new RibbonApplicationMenuEntrySecondary(
				getResizableIconFromResource16x16("/main/resources/iconos/config_usuarios.png"),
				"Privilegios por Usuario", cOpciones
						.returnAction("FrmPrivilegiosUsuario"),
				CommandButtonKind.ACTION_ONLY);

		if (Sys.usuario.getGrupoUsuario().getEsAdministrador() == 1) {

			config_popup
					.addSecondaryMenuGroup("Configuración Inicial", configs);

			ribbon.addMenuEntry(config_popup);
		}

		RibbonApplicationMenuEntryPrimary cambiar_clave = new RibbonApplicationMenuEntryPrimary(
				getResizableIconFromResource("/main/resources/iconos/cambia_clave.png"),
				"Cambiar Clave", cOpciones.actionAbrirFormulario("vista.formularios.maestros.FrmCambioClave"),
				CommandButtonKind.ACTION_ONLY);

		ribbon.addMenuEntry(cambiar_clave);

		RibbonApplicationMenuEntryPrimary cerrar_sesion = new RibbonApplicationMenuEntryPrimary(
				getResizableIconFromResource("/main/resources/iconos/cerrar_sesion.png"),
				"Cerrar Sesión", returnAction2(), CommandButtonKind.ACTION_ONLY);

		ribbon.addMenuEntry(cerrar_sesion);

		RibbonApplicationMenuEntryFooter footer = new RibbonApplicationMenuEntryFooter(
				getResizableIconFromResource("/main/resources/salir.png"),
				"Salir", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});

		ribbon.addFooterEntry(footer);

		getRibbon().setApplicationMenu(ribbon);
		setApplicationIcon(getResizableIconFromResource("/main/resources/iconos/logo.png"));
		setIconImage(new ImageIcon(
				MainFrame.class.getResource("/main/resources/iconos/logo.png"))
				.getImage());

		if (moduloIncial != null) {
			try {
				CreaRibbonMenu(MenuController.getTitulosPorModulo(moduloIncial));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ActionListener returnAction2() {
		try {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// setVisible(false);
					new vista.Sys("").iniciar();
					dispose();
				}
			};
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void CreaRibbonMenu(List<SysTitulo> titulos) {
		getRibbon().removeAllTasks();
		for (SysTitulo titulo : titulos) {

			if (titulo.getSysGrupos() == null) {
				titulo.setSysGrupos(new ArrayList<SysGrupo>());
			}

			List<JRibbonBand> bandas_aux = new ArrayList<JRibbonBand>();
			for (SysGrupo grupo : titulo.getSysGrupos()) {

				if (grupo.getSysOpcions() == null) {
					grupo.setSysOpcions(new ArrayList<SysOpcion>());
				}

				boolean dibujaGrupo = (grupo.getSysOpcions().size() > 0);
				JRibbonBand band = new JRibbonBand(
						grupo.getDescripcion(),
						getResizableIconFromResource("/main/resources/iconos/nuevo.png"));

				if (dibujaGrupo) {

					bandas_aux.add(band);

					for (SysOpcion opcion : grupo.getSysOpcions()) {

						// SysFormulario formulario = opcion.getSysFormulario();

						SysFormulario formulario = formularioDAO.find(opcion
								.getId().getIdformulario());

						String url = null;
						if (formulario.getImagen() == null
								|| formulario.getImagen().toString().trim()
										.length() < 1) {
							url = "/main/resources/iconos/nuevo.png";
						} else {
							url = "/main/resources/iconos/"
									+ formulario.getImagen().toString().trim();
						}

						// formulario.setImagen("/main/resources/iconos/nuevo.png");

						JCommandButton button = new JCommandButton(
								formulario.getDescripcion(),
								getResizableIconFromResource(url));

						if (opcion.getPrioridad() == 0) {
							band.addCommandButton(button, TOP);
						} else if (opcion.getPrioridad() == 1) {
							band.addCommandButton(button, MEDIUM);
						} else {
							band.addCommandButton(button, LOW);
						}

						button.addActionListener(cOpciones
								.actionAbrirFormulario(formulario.getOpcion()));
					}

					band.setResizePolicies((List) Arrays.asList(
							new CoreRibbonResizePolicies.Mid2Mid(band
									.getControlPanel()),
							new IconRibbonBandResizePolicy(band
									.getControlPanel())));
				}
			}

			if (bandas_aux.size() > 0) {
				JRibbonBand[] bandas = new JRibbonBand[bandas_aux.size()];
				for (int i = 0; i < bandas_aux.size(); i++) {
					bandas[i] = bandas_aux.get(i);
				}

				RibbonTask task = new RibbonTask(titulo.getDescripcion(),
						bandas);

				this.getRibbon().addTask(task);
			}
		}
	}

	// public JPopupMenu getMenuVentanas() {
	// JPopupMenu popupMenu = new JPopupMenu();
	// boolean hayVentanas = false;
	//
	// // JInternalFrame[] ventanas = Sys.desktoppane.getAllFrames();
	// // if (ventanas != null) {
	// // for (final JInternalFrame v : ventanas) {
	// // JMenuItem mForm = new JMenuItem(v.getTitle());
	// // popupMenu.add(mForm);
	// // hayVentanas = true;
	// // mForm.addActionListener(new ActionListener() {
	// // @Override
	// // public void actionPerformed(ActionEvent arg0) {
	// // try {
	// // v.moveToFront();
	// // } catch (Exception e) {
	// // }
	// // }
	// // });
	// // }
	// //
	// // if (hayVentanas)
	// // return popupMenu;
	// // }
	//
	// JMenuItem mForm = new JMenuItem("No hay Ventanas Abiertas");
	// popupMenu.add(mForm);
	//
	// return popupMenu;
	//
	// }

	/** Serial version unique id. */
	private static final long serialVersionUID = 1L;
	// private JButton btnVentanas;
	private JSeparator separator;

	public static ResizableIcon getResizableIconFromResource(String resource) {
		return ImageWrapperResizableIcon.getIcon(
				MainFrame.class.getResource(resource), new Dimension(48, 48));
	}

	public static ResizableIcon getResizableIconFromResource16x16(
			String resource) {
		return ImageWrapperResizableIcon.getIcon(
				MainFrame.class.getResource(resource), new Dimension(10, 10));
	}
}
