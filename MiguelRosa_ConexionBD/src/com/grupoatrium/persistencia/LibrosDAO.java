package com.grupoatrium.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.grupoatrium.modelo.Libro;
import com.grupoatrium.modelo.LibroNoEncontradoException;


/*****************************************************************************************
 * Nombre de Clase : LibrosDAO															 *
 * Autor: Miguel Rosa																	 *
 * Fecha: 09 - Noviembre - 2016															 *
 * Versi�n: 1.0																			 *
 * Descripci�n: Esta clase se encarga de la capa de conexi�n con la base de datos.		 *
 * Los datos de conexi�n se definen directamente en la secci�n ATRIBUTOS. 				 *
 * Esta clase est� definida �nicamente para la conexi�n a una base de datos MySQL. 		 *
 *****************************************************************************************/

public class LibrosDAO implements ItfzLibrosDao{
	
	/*************************************************************************************
	 * ATRIBUTOS																		 *
	 *************************************************************************************/
	
	// Datos de Conexi�n
	
	private final String host = "localhost";	// Url o ip del servidor de la base de
												// datos. Por defecto, localhost.
	private final String port = "3306";			// Puerto por el que se comunica la
												// aplicaci�n con el servidor.
	private final String user = "root";			// Usuario.
	private final String password = "";			// Contrase�a.
	private final String nombreBD = "LIBRERIA";	// Nombre de la Base de Datos.
	
	
	// Cadena de conexion
	private String datosConexion;				// Cadena que almacena los datos de
												// conexion.
	
	// Objetos necesarios para la conexi�n
	
	private Connection miConexion;				// Variable Connection
	private Statement miStatement;				// Variable Statement
	private ResultSet miResultSet;				// Variable ResultSet

	
	
	/*************************************************************************************
	 * CONSTRUCTORES																	 *
	 *************************************************************************************/
	
	public LibrosDAO() {
		
		// Con las propiedas de los datos de conexi�n se genera este String con toda la
		// informacion necesaria para la conexion.
		datosConexion = "jdbc:mysql://" + host + ":" + port + "/" + nombreBD;
				
	}
	
	/*************************************************************************************
	 * METODOS 																			 *
	 *************************************************************************************/
	
	/* Nombre: mensajeError()
	 * Argumentos: String
	 * Devuelve: void
	 * Descripci�n: Se detecta durante la realizaci�n del programa que las
	 * instrucciones de este m�todo se repiten considerablemente.
	 * Se crea este m�todo para simplificar y clarificar el c�digo. El String que
	 * se pasa como par�metro se muestra al usuario para informarle de que se
	 * ha producido un error.
	 */
	
	public String mensajeError(String str, SQLException e) {
		
		// Imprimimos el registro de error
		e.printStackTrace();
		
		// Se informa al usuario o se le solicita un dato.
		System.out.println();
		System.out.println(str);
		
		// Se devuelven los datos escritos por el usuario.
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
		
	}
	
	/*************************************************************************************
	 * METODOS SOBREESCRITOS INTERFAZ ItfzLibrosDao										 *
	 *************************************************************************************/
	
	/* Nombre: altaLibro()
	 * Argumentos: Libro
	 * Devuelve: Boolean
	 * Descripci�n: Este m�todo se encarga de gestionar todos los procesos necesarios
	 * para dar de alta un registro en la base de datos.
	 */

