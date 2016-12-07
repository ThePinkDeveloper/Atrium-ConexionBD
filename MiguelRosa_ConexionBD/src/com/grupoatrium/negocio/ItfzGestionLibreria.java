package com.grupoatrium.negocio;

import java.util.List;

import com.grupoatrium.modelo.Libro;

public interface ItfzGestionLibreria {
	
	public boolean altaLibro(Libro libro);
	public boolean eliminarLibro(int id);
	public List<Libro> consultarTodos();
	public Libro consultarISBN(String isbn);
	public List<Libro> consultarTitulo(String titulo);
	public boolean modificarPrecio(String isbn, double precio);

}
