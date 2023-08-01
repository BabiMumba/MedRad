package cd.babitech.medrad.Console
import kotlin.math.*

fun main() {

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371 // Rayon de la terre en kilomètres

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = earthRadius * c
        return distance
    }

// Exemple d'utilisation
    val lat1 = 48.858844 // Latitude de l'utilisateur 1
    val lon1 = 2.294350 // Longitude de l'utilisateur 1

    val lat2 = 51.5074 // Latitude de l'utilisateur 2
    val lon2 = -0.1278 // Longitude de l'utilisateur 2

    val distance = calculateDistance(lat1, lon1, lat2, lon2)
    println("Distance entre les deux utilisateurs : $distance kilomètres")


}