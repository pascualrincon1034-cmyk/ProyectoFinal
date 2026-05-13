# Proyecto Final

## 1. Descomposición del problema

### Mecanica de juego

1. Al iniciar la partida, el jugador puede escoger entre 4 modos de juego. 
El primero es personalizado, en el cual el jugador selecciona las dimensiones del tablero y la cantidad de minas. Las restricciones establecidas son de 8 a 24 filas, 8 a 32 columnas y el numero de minas debe ser a lo sumo 1/3 de las casillas.
El segundo es principiante, con 9 filas, 9 columnas y 10 minas.
El tercero es intermedio, con 16 filas, 16 columnas y 40 minas.
El cuarto es experto, con 16 filas, 30 columnas y 99 minas.

2. Se genera el tablero de juego con las dimensiones correspondientes. Al principio no se muestra ninguna casilla, por lo cual el jugador debe escoger una para inicializar el juego. Al escoger la casilla, esta no puede tocar ninguna bomba, y se realiza el recorrido en cascada para enseñar las casillas adyacentes que no sean bombas. 

3. Una vez inicializado el tablero, el jugador puede realizar dos acciones, Quitar / Poner bandera o revelar una casilla. Estas acciones no se pueden aplicar a una casilla que ya fue revelada, y no se puede revelar una casilla que tiene bandera, primero se le debe quitar.

4. Para colocar o quitar una bandera, si se aplica sobre una casilla que no tiene bandera, a esta se le coloca una, y si ya tenia una bandera, se le quita.

5. Al revelar una casilla, si esta es una bomba, se acaba el juego, revelando la posicion de las bombas a las que no se les coloco una bandera y las banderas que estuvieran colocadas sobre una casilla sin bomba se reemplazan por una x. Si se revela una casilla que no es bomba pero esta tocando alguna, se revela el numero de bombas que esta tocando. Si se revela una casilla sin bomba y esta no esta tocando ninguna bomba, se realiza el recorrido en cascada.

6. El juego finaliza al revelar una casilla con bomba, perdiendo la partida, o cuando todas las minas tengan bandera y la cantidad de banderas colocadas sea igual a la cantidad de minas. 

### Descomposicion

Partiendo de lo más pequeño, la pieza fundamental del buscaminas son las casillas. Una casilla es una pieza que contiene informacion y es revelada cuando el jugador ejecuta la accion de revelar. Existen dos tipos de casillas, las normales y las bombas o minas. Ambas casillas pueden tener banderas y ambas se pueden revelar. 
Una casilla normal posee atributos adicionales, como lo son la cantidad de minas que está tocando y el caso en el que el jugador pierde el juego y una de estas casillas tuviera una bandera, esta se muestra con una X.

Las casillas existen dentro de un tablero con dimensiones filas x columnas y n minas. El tablero se encarga de asignar las minas y actualizar los valores de las casillas normales, colocar banderas y revelar las casillas. El tablero lleva la cuenta la cantidad de banderas colocadas y las minas encontradas. 

El buscaminas es el que se encarga de la interaccion entre el jugador y el tablero al ejecutar la accion seleccionada por el usuario en el tablero, y modificando el estado de las casillas. El buscaminas se encarga de evaluar los criterios de finalizacion del juego. Ademas, guarda la informacion respecto a la duracion de la partida y la cantidad de minas encontradas.

La informacion de cada partida consiste la dificultad de la partida, las dimensiones, las minas, el tiempo, las minas encontradas, y si se gano la partida o no.

La informacion de las partidas se almacena en un registro de partidas, el cual va creciendo a medida que se juegan mas partidas. Este registro permite leer la informacion de las partidas jugadas segun algun criterio de busqueda.

Todo vive dentro de la sesion, la cual se encarga de la interaccion entre el usuario, buscaminas y registro de partidas. Al finalizar una partida, la informacion de la partida se añade al registro. El usuario puede consultar el registro para conocer la informacion de las partidas jugadas en la sesion, y buscar alguna partida.

### Convenciones usadas:

1. \# representa las banderas.
2. \* representa las minas.
3. X representa las casillas normales a las que se le puso bandera.

## 2. Desiciones de diseño POO y Estructuras de datos

### Clase Casilla, Normal y Bomba: 

La clase Casilla es una clase abstracta que tiene como subclases a Normal y Bomba. Esto dado que no tiene sentido tener una instancia de casilla que no sea Normal o Bomba. Los atributos de casilla son bandera y mostrar, los cuales heredan las clases hijas. La clase Bomba solo tiene los atributos que hereda de la superclase, mientras que la Normal posee minas, que se refiere a la cantidad de minas que toca la casilla e incorrecto, el cual es true cuando una casilla Normal tiene bandera. Ambas subclases tienen su metodo toString, que se usa para mostrar el estado de las casillas en printTablero. 

### Clase tablero: 

Acá se demuestra que es necesario que Normal y Bomba hereden de Casilla. El tablero contiene casillas, tanto normales como bomba, por lo cual ambas deben tener una superclase en comun para poder almacenar ambas subclases en la misma estructura de datos (declarándola para el tipo de dato de la superclase). Entre Casilla y tablero hay una relacion de composicion, y las casillas se encuentran dentro del atributo tablero, un arreglo bidimensional estatico. Se escogió esta estructura de datos debido a que la cantidad de casillas en el tablero se mantiene estatica, permitiendo mantener un control estricto sobre el numero de casillas y acceder a estas más facilmente. Los atributos de tablero llevan el registro del tablero y se actualizan segun las acciones del jugador. 

