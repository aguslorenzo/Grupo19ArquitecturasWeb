package arquitectura.grupo19.utils.constantes;


import java.util.Arrays;

public enum Genero {
    MALE, FEMALE, POLYGENDER, NON_BINARY, MASCULINO, GENDERFLUID, FEMENINO, BIGENDER, AGENDER;

    public static boolean esGeneroValido(String genero) {
        return Arrays.stream(values()).anyMatch(g -> g.name().equalsIgnoreCase(genero));
    }
}

/*
  values(): Devuelve todos los valores del enum

  Arrays.stream(values()): convierte el Array devuelto por values()
       en un Stream de objetos Genero. Un Stream permite realizar
       operaciones funcionales sobre colecciones (como filtrado,
       mapeo, búsqueda, etc.).

  anyMatch(g -> g.name().equalsIgnoreCase(genero)):
       - anyMatch: operación de Stream que verifica si algún elemento
       del Stream cumple con la condición
       - g -> g.name().equalsIgnoreCase(genero): expresión lambda que
       representa la condición para cada elemento g en el Stream.

  anyMatch(...): Devolverá true si al menos un elemento en el Stream
  cumple la condición (g.name().equalsIgnoreCase(genero)).
 */
