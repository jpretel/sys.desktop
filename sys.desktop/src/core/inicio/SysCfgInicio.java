package core.inicio;

public class SysCfgInicio {
	private String gestor;
	private String servidor;
	private String base_datos;
	private String usuario;
	private String clave;
	private String tipo_creacion;
	
	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getBase_datos() {
		return base_datos;
	}

	public void setBase_datos(String base_datos) {
		this.base_datos = base_datos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String getURL() {
		if (gestor.equals(ConectionManager.MYSQL))
			return "jdbc:mysql://" + getServidor() + "/" + getBase_datos();
		if (gestor.equals(ConectionManager.SQLSERVER))
			return "jdbc:sqlserver://" + getServidor() + ";databaseName=" + getBase_datos();
		return null;
	}
	
	public String getDriver() {
		if (gestor.equals(ConectionManager.MYSQL))
			return "com.mysql.jdbc.Driver";
		if (gestor.equals(ConectionManager.SQLSERVER))
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		return null;
	}
	
	public String getTipo_creacion() {
		return tipo_creacion;
	}

	public void setTipo_creacion(String tipo_creacion) {
		this.tipo_creacion = tipo_creacion;
	}

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

}
