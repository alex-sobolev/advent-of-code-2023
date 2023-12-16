package adventofcode2023.day4

import java.io.File
import kotlin.math.pow

class Task1(private val filePath: String = "src/main/kotlin/day4/input.txt") {
    fun solution(): Int {
        val input = File(filePath).readText()
        val lines = input.split("\n")
        val points = mutableListOf<Int>()

        for (line in lines) {
            val (left, right) = line.split(":")
            val cardId = left.trim().split(" ").filter { it.trim() != "" }[1].trim().toInt()
            val cardPoints = mutableListOf<Int>()

            val (winningNums, givenNums) = right.split("|").map { it.trim() }
                .map { it.split(" ").map { it.trim() }.filter { it != "" }.map { it.toInt() }.sorted() }

            for (num in winningNums) {
                if (givenNums.contains(num)) {
                    cardPoints.add(num)
                }
            }

            when (cardPoints.size) {
                0 -> points.add(0)
                else -> points.add(2.0.pow(cardPoints.size - 1).toInt())
            }
        }

        return points.sum()
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day4/input2.txt")
    println("task1 test: ${test.solution()}") // 13

    val task1 = Task1()
    println("task 1: ${task1.solution()}") // 23750
}
