
package Habitacion;

import enums.TipoHabitacion;
import enums.EstadoHabitacion;

public class DtoHabitacion {
    
    private String numero;
    private TipoHabitacion tipoHabitacion;
    private int capacidad;
    private EstadoHabitacion estadoHabitacion;
    private float costoPorNoche;
    
    public DtoHabitacion (){
        //Constructor por defecto
    }
    
    public DtoHabitacion (String numero, TipoHabitacion tipoHabitacion, int capacidad, EstadoHabitacion estadoHabitacion, float costoPorNoche) {
        this.numero = numero;
        this.tipoHabitacion = tipoHabitacion;
        this.capacidad = capacidad;
        this.estadoHabitacion = estadoHabitacion;
        this.costoPorNoche = costoPorNoche;
    }
   
    public String getNumero () {
        return numero;
    }
    public void setNumero (String numero) {
        this.numero = numero;
    }
    public TipoHabitacion getTipoHabitacion () {
        return tipoHabitacion;
    }
    public void setTipoHabitacion (TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    public int getCapacidad () {
        return capacidad;
    }
    public void setCapacidad (int capacidad) {
        this.capacidad = capacidad;
    }
    public EstadoHabitacion getEstadoHabitacion () {
        return estadoHabitacion;
    }
    public void setEstadoHabitacion (EstadoHabitacion estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }
    public float getCostoPorNoche () {
        return costoPorNoche;
    }
    public void setCostoPorNoche (float costoPorNoche) {
        this.costoPorNoche = costoPorNoche;
    }
}
