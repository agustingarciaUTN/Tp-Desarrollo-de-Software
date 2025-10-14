package Huesped;


import Dominio.Huesped;

public class GestorHuesped {
    //debe presentarse en pantalla la opcion para ejecutar el metodo de buscar huesped
    // solo si se autentico antes el conserje
    private final DaoHuespedInterfaz dao;
    public GestorHuesped(DaoHuespedInterfaz dao) {
        this.dao = dao;
    }

    public boolean buscarHuesped(){
        // presentar en pantalla los inputs necesarios de
        //apellido.
        //▪ Nombres.
        //▪ Tipo de documento: [DNI, LE, LC,
        //Pasaporte, Otro]
        //▪ Número de documento.
        //puede no poner ninguno, como algunos. Manejar esto.
        // Presiona buscar (hacer boton o algo por el estilo)

        //dao busca con los criterios pasados los huespedes, devuelve dtos?
        //ver si existe concordancia, si no pasar al cu11 y finalizar este cu.
        //si encuentra seguir
        // presentar los datos de los dto encontrados en pantalla de la manera correcta
        // manera: Esta lista contiene como columnas los datos mencionados en el paso 2.
        //Seleccionar una persona de alguna manera
        //Presiona siguiente.
        //Chequear si no selecciono nadie y apreto siguiente, ir al cu11.
        //si apreto bien ir al cu10. y terminar.
    }
}
