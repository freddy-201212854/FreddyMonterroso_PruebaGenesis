package service;

public class TipoCambio {
	
	public String fecha;
	public float venta;
	public float compra;

	public TipoCambio(String fecha, float venta, float compra) {
			this.fecha = fecha;
			this.venta = venta;
			this.compra = venta;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public float getVenta() {
		return this.venta;
	}
	
	public float getCompra() {
		return this.compra;
	}
}
