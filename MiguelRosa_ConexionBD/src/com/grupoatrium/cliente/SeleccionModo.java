package com.grupoatrium.cliente;

import java.util.Scanner;

import com.grupoatrium.negocio.GestionLibreria;
import com.grupoatrium.negociografico.EntornoGrafico;

/*****************************************************************************************
 * Nombre de Clase : SeleccionModo														 *
 * Autor: Miguel Rosa																	 *
 * Fecha: 07 - Noviembre - 2016															 *
 * Versión: 1.0																			 *
 * Descripción: Se escriben unas breves instrucciones por consola para que el			 *
 * 				usuario seleccione 1) modo consola o 2) modo gráfico. La				 *
 * 				aplicación permanece a la espera hasta que el usuario seleccione		 *
 * 				una u otra, o la instrucción para salir del programa. Si la opción		 *
 * 				introducida por el usuario no corresponde a ninguna de las				 *
 * 				anteriores se repetirá el mismo mensaje de selección hasta que la		 *
 * 				opción introducida coincida.											 *
 * 				En función de la selección se lanzará el entorno gráfico, se			 *
 * 				permanecerá en modo consola o se saldrá de la aplicación.				 *
 *****************************************************************************************/

public class SeleccionModo {
	
	/*************************************************************************************
	 * ATRIBUTOS																		 *
	 *************************************************************************************/
	
	private static boolean modoGrafico;
	
	// Atributo que almacenará la selección del usuario.
	private String seleccion; 	
	
	/*************************************************************************************
	 * CONSTRUCTORES																	 *
	 *************************************************************************************/
	
	public SeleccionModo() {
		
		// Impresión en consola de unas breves instrucciones.
		System.out.println("Seleccione el modo en el que desea ejecutar la aplicación:\n");
		System.out.println("\t1 Modo CONSOLA (por defecto)");
		System.out.println("\t2 Modo GRÁFICO");
		System.out.println();
		System.out.println("\t3 SALIR");
		System.out.println();
		System.out.println("Puede seleccionar el número o escribir la palabra clave.");
		
		// Se crea una instancia de la Scanner para recoger la opción del usuario
		Scanner sc = new Scanner(System.in);
		
		// Se asigna la opción del usuario al atributo "seleccion". El String devuelto
		// por el usuario se convierte a minúsculas antes de almacenarlo para que la
		// comprobación sea independiente de las mayúsculas o minúsculas.
		seleccion = sc.nextLine().toLowerCase();
		
		// Se invoca al método comprobar().
		try {
			comprobar();
		} catch (OpcionIncorrectaException e) {
			new SeleccionModo();
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
	 * 		· "1", "consola" ó ""  ->  Se continúa en modo consola.
	 * 		· "2", "gráfico" ó "grafico" -> Se lanza el modo gráfico.
	 * 		· "3" ó "salir" -> Se sale de la aplicación.
	 * 		· En caso de que el valor introducido no coincida con alguno de los
	 * 		anteriores se vuelve a imprimir el menu de inicio (se lanza nuevamente
	 * 		la aplicación desde el principio).
	 */
	
	public void comprobar() throws OpcionIncorrectaException {
		
		// Opción 1. Modo Consola
		
		if (seleccion.equals("1") || (seleccion.equals("consola")) || (seleccion.equals(""))) {
			modoGrafico = false;
			System.out.println();
			System.out.println("SE CONTINUARÁ EN MODO CONSOLA. Pulse INTRO para continuar.");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			new GestionLibreria();
		}
		
		// Opción 2. Modo Gráfico
		
		else if (seleccion.equals("2") || (seleccion.equals("grafico")) || (seleccion.equals("gráfico"))) {
			modoGrafico = true;
			System.out.println();
			System.out.println("SE CONTINUARÁ EN MODO GRÁFICO. Pulse INTRO para continuar.");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			new EntornoGrafico();
		}
		
		// Opción 3. Salir
		
		else if (seleccion.equals("3") || (seleccion.equals("salir"))) {
			System.out.println();
			System.out.println("SALIENDO DE LA APLICACIÓN...");
			System.exit(0);
		}
		
		// Se lanza la excepción OpcionIncorrecta cuando la opción introducida por el
		// usuario no corresponde con ninguna de las anteriores
		
		else throw new OpcionIncorrectaException();
	}
	
	/* Nombre: getModoGrafico()
	 * Argumentos: Ninguno
	 * Devuelve: boolean
	 * Descripción: 
	 * Devuelve el valor de atributo modoGrafico
	 */
	
	public static boolean getModoGrafico() {
		return modoGrafico;
	}
	
}
