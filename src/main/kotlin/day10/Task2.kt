package adventofcode2023.day10

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day10/input.txt") {
    fun solve(): Int {
        val input = File(filePath).readLines()
        val (graph, visited) = traverseMap(input)

        var count = 0

        for (row in graph.indices) {
            for (col in 0 until graph[0].length) {
                val pointCrossings = countCrossingsForPoint(graph, visited, row, col)

                if (pointCrossings % 2 != 0) {
                    count++
                }
            }
        }

        return count
    }

    private val connectionMap = mapOf(
        '|' to Pair('N', 'S'), // top, bottom
        '-' to Pair('E', 'W'), // right, left
        'L' to Pair('N', 'E'), // top, right
        'J' to Pair('N', 'W'), // top, left
        '7' to Pair('S', 'W'), // bottom, left
        'F' to Pair('S', 'E') // bottom, right
    )

    private data class Point(val row: Int, val col: Int, val connection: Char, val distance: Int)

    private fun countCrossingsForPoint(graph: List<String>, visited: Set<Pair<Int, Int>>, row: Int, col: Int): Int {
        val line = graph[row]
        val cell = line[col]
        var crossings = 0

        if (cell == '.') {
            for (i in 0 until col) {
                if (row to i in visited && (line[i] == 'J' || line[i] == 'L' || line[i] == '|')) {
                    crossings++
                }
            }
        }

        return crossings
    }

    private fun traverseMap(graph: List<String>): Pair<List<String>, Set<Pair<Int, Int>>> {
        val (startingRow, startingCol) = getStartingPoint(graph)
        val startingElem = getElemType(graph, startingRow, startingCol)

        val queue = ArrayDeque<Point>()
        queue.add(Point(startingRow, startingCol, startingElem, 0))

        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(startingRow to startingCol)
        val distances = mutableListOf<Int>()

        fun moveToNextPoint(nextMove: Pair<Int, Int>?, distance: Int) {
            if (nextMove != null && nextMove !in visited) {
                val (row, col) = nextMove
                val connectElem = graph[row][col]

                if (connectElem != '.' && connectElem != 'S') {
                    val newDistance = distance + 1

                    queue.add(Point(row, col, connectElem, newDistance))
                    visited.add(nextMove)
                    distances.add(newDistance)
                }
            }
        }

        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()
            val (direction1, direction2) = connectionMap[point.connection]!!
            val nextMove1 = getNextMove(graph, point.row, point.col, direction1)
            val nextMove2 = getNextMove(graph, point.row, point.col, direction2)

            moveToNextPoint(nextMove1, point.distance)
            moveToNextPoint(nextMove2, point.distance)
        }

        val updatedGraph = graph.toMutableList()

        for (row in graph.indices) {
            for (col in 0 until graph[0].length) {
                if (graph[row][col] == 'S') {
                    updatedGraph[row] = updatedGraph[row].replaceRange(col, col + 1, startingElem.toString())
                }

                if (row to col !in visited) {
                    updatedGraph[row] = updatedGraph[row].replaceRange(col, col + 1, ".")
                }
            }
        }

        return Pair(updatedGraph, visited)
    }

    private fun getNextMove(graph: List<String>, row: Int, col: Int, direction: Char): Pair<Int, Int>? {
        val height = graph.size
        val width = graph[0].length

        return when (direction) {
            'N' -> if (row > 0) Pair(row - 1, col) else null
            'S' -> if (row < height - 1) Pair(row + 1, col) else null
            'E' -> if (col < width - 1) Pair(row, col + 1) else null
            'W' -> if (col > 0) Pair(row, col - 1) else null
            else -> null
        }
    }

    private fun getStartingPoint(graph: List<String>): Pair<Int, Int> {
        val height = graph.size
        val width = graph[0].length

        for (i in 0..<height) {
            for (j in 0..<width) {
                if (graph[i][j] == 'S') {
                    return Pair(i, j)
                }
            }
        }

        return Pair(0, 0)
    }

    // get `S` element type based on its neighbours
    private fun getElemType(graph: List<String>, row: Int, col: Int): Char {
        val height = graph.size
        val width = graph[0].length

        val leftElem = if (col > 0) {
            val elem = graph[row][col - 1]
            if (elem == '-' || elem == 'L' || elem == 'F') elem else null
        } else null

        val rightElem = if (col < width - 1) {
            val elem = graph[row][col + 1]
            if (elem == '-' || elem == 'J' || elem == '7') elem else null
        } else null

        val topElem = if (row > 0) {
            val elem = graph[row - 1][col]
            if (elem == '|' || elem == '7' || elem == 'F') elem else null
        } else null

        val bottomElem = if (row < height - 1) {
            val elem = graph[row + 1][col]
            if (elem == '|' || elem == 'L' || elem == 'J') elem else null
        } else null

        // each element in a cycle is always connected to 2 elements
        val startingElem = when {
            topElem != null && bottomElem != null -> '|'
            leftElem != null && rightElem != null -> '-'
            topElem != null && rightElem != null -> 'L'
            topElem != null && leftElem != null -> 'J'
            bottomElem != null && leftElem != null -> '7'
            bottomElem != null && rightElem != null -> 'F'
            else -> null
        }

        return startingElem!!
    }
}

fun main() {
    val test1 = Task2("src/main/kotlin/day10/input4.txt")
    println("task2 test1: ${test1.solve()}") // 4

    val test2 = Task2("src/main/kotlin/day10/input5.txt")
    println("task2 test2: ${test2.solve()}") // 8

    val test3 = Task2("src/main/kotlin/day10/input6.txt")
    println("task2 test3: ${test3.solve()}") // 10

    val task2 = Task2()
    println("task 2: ${task2.solve()}") // 415
}
