package com.grupoatrium.negocio;

import java.util.List;
import java.util.Scanner;

import com.grupoatrium.cliente.OpcionIncorrectaException;
import com.grupoatrium.cliente.SeleccionModo;
import com.grupoatrium.modelo.Libro;
import com.grupoatrium.modelo.LibroNoEncontradoException;
import com.grupoatrium.persistencia.LibrosDAO;

/*****************************************************************************************
 * Nombre de Clase : GestionLibreria													 *
 * Autor: Miguel Rosa																	 *
 * Fecha: 07 - Noviembre - 2016															 *
 * Versión: 1.0																			 *
 * Descripción: Se escriben unas breves instrucciones por consola para que el			 *
 * usuario seleccione:																	 *
 * 																						 *
 * 		1 Dar de ALTA un libro en la Base de Datos.										 *
 *		2 ELIMINAR un libro de la Base de Datos.										 *
 *		3 Consultar TODOS los libros de la Base de Datos (por defecto).					 *
 *		4 Consultar libro por ISBN.														 *
 *		5 Consultar libro por TITULO.													 *
 *		6 Modificar PRECIO de un libro.													 *
 *		7 SALIR																			 *
 * 																						 *
 * Si la opción introducida por el usuario no corresponde a ninguna de las				 *
 * anteriores se repetirá el mismo mensaje de selección hasta que la opción 			 *
 * introducida coincida.																 *
 * 																						 *
 * En función de la selección del usuario, se lanzará el método correspondiente			 *
 * así como nuevas instrucciones por consola solicitando los datos que fueran			 *
 * necesarios.																			 *
 *****************************************************************************************/

public class GestionLibreria implements ItfzGestionLibreria{
	
	/*************************************************************************************
	 * ATRIBUTOS																		 *
	 *************************************************************************************/
	private String seleccion;	// Atributo que se encarga de almacenar la opción del
								// usuario
	
	private boolean salir;		// Atributo que se encarga de la salida del programa
	
	/*************************************************************************************
	 * CONSTRUCTORES																	 *
	 *************************************************************************************/
	public GestionLibreria() {
		
		salir = false;
		
		while (!salir) {
			// Impresión en consola de unas breves instrucciones.
			System.out.println();
			System.out.println("Seleccione una opción:\n");
			System.out.println("\t1 Dar de ALTA un libro en la Base de Datos.");
			System.out.println("\t2 ELIMINAR un libro de la Base de Datos.");
			System.out.println("\t3 Consultar TODOS los libros de la Base de Datos (por defecto).");
			System.out.println("\t4 Consultar libro por ISBN.");
			System.out.println("\t5 Consultar libro por TITULO.");
			System.out.println("\t6 Modificar PRECIO de un libro.");
			System.out.println();
			System.out.println("\t7 ATRAS");
			System.out.println();
			System.out.println("\t8 SALIR");
			System.out.println();
			System.out.println("Puede seleccionar el número o escribir la palabra clave.");
			
			// Se crea una instancia de la Scanner para recoger la opción del usuario
			Scanner sc = new Scanner(System.in);
			
			// Se asigna la opción del usuario al atributo "seleccion". El String devuelto
			// por el usuario se convierte a minúsculas antes de almacenarlo para que la
			// comprobación sea independiente de las mayúsculas o minúsculas.
			seleccion = sc.nextLine().toLowerCase();
			
			// Se invoca al método comprobar.
			try {
				comprobar();
			} catch (Exception e) {
				new GestionLibreria();
			}
		}
			
	}
	
	/*************************************************************************************
	 * METODOS																			 *
	 *************************************************************************************/
	
	/* Nombre: comprobar()
	 * Argumentos: Ninguno
	 * Devuelve: Nada
	 * Descripción: 
	 * Se compara el valor del atributo "seleccion" introducido por el usuario con 
	 * los siguientes valores:
	 * 
	 * 		· "1" ó "alta"  ->  Se lanza el método altaLibro(Libro libro)
	 * 		· "2" ó "eliminar" -> Se lanza el método eliminarLibro(int id)
	 * 		· "3" ó "todo" ó "todos" ó "" -> Se lanza el método consultarTodos()
	 * 		· "4" ó "isbn" -> Se lanza el método consultarISBN(String isbn)
	 * 		· "5" ó "titulo" ó "título" -> Se lanza el método 
	 * 									   consultarTitulo(String titulo)
	 * 		· "6" ó "precio" -> Se lanza el método 
	 * 							modificarPrecio(String isbn, double precio)
	 * 		· "7" ó "atrás" ó "atras" -> Se vuelve al menú anterior de 
	 * 									 Selección de Modo
	 * 		
	 * Si la opción introducida por el usuario no corresponde a ninguna de las
	 * anteriores se repetirá el mismo mensaje de selección hasta que la
	 * opción introducida coincida.
	 */
	
