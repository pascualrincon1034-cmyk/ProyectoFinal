import java.util.Scanner;

public class Sesion{

    private Scanner in = new Scanner(System.in);
    private RegistroPartidas registro = new RegistroPartidas();

    
    public void sesion(){
        int accion = 0;
        titulo();
        limpiarConsola();
        do {
            System.out.println();
            accion = menuPrincipal();
            limpiarConsola();
            if (accion == 1){
                Buscaminas b = new Buscaminas();
                b.playBuscaminas();
                System.out.println("              [ Presiona ENTER para volver ]");
                in.nextLine();
                in.nextLine();

                Partida p = new Partida(b.getDificultad(), b.getTablero().getDimension(), b.getTablero().getMinas(), 
                b.getTiempoPartida(), b.getTablero().getMinas() - b.getTablero().getMinas_restantes());
                registro.anadirPartida(p);
            
            } else if (accion == 2){
                reglas();

            } else if (accion == 3){
                

            }
        } while (accion != 4);

        limpiarConsola();
        System.out.println("Fin del juego.");

    }

    public void reglas(){
        System.out.println("El jugador cuenta con 4 opciones para la creacion de la partida.");
        System.out.println("1. Personalizado, siendo las dimensiones seleccionadas por el jugador");
        System.out.println("Este debe tener en cuenta que las dimensiones minimas son de 4x4 y la cantidad de minas debe ser");
        System.out.println("a lo sumo la cantidad de casillas menos nueve.");
        System.out.println("2. Principiante, de 9x9 y 10 minas.");
        System.out.println("3. Intermedio, con 16x16 y 40 minas");
        System.out.println("4. Experto, con 16x30 y 99 minas\n");
        System.out.println("En cada turno el jugador debe seleccionar una casilla valida ya sea para poner, quitar una bandera");
        System.out.println("o revelar una casilla. Si el jugador desea cancelar una accion, debe poner -1 en columna\n");
        System.out.println("El numero de una casilla representa la cantidad de bombas que esta esta tocando. el # representa");
        System.out.println("las banderas, el * las bombas y la X las casillas que no son bombas sobre las que se coloco una bandera\n");
        System.out.println("              [ Presiona ENTER para volver ]");
        in.nextLine();
        in.nextLine();

    }

    public int menuPrincipal(){
        String[] acciones = {"Nueva partida", "Consultar reglas","Consultar registro", "Terminar sesion"};
        int accion;

        System.out.println("Bienvenido!  Seleccione una opcion a continuacion:");
        for (int i = 0; i < 4; i++){
            System.out.printf("%d. %s         ", i + 1, acciones[i]);
            System.out.println();
        }
        do{
            System.out.print("Escoja una opcion: ");
            accion = in.nextInt();
        } while (accion > 4 || accion < 1);
        return accion;
    }

    public void titulo(){
            
        String banner = """
          ____  _   _ ____   ____    _    __  __ ___ _   _    _    ____ 
         | __ )| | | / ___| / ___|  / \\  |  \\/  |_ _| \\ | |  / \\  / ___|
         |  _ \\| | | \\___ \\| |     / _ \\ | |\\/| || ||  \\| | / _ \\ \\___ \\ 
         | |_) | |_| |___) | |___ / ___ \\| |  | || || |\\  |/ ___ \\ ___) |
         |____/ \\___/\\____/ \\____/_/   \\_\\_|  |_|___|_| \\_/_/   \\_\\____/ 
        """;

        System.out.println(banner);
        System.out.println("              [ Presiona ENTER para empezar ]");
        in.nextLine();
    }

    public void limpiarConsola(){
        for (int i = 0; i < 50; i++){
            System.out.println();
        }
    }
}