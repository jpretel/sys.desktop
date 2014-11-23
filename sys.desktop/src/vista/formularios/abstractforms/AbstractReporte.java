package vista.formularios.abstractforms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vista.barras.IFormReporte;
import vista.barras.PanelBarraReporte;
import vista.controles.DSGInternalFrame;
import vista.controles.DSGTableModelReporte;

public abstract class AbstractReporte extends DSGInternalFrame implements
		IFormReporte {
	private static final long serialVersionUID = 1L;
	private PanelBarraReporte panelBarraReporte;
	protected JPanel pnlContenido;
	protected JPanel pnlFiltro;
	protected JPanel pnlDatos;
	protected JScrollPane srclDatos;
	protected JTable tblDatos;
	private DSGTableModelReporte model;

	public AbstractReporte() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		this.panelBarraReporte = new PanelBarraReporte();
		FlowLayout flowLayout = (FlowLayout) this.panelBarraReporte.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(this.panelBarraReporte, BorderLayout.NORTH);

		panelBarraReporte.setFrmReporte(this);

		this.pnlContenido = new JPanel();
		getContentPane().add(this.pnlContenido, BorderLayout.CENTER);
		this.pnlContenido.setLayout(new BorderLayout(0, 0));

		this.pnlFiltro = new JPanel();
		this.pnlFiltro.setPreferredSize(new Dimension(10, 60));
		this.pnlFiltro.setMinimumSize(new Dimension(10, 50));
		this.pnlContenido.add(this.pnlFiltro, BorderLayout.NORTH);
		this.pnlFiltro.setLayout(null);

		this.pnlDatos = new JPanel();
		this.pnlContenido.add(this.pnlDatos, BorderLayout.CENTER);
		this.pnlDatos.setLayout(new BorderLayout(0, 0));

		this.srclDatos = new JScrollPane();
		this.pnlDatos.add(this.srclDatos, BorderLayout.CENTER);
		model = new DSGTableModelReporte();
		this.tblDatos = new JTable(model);
		this.srclDatos.setViewportView(this.tblDatos);

		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setResizable(true);
		setBounds(100, 100, 810, 451);
	}

	public DSGTableModelReporte getModel() {
		return model;
	}

	public abstract Object[][] getData();

	public abstract String[] getCabeceras();

	public abstract boolean isFiltrosValidos();

	@Override
	public void doVistaPrevia() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doImprimir() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doExportar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doConsultar() {
		if (isFiltrosValidos()) {
			model = new DSGTableModelReporte(getCabeceras());
			for (Object[] rowData : getData()) {
				model.addRow(rowData);
			}
			tblDatos.setModel(model);
		}
	}
}
