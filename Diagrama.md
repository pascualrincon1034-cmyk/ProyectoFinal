# Diagrama de Clases - Buscaminas

```mermaid
classDiagram
    class Casilla {
        <<abstract>>
        -boolean mostrar
        -boolean bandera
        +revelarCasilla()*
        +ponerBandera()*
        +quitarBandera()*
        +getMostrar() boolean
        +getBandera() boolean
    }

    class Bomba {
        +toString() String
    }

    class Normal {
        -boolean incorrecto
        -int bombas
        +getBomba() int
        +getIncorrecto() boolean
        +setIncorrecto()
        +toString() String
        +sumarBomba()
    }

    class Tablero {
        -Casilla[][] tablero
        -int columnas
        -int filas
        -int minas
        -int minas_restantes
        -int banderas
        +Tablero(int, int, int)
        +getTablero() Casilla[][]
        +getBanderas() int
        +getMinas_restantes() int
        +getMinas() int
        +getDimension() String
        +printTablero()
        +ponerBombas(int, int)
        +ponerBomba(int, int) boolean
        +sumarBombaAdyacentes(int, int)
        +normalABomba(int, int)
        +validCoord(int, int) boolean
        +recorridoCascada(int, int)
        +ponerQuitarBandera(int, int) boolean
        +revelarCasilla(int, int) int
        +revelarTablero()
        +revelarTodo()
    }

    class Buscaminas {
        -Tablero t
        -int dificultad
        -long tiempoPartida
        +{static} DIFICULTADES String[]
        +Buscaminas()
        +getTablero() Tablero
        +getDificultad() int
        +getTiempoPartida() long
        +playBuscaminas()
        +informacionPartida()
        +inicializarTablero()
    }

    class Partida {
        -int dificultad
        -String dimensiones
        -int minas
        -long tiempo
        -int minas_encontradas
        -boolean victoria
        +Partida(int, String, int, long, int)
        +getVictoria() boolean
        +getDificultad() int
        +getTiempo() long
        +getMinasEnontradas() int
        +minasEncontradasCmp(Partida) boolean
        +tiempoCmp(Partida) boolean
        +informacionPartida()
    }

    class RegistroPartidas {
        -ArrayList~Partida~ registro
        -int partidasJugadas
        -int partidasPerdidas
        -int partidasGanadas
        +RegistroPartidas()
        +getRegistro() ArrayList
        +getPartidasJugadas() int
        +getPartidasGanadas() int
        +getPartidasPerdidas() int
        +anadirPartida(Partida)
        +swap(int, int)
        +imprimirRegistro()
        +imprimirVictorias()
        +imprimirVictorias(int)
        +timeSort()
        +minasEncontradasSort()
        +buscarPorTiempo(long, int) int
        +buscarPorMinas(int, int) int
        +findLeft(int) int
        +findRight(int) int
    }

    class Sesion {
        -Scanner in
        -RegistroPartidas registro
        +sesion()
        +informacionPartidas()
        +buscarPartida()
        +mostrarPartidasGanadas()
        +reglas()
        +menuPrincipal() int
        +titulo()
        +limpiarConsola()
    }

    %% Relaciones de herencia
    Bomba --|> Casilla
    Normal --|> Casilla

    %% Relaciones de composición (rombo relleno *)
    %% Tablero crea y posee las Casillas
    Tablero "1" *-- "1..*" Casilla : contiene
    
    %% Buscaminas crea el Tablero
    Buscaminas "1" *-- "1" Tablero : crea
    
    %% Sesion crea RegistroPartidas
    Sesion "1" *-- "1" RegistroPartidas : crea

    %% Relaciones de agregación (rombo vacío o)
    %% Buscaminas puede existir sin Sesion
    Sesion "1" o-- "0..1" Buscaminas : usa
    
    %% Partida puede existir sin RegistroPartidas
    RegistroPartidas "1" o-- "0..*" Partida : almacena
```

## Explicación de las Relaciones

### Herencia
- **Bomba** y **Normal** heredan de **Casilla** (clase abstracta)
  - Utilizan todas las propiedades y métodos de Casilla
  - Implementan su propia versión del método `toString()`

### Composición (Rombo Relleno ♦)
Las siguientes relaciones son de **composición**, es decir, el objeto hijo **NO puede existir** sin el objeto padre:

- **Tablero → Casilla** (1 a 1..*)
  - Tablero CREA y es responsable de todas sus Casillas
  - Si el Tablero se destruye, sus Casillas desaparecen
  - Cardinalidad: Un tablero siempre contiene al menos 16 casillas (mínimo 4×4)

- **Buscaminas → Tablero** (1 a 1)
  - Buscaminas CREA el Tablero en su constructor
  - Si Buscaminas se destruye, el Tablero se destruye
  - Cardinalidad: Una partida de Buscaminas tiene exactamente un Tablero

- **Sesion → RegistroPartidas** (1 a 1)
  - Sesion CREA RegistroPartidas en su constructor
  - El registro existe solo mientras exista la Sesion
  - Cardinalidad: Una sesión tiene exactamente un registro

### Agregación (Rombo Vacío ○)
Las siguientes relaciones son de **agregación**, es decir, el objeto hijo **PUEDE existir** sin el objeto padre:

- **Sesion ⟷ Buscaminas** (1 a 0..1)
  - Sesion UTILIZA Buscaminas (la crea temporalmente en cada partida)
  - Buscaminas puede existir de forma independiente sin Sesion
  - Si Sesion se destruye, Buscaminas puede seguir existiendo
  - Cardinalidad: En un momento dado, una sesión tiene 0 o 1 Buscaminas activa

- **RegistroPartidas ⟷ Partida** (1 a 0..*)
  - RegistroPartidas ALMACENA Partidas (referencias)
  - Partida puede existir sin estar registrada
  - Si RegistroPartidas se destruye, las Partidas pueden seguir existiendo
  - Cardinalidad: Un registro puede tener múltiples partidas almacenadas

### Cardinalidades
- `1` : Exactamente uno
- `0..*` : Cero o más (muchos)
- `1..*` : Uno o más
- `0..1` : Cero o uno

## Cómo Visualizar el Diagrama en GitHub

### Opción 1: Visualización Automática en GitHub
1. Sube este archivo `Diagrama.md` a tu repositorio
2. GitHub renderizará automáticamente los bloques Mermaid
3. El diagrama se mostrará visualmente en la vista previa del archivo

### Opción 2: Visualización en VS Code
1. Instala la extensión **Markdown Preview Mermaid Support**
2. Abre el archivo `Diagrama.md`
3. Presiona `Ctrl+Shift+V` para ver la vista previa
4. El diagrama se renderizará en la vista previa

### Opción 3: Editor Mermaid Online
1. Ve a https://mermaid.live
2. Copia el contenido del bloque de código Mermaid
3. Pégalo en el editor online para visualizarlo

## Convenciones del Diagrama

### Tipos de Relaciones
- **Línea sólida con punta vacía (`--|>`)**: Herencia
- **Línea con rombo relleno (`*--`)**: Composición (el padre es responsable del ciclo de vida del hijo)
- **Línea con rombo vacío (`o--`)**: Agregación (el hijo puede existir independientemente del padre)

### Cardinalidades
Se muestran en los extremos de las líneas de relación:
- `1` : Exactamente uno
- `0..*` : Cero o más (muchos)
- `1..*` : Uno o más
- `0..1` : Cero o uno

Ejemplo: `Tablero "1" *-- "0..*" Casilla` significa "Un tablero compone cero o más casillas"
