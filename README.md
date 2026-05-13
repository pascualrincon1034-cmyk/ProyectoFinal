# ProyectoFinal - Buscaminas

Un juego de Buscaminas implementado en Java con gestión de partidas y sesiones de usuario.

## Requisitos Previos

- **Java JDK 8 o superior** instalado en tu sistema
- **Git** instalado (para clonar el repositorio)
- Un terminal/línea de comandos

### Verificar instalación de Java

Abre tu terminal/CMD y ejecuta:

```bash
java -version
javac -version
```

Si no ves las versiones, necesitas instalar Java desde [java.com](https://www.java.com/) o [adoptium.net](https://adoptium.net/).

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/pascualrincon1034-cmyk/ProyectoFinal
cd ProyectoFinal
```


### 2. Compilar el proyecto

Desde la carpeta raíz del proyecto, ejecuta:

```bash
javac -d . src/*.java
```

Este comando compila todos los archivos `.java` en la carpeta `src/`.

### 3. Ejecutar el programa

Una vez compilado, ejecuta:

```bash
java Run
```

O también puedes ejecutar directamente sin compilar manualmente (si tienes configurado correctamente):

```bash
javac src/Run.java && java -cp src Run
```

## Estructura del Proyecto

```
ProyectoFinal/
├── src/
│   ├── Run.java           # Punto de entrada del programa
│   ├── Sesion.java        # Gestión de sesiones de usuario
│   ├── Partida.java       # Lógica de una partida
│   ├── Tablero.java       # Tablero del juego
│   ├── Casilla.java       # Casillas individuales
│   ├── Buscaminas.java    # Lógica del juego
│   ├── Normal.java        # Tipo de casilla normal
│   └── Bomba.java         # Tipo de casilla con bomba
├── README.md              # Este archivo
└── diseno.md              # Documentación del diseño
```

## Solución de Problemas

| Problema | Solución |
|----------|----------|
| `java: command not found` | Java no está instalado o no está en el PATH |
| `javac: command not found` | JDK no está instalado (necesitas el Development Kit, no solo el Runtime) |
| `class Run not found` | Asegúrate de estar en la carpeta raíz del proyecto |
| Errores de compilación | Verifica que todos los archivos `.java` estén en la carpeta `src/` |

## Uso

Al ejecutar el programa, se iniciará una sesión interactiva donde podrás:
- Crear una nueva partida
- Ver el historial de partidas
- Jugar al Buscaminas

## Documentación Adicional

- Ver [diseno.md](diseno.md) para más detalles sobre la arquitectura del proyecto
- Ver [Diagrama.md](Diagrama.md) para el diagrama de clases