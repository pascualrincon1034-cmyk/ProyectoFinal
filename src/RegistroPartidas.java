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


}