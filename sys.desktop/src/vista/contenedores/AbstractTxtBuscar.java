package vista.contenedores;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.util.List;

import javax.swing.JTextField;

import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import vista.Sys;

public abstract class AbstractTxtBuscar<T> extends JTextField {
	private static final long serialVersionUID = 1L;
	public JXTextFieldEntityAC<T> txtCodigo;

	public AbstractTxtBuscar(String[] cabeceras, int[] anchos) {

		setForeground(Color.LIGHT_GRAY);
		this.setBounds(152, 11, 100, 30);
		txtCodigo = new JXTextFieldEntityAC<T>(getFormulario(), cabeceras,
				anchos) {
			private static final long serialVersionUID = 1L;

			/*
			 * Sobreescribir este motodo en caso de que se quiere buscar a
			 * partir del dígito N, por defecto es 3. En este caso va a mostrar
			 * el popup a partir del 2do digito
			 */
			@Override
			public int getMinimoBusqueda() {
				return 1;
			}

			/*
			 * Sobreescribir este motodo para recuperar el valor seleccionado
			 */
			@Override
			public void cargaDatos() {
				if (getSeleccionado() != null) {					
					AbstractTxtBuscar.this.cargarDatos(getSeleccionado());
				}
			}

			@Override
			public boolean coincideBusqueda(T dato, String cadena) {
				return AbstractTxtBuscar.this.coincideBusqueda(dato, cadena);
			}

			@Override
			public Object[] entity2Object(T entity) {
				return AbstractTxtBuscar.this.entity2Object(entity);
			}

			@Override
			public String getEntityCode(T entity) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		txtCodigo.setColumns(10);
		GridBagConstraints gbc_txtCodigo = new GridBagConstraints();
		gbc_txtCodigo.gridwidth = 2;
		
		gbc_txtCodigo.fill = GridBagConstraints.BOTH;
		gbc_txtCodigo.gridx = 0;
		gbc_txtCodigo.gridy = 0;
		add(txtCodigo, gbc_txtCodigo);
	}

	public AbstractTxtBuscar() {
		this(new String[] { "Codigo", "Descripcion" }, new int[] { 90, 200 });
	}

	public Window getFormulario() {
		return (Window) Sys.mainF;
	}

	/**
	 * 
	 * @param Actualiza
	 *            lista de Datos a filtrar
	 */
	public void setData(List<T> data) {
		txtCodigo.setData(data);
	}

	public T getSeleccionado() {
		return txtCodigo.getSeleccionado();
	}
	
	public void setSeleccionado(T seleccionado){
		txtCodigo.setSeleccionado(seleccionado);
		cargarDatos(seleccionado);
	}

	public static ResizableIcon getResizableIconFromResource16x16(
			String resource) {
		return ImageWrapperResizableIcon.getIcon(AbstractTxtBuscar.class
				.getResource(resource), new Dimension(30, 30));
	}

	public abstract void cargarDatos(T entity);

	public abstract boolean coincideBusqueda(T entity, String cadena);

	public abstract Object[] entity2Object(T entity);

	public abstract void refrescar();
	
	public void mostrar(String dato){
		txtCodigo.checkForAndShowSuggestions(dato);
	}

}