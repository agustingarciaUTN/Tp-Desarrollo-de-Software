
package Habitacion;

import enums.TipoCama;

public class DtoCama {
    
    private TipoCama tipoCama;
    
    public DtoCama (){
        //Constructor por defecto
    }
    
    public DtoCama (TipoCama tipoCama) {
        this.tipoCama = tipoCama;
    }
    
    public TipoCama getTipoCama () {
        return tipoCama;
    }
    public void setTipoCama (TipoCama tipoCama) {
        this.tipoCama = tipoCama;
    }
    
}
