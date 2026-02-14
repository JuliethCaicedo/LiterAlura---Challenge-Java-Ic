package com.ic.LiterAluraChallengeIc.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosAutores(

        @JsonAlias("name")  String nombre,
        @JsonAlias("birth_year") int fechaNacimientoDelAutor,
        @JsonAlias("death_year") int fechaMuerteDelAutor

) {
    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", fechaNacimientoDelAutor=" + fechaNacimientoDelAutor +
                ", fechaMuerteDelAutor=" + fechaMuerteDelAutor
                ;
    }
}
