package vista.combobox;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBox extends JComboBox<String[]> {

	private static final long serialVersionUID = 1L;
	
	private int posDescripcion;
	@SuppressWarnings("unchecked")
	public ComboBox(List<String[]> content, int posDescripcion) {
		super(new ArrayListComboBoxModel(content));
		this.posDescripcion = posDescripcion;
		
		setRenderer(new BasicComboBoxRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null) {
					String[] item = (String[]) value;
					setText(item[ComboBox.this.posDescripcion]);
					return this;
				}
				setText("Seleccione Uno");
				return this;
			}
		});
	}
	

}