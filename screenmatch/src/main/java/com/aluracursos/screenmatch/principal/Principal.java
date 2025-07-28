package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.models.DatosEpisodio;
import com.aluracursos.screenmatch.models.DatosSerie;
import com.aluracursos.screenmatch.models.DatosTemporada;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=379dea3";

    public void mostrarMenu() {
        System.out.println("Escribi el nombre de la serie a buscar: ");
        var serie = input.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + serie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.cantidadTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + serie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);

        //mostrar solo el titulo de los episodios
//        for (int i = 0; i < datos.cantidadTemporadas(); i++) {
//            List<DatosEpisodio> listaEpisodios = temporadas.get(i).episodios();
//            for (int j = 0; j < listaEpisodios.size(); j++) {
//                System.out.println(listaEpisodios.get(j).title());
//            }
//        }

        //usando lambda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println("Titulo del episodio: " + e.title() + ", Numero de episodio: " + e.numeroEpisodio())));

        //convertir a lista de datosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //top 5 episodios
//        System.out.println("Top 5 de episodios: ");
//        datosEpisodios.stream()
//                .filter(e -> !e.rate().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DatosEpisodio::rate).reversed())
//                .peek(e -> System.out.println("Segundo filtro ordena de M - m " + e))
//                .map(e -> e.title().toUpperCase())
//                .peek(e -> System.out.println("Tercer filtro todo a mayuscula " + e))
//                .limit(5)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        //episodios.forEach(System.out::println);

        //buscar episodios a partir de cierto año
//        System.out.println("Indica el año a partir del cual deseas ver los episodios: ");
//        var fecha = input.nextInt();
//        input.nextLine();

//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Epidosido: " + e.getTitulo() +
//                                " Fecha de Lanzamiento: " + e.getFechaLanzamiento().format(dtf)
//                ));

        //bsuqueda
//        System.out.println("Ingrese el titulo del episodio que desea ver: ");
//        var titulo = input.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(titulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episodio encontrado!");
//            System.out.println(episodioBuscado.get()); //trae todos los datos
//        } else {
//            System.out.println("Ups, episodio no encontrado!");
//        }

        Map<Integer, Double> ratePorTemporada = episodios.stream()
                .filter(e -> e.getRate() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getRate)));
        System.out.println(ratePorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getRate() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getRate));
        System.out.println("Media de las evaluaciones: " + est.getAverage());
        System.out.println("Episodio mejor evaluado: " +est.getMax());
        System.out.println("Episodio peor evaluado: " +est.getMin());
    }
}
