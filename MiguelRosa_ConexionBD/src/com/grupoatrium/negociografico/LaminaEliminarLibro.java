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

import com.grupoatrium.modelo.Libro;
import com.grupoatrium.modelo.LibroNoEncontradoException;
import com.grupoatrium.persistencia.LibrosDAO;

/******************************************************************************
 * Nombre de Clase : LaminaEliminarLibro
 * Autor: Miguel Rosa 
 * Fecha: 15 - Noviembre - 2016 
 * Versión: 1.0 
 * Hereda: JPanel 
 * Implementa: ActionListener
 * Descripcion: 
 * LaminaEliminarLibro es la clase que crea el JPanel que tiene todos los
 * componentes para poder eliminar un libro en la base de datos según el id
 * introducido por el usuario. No se le asigna ningún layout para poder 
 * posicionar los elementos en sus coordenadas X e Y. Cuando se presiona el 
 * botón eliminar, se recoge el id introducido en el JTextField y se lanza el 
 * método eliminarLibro() de la clase LibrosDAO eliminando así el libro en la 
 * base de datos.
 ******************************************************************************/

public class LaminaEliminarLibro extends JPanel implements ActionListener {

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/
	
	// Atributo que define e instancia el JTextField para que el usuario
	// introduzca el id del libro que desea eliminar.
	JTextField id = new JTextField(50);

	// Se define e instancia el JButton que dispara la recogida del dato
	// introducido por el usuario y eliminar el libro en la base de datos.
	JButton eliminar = new JButton("ELIMINAR");

	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaEliminarLibro() {
		// No se le define ningún Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tamaño de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se añade y posiciona el JTextField id
		add(id);
		id.setBounds(150, 210, 100, 20);

		// Se añade y posiciona el botón para dar de alta
		eliminar.setBounds(340, 520, 150, 50);
		add(eliminar);
		// Se ponen el botón eliminar a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaEliminarLibro
		eliminar.addActionListener(this);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel *
	 *************************************************************************/

	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripción: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaEliminarLibro los
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
		g2.drawString("ELIMINAR UN LIBRO POR ID", 100, 50);
		
		// Creamos una nueva fuente, Arial, tamaño 16 y en negrita
		Font miFont2 = new Font("Arial", 1, 16);
		// Asignamos la fuente al pincel 
		g2.setFont(miFont2);
		// Pintamos y posicionamos los carteles que indican el dato del
		// JTextField y la acción que debe tomar el usuario
		g2.drawString("POR FAVOR, INTRUZCA EL ID DEL LIBRO QUE DESEA", 50, 125);
		g2.drawString("ELIMINAR.", 50, 140);
		g2.drawString("ID:", 50, 225);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener *
	 *************************************************************************/

	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripción: 
	 * Captura el evento lanzado por el JButton eliminar. Recoge el dato
	 * que hay en los JtextField id y elimina el registro de la base de datos
	 * con ese ID.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Variable para controlar si se ha producido un error en el proceso
		boolean todoBien = true;
		
		// Almacenamos el valor del JTextField id en una variable local
		String id = this.id.getText();
		// Variable para almacenar el valor entero del id
		int id_int = 0;
		
		// Convertimos el valor del String id, a entero, si no se puede
		// se lanza un Alert para que se cambie el valor a uno correcto.
		try {
			id_int = Integer.parseInt(id);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "El id del libro no es un dato válido.");
			todoBien = false;
		}
		
		// Si todos los datos están correctos intenta eliminar el libro de la base de
		// datos
		if (todoBien) {
			// Se crea una instancia de la clase LibrosDAO
			LibrosDAO elimina_BD = new LibrosDAO();
			
			// Se intenta eliminar el libro de la base de datos
			try {
				elimina_BD.eliminarLibro(id_int);
				// Se muestra un Alert indicando que todo ha salido bien
				JOptionPane.showMessageDialog(null, "Se ha eliminado el libro con el id " + id_int + ".");
				// Se borra el datos del TextField ID en espera de un nuevo libro
				this.id.setText("");
				
			// Si el id indicado no pertenece a ningún libro de la base de datos
			} catch (LibroNoEncontradoException excepcion ) {
				excepcion.mensajeAlert("ID");
				
			// Si ocurre un problema durante el proceso de eliminación
			} catch (Exception excepcion2) {
				// se muestra un Alert informando al usuario
				JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN ERROR AL INTENTAR INTRODUCIR LOS DATOS EN LA BASE DE DATOS.");
			}
		}

	}
}