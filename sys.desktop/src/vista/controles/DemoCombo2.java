package vista.controles;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


public class DemoCombo2 {
	public static void main(String args[]) {
		JFrame frame = new JFrame("ArrayListComboBoxModel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		List<ComboObject<Prueba>> datos = new ArrayList<ComboObject<Prueba>>();
		
		datos.add(new ComboObject<Prueba>(new Prueba("01", "d01"), "d01"));
		datos.add(new ComboObject<Prueba>(new Prueba("02", "d02"), "d02"));
		datos.add(new ComboObject<Prueba>(new Prueba("03", "d03"), "d03"));
		datos.add(new ComboObject<Prueba>(new Prueba("04", "d04"), "d04"));
		datos.add(new ComboObject<Prueba>(new Prueba("05", "d05"), "d05"));
		
		ComboBox<Prueba> cboPrueba = new ComboBox<Prueba>(datos);
		frame.getContentPane().add(cboPrueba, BorderLayout.NORTH);

		frame.setSize(300, 225);
		frame.setVisible(true);
	}
}

class Prueba {
	private String id;
	private String descripcion;
	public Prueba(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
