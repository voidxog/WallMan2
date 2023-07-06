package com.colorata.wallman.core.data

sealed interface Coordinates {
    data class ExactCoordinates(val latitude: Float, val longitude: Float): Coordinates {
        override fun toString(): String {
            return """
                Coordinates.ExactCoordinates(
                    latitude=${latitude}f,
                    longitude=${longitude}f
                )
            """.trimIndent()
        }

        override fun equals(other: Any?): Boolean {
            if (other !is ExactCoordinates) return false
            return other.latitude == latitude && other.longitude == longitude
        }

        override fun hashCode(): Int {
            var result = latitude.hashCode()
            result = 31 * result + longitude.hashCode()
            return result
        }
    }
    data class AddressCoordinates(val address: String): Coordinates
}