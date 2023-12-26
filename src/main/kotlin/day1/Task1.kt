package adventofcode2023.day1

import java.io.File
import java.io.InputStream

class Task1(private val filePath: String = "src/main/kotlin/day1/input.txt") {
    fun solve(): Int {
        val inputStream: InputStream = File(filePath).inputStream()
        val lineList = mutableListOf<Int>()

        inputStream.bufferedReader().forEachLine { line ->
            val first = line.first { it.isDigit() }
            val last = line.last { it.isDigit() }
            val num = "$first$last".toInt()

            lineList.add(num)
        }

        return lineList.sum()
    }
}

fun main() {
    val task1 = Task1()

    println("task1: ${task1.solve()}") // 53386
}