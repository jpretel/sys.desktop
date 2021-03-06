package vista.formularios.modal;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.MenuComponent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.scrollabledesktop.JScrollableDesktopPane;

import vista.Sys;

public class ModalInternalPanel {

	public static Object showInternalDialog(Component parentComponent,
			Container container, HashMap<String, Object> hasReturnValue,
			String title) {
		JInternalFrame frame = createInternalFrame(parentComponent, title,
				container);
		startModal(frame);
		if (hasReturnValue != null)
			return hasReturnValue;
		else
			return null;
	}

	public static Object showInternalDialog(Component parentComponent,
			JInternalFrame iframe, HashMap<String, Object> hasReturnValue) {
		JInternalFrame frame = createInternalFrame(parentComponent, iframe);
		startModal(frame);
		if (hasReturnValue != null)
			return hasReturnValue;
		else
			return null;
	}

	private static JInternalFrame createInternalFrame(
			Component parentComponent, JInternalFrame frame)
			throws RuntimeException {
		// Try to find a JDesktopPane.
//		JLayeredPane toUse = JOptionPane
//				.getDesktopPaneForComponent(parentComponent);
		JScrollableDesktopPane toUse = Sys.desktoppane;
		// If we don't have a JDesktopPane, we try to find a JLayeredPane.
//		if (toUse == null)
//			toUse = JLayeredPane.getLayeredPaneAbove(parentComponent);
		// If this still fails, we throw a RuntimeException.
		if (toUse == null)
			throw new RuntimeException(
					"parentComponent does not have a valid parent");
		frame.setClosable(true);
		
		frame.addInternalFrameListener(new InternalFrameListener() {	
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				Sys.desktoppane.getDesktopMediator().getDesktopResizableToolbar().enableButtons();
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				Sys.desktoppane.getDesktopMediator().getDesktopResizableToolbar().enableButtons();
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
			}
		});
		
		Sys.desktoppane.getDesktopMediator().getDesktopResizableToolbar().disableButtons();
		toUse.add(frame);
		frame.setLayer(JLayeredPane.MODAL_LAYER);

//		frame.pack();
		frame.setVisible(true);

		return frame;
	}

	private static JInternalFrame createInternalFrame(
			Component parentComponent, String title, Container container)
			throws RuntimeException {
		// Try to find a JDesktopPane.
		JLayeredPane toUse = JOptionPane
				.getDesktopPaneForComponent(parentComponent);
		// If we don't have a JDesktopPane, we try to find a JLayeredPane.
		if (toUse == null)
			toUse = JLayeredPane.getLayeredPaneAbove(parentComponent);
		// If this still fails, we throw a RuntimeException.
		if (toUse == null)
			throw new RuntimeException(
					"parentComponent does not have a valid parent");

		JInternalFrame frame = new JInternalFrame(title);

		frame.setContentPane(container);
		frame.setClosable(true);

		toUse.add(frame);
		frame.setLayer(JLayeredPane.MODAL_LAYER);

		frame.pack();
		frame.setVisible(true);

		return frame;
	}

	private static void startModal(JInternalFrame f) {
		// We need to add an additional glasspane-like component directly
		// below the frame, which intercepts all mouse events that are not
		// directed at the frame itself.
		JPanel modalInterceptor = new JPanel();
		modalInterceptor.setOpaque(false);
		JLayeredPane lp = JLayeredPane.getLayeredPaneAbove(f);
		lp.setLayer(modalInterceptor, JLayeredPane.MODAL_LAYER.intValue());
		modalInterceptor.setBounds(0, 0, lp.getWidth(), lp.getHeight());
		modalInterceptor.addMouseListener(new MouseAdapter() {
		});
		modalInterceptor.addMouseMotionListener(new MouseMotionAdapter() {
		});
		lp.add(modalInterceptor);
		f.toFront();

		// We need to explicitly dispatch events when we are blocking the event
		// dispatch thread.
		EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		try {
			while (!f.isClosed()) {
				if (EventQueue.isDispatchThread()) {
					// The getNextEventMethod() issues wait() when no
					// event is available, so we don't need do explicitly
					// wait().
					AWTEvent ev = queue.getNextEvent();
					// This mimics EventQueue.dispatchEvent(). We can't use
					// EventQueue.dispatchEvent() directly, because it is
					// protected, unfortunately.
					if (ev instanceof ActiveEvent)
						((ActiveEvent) ev).dispatch();
					else if (ev.getSource() instanceof Component)
						((Component) ev.getSource()).dispatchEvent(ev);
					else if (ev.getSource() instanceof MenuComponent)
						((MenuComponent) ev.getSource()).dispatchEvent(ev);
					// Other events are ignored as per spec in
					// EventQueue.dispatchEvent
				} else {
					// Give other threads a chance to become active.
					Thread.yield();
				}
			}
		} catch (InterruptedException ex) {
			// If we get interrupted, then leave the modal state.
		} finally {
			// Clean up the modal interceptor.
			lp.remove(modalInterceptor);

			// Remove the internal frame from its parent, so it is no longer
			// lurking around and clogging memory.
			Container parent = f.getParent();
			if (parent != null)
				parent.remove(f);
		}
	}
}