import com.voidxog.wallman2.core.data.serialization.Json
import com.voidxog.wallman2.wallpapers.walls
import kotlinx.serialization.encodeToString
import org.junit.Test
import java.io.File

class WallpaperSerializationTest {
    @Test
    fun wallpaperSerializationTest() {
        File("output.txt").writeText(Json.encodeToString(walls.toList()))
    }
}
