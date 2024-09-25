package arquitectura.grupo19.dto;

import javax.persistence.Column;

public class CarreraDTO {
    private int id;
    private String nombre;
    private int duracion;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }
}
