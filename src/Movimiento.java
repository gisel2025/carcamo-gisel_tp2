// Movimiento: un "hecho" que le pasó a un envío en su recorrido.
// Ejemplo: "recibido en Sucursal Viedma", "despachado", "entregado".
// Guardamos fecha, sucursal (puede no haber, si es la entrega final)
// y qué tipo de evento fue.

import java.time.LocalDateTime;

public class Movimiento {

    // Los tipos de evento posibles. Con un enum en vez de un String,
    // no se puede escribir mal el evento ("RECIBIOD") ni inventar uno
    // que no existe: el compilador lo controla.
    public enum TipoDeEvento {
        RECIBIDO,
        DESPACHADO,
        ENTREGADO
    }

    private LocalDateTime fecha;
    private Sucursal sucursal; // puede quedar null si es la entrega final
    private TipoDeEvento tipoDeEvento;

    public Movimiento(Sucursal sucursal, TipoDeEvento tipoDeEvento) {
        this.fecha = LocalDateTime.now();
        this.sucursal = sucursal;
        this.tipoDeEvento = tipoDeEvento;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public TipoDeEvento getTipoDeEvento() {
        return tipoDeEvento;
    }

    @Override
    public String toString() {
        String lugar = (sucursal != null) ? sucursal.getNombre() : "destino final";
        return "[" + fecha + "] " + tipoDeEvento + " en " + lugar;
    }
}
