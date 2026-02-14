package com.ic.LiterAluraChallengeIc.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibros(

       @JsonAlias("title")  String titulo,
       @JsonAlias("authors")  List<DatosAutores> autores ,
       @JsonAlias("languages")  List<String> lenguaje,
       @JsonAlias("download_count")  Double descargas


) {
    @Override
    public String toString() {
        return
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", lenguaje=" + lenguaje +
                ", descargas=" + descargas ;
    }
}
