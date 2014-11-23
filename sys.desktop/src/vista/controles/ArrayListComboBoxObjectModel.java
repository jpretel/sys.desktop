package vista.controles;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ArrayListComboBoxObjectModel<T> extends
		AbstractListModel<ComboObject<T>> implements
		ComboBoxModel<ComboObject<T>> {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<ComboObject<T>> listItems;

	private ComboObject<T> selectedItem;

	public ArrayListComboBoxObjectModel(List<ComboObject<T>> listItems) {
		this.listItems = listItems;
	}

	@Override
	public ComboObject<T> getElementAt(int i) {
		return listItems.get(i);
	}

	@Override
	public int getSize() {
		return listItems.size();
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(Object newValue) {
		selectedItem = (ComboObject<T>) newValue;
	}

}