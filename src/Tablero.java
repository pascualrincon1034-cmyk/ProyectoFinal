import java.util.Random;

public class Tablero {
    private Casilla[][] tablero;
    private int columnas;
    private int filas;
    private int minas;
    private int minas_restantes;
    private int banderas;


    /*
    El constructor de tablero verifica que la cantidad de minas sea menor o igual que la cantidad de casillas - 9.
    Ademas, por motivos practicos, el tamaño minimo de una tablero sera de 4x4

    Crea un tablero de filas x columnas, con casillas, y lo llena de Casillas Normales.
    */

    public Tablero(int filas, int columnas, int minas){
        if (minas > filas*columnas - 9) {
            throw new IllegalArgumentException("Numero de minas debe ser menor a la cantidad de casillas menos 9");
        }
        if (filas < 4 || columnas < 4){
            throw new IllegalArgumentException("El tamaño del tablero debe ser de minimo 4x4");
        }

        this.columnas = columnas;
        this.filas = filas;
        this.minas = minas;

        tablero = new Casilla[filas][columnas];
        
        for (Casilla[] fila: tablero){
           
            for (int j = 0; j < fila.length; j++){
                Casilla p = new Normal();
                fila[j] = p;
            }
        }

        minas_restantes = minas;
        banderas = minas;
    }

    public Casilla[][] getTablero(){
            return tablero;
        }

    public int getBanderas(){
        return banderas;
    }

    public int getMinas_restantes(){
        return minas_restantes;
    }

    public int getMinas(){
        return minas;
    }

    public String getDimension(){
        return Integer.toString(filas) + "x" + Integer.toString(columnas);
    } 


    /*
    El siguiente metodo imprime una representacion del tablero de la partida, donde la primera fila y columna de la impresion
    del tablero representan las coordenadas de cada una de las casillas. Se formatea la salida para mantener el orden de las 
    columnas y las filas
    */

    public void printTablero(){

        System.out.println();
        System.out.print("  ");

        for (int i = 0; i <= columnas; i++){
            System.out.printf("%-3s", Integer.toString(i));
        }

        System.out.print("\n    ");

        for (int i = 1; i <= columnas; i++){
            System.out.print("---");
        }

        System.out.println();

        int rows = 1;
        
        for (Casilla[] fila: tablero){
            System.out.printf("%-2s", Integer.toString(rows));
            System.out.print(" | ");

            rows++;
        
            for (Casilla c: fila){
                System.out.printf("%-3s", c.toString());
            }
        
            System.out.println();
        }
    }


    /*
    El siguiente metodo llena el tablero con la respectiva cantidad de bombas. Recibe dos parametros que corresponden a las
    coordenadas del cuadrado con el cual se inicializa el juego. Se garantiza que el la casilla correspondiente a los argumentos 
    sea normal y no este tocando ninguna mina
    */

    public void ponerBombas(int fila, int columna){
        Random rand = new Random();

        if (!this.validCoord(columna - 1, fila - 1)){
            throw new IllegalArgumentException("Coordenadas invalidas");
        }


        for (int restantes = minas; restantes > 0;){
            int x = rand.nextInt(columnas) + 1, y = rand.nextInt(filas) + 1;
            //el random genera coordenadas con (x,y) con x >= 1, y >= 1 
            
            boolean flag = false;
            // booleano para determinar si alguna de las casillas adyacentes fue generada por el rand

            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (columna - 1 + i == x && fila - 1 + j == y){
                        flag = true;
                    }
                }
            }
            if (flag) continue;
            
