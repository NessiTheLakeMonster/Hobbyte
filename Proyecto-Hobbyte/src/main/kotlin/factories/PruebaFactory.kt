package factories

import model.Prueba

class PruebaFactory {

    /*
    1 -> magia
    2 -> fuerza
    3 -> habilidad
     */
    fun randomTipos(): Int {
        val tipos = listOf(1, 2, 3)
        val random = (0..2).random()
        return tipos[random]
    }

    fun randomEsfuerzo(): Int {
        val esfuerzo = listOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50)
        val random = (0..9).random()
        return esfuerzo[random]
    }

    fun generatePruebas(): List<Prueba> {
        val pruebas = mutableListOf<Prueba>()

        for (i in 1..20) {
            val tipo = randomTipos()
            val esfuerzo = randomEsfuerzo()
            val prueba = Prueba(tipo, esfuerzo)
            pruebas.add(prueba)
        }

        return pruebas
    }
}