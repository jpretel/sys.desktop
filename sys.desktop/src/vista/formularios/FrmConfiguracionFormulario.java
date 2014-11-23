package vista.formularios;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vista.contenedores.CntSysFormulario;
import vista.controles.ArrayListTransferHandler;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtFlujo;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import core.dao.FlujoAprobacionDAO;
import core.dao.FlujoDAO;
import core.dao.SysFormularioDAO;
import core.entity.Flujo;
import core.entity.FlujoAprobacion;
import core.entity.FlujoAprobacionPK;
import core.entity.SysFormulario;

public class FrmConfiguracionFormulario extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel pnlContenido;
	private JLabel lblFormulario;
	private JTabbedPane tabPrivilegios;
	private JPanel pnlFlujoAprobacion;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable tblFlujoAprobacion;
	private TxtFlujo txtflujo;
	private JButton btnCancelar;
	private JButton btnCambiar;
	private FlujoDAO flujoDAO = new FlujoDAO();
	private List<FlujoAprobacion> flujoAprobacion;
	private FlujoAprobacionDAO flujoAprobacionDAO = new FlujoAprobacionDAO();
	private CntSysFormulario cntSysFormulario;

	public static void main(String[] args) {
		new FrmConfiguracionFormulario();
	}

	public FrmConfiguracionFormulario() {
		setTitle("Configuraci\u00F3n por Formulario");
		setMaximizable(false);
		setIconifiable(false);
		setClosable(true);
		setVisible(true);
		setResizable(false);
		this.show();

		pnlContenido = new JPanel();
		this.pnlContenido.setBounds(0, 0, 0, 0);
		getContentPane().add(pnlContenido, BorderLayout.CENTER);
		setBounds(100, 100, 443, 454);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(317, 390, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnCambiar = new JButton("Cambiar");
		btnCambiar.setBounds(218, 390, 89, 23);
		btnCambiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (isValidaVista()) {
					Grabar();
				}
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(this.pnlContenido);
		getContentPane().add(btnCambiar);
		getContentPane().add(btnCancelar);

		this.lblFormulario = new JLabel("Formulario");
		this.lblFormulario.setBounds(20, 29, 81, 14);
		getContentPane().add(this.lblFormulario);

		this.tabPrivilegios = new JTabbedPane(JTabbedPane.TOP);
		this.tabPrivilegios.setBounds(10, 57, 411, 322);
		getContentPane().add(this.tabPrivilegios);

		this.pnlFlujoAprobacion = new JPanel();
		this.tabPrivilegios.addTab("Flujo Aprobaci\u00F3n", null,
				this.pnlFlujoAprobacion, null);
		this.pnlFlujoAprobacion.setLayout(null);

		this.lblNewLabel = new JLabel("Estados");
		this.lblNewLabel.setBounds(10, 11, 150, 14);
		this.pnlFlujoAprobacion.add(this.lblNewLabel);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 35, 386, 248);
		this.pnlFlujoAprobacion.add(this.scrollPane);

		tblFlujoAprobacion = new JTable(new DSGTableModel(new String[] {
				"Cód. Estado", "Estado" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1)
					return false;
				else
					return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "" });
			}
		}) {
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					String idflujo = getFlujoAprobacionTM().getValueAt(row, 0)
							.toString();
					txtflujo.refresValue(idflujo);
				}
			}
		};
		tblFlujoAprobacion.setDragEnabled(true);
		tblFlujoAprobacion.setDropMode(DropMode.INSERT_ROWS);
		tblFlujoAprobacion.setTransferHandler(new ArrayListTransferHandler() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			public boolean importData(JComponent c, Transferable t) {
				
				JTable.DropLocation dl = ((JTable) c).getDropLocation();

				ArrayList alist = null;
				if (!canImport(c, t.getTransferDataFlavors())) {
					return false;
				}

				try {
					if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
						alist = (ArrayList) t
								.getTransferData(localArrayListFlavor);
					} else if (hasSerialArrayListFlavor(t
							.getTransferDataFlavors())) {
						alist = (ArrayList) t
								.getTransferData(serialArrayListFlavor);
					} else {
						return false;
					}
				} catch (UnsupportedFlavorException ufe) {
					System.out.println("importData: unsupported data flavor");
					return false;
				} catch (IOException ioe) {
					System.out.println("importData: I/O exception");
					return false;
				}
				
				
				int row = dl.getRow();
				
				Object[] rowData = alist.toArray();
				
				DefaultTableModel tableModel = (DefaultTableModel) tblFlujoAprobacion.getModel();
				
				tableModel.insertRow(row, rowData);
				
				row = tblFlujoAprobacion.getSelectedRow();
				
				if (row> -1) {
					tableModel.removeRow(row);
				}
				
				Rectangle rect = tblFlujoAprobacion.getCellRect(row, 0, false);
				if (rect != null) {
					tblFlujoAprobacion.scrollRectToVisible(rect);
				}
				
				return true;
			}
		});

		getFlujoAprobacionTM().setScrollAndTable(scrollPane, tblFlujoAprobacion);
		getFlujoAprobacionTM().setObligatorios(0, 1);
		getFlujoAprobacionTM().setRepetidos(0);
		getFlujoAprobacionTM().setEditar(true);
		scrollPane.setViewportView(tblFlujoAprobacion);

		txtflujo = new TxtFlujo(tblFlujoAprobacion, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Flujo entity) {
				int row = tblFlujoAprobacion.getSelectedRow();
				if (entity == null) {
					setText("");
					getFlujoAprobacionTM().setValueAt("", row, 0);
					getFlujoAprobacionTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdflujo());
					getFlujoAprobacionTM().setValueAt(entity.getIdflujo(), row, 0);
					getFlujoAprobacionTM().setValueAt(entity.getDescripcion(), row,
							1);
				}
				setSeleccionado(null);
			}
		};
		txtflujo.updateCellEditor();
		txtflujo.setData(flujoDAO.findAll());

		this.cntSysFormulario = new CntSysFormulario(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void afterUpdateData() {
				llenarPorFormulario(getSeleccionado());
			}
		};
		this.cntSysFormulario.setBounds(84, 23, 268, 20);
		cntSysFormulario.setData(new SysFormularioDAO().findAll());
		getContentPane().add(this.cntSysFormulario);

	}

	private DSGTableModel getFlujoAprobacionTM() {
		return (DSGTableModel) tblFlujoAprobacion.getModel();
	}

	public void Grabar() {
		llenarDesdeVista();

		SysFormulario formulario = cntSysFormulario.getSeleccionado();

		flujoAprobacionDAO.borrarPorFormulario(formulario);

		flujoAprobacionDAO.create(flujoAprobacion);

		UtilMensajes.mensaje_alterta("ACTUALIZA_OK");
		this.dispose();
	}

	private void llenarPorFormulario(SysFormulario formulario) {
		flujoAprobacion = new ArrayList<FlujoAprobacion>();

		if (formulario == null) {
			noEdicion();
		} else {
			edicion();
			flujoAprobacion = flujoAprobacionDAO.getPorFormulario(formulario);
		}
		llenarDatos();
	}

	private void llenarDesdeVista() {
		SysFormulario formulario = cntSysFormulario.getSeleccionado();
		if (formulario != null) {
			flujoAprobacion = new ArrayList<FlujoAprobacion>();
			int rows = getFlujoAprobacionTM().getRowCount();
			for (int row = 0; row < rows; row++) {
				String idflujo;
				idflujo = getFlujoAprobacionTM().getValueAt(row, 0).toString();
				Flujo flujo = flujoDAO.find(idflujo);
				FlujoAprobacion f = new FlujoAprobacion();
				FlujoAprobacionPK id = new FlujoAprobacionPK();
				
				id.setIdformulario(formulario.getIdformulario());
				id.setOrden(row);
				
				f.setId(id);
				f.setFlujo(flujo);
				f.setSysFormulario(formulario);
				flujoAprobacion.add(f);
			}
		}
	}

	private void llenarDatos() {
		getFlujoAprobacionTM().limpiar();
		for (FlujoAprobacion f : flujoAprobacion) {
			Flujo flujo;

			flujo = f.getFlujo();

			getFlujoAprobacionTM().addRow(
					new Object[] { flujo.getIdflujo(),
							flujo.getDescripcion() });
		}
	}

	private void noEdicion() {
		getFlujoAprobacionTM().setEditar(false);
		btnCambiar.setEnabled(false);
	}

	private void edicion() {
		getFlujoAprobacionTM().setEditar(true);
		btnCambiar.setEnabled(true);
	}

	public boolean isValidaVista() {
		if (!FormValidador.CntObligatorios(cntSysFormulario))
			return false;
		if (!getFlujoAprobacionTM().esValido())
			return false;
		return true;
	}
}