### Clase Buscaminas:

La clase buscaminas tiene como atributos dificultad, tiempo de la partida, un tablero t y si se ganó la partida o no, además de una variable estática que permite mapear entre los nombres de las dificultades y los numeros del 0 al 3. La relación entre Tablero y Buscaminas es de Composición. La clase Buscaminas se encarga de la interacción entre el jugador y el tablero, actualizando el tablero tras cada acción del jugador, y verificando tras cada turno los criterios de finalizacion del juego con base en los atributos del tablero. Una vez se pierda o gane, se asigna true o false al atributo victoria y se determina cuanto duró la partida. 

### Clase Partida:

La clase Partida permite almacenar la informacion de cada partida jugada en sus atributos, los cuales son dificultad, tiempo, dimensiones, minas, tiempo, minas encontradas y victoria. Los metodos permiten organizar el registro de partidas por minas encontradas o por tiempo, pero siempre separandolos por dificultad, dado que no tiene sentido comparar los tiempos o las minas encontradas de partidas de distinta dificultad. 

### Clase RegistroPartida: 

La clase RegistroPartida se encarga de almacenar el numero de partidas, partidas ganadas, partidas perdidas y cada una de las partidas en un ArrayList<> de partidas. Se escogió esta estructura de datos dado que la cantidad de partidas jugadas va creciendo. Esta clase ademas se encarga de los metodos de ordenamiento y busqueda por los criterios de tiempo y minas encontradas. La relacion entre Partidas y RegistroPartidas es de agregación.

### Clase Sesion:

La clase sesion es la que se encarga del flujo de programa. Sesion contiene un registro de partidas, siendo la relacion entre RegistroPartidas y Sesion de composicion. La relacion entre Buscaminas y Sesion es de agregacion. 

## 3. Flujo del programa

Para la ejecución del programa, se debe crear una clase con un PSVM, en el cual se instancia una Sesion y despues se invoca sobre esta el metodo sesion(). Al ejecutar el codigo en esta clase, se ejecutará todo el buscaminas. El atributo RegistroPartidas se inicializa con la creacion de la sesion.

A continuación se explica el flujo del programa:

1. Pantalla de titulo, que al presionar enter da paso a un menu con 4 acciones, las cuales son: jugar una partida, acceder al registro, mostrar las reglas de juego y finalizar sesion. El jugador debe seleccionar una de las 4 acciones.

2. Si se escoge jugar partida, se instancia un buscaminas, el cual a su vez instancia un tablero segun la dificultad seleccionada.
Al inicializar el tablero, todas sus casillas son normales y ninguna se está mostrando. Se le pide al jugador que seleccione una casilla para comenzar a jugar. Segun la eleccion del jugador, se seleccionan casillas aleatoriamente para volverlas bomba, sumando 1 al contador de las casillas normales adyacentes. Se mantienen las invariantes mencionadas en mecanica del juego, a excepcion de las dimensiones y cantidad de minas, para las cuales se decidio que las dimensiones minimas serian de 4x4 y la cantidad de minas debe ser a lo sumo filas x columnas - 9. Una vez repartidas las bombas, se revela la casilla seleccionada el usuario, y como se sabe que esta está vacía, se aplica sobre está el metodo recorrer en cascada. 

3. Una vez realizado el movimiento inicial, el usuario puede elegir entre poner / quitar bandera o revelar casilla. Ya se conocen los comportamientos de poner / quitar bandera y revelar casilla, funcionando exactamente igual que como se describio en la mecanica del juego. Se añadió la posibilidad de cancelar una accion, y consiste en poner -1 las coordenadas solicitadas. Así transcurre el juego hasta que se cumpla alguno de los criterios de finalizacion. Una vez se acaba la partida, se instancia Partida con base en la informacion del buscaminas, y se añade la partida al RegistroPartidas, sumando 1 a partidas jugadas, y segun el resultado, se suma 1 a partidas ganadas o perdidas. Se envía al jugador nuevamente al menu principal.

4. Si se escoge mostrar registro, el jugador tiene la posibilidad de mostrar todas las partidas, mostrar partidas ganadas por dificultad, buscar partida, y volver. Para mostrar y mostrar partidas ganadas, simplemente se recorre el arreglo imprimiendo la informacion de las partidas, con la diferencia de que para las ganadas, el numero de partidas ganadas debe ser mayor que 0 y el usuario escoge entre mostrar todas las ganadas y mostrar ganadas por dificultad. Independiente de la opcion que se elija, el arreglo se ordena primero, separando por dificultad, y al recorrer el arreglo se pregunta si la partida en cuestionfue ganada o no, empezando en el indice en el que quedaron las partidas de la dificultad de interes para la busqueda por dificultad. Para mostrar todas las ganadas, se empieza desde 0 y se enseñan todas las ganadas.

5. Para buscar partida, se debe elegir entre buscar por tiempo o buscar por minas encontradas, y se debe seleccionar una dificultad. Si existe la partida en cuestion, el programa imprime la informacion de la partida. En caso contrario, se le dice al usario que tal partida no existe.


Las explicaciones de las opciones que se omitieron son exactamente lo que describe la opcion.