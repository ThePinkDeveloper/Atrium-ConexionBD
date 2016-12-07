package com.grupoatrium.negociografico;

import javax.swing.JFrame;

/*****************************************************************************
 * Nombre de Clase : Main 
 * Autor: Miguel Rosa 
 * Fecha: 12 - Noviembre - 2016 
 * Versión: 1.0 
 * Descripción: 
 * Esta clase se encarga de la creación de la ventana (JFrame) que contendrá
 * todos los elementos gráficos
 *****************************************************************************/

public class EntornoGrafico {

	/*************************************************************************
	 * CONSTRUCTORES
	 *************************************************************************/

	public EntornoGrafico() {
		// Creamos la ventana con el título "Gestión Librería"
		JFrame miVentana = new JFrame("Gestión Librería");

		// Cuando se cierre la ventana el programa terminará.
		miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Añadimos una instancia del JPanel LaminaPrincipal a nuestro JFrame
		miVentana.add(new LaminaPrincipal());

		// Hacemos que los bordes de nuestra ventana se ajusten a los del JFrame
		// que
		// añadimos en el paso anterior
		miVentana.pack();

		// Hacemos que nuestra ventana aparezca en el centro de la pantalla
		miVentana.setLocationRelativeTo(null);

		// Impedimos que los bordes de la ventana se puedan redimensionar
		miVentana.setResizable(false);

		// Hacemos la ventana visible
		miVentana.setVisible(true);

	}

}
