package vista.controles;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBox<T> extends JComboBox<ComboObject<T>> {

	private static final long serialVersionUID = 1L;

	public ComboBox() {
		super();
		iniciar();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ComboBox(List<ComboObject<T>> content) {
		super(new ArrayListComboBoxObjectModel(content));
		iniciar();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void iniciar() {
		setRenderer(new BasicComboBoxRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null) {
					ComboObject item = (ComboObject) value;
					setText(item.getEtiqueta());
					return this;
				}
				setText("Seleccione Uno");
				return this;
			}
		});
	}
}