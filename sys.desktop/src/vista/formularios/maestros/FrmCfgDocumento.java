package vista.formularios.maestros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import vista.controles.JTextFieldLimit;
import vista.formularios.abstractforms.AbstractMaestro;
import vista.utilitarios.MaestroTableModel;
import core.dao.SysDocDAO;
import core.dao.SysDocDocserDAO;
import core.dao.SysDocFlujoDAO;
import core.entity.SysDoc;
import core.entity.SysDocDocser;
import core.entity.SysDocDocserPK;
import core.entity.SysDocFlujo;

public class FrmCfgDocumento extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tblLista;

	private JTable tblnumeradores;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private CfgDocTableModel numeradoresTM = new CfgDocTableModel();
	private SysDoc documento;

	private List<SysDoc> documentos;
	private List<SysDocDocser> numeradores;
	private List<SysDocFlujo> flujos;

	private SysDocDAO documentoDAO = new SysDocDAO();

	private SysDocDocserDAO docnumDAO = new SysDocDocserDAO();

	private SysDocFlujoDAO flujoDAO = new SysDocFlujoDAO();

	private JButton btnILinea;
	private JButton btnBLinea;

	public FrmCfgDocumento() {
		super("Documentos");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 199, 402);

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);
		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPaneNum = new JScrollPane();
		
		tblnumeradores = new JTable(numeradoresTM);
		scrollPaneNum.setViewportView(tblnumeradores);
		tblnumeradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblCdigo = new JLabel("Cdigo");
		lblCdigo.setBounds(213, 18, 66, 14);

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(213, 42, 66, 14);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(298, 15, 67, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new JTextFieldLimit(3, true));

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(298, 39, 126, 20);
		txtDescripcion.setColumns(10);
		txtDescripcion.setDocument(new JTextFieldLimit(75, true));

		btnILinea = new JButton("I Linea");
		btnILinea.setBounds(375, 15, 65, 20);
		btnILinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final Object fila[] = { "", "" };
				numeradoresTM.addRow(fila);
			}
		});

		btnBLinea = new JButton("B Linea");
		btnBLinea.setBounds(446, 15, 83, 20);
		btnBLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ind = tblnumeradores.getSelectedRow();
				if (ind >= 0)
					numeradoresTM.removeRow(ind);
			}
		});
		pnlContenido.setLayout(null);
		pnlContenido.add(scrollPane);
		pnlContenido.add(lblDescripcin);
		pnlContenido.add(lblCdigo);
		pnlContenido.add(txtDescripcion);
		pnlContenido.add(txtCodigo);
		pnlContenido.add(btnILinea);
		pnlContenido.add(btnBLinea);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(219, 143, 403, 250);
		pnlContenido.add(tabbedPane);
		tabbedPane.add(scrollPaneNum);
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setDocumento(getDocumentos().get(selectedRow));
						else
							setDocumento(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void nuevo() {
		setDocumento(new SysDoc());
	}

	@Override
	public void editar() {
		super.editar();
	}

	@Override
	public void anular() {

	}

	@Override
	public void grabar() {
		documentoDAO.crear_editar(getDocumento());
		docnumDAO.borrarPorDocumento(getDocumento());
		for (SysDocDocser num : getNumeradores()) {
			System.out.println(num.getId().getIddocumento());
			System.out.println(num.getId().getSerie());
			System.out.println(num.getId().getIddocumento());
			docnumDAO.create(num);
		}
		/*
		docnumDAO.borrarPorDocumento(getDocumento());
		for (SysDocFlujo num : getFlujos()) {
			flujoDAO.create(num);
		}*/
	}

	public SysDocDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(SysDocDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	public SysDocDocserDAO getDocnumDAO() {
		return docnumDAO;
	}

	public void setDocnumDAO(SysDocDocserDAO docnumDAO) {
		this.docnumDAO = docnumDAO;
	}

	@Override
	public void llenarDesdeVista() {
		getDocumento().setSysDoc(this.txtCodigo.getText().trim());
		getDocumento().setDescripcion(this.txtDescripcion.getText().trim());

		setNumeradores(new ArrayList<SysDocDocser>());

		for (int i = 0; i < numeradoresTM.getRowCount(); i++) {
			SysDocDocserPK id = new SysDocDocserPK();
			SysDocDocser obj = new SysDocDocser();
			
			id.setSysDoc(getDocumento().getSysDoc());
			id.setIddocumento(this.numeradoresTM.getValueAt(i, 0).toString());
			id.setSerie(this.numeradoresTM.getValueAt(i, 1).toString());
			obj.setId(id);
			getNumeradores().add(obj);
		}

		/*
		 * setFlujos(new ArrayList<SysDocFlujo>());
		 * 
		 * for (int i = 0; i < numeradoresTM.getRowCount(); i++) {
		 * SysDocDocserPK id = new SysDocDocserPK(); SysDocDocser obj = new
		 * SysDocDocser();
		 * 
		 * id.setSysDoc(getDocumento().getSysDoc());
		 * id.setIddocumento(this.numeradoresTM.getValueAt(i, 0).toString());
		 * 
		 * obj.setId(id); getNumeradores().add(obj); }
		 */

	}

	@Override
	public void llenar_datos() {
		numeradoresTM.limpiar();
		setNumeradores(new ArrayList<SysDocDocser>());
		if (getDocumento() != null) {
			txtCodigo.setText(getDocumento().getSysDoc());
			txtDescripcion.setText(getDocumento().getDescripcion());
			setNumeradores(docnumDAO.getPorDocumento(getDocumento()));

			for (SysDocDocser num : getNumeradores()) {
				numeradoresTM
						.addRow(new Object[] { num.getId().getIddocumento(),
								num.getId().getSerie(), });
			}
		} else {
			txtCodigo.setText("");
			txtDescripcion.setText("");
		}
	}

	@Override
	public boolean isValidaVista() {
		if (this.txtCodigo.getText().trim().isEmpty())
			return false;
		if (this.txtDescripcion.getText().trim().isEmpty())
			return false;
		return true;
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (SysDoc cuenta : getDocumentos()) {
			model.addRow(new Object[] { cuenta.getSysDoc(),
					cuenta.getDescripcion() });
		}
		if (getDocumentos().size() > 0) {
			setDocumento(getDocumentos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setDocumentos(getDocumentoDAO().findAll());
	}

	@Override
	public void vista_edicion() {
		if (getEstado().equals(NUEVO))
			txtCodigo.setEditable(true);
		else
			txtCodigo.setEditable(false);
		txtDescripcion.setEditable(true);
		numeradoresTM.setEditar(true);
		btnILinea.setEnabled(true);
		btnBLinea.setEnabled(true);

	}

	@Override
	public void vista_noedicion() {
		txtCodigo.setEditable(false);
		txtDescripcion.setEditable(false);
		numeradoresTM.setEditar(false);
		btnILinea.setEnabled(false);
		btnBLinea.setEnabled(false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	public List<SysDocDocser> getNumeradores() {
		return numeradores;
	}

	public void setNumeradores(List<SysDocDocser> numeradores) {
		this.numeradores = numeradores;
	}

	public List<SysDocFlujo> getFlujos() {
		return flujos;
	}

	public void setFlujos(List<SysDocFlujo> flujos) {
		this.flujos = flujos;
	}

	public SysDocFlujoDAO getFlujoDAO() {
		return flujoDAO;
	}

	public void setFlujoDAO(SysDocFlujoDAO flujoDAO) {
		this.flujoDAO = flujoDAO;
	}

	public SysDoc getDocumento() {
		return documento;
	}

	public void setDocumento(SysDoc documento) {
		this.documento = documento;
	}

	public List<SysDoc> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<SysDoc> documentos) {
		this.documentos = documentos;
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
	}
}

class CfgDocTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editar = false;

	public CfgDocTableModel() {
		addColumn("Cod. Documento");
		addColumn("Serie");
	}

	public boolean isCellEditable(int row, int column) {
		return editar;
	}

	public void limpiar() {
		while (getRowCount() != 0) {
			removeRow(0);
		}
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public boolean getEditar() {
		return this.editar;
	}
}
