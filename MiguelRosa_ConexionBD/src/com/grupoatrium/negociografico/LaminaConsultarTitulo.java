package com.grupoatrium.negociografico;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.grupoatrium.modelo.Libro;
import com.grupoatrium.modelo.LibroNoEncontradoException;
import com.grupoatrium.persistencia.LibrosDAO;

public class LaminaConsultarTitulo extends JPanel implements ActionListener {
	
	/******************************************************************************
	 * Nombre de Clase : LaminaConsultarTitulo
	 * Autor: Miguel Rosa 
	 * Fecha: 15 - Noviembre - 2016 
	 * Versión: 1.0 
	 * Hereda: JPanel 
	 * Implementa: ActionListener
	 * Descripcion: 
	 * LaminaConsultarTitulo es la clase que crea el JPanel que tiene todos los
	 * componentes para poder mostrar los datos de un libro de la base de datos 
	 * según el título introducido por el usuario. No se le asigna ningún layout
	 * para poder posicionar los elementos en sus coordenadas X e Y. 
	 * Cuando se presiona el botón buscar, se recoge el String introducido en el 
	 * JTextField y se lanza el método consultarTitulo() de la clase LibrosDAO
	 * mostrando los datos recibidos en el JTextArea habilitado para ello.
	 ******************************************************************************/

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/
	
	// Atributo que define e instancia el JTextField para que el usuario
	// introduzca el título del libro que desea buscar.
	JTextField titulo = new JTextField(50);
	
	// Atributo que define e instancia el JTextArea donde se muestran los
	// resultados.
	JTextArea registros = new JTextArea();
	// Atributo que define e instancia el JScrollPane que proporciona barra de
	// scroll al JtextArea anterior.
	JScrollPane scroll = new JScrollPane(registros);

	// Se define e instancia el JButton que dispara el muestreo de datos
	// de la base de datos.
	JButton buscar = new JButton("BUSCAR");

	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaConsultarTitulo() {
		// No se le define ningún Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tamaño de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se añade y posiciona el JTextField titulo
		add(titulo);
		titulo.setBounds(150, 210, 340, 20);

		// Se añade y posiciona el JTextArea, se activan las propiedades
		// LineWrap y WrapStyleWord para se cambie de renglón
		// automáticamente
		registros.setLineWrap(true);
		registros.setWrapStyleWord(true);
		registros.setBounds(50, 260, 440, 230);

		// Se añade y posiciona el JScrollPane.
		scroll.setBounds(50, 260, 440, 230);
		add(scroll);
		
		// Se añade y posiciona el botón buscar
		buscar.setBounds(340, 520, 150, 50);
		add(buscar);
		// Se ponen el botón buscar a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaConsutarTitulo
		buscar.addActionListener(this);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel *
	 *************************************************************************/
	
	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripción: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaConsultarTitulo los
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
		g2.drawString("MOSTRAR LIBRO POR TÍTULO", 80, 50);
		
		
		// Creamos una nueva fuente, Arial, tamaño 16 y en negrita
		Font miFont2 = new Font("Arial", 1, 16);
		// Asignamos la fuente al pincel 
		g2.setFont(miFont2);
		// Pintamos y posicionamos los carteles que indican el dato del
		// JTextField y la acción que debe tomar el usuario
		g2.drawString("POR FAVOR, INTRUZCA EL TÍTULO DEL LIBRO QUE DESEA", 50, 125);
		g2.drawString("BUSCAR.", 50, 140);
		g2.drawString("TÍTULO:", 50, 225);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener *
	 *************************************************************************/

	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripción: 
	 * Captura el evento lanzado por el JButton buscar. Recopila el dato
	 * del JtextField titulo y lanza el método consultarTitulo() de 
	 * la clase LibrosDAO. Recoge los datos devueltos y los muestra en el
	 * JTextArea registros.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Se crea una instancia de la clase LibrosDAO
		LibrosDAO muestra_BD = new LibrosDAO();
		
		// Se recoge el String del JTextField titulo y se almacena
		String cadena = titulo.getText();
		
		// Se llama al método consultarTitulo y se almacena el List de
		// Libros que devuelve. Como este método lanza la excepción
		// LibroNoEncontradoException se captura. 
		try {
			List<Libro> libro = muestra_BD.consultarTitulo(cadena);
			
			// Se pasa el List a Array de objetos genéricos
			Object[] arrayLibros = libro.toArray();
			
			// Creamos una variable String y le asignamos una cadena vacía
			String str = "";
			
			// Recorremos el array y almacenamos los atributos de todos los objetos que lo componen
			// en la variable String creada en el paso anterior. Previo a la optención del atributo 
			// hay que castear el objeto generérico del Array a Libro para poder utilizar los
			// métodos getters.
			for (int i = 0; i < arrayLibros.length; i++) {
				str = str + ("ID: " + ((Libro) arrayLibros[i]).getID() + "\n");
				str = str + ("TÍTULO: " + ((Libro) arrayLibros[i]).getTitulo() + "\n");
				str = str + ("AUTOR: " + ((Libro) arrayLibros[i]).getAutor() + "\n");
				str = str + ("EDITORIAL: " + ((Libro) arrayLibros[i]).getEditorial()+ "\n");
				str = str + ("ISBN: " + ((Libro) arrayLibros[i]).getIsbn() + "\n");
				str = str + ("PUBLICACION: " + ((Libro) arrayLibros[i]).getPublicacion() + "\n");
				str = str + ("PRECIO: " + ((Libro) arrayLibros[i]).getPrecio() + "\n");
				str = str + ("DESCRIPCIÓN: " + ((Libro) arrayLibros[i]).getDescripcion() + "\n\n");
				str = str + ("--------------------------------------------------\n\n");
			}
			
			// Se asigna el contenido del String str al JTextArea registro
			registros.setText(str);
		
		// En caso de que no se encuentre ningún resultado en la base de datos
		// capturamos la excepción y mostrarmos un Alert informando al usuario
		} catch (LibroNoEncontradoException excepcion) {
			excepcion.mensajeAlert("TITULO");;
		}
	}
}