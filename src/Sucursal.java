// Sucursal: un punto físico por donde pasa el envío en su recorrido.
// La sucursal NO decide cómo cambia el estado del envío: solo le avisa
// "te recibí" o "te despacho", y es el propio Envio el que se encarga
// de todo lo demás (cambiar su estado, avisarle a sus paquetes, etc).

import java.util.ArrayList;
import java.util.List;

public class Sucursal {

    private int id;
    private String nombre;
    private String ciudad;
    private List<Envio> enviosQueTengoAhora;

    public Sucursal(int id, String nombre, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.enviosQueTengoAhora = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public List<Envio> getEnviosQueTengoAhora() {
        return enviosQueTengoAhora;
    }

    public void recibirEnvio(Envio envio) {
        envio.serRecibidoEn(this); // le pido al envío que se encargue
        if (!enviosQueTengoAhora.contains(envio)) {
            enviosQueTengoAhora.add(envio);
        }
    }

    public void despacharEnvio(Envio envio) {
        envio.serDespachadoDesde(this); // idem
        enviosQueTengoAhora.remove(envio);
    }

    @Override
    public String toString() {
        return "Sucursal #" + id + " - " + nombre + " (" + ciudad + ")";
    }
}
