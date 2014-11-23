package vista;

import static vista.utilitarios.UtilMensajes.mensaje_alterta;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vista.formularios.FrmLogin;

import com.scrollabledesktop.JScrollableDesktopPane;

import controlador.Mensajes;
import core.DAOConfig;
import core.dao.ConceptoDAO;
import core.dao.EmpresaDAO;
import core.dao.GrupoUsuarioDAO;
import core.dao.MonedaDAO;
import core.dao.UsuarioDAO;
import core.entity.Empresa;
import core.entity.GrupoUsuario;
import core.entity.Moneda;
import core.entity.Usuario;
import core.inicio.ConectionManager;
import core.inicio.ConfigInicial;
import core.inicio.SysCfgInicio;
import core.security.Encryption;

public class Sys {
	public static final String EDICION = "EDICION";
	public static final String VISTA = "VISTA";
	public static final String NUEVO = "NUEVO";

	public final static String SYS_CONFIG = "config.properties";

	public static SysCfgInicio cfgInicio;

	public static Usuario usuario;
	public static Empresa empresa;
	public static Moneda moneda_of;
	public static Moneda moneda_ex;
	public static JScrollableDesktopPane desktoppane;
	public static Mensajes mensajes;

	private FrmSysConfig frm;
	public static MainFrame mainF;

	private String opcion;

	public static void main(String[] args) {
		try {
			salir: for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				switch (info.getName()) {
				case "GTK+":
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break salir;
				case "Windows":
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break salir;
				default:
					javax.swing.UIManager
							.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
		}
		Sys sys = null;
		if (args != null && args.length > 0 && args[0] != null) {
			sys = new Sys(args[0]);
		} else {
			sys = new Sys("");
		}
		sys.iniciar();
	}

	public Sys(String opcion) {
		this.opcion = opcion;
	}

	public void iniciar() {
		mensajes = new Mensajes("ESPANOL");

		File sys_file = new File(SYS_CONFIG);
		String[] datos = null;
		frm = new FrmSysConfig();
		frm.setLocationRelativeTo(null);
		boolean isOK;
		if (sys_file.exists()) {
			System.out.println("Existe");
			datos = ConfigInicial.LlenarConfig();
			if (datos != null) {
				cfgInicio = new SysCfgInicio();

				cfgInicio.setServidor(datos[0]);
				cfgInicio.setBase_datos(datos[1]);
				cfgInicio.setUsuario(datos[2]);
				cfgInicio.setClave(datos[3]);
				cfgInicio.setGestor(datos[4]);

				if (this.opcion.equals("UPDATE")) {
					cfgInicio.setTipo_creacion("UPDATE");
				}
				
				isOK = ConectionManager.isConexionOK(cfgInicio, null);
				
				if (isOK) {

					Map<String, String> persistenceMap = new HashMap<String, String>();
					
					persistenceMap.put("javax.persistence.jdbc.url",
							Sys.cfgInicio.getURL());
					
					persistenceMap.put("javax.persistence.jdbc.driver",
							Sys.cfgInicio.getDriver());
					

					persistenceMap.put("javax.persistence.jdbc.user",
							Sys.cfgInicio.getUsuario());
					persistenceMap.put("javax.persistence.jdbc.password",
							Sys.cfgInicio.getClave());
					if (Sys.cfgInicio.getTipo_creacion() != null
							&& !Sys.cfgInicio.getTipo_creacion().isEmpty()) {
						if (Sys.cfgInicio.getTipo_creacion().equals("UPDATE")) {
							persistenceMap.put("eclipselink.ddl-generation",
									"create-or-extend-tables");
						}
						if (Sys.cfgInicio.getTipo_creacion().equals("DROP")) {
							persistenceMap.put("eclipselink.ddl-generation",
									"drop-and-create-tables");
						}
					}
					
					DAOConfig.entityFactory = Persistence.createEntityManagerFactory(
							"sys.dao", persistenceMap);
					
					ConceptoDAO cDAO = new ConceptoDAO();
					System.out.println(cDAO.findAll());
					
					
					abrir();
				} else {
					mensaje_alterta("ERROR_CONFIG");
					frm.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							ConfigInicial.CrearConfig(frm.getCfgInicio());
							iniciar();
						}
					});
					frm.setVisible(true);
				}
			}
		} else {
			mensaje_alterta("NO_HAY_CONFIG");
			frm.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					ConfigInicial.CrearConfig(frm.getCfgInicio());
					iniciar();
				}
			});
			frm.setVisible(true);
		}
	}

	public void abrir() {
		
		frm.setVisible(false);
		
		EmpresaDAO empresaDAO = new EmpresaDAO();

		MonedaDAO monedaDAO = new MonedaDAO();

		GrupoUsuario grpAdmin = new GrupoUsuarioDAO().find("ADM");

		if (grpAdmin == null) {
			grpAdmin = new GrupoUsuario();
			grpAdmin.setIdgrupoUsuario("ADM");
			grpAdmin.setDescripcion("Grupo de Administradores");
			grpAdmin.setEsAdministrador(1);
			new GrupoUsuarioDAO().create(grpAdmin);
		}

		if (new UsuarioDAO().getTotalPorGrupoUsuario(grpAdmin) == 0) {
			Usuario u = new Usuario();
			u.setIdusuario("ADMINISTRADOR");
			u.setEstado(1);
			u.setGrupoUsuario(grpAdmin);
			u.setNombres("USUARIO ADMINISTRADOR");
			u.setClave(Encryption.pss_encrypt("administrador"));
			new UsuarioDAO().create(u);
		}

		Empresa empresa = empresaDAO.find('0');

		if (empresa == null) {
			empresa = new Empresa();
			empresa.setId('0');
			empresa.setRazon_social("Nueva Empresa");
			empresa.setRuc("12345678901");
			empresa.setDireccion("Nueva Dirección");
			empresaDAO.create(empresa);
		}

		Sys.empresa = empresa;

		// Monedas
		Moneda mof = monedaDAO.getPorTipo(0);

		Moneda mex = monedaDAO.getPorTipo(1);

		Sys.moneda_of = mof;
		Sys.moneda_ex = mex;

		if (mof == null) {
			System.out.println("No hay moneda oficial");
		}

		if (mex == null) {
			System.out.println("No hay moneda extranjera");
		}

		FrmLogin frm = new FrmLogin();
		frm.setVisible(true);

		frm.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				iniciaMainFrame();
			}
		});
	}

	private void iniciaMainFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

}
