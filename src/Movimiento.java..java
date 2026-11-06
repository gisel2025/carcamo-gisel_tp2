// Movimiento: un "hecho" que le pasó a un envío en su recorrido.
// Ejemplo: "recibido en Sucursal Viedma", "despachado", "entregado".
// Guardamos fecha, sucursal (puede no haber, si es la entrega final)
// y qué tipo de evento fue.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // MEJORA DE USABILIDAD: la fecha se muestra como la lee una persona
    // (día/mes/año hora:minutos) y el evento con palabras comunes.
    private static final DateTimeFormatter FORMATO_FECHA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String eventoEnPalabras() {
        switch (tipoDeEvento) {
            case RECIBIDO:   return "Recibido en";
            case DESPACHADO: return "Despachado desde";
            default:         return "Entregado al cliente";
        }
    }

    @Override
    public String toString() {
        String texto = fecha.format(FORMATO_FECHA) + " - " + eventoEnPalabras();
        if (sucursal != null) {
            texto += " " + sucursal.getNombre();
        }
        return texto;
    }
}
