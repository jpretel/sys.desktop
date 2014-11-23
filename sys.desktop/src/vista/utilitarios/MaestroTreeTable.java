package vista.utilitarios;

import java.util.Arrays;
import java.util.List;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class MaestroTreeTable {
	public List<String[]> getContent() {
		return content;
	}

	public void setContent(List<String[]> content) {
		this.content = content;
	}

	private String[] headings = {"Column1", "Column2", "Column3", "Column 4"};
	private Node root;
	private DefaultTreeTableModel model;
	private JXTreeTable table;
	
	private List<String[]> content;
	
	public MaestroTreeTable(List<String[]> content) {
		this.content = content;
	}
	
	public JXTreeTable getTreeTable(){
		root = new RootNode("Root");
		
		ChildNode myChild = null;
		
		for (String[] data : this.content){
			ChildNode child = new ChildNode(data);
			if (data.length <= 1){
				root.add(child);
				myChild = child;
			} else {
				myChild.add(child);
			}
		}
		
		model = new DefaultTreeTableModel(root, Arrays.asList(headings));
		table = new JXTreeTable(model);
		table.setShowGrid(true, true);
		table.setColumnControlVisible(true);
		table.packAll();
		return table;
	}
}
