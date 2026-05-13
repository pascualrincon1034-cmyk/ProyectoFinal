public class Partida{
    
    private int dificultad;
    private String dimensiones; //Filas x Columnas
    private int minas;
    private long tiempo;
    private int minas_encontradas;
    private boolean victoria;

    public Partida(int dificultad, String dimensiones, int minas, long tiempo, int minas_encontradas){
        this.dificultad = dificultad;   
        this.dimensiones = dimensiones;
        this.minas = minas;
        this.tiempo = tiempo;
        this.minas_encontradas = minas_encontradas;

        if (minas_encontradas == minas){
            victoria = true;
        } else {
            victoria = false;
        }
    }

    public boolean getVictoria(){
        return victoria;
    }

    public int getDificultad(){
        return dificultad;
    }

    public long getTiempo(){
        return tiempo;
    }

    public int getMinasEnontradas(){
        return minas_encontradas;
    }


    /*
    Para la implementacion del bubbleSort por dificultad y minas encontradas
    1. Las partidas se organizan primero de 0 a 3 segun las dificultades definidas.
    2. Despues se comparan la cantidad de minas encontradas, organizando de mayor a menor
    */


    public boolean minasEncontradasCmp(Partida that){
        if (this.dificultad == that.dificultad){
        return this.minas_encontradas > that.minas_encontradas;
        } else {
            return this.dificultad < that.dificultad;
        }
    }


    /*
    Para la implementacion del bubbleSort por dificultad y minas encontradas
    1. Las partidas se organizan primero de 0 a 3 segun las dificultades definidas.
    2. Despues se comparan los tiempos.
    */

    public boolean tiempoCmp(Partida that){
        if (this.dificultad == that.dificultad){
        return this.tiempo < that.tiempo;
        } else {
            return this.dificultad < that.dificultad;
        }
    }


    public void informacionPartida(){
        System.out.println();
        System.out.println("Dificultad: " + Buscaminas.DIFICULTADES[dificultad]);
        System.out.println("Dimensiones: " + dimensiones);
        System.out.println("Minas: " + minas);
        System.out.println("Minas encontradas: " + minas_encontradas);
        System.out.printf("Tiempo: %d minutos y %d segundos\n", tiempo/60, tiempo%60);
        System.out.println("Partida " + (victoria ? "ganada!" : "perdida :("));
        

    }
}
