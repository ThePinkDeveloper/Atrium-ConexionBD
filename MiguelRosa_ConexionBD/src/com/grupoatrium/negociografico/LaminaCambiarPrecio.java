package com.grupoatrium.negociografico;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.grupoatrium.modelo.LibroNoEncontradoException;
import com.grupoatrium.persistencia.LibrosDAO;

public class LaminaCambiarPrecio extends JPanel implements ActionListener {
	
	/*************************************************************************
	 * Nombre de Clase : LaminaCambiarPRecio
	 * Autor: Miguel Rosa 
	 * Fecha: 15 - Noviembre - 2016 
	 * Versión: 1.0 
	 * Hereda: JPanel 
	 * Implementa: ActionListener
	 * Descripcion: 
	 * LaminaCambiarPrecio es la clase que crea el JPanel que tiene todos los
	 * componentes para poder cambiar el precio de un libro de la base de datos 
	 * según el código isbn introducido por el usuario. No se le asigna ningún
	 * layout para poder posicionar los elementos en sus coordenadas X e Y. 
	 * Cuando se presiona el botón cambiar, se recoge el isbn y el precio de los
	 * JTextFields habilitados para ello y se lanza el método cambiarPrecio()
	 * de la clase LibrosDAO.
	 *************************************************************************/

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/

	// Atributo que define e instancia el JTextField para que el usuario
	// introduzca el isbn del libro que desea cambiar.
	JTextField isbn = new JTextField(50);
	// Atributo que define e instancia el JTextField para que el usuario
	// introduzca el nuevo precio.
	JTextField precio = new JTextField(50);

	// Se define e instancia el JButton que dispara el muestreo de datos
	// de la base de datos.
	JButton cambiar = new JButton("CAMBIAR");

	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaCambiarPrecio() {
		// No se le define ningún Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tamaño de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se añade y posiciona el JTextField isbn		
		add(isbn);
		isbn.setBounds(150, 210, 200, 20);

		// Se añade y posiciona el JTextField precio
		add(precio);
		precio.setBounds(280, 260, 70, 20);

		// Se añade y posiciona el botón cambiar
		cambiar.setBounds(340, 520, 150, 50);
		add(cambiar);
		// Se ponen el botón cambiar a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaCambiarPrecio
		cambiar.addActionListener(this);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel *
	 *************************************************************************/
	
	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripción: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaCambiarPrecio los
	 * carteles informativos.
	 */

	public void paintComponent(Graphics g) {
		// Se llama al método paintComponent de la clase JPanel para no
		// perder su contenido
		super.paintComponent(g);
		// Casteamos la instancia "g" de Graphics a Graphics2D.
		// g2 es como si fuera el pincel que se encarga de pintar en el JPanel
		Graphics2D g2 = (Graphics2D) g;

		// Creamos una fuente, Arial, tamaño 26, en negrita y cursiva
		Font miFont = new Font("Arial", 3, 26);
		// Asignamos la fuente al pincel
		g2.setFont(miFont);
		// Pintamos y posicionamos el texto del título del JPanel
		g2.drawString("CAMBIAR EL PRECIO DE UN LIBRO", 50, 50);
		
		// Creamos una nueva fuente, Arial, tamaño 16 y en negrita
		Font miFont2 = new Font("Arial", 1, 16);
		// Asignamos la fuente al pincel 
		g2.setFont(miFont2);
		// Pintamos y posicionamos los carteles que indican el dato del
		// JTextField y la acción que debe tomar el usuario
		g2.drawString("POR FAVOR, INTRUZCA EL ISBN Y EL NUEVO PRECIO DEL", 50, 125);
		g2.drawString("LIBRO QUE DESEA CAMBIAR.", 50, 140);
		g2.drawString("ISBN:", 50, 225);
		g2.drawString("PRECIO:", 50, 275);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener *
	 *************************************************************************/
	
	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripción: 
	 * Captura el evento lanzado por el JButton cambiar. Recoge los datos
	 * que hay en los JtextFields isbn y precio y llama al método 
	 * modificarPrecio() de la clase LibrosDAO.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Variable para controlar si se ha producido un error en el proceso
		boolean todoBien = true;
		
		// Almacenamos el valor del JTextField isbn en una variable local
		String isbn = this.isbn.getText();
		
		// Alamacenamos el valor del JTextField precio en una variable local
		String precio = this.precio.getText();
		// Variable para almacenar el valor double del precio
		double precio_double = 0;
		
		// Convertimos el valor del String precio, a doubleo, si no se puede
		// se lanza un Alert para que se cambie el valor a uno correcto.
		try {
			precio_double = Double.parseDouble(precio);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "El precio introducido no es un dato válido.");
			todoBien = false;
		}
		
		// Si todos los datos están correctos intenta cambiar el precio del libro
		if (todoBien) {
			// Se crea una instancia de la clase LibrosDAO
			LibrosDAO cambiar_BD = new LibrosDAO();
			
			// Se intenta modificar el precio del libro de la base de datos
			try {
				cambiar_BD.modificarPrecio(isbn, precio_double);
				// Se muestra un Alert indicando que todo ha salido bien
				JOptionPane.showMessageDialog(null, "SE HA MODIFICADO EL PRECIO DEL LIBRO CON ISBN " +
											isbn + " A " + precio_double + ".");
				// Se borran los datos de los TextFields ISBN y Precio en espera de un 
				// nuevo cambio de precio
				this.isbn.setText("");
				this.precio.setText("");
				
			// Si el isbn indicado no pertenece a ningún libro de la base de datos
			} catch (LibroNoEncontradoException excepcion ) {
				excepcion.mensajeAlert("ISBN");
				
			// Si ocurre un problema durante el proceso de eliminación
			} catch (Exception excepcion2) {
				// se muestra un Alert informando al usuario
				JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN ERROR AL INTENTAR INTRODUCIR LOS DATOS EN LA BASE DE DATOS.");
			}
		}
		

	}
}
