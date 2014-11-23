package vista.combobox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ArrayListComboBoxModel extends AbstractListModel<String[]>
		implements ComboBoxModel<String[]> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] selectedItem;

	private List<String[]> anArrayList;

	public ArrayListComboBoxModel(List<String[]> arrayList) {
		anArrayList = arrayList;
	}

	public Object getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
		selectedItem = (String[]) newValue;
	}

	public int getSize() {
		return anArrayList.size();
	}

	public String[] getElementAt(int i) {
		return anArrayList.get(i);
	}

	public String getSelectedItemValue(int i) {
		return selectedItem[i];
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("ArrayListComboBoxModel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<String[]> lista = new ArrayList<>();

		lista.add(new String[] { "01", "Desc" });

		lista.add(new String[] { "02", "Desc2" });

		lista.add(new String[] { "03", "Desc3" });

		ArrayListComboBoxModel model = new ArrayListComboBoxModel(lista);

		JComboBox<String[]> comboBox = new JComboBox<String[]>(model);
		
		comboBox.setRenderer(new BasicComboBoxRenderer(){
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				if (value != null) {
					String[] item = (String[]) value;
					setText(item[1]);
					return this;
				}
				setText("Seleccione Uno");
				return this;
			}
		});
		
		Container contentPane = frame.getContentPane();
		contentPane.add(comboBox, BorderLayout.NORTH);
		frame.setSize(300, 225);
		frame.setVisible(true);
	}
}
