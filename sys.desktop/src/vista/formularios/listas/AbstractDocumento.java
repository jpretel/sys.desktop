package vista.formularios.listas;

import java.util.Date;

public abstract class AbstractDocumento {
	private String _iddocumento;
	private String _serie;
	private String _numero;
	private Date _fecha;
	
	
	public String get_iddocumento() {
		return _iddocumento;
	}
	public void set_iddocumento(String _iddocumento) {
		this._iddocumento = _iddocumento;
	}
	public String get_serie() {
		return _serie;
	}
	public void set_serie(String _serie) {
		this._serie = _serie;
	}
	public String get_numero() {
		return _numero;
	}
	public void set_numero(String _numero) {
		this._numero = _numero;
	}
	public Date get_fecha() {
		return _fecha;
	}
	public void set_fecha(Date _fecha) {
		this._fecha = _fecha;
	}	
}