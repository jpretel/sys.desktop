package vista.utilitarios;

public class Main {
	public static void main(String args[]){
        Observado observado = new Observado();
        Observador observador = new Observador();
        
        observado.addObserver(observador);
        
        observado.cambiarMensaje("Cambio 1");
        observado.cambiarMensaje("Cambio 2");
        observado.cambiarMensaje("Cambio 3");
    }
}
