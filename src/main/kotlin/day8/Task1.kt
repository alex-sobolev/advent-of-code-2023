package adventofcode2023.day8

import java.io.File

class Task1(private val filePath: String = "src/main/kotlin/day8/input.txt") {
    fun solve(): Int {
        val input = File(filePath).readLines()
        val graph = mutableMapOf<String, Pair<String, String>>()
        val directions = input[0].trim()

        for (i in 2..<input.size) {
            val (node, edges) = input[i]
                .split("=")
                .map { it.trim() }

            val (left, right) = edges
                .split(",")
                .map {
                    it.trim()
                        .replace("(", "")
                        .replace(")", "")
                }

            graph[node] = Pair(left, right)
        }

        var currentNode = "AAA"
        var stepsCount = 0

        while (currentNode != "ZZZ") {
            val index = stepsCount % directions.length
            val direction = directions[index]

            when (direction) {
                'L' -> currentNode = graph[currentNode]!!.first
                'R' -> currentNode = graph[currentNode]!!.second
            }

            stepsCount++
        }

        return stepsCount
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day8/input2.txt")
    println("task1 test1: ${test.solve()}") // 2

    val test2 = Task1("src/main/kotlin/day8/input3.txt")
    println("task1 test2: ${test2.solve()}") // 6

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 18157
}
