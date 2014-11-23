package vista.controles;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import vista.Sys;

public class ModalInternalFrame extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean modal = false;

	@Override
	public void show() {
		super.show();
		if (this.modal) {
			startModal();
		}
	}

	@Override
	public void setVisible(boolean value) {
		super.setVisible(value);
		if (modal) {
			if (value) {
				startModal();
			} else {
				stopModal();
			}
		}
	}

	private synchronized void startModal() {

		try {
			Dimension parentSize = parent.getSize();
			Dimension rootSize = Sys.desktoppane.getSize();
			Dimension frameSize = getSize();
			Point frameCoord = new Point();
			
			frameCoord = SwingUtilities.convertPoint(parent, 0, 0, Sys.desktoppane);
	        int x = (parentSize.width - frameSize.width) / 2 + frameCoord.x;
	        int y = (parentSize.height - frameSize.height) / 2 + frameCoord.y;

	        int ovrx = x + frameSize.width - rootSize.width;
	        int ovry = y + frameSize.height - rootSize.height;
	        x = Math.max((ovrx > 0 ? x - ovrx : x), 0);
	        y = Math.max((ovry > 0 ? y - ovry : y), 0);
	        setBounds(x, y, frameSize.width, frameSize.height);
			
			if (SwingUtilities.isEventDispatchThread()) {
				EventQueue theQueue = getToolkit().getSystemEventQueue();
				while (isVisible()) {
					AWTEvent event = theQueue.getNextEvent();
					Object source = event.getSource();
					boolean dispatch = true;

					if (event instanceof MouseEvent) {
						MouseEvent e = (MouseEvent) event;
						MouseEvent m = SwingUtilities.convertMouseEvent(
								(Component) e.getSource(), e, this);
						if (!this.contains(m.getPoint())
								&& e.getID() != MouseEvent.MOUSE_DRAGGED) {
							dispatch = false;
						}
					}

					if (dispatch) {
						if (event instanceof ActiveEvent) {
							((ActiveEvent) event).dispatch();
						} else if (source instanceof Component) {
							((Component) source).dispatchEvent(event);
						} else if (source instanceof MenuComponent) {
							((MenuComponent) source).dispatchEvent(event);
						} else {
							System.err.println("Unable to dispatch: " + event);
						}
					}
				}
			} else {
				while (isVisible()) {
					wait();
				}
			}
		} catch (InterruptedException ignored) {
		}

	}

	private synchronized void stopModal() {
		notifyAll();
	}

	public void setModal(boolean modal) {
		this.modal = modal;
	}

	public boolean isModal() {
		return this.modal;
	}
	
	public ModalInternalFrame(JInternalFrame parent) {
		this.parent = parent;
	}
	
	JInternalFrame parent;
}