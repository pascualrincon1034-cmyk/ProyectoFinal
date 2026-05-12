public abstract class Casilla {
    
    private boolean mostrar = false;
    private boolean bandera = false;

    /*
    El siguiente metodo revela una casilla siempre y cuando no tenga una bandera.
    */

    public void revelarCasilla(){
        if (bandera){
            throw new IllegalStateException(
                "Una casilla con bandera no se puede revelar");
        }

        mostrar = true;
    }

    /*
    El siguiente metodo coloca una bandera en una casilla
    Primero verifica que la casilla no se esté mostrando, en caso tal arroja un IllegalStateException
    */

    public void ponerBandera(){
        if (mostrar || bandera) {
            throw new IllegalStateException(
                "Una casilla que ya se revelo o que ya tenia una no se le puede colocar");
        }
        bandera = true;
    }
    
    /*
    Este metodo quita una bandera, verificando primero que no se este mostrando la casilla, 
    o que si no se esta mostrando, verifica que tenga una bandera
    */

    public void quitarBandera(){
        if (!bandera || mostrar) {
            throw new IllegalStateException(
                "No se le puede quitar una bandera a una casilla que no tiene");
        }
        bandera = false;
    }


    public boolean getMostrar(){
        return mostrar;
    }

    public boolean getBandera(){
        return bandera;
    }

}
