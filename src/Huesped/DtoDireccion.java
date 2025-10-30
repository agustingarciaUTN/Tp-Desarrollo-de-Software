package Huesped;

public class DtoDireccion {
    private int ID;
    private String calle;
    private Integer numero;
    private String departamento;
    private Integer piso;
    private Integer codPostal;
    private String localidad;
    private String provincia;
    private String pais;

    // Constructor con todos los datos
    public DtoDireccion(String calle, Integer numero, String departamento, Integer piso, Integer codPostal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.codPostal = codPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y Setters
    public void setId(int ID){this.ID = ID;}
    public int getId(){return this.ID;}
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public Integer getPiso() {
        return piso;
    }
    public void setPiso(Integer piso) {
        this.piso = piso;
    }
    public Integer getCodPostal() {
        return codPostal;
    }
    public void setCodPostal(Integer codPostal) {
        this.codPostal = codPostal;
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
