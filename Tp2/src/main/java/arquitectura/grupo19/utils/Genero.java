package arquitectura.grupo19.utils;

public enum Genero {
    MASCULINO("M"),
    FEMENINO("F"),
    OTRO("X");

    private String codigo;

    Genero(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Genero fromCodigo(String codigo) {
        for (Genero genero : Genero.values()) {
            if (genero.getCodigo().equalsIgnoreCase(codigo)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Género no válido: " + codigo);
    }
}