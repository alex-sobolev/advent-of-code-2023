package adventofcode2023.day3

import java.io.File

class Task1(private val filePath: String = "src/main/kotlin/day3/input.txt") {
    fun solution(): Int {
        val input = File(filePath).readText()
        val validNums = mutableListOf<Int>()
        val lines = input.split("\n")

        for ((index1, line) in lines.withIndex()) {
            var index2 = 0

            while (index2 < line.length) {
                val char = line[index2]

                if (char.isDigit()) {
                    val start = index2
                    var end = index2
                    var strNum = "$char"

                    index2++

                    while (index2 < line.length && line[index2].isDigit()) {
                        strNum += line[index2]
                        index2++
                        end++
                    }

                    val leftCheck = if (start > 0) {
                        val ch = line[start - 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val rightCheck = if (end < line.length - 1) {
                        val ch = line[end + 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val topCheck = if (index1 > 0) (start..end).any {
                        val ch = lines[index1 - 1][it]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val bottomCheck = if (index1 < lines.size - 1) (start..end).any {
                        val ch = lines[index1 + 1][it]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val topDiagonalLeftCheck = if (index1 > 0 && start > 0) {
                        val ch = lines[index1 - 1][start - 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val topDiagonalRightCheck = if (index1 > 0 && end < line.length - 1) {
                        val ch = lines[index1 - 1][end + 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val bottomDiagonalLeftCheck = if (index1 < lines.size - 1 && start > 0) {
                        val ch = lines[index1 + 1][start - 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    val bottomDiagonalRightCheck = if (index1 < lines.size - 1 && end < line.length - 1) {
                        val ch = lines[index1 + 1][end + 1]
                        ch != '.' && !ch.isDigit()
                    } else false

                    if (leftCheck || rightCheck || topCheck || bottomCheck || topDiagonalLeftCheck || topDiagonalRightCheck || bottomDiagonalLeftCheck || bottomDiagonalRightCheck) {
                        validNums.add(strNum.toInt())
                    }
                }
                index2++
            }
        }

        return validNums.sum()
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day3/input2.txt")
    println("test task1: ${test.solution()}") // 4361

    val task1 = Task1()
    println("day3 task1: ${task1.solution()}") // 544433
}
