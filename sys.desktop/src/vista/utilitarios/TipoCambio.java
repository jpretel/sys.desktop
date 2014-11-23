package vista.utilitarios;

public class TipoCambio {
	private float compra;
	private float venta;
	private int dia;

	public TipoCambio(int dia, float compra, float venta) {
		this.dia = dia;
		this.compra = compra;
		this.venta = venta;
	}

	public float getCompra() {
		return compra;
	}

	public void setCompra(float compra) {
		this.compra = compra;
	}

	public float getVenta() {
		return venta;
	}

	public void setVenta(float venta) {
		this.venta = venta;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}
}
