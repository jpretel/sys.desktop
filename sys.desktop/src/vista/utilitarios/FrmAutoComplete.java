package vista.utilitarios;

import java.awt.EventQueue;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.controles.IBusqueda;

public class FrmAutoComplete extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAutoComplete frame = new FrmAutoComplete();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmAutoComplete() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * La lista de datos desde la Interface
		 */
		
		List<IBusqueda> data = new ArrayList<IBusqueda>();
		data.add(new Obj("aa12", "ssqws"));
		data.add(new Obj("asa", "ewqsss"));
		data.add(new Obj("ASDVa", "wqsss"));
		data.add(new Obj("adsaa", "qwsss"));
		data.add(new Obj("asdaa", "weqsss"));
		data.add(new Obj("vdaa", "12sss"));
		data.add(new Obj("aaaaaaaaaaaa", "122sss"));

		String[] cabeceras = new String[] { "Codigo", "Descripcion" };
		int[] anchos = new int[] { 90, 200 };
		
		/**
		 * Constructor para TextField this(debería ser el internalframe), 
		 * cabceras y ancho de columnas; y la data en un List<T>
		 */
		
		textField = new JXTextFieldAC(this, cabeceras, anchos, data) {
			private static final long serialVersionUID = 1L;
			/**
			 * Sobreescribir este motodo en caso de que se quiere buscar
			 * a partir del dígito N, por defecto es 3.
			 * En este caso va a mostrar el popup a partir del 2do dígito
			 */
			@Override
			public int getMinimoBusqueda() {
				return 2;
			}
			/**
			 * Sobreescribir este motodo para recuperar el valor seleccionado
			 */
			@Override
			public void cargaDatos() {
				if (getSeleccionado() != null) {
					if (getSeleccionado() instanceof Obj) {
						Obj obj = (Obj) getSeleccionado();
						textField.setText(obj.getCodigo());
						textField_1.setText(obj.getDescripcion());
					}
				}
			}
		};

		textField.setBounds(30, 50, 80, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(111, 50, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		/**
		 * Estos metodos son para que el formulario gane el foco
		 * al momento de cambiar la posición o cambiar el tamaño.
		 */
		getContentPane().addHierarchyBoundsListener(
				new HierarchyBoundsAdapter() {

					@Override
					public void ancestorMoved(HierarchyEvent e) {
						requestFocus();
					}

					@Override
					public void ancestorResized(HierarchyEvent e) {
						requestFocus();
					}
				});
	}
}
/**
 * 
 * @author DSG
 *
 */
class Obj implements IBusqueda {

	private String codigo;
	private String descripcion;

	public Obj(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String[] datoBusqueda() {
		return new String[] { this.codigo, this.descripcion };
	}

	@Override
	public boolean coincideBusqueda(String busqueda) {
		busqueda = busqueda.trim().toLowerCase();
		if (busqueda.isEmpty()) {
			return false;
		}
		if (getCodigo().toLowerCase().startsWith(busqueda)
				|| getDescripcion().toLowerCase().startsWith(busqueda)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return this.getCodigo() + " - " + getDescripcion();
	}

}