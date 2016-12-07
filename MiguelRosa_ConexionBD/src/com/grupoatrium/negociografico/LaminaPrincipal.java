package com.grupoatrium.negociografico;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/******************************************************************************
 * Nombre de Clase : LaminaPrincipal 
 * Autor: Miguel Rosa 
 * Fecha: 12 - Noviembre - 2016 
 * Versión: 1.0 
 * Hereda: JPanel 
 * Implementa: ActionListener
 * Descripcion: 
 * PanelPrincipal es un JPanel de dimensiones 800 x 600 pixeles al
 * que se le establece un Layout de tipo BorderLayout. A este JPanel en su
 * sección WEST (BorderLayout) se añade otro JPanel llamado "botones" que
 * contiene todos los botones desde los que se llama a las diferentes láminas
 * que contienen los componentes que solicitan y muestran datos al usuario.
 ******************************************************************************/

public class LaminaPrincipal extends JPanel implements ActionListener {

	/*************************************************************************
	 * ATRIBUTOS
	 *************************************************************************/

	// Ancho definido en 800 px.
	private final int ANCHO = 800;

	// Alto definido en 600 px.
	private final int ALTO = 600;

	// Se declaran y definen los botones que se van a añadir al JPanel botones

	// Dar de alta un libro
	private JButton altaLibro = new JButton("Dar de alta un nuevo libro");

	// Eliminar un libro por ID
	private JButton eliminarLibro = new JButton("Eliminar un libro por ID");

	// Mostrar todos los registros
	private JButton consultarTodos = new JButton("Motrar todos los registros");

	// Mostrar los datos de un libro con el ISBN indicado por el usuario
	private JButton consultarISBN = new JButton("Mostrar libro por ISBN");

	// Mostrar todos los libros que contengan en su título el String pasasdo
	// por el usuario
	private JButton consultarTitulo = new JButton("Filtrar libros por título");

	// Modificar el precio del libro con el ISBN indicado por el usuario
	private JButton modificarPrecio = new JButton("Modificar precio de un libro");

	// Se declara el JPanel botones
	JPanel botones;

	// Se declara el JPanel panelPrincipal
	JPanel panelPrincipal;

	// Se declara el JPanel lamina
	JPanel lamina;

	/*************************************************************************
	 * CONSTRUCTORES
	 *************************************************************************/

	public LaminaPrincipal() {

		// Se define el tamaño del JPanel LaminaPrincipal
		setPreferredSize(new Dimension(ANCHO, ALTO));

		// Se define su Layout a BorderLayout
		setLayout(new BorderLayout());

		// Se define el JPanel botones
		botones = new JPanel();
		// No se le asigna ningún Layout para poder posicionar los botones en
		// sus coordenadas X e Y
		botones.setLayout(null);
		// Se le define unas dimensiones de 250 de ancho por 600 de alto
		botones.setPreferredSize(new Dimension(250, 600));

		// Se añaden los botones al JPanel botones
		botones.add(altaLibro);
		botones.add(eliminarLibro);
		botones.add(consultarTodos);
		botones.add(consultarISBN);
		botones.add(consultarTitulo);
		botones.add(modificarPrecio);

		// Se posicionan los botones dentro de JPanel botones
		altaLibro.setBounds(10, 10, 230, 60);
		eliminarLibro.setBounds(10, 80, 230, 60);
		consultarTodos.setBounds(10, 150, 230, 60);
		consultarISBN.setBounds(10, 220, 230, 60);
		consultarTitulo.setBounds(10, 290, 230, 60);
		modificarPrecio.setBounds(10, 360, 230, 60);

		// Se ponen los botones a la espera de hacer clic siendo el objeto que
		// realizará la acción en todos los casos el propio JPanel
		// LaminaPrincipal
		altaLibro.addActionListener(this);
		eliminarLibro.addActionListener(this);
		consultarTodos.addActionListener(this);
		consultarISBN.addActionListener(this);
		consultarTitulo.addActionListener(this);
		modificarPrecio.addActionListener(this);

		// Se añade el JPanel botones a la sección WEST del BorderLayout del
		// JPanel LaminaPrincipal
		add(botones, BorderLayout.WEST);

		// Se define el JPanel panelPrincipal como JPanel
		panelPrincipal = new JPanel();
		// No se le asigna ningún Layout para posicionar sus componentes con los
		// ejes X e Y
		panelPrincipal.setLayout(null);
		// Se dimensiona el JPanel panelPrincipal a 550 de ancho por 600 de alto
		panelPrincipal.setPreferredSize(new Dimension(550, 600));
		// Se asigna el JPanel panelPrincipal a la sección CENTER
		// delBorderLayout del JPanel LaminaPrincipal
		add(panelPrincipal, BorderLayout.CENTER);

	}

	/*************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ActionListener
	 *************************************************************************/

	/*
	 * Nombre: actionPerformed() 
	 * Argumentos: ActioenEvent 
	 * Devuelve: Nada
	 * Descripción: Captura los eventos lanzados por los JButtons del JPanel
	 * botones. Se han creado varias clases que heredan de JPanel, son:
	 * 		· LaminaAltaLibro 
	 * 		· LaminaEliminarLibro 
	 * 		· LaminaConsultarTodos 
	 * 		· LaminaConsultarISBN 
	 * 		· LaminaConsultarTitulo 
	 * 		· LaminaModificarPrecio 
	 * Estas clases se instancian y añaden a una variable JPanel llamada "lamina"
	 * cuando el usuario hace clic sobre el botón correspondiente, a continuación 
	 * se añade "lamina" al JPanel panelPrincipal para que se le muestre al 
	 * usuario.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		// Si lamina tiene una lámina ya asignada la borramos.
		if (lamina != null)
			lamina.removeAll();

		// Si el botón apretado es "altaLibro"
		if (e.getSource() == altaLibro) {
			lamina = new LaminaAltaLibro(); 		// Creamos una instancia de
													// LaminaAltaLibro y la
													// asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

		// Si el botón apretado es "eliminarLibro"
		if (e.getSource() == eliminarLibro) {

			lamina = new LaminaEliminarLibro(); 	// Creamos una instancia de
													// LaminaEliminarLibro y la
													// asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

		// Si el botón apretado es "consultarTodos"
		if (e.getSource() == consultarTodos) {

			lamina = new LaminaConsultarTodos();	// Creamos una instancia de
													// LaminaConsultarTodos y la
													// asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

		// Si el botón apretado es "consultarISBN"
		if (e.getSource() == consultarISBN) {

			lamina = new LaminaConsultarISBN(); 	// Creamos una instancia de
													// LaminaConsultarISBN y la
													// asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

		// Si el botón apretado es "consultarTitulo"
		if (e.getSource() == consultarTitulo) {

			lamina = new LaminaConsultarTitulo(); 	// Creamos una instancia de
													// LaminaConsultarTitulo y
													// la asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

		// Si el botón apretado es "modificarPrecio"
		if (e.getSource() == modificarPrecio) {

			lamina = new LaminaCambiarPrecio(); 	// Creamos una instancia de
													// LaminaModificarPrecio y la
													// asignamos a lamina
			panelPrincipal.add(lamina); 			// Añadimos lamina al JPanel
													// panelPrincipal
			lamina.setBounds(0, 0, 550, 600); 		// Lo posicionamos en (0,0) y lo
													// dimensionamos a 550 x 600
		}

	}

}
