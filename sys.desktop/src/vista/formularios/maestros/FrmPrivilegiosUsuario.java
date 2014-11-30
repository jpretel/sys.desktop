package vista.formularios.maestros;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import vista.contenedores.CntUsuario;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTableModel;
import vista.controles.celleditor.TxtAlmacen;
import vista.controles.celleditor.TxtSucursal;
import vista.utilitarios.FormValidador;
import vista.utilitarios.UtilMensajes;
import core.dao.AlmacenDAO;
import core.dao.PrivUsuarioAlmacenDAO;
import core.dao.SucursalDAO;
import core.dao.UsuarioDAO;
import core.entity.Almacen;
import core.entity.AlmacenPK;
import core.entity.PrivUsuarioAlmacen;
import core.entity.Sucursal;
import core.entity.Usuario;

public class FrmPrivilegiosUsuario extends DSGInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel pnlContenido;
	private CntUsuario cntUsuario;
	private JLabel lblUsuario;
	private JTabbedPane tabPrivilegios;
	private JPanel pnlAlmacenes;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable tblAlmUsuario;
	private TxtSucursal txtsucursal;
	private TxtAlmacen txtalmacen;
	private JButton btnCancelar;
	private JButton btnCambiar;
	private SucursalDAO sucursalDAO = new SucursalDAO();
	private AlmacenDAO almacenDAO = new AlmacenDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private List<PrivUsuarioAlmacen> privUsuarioAlmacen;
	private PrivUsuarioAlmacenDAO privUsuarioAlmacenDAO = new PrivUsuarioAlmacenDAO();

	public static void main(String[] args) {
		new FrmPrivilegiosUsuario();
	}

	public FrmPrivilegiosUsuario() {
		setTitle("Privilegios por Usuario");
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

		this.cntUsuario = new CntUsuario() {
			private static final long serialVersionUID = 1L;

			@Override
			public void afterUpdateData() {
				llenarPorUsuario(getSeleccionado());
			}
		};
		cntUsuario.setData(usuarioDAO.findAll());
		this.cntUsuario.setBounds(76, 26, 311, 20);
		getContentPane().setLayout(null);
		getContentPane().add(this.pnlContenido);
		getContentPane().add(this.cntUsuario);
		getContentPane().add(btnCambiar);
		getContentPane().add(btnCancelar);

		this.lblUsuario = new JLabel("Usuario");
		this.lblUsuario.setBounds(20, 29, 46, 14);
		getContentPane().add(this.lblUsuario);

		this.tabPrivilegios = new JTabbedPane(JTabbedPane.TOP);
		this.tabPrivilegios.setBounds(10, 57, 411, 322);
		getContentPane().add(this.tabPrivilegios);

		this.pnlAlmacenes = new JPanel();
		this.tabPrivilegios.addTab("Almacenes", null, this.pnlAlmacenes, null);
		this.pnlAlmacenes.setLayout(null);

		this.lblNewLabel = new JLabel("Almacenes por Usuario");
		this.lblNewLabel.setBounds(10, 11, 150, 14);
		this.pnlAlmacenes.add(this.lblNewLabel);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 35, 386, 248);
		this.pnlAlmacenes.add(this.scrollPane);

		tblAlmUsuario = new JTable(new DSGTableModel(new String[] {
				"Cód. Sucursal", "Sucursal", "Cód. Almacen", "Almacen" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean evaluaEdicion(int row, int column) {
				if (column == 1 || column == 3)
					return false;
				else
					return getEditar();
			}

			@Override
			public void addRow() {
				addRow(new Object[] { "", "", "", "" });
			}
		}) {
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle,
					boolean extend) {
				super.changeSelection(row, column, toggle, extend);
				if (row > -1) {
					Sucursal sucursal = null;
					String idsucursal = getAlmUsuarioTM().getValueAt(row, 0)
							.toString();
					String idalmacen = getAlmUsuarioTM().getValueAt(row, 2)
							.toString();
					txtsucursal.refresValue(idsucursal);
					sucursal = sucursalDAO.find(idsucursal);
					if (sucursal == null)
						txtalmacen.setData(null);
					else
						txtalmacen.setData(almacenDAO.getPorSucursal(sucursal));
					txtalmacen.refresValue(idalmacen);
				}
			}
		};

		getAlmUsuarioTM().setScrollAndTable(scrollPane, tblAlmUsuario);
		getAlmUsuarioTM().setObligatorios(0, 1, 2, 3);
		getAlmUsuarioTM().setRepetidos(0, 2);
		getAlmUsuarioTM().setEditar(true);
		scrollPane.setViewportView(tblAlmUsuario);

		txtsucursal = new TxtSucursal(tblAlmUsuario, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Sucursal entity) {
				int row = tblAlmUsuario.getSelectedRow();
				if (entity == null) {
					setText("");
					getAlmUsuarioTM().setValueAt("", row, 0);
					getAlmUsuarioTM().setValueAt("", row, 1);
				} else {
					setText(entity.getIdsucursal());
					getAlmUsuarioTM()
							.setValueAt(entity.getIdsucursal(), row, 0);
					getAlmUsuarioTM().setValueAt(entity.getDescripcion(), row,
							1);
				}
				setSeleccionado(null);
			}
		};
		txtsucursal.updateCellEditor();
		txtsucursal.setData(sucursalDAO.findAll());
		txtalmacen = new TxtAlmacen(tblAlmUsuario, 2) {
			private static final long serialVersionUID = 1L;

			@Override
			public void cargaDatos(Almacen entity) {
				int row = tblAlmUsuario.getSelectedRow();
				if (entity == null) {
					setText("");
					getAlmUsuarioTM().setValueAt("", row, 2);
					getAlmUsuarioTM().setValueAt("", row, 3);
				} else {
					setText(entity.getId().getIdalmacen());
					getAlmUsuarioTM().setValueAt(entity.getId().getIdalmacen(),
							row, 2);
					getAlmUsuarioTM().setValueAt(entity.getDescripcion(), row,
							3);
				}
				setSeleccionado(null);
			}
		};
		txtalmacen.updateCellEditor();

	}

	private DSGTableModel getAlmUsuarioTM() {
		return (DSGTableModel) tblAlmUsuario.getModel();
	}

	public void Grabar() {
		llenarDesdeVista();

		Usuario usuario = cntUsuario.getSeleccionado();

		privUsuarioAlmacenDAO.borrarPorUsuario(usuario);

		privUsuarioAlmacenDAO.create(privUsuarioAlmacen);

		UtilMensajes.mensaje_alterta("ACTUALIZA_OK");
		this.dispose();
	}

	private void llenarPorUsuario(Usuario usuario) {
		privUsuarioAlmacen = new ArrayList<PrivUsuarioAlmacen>();
		System.out.println(usuario);
		if (usuario == null) {
			noEdicion();
		} else {
			edicion();
			privUsuarioAlmacen = privUsuarioAlmacenDAO.getPorUsuario(usuario);
		}
		llenarDatos();
	}

	private void llenarDesdeVista() {
		Usuario usuario = cntUsuario.getSeleccionado();
		if (usuario != null) {
			privUsuarioAlmacen = new ArrayList<PrivUsuarioAlmacen>();
			int rows = getAlmUsuarioTM().getRowCount();
			for (int row = 0; row < rows; row++) {
				PrivUsuarioAlmacen priv = new PrivUsuarioAlmacen();
//				PrivUsuarioAlmacenPK id = new PrivUsuarioAlmacenPK();
				String idsucursal, idalmacen;
				Sucursal sucursal;
				Almacen almacen;
				AlmacenPK pkalmacen = new AlmacenPK();
				idsucursal = String.valueOf(getAlmUsuarioTM()
						.getValueAt(row, 0));
				idalmacen = String
						.valueOf(getAlmUsuarioTM().getValueAt(row, 2));
				sucursal = sucursalDAO.find(idsucursal);
				pkalmacen.setIdsucursal(idsucursal);
				pkalmacen.setIdalmacen(idalmacen);

				almacen = almacenDAO.find(pkalmacen);
				
				priv.setUsuario(usuario);
				priv.setSucursal(sucursal);
				priv.setAlmacen(almacen);
				
				privUsuarioAlmacen.add(priv);
			}
		}
	}

	private void llenarDatos() {
		getAlmUsuarioTM().limpiar();
		for (PrivUsuarioAlmacen priv : privUsuarioAlmacen) {
			Sucursal sucursal;
			Almacen almacen;
			sucursal = priv.getSucursal();
			almacen = priv.getAlmacen();

			getAlmUsuarioTM().addRow(
					new Object[] { sucursal.getIdsucursal(),
							sucursal.getDescripcion(),
							almacen.getId().getIdalmacen(),
							almacen.getDescripcion() });
		}
	}

	private void noEdicion() {
		getAlmUsuarioTM().setEditar(false);
		btnCambiar.setEnabled(false);
	}

	private void edicion() {
		getAlmUsuarioTM().setEditar(true);
		btnCambiar.setEnabled(true);
	}

	public boolean isValidaVista() {
		if (!FormValidador.CntObligatorios(cntUsuario))
			return false;
		if (!getAlmUsuarioTM().esValido())
			return false;
		return true;
	}
}