	public void comprobar() throws OpcionIncorrectaException{
		
		// Opción 1. Dar de Alta un Libro
		
		if (seleccion.equals("1") || (seleccion.equals("alta"))) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A DAR DE ALTA UN LIBRO. Pulse INTRO para continuar.");
			
			// Se crea una instancia de la clase Libro. El constructor de esta clase
			// proporciona instrucciones al usuario para introducir los datos.
			Libro nuevoLibro = crearLibroUsuario();
			
			// Se lanza el método altaLibro(Libro libro). El argumento de este método
			// es la instancia de la Clase Libro que creamos en el paso anterior.
			if (altaLibro(nuevoLibro)) {
				informacionYEspera("EL LIBRO SE HA DADO DE ALTA EN LA BASE DE DATOS CON EL ID: " +
									nuevoLibro.getID() + ".\nPulse INTRO para continuar.");
			
			// Si altaLibro() devuelve false quiere decir que algo ha fallado y el libro no
			// se ha dado de alta.
			} else {
				informacionYEspera("SE LE REDIRECCIONARÁ AL MENÚ PRINCIPAL. Pulse INTRO para continuar.");
			}
		}
		
		// Opción 2. Eliminar un Libro
		
		else if (seleccion.equals("2") || (seleccion.equals("eliminar"))) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A ELIMINAR UN LIBRO. Pulse INTRO para continuar.");
			
			// Se informa al usuario para que introduca el ID del libro que desea 
			// eliminar de la base de datos.
			System.out.println();
			System.out.println("POR FAVOR, INTRODUZCA EL ID DEL LIBRO QUE DESEA ELIMINAR y pulse INTRO.");
			
			// Se espera a que el usuario introduzca el ID del libro. El ID se almacena
			// en una variable String temporal que luego es convertida a entero y 
			// almacenada en la variable int id. Debido a que el usuario puede 
			//introducir un valor no  convertible a entero se crea un bucle do/while 
			// que capture la posible excepción NumberFormatException y se solicita un 
			// nuevo valor en caso de que esto ocurra.
			
			boolean error; //Esta es la variable que controla la salida del 
						   //bucle do/while

