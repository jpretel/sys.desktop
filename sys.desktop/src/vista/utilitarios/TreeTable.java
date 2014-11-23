package vista.utilitarios;

import java.util.Arrays;
import java.util.List;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable {
	public List<String[]> getContent() {
		return content;
	}

	public void setContent(List<String[]> content) {
		this.content = content;
	}

	private String[] headings = {"Código", "Descripción"};
	private Node root;
	private DefaultTreeTableModel model;
	private JXTreeTable table;
	
	private List<String[]> content;
	
	public TreeTable(List<String[]> content, JXTreeTable table) {
		this.content = content;
		this.table = table;
	}
	
	public JXTreeTable getTreeTable() {
		root = new RootNode("Root");
		
		ChildNode myChild = null;
		
		for (String[] data : this.content){
			ChildNode child = new ChildNode(data);
			if (data.length <= 2){
				root.add(child);
				myChild = child;
			} else {
				myChild.add(child);
			}
		}
		
		model = new DefaultTreeTableModel(root, Arrays.asList(headings));
		//table = new JXTreeTable(model);
		table.setTreeTableModel(model);
		table.setShowGrid(true, true);
		table.setColumnControlVisible(true);
		table.packAll();
		return table;
	}
}
