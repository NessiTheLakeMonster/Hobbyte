package utils

object Parametros {
    val ip = "192.168.0.29"
    val port = 8090

    val secret = "algo"
    val issuer = "http://$ip:$port"
    val audience = "http://$ip:$port/rutasVarias"
    val realm = "Access to 'rutasVarias'"
}