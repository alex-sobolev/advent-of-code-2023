package adventofcode2023.day8

import java.io.File

class Task2Optimized(private val filePath: String = "src/main/kotlin/day8/input.txt") {
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

        val cycleSizes = mutableListOf<Int>()

        for (currentNode in currentNodes) {
            var stepsCount = 0
            var node = currentNode

            while (node[node.length - 1] != 'Z') {
                val index = stepsCount % directions.length
                val direction = directions[index]

                when (direction) {
                    'L' -> node = graph[node]!!.first
                    'R' -> node = graph[node]!!.second
                }

                stepsCount++
            }

            cycleSizes.add(stepsCount)
        }

        println("cycleSizes: $cycleSizes")

        return listLcm(cycleSizes)
    }

    // Greatest common divider
    private fun gcd(a: Long, b: Long): Long {
        if (a == 0.toLong()) return b
        return gcd(b % a, a)
    }

    // Least common multiple
    private fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

    // Least common multiple for a list of Int
    private fun listLcm(a: List<Int>): Long {
        var result = a[0].toLong()

        for (i in 1 until a.size) {
            val item = a[i].toLong()
            result = lcm(result, item)
        }

        return result
    }
}

fun main() {
    val test = Task2Optimized("src/main/kotlin/day8/input4.txt")
    println("task2 test: ${test.solve()}") // 6

    val task2 = Task2Optimized()
    println("task 2: ${task2.solve()}") // 14299763833181
}