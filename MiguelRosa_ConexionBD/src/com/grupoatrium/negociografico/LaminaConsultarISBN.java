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

/******************************************************************************
 * Nombre de Clase : LaminaConsultarISBN
 * Autor: Miguel Rosa 
 * Fecha: 15 - Noviembre - 2016 
 * Versi�n: 1.0 
 * Hereda: JPanel 
 * Implementa: ActionListener
 * Descripcion: 
 * LaminaConsultarISBN es la clase que crea el JPanel que tiene todos los
 * componentes para poder mostrar los datos de un libro de la base de datos 
 * seg�n el �c�digo ISBN introducido por el usuario. No se le asigna ning�n 
 * layout para poder posicionar los elementos en sus coordenadas X e Y. 
 * Cuando se presiona el bot�n buscar, se recoge el isbn introducido en el 
 * JTextField y se lanza el m�todo consultarISBN() de la clase LibrosDAO
 * mostrando los datos recibidos en el JTextArea habilitado para ello.
 ******************************************************************************/

public class LaminaConsultarISBN extends JPanel implements ActionListener {

	/*************************************************************************
	 * ATRIBUTOS *
	 *************************************************************************/
	
	// Atributo que define e instancia el JTextField para que el usuario
	// introduzca el isbn.
	JTextField isbn = new JTextField(50);
	
	// Atributo que define e instancia el JTextArea donde se muestran los
	// resultados.
	JTextArea registro = new JTextArea();
	// Atributo que define e instancia el JScrollPane que proporciona barra de
	// scroll al JtextArea anterior.
	JScrollPane scroll = new JScrollPane(registro);
	
	// Se define e instancia el JButton que dispara el muestreo de datos
	// de la base de datos.
	JButton buscar = new JButton("BUSCAR");

	/*************************************************************************
	 * CONSTRUCTORES *
	 *************************************************************************/

	public LaminaConsultarISBN() {
		// No se le define ning�n Layout para poder colocar los elementos
		// en el JPanel por sus ejes X e Y
		setLayout(null);
		// Se asigna al JPanel un tama�o de 550 x 600
		setPreferredSize(new Dimension(550, 600));

		// Se a�ade y posiciona el JTextField isbn
		add(isbn);
		isbn.setBounds(150, 210, 200, 20);

		// Se a�ade y posiciona el JTextArea, se activan las propiedades
		// LineWrap y WrapStyleWord para se cambie de rengl�n
		// autom�ticamente
		registro.setLineWrap(true);
		registro.setWrapStyleWord(true);
		registro.setBounds(50, 260, 440, 230);

		// Se a�ade y posiciona el JScrollPane.
		scroll.setBounds(50, 260, 440, 230);
		add(scroll);

		// Se a�ade y posiciona el bot�n buscar
		buscar.setBounds(340, 520, 150, 50);
		add(buscar);
		// Se ponen el bot�n buscar a la espera de hacer clic siendo el objeto 
		// receptor del evento la propia instancia de LaminaConsultarISBN
		buscar.addActionListener(this);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS CLASE JPanel *
	 *************************************************************************/
	
	/*
	 * Nombre: paintComponent() 
	 * Argumentos: Graphics 
	 * Devuelve: Nada
	 * Descripci�n: 
	 * Esta clase se encarga de dibujar en el JPanel LaminaConsultarISBN los
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
		g2.drawString("MOSTRAR LIBRO POR ISBN", 100, 50);
		
		// Creamos una nueva fuente, Arial, tama�o 16 y en negrita
		Font miFont2 = new Font("Arial", 1, 16);
		// Asignamos la fuente al pincel 
		g2.setFont(miFont2);
		// Pintamos y posicionamos los carteles que indican el dato del
		// JTextField y la acci�n que debe tomar el usuario
		g2.drawString("POR FAVOR, INTRUZCA EL ISBN DEL LIBRO QUE DESEA", 50, 125);
		g2.drawString("BUSCAR.", 50, 140);
		g2.drawString("ISBN:", 50, 225);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener *
	 *************************************************************************/

	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActionEvent
	 * Devuelve: Nada
	 * Descripci�n: 
	 * Captura el evento lanzado por el JButton buscar. Recopila el dato
	 * que hay en el JtextField isbn y lanza el m�todo consultarISBN() de la 
	 * clase LibrosDAO. Recoge los datos devueltos y los muestra en el
	 * JTextArea registro.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Se crea una instancia de la clase LibrosDAO
		LibrosDAO muestra_BD = new LibrosDAO();
		
		// Se recoge el String del JTextField isbn y se almacena
		String str = isbn.getText();
		
		// Se llama al m�todo consultarISBN y se almacena el objeto Libro que 
		// devuelve. Como este m�todo lanza la excepci�n
		// LibroNoEncontradoException se captura. 
		try {
			Libro libro = muestra_BD.consultarISBN(str);
			
			// Creamos el String y le damos como valor una cadena vac�a
			String cadena = "";
			
			// Recopilamos los atributos del objeto Libro devuelto por consultarISBN()
			// y lo almacenamos en el String cadena
			cadena = cadena + ("ID: " + libro.getID() + "\n");
			cadena = cadena + ("T�TULO: " + libro.getTitulo() + "\n");
			cadena = cadena + ("AUTOR: " + libro.getAutor() + "\n");
			cadena = cadena + ("EDITORIAL: " + libro.getEditorial()+ "\n");
			cadena = cadena + ("ISBN: " + libro.getIsbn() + "\n");
			cadena = cadena + ("PUBLICACION: " + libro.getPublicacion() + "\n");
			cadena = cadena + ("PRECIO: " + libro.getPrecio() + "\n");
			cadena = cadena + ("DESCRIPCI�N: " + libro.getDescripcion() + "\n");
			
			// Se asigna el contenido del String cadena al JTextArea registro
			registro.setText(cadena);
		
		// En caso de que no se encuentre ning�n resultado en la base de datos
		// capturamos la excepci�n y mostrarmos un Alert informando al usuario
		} catch (LibroNoEncontradoException excepcion) {
			excepcion.mensajeAlert("ISBN");;
		}

		

		

				
				

	}
}