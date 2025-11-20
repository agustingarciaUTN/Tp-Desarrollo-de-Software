
package enums;

import java.text.Normalizer;

public enum TipoHabitacion {
    IndividualEstandar("Individual Estándar"),
    DobleEstandar("Doble Estándar"),
    DobleSuperior("Doble Superior"),
    SuperiorFamilyPlan("Superior Family Plan"),
    SuiteDoble("Suite Doble");
    
    private final String descripcion;

    TipoHabitacion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    // Método robusto para convertir String -> Enum
    public static TipoHabitacion fromString(String s) {
        if (s == null) return null;
        String norm = normalize(s);
        for (TipoHabitacion t : values()) {
            if (norm.equals(normalize(t.name())) || norm.equals(normalize(t.descripcion))) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo inválido: " + s);
    }

    private static String normalize(String input) {
        if (input == null) return "";
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\s_\\-]+", "")
                .toLowerCase();
    }
    
}
