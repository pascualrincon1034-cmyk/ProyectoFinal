import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;


public class Buscaminas{
    
    public static Scanner in = new Scanner(System.in);
    public static final String[] DIFICULTADES = {
        "Personalizado", "Princiante", "Intermedio", "Experto"};
    
    private Tablero t;
    private int dificultad;
    private long tiempoPartida;
    private boolean victoria;

    public boolean getVictoria(){
        return victoria;
    }

    public Tablero getTablero(){
        return t;
    }

    public int getDificultad(){
        return dificultad;
    }

    public long getTiempoPartida(){
        return tiempoPartida;
    }

    public Buscaminas(){
        System.out.println("Bienvenido a buscaminas! Selecciona una dificultad:");
        
        for (int i = 0; i < 4; i++){
            System.out.printf("%d: %s.   ", i, DIFICULTADES[i]);
        }
        System.out.println();
        System.out.print("Ingrese un numero: ");
        int n = in.nextInt();
        while (n > 3 || n < 0){
            System.out.print("Ups! Ingresa una dificultad valida: ");
            n = in.nextInt();
        }

        System.out.println();
        dificultad = n;
        int columnas, filas, minas;
        
        switch (n){

            case 0:
                System.out.print("Seleccione una cantidad de columnas: ");
                columnas = in.nextInt();
                while (columnas < 4){
                    System.out.println("El numero de columnas debe ser positivo y mayor que 3");
                    columnas = in.nextInt();
                }

                System.out.print("Seleccione una cantidad de filas: ");
                filas = in.nextInt();
                while (filas < 4){
                    System.out.println("El numero de filas debe ser positivo y mayor que 3");
                    filas = in.nextInt();
                }

                System.out.print("Seleccione una cantidad de minas: ");
                minas = in.nextInt();
                while (minas <= 0 || minas > columnas*filas - 9){
                    System.out.println("El numero de minas debe ser positivo,"
                    + " y menor a la cantidad de casillas - 9");
                    minas = in.nextInt();
                }

                t = new Tablero(filas, columnas, minas);
                break;
            
            case 1:
                filas = 9; columnas = 9; minas = 10;
                t = new Tablero(filas, columnas, minas);
                break;

            case 2:
                filas = 16; columnas = 16; minas = 40;
                t = new Tablero(filas, columnas, minas);
                break;

            case 3:
                filas = 16; columnas = 30; minas = 99;
                t = new Tablero(filas, columnas, minas);
                break;

        }
    }

    public void playBuscaminas(){

        String[] acciones = {"Quitar/Poner bandera", "Revelar casilla"};

        Instant start = Instant.now();
        inicializarTablero();
        t.printTablero();

        int status = 0;
        do {
            System.out.println();
            
            for (int i = 1; i <= 2; i++){
                System.out.printf("%d: %s    ", i, acciones[i-1]);
            }
            System.out.println();

            int accion;
            int x0 = 0, y0 = 0;
            do{
                System.out.println();
                System.out.print("Seleccione una accion: ");
                accion = in.nextInt();
            } while (!(accion == 1 || accion == 2));

            System.out.println();

            if (accion == 1){

                do{
                    System.out.println("Seleccione una casilla valida!");
                    
                    System.out.print("Fila: ");
                    y0 = in.nextInt();
                    
                    System.out.print("Columna: ");
                    x0 = in.nextInt();
                
                } while (x0 != -1 && !t.validCoord(x0 - 1, y0 - 1));

                if (x0 == -1){
                    status = 1;
                    continue;
                }

                boolean flag = t.ponerQuitarBandera(x0 - 1, y0 - 1);
                
                if (!flag ){
                    status = 1;
                } else {
                    status = 3;
                }
            
            }

            if (accion == 2){

                do{
                    System.out.println("Seleccione una casilla valida!");
                    
                    System.out.print("Fila: ");
                    y0 = in.nextInt();
                    
                    System.out.print("Columna: ");
                    x0 = in.nextInt();
                
                } while (x0 != -1 && !t.validCoord(x0 - 1, y0 - 1));

                if (x0 == -1){
                    status = 1;
                    continue;
                }

                status = t.revelarCasilla(x0 - 1, y0 - 1);
            }

            if (status == 1){
                System.out.println("La casilla no se puede revelar o ya fue revelada");
                t.printTablero();

            } else if (status == 2){
                t.recorridoCascada(x0 - 1, y0 - 1);
                t.printTablero();

            } else if (status == 3){
                t.printTablero();
            }


        } while(status != 0 && (t.getMinas_restantes() != 0 || t.getMinas_restantes() != t.getBanderas()));
        
        if (status == 0){
            t.revelarTablero();
            t.printTablero();
            System.out.println("\nPerdiste");
            victoria = false;
        } else {
            t.revelarTodo();
            t.printTablero();
            System.out.println("\nGanaste!!!");
            victoria = true;
        }

        Instant end = Instant.now();
        
        tiempoPartida = Duration.between(start, end).getSeconds();

        informacionPartida();

        return;


    }

    /*
    El siguiente metodo imprime en consola la informacion de la partida.
    */

    public void informacionPartida(){
        System.out.println("Minas encontradas: " + (t.getMinas() - t.getMinas_restantes()));
        System.out.println("Tiempo transcurrido: " + tiempoPartida);
    }

    /*
    El siguiente metodo permite inicializar el tablero a partir de una casilla seleccionada por el jugador
    Invoca los metodos ponerBombas y recorridoCascada
    */

    public void inicializarTablero(){
        
        t.printTablero();
        
        int x0, y0;
        System.out.println();
        
        do{
            System.out.println("Seleccione una casilla valida para empezar!");
            
            System.out.print("Fila: ");
            y0 = in.nextInt();
            
            System.out.print("Columna: ");
            x0 = in.nextInt();
        
        } while (!t.validCoord(x0 - 1, y0 - 1));

        t.ponerBombas(y0, x0);
        t.recorridoCascada(x0 - 1, y0 - 1);

    }


}