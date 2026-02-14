package com.ic.LiterAluraChallengeIc.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")

public class Libros {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_lenguajes", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "lenguaje")
    private List<String> lenguaje;
    private Double descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libros (){}

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.lenguaje = datosLibros.lenguaje();
        this.descargas = datosLibros.descargas();
        if (!datosLibros.autores().isEmpty()){
            this.autor = new Autor(datosLibros.autores().get(0));
        }

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "------Libros---------" +'\n'+
                " Titulo = " + titulo + '\n' +
                " Autor = " + (autor != null ? autor.getNombre() : "Sin autor") +'\n' +
                " Idioma = " + lenguaje + '\n' +
                " Descargas = " + descargas + '\n' +
                "___________________________";

    }
}
