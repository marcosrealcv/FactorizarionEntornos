package prueba;
public class EntornosFactorizar {
    
    
    public double calculaDato(double precioBase, int cantidad, double descuento, double impuestos, boolean tieneTarjetaFidelidad, double saldoTarjeta, boolean esOfertaEspecial, boolean esNavidad, boolean esMiembroVip, String metodoPago, boolean aplicarCuotas, int cuota, boolean esEnvioGratis, double precioEnvio, String tipoProducto, String categoriaProducto, String codigoCupon, Usuario usuario) {
        double total = precioBase * cantidad;

       // Aplico descuento si existe
        if (descuento > 0) {
            total -= total * (descuento / 100);
        }

   
        if (tieneTarjetaFidelidad && saldoTarjeta > 0) {
            total -= saldoTarjeta;
        }

        //Agrego impuestos
       
        total += total * (impuestos / 100);
        
        // Ofertas conbinadas
        total = aplicarDescuentosPromocionales(total, esOfertaEspecial,esNavidad,esMiembroVip);


        // Metodos de pago
        
        if (metodoPago.equals("TarjetaCredito")) {
            total *= 1.05;
        } else if (metodoPago.equals("PayPal")) {
            total *= 1.02;
        }

        // Cuotas
        
        if (aplicarCuotas) {
            if (cuota == 3) {
                total *= 1.1;
            } else if (cuota == 6) {
                total *= 1.2;
            } else if (cuota == 12) {
                total *= 1.3;
            }
        }

        
        // Envios gratis
        
        if (!esEnvioGratis) {
            total += precioEnvio;
        }

        
        if (codigoCupon != null && !codigoCupon.isEmpty()) {
            total = aplicarCuponDescuento(total, codigoCupon);
        }

    
        if (!validarProducto(tipoProducto, categoriaProducto)) {
            throw new IllegalArgumentException("El producto no es válido para esta compra.");
        }

      
        // usuario nulo
        
        if (usuario != null) {
            total = aplicarDescuentoPorUsuario(usuario, total);
        }

     
        if (total < 0) {
            total = 0;
        }

        return total;
    }

    // Las ofertas conbinadas estan metidas en el mismo metodo
    
    private double aplicarDescuentosPromocionales(double total,boolean esOfertaEspecial,boolean esNavidad,boolean esMiembroVip){
    	if (esOfertaEspecial) {
            total *= 0.9;  
        }
        if (esNavidad) {
            total *= 0.85; 
        }
        if (esMiembroVip) {
            total *= 0.8;  
        }
        return total;
    }
    
    // Metodo aplicar cupones descuentos
    
    private double aplicarCuponDescuento(double total, String codigoCupon) {
        if (codigoCupon.equals("CUPOFF")) {
            total *= 0.8;
        } else if (codigoCupon.equals("NAVIDAD2025")) {
            total *= 0.75;
        }
        return total;
    }

   // Metodo validacion de productos
    
    private boolean validarProducto(String tipoProducto, String categoriaProducto) {
        if (tipoProducto.equals("Electronico") && categoriaProducto.equals("Smartphones")) {
            return true;
        } else if (tipoProducto.equals("Ropa") && categoriaProducto.equals("Hombre")) {
            return true;
        } else if (tipoProducto.equals("Ropa") && categoriaProducto.equals("Mujer")) {
            return true;
        }
        return false;
    }

   // Aplicar descuentos por usuarios
    
    private double aplicarDescuentoPorUsuario(Usuario usuario, double total) {
        if (usuario.esEmpleado()) {
            total *= 0.7; 
        } else if (usuario.esMiembroGold()) {
            total *= 0.85;  
        } else if (usuario.esMiembroSilver()) {
            total *= 0.9; 
        }
        return total;
    }
}
