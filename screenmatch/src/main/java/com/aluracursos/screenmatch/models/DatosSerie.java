package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") Integer cantidadTemporadas,
        @JsonAlias("imdbRating") String rate) {
}
