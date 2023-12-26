package adventofcode2023.day11

import java.io.File
import kotlin.math.abs

class Task1(val filePath: String = "src/main/kotlin/day11/input.txt") {
    fun solve(): Int {
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

        // insert empty rows and cols
        val newLines = lines.toMutableList()

        emptyRows.forEachIndexed { index, row ->
            newLines.add(row + 1 + index, ".".repeat(lines[0].length))
        }

        emptyCols.forEachIndexed { index, col ->
            newLines.forEachIndexed { j, s ->
                newLines[j] = s.substring(0, col + 1 + index) + "." + s.substring(col + 1 + index)
            }
        }

        val galaxies = mutableListOf<Pair<Int, Int>>() // row, col

        newLines.forEachIndexed { row, s ->
            s.forEachIndexed { col, c ->
                if (c == '#') {
                    galaxies.add(Pair(row, col))
                }
            }
        }

        val shortestPaths = mutableListOf<Int>()

        for (i in 0 until galaxies.size - 1) {
            for (j in i + 1 until galaxies.size) {
                val a = galaxies[i]
                val b = galaxies[j]

                val shortestPath = findAbsoluteDiff(a, b)
                shortestPaths.add(shortestPath)
            }
        }

        return shortestPaths.sum()
    }

    private fun findAbsoluteDiff(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        val rowDiff = abs(a.first - b.first)
        val colDiff = abs(a.second - b.second)

        return rowDiff + colDiff
    }
}

fun main() {
    val test1 = Task1("src/main/kotlin/day11/input2.txt")
    println("task1 test1: ${test1.solve()}") // 374

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 9418609
}