	@Override
	public boolean altaLibro(Libro libro) {
		
		// Intentamos la conexion con la base de datos e intentamos pasarle todos los datos
		// para crear un nuevo registro.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Obtenemos la columna ID de base de datos.
			ResultSet miResultSet = miStatement.executeQuery("SELECT ID FROM LIBROS");
			
			// Nos movemos al �ltimo registro de la columna ID.
			miResultSet.last();
			
			// Leemos y almacenamos el valor de ID del �ltimo registro.
			int ultimoID = miResultSet.getInt("ID");
			
			// Incrementamos ultimoID en 1 y lo asignamos al Atributo ID de nuestro objeto libro.
			libro.setID(++ultimoID);
			
			// Creamos la cadena que se va a pasar, gracias a la instancia de la clase Statement,
			// a la base de datos. Esta cadena recoge la instrucci�n SQL junto con todos los
			// datos de la instancia libro que se ha tenido que crear.
			String str = "INSERT INTO LIBROS VALUES (" +
					libro.getID() + ", " +
					"'" + libro.getTitulo()    	+ "'" + ", " +
					"'" + libro.getAutor()     	+ "'" + ", " +
					"'" + libro.getEditorial() 	+ "'" + ", " +
					"'" + libro.getIsbn() 	   	+ "'" + ", " +
					libro.getPublicacion()		+ ", " +
					libro.getPrecio() 			+ ", " +
					"'" + libro.getDescripcion()+ "'" + ");";
			
			// Se pasa la cadena str a la base de datos para que �sta cree el registro.
			miStatement.executeUpdate(str);
			
			// Llegados a este paso, como se han cumplido todos los paso necesarios para
			// el registro del libro, cerramos la conexion.
			miConexion.close();
			
			// Devolvemos true como se�al que todo ha ido bien
			return true;
			
		// Si ocurre un error en el proceso capturamos la Excepci�n 
			
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el registro
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR INTRODUCIR LOS DATOS EN LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha realizado el registro del libro en la base de datos devolvemos
			// false.
			return false;
		}	
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: eliminarLibro()
	 * Argumentos: Integer
	 * Devuelve: Boolean
	 * Descripci�n: Este m�todo se encarga de gestionar los procesos necesarios
	 * para eliminar de la base de datos un registro, con el id que coincida con el 
	 * Integer que se le pasa como par�metro al m�todo.
	 * En caso de que el Integer que se pasa por par�metro no coincida con el ID de ning�n
	 * registro en la base de datos se lanzar� la excepci�n LibroNoEncontradoException.
	 */

	@Override
	public boolean eliminarLibro(int id) throws LibroNoEncontradoException {
		// Intentamos la conexion con la base de datos e intentamos pasarle la
		// instrucci�n para eliminar el registro.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Comprobamos que el id pasado por el usuario existe en la base de datos.
			
			// Obtenemos el ResultSet de todos los registros de la columna ID de la
			// base de datos
			miResultSet = miStatement.executeQuery("SELECT ID FROM LIBROS");
			
			// Recorremos el ResultSet en busca de la coincidencia con el id
			// proporcionado por el usuario	
			while (miResultSet.next()) {
				// En caso afirmativo, salimos del bucle y continuamos con el
				// proceso
				if (miResultSet.getInt("ID") == id)
					break;
				// En caso negativo, si es el �ltimo registro, lanzamos la
				// excepci�n LibroNoEncontradoException()
				else if (miResultSet.isLast()) {
					throw new LibroNoEncontradoException();
				}
			}	
			// Creamos la cadena que se va a pasar a la base de datos con la instrucci�n
			// para eliminar el registro con el id indicado.
			String str = "DELETE FROM LIBROS WHERE ID = " + id + ";";
			
			// Se pasa la cadena str a la base de datos para que �sta elimine el registro.
			miStatement.executeUpdate(str);
			
			// Llegados a este paso, como se han cumplido todos los paso necesarios para
			//la eliminaci�n del registro, cerramos la conexion.
			miConexion.close();
			
			// Devolvemos true como se�al que todo ha ido bien
			return true;
			
		// Si ocurre un error en el proceso capturamos la Excepci�n
			
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el proceso
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR ELIMINAR EL REGISTRO DE LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha eliminado el registro del libro en la base de datos devolvemos
			// false.
			return false;
		}	
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarTodos()
	 * Argumentos: Ninguno
	 * Devuelve: List
	 * Descripci�n: Este m�todo se encarga de gestionar los procesos necesarios
	 * para recoger de la base de datos, todos los datos, de todos los registros.
	 */

