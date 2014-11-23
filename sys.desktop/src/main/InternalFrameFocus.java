package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class InternalFrameFocus {

	private static final int MAX = 5;
	private ArrayList<MyFrame> frames = new ArrayList<MyFrame>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			// @Override
			public void run() {
				new InternalFrameFocus().createAndShowGUI();
			}
		});
	}
	
	

	void createAndShowGUI() {

		JDesktopPane desktop = new JDesktopPane();
		desktop.setPreferredSize(new Dimension(300, 200));
		for (int i = 1; i <= MAX; i++) {
			MyFrame frame = new MyFrame(desktop, "F" + i, i * 20);
			frames.add(frame);
		}

		JMenu menu = new JMenu("Focus");
		for (int i = 0; i < MAX; i++) {
			menu.add(new JMenuItem(frames.get(i).getAction()));
		}
		JMenuBar bar = new JMenuBar();
		bar.add(menu);

		JFrame f = new JFrame("InternalFrameFocus");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(desktop);
		f.setJMenuBar(bar);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

class MyFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private Action action;

	MyFrame(JDesktopPane desktop, String name, int offset) {
		this.setSize(120, 80);
		this.setLocation(offset, offset);
		this.setTitle(name);
		this.setVisible(true);
		desktop.add(this);
		action = new AbstractAction(name) {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent ae) {
				try {
					MyFrame.this.setSelected(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public Action getAction() {
		return action;
	}
}