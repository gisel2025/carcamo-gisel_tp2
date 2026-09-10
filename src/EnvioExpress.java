// Envío express: más caro, porque es más rápido. Tiene un recargo extra.

public class EnvioExpress extends Envio {

    private static final double COSTO_FIJO = 900.0;
    private static final double COSTO_POR_PAQUETE = 150.0;
    private static final double RECARGO_POR_SER_EXPRESS = 1.5; // 50% más caro

    public EnvioExpress(int id, Cliente cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularCosto() {
        double costoBase = COSTO_FIJO + (getPaquetes().size() * COSTO_POR_PAQUETE);
        return costoBase * RECARGO_POR_SER_EXPRESS;
    }
}
