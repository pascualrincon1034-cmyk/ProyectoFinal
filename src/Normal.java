public class Normal extends Casilla {

    //El atributo sirve para indicar que una casilla Normal tiene bandera
    //Es necesario para poder imprimir una X en las casillas como en el juego original.
    private boolean incorrecto = false;
   
    private int bombas = 0;

    public int getBomba(){
        return bombas;
    }

    public boolean getIncorrecto(){
        return incorrecto;
    }

    /*
    Metodo para cambiar el estado de incorrecto a true. Se usa en el metodo de tablero revelarTablero.
    */

    public void setIncorrecto(){
        incorrecto = true;
    }

    /*
    Devuelve el String equivalente a la casilla. Las convenciones usadas se especifican en el archivo diseno.md
    */

    public String toString(){
        if (getMostrar()){
            return Integer.toString(bombas);

        } else if (incorrecto){
            return "X";

        } else if (getBandera()){
            return "#";

        } else {
            return " ";

        }
    }

    /*
    El siguiente metodo verifica que la cantidad de bombas adyacentes no exceda 8, y en caso tal suma 1.
    */

    public void sumarBomba(){
        if (bombas == 8){
            throw new IllegalStateException("Numero bombas por casilla excedido");
        }
        bombas++;
    }
}
