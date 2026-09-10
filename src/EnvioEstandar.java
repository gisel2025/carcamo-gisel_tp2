// Envío estándar: el más básico. Un costo fijo más un extra por cada paquete.

public class EnvioEstandar extends Envio {

    private static final double COSTO_FIJO = 500.0;
    private static final double COSTO_POR_PAQUETE = 100.0;

    public EnvioEstandar(int id, Cliente cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularCosto() {
        return COSTO_FIJO + (getPaquetes().size() * COSTO_POR_PAQUETE);
    }
}