			do {
				error = false;
				try {
					Scanner sc = new Scanner(System.in);
					String temporal = sc.nextLine();
					int id = Integer.parseInt(temporal);
					
					// Se lanza el método eliminarLibro(int id), el ID que se pasa 
					// por argumento es el id introducido por el usuario.
					if (eliminarLibro(id)) {
						informacionYEspera("EL LIBRO CON ID " + id + ", SE HA ELIMINADO DE LA BASE DE DATOS.\nPulse INTRO para continuar.");
					
					// Si altaLibro() devuelve false quiere decir que algo ha fallado y el libro no
					// se ha eliminado.
					} else {
						informacionYEspera("SE LE REDIRECCIONARÁ AL MENÚ PRINCIPAL. Pulse INTRO para continuar.");
					}
					
				} catch (NumberFormatException e) {
					System.out.println("EL DATO INTRODUCIDO NO SE RECONOCE. Introduzca un ID correcto.");
					error = true;
				}
			} while (error);
		}
		
		// Opción 3. Mostrar los libros de la Base de Datos
		
		else if (seleccion.equals("3") || seleccion.equals("todo") || 
				seleccion.equals("todos") || seleccion.equals("")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VAN A MOSTRAR TODOS LOS LIBROS DE LA BASE DE DATOS. Pulse INTRO para continuar.");
			
			// Se lanza el método consultarTodos() y se almacena en un List;
			List<Libro> librosBD = consultarTodos();
			
			// Se pasa el List a Array de objetos genéricos
			Object[] arrayLibros = librosBD.toArray();
			
			// Recorremos el array e imprimimos los atributos de todos los objetos que lo componen.
			// Previo a la optención del atributo hat que castear el objeto generérico del Array a
			// Libro para poder utilizar los métodos getters.
			for (int i = 0; i < arrayLibros.length; i++) {
				System.out.println("ID: " + ((Libro) arrayLibros[i]).getID());
				System.out.println("TÍTULO: " + ((Libro) arrayLibros[i]).getTitulo());
				System.out.println("AUTOR: " + ((Libro) arrayLibros[i]).getAutor());
				System.out.println("EDITORIAL: " + ((Libro) arrayLibros[i]).getEditorial());
				System.out.println("ISBN: " + ((Libro) arrayLibros[i]).getIsbn());
				System.out.println("PUBLICACION: " + ((Libro) arrayLibros[i]).getPublicacion());
				System.out.println("PRECIO: " + ((Libro) arrayLibros[i]).getPrecio());
				System.out.println("DESCRIPCIÓN: " + ((Libro) arrayLibros[i]).getDescripcion());
				System.out.println();
				System.out.println("--------------------------------------------------");
				System.out.println();
			}
			
		} 
		
		// Opción 4. Mostrar el libro con el ISBN indicado por el usuario
		
		else if (seleccion.equals("4") || seleccion.equals("isbn")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A REALIZAR UNA BÚSQUEDA POR ISBN EN LA BASE DE DATOS. Pulse INTRO para continuar.");
			
			// Se informa al usuario para que introduca el ISBN del libro que desea
			// localizar de la base de datos.
			System.out.println();
			System.out.println("POR FAVOR, INTRODUZCA EL ISBN DEL LIBRO QUE DESEA LOCALIZAR y pulse INTRO.");
			
			// Se crea una instancia de la clase Scanner para recoger el dato
			// introducido por el usuario y se almacena en el String isbn. 
			Scanner sc = new Scanner(System.in);
			String isbn = sc.nextLine();
			
			// Se lanza el método consultarISBN(String isbn) que nos debe devolver el
			// libro con ese isbn
			Libro miLibro = consultarISBN(isbn);
			
			// Imprimimos todos los atributos del objeto Libro devuelto en el paso anterior
			System.out.println("ID: " + miLibro.getID());
			System.out.println("TÍTULO: " + miLibro.getTitulo());
			System.out.println("AUTOR: " + miLibro.getAutor());
			System.out.println("EDITORIAL: " + miLibro.getEditorial());
			System.out.println("ISBN: " + miLibro.getIsbn());
			System.out.println("PUBLICACION: " + miLibro.getPublicacion());
			System.out.println("PRECIO: " + miLibro.getPrecio());
			System.out.println("DESCRIPCIÓN: " + miLibro.getDescripcion());
			System.out.println();
			System.out.println("--------------------------------------------------");
			System.out.println();			
		} 
		
		// Opción 5. Mostrar el/los libro/s con el título similar al introducido por el usuario
		
		else if (seleccion.equals("5") || seleccion.equals("titulo") ||
				   seleccion.equals("título")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A REALIZAR UNA BÚSQUEDA POR TITULO EN LA BASE DE DATOS. Pulse INTRO para continuar.");
			
			// Se informa al usuario para que introduca el título del libro que desea
			// localizar de la base de datos.
			System.out.println();
			System.out.println("POR FAVOR, INTRODUZCA EL TÍTULO DEL LIBRO QUE DESEA LOCALIZAR y pulse INTRO.");
			
			// Se crea una instancia de la clase Scanner para recoger el dato
			// introducido por el usuario y se almacena en el String titulo. 
			Scanner sc = new Scanner(System.in);
			String titulo = sc.nextLine();
			
			// Se lanza el método consultarTitulo() y se almacena en un List;
			List<Libro> librosBD = consultarTitulo(titulo);
			
			// Se pasa el List a Array objetos genéricos
			Object[] arrayLibros = librosBD.toArray();
			
			// Recorremos el array e imprimimos los atributos de todos los objetos que lo componen.
			// Previo a la optención del atributo hat que castear el objeto generérico del Array a
			// Libro para poder utilizar los métodos getters.
			for (int i = 0; i < arrayLibros.length; i++) {
				System.out.println("ID: " + ((Libro) arrayLibros[i]).getID());
				System.out.println("TÍTULO: " + ((Libro) arrayLibros[i]).getTitulo());
				System.out.println("AUTOR: " + ((Libro) arrayLibros[i]).getAutor());
				System.out.println("EDITORIAL: " + ((Libro) arrayLibros[i]).getEditorial());
				System.out.println("ISBN: " + ((Libro) arrayLibros[i]).getIsbn());
				System.out.println("PUBLICACION: " + ((Libro) arrayLibros[i]).getPublicacion());
				System.out.println("PRECIO: " + ((Libro) arrayLibros[i]).getPrecio());
				System.out.println("DESCRIPCIÓN: " + ((Libro) arrayLibros[i]).getDescripcion());
				System.out.println();
				System.out.println("--------------------------------------------------");
				System.out.println();
			}
			
			
		} 
		
		// Opción 6. Cambiar el precio de un libro
		
		else if (seleccion.equals("6") || seleccion.equals("precio")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A CAMBIAR EL PRECIO DE UN LIBRO EN LA BASE DE DATOS. Pulse INTRO para continuar.");
			
			// Se informa al usuario para que introduca el ISBN del libro que desea
			// localizar de la base de datos.
			System.out.println();
			System.out.println("POR FAVOR, INTRODUZCA EL ISBN DEL LIBRO AL QUE DESEA CAMBIAR EL PRECIO y pulse INTRO.");
			
			// Se crea una instancia de la clase Scanner para recoger el dato
			// introducido por el usuario y se almacena en el String isbn. 
			Scanner sc = new Scanner(System.in);
			String isbn = sc.nextLine();				
			
			// Se informa al usuario para que introduca el nuevo precio del libro
			// al que acaba de introducir el ISBN.
			System.out.println();
			System.out.println("POR FAVOR, INTRODUZCA EL NUEVO PRECIO PARA EL LIBRO CON ISBN " + isbn + "\ny pulse INTRO.");
			
			// Se espera a que el usuario introduzca el PRECIO del libro, éste se almacena
			// en una variable String temporal que luego es convertida a Double y 
			// almacenada en la variable double precio. Debido a que el usuario puede 
			//introducir un valor no  convertible a double se crea un bucle do/while 
			// que capture la posible excepción NumberFormatException y se solicita un 
			// nuevo valor en caso de que esto ocurra.
			
			boolean error; 	//Esta es la variable que controla la salida del 
			 				//bucle do/while
			do {
				error = false;
				try {
					sc = new Scanner(System.in);
					String temporal = sc.nextLine();
					double precio = Double.parseDouble(temporal);
					
					// Se lanza el método modificarPrecio(String isbn, double precio),
					// los argumentos isbn y precio son datos introducidos anteriormente
					// por el usuario.
					if (modificarPrecio(isbn, precio)) {
						informacionYEspera("SE HA MODIFICADO EL PRECIO DEL LIBRO CON ISBN " +
											isbn + " A " + precio + ".\nPulse INTRO para continuar.");
						
					// Si modificarPrecio() devuelve false quiere decir que algo ha fallado y el precio del
					// libro no se ha modificado.
					} else {
						informacionYEspera("SE LE REDIRECCIONARÁ AL MENÚ PRINCIPAL. Pulse INTRO para continuar.");
					}
					
				} catch (NumberFormatException e) {
					System.out.println("EL DATO INTRODUCIDO NO SE RECONOCE COMO PRECIO. Introduzca un PRECIO correcto.");
					error = true;
				}
			} while (error);

		} 
		
		// Opción 7. Volver al menú de selección de modo gráfico.
		
		else if (seleccion.equals("7") || seleccion.equals("atrás") ||
				   seleccion.equals("atras")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE LE VA A REDIRIGIR AL MENÚ ANTERIOR. Pulse INTRO para continuar.");
			
			// Se vuelve al menú inicial creando una nueva instancia de la Clase SeleccionModo.
			new SeleccionModo();
			
		}
		
		// Opción 8. Salir de la aplicación.
		
		else if (seleccion.equals("8") || seleccion.equals("salir")) {
			
			// Se informa al usuario de la opción escogida.
			informacionYEspera("SE VA A SALIR DE LA APLICACIÓN. MUCHAS GRACIAS POR SU USO. Pulse INTRO para continuar.");
			
			// Se sale del bucle del constructor poniendo la variable salir a true 
			salir = true;
			
			
		}
		
		// Se lanza la excepción OpcionIncorrecta cuando la opción introducida por el
		// usuario no corresponde con ninguna de las anteriores
		
		else throw new OpcionIncorrectaException();
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: informacionYEspera()
	 * Argumentos: String
	 * Devuelve: void
	 * Descripción: Se detecta durante la realización del programa que las
	 * instrucciones de este método se repiten considerablemente.
	 * Se crea este método para simplificar y clarificar el código. El String que
	 * se pasa como parámetro se muestra al usuario para informarle de algún evento 
	 * o solicitarle información.
	 */
	
	public String informacionYEspera(String str) {
		
		// Se informa al usuario o se le solicita un dato.
		System.out.println();
		System.out.println(str);
		
		// Se devuelven los datos escritos por el usuario.
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: crearLibroUsuario()
	 * Argumentos: Ninguno
	 * Devuelve: Libro
	 * Descripción: Este método se encarga de la solicitud al usuario de los datos
	 * necesarios para crear un objeto de la clase Libro.
	 */
	
	public Libro crearLibroUsuario() {
		
		// Se declaran las variables que almacenarán los datos asignando valores por defecto.
		
		String temporal = "";			// Usamos esta variable para almacenar temporalmente los
										// datos introducidos por el usuario que requieran ser
										// convertidos a otro tipo, como en el caso de las
										// variables publicacion y precio.
		
		boolean error = false; 			// Esta es la variable que controla la salida de los 
										// bucles do/while para la conversión de datos.
		
		String autor = ""; 				// Nombre del autor
		String descripcion = ""; 		// Sinopsis del Libro
		String editorial = "";			// Editorial que lo ha publicado
		int ID = 0;						// identificador interno
		String isbn = "";				// Código ISBN
		double precio = 0.0;			// Precio en euros
		int publicacion = 0;			// Año de publicación
		String titulo = "";				// Título del libro
		
		// Se solicitan los datos al usuario.
		
		// titulo
		titulo = informacionYEspera("INTRODUZCA EL TÍTULO DEL LIBRO");
		
		// autor
		autor = informacionYEspera("INTRODUZCA EL AUTOR DEL LIBRO");
		
		// editorial
		editorial = informacionYEspera("INTRODUZCA LA EDITORIAL DEL LIBRO");
		
		// isbn
		isbn = informacionYEspera("INTRODUZCA EL ISBN DEL LIBRO");
		
		// publicacion. Al tratarse publicacion de una variable de tipo int hay que convertir
		// el dato del usuario de String a Integer, como este proceso puede lanzar una
		// excepción, la capturamos y solicitamos al usuario que repita el proceso hasta que
		// que el dato introducido sea correcto.
		
		temporal = informacionYEspera("INTRODUZCA EL AÑO DE PUBLICACION DEL LIBRO");
		
		do {
			error = false;
			try {
				publicacion = Integer.parseInt(temporal);
			} catch (NumberFormatException e) {
				System.out.println("EL DATO INTRODUCIDO NO SE RECONOCE COMO AÑO. Introduzca un AÑO correcto.");
				error = true;
			}
		} while (error);
		
		// precio. Al tratarse precio de una variable de tipo double hay que convertir
		// el dato del usuario de String a Double, como este proceso puede lanzar una
		// excepción, la capturamos y solicitamos al usuario que repita el proceso hasta que
		// que el dato introducido sea correcto.
		
		temporal = informacionYEspera("INTRODUZCA EL PRECIO DEL LIBRO");
		
			do {
				error = false;
				try {
					precio = Double.parseDouble(temporal);
				} catch (NumberFormatException e) {
					System.out.println("EL DATO INTRODUCIDO NO SE RECONOCE COMO PRECIO. Introduzca un PRECIO correcto.");
					error = true;
				}
			} while (error);
	
		// descripcion		
		descripcion = informacionYEspera("INTRODUZCA UNA DESCRIPCION DEL LIBRO");
		
		Libro miLibro = new Libro(autor, descripcion, editorial, isbn, precio, publicacion, titulo);
		
		return miLibro;
	}
	
	/*************************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ItfzGestionLibreria								 *
	 *************************************************************************************/
	
	/* Nombre: altaLibro()
	 * Argumentos: Libro
	 * Devuelve: Boolean
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * altaLibro() devolviendo su resultado e independizando la clase GestionLibreria de
	 * la conexión a la base de datos
	 * El parámetro que recibe este método es el mismo que se pasa al método homónimo de
	 * la clase LibrosDAO.
	 */
	
	@Override
	public boolean altaLibro(Libro libro) {
		LibrosDAO conexionBD = new LibrosDAO();
		
		if (conexionBD.altaLibro(libro))
			return true;
		else
			return false;
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: eliminarLibro()
	 * Argumentos: Integer
	 * Devuelve: Boolean
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * eliminarLibro() devolviendo su resultado e independizando la clase GestionLibreria de
	 * la conexión a la base de datos.
	 * El parámetro que recibe este método es el mismo que se pasa al método homónimo de
	 * la clase LibrosDAO. 
	 */

	@Override
	public boolean eliminarLibro(int id) {
		
		LibrosDAO conexionBD = new LibrosDAO();
		
		try {
			conexionBD.eliminarLibro(id);
			return true;
				
		} catch (LibroNoEncontradoException e) {
			e.mensajeConsola("ID");
			return false;
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarTodos()
	 * Argumentos: Ninguno
	 * Devuelve: List
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * consultarTodos() devolviendo su resultado e independizando la clase GestionLibreria 
	 * de la conexión a la base de datos.
	 * El parámetro que recibe este método es el mismo que se pasa al método homónimo de
	 * la clase LibrosDAO. 
	 */

	@Override
	public List<Libro> consultarTodos() {
		
		LibrosDAO conexionBD = new LibrosDAO();
		
		if (conexionBD.consultarTodos() != null)
			return conexionBD.consultarTodos();
		else
			return null;
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarISBN()
	 * Argumentos: String
	 * Devuelve: Libro
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * consultarISBN() devolviendo su resultado e independizando la clase GestionLibreria 
	 * de la conexión a la base de datos.
	 * El parámetro que recibe este método es el mismo que se pasa al método homónimo de
	 * la clase LibrosDAO. 
	 */

	@Override
	public Libro consultarISBN(String isbn) {
		
		LibrosDAO conexionBD = new LibrosDAO();
		
		try {
			return conexionBD.consultarISBN(isbn);
		} catch (LibroNoEncontradoException e) {
			e.mensajeConsola("ISBN");
			return null;
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarTitulo()
	 * Argumentos: String
	 * Devuelve: List
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * consultarTitulo() devolviendo su resultado e independizando la clase GestionLibreria 
	 * de la conexión a la base de datos.
	 * El parámetro que recibe este método es el mismo que se pasa al método homónimo de
	 * la clase LibrosDAO. 
	 */

	@Override
	public List<Libro> consultarTitulo(String titulo) {
		
		LibrosDAO conexionBD = new LibrosDAO();
		
		try {
			return conexionBD.consultarTitulo(titulo);
		} catch (LibroNoEncontradoException e) {
			e.mensajeConsola("TÍTULO");
			return null;
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: modificarPrecio()
	 * Argumentos: String, Double
	 * Devuelve: Boolean
	 * Descripción: Crea una instancia de la clase LibrosDAO y lanza su método
	 * modificarPrecio() devolviendo su resultado e independizando la clase GestionLibreria 
	 * de la conexión a la base de datos.
	 * Los parámetros que recibe este método son los mismos que se pasan al método homónimo
	 * de la clase LibrosDAO. 
	 */

	@Override
	public boolean modificarPrecio(String isbn, double precio) {
		
		LibrosDAO conexionBD = new LibrosDAO();
		
		try {
			return conexionBD.modificarPrecio(isbn, precio);
		} catch (LibroNoEncontradoException e) {
			e.mensajeConsola("ISBN");
			return false;
		}
	}
	
}

/*************************************************************************************
 * METODOS INNECESARIOS																 *
 *************************************************************************************/

/* Nombre: formateadoISBN()
 * Argumentos: String
 * Devuelve: String
 * Descripción: Ya que el ISBN es un número complejo que en ocasiones viene divido
 * por diferentes bloques numéricos separados por guiones o espacios, este método
 * elimina los guines y espacios quedándose con un único bloque numérico.
 * Este método está pensado únicamente para propósitos de búsqueda y comparación,
 * por lo que los datos introducidos en la Base de Datos deben intentar pasarse 
 * con el formato original de guiones o espacios.
 * Además, todos aquellos caracteres que no sean numéricos serán eliminados. 
 */

 /*

public String formateadoISBN(String str) {
	// Todos los caracteres numéricos
	char[] comparacion = {'0','1', '2','3','4','5','6','7','8','9'};
	// La cadena pasada como argumento se pasa a un array de Character
	char[] array = str.toCharArray();
	// Se crea un ArrayList vacío para ir almacenando caracteres
	List<Character> lista = new ArrayList<Character>();
	
	// Se recorre la cadena de ISBN carácter a carácter, en caso de que sea
	// un número, éste se añade al Arraylist y se continúa con el siguiente
	// carácter.
	for (int i = 0; i < array.length; i++) {
		for(int j = 0; j < comparacion.length; j++) {
			if (array[i] == comparacion[j]) {
				lista.add(array[i]);
				break;
			}
		}
	}
	// Se devuelve el contenido de la lista de Character como un String
	return lista.toString(); 
}

 */
