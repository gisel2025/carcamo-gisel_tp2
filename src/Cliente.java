// Cliente: la persona que pide los envíos.
// Guarda sus datos y la lista de envíos que hizo.

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int id;
    private String nombre;
    private String contacto;
    private List<Envio> misEnvios;

    public Cliente(int id, String nombre, String contacto) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.misEnvios = new ArrayList<>();
    }

    public Cliente(int id, String nombre) {
        this(id, nombre, "");
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public List<Envio> getMisEnvios() {
        return misEnvios;
    }

    // Solo lo llama Envio cuando se crea, para anotarse en la lista del cliente.
    void agregarEnvio(Envio envio) {
        misEnvios.add(envio);
    }

    public int cantidadDeEnvios() {
        return misEnvios.size();
    }

    @Override
    public String toString() {
        return "Cliente #" + id + " - " + nombre;
    }
}
