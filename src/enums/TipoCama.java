
package enums;


public enum TipoCama {
    INDIVIDUAL(1),
    DOBLE(2),
    KING(2);
    
    private final int plazas;
    
    public int getCapacidad () {
        return plazas;
    }
    
    TipoCama (int capacidad) {
        this.plazas = capacidad;
    }
    
}
