package adventofcode2023.day4

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day4/input.txt") {
    fun solution(): Int {
        val input = File(filePath).readText()
        val lines = input.split("\n")
        val points = mutableListOf<Int>()

        return 0
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day4/input2.txt")
    println("task 1: ${test.solution()}")

    val task1 = Task1()
    println("task 1: ${task1.solution()}")
}
