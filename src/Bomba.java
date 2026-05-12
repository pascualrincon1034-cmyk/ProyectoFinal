public class Bomba extends Casilla{
    
    /*
    Devuelve el String equivalente a la casilla. Las convenciones usadas se especifican en el archivo diseno.md
    */

    public String toString(){
        
        if (this.getMostrar()){
            return "*";

        } else if (this.getBandera()){
            return "#";

        } else {
            return " ";

        }
    }
}
