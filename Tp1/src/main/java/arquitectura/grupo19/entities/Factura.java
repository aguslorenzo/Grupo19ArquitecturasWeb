package arquitectura.grupo19.entities;

public class Factura {
    private int id;
    private int id_cliente;

    public Factura(int id, int id_cliente) {
        this.id = id;
        this.id_cliente = id_cliente;
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
