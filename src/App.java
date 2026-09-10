// App: acá se crean los objetos y se prueba que todo funcione.
// Esta clase no decide NADA sobre el negocio: solo pide cosas
// (crea objetos y manda mensajes). Toda la lógica real vive en
// las otras clases.

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        // 1) Creo los clientes
        Cliente juan = new Cliente(1, "Juan Perez", "juan@mail.com");
        Cliente maria = new Cliente(2, "Maria Gomez");

        // 2) Creo las sucursales
        Sucursal viedma = new Sucursal(1, "Sucursal Viedma", "Viedma");
        Sucursal bahiaBlanca = new Sucursal(2, "Sucursal Bahia Blanca", "Bahia Blanca");
        Sucursal buenosAires = new Sucursal(3, "Sucursal Buenos Aires", "Buenos Aires");

        // 3) Creo los envíos (cada uno de un tipo distinto)
        Envio envio1 = new EnvioEstandar(1, juan);
        envio1.agregarPaquete(new Paquete(1, "Notebook", 2.5));
        envio1.agregarPaquete(new Paquete(2, "Libros", 3.0));

        Envio envio2 = new EnvioExpress(2, maria);
        envio2.agregarPaquete(new Paquete(3, "Documentos", 0.5));

        List<Envio> todosLosEnvios = new ArrayList<>();
        todosLosEnvios.add(envio1);
        todosLosEnvios.add(envio2);

        // 4) Recorrido del envío 1: Viedma -> Bahia Blanca -> Buenos Aires
        viedma.recibirEnvio(envio1);
        viedma.despacharEnvio(envio1);

        bahiaBlanca.recibirEnvio(envio1);
        bahiaBlanca.despacharEnvio(envio1);

        buenosAires.recibirEnvio(envio1);
        envio1.entregar();

        // 5) Recorrido del envío 2: Viedma -> Buenos Aires
        viedma.recibirEnvio(envio2);
        viedma.despacharEnvio(envio2);
        buenosAires.recibirEnvio(envio2);
        envio2.entregar();

        // 6) Consultas sobre el historial
        System.out.println("=== Historial del envío 1 ===");
        envio1.mostrarHistorial();

        System.out.println("\n=== Por qué sucursales pasó el envío 1 ===");
        System.out.println(envio1.sucursalesPorLasQuePaso());

        System.out.println("\n=== Ultimo movimiento del envío 2 ===");
        System.out.println(envio2.ultimoMovimiento());

        // 7) Información general de todos los envíos
        System.out.println("\n=== Resumen de todos los envíos ===");
        double costoTotal = 0;
        for (Envio e : todosLosEnvios) {
            System.out.println(e + " -> Costo: $" + e.calcularCosto());
            costoTotal += e.calcularCosto();
        }
        System.out.println("Cantidad de envíos registrados: " + todosLosEnvios.size());
        System.out.println("Costo total de todos los envíos: $" + costoTotal);
        System.out.println("Envíos de " + juan.getNombre() + ": " + juan.cantidadDeEnvios());

        // 8) Pruebas de que las validaciones funcionan (operaciones inválidas)
        System.out.println("\n=== Probando que el sistema rechace operaciones inválidas ===");

        try {
            envio1.entregar(); // ya se había entregado antes
        } catch (IllegalStateException error) {
            System.out.println("Error esperado (entregar dos veces): " + error.getMessage());
        }

        try {
            Envio envioSinPaquetes = new EnvioEstandar(3, juan);
            viedma.recibirEnvio(envioSinPaquetes); // no tiene paquetes
        } catch (IllegalStateException error) {
            System.out.println("Error esperado (envío sin paquetes): " + error.getMessage());
        }

        try {
            Envio envioNuevo = new EnvioEstandar(4, maria);
            envioNuevo.agregarPaquete(new Paquete(4, "Caja"));
            envioNuevo.entregar(); // intento entregar sin haberlo despachado
        } catch (IllegalStateException error) {
            System.out.println("Error esperado (entregar sin despachar): " + error.getMessage());
        }

        try {
            Envio otroEnvio = new EnvioEstandar(5, juan);
            otroEnvio.agregarPaquete(new Paquete(5, "Caja 2"));
            viedma.recibirEnvio(otroEnvio);
            bahiaBlanca.recibirEnvio(otroEnvio); // recibir en otra sucursal sin despachar antes
        } catch (IllegalStateException error) {
            System.out.println("Error esperado (recibir sin despachar): " + error.getMessage());
        }
    }
}
