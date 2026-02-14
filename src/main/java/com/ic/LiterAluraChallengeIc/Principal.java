package com.ic.LiterAluraChallengeIc;

import com.ic.LiterAluraChallengeIc.model.Autor;
import com.ic.LiterAluraChallengeIc.model.DatosAutores;
import com.ic.LiterAluraChallengeIc.model.DatosLibros;
import com.ic.LiterAluraChallengeIc.model.Libros;
import com.ic.LiterAluraChallengeIc.repository.AutorRepository;
import com.ic.LiterAluraChallengeIc.repository.LibroRepository;
import com.ic.LiterAluraChallengeIc.service.ConsumoApi;
import com.ic.LiterAluraChallengeIc.service.ConvierteLibrosDesdeJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteLibrosDesdeJson conversor = new ConvierteLibrosDesdeJson();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private List<Libros> libros;
    private List<Autor> autores;
    private List<Libros> librosPorIdioma;

    private final AutorRepository autorRepository;
    private final LibroRepository repository;
    public Principal(LibroRepository repository,
                     AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

    public void bienvenidoLiterAlura(){
        var opcion =-1;

        while (opcion != 0) {
            var menu = """
                -------------------
                
                Elija la opción a través de su número:
                
                1- Buscar libro por titulo.
                2- Listar libros registrados.
                3- Listar autores registrados.
                4- Listar autores vivios  en un determinado año.
                5- Listar libros por idioma.
                0- Salir.
                
                -----------------""";
            System.out.println(menu);

            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (InputMismatchException e ){
                System.out.println("Debe ingresar un número válido.");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2 :
                    listarLibrosRegistrados();
                    break;
                case 3 :
                    listarAutoresRegistrados();
                    break;
                case 4 :
                    autoresEnUnaDeterminadaFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Gracias por Utilizar los Servicios");
                    break;

                default:
                    System.out.println("Ingrese una Opcion correcta");
            }
        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el Idioma del que desea asignar el libro
                es - español
                en - ingles
                fs - frances
                """);
        var lenguaje = teclado.nextLine().trim().toLowerCase();;
        librosPorIdioma = repository.buscarPorLenguaje(lenguaje);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
            return;
        }

        librosPorIdioma.forEach(System.out::println);

    }

    private void autoresEnUnaDeterminadaFecha() {
        System.out.println("Ingrese el año del que quiere buscar el autor");
        var fecha = teclado.nextInt();

        autores = autorRepository.autoresVivosEnAnio(fecha);

        if (autores.isEmpty()){
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }
        autores.forEach(System.out::println);
    }


    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay Autores registrados.");
            return;
        }

        autores.forEach(System.out::println);


    }

    private void listarLibrosRegistrados() {

        libros = repository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(System.out::println);

    }

    private DatosLibros getDatosLibros(){
        System.out.println("Ingrese el fragmento del libro que desee buscar");
        var parteDelLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + parteDelLibro.replace(" ", "+"));
        var datosLibroPorNombre = conversor.obtenerDatos(json);
        Optional<DatosLibros> librosBuscados = datosLibroPorNombre.datoslibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(parteDelLibro.toUpperCase()))
                .findFirst();
         if (librosBuscados.isPresent()){
            return  librosBuscados.get();
        }else {
            System.out.println("Libro no encontrado");
            return null;        }

    }

    private void buscarLibroPorTitulo() {
        DatosLibros datos = getDatosLibros();

        if (datos == null) {
            return;
        }

        Optional<Libros> librosOptional = repository.findByTituloIgnoreCase(datos.titulo());

        if (librosOptional.isPresent()){
            System.out.println(" El libro ya está registrado...");
            return;
        }

        DatosAutores datosAutor = datos.autores().get(0);
        List<Autor> autorExistente =
                autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

        Autor autor;

        if (!autorExistente.isEmpty()) {
            autor = autorExistente.get(0); // toma el primero
        } else {
            autor = new Autor(datosAutor);
            autor = autorRepository.save(autor);
        }

        Libros libros = new Libros(datos);
        libros.setAutor(autor);

        Libros libroGuardado = repository.save(libros);

        System.out.println(libroGuardado);

    }

}
