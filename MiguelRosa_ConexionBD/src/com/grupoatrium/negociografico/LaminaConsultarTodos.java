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

import com.grupoatrium.modelo.Libro;
import com.grupoatrium.persistencia.LibrosDAO;

/******************************************************************************
 * Nombre de Clase : LaminaConsultarTodos
 * Autor: Miguel Rosa 
 * Fecha: 15 - Noviembre - 2016 
 * Versi�n: 1.0 
 * Hereda: JPanel 
 * Implementa: ActionListener
 * Descripcion: 
 * LaminaConsultarTodos es la clase que crea el JPanel que tiene todos los
 * componentes para poder mostrar el contenido �ntegro de los registros de 
 * la base de datos. No se le asigna ning�n layout para poder posicionar los 
 * elementos en sus coordenadas X e Y. Cuando se presiona el bot�n mostrar, 
 * se lanza el m�todo mostrarTodo() de la clase LibrosDAO y se recogen los
 * datos para mostrarlos en el JTextArea habilitado dentro de un JScrollPane
 * para que aparezca la barra de scroll.
 ******************************************************************************/

public class LaminaConsultarTodos extends JPanel implements ActionListener {

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/

	// Atributo que define e instancia el JTextArea donde se muestran los
	// resultados.
	JTextArea registros = new JTextArea();
	// Atributo que define e instancia el JScrollPane que proporciona barra de
	// scroll al JtextArea anterior.
	JScrollPane scroll = new JScrollPane(registros);
	
	// Se define e instancia el JButton que dispara el muestreo de datos
	// de la base de datos.
	JButton mostrar = new JButton("MOSTRAR");


	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaConsultarTodos() {
		// No se le define ning�n Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tama�o de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se a�ade y posiciona el JTextArea, se activan las propiedades
		// LineWrap y WrapStyleWord para se cambie de rengl�n
		// autom�ticamente
		registros.setLineWrap(true);
		registros.setWrapStyleWord(true);
		registros.setBounds(50, 110, 440, 380);

		// Se a�ade y posiciona el JScrollPane.
		scroll.setBounds(50, 110, 440, 380);
		add(scroll);
		
		// Se a�ade y posiciona el bot�n mostrar
		add(mostrar);
		mostrar.setBounds(340, 520, 150, 50);
		// Se ponen el bot�n mostrar a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaConsultarTodos
		mostrar.addActionListener(this);
	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel *
	 *************************************************************************/

	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripci�n: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaMostrarTodo los
	 * carteles informativos.
	 */
	
	public void paintComponent(Graphics g) {
		// Se llama al m�todo paintComponent de la clase JPanel para no
		// perder su contenido
		super.paintComponent(g);
		// Casteamos la instancia "g" de Graphics a Graphics2D.
		// g2 es como si fuera el pincel que se encarga de pintar en el JPanel
		Graphics2D g2 = (Graphics2D) g;

		// Creamos una fuente, Arial, tama�o 26, en negrita y cursiva
		Font miFont = new Font("Arial", 3, 26);
		// Asignamos la fuente al pincel
		g2.setFont(miFont);
		// Pintamos y posicionamos el texto del t�tulo del JPanel
		g2.drawString("MOSTRAR TODOS LOS REGISTROS", 50, 50);
	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener *
	 *************************************************************************/
	
	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripci�n: 
	 * Captura el evento lanzado por el JButton mostrar. Recopila los datos
	 * de los registros de la base de datos lanzando el m�todo consultarTodos()
	 * de la clase LibrosDAO y los muestra en el JTextArea registros
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// Se crea una instancia de la clase LibrosDAO
		LibrosDAO muestra_BD = new LibrosDAO();
		
		// Se llama al m�todo consultarTodos y se almacena el List que devuelve
		List<Libro> librosBD = muestra_BD.consultarTodos();
		
		// Se pasa el List a Array de objetos gen�ricos
		Object[] arrayLibros = librosBD.toArray();
		
		// Creamos una variable String y le asignamos una cadena vac�a
		String str = "";
		
		// Recorremos el array y almacenamos los atributos de todos los objetos que lo componen
		// en la variable String creada en el paso anterior. Previo a la optenci�n del atributo 
		// hay que castear el objeto gener�rico del Array a Libro para poder utilizar los
		// m�todos getters.
		for (int i = 0; i < arrayLibros.length; i++) {
			str = str + ("ID: " + ((Libro) arrayLibros[i]).getID() + "\n");
			str = str + ("T�TULO: " + ((Libro) arrayLibros[i]).getTitulo() + "\n");
			str = str + ("AUTOR: " + ((Libro) arrayLibros[i]).getAutor() + "\n");
			str = str + ("EDITORIAL: " + ((Libro) arrayLibros[i]).getEditorial()+ "\n");
			str = str + ("ISBN: " + ((Libro) arrayLibros[i]).getIsbn() + "\n");
			str = str + ("PUBLICACION: " + ((Libro) arrayLibros[i]).getPublicacion() + "\n");
			str = str + ("PRECIO: " + ((Libro) arrayLibros[i]).getPrecio() + "\n");
			str = str + ("DESCRIPCI�N: " + ((Libro) arrayLibros[i]).getDescripcion() + "\n\n");
			str = str + ("--------------------------------------------------\n\n");
		}
		
		// Asignamos el String str a JTextArea de JPanel LaminaConsultarTodos
		registros.setText(str);
		
	}
		
}