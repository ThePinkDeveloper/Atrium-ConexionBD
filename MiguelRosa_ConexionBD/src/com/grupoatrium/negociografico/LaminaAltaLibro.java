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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.grupoatrium.modelo.Libro;
import com.grupoatrium.persistencia.LibrosDAO;

/******************************************************************************
 * Nombre de Clase : LaminaAltaLibro
 * Autor: Miguel Rosa 
 * Fecha: 15 - Noviembre - 2016 
 * Versión: 1.0 
 * Hereda: JPanel 
 * Implementa: ActionListener
 * Descripcion: 
 * LaminaAltaLibro es la clase que crea el JPanel que tiene todos los
 * componentes para poder dar de alta un libro en la base de datos. No se le
 * asigna ningún layout para poder posicionar los elementos en sus coordenadas
 * X e Y. Cuando se presiona el botón darDeAlta, se recogen todos los datos
 * introducidos en los JTextFields y JTextArea para crear una instancia de la
 * clase Libro y se lanza el método altaLibro() de la clase LibrosDAO dando
 * así el libro en la base de datos.
 ******************************************************************************/

public class LaminaAltaLibro extends JPanel implements ActionListener {

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/

	// Atributos que definen e instancian los JTextField para que el usuario
	// introduzca los datos.
	JTextField titulo = new JTextField(50); // Título
	JTextField autor = new JTextField(50); // Autor
	JTextField editorial = new JTextField(50); // Editorial
	JTextField isbn = new JTextField(20); // ISBN
	JTextField publicacion = new JTextField(6); // año de publicación
	JTextField precio = new JTextField(6); // Precio

	// Como la descripción puede contener hasta 200 caracteres, con un
	// JTextField es insuficiente para contener ese String, se define e
	// instancia un JTextArea para el atributo "descripcon".
	JTextArea descripcion = new JTextArea(); // Descripción

	// Se define e instancia el JButton que dispara la recolección de los
	// datos introducidos por el usuario y da de alta el libro en la base
	// de datos.
	JButton darDeAlta = new JButton("REGISTRAR");

	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaAltaLibro() {
		// No se le define ningún Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tamaño de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se añaden y posicionan los JTextFields
		add(titulo);
		titulo.setBounds(150, 110, 340, 20);
		add(autor);
		autor.setBounds(150, 160, 340, 20);
		add(editorial);
		editorial.setBounds(150, 210, 340, 20);
		add(isbn);
		isbn.setBounds(320, 260, 170, 20);
		add(publicacion);
		publicacion.setBounds(390, 310, 100, 20);
		add(precio);
		precio.setBounds(390, 360, 100, 20);

		// Se añade y posiciona el JTextArea, se activan las propiedades
		// LineWrap y WrapStyleWord para se cambie de renglón
		// automáticamente
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		add(descripcion);
		descripcion.setBounds(200, 410, 290, 80);

		// Se añade y posiciona el botón para dar de alta
		darDeAlta.setBounds(340, 520, 150, 50);
		add(darDeAlta);	
		// Se ponen el botón darDeAlta a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaAltaLibro
		darDeAlta.addActionListener(this);
	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel
	 *************************************************************************/
	
	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripción: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaAltaLibro los
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
		g2.drawString("DAR DE ALTA UN LIBRO", 120, 50);
		
		// Creamos una nueva fuente, Arial, tamaño 16 y en negrita
		Font miFont2 = new Font("Arial", 1, 16);
		// Asignamos la fuente al pincel 
		g2.setFont(miFont2);
		// Pintamos y posicionamos los carteles que indican el dato de cada
		// JTextField y el JTextArea
		g2.drawString("TÍTULO:", 50, 125);
		g2.drawString("AUTOR:", 50, 175);
		g2.drawString("EDITORIAL:", 50, 225);
		g2.drawString("ISBN:", 50, 275);
		g2.drawString("AÑO PUBLICACION:", 50, 325);
		g2.drawString("PRECIO:", 50, 375);
		g2.drawString("DESCRIPCIÓN:", 50, 425);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener
	 *************************************************************************/
	
	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripción: 
	 * Captura el evento lanzado por el JButton darDeAlta. Recopila los datos
	 * que hay en los JtextFields y JTextArea y crea una instancia d ela clase
	 * Libro. Crea una instancia de la clase LibrosDAO y llama a su método
	 * altaLibro().
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Variable para controlar si se ha producido un error en el proceso
		boolean todoBien = true;
		
		// Almacenamos los valores de los JTextFields y JtextArea en variables
		// locales
		String titulo = this.titulo.getText();
		String autor = this.autor.getText();
		String editorial = this.editorial.getText();
		String isbn = this.isbn.getText();
		String publicacion = this.publicacion.getText();
		String precio = this.precio.getText();
		String descripcion = this.descripcion.getText();
		// Variables para almacenar los valores que no sean String
		int publicacion_int = 0;
		double precio_double = 0;
		
		// Convertimos el valor del String publicacion, a entero, si no se puede
		// se lanza un Alert para que se cambie el valor a uno correcto.
		try {
			publicacion_int = Integer.parseInt(publicacion);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "El año de publicación no es un dato válido.");
			todoBien = false;
		}
		// Convertimos el valor del String precio, a doubleentero, si no se puede
		// se lanza un Alert para que se cambie el valor a uno correcto.
		try {
			precio_double = Double.parseDouble(precio);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "El precio indicado no es un dato válido.");
			todoBien = false;
		}
		
		// Si todos los datos están correctos se intenta dar el libro de alta en la base de
		// de datos
		if (todoBien) {
			// Se crea una instancia de la clase Libro
			Libro miLibro = new Libro(autor, descripcion, editorial, isbn, precio_double, publicacion_int, titulo);
			// Se crea una instancia de la clase LibrosDAO
			LibrosDAO alta_BD = new LibrosDAO();
			// Se da de alta el libro en la base de datos
			if (alta_BD.altaLibro(miLibro)) {
				// Se muestra un Alert indicando que todo ha salido bien
				JOptionPane.showMessageDialog(null, "Se ha dado de alta el libro con el id " + miLibro.getID() + ".");
				// Se borran los datos en los campos d eusuario en espera de un nuevo libro
				this.titulo.setText("");
				this.autor.setText("");
				this.editorial.setText("");
				this.isbn.setText("");
				this.publicacion.setText("");
				this.precio.setText("");
				this.descripcion.setText("");
			
			// Si ocurre un problema durante el proceso de dar de alta
			} else {
				// se muestra un Alert informando al usuario
				JOptionPane.showMessageDialog(null, "SE HA PRODUCIDO UN ERROR AL INTENTAR INTRODUCIR LOS DATOS EN LA BASE DE DATOS.");
			}
		}

	}

}
