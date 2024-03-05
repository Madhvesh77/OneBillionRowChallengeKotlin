import java.io.BufferedReader
import java.io.FileReader
import java.security.KeyStore.Entry
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Collectors.*
import java.util.HashMap.*

fun main() {
    var start = System.currentTimeMillis()
    calculateAndPrint()
    println("Took ${(System.currentTimeMillis()-start)/1000} Seconds")
}

private fun calculateAndPrint() {
    val bufferedReader = BufferedReader(FileReader("src/main/resources/measurements.txt"))
    val lineStream = bufferedReader.lines().parallel().collect(
        groupingBy(
            { line -> line.substring(0, line.indexOf(";")) },
            summarizingDouble { line -> (line.substring(line.indexOf(";") + 1)).toDouble() })
    )
    val result = lineStream.entries.stream().collect(toMap(
        { entry -> entry.key },
        { entry ->
            val stats = entry.value
            String.format("%.1f/%.1f/%.1f", stats.min, stats.average, stats.max)
        },
        { l, r -> r },
        { TreeMap() }
    ))
    println(result)
}