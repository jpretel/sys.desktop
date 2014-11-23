package vista.utilitarios;

import java.util.ArrayList;
import java.util.List;

import vista.Sys;
import core.dao.SysGrupoDAO;
import core.dao.SysOpcionDAO;
import core.dao.SysTituloDAO;
import core.entity.SysGrupo;
import core.entity.SysModulo;
import core.entity.SysTitulo;

public class MenuController {

	public static List<SysTitulo> getTitulosPorModulo(SysModulo modulo) {
		List<SysTitulo> titulos = null;
		SysTituloDAO sysTituloDAO = new SysTituloDAO();
		SysGrupoDAO sysGrupoDAO = new SysGrupoDAO();
		SysOpcionDAO sysOpcionDAO = new SysOpcionDAO();

		if (Sys.usuario.getGrupoUsuario().getEsAdministrador() == 1) {
			titulos = sysTituloDAO.getPorModulo(modulo);
			if (titulos == null)
				titulos = new ArrayList<SysTitulo>();
			for (SysTitulo titulo : titulos) {
				titulo.setSysGrupos(sysGrupoDAO.getPorTitulo(titulo));
				if (titulo.getSysGrupos() == null) {
					titulo.setSysGrupos(new ArrayList<SysGrupo>());
				}
				for (SysGrupo grupo : titulo.getSysGrupos()) {
					grupo.setSysOpcions(sysOpcionDAO.getPorGrupo(grupo));
				}
			}
		} else {
			titulos = sysTituloDAO.getPorModuloUsuario(modulo, Sys.usuario);
			for (SysTitulo titulo : titulos) {
				titulo.setSysGrupos(sysGrupoDAO.getPorTitulo(titulo,
						Sys.usuario));
				if (titulo.getSysGrupos() == null) {
					titulo.setSysGrupos(new ArrayList<SysGrupo>());
				}
				for (SysGrupo grupo : titulo.getSysGrupos()) {
					grupo.setSysOpcions(sysOpcionDAO.getPorGrupo(grupo,
							Sys.usuario));
				}
			}
		}

		return titulos;
	}

}
