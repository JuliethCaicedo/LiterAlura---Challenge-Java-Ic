package com.ic.LiterAluraChallengeIc.repository;

import com.ic.LiterAluraChallengeIc.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros , Long> {

    @Query("""
       SELECT l FROM Libros l
       WHERE :lenguaje MEMBER OF l.lenguaje
       """)
    List<Libros> buscarPorLenguaje(@Param("lenguaje") String lenguaje);

    Optional<Libros> findByTituloIgnoreCase(String titulo);


}
