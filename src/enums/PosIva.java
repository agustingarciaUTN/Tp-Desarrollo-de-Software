package enums;

public enum PosIva {
    ConsumidorFinal("Consumidor Final"),
    Monotributista("Monotributista"),
    ResponsableInscripto("Responsable inscripto"),
    Excento("Excento");

    private final String displayName;

    PosIva(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PosIva fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return ConsumidorFinal;
        }

        // Primero intentamos con el nombre exacto
        for (PosIva pos : PosIva.values()) {
            if (pos.displayName.equalsIgnoreCase(text.trim())) {
                return pos;
            }
        }

        // Si no funciona, intentamos sin espacios
        String normalized = text.replace(" ", "").toUpperCase();
        try {
            return valueOf(normalized);
        } catch (IllegalArgumentException e) {
            System.err.println("Valor de posicion_iva no reconocido: " + text + ". Usando valor por defecto.");
            return ConsumidorFinal;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}