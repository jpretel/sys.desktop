package vista.controles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejaNumeros implements KeyListener{
	@Override
    public void keyTyped(KeyEvent ke) {
         char k = ke.getKeyChar(); 
        if (Character.isLetter(k)  && k != '"' && k != '=') {
        	ke.consume();       
        	System.out.println("solo admite numeros");
        }     
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
