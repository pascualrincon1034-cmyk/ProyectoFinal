import java.util.ArrayList;;

public class RegistroPartidas{

    private ArrayList<Partida> registro;
    private int partidasJugadas;
    private int partidasPerdidas;
    private int partidasGanadas;

    public RegistroPartidas(){
        registro = new ArrayList<>();
        partidasJugadas = 0;
        partidasGanadas = 0;
        partidasPerdidas = 0;
    }

    public ArrayList<Partida> getRegistro(){
        return registro;
    }

    public int getPartidasJugadas(){
        return partidasJugadas;
    }
    
    public int getPartidasGanadas(){
        return partidasGanadas;
    }

    public int getPartidasPerdidas(){
        return partidasPerdidas;
    }

    public void anadirPartida(Partida p){
        registro.add(p);
        partidasJugadas++;
        if (p.getVictoria()){
            partidasGanadas++;
        } else {
            partidasPerdidas++;
        }
    }

    public void swap(int index1, int index2){
        Partida temp = registro.get(index1);
        registro.set(index1, registro.get(index2));
        registro.set(index2, temp);
    }

    public void imprimirRegistro(){
        for (Partida p: registro){
            p.informacionPartida();
        }
    }

    public void imprimirVictorias(){
        for (Partida p: registro){
            if (p.getVictoria()){
                p.informacionPartida();
            }
        }
    }

    public void imprimirVictorias(int left, int right){
        minasEncontradasSort();
        for (int i = left; i <= right; i++){
            Partida p = registro.get(i);
            if (p.getVictoria()){
                p.informacionPartida();
            } 
        }
        System.out.println();
    }

    public void timeSort(){
        for (int i = 0; i < partidasJugadas - 1; i++){
            boolean flag = true;
            for (int j = 0; j < partidasJugadas - i - 1; j++){
                if (!registro.get(j).tiempoCmp(registro.get(j+1))){
                    swap(j,j+1);
                    flag = false;
                }
            }
            if (flag) break;
        }
    }

    public void minasEncontradasSort(){
        for (int i = 0; i < partidasJugadas - 1; i++){
            boolean flag = true;
            for (int j = 0; j < partidasJugadas - i - 1; j++){
                if (!registro.get(j).minasEncontradasCmp(registro.get(j+1))){
                    swap(j,j+1);
                    flag = false;
                }
            }
            if (flag) break;
        }
    }


    /*
    El siguiente metodo ordena el arreglo primero por dificultades, y cada una de estas segun el tiempo, de menor a mayor, para 
    posteriormente buscar una partida por tiempo y dificultad, para lo cual busca los indices entre los cuales quedaron las partidas
    de la dificultad de interes, para ello se apoya del metodo findLeft y findRight.
    */

    public int buscarPorTiempo(long tiempo, int dificultad){
        timeSort();
        int left = findLeft(dificultad);
        if (left == -1) {return -1;}
        
        int right = findRight(dificultad);
        while (left <= right){
            int mid = (left + right)/2;
            if (registro.get(mid).getTiempo() == tiempo){
                return mid;
            }

            if (registro.get(mid).getTiempo() < tiempo){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;

        

    }

    /*
    El siguiente metodo ordena el arreglo primero por dificultades, y cada una de estas segun las minas encontradas, de mayor a menor, para 
    posteriormente buscar una partida por minas encontradas y dificultad, para lo cual busca los indices entre los cuales quedaron las partidas
    de la dificultad de interes, para ello se apoya del metodo findLeft y findRight.
    */

    public int buscarPorMinas(int minas_encontradas, int dificultad){
        minasEncontradasSort();
        int left = findLeft(dificultad);
        if (left == -1) {return -1;}
        
        int right = findRight(dificultad);
        while (left <= right){
            int mid = (left + right)/2;
            if (registro.get(mid).getMinasEnontradas() == minas_encontradas){
                return mid;
            }

            if (registro.get(mid).getMinasEnontradas() > minas_encontradas){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;

        

    }

    public int findLeft(int dificultad){
        for (int i = 0; i < partidasJugadas; i++){
            if (registro.get(i).getDificultad() == dificultad){
                return i;
            }
        }
        return -1;
    }

    public int findRight(int dificultad){
        for (int i = partidasJugadas - 1; i >= 0 ; i--){
            if (registro.get(i).getDificultad() == dificultad){
                return i;
            }
        }
        return -1;
    }

}