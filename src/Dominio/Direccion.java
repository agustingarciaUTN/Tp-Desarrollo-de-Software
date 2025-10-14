package Dominio;

public class Direccion {
    private String calle;
    private int numero;
    private String departamento;
    private int piso;
    private int cod_postal;
    private String localidad;
    private String provincia;
    private String pais;

    // Constructor default
    public Direccion() {
    }

    // Constructor con todos los datos
    public Direccion(String calle, int numero, String departamento, int piso, int cod_postal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.cod_postal = cod_postal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Constructor con datos principales
    public Direccion(String calle, int numero, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y Setters
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public int getPiso() {
        return piso;
    }
    public void setPiso(int piso) {
        this.piso = piso;
    }
    public int getCod_postal() {
        return cod_postal;
    }
    public void setCod_postal(int cod_postal) {
        this.cod_postal = cod_postal;
    }
    public String getLocalidad() {
        return localidad;
    }
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
}
