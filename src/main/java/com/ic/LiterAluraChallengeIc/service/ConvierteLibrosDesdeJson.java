package com.ic.LiterAluraChallengeIc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ic.LiterAluraChallengeIc.model.Datos;

public class ConvierteLibrosDesdeJson {

    private final ObjectMapper mapper = new ObjectMapper();

    public Datos obtenerDatos (String json){
        try {
            return mapper.readValue(json, Datos.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
