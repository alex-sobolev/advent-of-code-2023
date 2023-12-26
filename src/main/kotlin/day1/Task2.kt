package adventofcode2023.day1

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day1/input.txt") {
    fun solve(): Int {
        var result = 0

        File(filePath).bufferedReader().forEachLine { line ->
            val num = findFirstAndLastDigits(line)
            result += num
        }

        return result
    }

    private val digitDict = mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    private fun findFirstAndLastDigits(line: String): Int {
        val nums = mutableListOf<Int>()

        for (i in line.indices) {
            val char = line[i]

            when {
                char.isDigit() -> {
                    nums.add(char.toString().toInt())
                }

                char.isLetter() -> {
                    for (key in digitDict.keys) {
                        val wordLen = key.length
                        val end = i + wordLen

                        if (end <= line.length) {
                            val substr = line.substring(i, end)

                            if (substr == key) {
                                nums.add(digitDict[key]!!)
                            }
                        }
                    }
                }
            }
        }

        return "${nums.first()}${nums.last()}".toInt()
    }
}

fun main() {
    val task2 = Task2()

    println("task2: ${task2.solve()}") // 53312
}