	@Override
	public List<Libro> consultarTodos() {
		// Intentamos la conexion con la base de datos e intentamos pasarle la
		// instrucci�n para recibir los datos de todos los registros.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Creamos la cadena que se va a pasar a la base de datos con la instrucci�n
			// para recibir los datos de todos los registros.
			String str = "SELECT * FROM LIBROS;";
			
			// Se pasa la cadena str a la base de datos para que nos devuelva el ResultSet.
			ResultSet miResultSet = miStatement.executeQuery(str);
			
			// Creamos una colecci�n ArrayList
			List<Libro> misLibros = new ArrayList<Libro>();
			
			// Leemos el objeto ResultSet, vamos creando instancias de la clase Libro y
			// las a�adimos a nuestro ArrayList
			
			while(miResultSet.next()) {
				// Obtenemos todos los datos de cada registro.
				int id = miResultSet.getInt("ID");
				String autor = miResultSet.getString("AUTOR");
				String descripcion = miResultSet.getString("DESCRIPCION");
				String editorial = miResultSet.getString("EDITORIAL");
				String isbn = miResultSet.getString("ISBN");
				Double precio = miResultSet.getDouble("PRECIO");
				int publicacion = miResultSet.getInt("PUBLICACION");
				String titulo = miResultSet.getString("TITULO");

				// Creamos la instancia de la clase Libro
				Libro libro = new Libro(id, autor, descripcion, editorial, isbn, precio, publicacion, titulo);
				
				// A�adimos la instancia de la clase Libro a nuestro ArrayList
				misLibros.add(libro);				
			}
			
			// Llegados a este paso, como se han cumplido todos los paso necesarios para
			// la obtenci�n de los datos, cerramos la conexion.
			miConexion.close();
			
			// Cuando hemos terminado de recorrer el objeto ResultSet y tenemos completa
			// la colecci�n de libros, devolvemos la colecci�n.
			return misLibros;
		
		// Si ocurre un error en el proceso capturamos la Excepci�n
			
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el proceso
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR LEER LOS REGISTROS DE LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha podido leer la base de datos y el ArrayList estar� vac�o
			// devolvemos null
			return null;
		}	
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarISBN()
	 * Argumentos: String
	 * Devuelve: Libro
	 * Descripci�n: Este m�todo se encarga de gestionar los procesos necesarios
	 * para filtar de la base de datos, un registro con el isbn que coincida con el String 
	 * que se le pasa como par�metro al m�todo, y recoger sus datos.
	 * En caso de que el String no coincida con el ISBN de ning�n registro en la base de 
	 * datos se lanzar� la excepci�n LibroNoEncontradoException.
	 */

