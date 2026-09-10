// Envío internacional: el más caro, porque suma un costo fijo de aduana.

public class EnvioInternacional extends Envio {

    private static final double COSTO_FIJO = 2000.0;
    private static final double COSTO_POR_PAQUETE = 300.0;
    private static final double COSTO_DE_ADUANA = 800.0;

    public EnvioInternacional(int id, Cliente cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularCosto() {
        return COSTO_FIJO + (getPaquetes().size() * COSTO_POR_PAQUETE) + COSTO_DE_ADUANA;
    }
}
