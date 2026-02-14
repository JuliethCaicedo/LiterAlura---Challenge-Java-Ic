package com.ic.LiterAluraChallengeIc.repository;

import com.ic.LiterAluraChallengeIc.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("""
       SELECT a FROM Autor a
       WHERE a.fechaNacimientoDelAutor <= :fecha
       AND (a.fechaMuerteDelAutor IS NULL OR a.fechaMuerteDelAutor >= :fecha)
       """)
    List<Autor> autoresVivosEnAnio(@Param("fecha") Integer fecha);

    List<Autor> findByNombreIgnoreCase(String nombre);


}

