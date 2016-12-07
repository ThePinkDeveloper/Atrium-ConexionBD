package com.grupoatrium.modelo;

public class Libro {
	
	/*************************************************************************************
	 * ATRIBUTOS																		 *
	 *************************************************************************************/
	
	private static int contadorID;	// Variable que lleva el conteo de IDs en la Base
									// de Datos
	
	private String autor; 			// Nombre del autor
	private String descripcion; 	// Sinopsis del Libro
	private String editorial;		// Editorial que lo ha publicado
	private int ID;					// identificador interno
	private String isbn;			// Código ISBN
	private double precio;			// Precio en euros
	private int publicacion;		// Año de publicación
	private String titulo;			// Título del libro
	
	/*************************************************************************************
	 * CONSTRUCTORES																	 *
	 *************************************************************************************/
	
	public Libro( String autor, String descripcion, String editorial, String isbn,
				  double precio, int publicacion, String titulo) {
		
		this.autor = autor;
		this.descripcion = descripcion;
		this.editorial = editorial;
		this.isbn = isbn;
		this.precio = precio;
		this.publicacion = publicacion;
		this.titulo = titulo;		
	}
	
	public Libro( int id, String autor, String descripcion, String editorial, String isbn,
			  double precio, int publicacion, String titulo) {
	
		this.ID = id;
		this.autor = autor;
		this.descripcion = descripcion;
		this.editorial = editorial;
		this.isbn = isbn;
		this.precio = precio;
		this.publicacion = publicacion;
		this.titulo = titulo;		
	}
	
	/*************************************************************************************
	 * METODOS																			 *
	 *************************************************************************************/
	
	
	
	//GETTERS
	
	public String getAutor() {
		return autor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getEditorial() {
		return editorial;
	}
	public int getID() {
		return ID;
	}
	public String getIsbn() {
		return isbn;
	}
	public double getPrecio() {
		return precio;
	}
	public int getPublicacion() {
		return publicacion;
	}
	public String getTitulo() {
		return titulo;
	}
	
	//SETTERS
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setPublicacion(int publicacion) {
		this.publicacion = publicacion;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
