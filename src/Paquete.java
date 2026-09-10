// Paquete: representa una caja/objeto que se transporta.
// Guarda sus propios datos y controla su propio estado.
// Nadie de afuera puede "forzar" el estado con un setter:
// el paquete tiene un método por cada acción real que le puede pasar.

public class Paquete {

    // Los posibles estados por los que pasa un paquete, en orden.
    public enum Estado {
        RECIBIDO,
        EN_PREPARACION,
        EN_VIAJE,
        ENTREGADO
    }

    private int id;
    private String descripcion;
    private double peso;
    private Estado estado;

    public Paquete(int id, String descripcion, double peso) {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.estado = Estado.RECIBIDO; // todo paquete nace "recibido"
    }

    // Constructor más simple, sin peso (queda en 0).
    public Paquete(int id, String descripcion) {
        this(id, descripcion, 0.0);
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPeso() {
        return peso;
    }

    public Estado getEstado() {
        return estado;
    }

    // El paquete pasa a "en preparación".
    public void pasarAPreparacion() {
        if (estado != Estado.RECIBIDO) {
            throw new IllegalStateException(
                "El paquete " + id + " no puede pasar a preparación, su estado es " + estado);
        }
        estado = Estado.EN_PREPARACION;
    }

    // El paquete arranca su viaje.
    public void pasarAEnViaje() {
        boolean puedeViajar = (estado == Estado.EN_PREPARACION || estado == Estado.RECIBIDO);
        if (!puedeViajar) {
            throw new IllegalStateException(
                "El paquete " + id + " no puede iniciar viaje, su estado es " + estado);
        }
        estado = Estado.EN_VIAJE;
    }

    // El paquete llega a destino.
    public void pasarAEntregado() {
        if (estado != Estado.EN_VIAJE) {
            throw new IllegalStateException(
                "El paquete " + id + " no puede entregarse, su estado es " + estado);
        }
        estado = Estado.ENTREGADO;
    }

    @Override
    public String toString() {
        return "Paquete #" + id + " (" + descripcion + ") - " + estado;
    }
}