            if (this.ponerBomba(x - 1, y - 1)){
                // Se resta 1 a cada coordenada para el mapeo entre el sistema de 
                // coordenadas que ve el usario y el mapeo que hace la matriz
                restantes--;
            }
        
        }
    }

    /*
    Metodo de ayuda para el metodo poner bomba.
    Si en la casilla no habia bomba, se cambia la casilla por una bomba, y se retorna true.
    Si ya habia una bomba, no se inserta y se retorna false. Ademas, suma 1 al atributo bombas de 
    las Casillas normales adyacentes.
    */
    
    public boolean ponerBomba(int x, int y){
        
        if (tablero[y][x] instanceof Bomba){
            return false;
        }
        
        this.normalABomba(x, y);
        this.sumarBombaAdyacentes(x,y);
        return true;

    }

    /*
    Metodo de ayuda para el metodo poner bomba. Este se encarga de sumar 1 al atributo bombas de las casillas normales
    adyacentes.
    */

    public void sumarBombaAdyacentes(int x, int y){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (!this.validCoord(x - 1 + i, y - 1 + j)) continue;
                
                if (tablero[y - 1 + j][x - 1 + i] instanceof Normal){
                    ((Normal) tablero[y - 1 + j][x - 1 + i]).sumarBomba();
                }
            }
        }
    }

    public void normalABomba(int x, int y){
        tablero[y][x] = new Bomba();
    }

    /*
    Metodo para validar que las coordenadas ingresadas sean validas. Observese que aqui se evalua con respecto a los indices del
    atributo tablero. 
    */

    public boolean validCoord(int x, int y){
        if (x < 0 || x >= columnas || y < 0 || y >= filas){
            return false;
        }
        return true;
    }

    /* 
    Algoritmo cascada:
    1. El llamado inicial se hace sobre una ficha vacia sin revelar
    2. Se revela la casilla sobre la que se hizo el llamado
    3. Si la ficha actual esta vacia, hace el llamado sombre las adyacentes.

    El metodo se apoya de validCoord evitar indexOutOfBound
    El metodo recibe por parametros las coordenadas de la ficha vacia segun los indices del tablero
    */

    public void recorridoCascada(int x, int y){
        tablero[y][x].revelarCasilla();
        if (( (Normal) tablero[y][x]).getBomba() > 0) return;

        for (int i = 0; i < 3; i++){
            int x1 = x - 1 + i;
            for (int j = 0; j < 3; j++){
                int y1 = y - 1 + j;
                if (!validCoord(x1, y1)) continue;
                if (tablero[y1][x1].getMostrar()) continue;
                recorridoCascada(x1, y1);
            }
        }
    }
    
    /*
    El siguiente metodo permite quitar o poner banderas. Al poner banderas, si la casilla es una mina, entonces
    minas-restantes se le resta 1. En cualquier caso se le resta 1 a banderas.
    Al quitar banderas, si la casilla que tenia bandera es una mina, se le suma 1 a minas_restantes. En cualquier caso se
    suma 1 a banderas.

    Se retorna un booleano, indicando si se pudo realizar el cambio o no.
    Las coordenadas corresponden a las del atributo tablero
    */

    public boolean ponerQuitarBandera(int x, int y){
        if (!validCoord(x, y)){
            throw new IllegalArgumentException("Casilla invalida");
        }

        Casilla c = tablero[y][x];
        if (c.getMostrar()){
            return false;
        }

        if (c.getBandera()){
            c.quitarBandera();
            banderas++;
            if (c instanceof Bomba){
                minas_restantes++;
            }
        } else {
            c.ponerBandera();
            banderas--;
            if (c instanceof Bomba){
                minas_restantes--;
            }
        }
        return true;
    }

    /*
    El siguiente metodo revela una casilla y retorna:
    0 si la ficha es una bomba
    1 si tiene bandera
    2 si la ficha es normal y esta vacia
    3 si es normal y no vacia
    Esto para la implementacion de playBuscaminas
     
    Se verifican las coordenadas, las cuales corresponden a los indices del tablero, usando validCoord.
    */

    public int revelarCasilla(int x, int y){
        if (!validCoord(x, y)){
            throw new IllegalArgumentException("Casilla invalida");
        }
        
        Casilla c = tablero[y][x];
        if (c.getBandera() || c.getMostrar()){
            return 1;
        }
        if (c instanceof Bomba){
            return 0;
        }
        if (((Normal) c).getBomba() == 0){
            c.revelarCasilla();
            return 2;
        } else {
            c.revelarCasilla();
            return 3;
        }
    }

    /*
    El siguiente metodo se usará cuando el jugador pierda. Si en una casilla hay una bomba sin bandera, se revelara.
    Si en una casilla hay una bomba con bandera, se dejará así. Si en una casilla hay una bandera pero no una mina, 
    se imprime una X;
    */
    
    public void revelarTablero(){
        for (Casilla[] fila: tablero){
            for (Casilla c: fila){
                if (c instanceof Bomba && !c.getBandera()){
                    c.revelarCasilla();

                } else if (c instanceof Normal && c.getBandera()){
                    ((Normal) c).setIncorrecto();

                }
            }
        }
    }

    /*
    Metodo invocado al ganar el juego, el cual garantiza que todas las casillas que no tengan bandera sean reveladas
    */

    public void revelarTodo(){
        for (Casilla[] fila: tablero){
            for (Casilla c: fila){
                if (c.getBandera()) continue;
                c.revelarCasilla();
            }
        }
    }
}
