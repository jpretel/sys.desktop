package vista.formularios;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtCuenta;
import vista.controles.celleditor.TxtGrupo;
import vista.controles.celleditor.TxtSubGrupo;
import vista.utilitarios.MaestroTableModel;
import core.dao.CfgCentralizaAlmDAO;
import core.dao.ConceptoDAO;
import core.dao.CuentaDAO;
import core.dao.GrupoDAO;
import core.dao.SubgrupoDAO;
import core.entity.CfgCentralizaAlm;
import core.entity.CfgCentralizaAlmPK;
import core.entity.Concepto;
import core.entity.Cuenta;
import core.entity.Grupo;
import core.entity.Subgrupo;
import core.entity.SubgrupoPK;

public class FrmCfgCentralizaAlm extends AbstractMaestro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tblLista;

	private JTable tblCentraliza;

	private List<Concepto> conceptos;
	private List<CfgCentralizaAlm> centraliza;

	private Concepto concepto;
	private ConceptoDAO conceptoDAO = new ConceptoDAO();
	private GrupoDAO grupoDAO = new GrupoDAO();
	private SubgrupoDAO subgrupoDAO = new SubgrupoDAO();
	private CuentaDAO cuentaDAO = new CuentaDAO();

	private CfgCentralizaAlmDAO centralizaDAO = new CfgCentralizaAlmDAO();
	private JScrollPane scrollPaneNum;

	private TxtGrupo txtgrupo;
	private TxtSubGrupo txtsubgrupo;
	private TxtCuenta txtCuentaDebe;
	private TxtCuenta txtCuentaHaber;

	public FrmCfgCentralizaAlm() {
		super("Configuracion de Centralización");

		JScrollPane scrollPane = new JScrollPane();

		tblLista = new JTable(new MaestroTableModel());
		scrollPane.setViewportView(tblLista);

		tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblCentraliza = new JTable(new DSGTableModel(new String[] {
				"Cod. Grupo", "Grupo", "Cod. Subgrupo", "Sub Grupo", "Debe",
				"Haber" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 3)
					return false;
				return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", "", "", "", "" });
			}

		}) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					String idgrupo = getCentralizaTM().getValueAt(row, 0)
							.toString();
					String idsubgrupo = getCentralizaTM().getValueAt(row, 2)
							.toString();
					String idcuentadebe = getCentralizaTM().getValueAt(row, 4)
							.toString();

					String idcuentahaber = getCentralizaTM().getValueAt(row, 5)
							.toString();

					txtgrupo.refresValue(idgrupo);

					txtsubgrupo.setData(subgrupoDAO.findAllbyGrupo(grupoDAO
							.find(idgrupo)));
					txtsubgrupo.refresValue(idsubgrupo);

					txtCuentaDebe.refresValue(idcuentadebe);
					txtCuentaHaber.refresValue(idcuentahaber);
				}
			}
		};

		tblCentraliza.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneNum = new JScrollPane(tblCentraliza,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tblCentraliza.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		getCentralizaTM().setNombre_detalle("Grupos de Productos");
		getCentralizaTM().setObligatorios(0, 1, 2, 3, 4, 5);
		getCentralizaTM().setRepetidos(0, 2);
		getCentralizaTM().setScrollAndTable(scrollPaneNum, tblCentraliza);

		txtgrupo = new TxtGrupo(tblCentraliza, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Grupo entity) {
				int row = tblCentraliza.getSelectedRow();
				if (entity == null) {
					getCentralizaTM().setValueAt("", row, 0);
					getCentralizaTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdgrupo());
					getCentralizaTM().setValueAt(entity.getIdgrupo(), row, 0);
					getCentralizaTM().setValueAt(entity.getDescripcion(), row,
							1);
				}
				setSeleccionado(null);
			}
		};

		txtsubgrupo = new TxtSubGrupo(tblCentraliza, 2) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Subgrupo entity) {
				int row = tblCentraliza.getSelectedRow();
				if (entity == null) {
					setText("");
					getCentralizaTM().setValueAt("", row, 2);
					getCentralizaTM().setValueAt("", row, 3);
				} else {
					setText(entity.getId().getIdsubgrupo());
					getCentralizaTM().setValueAt(
							entity.getId().getIdsubgrupo(), row, 2);
					getCentralizaTM().setValueAt(entity.getDescripcion(), row,
							3);
				}
				setSeleccionado(null);
			}
		};

		txtCuentaDebe = new TxtCuenta(tblCentraliza, 4) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Cuenta entity) {
				int row = tblCentraliza.getSelectedRow();
				if (entity == null) {
					getCentralizaTM().setValueAt("", row, 4);
				} else {
					setText(entity.getIdcuenta());
					getCentralizaTM().setValueAt(entity.getIdcuenta(), row, 4);
				}
			}
		};

		txtCuentaHaber = new TxtCuenta(tblCentraliza, 5) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Cuenta entity) {
				int row = tblCentraliza.getSelectedRow();
				if (entity == null) {
					getCentralizaTM().setValueAt("", row, 5);
				} else {
					setText(entity.getIdcuenta());
					getCentralizaTM().setValueAt(entity.getIdcuenta(), row, 5);
				}
			}
		};

		txtgrupo.updateCellEditor();
		txtsubgrupo.updateCellEditor();
		txtCuentaDebe.updateCellEditor();
		txtCuentaHaber.updateCellEditor();

		GroupLayout groupLayout = new GroupLayout(pnlContenido);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(10)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								202, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(this.scrollPaneNum,
								GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(11)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(this.scrollPaneNum,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 230,
												Short.MAX_VALUE)
										.addComponent(scrollPane,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 230,
												Short.MAX_VALUE)).addGap(11)));
		pnlContenido.setLayout(groupLayout);
		tblLista.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						int selectedRow = tblLista.getSelectedRow();
						if (selectedRow >= 0)
							setConcepto(getConceptos().get(selectedRow));
						else
							setConcepto(null);
						llenar_datos();
					}
				});
		iniciar();
	}

	@Override
	public void DoNuevo() {
		// TODO Auto-generated method stub
		// super.DoNuevo();
	}

	@Override
	public void nuevo() {
		//
	}

	@Override
	public void anular() {

	}

	@Override
	public void grabar() {
		for (CfgCentralizaAlm c : centralizaDAO.aEliminar(getConcepto(),
				getCentraliza())) {
			centralizaDAO.remove(c);
		}

		for (CfgCentralizaAlm c : centraliza) {
			centralizaDAO.crear_editar(c);
		}
	}

	@Override
	public void llenarDesdeVista() {
		setCentraliza(new ArrayList<CfgCentralizaAlm>());
		String idconcepto;
		idconcepto = getConcepto().getIdconcepto();

		for (int i = 0; i < getCentralizaTM().getRowCount(); i++) {
			CfgCentralizaAlmPK id = new CfgCentralizaAlmPK();
			CfgCentralizaAlm alm = new CfgCentralizaAlm();

			id.setIdconcepto(idconcepto);
			id.setIdgrupo(getCentralizaTM().getValueAt(i, 0).toString());
			id.setIdsubgrupo(getCentralizaTM().getValueAt(i, 2).toString());

			Cuenta debe, haber;
			debe = new Cuenta();
			debe.setIdcuenta(getCentralizaTM().getValueAt(i, 4).toString());

			haber = new Cuenta();
			haber.setIdcuenta(getCentralizaTM().getValueAt(i, 5).toString());

			alm.setId(id);
			alm.setCta_debe(debe);
			alm.setCta_haber(haber);

			getCentraliza().add(alm);
		}
	}

	@Override
	public void llenar_datos() {
		getCentralizaTM().limpiar();
		if (getConcepto() != null) {
			setCentraliza(null);
			setCentraliza(centralizaDAO.getPorConcepto(getConcepto()));
			for (CfgCentralizaAlm alm : getCentraliza()) {
				alm.setGrupo(grupoDAO.find(alm.getId().getIdgrupo()));
				SubgrupoPK ids = new SubgrupoPK();
				ids.setIdgrupo(alm.getId().getIdgrupo());
				ids.setIdsubgrupo(alm.getId().getIdsubgrupo());
				alm.setSubgrupo(subgrupoDAO.find(ids));

				getCentralizaTM().addRow(
						new Object[] { alm.getGrupo().getIdgrupo(),
								alm.getGrupo().getDescripcion(),
								alm.getSubgrupo().getId().getIdsubgrupo(),
								alm.getSubgrupo().getDescripcion(),
								alm.getCta_debe().getIdcuenta(),
								alm.getCta_haber().getIdcuenta() });
			}
		}
	}

	@Override
	public boolean isValidaVista() {
		if (!validarDetalles()) {
			return false;
		}
		return true;
	}

	private boolean validarDetalles() {
		return getCentralizaTM().esValido();
	}

	@Override
	public void llenar_lista() {
		tblLista.setFillsViewportHeight(true);

		MaestroTableModel model = (MaestroTableModel) tblLista.getModel();
		model.limpiar();
		for (Concepto obj : getConceptos()) {
			model.addRow(new Object[] { obj.getIdconcepto(),
					obj.getDescripcion() });
		}
		if (getConceptos().size() > 0) {
			setConcepto(getConceptos().get(0));
			tblLista.setRowSelectionInterval(0, 0);
		}
	}

	@Override
	public void llenar_tablas() {
		setConceptos(conceptoDAO.findAll());
		actualiza_tablas();
	}

	@Override
	public void vista_edicion() {
		getCentralizaTM().setEditar(true);

	}

	@Override
	public void vista_noedicion() {
		getCentralizaTM().setEditar(false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualiza_objeto(Object entidad) {
		// TODO Auto-generated method stub

	}

	public DSGTableModel getCentralizaTM() {
		return ((DSGTableModel) tblCentraliza.getModel());
	}

	@Override
	public void eliminar() {
		// ////
	}

	public void actualiza_tablas() {
		if (txtgrupo != null)
			txtgrupo.setData(grupoDAO.findAll());
		if (txtsubgrupo != null)
			txtsubgrupo.setData(null);
		if (txtCuentaDebe != null)
			txtCuentaDebe.setData(cuentaDAO.findAll());
		if (txtCuentaHaber != null)
			txtCuentaHaber.setData(cuentaDAO.findAll());
	}

	public List<Concepto> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<Concepto> conceptos) {
		this.conceptos = conceptos;
	}

	public List<CfgCentralizaAlm> getCentraliza() {
		return centraliza;
	}

	public void setCentraliza(List<CfgCentralizaAlm> centraliza) {
		this.centraliza = centraliza;
	}

	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	public CfgCentralizaAlmDAO getCentralizaDAO() {
		return centralizaDAO;
	}

	public void setCentralizaDAO(CfgCentralizaAlmDAO centralizaDAO) {
		this.centralizaDAO = centralizaDAO;
	}
}