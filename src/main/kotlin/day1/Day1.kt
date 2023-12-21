package adventofcode2023.day1

import java.io.File
import java.io.InputStream
import java.util.*

class Day1(private val filePath: String = "src/main/kotlin/day1/input.txt") {
    fun task1(): Int {
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

    fun task2(): Int {
        val result = mutableListOf<Int>()
        val inputStream: InputStream = File(filePath).inputStream()

        val wordsAndDigits = listOf(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        ) + (0..9).map { it.toString() }

        println("wordsAndDigits: $wordsAndDigits")

        inputStream.bufferedReader().forEachLine { line ->
            val matches = mutableListOf<Int>()

            for (i in line.indices) {
                for (word in wordsAndDigits) {
                    if (i + word.length <= line.length && line.regionMatches(
                            i,
                            word,
                            0,
                            word.length,
                            ignoreCase = true
                        )
                    ) {
                        matches.add(
                            word.toIntOrNull() ?: when (word.lowercase(Locale.getDefault())) {
                                "zero" -> 0
                                "one" -> 1
                                "two" -> 2
                                "three" -> 3
                                "four" -> 4
                                "five" -> 5
                                "six" -> 6
                                "seven" -> 7
                                "eight" -> 8
                                "nine" -> 9
                                else -> null
                            }!!
                        )
                    }
                }
            }

            val first = matches.firstOrNull()
            val last = matches.lastOrNull()
            if (first != null && last != null) {
                val num = "$first$last".toInt()
                result.add(num)
            }
        }

        return result.sum()
    }
}

fun main() {
    val res = Day1()

    println("task1: ${res.task1()}") // 53386
    println("task2: ${res.task2()}") // 53312
}