package vista.controles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejaLetras implements KeyListener{
	
	@Override
    public void keyTyped(KeyEvent ke) {
        
        
         char c=ke.getKeyChar();
        
    
      if(Character.isDigit(c)) {          
          ke.consume();          
          System.out.println("Ingrese solo letras");
          
      }
        
                
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
