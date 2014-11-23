package vista.controles;

import vista.utilitarios.UtilMensajes;

public class DSGVNDetalle {

	private Operador operador;
	private int[] columnas;
	private float operando;

	public static enum Operador {
		MAYOR, MENOR, IGUAL, MAYOR_IGUAL, MENOR_IGUAL, DIFERENTE;
	}

	public DSGVNDetalle(Operador operador, float operando, int... columnas) {
		this.operador = operador;
		this.operando = operando;
		this.columnas = columnas;
	}

	public DSGVNDetalle() {
		// TODO Auto-generated constructor stub
	}

	public int[] getColumnas() {
		return columnas;
	}

	public void setColumnas(int[] columnas) {
		this.columnas = columnas;
	}

	public float getOperando() {
		return operando;
	}

	public void setOperando(float operando) {
		this.operando = operando;
	}

	public boolean validarModelo(DSGTableModel model, String[] cabeceras) {

		String sOperando = String.valueOf(getOperando()).trim();
		for (int row = 0; row < model.getRowCount(); row++)
			for (int i = 0; i < columnas.length; i++) {
				String sRow = String.valueOf(row + 1);
				String cabecera = cabeceras[columnas[i]];
				float valor;
				try {
					valor = Float.parseFloat(model.getValueAt(row, columnas[i])
							.toString());
				} catch (Exception e) {
					valor = 00.F;
				}
				switch (this.operador) {
				case MAYOR:
					if (!(valor > this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_NUMMAYOR",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;

				case MENOR:
					if (!(valor < this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_NUMMENOR",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;
				case IGUAL:
					if (!(valor == this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_NUMIGUAL",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;
				case MAYOR_IGUAL:
					if (!(valor >= this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_NUMMAYORIGUAL",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;
				case MENOR_IGUAL:
					if (!(valor <= this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_NUMMENORIGUAL",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;
				case DIFERENTE:
					if (!(valor != this.operando)) {
						UtilMensajes.mensaje_alterta("DETALLE_DIFERENTE",
								model.getNombre_detalle(), cabecera, sOperando,
								sRow);
						return false;
					}
					break;
				}

			}
		return true;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}
}
