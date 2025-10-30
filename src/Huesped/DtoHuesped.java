package Huesped;

import enums.TipoDocumento;
import enums.PosIva;
import java.util.Date;

public class DtoHuesped {
    private String nombres;
    private String apellido;
    private long telefono;
    private TipoDocumento tipoDocumento;
    private long documento;
    private String cuit;
    private PosIva posicionIva;
    private Date fechaNacimiento;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private DtoDireccion dtoDireccion;
    private int idDireccion;

    // Constructor con todos los datos
    public DtoHuesped(String nombres, String apellido, long telefono, TipoDocumento tipoDocumento, long documento, String cuit, PosIva posicionIva, Date fechaNacimiento, String email, String ocupacion, String nacionalidad) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.cuit = cuit;
        this.posicionIva = posicionIva;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
    }
    
    public DtoHuesped (){
        this.posicionIva = PosIva.ConsumidorFinal; // Establecemos el valor por defecto
    }

    // Getters y Setters
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public long getDocumento() {
        return documento;
    }
    public void setDocumento(long documento) {
        this.documento = documento;
    }
    public String getCuit() {
        return cuit;
    }
    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
    public PosIva getPosicionIva() {
        return posicionIva;
    }
    public void setPosicionIva(PosIva posicionIva) {
        this.posicionIva = posicionIva;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public void setDireccion(DtoDireccion dtoDireccion){ this.dtoDireccion = dtoDireccion; }
    public DtoDireccion getDireccion(){ return this.dtoDireccion; }
    public int getIdDireccion() {
        return idDireccion;
    }
    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    public boolean estanVacios() {
        boolean apellidoVacio = (apellido == null || apellido.trim().isEmpty());
        boolean nombresVacio = (nombres == null || nombres.trim().isEmpty());
        boolean tipoDocVacio = (tipoDocumento == null);
        boolean docVacio = (documento <= 0);
        return apellidoVacio && nombresVacio && tipoDocVacio && docVacio;
    }
    
    // Método auxiliar para convertir string de la BD a PosIva
    public static PosIva convertirPosIvaString(String posicionIvaStr) {
        if (posicionIvaStr == null) {
            return PosIva.ConsumidorFinal;
        }
        try {
            // Eliminamos espacios y convertimos a mayúsculas
            String posIvaSinEspacios = posicionIvaStr.replace(" ", "").toUpperCase();
            return PosIva.valueOf(posIvaSinEspacios);
        } catch (IllegalArgumentException e) {
            System.err.println("Valor de posicion_iva no válido: " + posicionIvaStr);
            return PosIva.ConsumidorFinal;
        }
    }
}