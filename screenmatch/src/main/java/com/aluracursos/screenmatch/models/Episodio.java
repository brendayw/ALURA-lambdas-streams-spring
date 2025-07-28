package com.aluracursos.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double rate;
    private LocalDate fechaLanzamiento;

    public Episodio(Integer temporada, DatosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.title();
        this.numeroEpisodio = d.numeroEpisodio();
        try {
            this.rate = Double.valueOf(d.rate());
        } catch (NumberFormatException e) {
            this.rate = 0.0;
        }
        try {
            this.fechaLanzamiento = LocalDate.parse(d.fechaLanzamiento());
        } catch (DateTimeParseException e) {
            this.fechaLanzamiento = null;
        }
    }

    //getters y setters
    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", rate=" + rate +
                ", fechaLanzamiento=" + fechaLanzamiento;
    }
}
