package com.example.findBookApi.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
