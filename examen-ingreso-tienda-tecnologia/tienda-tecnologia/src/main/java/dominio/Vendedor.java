package dominio;

import dominio.repositorio.RepositorioProducto;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String EL_PRODUCTO_TIENE_TRES_VOCALES = "Este producto no cuenta con garantia extendida";

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;

	}

	public void generarGarantia(String codigo,String nombreCliente) {

		Producto producto = repositorioProducto.obtenerPorCodigo(codigo);
		GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto,nombreCliente);

		if (tieneGarantia(codigo) == true) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
		} else if (Numero_Vocales(codigo) == 3) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_TRES_VOCALES);
		} else {
			repositorioGarantia.agregar(garantiaExtendida);
		}
	}

	public int Numero_Vocales(String codigo) {
		int contador = 0;

		for (int x = 0; x < codigo.length(); x++) {
			if ((codigo.charAt(x) == 'A') || (codigo.charAt(x) == 'E') || (codigo.charAt(x) == 'I')
					|| (codigo.charAt(x) == 'O') || (codigo.charAt(x) == 'U')) {
				contador++;
			}
		}
		return contador;
	}

	public boolean tieneGarantia(String codigo) {

		Producto producto = repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
		if (producto != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void precioGarantiaExtendida (String codigo, Date fechaSolicitudGarantia, Date fechaFinGarantia, //constructor
            double precioGarantia, String nombreCliente){
		
		Producto producto = repositorioProducto.obtenerPorCodigo(codigo);	
		GarantiaExtendida garantiaExtendid = new GarantiaExtendida(producto, fechaSolicitudGarantia,fechaFinGarantia,precioGarantia,nombreCliente);	
		double precioProducto = producto.getPrecio();
		
		if (precioProducto > 500000) {
				precioGarantia = precioProducto*0.2; 
			 
					
		} else if (precioProducto <= 500000){
			
			precioGarantia = precioProducto*0.1; 
		   
			
		}
		
	}
		
}
