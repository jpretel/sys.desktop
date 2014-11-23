package vista.controles;

public class ComboObject<T> {
	private T object;
	private String etiqueta;

	public ComboObject(T object, String etiqueta) {
		this.object = object;
		this.etiqueta = etiqueta;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
}