package vista.controles;

import java.util.List;

public interface IDocumentoDAO {
	public List<Object[]> getListaDocumentos(int desde_dia, int desde_mes,
			int desde_a�o, int hasta_dia, int hasta_mes, int hasta_a�o,
			String iddocumento, String serie, String numero);
}