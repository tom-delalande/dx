import kotlin.String
import kotlin.collections.List

data class Dx(
  val doctor: List<Doctor>
) {
  data class Doctor(
    val name: String,
    val command: String,
    val expectOutputContains: String
  )
}
