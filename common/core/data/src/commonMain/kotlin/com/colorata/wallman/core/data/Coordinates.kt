package com.voidxog.wallman2.core.data

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
sealed interface Coordinates {

    @Serializable
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

    @Serializable
    data class AddressCoordinates(val address: String): Coordinates
}