	@Override
	public Libro consultarISBN(String isbn) throws LibroNoEncontradoException {
		// Intentamos la conexion con la base de datos e intentamos pasarle la
		// instrucci�n para recibir los datos del registro con el isbn solicitado.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Comprobamos que el isbn pasado por el usuario existe en la base de datos.
			
			// Obtenemos el ResultSet de todos los registros de la columna ISBN de la
			// base de datos
			miResultSet = miStatement.executeQuery("SELECT ISBN FROM LIBROS");
			
			// Recorremos el ResultSet en busca de la coincidencia con el isbn
			// proporcionado por el usuario
			while (miResultSet.next()) {
				// En caso afirmativo, salimos del bucle y continuamos con el
				// proceso
				if (miResultSet.getString("ISBN").equals(isbn))
					break;
				// En caso negativo, si es el �ltimo registro, lanzamos la
				// excepci�n LibroNoEncontradoException()
				else if (miResultSet.isLast())
					throw new LibroNoEncontradoException();
			}
			
			// Creamos la cadena que se va a pasar a la base de datos con la instrucci�n
			// para recibir los datos del registro con el ISBN indicado.
			String str = "SELECT * FROM LIBROS WHERE ISBN = '" + isbn + "';";
			
			// Se pasa la cadena str a la base de datos para que nos devuelva el ResultSet.
			ResultSet miResultSet = miStatement.executeQuery(str);
			
			// Iniciamos el objeto ResultSet yendo al primer (y �nico) registro
			miResultSet.first();
			
			// Leemos el objeto ResultSet y guardamos los valores para crear la instancia
			// de la clase Libro
			int id = miResultSet.getInt("ID");
			String autor = miResultSet.getString("AUTOR");
			String descripcion = miResultSet.getString("DESCRIPCION");
			String editorial = miResultSet.getString("EDITORIAL");
			Double precio = miResultSet.getDouble("PRECIO");
			int publicacion = miResultSet.getInt("PUBLICACION");
			String titulo = miResultSet.getString("TITULO");

			// Creamos la instancia de la clase Libro
			Libro libro = new Libro(id, autor, descripcion, editorial, isbn, precio, publicacion, titulo);
			
			// Llegados a este paso, como se han cumplido todos los pasos necesarios para
			// la obtenci�n de los datos, cerramos la conexion.
			miConexion.close();
							
			// Devolvemos la instancia de la clase Libro creada.
			return libro;
		
		// Si ocurre un error en el proceso capturamos la Excepci�n
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el proceso
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR LEER LOS REGISTROS DE LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha podido leer la base de datos no se ha podido crear la
			// instancia de la clase Libro, devolvemos null
			return null;
		}	

	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: consultarTitulo()
	 * Argumentos: String
	 * Devuelve: List
	 * Descripci�n: Este m�todo se encarga de gestionar los procesos necesarios
	 * para filtar de la base de datos los registros que contengan en su t�tulo el String
	 * que se le pasa como par�metro al m�todo y recoger todos sus datos.
	 * En caso de que el String no forme parte del TITULO de ninguno de los registros 
	 * se lanzar� la excepci�n LibroNoEncontradoException.
	 */

	@Override
	public List<Libro> consultarTitulo(String titulo) throws LibroNoEncontradoException {
		// Intentamos la conexion con la base de datos e intentamos pasarle la
		// instrucci�n para recibir los datos de los registros que contengan en su
		// t�tulo el String titulo pasado como argumento.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Creamos la cadena que se va a pasar a la base de datos con la instrucci�n
			// para recibir los datos del registro que contengan el String titulo en su
			// campo TITULO.
			String str = "SELECT * FROM LIBROS WHERE TITULO LIKE '%" + titulo + "%';";
			
			// Se pasa la cadena str a la base de datos para que nos devuelva el ResultSet.
			ResultSet miResultSet = miStatement.executeQuery(str);
			
			// Comprobamos si el ResultSet est� vac�o
			if (!miResultSet.next()) throw new LibroNoEncontradoException();
			// Como hemos movido el puntero para comprobar si est� vac�o lo volvemos a colocar
			// antes del primer registro.
			else miResultSet.beforeFirst();
			
			// Creamos una colecci�n ArrayList
			List<Libro> misLibros = new ArrayList<Libro>();
			
			// Leemos el objeto ResultSet, vamos creando instancias de la clase Libro y
			// las a�adimos a nuestro ArrayList
			
			while(miResultSet.next()) {
				// Obtenemos todos los datos de cada registro.
				int id = miResultSet.getInt("ID");
				String autor = miResultSet.getString("AUTOR");
				String descripcion = miResultSet.getString("DESCRIPCION");
				String editorial = miResultSet.getString("EDITORIAL");
				String isbn = miResultSet.getString("ISBN");
				Double precio = miResultSet.getDouble("PRECIO");
				int publicacion = miResultSet.getInt("PUBLICACION");
				String tituloBD = miResultSet.getString("TITULO");

				// Creamos la instancia de la clase Libro
				Libro libro = new Libro(id, autor, descripcion, editorial, isbn, precio, publicacion, tituloBD);
				
				// A�adimos la instancia de la clase Libro a nuestro ArrayList
				misLibros.add(libro);		
			}
			
			// Llegados a este paso, como se han cumplido todos los pasos necesarios para
			// la obtenci�n de los datos, cerramos la conexion.
			miConexion.close();
			
			// Cuando hemos terminado de recorrer el objeto ResultSet y tenemos completa
			// la colecci�n de libros, devolvemos la colecci�n.
			return misLibros;
			
		// Si ocurre un error en el proceso capturamos la Excepci�n
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el proceso
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR LEER LOS REGISTROS DE LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha podido leer la base de datos y el ArrayList estar� vac�o
			// devolvemos null
			return null;
		}	
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	/* Nombre: modificarPrecio()
	 * Argumentos: String, Double
	 * Devuelve: Boolean
	 * Descripci�n: Este m�todo se encarga de gestionar los procesos necesarios
	 * para filtar de la base de datos el registro cuyo ISBN coincida con el String que
	 * se pasa como par�metro y modificar el precio que aparece en el registro
	 * por el Double que se pasa como par�metro.
	 * En caso de que el String no coincida con el ISBN de ning�n registro en la base de 
	 * datos se lanzar� la excepci�n LibroNoEncontradoException.
	 */

