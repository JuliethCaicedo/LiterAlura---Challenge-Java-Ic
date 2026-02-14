package com.ic.LiterAluraChallengeIc.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private Integer fechaNacimientoDelAutor;
    private Integer  fechaMuerteDelAutor;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autor(){}

    public Autor(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.fechaNacimientoDelAutor = datosAutores.fechaNacimientoDelAutor();
        this.fechaMuerteDelAutor = datosAutores.fechaMuerteDelAutor();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimientoDelAutor() {
        return fechaNacimientoDelAutor;
    }

    public void setFechaNacimientoDelAutor(Integer fechaNacimientoDelAutor) {
        this.fechaNacimientoDelAutor = fechaNacimientoDelAutor;
    }

    public Integer getFechaMuerteDelAutor() {
        return fechaMuerteDelAutor;
    }

    public void setFechaMuerteDelAutor(Integer fechaMuerteDelAutor) {
        this.fechaMuerteDelAutor = fechaMuerteDelAutor;
    }

    @Override
    public String toString() {

        if (libros == null || libros.isEmpty()) {
            return "--------Autor--------\n" +
                    "nombre = " + nombre + "\n" +
                    "Sin libros registrados";
        }

        String titulosLibros = libros.stream()
                .map(libros -> libros.getTitulo())
                .reduce( "" , (a,b) -> a +  "\n   - " + b);

        return "--------Autor--------" + '\n'+
                " nombre = " + nombre + '\n'+
                " fechaNacimientoDelAutor = " + fechaNacimientoDelAutor + '\n'+
                " fechaMuerteDelAutor = " + fechaMuerteDelAutor + '\n'+
                " libros = " + titulosLibros ;
    }
}
