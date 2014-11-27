package com.scrollabledesktop;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * This class provides a custom desktop pane. The drag mode is set to
 * {@link javax.swing.JDesktopPane#OUTLINE_DRAG_MODE outline} by default, the
 * desktop manager is set to {@link com.scrollabledesktop.BaseDesktopManager
 * BaseDesktopManager}, and the look and feel DesktopIconUI is replaced by the
 * blank icon generator, {@link com.scrollabledesktop.EmptyDesktopIconUI
 * EmptyDesktopIconUI}.
 *
 * @author <a href="mailto:tessier@gabinternet.com">Tom Tessier</a>
 * @version 1.0 9-Aug-2001
 */

public class BaseDesktopPane extends JDesktopPane {
	private static final long serialVersionUID = 1L;
	private DesktopScrollPane desktopScrollpane;
	private Image IMG = new ImageIcon(getClass().getResource(
			"/main/resources/iconos/dsg.png")).getImage();

	/**
	 * creates the BaseDesktopPane
	 *
	 * @param desktopScrollpane
	 *            a reference to DesktopScrollPane
	 */
	public BaseDesktopPane(DesktopScrollPane desktopScrollpane) {

		this.desktopScrollpane = desktopScrollpane;

		// setup the UIManager to replace the look and feel DesktopIconUI
		// with an empty one (EmptyDesktopIconUI) so that the desktop icon
		// for the internal frame is not painted
		// (ie: when internal frame iconified...)

		UIDefaults defaults = UIManager.getDefaults();
		defaults.put("DesktopIconUI", getClass().getPackage().getName()
				+ ".EmptyDesktopIconUI");

		// set up some defaults
		setDesktopManager(new BaseDesktopManager(this));

		// pre-1.3 code (has no effect in JFC implementations before Swing 1.1.1
		// Beta 1)
		// putClientProperty("JDesktopPane.dragMode", "outline");
		//
		// replace the following line with the above to execute under JDK 1.2
		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

	}

	public void paintChildren(Graphics g) {
		// g.drawImage(IMG, 0, 0, getWidth(), getHeight(), this);
		int x, y;
		x = 403;
		y = 140;
		g.drawImage(IMG, getWidth() - x, getHeight() - y, x, y, this);
		super.paintChildren(g);
	}

	/**
	 * returns the view rectangle associated with the
	 * {@link com.scrollabledesktop.DesktopScrollPane DesktopScrollPane}
	 * viewport
	 *
	 * @return the Rectangle object of the viewport
	 */
	public Rectangle getScrollPaneRectangle() {
		return desktopScrollpane.getViewport().getViewRect();
	}

	/**
	 * propogates the removeAssociatedComponents() call to
	 * {@link com.scrollabledesktop.DesktopScrollPane DesktopScrollPane}
	 *
	 * @param f
	 *            the internal frame whose associated components are to be
	 *            removed
	 */
	public void removeAssociatedComponents(BaseInternalFrame f) {
		desktopScrollpane.removeAssociatedComponents(f);
	}

	/**
	 * propogates the resizeDesktop() call to
	 * {@link com.scrollabledesktop.DesktopScrollPane DesktopScrollPane}
	 */
	public void resizeDesktop() {
		desktopScrollpane.resizeDesktop();
	}

}