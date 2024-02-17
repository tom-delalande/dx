package dx

import Dx
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.runBlocking
import org.pkl.config.java.ConfigEvaluator
import org.pkl.config.kotlin.forKotlin
import org.pkl.config.kotlin.to
import org.pkl.core.ModuleSource

const val DX_VERSION = "0.1.0"

private class Main : CliktCommand(name = "dx") {
    val showVersion: Boolean by option(
        "--version",
        help = "Report a version of KDoctor"
    ).flag()

    override fun run() {
        when {
            showVersion -> {
                println(DX_VERSION)
            }
        }
    }
}

private class Doctor : CliktCommand(name = "doctor", help = "Check current directory dependencies") {
    override fun run() {
        runBlocking {
            val dxConfig = ConfigEvaluator.preconfigured().forKotlin().use { evaluator ->
                evaluator.evaluate(ModuleSource.file("dx.pkl"))
            }.to<Dx>()
            DX().doctor(dxConfig.doctor).collect { line ->
                print(line)
            }
        }
    }
}

fun main(args: Array<String>) = Main().subcommands(Doctor()).main(args)