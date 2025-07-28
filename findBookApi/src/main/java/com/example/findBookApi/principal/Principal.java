package com.example.findBookApi.principal;

import com.example.findBookApi.models.Datos;
import com.example.findBookApi.models.DatosLibros;
import com.example.findBookApi.service.ConsumoApi;
import com.example.findBookApi.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    public void mostrarMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //top 10 más descargados
        System.out.println("---------------------------------------------");
        System.out.println("Top 10 libros más descargados: ");
        datos.resultado().stream()
                        .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                        .limit(10)
                        .map(l -> l.titulo().toLowerCase())
                        .forEach(System.out::println);


        //buscar libro por nombre
        System.out.println("---------------------------------------------");
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = input.nextLine();
        json = consumoApi.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ", "+"));
        var libroBuscado = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> buscado = libroBuscado.resultado().stream()
                .filter(l -> l.titulo().toLowerCase().contains(tituloLibro.toLowerCase()))
                .findFirst();
       if (buscado.isPresent()) {
           System.out.println("Libro encontrado!");
           System.out.println(buscado.get());
       } else {
           System.out.println("Libro no encontrado :(");
       }

        //estadisticas
        System.out.println("---------------------------------------------");
        System.out.println("Estadisticas: ");
        DoubleSummaryStatistics est = datos.resultado().stream()
                .filter(d -> d.numeroDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("Cantidad media de descargar: " + est.getAverage());
        System.out.println("Cantidad maxima de descargar: " + est.getMax());
        System.out.println("Cantidad minima de descargar: " + est.getMin());


    }
}