	@Override
	public boolean modificarPrecio(String isbn, double precio) throws LibroNoEncontradoException {
		// Intentamos la conexion con la base de datos e intentamos pasarle las
		// instrucciones para recibir los datos del registro con el isbn solicitado
		// y cambiarle el precio.
		try {
			
			// Realizamos la conexi�n
			miConexion = DriverManager.getConnection(datosConexion, user, password);
			
			// Creamos el objeto de la clase Statement con la ayuda del m�todo createStetement()
			// de la clase Connection.
			miStatement  = miConexion.createStatement();
			
			// Comprobamos que el isbn pasado por el usuario existe en la base de datos.
			
			// Obtenemos el ResultSet de todos los registros de la columna ISBN de la
			// base de datos
			miResultSet = miStatement.executeQuery("SELECT ISBN FROM LIBROS");
			
			// Recorremos el ResultSet en busca de la coincidencia con el isbn
			// proporcionado por el usuario
			while (miResultSet.next()) {
				// En caso afirmativo, salimos del bucle y continuamos con el
				// proceso
				if (miResultSet.getString("ISBN").equals(isbn))
					break;
				// En caso negativo, si es el �ltimo registro, lanzamos la
				// excepci�n LibroNoEncontradoException()
				else if (miResultSet.isLast())
					throw new LibroNoEncontradoException();
			}
			
			// Creamos la cadena que se va a pasar a la base de datos con la instrucci�n
			// para recibir los datos del registro con el ISBN indicado.
			String str = "UPDATE LIBROS SET PRECIO = " + precio + " WHERE ISBN = '" + isbn + "';";
			
			// Se pasa la cadena str a la base de datos para que realice el cambio.
			miStatement.executeUpdate(str);
			
			// Llegados a este paso, como se han cumplido todos los paso necesarios para
			// el cambio de precio del libro, cerramos la conexion.
			miConexion.close();
			
			// Devolvemos true como se�al que todo ha ido bien
			return true;
		
		// Si ocurre un error en el proceso capturamos la Excepci�n
		} catch (SQLException e) {
			
			// Informamos al usuario de que se ha producido un error durante el proceso
			mensajeError("\nSE HA PRODUCIDO UN ERROR AL INTENTAR MODIFICAR LOS DATOS EN LA BASE DE DATOS.\nPulse INTRO para CONTINUAR.", e);
			
			// Como no sabemos si la conexi�n ha podido quedar abierta la intentamos cerrar.
			try {
				miConexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// Como no se ha realizado el cambio del precio en la base de datos devolvemos
			// false.
			return false;
		}
	}
}
