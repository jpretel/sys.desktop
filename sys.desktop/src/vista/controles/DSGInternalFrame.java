package vista.controles;

import javax.swing.ImageIcon;

import vista.MainFrame;

import com.scrollabledesktop.BaseInternalFrame;

public class DSGInternalFrame extends BaseInternalFrame {
	
	public static final String EDICION = "EDICION";
	public static final String VISTA = "VISTA";
	public static final String NUEVO = "NUEVO";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DSGInternalFrame() {
		super();
		setFrameIcon(new ImageIcon(
				MainFrame.class.getResource("/main/resources/iconos/logo.png")));
	}

}
