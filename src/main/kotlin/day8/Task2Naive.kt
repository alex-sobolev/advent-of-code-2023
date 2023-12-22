package adventofcode2023.day8

import java.io.File

class Task2Naive(private val filePath: String = "src/main/kotlin/day8/input.txt") {
    fun solve(): Long {
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

        val currentNodes = mutableListOf<String>()

        graph.keys.forEach { key ->
            if (key[key.length - 1] == 'A') {
                currentNodes.add(key)
            }
        }

        var stepsCount = 0.toLong()

        while (!allEndWithZ(currentNodes)) {
            val newCurrentNodes = mutableListOf<String>()
            val index = stepsCount % directions.length
            val direction = directions[index.toInt()]

            currentNodes.forEach { currentNode ->
                when (direction) {
                    'L' -> newCurrentNodes.add(graph[currentNode]!!.first)
                    'R' -> newCurrentNodes.add(graph[currentNode]!!.second)
                }
            }

            currentNodes.clear()
            currentNodes.addAll(newCurrentNodes)
            stepsCount++
        }

        return stepsCount
    }

    private fun allEndWithZ(nodes: List<String>): Boolean = nodes.all { it[it.length - 1] == 'Z' }
}

fun main() {
    val test = Task2Naive("src/main/kotlin/day8/input4.txt")
    println("task2 test: ${test.solve()}") // 6

    val task2 = Task2Naive()

    // This solution will take forever to run
    // See optimized solution in `src/main/kotlin/day8/Task2Optimized.kt`
    println("task 2: ${task2.solve()}")
}