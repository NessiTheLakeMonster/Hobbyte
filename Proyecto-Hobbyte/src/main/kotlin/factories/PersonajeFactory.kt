package factories

class PersonajeFactory {

    fun crearNombres(): String {
        val nombres = listOf("Gandalf", "Bilbo", "Thorin")
        val random = (0..32).random()
        return nombres[random]
    }
}