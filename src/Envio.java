// Envio: representa el "viaje" de 1 a 3 paquetes, desde que se pide
// hasta que se entrega. Es la clase que más responsabilidad tiene:
// coordina a sus paquetes y guarda su propio historial de movimientos.
//
// Es "abstract" porque el costo se calcula distinto según el tipo de
// envío (Estándar, Express, Internacional). Cada subclase completa
// el método calcularCosto() a su manera. Esto evita tener que escribir
// un montón de "if (tipo.equals(...))".

import java.util.ArrayList;
import java.util.List;

public abstract class Envio {

    // Los estados posibles de un envío completo.
    public enum Estado {
        REGISTRADO,     // recién creado, todavía no salió de ningún lado
        EN_PREPARACION, // ya está en la primera sucursal
        EN_VIAJE,       // ya fue despachado al menos una vez
        ENTREGADO       // llegó a destino
    }

    private static final int MAXIMO_DE_PAQUETES = 3;

    private int id;
    private Cliente cliente;
    private List<Paquete> paquetes;
    private List<Movimiento> historial;
    private Estado estado;
    private Sucursal sucursalDondeEstoyAhora; // null = estoy viajando entre sucursales

    protected Envio(int id, Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Todo envío necesita un cliente");
        }
        this.id = id;
        this.cliente = cliente;
        this.paquetes = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.estado = Estado.REGISTRADO;

        // Apenas se crea el envío, se anota solo en la lista del cliente.
        // Así nunca puede existir un envío "perdido" sin cliente asociado.
        cliente.agregarEnvio(this);
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public List<Movimiento> getHistorial() {
        return historial;
    }

    // Agregar un paquete al envío. Solo se puede antes de despacharlo
    // y hasta un máximo de 3.
    public void agregarPaquete(Paquete paquete) {
        boolean hayLugar = paquetes.size() < MAXIMO_DE_PAQUETES;
        boolean todaviaNoSalio = (estado == Estado.REGISTRADO);

        if (!hayLugar || !todaviaNoSalio) {
            throw new IllegalStateException(
                "No se puede agregar el paquete al envío " + id +
                " (máximo " + MAXIMO_DE_PAQUETES + " paquetes, y solo antes de despacharlo)");
        }
        paquetes.add(paquete);
    }

    // --------------------------------------------------------------
    // Estos dos métodos los llama Sucursal. No son "public" porque
    // solo tiene sentido que los use una Sucursal, no cualquier objeto.
    // --------------------------------------------------------------

    void serRecibidoEn(Sucursal sucursal) {
        if (estado == Estado.ENTREGADO) {
            throw new IllegalStateException("El envío " + id + " ya fue entregado");
        }
        if (paquetes.isEmpty()) {
            throw new IllegalStateException("El envío " + id + " no tiene paquetes cargados");
        }
        if (sucursalDondeEstoyAhora != null) {
            throw new IllegalStateException(
                "El envío " + id + " ya está en " + sucursalDondeEstoyAhora.getNombre() +
                ", primero hay que despacharlo");
        }

        sucursalDondeEstoyAhora = sucursal;

        // La primera vez que llega a una sucursal, pasa a "en preparación"
        // y sus paquetes también.
        if (estado == Estado.REGISTRADO) {
            estado = Estado.EN_PREPARACION;
            for (Paquete p : paquetes) {
                p.pasarAPreparacion();
            }
        }

        historial.add(new Movimiento(sucursal, "RECIBIDO"));
    }

    void serDespachadoDesde(Sucursal sucursal) {
        boolean puedeDespacharse = (estado == Estado.EN_PREPARACION || estado == Estado.EN_VIAJE);
        if (!puedeDespacharse) {
            throw new IllegalStateException(
                "El envío " + id + " no se puede despachar, su estado es " + estado);
        }
        if (sucursalDondeEstoyAhora != sucursal) {
            throw new IllegalStateException(
                "El envío " + id + " no está en " + sucursal.getNombre());
        }

        // Los paquetes pasan a "en viaje" solo la primera vez que se
        // despachan. Si ya estaban en viaje (segunda o tercera escala),
        // no hace falta repetirlo.
        if (estado == Estado.EN_PREPARACION) {
            for (Paquete p : paquetes) {
                p.pasarAEnViaje();
            }
        }
        estado = Estado.EN_VIAJE;

        historial.add(new Movimiento(sucursal, "DESPACHADO"));
        sucursalDondeEstoyAhora = null; // ahora está "en el camino"
    }

    // Entrega final: no pasa en una sucursal, sino en el destino del cliente.
    public void entregar() {
        if (estado != Estado.EN_VIAJE) {
            throw new IllegalStateException(
                "El envío " + id + " no se puede entregar, su estado es " + estado);
        }
        for (Paquete p : paquetes) {
            p.pasarAEntregado();
        }
        estado = Estado.ENTREGADO;
        historial.add(new Movimiento(null, "ENTREGADO"));
    }

    // --------------------------------------------------------------
    // Consultas: en vez de que otra clase recorra el historial "a mano",
    // se le pregunta directamente al envío.
    // --------------------------------------------------------------

    public void mostrarHistorial() {
        System.out.println("Historial del envío " + id + ":");
        for (Movimiento m : historial) {
            System.out.println("  " + m);
        }
    }

    public List<String> sucursalesPorLasQuePaso() {
        List<String> nombres = new ArrayList<>();
        for (Movimiento m : historial) {
            if (m.getSucursal() != null && m.getTipoDeEvento().equals("RECIBIDO")) {
                nombres.add(m.getSucursal().getNombre());
            }
        }
        return nombres;
    }

    public Movimiento ultimoMovimiento() {
        if (historial.isEmpty()) {
            return null;
        }
        return historial.get(historial.size() - 1);
    }

    // Cada subclase (Estándar, Express, Internacional) decide cómo
    // calcula su propio costo.
    public abstract double calcularCosto();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " #" + id +
               " - Cliente: " + cliente.getNombre() +
               " - Estado: " + estado +
               " - Paquetes: " + paquetes.size();
    }
}
