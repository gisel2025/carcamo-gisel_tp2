// Movimiento: un "hecho" que le pasó a un envío en su recorrido.
// Ejemplo: "recibido en Sucursal Viedma", "despachado", "entregado".
// Guardamos fecha, sucursal (puede no haber, si es la entrega final)
// y qué tipo de evento fue.

import java.time.LocalDateTime;

public class Movimiento {

    private LocalDateTime fecha;
    private Sucursal sucursal; // puede quedar null si es la entrega final
    private String tipoDeEvento; // "RECIBIDO", "DESPACHADO" o "ENTREGADO"

    public Movimiento(Sucursal sucursal, String tipoDeEvento) {
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

    public String getTipoDeEvento() {
        return tipoDeEvento;
    }

    @Override
    public String toString() {
        String lugar = (sucursal != null) ? sucursal.getNombre() : "destino final";
        return "[" + fecha + "] " + tipoDeEvento + " en " + lugar;
    }
}
