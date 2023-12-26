package adventofcode2023.day11

import java.io.File
import kotlin.math.max
import kotlin.math.min

class Task2(val filePath: String = "src/main/kotlin/day11/input.txt") {
    fun solve(): Long {
        val lines = File(filePath).readLines()

        val emptyRows = lines.foldIndexed(mutableListOf<Int>()) { index, acc, row ->
            when {
                row.all { it == '.' } -> {
                    acc.add(index)
                    acc
                }

                else -> acc
            }
        }

        val emptyCols = lines[0].foldIndexed(mutableListOf<Int>()) { index, acc, _ ->
            when {
                lines.all { it[index] == '.' } -> {
                    acc.add(index)
                    acc
                }

                else -> acc
            }
        }

        val galaxies = mutableListOf<Pair<Int, Int>>()

        lines.forEachIndexed { row, s ->
            s.forEachIndexed { col, c ->
                if (c == '#') {
                    galaxies.add(Pair(row, col))
                }
            }
        }

        var totalDistance = 0.toLong()

        for (i in 0 until galaxies.size - 1) {
            for (j in i + 1 until galaxies.size) {
                val a = galaxies[i]
                val b = galaxies[j]

                val dist = findDist(a, b, emptyRows, emptyCols)
                totalDistance += dist
            }
        }

        return totalDistance
    }

    private fun findDist(a: Pair<Int, Int>, b: Pair<Int, Int>, emptyRows: List<Int>, emptyCols: List<Int>): Long {
        val x1 = min(a.first, b.first)
        val x2 = max(a.first, b.first)
        val y1 = min(a.second, b.second)
        val y2 = max(a.second, b.second)
        val expansion = 1_000_000

        var dist = 0.toLong()

        for (i in x1 until x2) {
            dist += 1

            if (i in emptyRows) {
                dist += expansion - 1
            }
        }

        for (j in y1 until y2) {
            dist += 1

            if (j in emptyCols) {
                dist += expansion - 1
            }
        }

        return dist
    }
}

fun main() {
    val test1 = Task2("src/main/kotlin/day11/input2.txt")
    println("task2 test1: ${test1.solve()}") // 82000210

    val task2 = Task2()
    println("task 2: ${task2.solve()}") // 593821230983
}
