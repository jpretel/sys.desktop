package vista.formularios.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTreeTableModel;
import vista.utilitarios.StringUtils;
import vista.utilitarios.UtilMensajes;
import core.dao.ConsumidorDAO;
import core.entity.Consumidor;

public class FrmConsumidor extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Consumidor consumidor;

	private ConsumidorDAO consumidorDAO = new ConsumidorDAO();

	private List<Consumidor> consumidores = new ArrayList<Consumidor>();

	private Consumidor consumidorPADRE = new Consumidor();

	// private List<Consumidor> treeConsumidores = new ArrayList<>();

	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JXTreeTable tblLista = new JXTreeTable();

	JScrollPane scrollPane;
	JCheckBox chkEsConsumidorInicial;

	public FrmConsumidor() {
		super("Centros de Costo");
		scrollPane = new JScrollPane(tblLista);

		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();

						if (selectedRow >= 0) {
							TreePath path = tblLista.getPathForRow(selectedRow);
							Object object = path.getLastPathComponent();
							if (object instanceof Consumidor) {
								setConsumidor((Consumidor) object);
							} else {
								setConsumidor(null);
							}
						} else {
							setConsumidor(null);
						}
						llenar_datos();
					}
				});

		scrollPane.setBounds(10, 10, 207, 273);

		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(248, 12, 46, 14);

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(248, 40, 75, 14);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(294, 9, 86, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(15, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(321, 37, 184, 20);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		chkEsConsumidorInicial = new JCheckBox("Es Consumidor Inicial");
		
		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescripcin, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(chkEsConsumidorInicial))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtDescripcion, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
							.addGap(79)))
					.addGap(4))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chkEsConsumidorInicial))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescripcin, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(3))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlContenido.setLayout(groupLayout);
		iniciar();
	}

	@Override
	public void nuevo() {
		setConsumidorPADRE(null);
		chkEsConsumidorInicial.setSelected(true);
		if (getConsumidor() != null) {
			setConsumidorPADRE(getConsumidor());
			chkEsConsumidorInicial.setSelected(false);
		}
		setConsumidor(new Consumidor());
	}

	@Override
	public void grabar() {
		getConsumidorDAO().crear_editar(getConsumidor());
	}
	
	@Override
	public void llenarDesdeVista() {
		getConsumidor().setIdconsumidor(txtCodigo.getText());
		getConsumidor().setDescripcion(txtDescripcion.getText());
		if (getEstado().equals(NUEVO)) {
			if ((getConsumidorPADRE() == null || chkEsConsumidorInicial
					.isSelected())) {
				getConsumidor().setConsumidor(null);
				String jerarquia = "001";
				int max = 0;
				for (Consumidor c : getConsumidores()) {
					int hjerarquia = Integer.parseInt(c.getJerarquia().trim());
					if (hjerarquia > max){
						max = hjerarquia;
					}
				}
				if (max > 0)
					jerarquia = StringUtils._right("000" + String.valueOf(max + 1),3);
				getConsumidor().setJerarquia(jerarquia);
			} else {
				getConsumidor().setConsumidor(getConsumidorPADRE());
				String jerarquia = "001";
				int max = 0;
				for (Consumidor c : getConsumidorPADRE().getConsumidors()) {
					int hjerarquia = Integer.parseInt(StringUtils._right(c.getJerarquia(),3));
					if (hjerarquia > max){
						max = hjerarquia;
					}
				}
				if (max > 0)
					jerarquia = StringUtils._right("000" + String.valueOf(max + 1),3);
				getConsumidor().setJerarquia(jerarquia);
			}
		}
	}
	
	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Código");
			this.txtCodigo.requestFocus();
			return false;
		}
		if (getEstado().equals(NUEVO)) {
			if (getConsumidorDAO().find(this.txtCodigo.getText().trim()) != null) {
				UtilMensajes.mensaje_alterta("CODIGO_EXISTE");
				this.txtCodigo.requestFocus();
				return false;
			}
		}
		if (this.txtDescripcion.getText().trim().isEmpty()) {
			UtilMensajes.mensaje_alterta("DATO_REQUERIDO", "Descripción");
			this.txtDescripcion.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void llenar_datos() {
		limpiarVista();
		if (getConsumidor() != null) {
			txtCodigo.setText(getConsumidor().getIdconsumidor());
			txtDescripcion.setText(getConsumidor().getDescripcion());
		}
	}

	@Override
	public void llenar_lista() {
		ConsumidorTreeTableModel cttm = new ConsumidorTreeTableModel(
				getConsumidores());
		tblLista.setTreeTableModel(cttm);
	}

	public void llenar_arbol(Consumidor padre, List<Consumidor> lista) {
		padre.setConsumidors(new ArrayList<Consumidor>());

		for (Consumidor hijo : lista) {
			if (hijo.getConsumidor() != null
					&& padre.getIdconsumidor().equals(hijo.getConsumidor().getIdconsumidor())) {
				padre.addConsumidor(hijo);
				llenar_arbol(hijo, lista);
			}
		}
	}

	@Override
	public void llenar_tablas() {
		List<Consumidor> lista = new ArrayList<>();
		lista = getConsumidorDAO().findAllOrderByJerarquia();
		setConsumidores(new ArrayList<Consumidor>());
		for (Consumidor c : lista) {
			if (c.getConsumidor() == null) {
				getConsumidores().add(c);
				llenar_arbol(c, lista);
			}
		}
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO)) {
			txtCodigo.setEditable(true);
			chkEsConsumidorInicial.setVisible(true);
		} else {
			txtCodigo.setEditable(false);
			chkEsConsumidorInicial.setVisible(false);
		}
		txtDescripcion.setEditable(true);
		tblLista.setEnabled(false);
	}

	@Override
	public void vista_noedicion() {
		chkEsConsumidorInicial.setVisible(false);
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		tblLista.setEnabled(true);
	}

	public Consumidor getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(Consumidor consumidor) {
		this.consumidor = consumidor;
	}

	public ConsumidorDAO getConsumidorDAO() {
		return consumidorDAO;
	}

	public void setConsumidorDAO(ConsumidorDAO consumidorDAO) {
		this.consumidorDAO = consumidorDAO;
	}

	public List<Consumidor> getConsumidores() {
		return consumidores;
	}

	public void setConsumidores(List<Consumidor> consumidores) {
		this.consumidores = consumidores;
	}

	@Override
	public void anular() {
		// TODO Auto-generated method stub
	}

	public Consumidor getConsumidorPADRE() {
		return consumidorPADRE;
	}

	public void setConsumidorPADRE(Consumidor consumidorPADRE) {
		this.consumidorPADRE = consumidorPADRE;
	}

	@Override
	public void limpiarVista() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void eliminar() {
		if (getConsumidor() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				getConsumidorDAO().borrarByJerarquia();
				getConsumidorDAO().remove(getConsumidor());
				iniciar();
			}			
		}
		
		if (getConsumidorPADRE() != null) {
			int seleccion = UtilMensajes.msj_error("ELIMINAR_REG");
			
			if (seleccion == 0){
				getConsumidorDAO().borrarPorConsumidor(getConsumidorPADRE());
				getConsumidorDAO().remove(getConsumidorPADRE());
				iniciar();
			}			
		}
	}
}

class ConsumidorTreeTableModel extends MaestroTreeTableModel {

	private Consumidor myroot;

	public ConsumidorTreeTableModel(List<Consumidor> content) {
		super(new String[] { "Código", "Descripción" });
		this.myroot = new Consumidor();
		this.myroot.setConsumidors(content);
	}

	@Override
	public Object getValueAt(Object node, int column) {
		Consumidor treenode = (Consumidor) node;
		switch (column) {
		case 0:
			return treenode.getIdconsumidor();
		case 1:
			return treenode.getDescripcion();
		default:
			return "Unknown";
		}
	}

	@Override
	public Object getChild(Object node, int index) {
		Consumidor treenode = (Consumidor) node;
		return treenode.getConsumidors().get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		Consumidor treenode = (Consumidor) parent;
		return treenode.getConsumidors().size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Consumidor treenode = (Consumidor) parent;
		for (int i = 0; i > treenode.getConsumidors().size(); i++) {
			if (treenode.getConsumidors().get(i) == child) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public Object getRoot() {
		return myroot;
	}

}
