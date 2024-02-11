# Hobbyte

Proyecto realizado por Inés Mª Barrera Llerena

---
# Manual del Administrador

## Montar el servidor
En el archivo `Constantes` se debe cambiar el parámetro de la `IP` a la que tenga el
usuario actualmente.

## Rutas para los Usuarios

#### Registro 
- Ruta : `http://192.168.0.29:8090/registrar`
- Verbo : `POST`

##### JSON para poder probar la ruta
```json
{
  "nombre": "John",
  "apellido": "Doe",
  "email": "john.doe2@example.com",
  "password": "password123"
}
```

#### Inicio de sesión
- Ruta : `http://192.168.0.29:8090/login`
- Verbo : `POST`

##### JSON para poder probar la ruta
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

#### Cerrar sesión
- Ruta : `http://192.168.0.29:8090/logout`
- Verbo : `GET`

##### JSON para poder probar la ruta
```json
{
  "token" : "Añade aquí tu token"
}
```

## Rutas para las pruebas

#### Insertar las pruebas
- Ruta : `http://192.168.0.29:8090/insertarPruebas`
- Verbo : `POST`

## Rutas para los personajes

#### Creación de los personajes
- Ruta : `http://192.168.0.29:8090/crearPersonajes/{id}`
- Verbo : `POST`
- Parámetros necesarios:
  - `id` -> la id del usuario del que van a ser los personajes

## Rutas para las partidas

#### Creación de una partida [RUTA PROTEGIDA CON TOKEN]
- Ruta : `http://192.168.0.29:8090/generarPartida/{id}`
- Verbo : `POST`
- Parámetros necesarios:
  - `id` -> la id del usuario que va a crear esa partida
  - `Bearer Token`


----
# Enunciado

Vamos a realizar un jueguecillo sobre El Hobbit.
Consiste en lo siguiente:
- Tenemos tres personajes: `Gandalf, Thorin y Bilbo`. 
  - Gandalf puede hacer las pruebas de magia
  - Thorin las de fuerza 
  - Bilbo las de habilidad.
-  Los tres parten con una capacidad máxima respectiva de 50. Estos datos están inicialmente 
en la base de datos. No hará falta hacer un CRUD de ellos.

- Se genera un tablero de 20 casillas en las que habrá escondidas 20 pruebas (una por casilla). Las pruebas pueden 
ser de los 3 tipos (magia, fuerza o habilidad) y la cantidad de esfuerzo necesario para lograrla. 
    - Esta cantidad de esfuerzo serán los siguientes números posibles: `5, 10, 15, 20, 25, 30, 35, 40, 45 y 50`.<br> 
Estas pruebas también 
están ya en la base de datos pero debemos dar la posibilidad de generar con factorías una cantidad determinada de ellas.

- Una vez generado el tablero (los tableros), comienza el juego. Consiste en destapar casillas y realizar las pruebas 
(por el héroe correspondiente y si le queda poder) de forma que:
    - Si el poder del héroe es mayor que el necesario para realizar la prueba se logra la prueba al 90 %.
    - Si es igual se logra al 70%.
    - Si es menor se consigue al 50%.
    - Si no se logra, ese héroe pierde toda su capacidad y quedará inactivo. Si se logra pierde la capacidad necesaria para lograr la prueba (se resta).
    - Si al héroe no le quedara poder para afrontar la prueba se debe dar por perdida.
- *Ganamos* cuando hemos logrado destapar la mitad de las casillas y vive, al menos, un héroe.
- *Perdemos* si nos quedamos sin héroes o si hemos destapado 5 casillas seguidas perdiendo.

---

# Estructura para la Base de Datos 

## Tabla Usuario

| id | nombre | apellido | email | password |
|----|--------|----------|-------|-----------|

## Tabla Personaje

| id | nombre | idTipoPrueba | capacidadMax | idUsuario |
|----|--------|--------------|--------------|-----------|

## Tabla Prueba

| id | idTipoPrueba | esfuerzo |
|----|------|----------|

## Tabla TipoPrueba

| id | nombre |
|----|------|

## Tabla Partida

| id | idUsuario | estado |
|----|-----------|--------|

## Tabla Casilla

| id | idPartida | idTipoPrueba | capacidadActual |
|----|-----------|--------------|-----------------|

€