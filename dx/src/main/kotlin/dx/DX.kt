package dx

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class DX {
    fun doctor(values: List<Dx.Doctor>): Flow<String> = channelFlow {
        values.forEach {
            val output = it.command.runCommand(File("."))
            if (output?.contains(it.expectOutputContains) == true) {
                with(DiagnosisResult.Success) {
                    send("$color[${symbol}]${TextPainter.RESET} ${it.name} succeeded\n")
                }
            } else {
                with(DiagnosisResult.Failure) {
                    send("$color[${symbol}]${TextPainter.RESET} ${it.name} failed \nExpected: ${it.expectOutputContains}\nReceived: $output\n")
                }
            }

        }
    }
}

fun String.runCommand(workingDir: File): String? {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

enum class DiagnosisResult(val symbol: Char, val color: String) {
    Success('✓', TextPainter.GREEN),
    Info('i', TextPainter.YELLOW),
    Warning('!', TextPainter.YELLOW),
    Failure('✖', TextPainter.RED)
}