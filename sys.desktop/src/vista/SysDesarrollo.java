package vista;
	
import static vista.Sys.cfgInicio;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.persistence.Tuple;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.scrollabledesktop.JScrollableDesktopPane;

import core.dao.KardexDAO;
import core.dao.ProductoDAO;
import core.dao.UsuarioDAO;
import core.entity.Producto;
import core.entity.Usuario;
import core.inicio.ConfigInicial;
import core.inicio.SysCfgInicio;
	
public class SysDesarrollo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JScrollableDesktopPane desktopPane;
	public SysDesarrollo() {
		//setSize(new Dimension(529, 463));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//Se establece el tamaño minimo del SysDesarrollo
		setMinimumSize(new Dimension(800, 600)); 
		setTitle(":::FORMULRIO PARA PRUEBAS:::");
		//Termina SysDesarrollo al darle click en el boton Cerrar del extremo superior derechi 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		desktopPane = new JScrollableDesktopPane();
		Sys.desktoppane = desktopPane;
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		String[] datos = null;
		datos = ConfigInicial.LlenarConfig();
		cfgInicio = new SysCfgInicio();
		cfgInicio.setServidor(datos[0]);
		cfgInicio.setBase_datos(datos[1]);
		cfgInicio.setUsuario(datos[2]);
		cfgInicio.setClave(datos[3]);
		
		Usuario u = new UsuarioDAO().find("ADMINISTRADOR");
		
		System.out.println(u.getIdusuario());
		
		ProductoDAO pdao = new ProductoDAO();
		Producto p = pdao.find("001");
		KardexDAO kdao = new KardexDAO();
		
		//System.out.println(kdao.getSaldoAntesDe(20140926, p, null, null));
		//System.out.println(kdao.getMovimientos(20140925, 20140927, p, null, null));
		
		for (Tuple t : kdao.getSaldosSucursalAlmacen(20140925,null,null)){
			System.out.println(t.get("producto"));
			System.out.println(t.get("cantidad"));
		}
		
		//		SysModuloDAO moduloDAO = new SysModuloDAO();
//		
//		moduloDAO.getModulos(u.getGrupoUsuario());
		/*
		cfgInicio.setTipo_creacion("");//No Hace Nada
		Sys.mensajes = new Mensajes("ESPANOL");
		SysDesarrollo frm = new SysDesarrollo();
		frm.setVisible(true);		
		
		FrmListaOrdenCompra frm4 = new FrmListaOrdenCompra();
		frm.getDesktopPane().add(frm4);
		
		FrmListaProductos frm5 = new FrmListaProductos();
		frm.getDesktopPane().add(frm5);
		
		
		
		System.out.println(kdao.getSaldoAntesDe(20140926, p, null, null));
		
		FrmUnimedida frm6 = new FrmUnimedida();
		frm.getDesktopPane().add(frm6);
		*/
	}
}